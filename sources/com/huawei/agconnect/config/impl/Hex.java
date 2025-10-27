package com.huawei.agconnect.config.impl;

import net.lingala.zip4j.crypto.PBKDF2.BinTools;

/* loaded from: classes4.dex */
public class Hex {
    private static final char[] HEX_CODE = BinTools.hex.toCharArray();

    private static byte[] decodeHex(char[] cArr) {
        if ((cArr.length & 1) != 0) {
            throw new IllegalArgumentException("Odd number of characters.");
        }
        byte[] bArr = new byte[cArr.length >> 1];
        int i2 = 0;
        int i3 = 0;
        while (i2 < cArr.length) {
            int iDigit = Character.digit(cArr[i2], 16);
            if (iDigit == -1) {
                throw new IllegalArgumentException("Illegal hexadecimal character at index " + i2);
            }
            int i4 = i2 + 1;
            int iDigit2 = Character.digit(cArr[i4], 16);
            if (iDigit2 == -1) {
                throw new IllegalArgumentException("Illegal hexadecimal character at index " + i4);
            }
            i2 = i4 + 1;
            bArr[i3] = (byte) (((iDigit << 4) | iDigit2) & 255);
            i3++;
        }
        return bArr;
    }

    public static byte[] decodeHexString(String str) {
        return decodeHex(str.toCharArray());
    }

    public static String encodeHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (byte b3 : bArr) {
            char[] cArr = HEX_CODE;
            sb.append(cArr[(b3 >> 4) & 15]);
            sb.append(cArr[b3 & 15]);
        }
        return sb.toString();
    }
}
