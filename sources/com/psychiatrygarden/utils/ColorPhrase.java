package com.psychiatrygarden.utils;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import cn.hutool.core.text.StrPool;
import java.util.Stack;

/* loaded from: classes6.dex */
public class ColorPhrase {
    private static final int EOF = 0;
    private char curChar;
    private int curCharIndex;
    private CharSequence formatted;
    private Token head;
    private int innerColor;
    private int outerColor;
    private final CharSequence pattern;
    private String separator;

    public static class InnerToken extends Token {
        private int color;
        private final String innerText;

        public InnerToken(Token prev, String _inner, int _color) {
            super(prev);
            this.innerText = _inner;
            this.color = _color;
        }

        @Override // com.psychiatrygarden.utils.ColorPhrase.Token
        public void expand(SpannableStringBuilder target) {
            int formattedStart = getFormattedStart();
            int length = this.innerText.length() + formattedStart + 2;
            target.replace(formattedStart, length, (CharSequence) this.innerText);
            target.setSpan(new ForegroundColorSpan(this.color), formattedStart, length - 2, 33);
        }

        @Override // com.psychiatrygarden.utils.ColorPhrase.Token
        public int getFormattedLength() {
            return this.innerText.length();
        }
    }

    public static class LeftSeparatorToken extends Token {
        private char leftSeparetor;

        public LeftSeparatorToken(Token prev, char _leftSeparator) {
            super(prev);
            this.leftSeparetor = _leftSeparator;
        }

        @Override // com.psychiatrygarden.utils.ColorPhrase.Token
        public void expand(SpannableStringBuilder target) {
            int formattedStart = getFormattedStart();
            target.replace(formattedStart, formattedStart + 2, (CharSequence) String.valueOf(this.leftSeparetor));
        }

        @Override // com.psychiatrygarden.utils.ColorPhrase.Token
        public int getFormattedLength() {
            return 1;
        }
    }

    public static class OuterToken extends Token {
        private int color;
        private final int textLength;

        public OuterToken(Token prev, int textLength, int _color) {
            super(prev);
            this.textLength = textLength;
            this.color = _color;
        }

        @Override // com.psychiatrygarden.utils.ColorPhrase.Token
        public void expand(SpannableStringBuilder target) {
            int formattedStart = getFormattedStart();
            target.setSpan(new ForegroundColorSpan(this.color), formattedStart, this.textLength + formattedStart, 33);
        }

        @Override // com.psychiatrygarden.utils.ColorPhrase.Token
        public int getFormattedLength() {
            return this.textLength;
        }
    }

    public static abstract class Token {
        private Token next;
        private final Token prev;

        public Token(Token prev) {
            this.prev = prev;
            if (prev != null) {
                prev.next = this;
            }
        }

        public abstract void expand(SpannableStringBuilder target);

        public abstract int getFormattedLength();

        public final int getFormattedStart() {
            Token token = this.prev;
            if (token == null) {
                return 0;
            }
            return token.getFormattedStart() + this.prev.getFormattedLength();
        }
    }

    private ColorPhrase(CharSequence pattern) {
        this.curChar = pattern.length() > 0 ? pattern.charAt(0) : (char) 0;
        this.pattern = pattern;
        this.formatted = null;
        this.separator = StrPool.EMPTY_JSON;
        this.outerColor = -10066330;
        this.innerColor = -1686198;
    }

