package org.apache.http.message;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

/* loaded from: classes9.dex */
public class BasicLineParser implements LineParser {
    public static final BasicLineParser DEFAULT = new BasicLineParser();
    protected final ProtocolVersion protocol;

    public BasicLineParser(ProtocolVersion protocolVersion) {
        this.protocol = protocolVersion == null ? HttpVersion.HTTP_1_1 : protocolVersion;
    }

    public static final Header parseHeader(String str, LineParser lineParser) throws ParseException {
        if (str == null) {
            throw new IllegalArgumentException("Value to parse may not be null");
        }
        if (lineParser == null) {
            lineParser = DEFAULT;
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        return lineParser.parseHeader(charArrayBuffer);
    }

    public static final ProtocolVersion parseProtocolVersion(String str, LineParser lineParser) throws ParseException {
        if (str == null) {
            throw new IllegalArgumentException("Value to parse may not be null.");
        }
        if (lineParser == null) {
            lineParser = DEFAULT;
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        return lineParser.parseProtocolVersion(charArrayBuffer, new ParserCursor(0, str.length()));
    }

    public static final RequestLine parseRequestLine(String str, LineParser lineParser) throws ParseException {
        if (str == null) {
            throw new IllegalArgumentException("Value to parse may not be null.");
        }
        if (lineParser == null) {
            lineParser = DEFAULT;
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        return lineParser.parseRequestLine(charArrayBuffer, new ParserCursor(0, str.length()));
    }

    public static final StatusLine parseStatusLine(String str, LineParser lineParser) throws ParseException {
        if (str == null) {
            throw new IllegalArgumentException("Value to parse may not be null.");
        }
        if (lineParser == null) {
            lineParser = DEFAULT;
        }
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(str.length());
        charArrayBuffer.append(str);
        return lineParser.parseStatusLine(charArrayBuffer, new ParserCursor(0, str.length()));
    }

    public ProtocolVersion createProtocolVersion(int i2, int i3) {
        return this.protocol.forVersion(i2, i3);
    }

    public RequestLine createRequestLine(String str, String str2, ProtocolVersion protocolVersion) {
        return new BasicRequestLine(str, str2, protocolVersion);
    }

    public StatusLine createStatusLine(ProtocolVersion protocolVersion, int i2, String str) {
        return new BasicStatusLine(protocolVersion, i2, str);
    }

    @Override // org.apache.http.message.LineParser
    public boolean hasProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        if (charArrayBuffer == null) {
            throw new IllegalArgumentException("Char array buffer may not be null");
        }
        if (parserCursor == null) {
            throw new IllegalArgumentException("Parser cursor may not be null");
        }
        int pos = parserCursor.getPos();
        String protocol = this.protocol.getProtocol();
        int length = protocol.length();
        if (charArrayBuffer.length() < length + 4) {
            return false;
        }
        if (pos < 0) {
            pos = (charArrayBuffer.length() - 4) - length;
        } else if (pos == 0) {
            while (pos < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(pos))) {
                pos++;
            }
        }
        int i2 = pos + length;
        if (i2 + 4 > charArrayBuffer.length()) {
            return false;
        }
        boolean z2 = true;
        for (int i3 = 0; z2 && i3 < length; i3++) {
            z2 = charArrayBuffer.charAt(pos + i3) == protocol.charAt(i3);
        }
        if (z2) {
            return charArrayBuffer.charAt(i2) == '/';
        }
        return z2;
    }

    public void skipWhitespace(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) {
        int pos = parserCursor.getPos();
        int upperBound = parserCursor.getUpperBound();
        while (pos < upperBound && HTTP.isWhitespace(charArrayBuffer.charAt(pos))) {
            pos++;
        }
        parserCursor.updatePos(pos);
    }

    public BasicLineParser() {
        this(null);
    }

    @Override // org.apache.http.message.LineParser
    public Header parseHeader(CharArrayBuffer charArrayBuffer) throws ParseException {
        return new BufferedHeader(charArrayBuffer);
    }

    @Override // org.apache.http.message.LineParser
    public ProtocolVersion parseProtocolVersion(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException, NumberFormatException {
        if (charArrayBuffer == null) {
            throw new IllegalArgumentException("Char array buffer may not be null");
        }
        if (parserCursor != null) {
            String protocol = this.protocol.getProtocol();
            int length = protocol.length();
            int pos = parserCursor.getPos();
            int upperBound = parserCursor.getUpperBound();
            skipWhitespace(charArrayBuffer, parserCursor);
            int pos2 = parserCursor.getPos();
            int i2 = pos2 + length;
            if (i2 + 4 > upperBound) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Not a valid protocol version: ");
                stringBuffer.append(charArrayBuffer.substring(pos, upperBound));
                throw new ParseException(stringBuffer.toString());
            }
            boolean z2 = true;
            for (int i3 = 0; z2 && i3 < length; i3++) {
                z2 = charArrayBuffer.charAt(pos2 + i3) == protocol.charAt(i3);
            }
            if (z2) {
                z2 = charArrayBuffer.charAt(i2) == '/';
            }
            if (z2) {
                int i4 = pos2 + length + 1;
                int iIndexOf = charArrayBuffer.indexOf(46, i4, upperBound);
                if (iIndexOf != -1) {
                    try {
                        int i5 = Integer.parseInt(charArrayBuffer.substringTrimmed(i4, iIndexOf));
                        int i6 = iIndexOf + 1;
                        int iIndexOf2 = charArrayBuffer.indexOf(32, i6, upperBound);
                        if (iIndexOf2 == -1) {
                            iIndexOf2 = upperBound;
                        }
                        try {
                            int i7 = Integer.parseInt(charArrayBuffer.substringTrimmed(i6, iIndexOf2));
                            parserCursor.updatePos(iIndexOf2);
                            return createProtocolVersion(i5, i7);
                        } catch (NumberFormatException unused) {
                            StringBuffer stringBuffer2 = new StringBuffer();
                            stringBuffer2.append("Invalid protocol minor version number: ");
                            stringBuffer2.append(charArrayBuffer.substring(pos, upperBound));
                            throw new ParseException(stringBuffer2.toString());
                        }
                    } catch (NumberFormatException unused2) {
                        StringBuffer stringBuffer3 = new StringBuffer();
                        stringBuffer3.append("Invalid protocol major version number: ");
                        stringBuffer3.append(charArrayBuffer.substring(pos, upperBound));
                        throw new ParseException(stringBuffer3.toString());
                    }
                }
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("Invalid protocol version number: ");
                stringBuffer4.append(charArrayBuffer.substring(pos, upperBound));
                throw new ParseException(stringBuffer4.toString());
            }
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Not a valid protocol version: ");
            stringBuffer5.append(charArrayBuffer.substring(pos, upperBound));
            throw new ParseException(stringBuffer5.toString());
        }
        throw new IllegalArgumentException("Parser cursor may not be null");
    }

    @Override // org.apache.http.message.LineParser
    public RequestLine parseRequestLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException, NumberFormatException {
        if (charArrayBuffer == null) {
            throw new IllegalArgumentException("Char array buffer may not be null");
        }
        if (parserCursor != null) {
            int pos = parserCursor.getPos();
            int upperBound = parserCursor.getUpperBound();
            try {
                skipWhitespace(charArrayBuffer, parserCursor);
                int pos2 = parserCursor.getPos();
                int iIndexOf = charArrayBuffer.indexOf(32, pos2, upperBound);
                if (iIndexOf >= 0) {
                    String strSubstringTrimmed = charArrayBuffer.substringTrimmed(pos2, iIndexOf);
                    parserCursor.updatePos(iIndexOf);
                    skipWhitespace(charArrayBuffer, parserCursor);
                    int pos3 = parserCursor.getPos();
                    int iIndexOf2 = charArrayBuffer.indexOf(32, pos3, upperBound);
                    if (iIndexOf2 >= 0) {
                        String strSubstringTrimmed2 = charArrayBuffer.substringTrimmed(pos3, iIndexOf2);
                        parserCursor.updatePos(iIndexOf2);
                        ProtocolVersion protocolVersion = parseProtocolVersion(charArrayBuffer, parserCursor);
                        skipWhitespace(charArrayBuffer, parserCursor);
                        if (parserCursor.atEnd()) {
                            return createRequestLine(strSubstringTrimmed, strSubstringTrimmed2, protocolVersion);
                        }
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Invalid request line: ");
                        stringBuffer.append(charArrayBuffer.substring(pos, upperBound));
                        throw new ParseException(stringBuffer.toString());
                    }
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Invalid request line: ");
                    stringBuffer2.append(charArrayBuffer.substring(pos, upperBound));
                    throw new ParseException(stringBuffer2.toString());
                }
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Invalid request line: ");
                stringBuffer3.append(charArrayBuffer.substring(pos, upperBound));
                throw new ParseException(stringBuffer3.toString());
            } catch (IndexOutOfBoundsException unused) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("Invalid request line: ");
                stringBuffer4.append(charArrayBuffer.substring(pos, upperBound));
                throw new ParseException(stringBuffer4.toString());
            }
        }
        throw new IllegalArgumentException("Parser cursor may not be null");
    }

