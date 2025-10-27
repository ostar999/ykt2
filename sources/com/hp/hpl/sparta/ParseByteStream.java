package com.hp.hpl.sparta;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
class ParseByteStream implements ParseSource {
    private ParseCharStream parseSource_;

    public ParseByteStream(String str, InputStream inputStream, ParseLog parseLog, String str2, ParseHandler parseHandler) throws ParseException, IOException {
        ParseLog parseLog2 = parseLog == null ? ParseSource.DEFAULT_LOG : parseLog;
        if (!inputStream.markSupported()) {
            throw new Error("Precondition violation: the InputStream passed to ParseByteStream must support mark");
        }
        inputStream.mark(ParseSource.MAXLOOKAHEAD);
        byte[] bArr = new byte[4];
        String strGuessEncoding = str2 == null ? guessEncoding(str, bArr, inputStream.read(bArr), parseLog2) : str2;
        try {
            inputStream.reset();
            try {
                this.parseSource_ = new ParseCharStream(str, new InputStreamReader(inputStream, fixEncoding(strGuessEncoding)), parseLog2, strGuessEncoding, parseHandler);
            } catch (IOException unused) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Problem reading with assumed encoding of ");
                stringBuffer.append(strGuessEncoding);
                stringBuffer.append(" so restarting with ");
                stringBuffer.append("euc-jp");
                parseLog2.note(stringBuffer.toString(), str, 1);
                inputStream.reset();
                try {
                    this.parseSource_ = new ParseCharStream(str, new InputStreamReader(inputStream, fixEncoding("euc-jp")), parseLog2, (String) null, parseHandler);
                } catch (UnsupportedEncodingException unused2) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("\"");
                    stringBuffer2.append("euc-jp");
                    stringBuffer2.append("\" is not a supported encoding");
                    throw new ParseException(parseLog2, str, 1, 0, "euc-jp", stringBuffer2.toString());
                }
            }
        } catch (EncodingMismatchException e2) {
            String declaredEncoding = e2.getDeclaredEncoding();
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Encoding declaration of ");
            stringBuffer3.append(declaredEncoding);
            stringBuffer3.append(" is different that assumed ");
            stringBuffer3.append(strGuessEncoding);
            stringBuffer3.append(" so restarting the parsing with the new encoding");
            parseLog2.note(stringBuffer3.toString(), str, 1);
            inputStream.reset();
            try {
                this.parseSource_ = new ParseCharStream(str, new InputStreamReader(inputStream, fixEncoding(declaredEncoding)), parseLog2, (String) null, parseHandler);
            } catch (UnsupportedEncodingException unused3) {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("\"");
                stringBuffer4.append(declaredEncoding);
                stringBuffer4.append("\" is not a supported encoding");
                throw new ParseException(parseLog2, str, 1, 0, declaredEncoding, stringBuffer4.toString());
            }
        }
    }

    private static boolean equals(byte[] bArr, int i2) {
        return bArr[0] == ((byte) (i2 >>> 24)) && bArr[1] == ((byte) ((i2 >>> 16) & 255)) && bArr[2] == ((byte) ((i2 >>> 8) & 255)) && bArr[3] == ((byte) (i2 & 255));
    }

    private static boolean equals(byte[] bArr, short s2) {
        return bArr[0] == ((byte) (s2 >>> 8)) && bArr[1] == ((byte) (s2 & 255));
    }

    private static String fixEncoding(String str) {
        return str.toLowerCase().equals("utf8") ? "UTF-8" : str;
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String guessEncoding(java.lang.String r5, byte[] r6, int r7, com.hp.hpl.sparta.ParseLog r8) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hp.hpl.sparta.ParseByteStream.guessEncoding(java.lang.String, byte[], int, com.hp.hpl.sparta.ParseLog):java.lang.String");
    }

    private static String hex(byte b3) {
        String hexString = Integer.toHexString(b3);
        int length = hexString.length();
        if (length != 1) {
            return length != 2 ? hexString.substring(hexString.length() - 2) : hexString;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("0");
        stringBuffer.append(hexString);
        return stringBuffer.toString();
    }

    @Override // com.hp.hpl.sparta.ParseSource
    public int getLineNumber() {
        return this.parseSource_.getLineNumber();
    }

    @Override // com.hp.hpl.sparta.ParseSource
    public String getSystemId() {
        return this.parseSource_.getSystemId();
    }

    @Override // com.hp.hpl.sparta.ParseSource
    public String toString() {
        return this.parseSource_.toString();
    }
}