    private boolean checkPattern() {
        if (this.pattern == null) {
            return false;
        }
        char leftSeparator = getLeftSeparator();
        char rightSeparator = getRightSeparator();
        Stack stack = new Stack();
        for (int i2 = 0; i2 < this.pattern.length(); i2++) {
            char cCharAt = this.pattern.charAt(i2);
            if (cCharAt == leftSeparator) {
                stack.push(Character.valueOf(cCharAt));
            } else if (cCharAt == rightSeparator && (stack.isEmpty() || ((Character) stack.pop()).charValue() != leftSeparator)) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    private void consume() {
        int i2 = this.curCharIndex + 1;
        this.curCharIndex = i2;
        this.curChar = i2 == this.pattern.length() ? (char) 0 : this.pattern.charAt(this.curCharIndex);
    }

    private void createDoubleLinkWithToken() {
        Token token = null;
        while (true) {
            token = token(token);
            if (token == null) {
                return;
            }
            if (this.head == null) {
                this.head = token;
            }
        }
    }

    @TargetApi(11)
    public static ColorPhrase from(Fragment f2, int patternResourceId) {
        return from(f2.getResources(), patternResourceId);
    }

    private char getLeftSeparator() {
        return this.separator.charAt(0);
    }

    private char getRightSeparator() {
        return this.separator.length() == 2 ? this.separator.charAt(1) : this.separator.charAt(0);
    }

    private InnerToken inner(Token prev) {
        char c3;
        StringBuilder sb = new StringBuilder();
        consume();
        char rightSeparator = getRightSeparator();
        while (true) {
            c3 = this.curChar;
            if (c3 == rightSeparator || c3 == 0) {
                break;
            }
            sb.append(c3);
            consume();
        }
        if (c3 == 0) {
            throw new IllegalArgumentException("Missing closing separator");
        }
        consume();
        if (sb.length() != 0) {
            return new InnerToken(prev, sb.toString(), this.innerColor);
        }
        throw new IllegalStateException("Disallow empty content between separators,for example {}");
    }

    private LeftSeparatorToken leftSeparator(Token prev) {
        consume();
        consume();
        return new LeftSeparatorToken(prev, getLeftSeparator());
    }

    private char lookahead() {
        if (this.curCharIndex < this.pattern.length() - 1) {
            return this.pattern.charAt(this.curCharIndex + 1);
        }
        return (char) 0;
    }

    private OuterToken outer(Token prev) {
        int i2 = this.curCharIndex;
        while (this.curChar != getLeftSeparator() && this.curChar != 0) {
            consume();
        }
        return new OuterToken(prev, this.curCharIndex - i2, this.outerColor);
    }

    private Token token(Token prev) {
        char c3 = this.curChar;
        if (c3 == 0) {
            return null;
        }
        return c3 == getLeftSeparator() ? lookahead() == getLeftSeparator() ? leftSeparator(prev) : inner(prev) : outer(prev);
    }

    public CharSequence format() {
        if (this.formatted == null) {
            if (!checkPattern()) {
                throw new IllegalStateException("the separators don't match in the pattern!");
            }
            createDoubleLinkWithToken();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.pattern);
            for (Token token = this.head; token != null; token = token.next) {
                token.expand(spannableStringBuilder);
            }
            this.formatted = spannableStringBuilder;
        }
        return this.formatted;
    }

    public ColorPhrase innerColor(int _innerColor) {
        this.innerColor = _innerColor;
        return this;
    }

    public ColorPhrase outerColor(int _outerColor) {
        this.outerColor = _outerColor;
        return this;
    }

    public String toString() {
        return this.pattern.toString();
    }

    public ColorPhrase withSeparator(String _separator) {
        if (TextUtils.isEmpty(_separator)) {
            throw new IllegalArgumentException("separator must not be empty!");
        }
        if (_separator.length() > 2) {
            throw new IllegalArgumentException("separator‘s length must not be more than 3 charactors!");
        }
        this.separator = _separator;
        return this;
    }

    public static ColorPhrase from(View v2, int patternResourceId) {
        return from(v2.getResources(), patternResourceId);
    }

    public static ColorPhrase from(Context c3, int patternResourceId) {
        return from(c3.getResources(), patternResourceId);
    }

    public static ColorPhrase from(Resources r2, int patternResourceId) {
        return from(r2.getText(patternResourceId));
    }

    public static ColorPhrase from(CharSequence pattern) {
        return new ColorPhrase(pattern);
    }
}
