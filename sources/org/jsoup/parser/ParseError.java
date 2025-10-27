package org.jsoup.parser;

/* loaded from: classes9.dex */
public class ParseError {
    private String errorMsg;
    private int pos;

    public ParseError(int i2, String str) {
        this.pos = i2;
        this.errorMsg = str;
    }

    public String getErrorMessage() {
        return this.errorMsg;
    }

    public int getPosition() {
        return this.pos;
    }

    public String toString() {
        return this.pos + ": " + this.errorMsg;
    }

    public ParseError(int i2, String str, Object... objArr) {
        this.errorMsg = String.format(str, objArr);
        this.pos = i2;
    }
}
