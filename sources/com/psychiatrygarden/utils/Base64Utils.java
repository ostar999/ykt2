package com.psychiatrygarden.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes6.dex */
public class Base64Utils {
    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    private static int decode(char c3) {
        int i2;
        if (c3 >= 'A' && c3 <= 'Z') {
            return c3 - 'A';
        }
        if (c3 >= 'a' && c3 <= 'z') {
            i2 = c3 - 'a';
        } else {
            if (c3 < '0' || c3 > '9') {
                if (c3 == '+') {
                    return 62;
                }
                if (c3 == '/') {
                    return 63;
                }
                if (c3 == '=') {
                    return 0;
                }
                throw new RuntimeException("unexpected code: " + c3);
            }
            i2 = (c3 - '0') + 26;
        }
        return i2 + 26;
    }

    public static String encode(byte[] data) {
        int length = data.length;
        StringBuffer stringBuffer = new StringBuffer((data.length * 3) / 2);
        int i2 = length - 3;
        int i3 = 0;
        int i4 = 0;
        while (i3 <= i2) {
            int i5 = ((data[i3] & 255) << 16) | ((data[i3 + 1] & 255) << 8) | (data[i3 + 2] & 255);
            char[] cArr = legalChars;
            stringBuffer.append(cArr[(i5 >> 18) & 63]);
            stringBuffer.append(cArr[(i5 >> 12) & 63]);
            stringBuffer.append(cArr[(i5 >> 6) & 63]);
            stringBuffer.append(cArr[i5 & 63]);
            i3 += 3;
            int i6 = i4 + 1;
            if (i4 >= 14) {
                stringBuffer.append(" ");
                i4 = 0;
            } else {
                i4 = i6;
            }
        }
        int i7 = 0 + length;
        if (i3 == i7 - 2) {
            int i8 = ((data[i3 + 1] & 255) << 8) | ((data[i3] & 255) << 16);
            char[] cArr2 = legalChars;
            stringBuffer.append(cArr2[(i8 >> 18) & 63]);
            stringBuffer.append(cArr2[(i8 >> 12) & 63]);
            stringBuffer.append(cArr2[(i8 >> 6) & 63]);
            stringBuffer.append("=");
        } else if (i3 == i7 - 1) {
            int i9 = (data[i3] & 255) << 16;
            char[] cArr3 = legalChars;
            stringBuffer.append(cArr3[(i9 >> 18) & 63]);
            stringBuffer.append(cArr3[(i9 >> 12) & 63]);
            stringBuffer.append("==");
        }
        return stringBuffer.toString();
    }

    public static byte[] decode(String s2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            decode(s2, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                System.err.println("Error while decoding BASE64: " + e2.toString());
            }
            return byteArray;
        } catch (IOException unused) {
            throw new RuntimeException();
        }
    }

    private static void decode(String s2, OutputStream os) throws IOException {
        int length = s2.length();
        int i2 = 0;
        while (true) {
            if (i2 < length && s2.charAt(i2) <= ' ') {
                i2++;
            } else {
                if (i2 == length) {
                    return;
                }
                int i3 = i2 + 2;
                int i4 = i2 + 3;
                int iDecode = (decode(s2.charAt(i2)) << 18) + (decode(s2.charAt(i2 + 1)) << 12) + (decode(s2.charAt(i3)) << 6) + decode(s2.charAt(i4));
                os.write((iDecode >> 16) & 255);
                if (s2.charAt(i3) == '=') {
                    return;
                }
                os.write((iDecode >> 8) & 255);
                if (s2.charAt(i4) == '=') {
                    return;
                }
                os.write(iDecode & 255);
                i2 += 4;
            }
        }
    }
}
