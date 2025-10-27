package com.hp.hpl.sparta;

/* loaded from: classes4.dex */
public class ParseException extends Exception {
    private Throwable cause_;
    private int lineNumber_;

    public ParseException(ParseCharStream parseCharStream, char c3, char c4) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("got '");
        stringBuffer.append(c3);
        stringBuffer.append("' instead of expected '");
        stringBuffer.append(c4);
        stringBuffer.append("'");
        this(parseCharStream, stringBuffer.toString());
    }

    public ParseException(ParseCharStream parseCharStream, char c3, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("got '");
        stringBuffer.append(c3);
        stringBuffer.append("' instead of ");
        stringBuffer.append(str);
        stringBuffer.append(" as expected");
        this(parseCharStream, stringBuffer.toString());
    }

    public ParseException(ParseCharStream parseCharStream, char c3, char[] cArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("got '");
        stringBuffer.append(c3);
        stringBuffer.append("' instead of ");
        stringBuffer.append(toString(cArr));
        this(parseCharStream, stringBuffer.toString());
    }

    public ParseException(ParseCharStream parseCharStream, String str) {
        this(parseCharStream.getLog(), parseCharStream.getSystemId(), parseCharStream.getLineNumber(), parseCharStream.getLastCharRead(), parseCharStream.getHistory(), str);
    }

    public ParseException(ParseCharStream parseCharStream, String str, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("got \"");
        stringBuffer.append(str);
        stringBuffer.append("\" instead of \"");
        stringBuffer.append(str2);
        stringBuffer.append("\" as expected");
        this(parseCharStream, stringBuffer.toString());
    }

    public ParseException(ParseCharStream parseCharStream, String str, char[] cArr) {
        this(parseCharStream, str, new String(cArr));
    }

    public ParseException(ParseLog parseLog, String str, int i2, int i3, String str2, String str3) {
        this(str, i2, i3, str2, str3);
        parseLog.error(str3, str, i2);
    }

    public ParseException(String str) {
        super(str);
        this.lineNumber_ = -1;
        this.cause_ = null;
    }

    public ParseException(String str, int i2, int i3, String str2, String str3) {
        super(toMessage(str, i2, i3, str2, str3));
        this.cause_ = null;
        this.lineNumber_ = i2;
    }

    public ParseException(String str, Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(" ");
        stringBuffer.append(th);
        super(stringBuffer.toString());
        this.lineNumber_ = -1;
        this.cause_ = th;
    }

    public static String charRepr(int i2) {
        if (i2 == -1) {
            return "EOF";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        stringBuffer.append((char) i2);
        return stringBuffer.toString();
    }

    private static String toMessage(String str, int i2, int i3, String str2, String str3) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append("(");
        stringBuffer.append(i2);
        stringBuffer.append("): \n");
        stringBuffer.append(str2);
        stringBuffer.append("\nLast character read was '");
        stringBuffer.append(charRepr(i3));
        stringBuffer.append("'\n");
        stringBuffer.append(str3);
        return stringBuffer.toString();
    }

    private static String toString(char[] cArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(cArr[0]);
        for (int i2 = 1; i2 < cArr.length; i2++) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("or ");
            stringBuffer2.append(cArr[i2]);
            stringBuffer.append(stringBuffer2.toString());
        }
        return stringBuffer.toString();
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause_;
    }

    public int getLineNumber() {
        return this.lineNumber_;
    }
}
