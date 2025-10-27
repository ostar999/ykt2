package com.catchpig.mvvm.utils;

import com.yikaobang.yixue.R2;
import okio.Utf8;

/* loaded from: classes2.dex */
public final class Base64 {
    private static final int BASELENGTH = 128;
    private static final int EIGHTBIT = 8;
    private static final int FOURBYTE = 4;
    private static final int LOOKUPLENGTH = 64;
    private static char PAD = '=';
    private static final int SIGN = -128;
    private static final int SIXTEENBIT = 16;
    private static final int TWENTYFOURBITGROUP = 24;
    private static byte[] base64Alphabet = new byte[128];
    private static char[] lookUpBase64Alphabet = new char[64];

    static {
        int i2;
        int i3;
        int i4 = 0;
        for (int i5 = 0; i5 < 128; i5++) {
            base64Alphabet[i5] = -1;
        }
        for (int i6 = 90; i6 >= 65; i6--) {
            base64Alphabet[i6] = (byte) (i6 - 65);
        }
        int i7 = 122;
        while (true) {
            i2 = 26;
            if (i7 < 97) {
                break;
            }
            base64Alphabet[i7] = (byte) ((i7 - 97) + 26);
            i7--;
        }
        int i8 = 57;
        while (true) {
            i3 = 52;
            if (i8 < 48) {
                break;
            }
            base64Alphabet[i8] = (byte) ((i8 - 48) + 52);
            i8--;
        }
        byte[] bArr = base64Alphabet;
        bArr[43] = 62;
        bArr[47] = Utf8.REPLACEMENT_BYTE;
        for (int i9 = 0; i9 <= 25; i9++) {
            lookUpBase64Alphabet[i9] = (char) (i9 + 65);
        }
        int i10 = 0;
        while (i2 <= 51) {
            lookUpBase64Alphabet[i2] = (char) (i10 + 97);
            i2++;
            i10++;
        }
        while (i3 <= 61) {
            lookUpBase64Alphabet[i3] = (char) (i4 + 48);
            i3++;
            i4++;
        }
        char[] cArr = lookUpBase64Alphabet;
        cArr[62] = '+';
        cArr[63] = '/';
    }

