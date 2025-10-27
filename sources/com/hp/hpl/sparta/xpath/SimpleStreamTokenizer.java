package com.hp.hpl.sparta.xpath;

import java.io.IOException;
import java.io.Reader;

/* loaded from: classes4.dex */
public class SimpleStreamTokenizer {
    private static final int QUOTE = -6;
    public static final int TT_EOF = -1;
    public static final int TT_NUMBER = -2;
    public static final int TT_WORD = -3;
    private static final int WHITESPACE = -5;
    private int nextType_;
    private final Reader reader_;
    public int ttype = Integer.MIN_VALUE;
    public int nval = Integer.MIN_VALUE;
    public String sval = "";
    private final StringBuffer buf_ = new StringBuffer();
    private final int[] charType_ = new int[256];
    private boolean pushedBack_ = false;
    private char inQuote_ = 0;

    public SimpleStreamTokenizer(Reader reader) throws IOException {
        char c3 = 0;
        this.reader_ = reader;
        while (true) {
            int[] iArr = this.charType_;
            if (c3 >= iArr.length) {
                nextToken();
                return;
            }
            if (('A' <= c3 && c3 <= 'Z') || (('a' <= c3 && c3 <= 'z') || c3 == '-')) {
                iArr[c3] = -3;
            } else if ('0' <= c3 && c3 <= '9') {
                iArr[c3] = -2;
            } else if (c3 < 0 || c3 > ' ') {
                iArr[c3] = c3;
            } else {
                iArr[c3] = -5;
            }
            c3 = (char) (c3 + 1);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x00a3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int nextToken() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 191
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hp.hpl.sparta.xpath.SimpleStreamTokenizer.nextToken():int");
    }

    public void ordinaryChar(char c3) {
        this.charType_[c3] = c3;
    }

    public void pushBack() {
        this.pushedBack_ = true;
    }

    public String toString() {
        int i2 = this.ttype;
        if (i2 != -3) {
            if (i2 == -2) {
                return Integer.toString(this.nval);
            }
            if (i2 == -1) {
                return "(EOF)";
            }
            if (i2 != 34) {
                if (i2 != 39) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("'");
                    stringBuffer.append((char) this.ttype);
                    stringBuffer.append("'");
                    return stringBuffer.toString();
                }
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("'");
                stringBuffer2.append(this.sval);
                stringBuffer2.append("'");
                return stringBuffer2.toString();
            }
        }
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("\"");
        stringBuffer3.append(this.sval);
        stringBuffer3.append("\"");
        return stringBuffer3.toString();
    }

    public void wordChars(char c3, char c4) {
        while (c3 <= c4) {
            this.charType_[c3] = -3;
            c3 = (char) (c3 + 1);
        }
    }
}
