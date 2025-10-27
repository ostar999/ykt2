package cn.hutool.core.codec;

import cn.hutool.core.lang.Assert;

/* loaded from: classes.dex */
public class Caesar {
    public static final String TABLE = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";

    public static String decode(String str, int i2) throws IllegalArgumentException {
        Assert.notNull(str, "cipherText must be not null!", new Object[0]);
        int length = str.length();
        char[] charArray = str.toCharArray();
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (Character.isLetter(cCharAt)) {
                charArray[i3] = decodeChar(cCharAt, i2);
            }
        }
        return new String(charArray);
    }

    private static char decodeChar(char c3, int i2) {
        int iIndexOf = (TABLE.indexOf(c3) - i2) % 52;
        if (iIndexOf < 0) {
            iIndexOf += 52;
        }
        return TABLE.charAt(iIndexOf);
    }

    public static String encode(String str, int i2) throws IllegalArgumentException {
        Assert.notNull(str, "message must be not null!", new Object[0]);
        int length = str.length();
        char[] charArray = str.toCharArray();
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (Character.isLetter(cCharAt)) {
                charArray[i3] = encodeChar(cCharAt, i2);
            }
        }
        return new String(charArray);
    }

    private static char encodeChar(char c3, int i2) {
        return TABLE.charAt((TABLE.indexOf(c3) + i2) % 52);
    }
}