    public static byte[] decode(String str) {
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        int iRemoveWhiteSpace = removeWhiteSpace(charArray);
        if (iRemoveWhiteSpace % 4 != 0) {
            return null;
        }
        int i2 = iRemoveWhiteSpace / 4;
        if (i2 == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i2 * 3];
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i3 < i2 - 1) {
            int i6 = i4 + 1;
            char c3 = charArray[i4];
            if (isData(c3)) {
                int i7 = i6 + 1;
                char c4 = charArray[i6];
                if (isData(c4)) {
                    int i8 = i7 + 1;
                    char c5 = charArray[i7];
                    if (isData(c5)) {
                        int i9 = i8 + 1;
                        char c6 = charArray[i8];
                        if (isData(c6)) {
                            byte[] bArr2 = base64Alphabet;
                            byte b3 = bArr2[c3];
                            byte b4 = bArr2[c4];
                            byte b5 = bArr2[c5];
                            byte b6 = bArr2[c6];
                            int i10 = i5 + 1;
                            bArr[i5] = (byte) ((b3 << 2) | (b4 >> 4));
                            int i11 = i10 + 1;
                            bArr[i10] = (byte) (((b4 & 15) << 4) | ((b5 >> 2) & 15));
                            i5 = i11 + 1;
                            bArr[i11] = (byte) ((b5 << 6) | b6);
                            i3++;
                            i4 = i9;
                        }
                    }
                }
            }
            return null;
        }
        int i12 = i4 + 1;
        char c7 = charArray[i4];
        if (!isData(c7)) {
            return null;
        }
        int i13 = i12 + 1;
        char c8 = charArray[i12];
        if (!isData(c8)) {
            return null;
        }
        byte[] bArr3 = base64Alphabet;
        byte b7 = bArr3[c7];
        byte b8 = bArr3[c8];
        int i14 = i13 + 1;
        char c9 = charArray[i13];
        char c10 = charArray[i14];
        if (isData(c9) && isData(c10)) {
            byte[] bArr4 = base64Alphabet;
            byte b9 = bArr4[c9];
            byte b10 = bArr4[c10];
            int i15 = i5 + 1;
            bArr[i5] = (byte) ((b7 << 2) | (b8 >> 4));
            bArr[i15] = (byte) (((b8 & 15) << 4) | ((b9 >> 2) & 15));
            bArr[i15 + 1] = (byte) (b10 | (b9 << 6));
            return bArr;
        }
        if (isPad(c9) && isPad(c10)) {
            if ((b8 & 15) != 0) {
                return null;
            }
            int i16 = i3 * 3;
            byte[] bArr5 = new byte[i16 + 1];
            System.arraycopy(bArr, 0, bArr5, 0, i16);
            bArr5[i5] = (byte) ((b7 << 2) | (b8 >> 4));
            return bArr5;
        }
        if (isPad(c9) || !isPad(c10)) {
            return null;
        }
        byte b11 = base64Alphabet[c9];
        if ((b11 & 3) != 0) {
            return null;
        }
        int i17 = i3 * 3;
        byte[] bArr6 = new byte[i17 + 2];
        System.arraycopy(bArr, 0, bArr6, 0, i17);
        bArr6[i5] = (byte) ((b7 << 2) | (b8 >> 4));
        bArr6[i5 + 1] = (byte) (((b11 >> 2) & 15) | ((b8 & 15) << 4));
        return bArr6;
    }

    public static String encode(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length * 8;
        if (length == 0) {
            return "";
        }
        int i2 = length % 24;
        int i3 = length / 24;
        char[] cArr = new char[(i2 != 0 ? i3 + 1 : i3) * 4];
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < i3) {
            int i7 = i5 + 1;
            byte b3 = bArr[i5];
            int i8 = i7 + 1;
            byte b4 = bArr[i7];
            int i9 = i8 + 1;
            byte b5 = bArr[i8];
            byte b6 = (byte) (b4 & 15);
            byte b7 = (byte) (b3 & 3);
            byte b8 = (byte) ((b3 & (-128)) == 0 ? b3 >> 2 : (b3 >> 2) ^ 192);
            byte b9 = (byte) ((b4 & (-128)) == 0 ? b4 >> 4 : (b4 >> 4) ^ 240);
            int i10 = (b5 & (-128)) == 0 ? b5 >> 6 : (b5 >> 6) ^ R2.attr.actionModeShareDrawable;
            int i11 = i6 + 1;
            char[] cArr2 = lookUpBase64Alphabet;
            cArr[i6] = cArr2[b8];
            int i12 = i11 + 1;
            cArr[i11] = cArr2[(b7 << 4) | b9];
            int i13 = i12 + 1;
            cArr[i12] = cArr2[(b6 << 2) | ((byte) i10)];
            cArr[i13] = cArr2[b5 & Utf8.REPLACEMENT_BYTE];
            i4++;
            i6 = i13 + 1;
            i5 = i9;
        }
        if (i2 == 8) {
            byte b10 = bArr[i5];
            byte b11 = (byte) (b10 & 3);
            int i14 = (b10 & (-128)) == 0 ? b10 >> 2 : (b10 >> 2) ^ 192;
            int i15 = i6 + 1;
            char[] cArr3 = lookUpBase64Alphabet;
            cArr[i6] = cArr3[(byte) i14];
            int i16 = i15 + 1;
            cArr[i15] = cArr3[b11 << 4];
            char c3 = PAD;
            cArr[i16] = c3;
            cArr[i16 + 1] = c3;
        } else if (i2 == 16) {
            byte b12 = bArr[i5];
            byte b13 = bArr[i5 + 1];
            byte b14 = (byte) (b13 & 15);
            byte b15 = (byte) (b12 & 3);
            byte b16 = (byte) ((b12 & (-128)) == 0 ? b12 >> 2 : (b12 >> 2) ^ 192);
            int i17 = (b13 & (-128)) == 0 ? b13 >> 4 : (b13 >> 4) ^ 240;
            int i18 = i6 + 1;
            char[] cArr4 = lookUpBase64Alphabet;
            cArr[i6] = cArr4[b16];
            int i19 = i18 + 1;
            cArr[i18] = cArr4[((byte) i17) | (b15 << 4)];
            cArr[i19] = cArr4[b14 << 2];
            cArr[i19 + 1] = PAD;
        }
        return new String(cArr);
    }

    private static boolean isData(char c3) {
        return c3 < 128 && base64Alphabet[c3] != -1;
    }

    private static boolean isPad(char c3) {
        return c3 == PAD;
    }

    private static boolean isWhiteSpace(char c3) {
        return c3 == ' ' || c3 == '\r' || c3 == '\n' || c3 == '\t';
    }

    private static int removeWhiteSpace(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        int length = cArr.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (!isWhiteSpace(cArr[i3])) {
                cArr[i2] = cArr[i3];
                i2++;
            }
        }
        return i2;
    }
}