    @Override // org.apache.http.message.LineParser
    public StatusLine parseStatusLine(CharArrayBuffer charArrayBuffer, ParserCursor parserCursor) throws ParseException, NumberFormatException {
        if (charArrayBuffer == null) {
            throw new IllegalArgumentException("Char array buffer may not be null");
        }
        if (parserCursor != null) {
            int pos = parserCursor.getPos();
            int upperBound = parserCursor.getUpperBound();
            try {
                ProtocolVersion protocolVersion = parseProtocolVersion(charArrayBuffer, parserCursor);
                skipWhitespace(charArrayBuffer, parserCursor);
                int pos2 = parserCursor.getPos();
                int iIndexOf = charArrayBuffer.indexOf(32, pos2, upperBound);
                if (iIndexOf < 0) {
                    iIndexOf = upperBound;
                }
                String strSubstringTrimmed = charArrayBuffer.substringTrimmed(pos2, iIndexOf);
                for (int i2 = 0; i2 < strSubstringTrimmed.length(); i2++) {
                    if (!Character.isDigit(strSubstringTrimmed.charAt(i2))) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Status line contains invalid status code: ");
                        stringBuffer.append(charArrayBuffer.substring(pos, upperBound));
                        throw new ParseException(stringBuffer.toString());
                    }
                }
                try {
                    return createStatusLine(protocolVersion, Integer.parseInt(strSubstringTrimmed), iIndexOf < upperBound ? charArrayBuffer.substringTrimmed(iIndexOf, upperBound) : "");
                } catch (NumberFormatException unused) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Status line contains invalid status code: ");
                    stringBuffer2.append(charArrayBuffer.substring(pos, upperBound));
                    throw new ParseException(stringBuffer2.toString());
                }
            } catch (IndexOutOfBoundsException unused2) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Invalid status line: ");
                stringBuffer3.append(charArrayBuffer.substring(pos, upperBound));
                throw new ParseException(stringBuffer3.toString());
            }
        }
        throw new IllegalArgumentException("Parser cursor may not be null");
    }
}
