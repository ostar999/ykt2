package org.jsoup.parser;

import cn.hutool.core.text.CharPool;
import com.alipay.sdk.util.h;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import kotlin.text.Typography;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Token;

/* loaded from: classes9.dex */
class Tokeniser {
    static final char replacementChar = 65533;
    Token.Comment commentPending;
    StringBuilder dataBuffer;
    Token.Doctype doctypePending;
    private Token emitPending;
    private ParseErrorList errors;
    private Token.StartTag lastStartTag;
    private CharacterReader reader;
    Token.Tag tagPending;
    private TokeniserState state = TokeniserState.Data;
    private boolean isEmitPending = false;
    private StringBuilder charBuffer = new StringBuilder();
    private boolean selfClosingFlagAcknowledged = true;

    public Tokeniser(CharacterReader characterReader, ParseErrorList parseErrorList) {
        this.reader = characterReader;
        this.errors = parseErrorList;
    }

    private void characterReferenceError(String str) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Invalid character reference: %s", str));
        }
    }

    public void acknowledgeSelfClosingFlag() {
        this.selfClosingFlagAcknowledged = true;
    }

    public void advanceTransition(TokeniserState tokeniserState) {
        this.reader.advance();
        this.state = tokeniserState;
    }

    public String appropriateEndTagName() {
        Token.StartTag startTag = this.lastStartTag;
        if (startTag == null) {
            return null;
        }
        return startTag.tagName;
    }

    public char[] consumeCharacterReference(Character ch, boolean z2) {
        int iIntValue;
        if (this.reader.isEmpty()) {
            return null;
        }
        if ((ch != null && ch.charValue() == this.reader.current()) || this.reader.matchesAny('\t', '\n', '\r', '\f', ' ', Typography.less, '&')) {
            return null;
        }
        this.reader.mark();
        if (!this.reader.matchConsume(DictionaryFactory.SHARP)) {
            String strConsumeLetterThenDigitSequence = this.reader.consumeLetterThenDigitSequence();
            boolean zMatches = this.reader.matches(';');
            if (!(Entities.isBaseNamedEntity(strConsumeLetterThenDigitSequence) || (Entities.isNamedEntity(strConsumeLetterThenDigitSequence) && zMatches))) {
                this.reader.rewindToMark();
                if (zMatches) {
                    characterReferenceError(String.format("invalid named referenece '%s'", strConsumeLetterThenDigitSequence));
                }
                return null;
            }
            if (z2 && (this.reader.matchesLetter() || this.reader.matchesDigit() || this.reader.matchesAny('=', CharPool.DASHED, '_'))) {
                this.reader.rewindToMark();
                return null;
            }
            if (!this.reader.matchConsume(h.f3376b)) {
                characterReferenceError("missing semicolon");
            }
            return new char[]{Entities.getCharacterByName(strConsumeLetterThenDigitSequence).charValue()};
        }
        boolean zMatchConsumeIgnoreCase = this.reader.matchConsumeIgnoreCase("X");
        CharacterReader characterReader = this.reader;
        String strConsumeHexSequence = zMatchConsumeIgnoreCase ? characterReader.consumeHexSequence() : characterReader.consumeDigitSequence();
        if (strConsumeHexSequence.length() == 0) {
            characterReferenceError("numeric reference with no numerals");
            this.reader.rewindToMark();
            return null;
        }
        if (!this.reader.matchConsume(h.f3376b)) {
            characterReferenceError("missing semicolon");
        }
        try {
            iIntValue = Integer.valueOf(strConsumeHexSequence, zMatchConsumeIgnoreCase ? 16 : 10).intValue();
        } catch (NumberFormatException unused) {
            iIntValue = -1;
        }
        if (iIntValue != -1 && ((iIntValue < 55296 || iIntValue > 57343) && iIntValue <= 1114111)) {
            return Character.toChars(iIntValue);
        }
        characterReferenceError("character outside of valid range");
        return new char[]{65533};
    }

    public void createCommentPending() {
        this.commentPending = new Token.Comment();
    }

    public void createDoctypePending() {
        this.doctypePending = new Token.Doctype();
    }

    public Token.Tag createTagPending(boolean z2) {
        Token.Tag startTag = z2 ? new Token.StartTag() : new Token.EndTag();
        this.tagPending = startTag;
        return startTag;
    }

    public void createTempBuffer() {
        this.dataBuffer = new StringBuilder();
    }

    public boolean currentNodeInHtmlNS() {
        return true;
    }

    public void emit(Token token) {
        Validate.isFalse(this.isEmitPending, "There is an unread token pending!");
        this.emitPending = token;
        this.isEmitPending = true;
        Token.TokenType tokenType = token.type;
        if (tokenType != Token.TokenType.StartTag) {
            if (tokenType != Token.TokenType.EndTag || ((Token.EndTag) token).attributes == null) {
                return;
            }
            error("Attributes incorrectly present on end tag");
            return;
        }
        Token.StartTag startTag = (Token.StartTag) token;
        this.lastStartTag = startTag;
        if (startTag.selfClosing) {
            this.selfClosingFlagAcknowledged = false;
        }
    }

    public void emitCommentPending() {
        emit(this.commentPending);
    }

    public void emitDoctypePending() {
        emit(this.doctypePending);
    }

    public void emitTagPending() {
        this.tagPending.finaliseTag();
        emit(this.tagPending);
    }

    public void eofError(TokeniserState tokeniserState) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpectedly reached end of file (EOF) in input state [%s]", tokeniserState));
        }
    }

    public void error(TokeniserState tokeniserState) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpected character '%s' in input state [%s]", Character.valueOf(this.reader.current()), tokeniserState));
        }
    }

    public TokeniserState getState() {
        return this.state;
    }

    public boolean isAppropriateEndTagToken() {
        Token.StartTag startTag = this.lastStartTag;
        if (startTag == null) {
            return false;
        }
        return this.tagPending.tagName.equals(startTag.tagName);
    }

    public Token read() {
        if (!this.selfClosingFlagAcknowledged) {
            error("Self closing flag not acknowledged");
            this.selfClosingFlagAcknowledged = true;
        }
        while (!this.isEmitPending) {
            this.state.read(this, this.reader);
        }
        if (this.charBuffer.length() <= 0) {
            this.isEmitPending = false;
            return this.emitPending;
        }
        String string = this.charBuffer.toString();
        StringBuilder sb = this.charBuffer;
        sb.delete(0, sb.length());
        return new Token.Character(string);
    }

    public void transition(TokeniserState tokeniserState) {
        this.state = tokeniserState;
    }

    public String unescapeEntities(boolean z2) {
        StringBuilder sb = new StringBuilder();
        while (!this.reader.isEmpty()) {
            sb.append(this.reader.consumeTo('&'));
            if (this.reader.matches('&')) {
                this.reader.consume();
                char[] cArrConsumeCharacterReference = consumeCharacterReference(null, z2);
                if (cArrConsumeCharacterReference == null || cArrConsumeCharacterReference.length == 0) {
                    sb.append('&');
                } else {
                    sb.append(cArrConsumeCharacterReference);
                }
            }
        }
        return sb.toString();
    }

    private void error(String str) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), str));
        }
    }

    public void emit(String str) {
        this.charBuffer.append(str);
    }

    public void emit(char[] cArr) {
        this.charBuffer.append(cArr);
    }

    public void emit(char c3) {
        this.charBuffer.append(c3);
    }
}
