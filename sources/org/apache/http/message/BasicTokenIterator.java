package org.apache.http.message;

import java.util.NoSuchElementException;
import org.apache.http.HeaderIterator;
import org.apache.http.ParseException;
import org.apache.http.TokenIterator;

/* loaded from: classes9.dex */
public class BasicTokenIterator implements TokenIterator {
    public static final String HTTP_SEPARATORS = " ,;=()<>@:\\\"/[]?{}\t";
    protected String currentHeader;
    protected String currentToken;
    protected final HeaderIterator headerIt;
    protected int searchPos;

    public BasicTokenIterator(HeaderIterator headerIterator) {
        if (headerIterator == null) {
            throw new IllegalArgumentException("Header iterator must not be null.");
        }
        this.headerIt = headerIterator;
        this.searchPos = findNext(-1);
    }

    public String createToken(String str, int i2, int i3) {
        return str.substring(i2, i3);
    }

    public int findNext(int i2) throws ParseException {
        int iFindTokenSeparator;
        if (i2 >= 0) {
            iFindTokenSeparator = findTokenSeparator(i2);
        } else {
            if (!this.headerIt.hasNext()) {
                return -1;
            }
            this.currentHeader = this.headerIt.nextHeader().getValue();
            iFindTokenSeparator = 0;
        }
        int iFindTokenStart = findTokenStart(iFindTokenSeparator);
        if (iFindTokenStart < 0) {
            this.currentToken = null;
            return -1;
        }
        int iFindTokenEnd = findTokenEnd(iFindTokenStart);
        this.currentToken = createToken(this.currentHeader, iFindTokenStart, iFindTokenEnd);
        return iFindTokenEnd;
    }

    public int findTokenEnd(int i2) {
        if (i2 < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Token start position must not be negative: ");
            stringBuffer.append(i2);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        int length = this.currentHeader.length();
        do {
            i2++;
            if (i2 >= length) {
                break;
            }
        } while (isTokenChar(this.currentHeader.charAt(i2)));
        return i2;
    }

    public int findTokenSeparator(int i2) {
        if (i2 < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Search position must not be negative: ");
            stringBuffer.append(i2);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        int length = this.currentHeader.length();
        boolean z2 = false;
        while (!z2 && i2 < length) {
            char cCharAt = this.currentHeader.charAt(i2);
            if (isTokenSeparator(cCharAt)) {
                z2 = true;
            } else {
                if (!isWhitespace(cCharAt)) {
                    if (isTokenChar(cCharAt)) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("Tokens without separator (pos ");
                        stringBuffer2.append(i2);
                        stringBuffer2.append("): ");
                        stringBuffer2.append(this.currentHeader);
                        throw new ParseException(stringBuffer2.toString());
                    }
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append("Invalid character after token (pos ");
                    stringBuffer3.append(i2);
                    stringBuffer3.append("): ");
                    stringBuffer3.append(this.currentHeader);
                    throw new ParseException(stringBuffer3.toString());
                }
                i2++;
            }
        }
        return i2;
    }

    public int findTokenStart(int i2) {
        if (i2 < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Search position must not be negative: ");
            stringBuffer.append(i2);
            throw new IllegalArgumentException(stringBuffer.toString());
        }
        boolean z2 = false;
        while (!z2) {
            String str = this.currentHeader;
            if (str == null) {
                break;
            }
            int length = str.length();
            while (!z2 && i2 < length) {
                char cCharAt = this.currentHeader.charAt(i2);
                if (isTokenSeparator(cCharAt) || isWhitespace(cCharAt)) {
                    i2++;
                } else {
                    if (!isTokenChar(this.currentHeader.charAt(i2))) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("Invalid character before token (pos ");
                        stringBuffer2.append(i2);
                        stringBuffer2.append("): ");
                        stringBuffer2.append(this.currentHeader);
                        throw new ParseException(stringBuffer2.toString());
                    }
                    z2 = true;
                }
            }
            if (!z2) {
                if (this.headerIt.hasNext()) {
                    this.currentHeader = this.headerIt.nextHeader().getValue();
                    i2 = 0;
                } else {
                    this.currentHeader = null;
                }
            }
        }
        if (z2) {
            return i2;
        }
        return -1;
    }

    @Override // org.apache.http.TokenIterator, java.util.Iterator
    public boolean hasNext() {
        return this.currentToken != null;
    }

    public boolean isHttpSeparator(char c3) {
        return HTTP_SEPARATORS.indexOf(c3) >= 0;
    }

    public boolean isTokenChar(char c3) {
        if (Character.isLetterOrDigit(c3)) {
            return true;
        }
        return (Character.isISOControl(c3) || isHttpSeparator(c3)) ? false : true;
    }

    public boolean isTokenSeparator(char c3) {
        return c3 == ',';
    }

    public boolean isWhitespace(char c3) {
        return c3 == '\t' || Character.isSpaceChar(c3);
    }

    @Override // java.util.Iterator
    public final Object next() throws ParseException, NoSuchElementException {
        return nextToken();
    }

    @Override // org.apache.http.TokenIterator
    public String nextToken() throws ParseException, NoSuchElementException {
        String str = this.currentToken;
        if (str == null) {
            throw new NoSuchElementException("Iteration already finished.");
        }
        this.searchPos = findNext(this.searchPos);
        return str;
    }

    @Override // java.util.Iterator
    public final void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Removing tokens is not supported.");
    }
}
