package cn.hutool.core.codec;

import cn.hutool.core.lang.Assert;

/* loaded from: classes.dex */
public class Rot {
    private static final char ACHAR = 'A';
    private static final char CHAR0 = '0';
    private static final char CHAR9 = '9';
    private static final char ZCHAR = 'Z';
    private static final char aCHAR = 'a';
    private static final char zCHAR = 'z';

    public static String decode(String str, int i2, boolean z2) throws IllegalArgumentException {
        Assert.notNull(str, "rot must not be null", new Object[0]);
        int length = str.length();
        char[] cArr = new char[length];
        for (int i3 = 0; i3 < length; i3++) {
            cArr[i3] = decodeChar(str.charAt(i3), i2, z2);
        }
        return new String(cArr);
    }

    public static String decode13(String str) {
        return decode13(str, true);
    }

    private static char decodeChar(char c3, int i2, boolean z2) {
        int i3;
        int i4;
        int i5 = c3;
        if (z2) {
            i5 = c3;
            if (c3 >= '0') {
                i5 = c3;
                if (c3 <= '9') {
                    int i6 = (c3 - 48) - i2;
                    while (i6 < 0) {
                        i6 += 10;
                    }
                    i5 = i6 + 48;
                }
            }
        }
        int i7 = 65;
        if (i5 < 65 || i5 > 90) {
            i7 = 97;
            i4 = i5;
            if (i5 >= 97) {
                i4 = i5;
                if (i5 <= 122) {
                    i3 = (i5 - 97) - i2;
                    if (i3 < 0) {
                        i3 += 26;
                    }
                }
            }
            return (char) i4;
        }
        i3 = (i5 - 65) - i2;
        while (i3 < 0) {
            i3 += 26;
        }
        i4 = i3 + i7;
        return (char) i4;
    }

    public static String encode(String str, int i2, boolean z2) throws IllegalArgumentException {
        Assert.notNull(str, "message must not be null", new Object[0]);
        int length = str.length();
        char[] cArr = new char[length];
        for (int i3 = 0; i3 < length; i3++) {
            cArr[i3] = encodeChar(str.charAt(i3), i2, z2);
        }
        return new String(cArr);
    }

    public static String encode13(String str) {
        return encode13(str, true);
    }

    private static char encodeChar(char c3, int i2, boolean z2) {
        int i3;
        if (z2 && c3 >= '0' && c3 <= '9') {
            c3 = (char) (((char) ((((char) (c3 - '0')) + i2) % 10)) + CHAR0);
        }
        char c4 = ACHAR;
        if (c3 < 'A' || c3 > 'Z') {
            c4 = aCHAR;
            if (c3 < 'a' || c3 > 'z') {
                return c3;
            }
            i3 = (((char) (c3 - 'a')) + i2) % 26;
        } else {
            i3 = (((char) (c3 - 'A')) + i2) % 26;
        }
        return (char) (((char) i3) + c4);
    }

    public static String decode13(String str, boolean z2) {
        return decode(str, 13, z2);
    }

    public static String encode13(String str, boolean z2) {
        return encode(str, 13, z2);
    }
}
