package com.hp.hpl.sparta;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.util.RandomUtil;
import com.alipay.sdk.util.h;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import kotlin.text.Typography;

/* loaded from: classes4.dex */
class ParseCharStream implements ParseSource {
    private static final char[] BEGIN_CDATA;
    private static final char[] BEGIN_ETAG;
    private static final char[] CHARREF_BEGIN;
    private static final char[] COMMENT_BEGIN;
    private static final char[] COMMENT_END;
    private static final boolean DEBUG = true;
    private static final char[] DOCTYPE_BEGIN;
    private static final char[] ENCODING;
    private static final char[] END_CDATA;
    private static final char[] END_EMPTYTAG;
    private static final char[] ENTITY_BEGIN;
    public static final int HISTORY_LENGTH = 100;
    private static final boolean H_DEBUG = false;
    private static final char[] MARKUPDECL_BEGIN;
    private static final int MAX_COMMON_CHAR = 128;
    private static final char[] NDATA;
    private static final char[] PI_BEGIN;
    private static final char[] PUBLIC;
    private static final char[] QU_END;
    private static final char[] SYSTEM;
    private static final int TMP_BUF_SIZE = 255;
    private static final char[] VERSION;
    private static final char[] VERSIONNUM_PUNC_CHARS;
    private static final char[] XML_BEGIN;
    private final int CBUF_SIZE;
    private final char[] cbuf_;
    private int ch_;
    private int curPos_;
    private String docTypeName_;
    private final String encoding_;
    private int endPos_;
    private final Hashtable entities_;
    private boolean eos_;
    private final ParseHandler handler_;
    private final CharCircBuffer history_;
    private boolean isExternalDtd_;
    private int lineNumber_;
    private final ParseLog log_;
    private final Hashtable pes_;
    private final Reader reader_;
    private String systemId_;
    private final char[] tmpBuf_;
    private static final char[] NAME_PUNCT_CHARS = {'.', CharPool.DASHED, '_', ':'};
    private static final boolean[] IS_NAME_CHAR = new boolean[128];

    static {
        for (char c3 = 0; c3 < 128; c3 = (char) (c3 + 1)) {
            IS_NAME_CHAR[c3] = isNameChar(c3);
        }
        COMMENT_BEGIN = "<!--".toCharArray();
        COMMENT_END = "-->".toCharArray();
        PI_BEGIN = "<?".toCharArray();
        QU_END = "?>".toCharArray();
        DOCTYPE_BEGIN = "<!DOCTYPE".toCharArray();
        XML_BEGIN = "<?xml".toCharArray();
        ENCODING = "encoding".toCharArray();
        VERSION = "version".toCharArray();
        VERSIONNUM_PUNC_CHARS = new char[]{'_', '.', ':', CharPool.DASHED};
        MARKUPDECL_BEGIN = "<!".toCharArray();
        CHARREF_BEGIN = "&#".toCharArray();
        ENTITY_BEGIN = "<!ENTITY".toCharArray();
        NDATA = "NDATA".toCharArray();
        SYSTEM = "SYSTEM".toCharArray();
        PUBLIC = "PUBLIC".toCharArray();
        BEGIN_CDATA = "<![CDATA[".toCharArray();
        END_CDATA = "]]>".toCharArray();
        END_EMPTYTAG = "/>".toCharArray();
        BEGIN_ETAG = "</".toCharArray();
    }

    public ParseCharStream(String str, Reader reader, ParseLog parseLog, String str2, ParseHandler parseHandler) throws ParseException, IOException {
        this(str, reader, null, parseLog, str2, parseHandler);
    }

