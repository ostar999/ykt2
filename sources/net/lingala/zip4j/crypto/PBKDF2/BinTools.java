package net.lingala.zip4j.crypto.PBKDF2;

/* loaded from: classes9.dex */
class BinTools {
    public static final String hex = "0123456789ABCDEF";

    public static String bin2hex(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b3 : bArr) {
            int i2 = (b3 + 256) % 256;
            stringBuffer.append(hex.charAt((i2 / 16) & 15));
            stringBuffer.append(hex.charAt((i2 % 16) & 15));
        }
        return stringBuffer.toString();
    }

    public static byte[] hex2bin(String str) {
        if (str == null) {
            str = "";
        } else if (str.length() % 2 != 0) {
            str = "0" + str;
        }
        byte[] bArr = new byte[str.length() / 2];
        int i2 = 0;
        int i3 = 0;
        while (i2 < str.length()) {
            int i4 = i2 + 1;
            bArr[i3] = (byte) ((hex2bin(str.charAt(i2)) * 16) + hex2bin(str.charAt(i4)));
            i3++;
            i2 = i4 + 1;
        }
        return bArr;
    }

    public static int hex2bin(char c3) {
        if (c3 >= '0' && c3 <= '9') {
            return c3 - '0';
        }
        char c4 = 'A';
        if (c3 < 'A' || c3 > 'F') {
            c4 = 'a';
            if (c3 < 'a' || c3 > 'f') {
                throw new IllegalArgumentException("Input string may only contain hex digits, but found '" + c3 + "'");
            }
        }
        return (c3 - c4) + 10;
    }
}
