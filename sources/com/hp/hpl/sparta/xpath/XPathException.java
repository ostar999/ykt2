package com.hp.hpl.sparta.xpath;

import java.io.IOException;

/* loaded from: classes4.dex */
public class XPathException extends Exception {
    private Throwable cause_;

    public XPathException(XPath xPath, Exception exc) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(xPath);
        stringBuffer.append(" ");
        stringBuffer.append(exc);
        super(stringBuffer.toString());
        this.cause_ = exc;
    }

    public XPathException(XPath xPath, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(xPath);
        stringBuffer.append(" ");
        stringBuffer.append(str);
        super(stringBuffer.toString());
        this.cause_ = null;
    }

    public XPathException(XPath xPath, String str, SimpleStreamTokenizer simpleStreamTokenizer, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(" got \"");
        stringBuffer.append(toString(simpleStreamTokenizer));
        stringBuffer.append("\" instead of expected ");
        stringBuffer.append(str2);
        this(xPath, stringBuffer.toString());
    }

    private static String toString(SimpleStreamTokenizer simpleStreamTokenizer) {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(tokenToString(simpleStreamTokenizer));
            if (simpleStreamTokenizer.ttype != -1) {
                simpleStreamTokenizer.nextToken();
                stringBuffer.append(tokenToString(simpleStreamTokenizer));
                simpleStreamTokenizer.pushBack();
            }
            return stringBuffer.toString();
        } catch (IOException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("(cannot get  info: ");
            stringBuffer2.append(e2);
            stringBuffer2.append(")");
            return stringBuffer2.toString();
        }
    }

    private static String tokenToString(SimpleStreamTokenizer simpleStreamTokenizer) {
        StringBuffer stringBuffer;
        int i2 = simpleStreamTokenizer.ttype;
        if (i2 == -3) {
            return simpleStreamTokenizer.sval;
        }
        if (i2 == -2) {
            stringBuffer = new StringBuffer();
            stringBuffer.append(simpleStreamTokenizer.nval);
        } else {
            if (i2 == -1) {
                return "<end of expression>";
            }
            stringBuffer = new StringBuffer();
            stringBuffer.append((char) simpleStreamTokenizer.ttype);
        }
        stringBuffer.append("");
        return stringBuffer.toString();
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause_;
    }
}