    public ParseCharStream(String str, Reader reader, char[] cArr, ParseLog parseLog, String str2, ParseHandler parseHandler) throws ParseException, IOException {
        this.docTypeName_ = null;
        Hashtable hashtable = new Hashtable();
        this.entities_ = hashtable;
        this.pes_ = new Hashtable();
        this.ch_ = -2;
        this.isExternalDtd_ = false;
        this.CBUF_SIZE = 1024;
        this.curPos_ = 0;
        this.endPos_ = 0;
        this.eos_ = false;
        this.tmpBuf_ = new char[255];
        this.lineNumber_ = 1;
        this.history_ = null;
        parseLog = parseLog == null ? ParseSource.DEFAULT_LOG : parseLog;
        this.log_ = parseLog;
        this.encoding_ = str2 == null ? null : str2.toLowerCase();
        hashtable.put("lt", "<");
        hashtable.put("gt", ">");
        hashtable.put("amp", "&");
        hashtable.put("apos", "'");
        hashtable.put("quot", "\"");
        if (cArr != null) {
            this.cbuf_ = cArr;
            this.curPos_ = 0;
            this.endPos_ = cArr.length;
            this.eos_ = true;
            this.reader_ = null;
        } else {
            this.reader_ = reader;
            this.cbuf_ = new char[1024];
            fillBuf();
        }
        this.systemId_ = str;
        this.handler_ = parseHandler;
        parseHandler.setParseSource(this);
        readProlog();
        parseHandler.startDocument();
        Element element = readElement();
        String str3 = this.docTypeName_;
        if (str3 != null && !str3.equals(element.getTagName())) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("DOCTYPE name \"");
            stringBuffer.append(this.docTypeName_);
            stringBuffer.append("\" not same as tag name, \"");
            stringBuffer.append(element.getTagName());
            stringBuffer.append("\" of root element");
            parseLog.warning(stringBuffer.toString(), this.systemId_, getLineNumber());
        }
        while (isMisc()) {
            readMisc();
        }
        Reader reader2 = this.reader_;
        if (reader2 != null) {
            reader2.close();
        }
        this.handler_.endDocument();
    }

    public ParseCharStream(String str, char[] cArr, ParseLog parseLog, String str2, ParseHandler parseHandler) throws ParseException, IOException {
        this(str, null, cArr, parseLog, str2, parseHandler);
    }

    private int fillBuf() throws IOException {
        if (this.eos_) {
            return -1;
        }
        int i2 = this.endPos_;
        char[] cArr = this.cbuf_;
        if (i2 == cArr.length) {
            this.endPos_ = 0;
            this.curPos_ = 0;
        }
        Reader reader = this.reader_;
        int i3 = this.endPos_;
        int i4 = reader.read(cArr, i3, cArr.length - i3);
        if (i4 <= 0) {
            this.eos_ = true;
            return -1;
        }
        this.endPos_ += i4;
        return i4;
    }

    private int fillBuf(int i2) throws IOException {
        int i3;
        int i4;
        if (this.eos_) {
            return -1;
        }
        int i5 = 0;
        if (this.cbuf_.length - this.curPos_ < i2) {
            int i6 = 0;
            while (true) {
                i3 = this.curPos_;
                int i7 = i3 + i6;
                i4 = this.endPos_;
                if (i7 >= i4) {
                    break;
                }
                char[] cArr = this.cbuf_;
                cArr[i6] = cArr[i3 + i6];
                i6++;
            }
            int i8 = i4 - i3;
            this.endPos_ = i8;
            this.curPos_ = 0;
            i5 = i8;
        }
        int iFillBuf = fillBuf();
        if (iFillBuf != -1) {
            return i5 + iFillBuf;
        }
        if (i5 == 0) {
            return -1;
        }
        return i5;
    }

    private boolean isCdSect() throws ParseException, IOException {
        return isSymbol(BEGIN_CDATA);
    }

    private final boolean isChar(char c3) throws ParseException, IOException {
        if (this.curPos_ < this.endPos_ || fillBuf() != -1) {
            return this.cbuf_[this.curPos_] == c3;
        }
        throw new ParseException(this, "unexpected end of expression.");
    }

    private final boolean isChar(char c3, char c4) throws ParseException, IOException {
        if (this.curPos_ >= this.endPos_ && fillBuf() == -1) {
            return false;
        }
        char c5 = this.cbuf_[this.curPos_];
        return c5 == c3 || c5 == c4;
    }

    private final boolean isChar(char c3, char c4, char c5, char c6) throws ParseException, IOException {
        if (this.curPos_ >= this.endPos_ && fillBuf() == -1) {
            return false;
        }
        char c7 = this.cbuf_[this.curPos_];
        return c7 == c3 || c7 == c4 || c7 == c5 || c7 == c6;
    }

    private final boolean isComment() throws ParseException, IOException {
        return isSymbol(COMMENT_BEGIN);
    }

    private boolean isDeclSep() throws ParseException, IOException {
        return isPeReference() || isS();
    }

    private boolean isDocTypeDecl() throws ParseException, IOException {
        return isSymbol(DOCTYPE_BEGIN);
    }

    private boolean isETag() throws ParseException, IOException {
        return isSymbol(BEGIN_ETAG);
    }

    private boolean isEncodingDecl() throws ParseException, IOException {
        return isSymbol(ENCODING);
    }

    private boolean isEntityDecl() throws ParseException, IOException {
        return isSymbol(ENTITY_BEGIN);
    }

    private final boolean isEntityValue() throws ParseException, IOException {
        return isChar(CharPool.SINGLE_QUOTE, '\"');
    }

    private static boolean isExtender(char c3) {
        if (c3 == 183 || c3 == 903 || c3 == 1600 || c3 == 3654 || c3 == 3782 || c3 == 12293 || c3 == 720 || c3 == 721 || c3 == 12445 || c3 == 12446) {
            return true;
        }
        switch (c3) {
            case R2.drawable.shape_computer_review_test_btn_bg /* 12337 */:
            case R2.drawable.shape_computer_review_test_btn_bg_round8 /* 12338 */:
            case R2.drawable.shape_computer_statistics_top_bg /* 12339 */:
            case R2.drawable.shape_computer_statustics_line_bg /* 12340 */:
            case R2.drawable.shape_computer_time_bg /* 12341 */:
                return true;
            default:
                switch (c3) {
                    case R2.drawable.shape_point_progress_one_bg /* 12540 */:
                    case R2.drawable.shape_point_progress_three /* 12541 */:
                    case R2.drawable.shape_point_progress_three_bg /* 12542 */:
                        return true;
                    default:
                        return false;
                }
        }
    }

    private boolean isExternalId() throws ParseException, IOException {
        return isSymbol(SYSTEM) || isSymbol(PUBLIC);
    }

    private static final boolean isIn(char c3, char[] cArr) {
        for (char c4 : cArr) {
            if (c3 == c4) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLetter(char c3) {
        return RandomUtil.BASE_CHAR.indexOf(Character.toLowerCase(c3)) != -1;
    }

    private boolean isMisc() throws ParseException, IOException {
        return isComment() || isPi() || isS();
    }

    private boolean isNameChar() throws ParseException, IOException {
        char cPeekChar = peekChar();
        return cPeekChar < 128 ? IS_NAME_CHAR[cPeekChar] : isNameChar(cPeekChar);
    }

    private static boolean isNameChar(char c3) {
        return Character.isDigit(c3) || isLetter(c3) || isIn(c3, NAME_PUNCT_CHARS) || isExtender(c3);
    }

    private boolean isPeReference() throws ParseException, IOException {
        return isChar('%');
    }

    private final boolean isPi() throws ParseException, IOException {
        return isSymbol(PI_BEGIN);
    }

    private final boolean isReference() throws ParseException, IOException {
        return isChar('&');
    }

    private final boolean isS() throws ParseException, IOException {
        return isChar(' ', '\t', '\r', '\n');
    }

    private final boolean isSymbol(char[] cArr) throws ParseException, IOException {
        int length = cArr.length;
        if (this.endPos_ - this.curPos_ < length && fillBuf(length) <= 0) {
            this.ch_ = -1;
            return false;
        }
        char[] cArr2 = this.cbuf_;
        int i2 = this.endPos_;
        this.ch_ = cArr2[i2 - 1];
        if (i2 - this.curPos_ < length) {
            return false;
        }
        for (int i3 = 0; i3 < length; i3++) {
            if (this.cbuf_[this.curPos_ + i3] != cArr[i3]) {
                return false;
            }
        }
        return true;
    }

    private boolean isVersionNumChar() throws ParseException, IOException {
        char cPeekChar = peekChar();
        return Character.isDigit(cPeekChar) || ('a' <= cPeekChar && cPeekChar <= 'z') || (('Z' <= cPeekChar && cPeekChar <= 'Z') || isIn(cPeekChar, VERSIONNUM_PUNC_CHARS));
    }

    private boolean isXmlDecl() throws ParseException, IOException {
        return isSymbol(XML_BEGIN);
    }

    private final char peekChar() throws ParseException, IOException {
        if (this.curPos_ < this.endPos_ || fillBuf() != -1) {
            return this.cbuf_[this.curPos_];
        }
        throw new ParseException(this, "unexpected end of expression.");
    }

    private String readAttValue() throws ParseException, IOException {
        char c3 = readChar(CharPool.SINGLE_QUOTE, '\"');
        StringBuffer stringBuffer = new StringBuffer();
        while (!isChar(c3)) {
            if (isReference()) {
                stringBuffer.append(readReference());
            } else {
                stringBuffer.append(readChar());
            }
        }
        readChar(c3);
        return stringBuffer.toString();
    }

    private void readAttribute(Element element) throws ParseException, IOException {
        String name = readName();
        readEq();
        String attValue = readAttValue();
        if (element.getAttribute(name) != null) {
            ParseLog parseLog = this.log_;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Element ");
            stringBuffer.append(this);
            stringBuffer.append(" contains attribute ");
            stringBuffer.append(name);
            stringBuffer.append("more than once");
            parseLog.warning(stringBuffer.toString(), this.systemId_, getLineNumber());
        }
        element.setAttribute(name, attValue);
    }

    private void readCdSect() throws ParseException, IOException {
        char[] cArr;
        readSymbol(BEGIN_CDATA);
        StringBuffer stringBuffer = null;
        int i2 = 0;
        while (true) {
            cArr = END_CDATA;
            if (isSymbol(cArr)) {
                break;
            }
            if (i2 >= 255) {
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer(i2);
                    stringBuffer.append(this.tmpBuf_, 0, i2);
                } else {
                    stringBuffer.append(this.tmpBuf_, 0, i2);
                }
                i2 = 0;
            }
            this.tmpBuf_[i2] = readChar();
            i2++;
        }
        readSymbol(cArr);
        if (stringBuffer == null) {
            this.handler_.characters(this.tmpBuf_, 0, i2);
            return;
        }
        stringBuffer.append(this.tmpBuf_, 0, i2);
        char[] charArray = stringBuffer.toString().toCharArray();
        this.handler_.characters(charArray, 0, charArray.length);
    }

    private final char readChar() throws ParseException, IOException {
        if (this.curPos_ >= this.endPos_ && fillBuf() == -1) {
            throw new ParseException(this, "unexpected end of expression.");
        }
        char[] cArr = this.cbuf_;
        int i2 = this.curPos_;
        char c3 = cArr[i2];
        if (c3 == '\n') {
            this.lineNumber_++;
        }
        this.curPos_ = i2 + 1;
        return c3;
    }

    private final char readChar(char c3, char c4) throws ParseException, IOException {
        char c5 = readChar();
        if (c5 == c3 || c5 == c4) {
            return c5;
        }
        throw new ParseException(this, c5, new char[]{c3, c4});
    }

    private final char readChar(char c3, char c4, char c5, char c6) throws ParseException, IOException {
        char c7 = readChar();
        if (c7 == c3 || c7 == c4 || c7 == c5 || c7 == c6) {
            return c7;
        }
        throw new ParseException(this, c7, new char[]{c3, c4, c5, c6});
    }

    private final void readChar(char c3) throws ParseException, IOException {
        char c4 = readChar();
        if (c4 != c3) {
            throw new ParseException(this, c4, c3);
        }
    }

    private char readCharRef() throws ParseException, IOException {
        int i2;
        readSymbol(CHARREF_BEGIN);
        if (isChar('x')) {
            readChar();
            i2 = 16;
        } else {
            i2 = 10;
        }
        int i3 = 0;
        while (!isChar(';')) {
            int i4 = i3 + 1;
            this.tmpBuf_[i3] = readChar();
            if (i4 >= 255) {
                this.log_.warning("Tmp buffer overflow on readCharRef", this.systemId_, getLineNumber());
                return ' ';
            }
            i3 = i4;
        }
        readChar(';');
        String str = new String(this.tmpBuf_, 0, i3);
        try {
            return (char) Integer.parseInt(str, i2);
        } catch (NumberFormatException unused) {
            ParseLog parseLog = this.log_;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("\"");
            stringBuffer.append(str);
            stringBuffer.append("\" is not a valid ");
            stringBuffer.append(i2 == 16 ? "hexadecimal" : "decimal");
            stringBuffer.append(" number");
            parseLog.warning(stringBuffer.toString(), this.systemId_, getLineNumber());
            return ' ';
        }
    }

    private final void readComment() throws ParseException, IOException {
        readSymbol(COMMENT_BEGIN);
        while (true) {
            char[] cArr = COMMENT_END;
            if (isSymbol(cArr)) {
                readSymbol(cArr);
                return;
            }
            readChar();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x000e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readContent() throws com.hp.hpl.sparta.ParseException, java.io.IOException {
        /*
            r5 = this;
            r5.readPossibleCharData()
            r0 = 1
        L4:
            if (r0 != 0) goto L7
            return
        L7:
            boolean r1 = r5.isETag()
            r2 = 0
            if (r1 == 0) goto L10
        Le:
            r0 = r2
            goto L4a
        L10:
            boolean r1 = r5.isReference()
            if (r1 == 0) goto L21
            char[] r1 = r5.readReference()
            com.hp.hpl.sparta.ParseHandler r3 = r5.handler_
            int r4 = r1.length
            r3.characters(r1, r2, r4)
            goto L4a
        L21:
            boolean r1 = r5.isCdSect()
            if (r1 == 0) goto L2b
            r5.readCdSect()
            goto L4a
        L2b:
            boolean r1 = r5.isPi()
            if (r1 == 0) goto L35
            r5.readPi()
            goto L4a
        L35:
            boolean r1 = r5.isComment()
            if (r1 == 0) goto L3f
            r5.readComment()
            goto L4a
        L3f:
            r1 = 60
            boolean r1 = r5.isChar(r1)
            if (r1 == 0) goto Le
            r5.readElement()
        L4a:
            r5.readPossibleCharData()
            goto L4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hp.hpl.sparta.ParseCharStream.readContent():void");
    }

    private void readDeclSep() throws ParseException, IOException {
        if (isPeReference()) {
            readPeReference();
        } else {
            readS();
        }
    }

    private void readDocTypeDecl() throws ParseException, IOException {
        readSymbol(DOCTYPE_BEGIN);
        readS();
        this.docTypeName_ = readName();
        if (isS()) {
            readS();
            if (!isChar(Typography.greater) && !isChar('[')) {
                this.isExternalDtd_ = true;
                readExternalId();
                if (isS()) {
                    readS();
                }
            }
        }
        if (isChar('[')) {
            readChar();
            while (!isChar(']')) {
                if (isDeclSep()) {
                    readDeclSep();
                } else {
                    readMarkupDecl();
                }
            }
            readChar(']');
            if (isS()) {
                readS();
            }
        }
        readChar(Typography.greater);
    }

    private void readETag(Element element) throws ParseException, IOException {
        readSymbol(BEGIN_ETAG);
        String name = readName();
        if (!name.equals(element.getTagName())) {
            ParseLog parseLog = this.log_;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("end tag (");
            stringBuffer.append(name);
            stringBuffer.append(") does not match begin tag (");
            stringBuffer.append(element.getTagName());
            stringBuffer.append(")");
            parseLog.warning(stringBuffer.toString(), this.systemId_, getLineNumber());
        }
        if (isS()) {
            readS();
        }
        readChar(Typography.greater);
    }

    private final Element readElement() throws ParseException, IOException {
        Element element = new Element();
        boolean emptyElementTagOrSTag = readEmptyElementTagOrSTag(element);
        this.handler_.startElement(element);
        if (emptyElementTagOrSTag) {
            readContent();
            readETag(element);
        }
        this.handler_.endElement(element);
        return element;
    }

    private boolean readEmptyElementTagOrSTag(Element element) throws ParseException, IOException {
        readChar(Typography.less);
        element.setTagName(readName());
        while (isS()) {
            readS();
            if (!isChar('/', Typography.greater)) {
                readAttribute(element);
            }
        }
        if (isS()) {
            readS();
        }
        boolean zIsChar = isChar(Typography.greater);
        if (zIsChar) {
            readChar(Typography.greater);
        } else {
            readSymbol(END_EMPTYTAG);
        }
        return zIsChar;
    }

    private String readEncodingDecl() throws ParseException, IOException {
        readSymbol(ENCODING);
        readEq();
        char c3 = readChar(CharPool.SINGLE_QUOTE, '\"');
        StringBuffer stringBuffer = new StringBuffer();
        while (!isChar(c3)) {
            stringBuffer.append(readChar());
        }
        readChar(c3);
        return stringBuffer.toString();
    }

    private void readEntityDecl() throws ParseException, IOException {
        String name;
        String externalId;
        Hashtable hashtable;
        readSymbol(ENTITY_BEGIN);
        readS();
        if (isChar('%')) {
            readChar('%');
            readS();
            name = readName();
            readS();
            externalId = isEntityValue() ? readEntityValue() : readExternalId();
            hashtable = this.pes_;
        } else {
            name = readName();
            readS();
            if (isEntityValue()) {
                externalId = readEntityValue();
            } else {
                if (!isExternalId()) {
                    throw new ParseException(this, "expecting double-quote, \"PUBLIC\" or \"SYSTEM\" while reading entity declaration");
                }
                externalId = readExternalId();
                if (isS()) {
                    readS();
                }
                char[] cArr = NDATA;
                if (isSymbol(cArr)) {
                    readSymbol(cArr);
                    readS();
                    readName();
                }
            }
            hashtable = this.entities_;
        }
        hashtable.put(name, externalId);
        if (isS()) {
            readS();
        }
        readChar(Typography.greater);
    }

    private String readEntityRef() throws ParseException, IOException {
        ParseLog parseLog;
        StringBuffer stringBuffer;
        String str;
        readChar('&');
        String name = readName();
        String str2 = (String) this.entities_.get(name);
        if (str2 == null) {
            if (this.isExternalDtd_) {
                parseLog = this.log_;
                stringBuffer = new StringBuffer();
                stringBuffer.append("&");
                stringBuffer.append(name);
                str = "; not found -- possibly defined in external DTD)";
            } else {
                parseLog = this.log_;
                stringBuffer = new StringBuffer();
                stringBuffer.append("No declaration of &");
                stringBuffer.append(name);
                str = h.f3376b;
            }
            stringBuffer.append(str);
            parseLog.warning(stringBuffer.toString(), this.systemId_, getLineNumber());
            str2 = "";
        }
        readChar(';');
        return str2;
    }

    private final String readEntityValue() throws ParseException, IOException {
        char c3 = readChar(CharPool.SINGLE_QUOTE, '\"');
        StringBuffer stringBuffer = new StringBuffer();
        while (!isChar(c3)) {
            if (isPeReference()) {
                stringBuffer.append(readPeReference());
            } else if (isReference()) {
                stringBuffer.append(readReference());
            } else {
                stringBuffer.append(readChar());
            }
        }
        readChar(c3);
        return stringBuffer.toString();
    }

    private final void readEq() throws ParseException, IOException {
        if (isS()) {
            readS();
        }
        readChar('=');
        if (isS()) {
            readS();
        }
    }

    private String readExternalId() throws ParseException, IOException {
        char[] cArr = SYSTEM;
        if (isSymbol(cArr)) {
            readSymbol(cArr);
        } else {
            char[] cArr2 = PUBLIC;
            if (!isSymbol(cArr2)) {
                throw new ParseException(this, "expecting \"SYSTEM\" or \"PUBLIC\" while reading external ID");
            }
            readSymbol(cArr2);
            readS();
            readPubidLiteral();
        }
        readS();
        readSystemLiteral();
        return "(WARNING: external ID not read)";
    }

    private void readMarkupDecl() throws ParseException, IOException {
        if (isPi()) {
            readPi();
            return;
        }
        if (isComment()) {
            readComment();
            return;
        }
        if (isEntityDecl()) {
            readEntityDecl();
            return;
        }
        if (!isSymbol(MARKUPDECL_BEGIN)) {
            throw new ParseException(this, "expecting processing instruction, comment, or \"<!\"");
        }
        while (!isChar(Typography.greater)) {
            if (isChar(CharPool.SINGLE_QUOTE, '\"')) {
                char c3 = readChar();
                while (!isChar(c3)) {
                    readChar();
                }
                readChar(c3);
            } else {
                readChar();
            }
        }
        readChar(Typography.greater);
    }

    private void readMisc() throws ParseException, IOException {
        if (isComment()) {
            readComment();
        } else if (isPi()) {
            readPi();
        } else {
            if (!isS()) {
                throw new ParseException(this, "expecting comment or processing instruction or space");
            }
            readS();
        }
    }

    private final String readName() throws ParseException, IOException {
        this.tmpBuf_[0] = readNameStartChar();
        StringBuffer stringBuffer = null;
        int i2 = 1;
        while (isNameChar()) {
            if (i2 >= 255) {
                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer(i2);
                    stringBuffer.append(this.tmpBuf_, 0, i2);
                } else {
                    stringBuffer.append(this.tmpBuf_, 0, i2);
                }
                i2 = 0;
            }
            this.tmpBuf_[i2] = readChar();
            i2++;
        }
        if (stringBuffer == null) {
            return Sparta.intern(new String(this.tmpBuf_, 0, i2));
        }
        stringBuffer.append(this.tmpBuf_, 0, i2);
        return stringBuffer.toString();
    }

    private char readNameStartChar() throws ParseException, IOException {
        char c3 = readChar();
        if (isLetter(c3) || c3 == '_' || c3 == ':') {
            return c3;
        }
        throw new ParseException(this, c3, "letter, underscore, colon");
    }

    private String readPeReference() throws ParseException, IOException {
        readChar('%');
        String name = readName();
        String str = (String) this.pes_.get(name);
        if (str == null) {
            ParseLog parseLog = this.log_;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No declaration of %");
            stringBuffer.append(name);
            stringBuffer.append(h.f3376b);
            parseLog.warning(stringBuffer.toString(), this.systemId_, getLineNumber());
            str = "";
        }
        readChar(';');
        return str;
    }

    private final void readPi() throws ParseException, IOException {
        readSymbol(PI_BEGIN);
        while (true) {
            char[] cArr = QU_END;
            if (isSymbol(cArr)) {
                readSymbol(cArr);
                return;
            }
            readChar();
        }
    }

    private void readPossibleCharData() throws ParseException, IOException {
        int i2;
        loop0: while (true) {
            i2 = 0;
            while (!isChar(Typography.less) && !isChar('&') && !isSymbol(END_CDATA)) {
                this.tmpBuf_[i2] = readChar();
                if (this.tmpBuf_[i2] == '\r' && peekChar() == '\n') {
                    this.tmpBuf_[i2] = readChar();
                }
                i2++;
                if (i2 == 255) {
                    break;
                }
            }
            this.handler_.characters(this.tmpBuf_, 0, 255);
        }
        if (i2 > 0) {
            this.handler_.characters(this.tmpBuf_, 0, i2);
        }
    }

    private void readProlog() throws ParseException, IOException {
        if (isXmlDecl()) {
            readXmlDecl();
        }
        while (isMisc()) {
            readMisc();
        }
        if (isDocTypeDecl()) {
            readDocTypeDecl();
            while (isMisc()) {
                readMisc();
            }
        }
    }

    private final void readPubidLiteral() throws ParseException, IOException {
        readSystemLiteral();
    }

    private final char[] readReference() throws ParseException, IOException {
        return isSymbol(CHARREF_BEGIN) ? new char[]{readCharRef()} : readEntityRef().toCharArray();
    }

    private final void readS() throws ParseException, IOException {
        readChar(' ', '\t', '\r', '\n');
        while (isChar(' ', '\t', '\r', '\n')) {
            readChar();
        }
    }

    private final void readSymbol(char[] cArr) throws ParseException, IOException {
        int length = cArr.length;
        if (this.endPos_ - this.curPos_ < length && fillBuf(length) <= 0) {
            this.ch_ = -1;
            throw new ParseException(this, "end of XML file", cArr);
        }
        char[] cArr2 = this.cbuf_;
        int i2 = this.endPos_;
        this.ch_ = cArr2[i2 - 1];
        if (i2 - this.curPos_ < length) {
            throw new ParseException(this, "end of XML file", cArr);
        }
        for (int i3 = 0; i3 < length; i3++) {
            if (this.cbuf_[this.curPos_ + i3] != cArr[i3]) {
                throw new ParseException(this, new String(this.cbuf_, this.curPos_, length), cArr);
            }
        }
        this.curPos_ += length;
    }

    private final void readSystemLiteral() throws ParseException, IOException {
        char c3 = readChar();
        while (peekChar() != c3) {
            readChar();
        }
        readChar(c3);
    }

    private void readVersionInfo() throws ParseException, IOException {
        readS();
        readSymbol(VERSION);
        readEq();
        char c3 = readChar(CharPool.SINGLE_QUOTE, '\"');
        readVersionNum();
        readChar(c3);
    }

    private void readVersionNum() throws ParseException, IOException {
        do {
            readChar();
        } while (isVersionNumChar());
    }

    private void readXmlDecl() throws ParseException, IOException {
        readSymbol(XML_BEGIN);
        readVersionInfo();
        if (isS()) {
            readS();
        }
        if (isEncodingDecl()) {
            String encodingDecl = readEncodingDecl();
            if (this.encoding_ != null && !encodingDecl.toLowerCase().equals(this.encoding_)) {
                throw new EncodingMismatchException(this.systemId_, encodingDecl, this.encoding_);
            }
        }
        while (true) {
            char[] cArr = QU_END;
            if (isSymbol(cArr)) {
                readSymbol(cArr);
                return;
            }
            readChar();
        }
    }

    public final String getHistory() {
        return "";
    }

    public int getLastCharRead() {
        return this.ch_;
    }

    @Override // com.hp.hpl.sparta.ParseSource
    public int getLineNumber() {
        return this.lineNumber_;
    }

    public ParseLog getLog() {
        return this.log_;
    }

    @Override // com.hp.hpl.sparta.ParseSource
    public String getSystemId() {
        return this.systemId_;
    }

    @Override // com.hp.hpl.sparta.ParseSource
    public String toString() {
        return this.systemId_;
    }
}
