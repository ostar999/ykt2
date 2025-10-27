package com.alipay.sdk.encrypt;

import com.yikaobang.yixue.R2;
import okio.Utf8;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private static final int f3263a = 128;

    /* renamed from: b, reason: collision with root package name */
    private static final int f3264b = 64;

    /* renamed from: c, reason: collision with root package name */
    private static final int f3265c = 24;

    /* renamed from: d, reason: collision with root package name */
    private static final int f3266d = 8;

    /* renamed from: e, reason: collision with root package name */
    private static final int f3267e = 16;

    /* renamed from: f, reason: collision with root package name */
    private static final int f3268f = 4;

    /* renamed from: g, reason: collision with root package name */
    private static final int f3269g = -128;

    /* renamed from: h, reason: collision with root package name */
    private static final char f3270h = '=';

    /* renamed from: i, reason: collision with root package name */
    private static final byte[] f3271i = new byte[128];

    /* renamed from: j, reason: collision with root package name */
    private static final char[] f3272j = new char[64];

    static {
        int i2;
        int i3;
        int i4 = 0;
        for (int i5 = 0; i5 < 128; i5++) {
            f3271i[i5] = -1;
        }
        for (int i6 = 90; i6 >= 65; i6--) {
            f3271i[i6] = (byte) (i6 - 65);
        }
        int i7 = 122;
        while (true) {
            i2 = 26;
            if (i7 < 97) {
                break;
            }
            f3271i[i7] = (byte) ((i7 - 97) + 26);
            i7--;
        }
        int i8 = 57;
        while (true) {
            i3 = 52;
            if (i8 < 48) {
                break;
            }
            f3271i[i8] = (byte) ((i8 - 48) + 52);
            i8--;
        }
        byte[] bArr = f3271i;
        bArr[43] = 62;
        bArr[47] = Utf8.REPLACEMENT_BYTE;
        for (int i9 = 0; i9 <= 25; i9++) {
            f3272j[i9] = (char) (i9 + 65);
        }
        int i10 = 0;
        while (i2 <= 51) {
            f3272j[i2] = (char) (i10 + 97);
            i2++;
            i10++;
        }
        while (i3 <= 61) {
            f3272j[i3] = (char) (i4 + 48);
            i3++;
            i4++;
        }
        char[] cArr = f3272j;
        cArr[62] = '+';
        cArr[63] = '/';
    }

    public static String a(byte[] bArr) {
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
            int i10 = b3 & (-128);
            int i11 = b3 >> 2;
            if (i10 != 0) {
                i11 ^= 192;
            }
            byte b8 = (byte) i11;
            int i12 = b4 & (-128);
            int i13 = b4 >> 4;
            if (i12 != 0) {
                i13 ^= 240;
            }
            byte b9 = (byte) i13;
            int i14 = (b5 & (-128)) == 0 ? b5 >> 6 : (b5 >> 6) ^ R2.attr.actionModeShareDrawable;
            int i15 = i6 + 1;
            char[] cArr2 = f3272j;
            cArr[i6] = cArr2[b8];
            int i16 = i15 + 1;
            cArr[i15] = cArr2[(b7 << 4) | b9];
            int i17 = i16 + 1;
            cArr[i16] = cArr2[(b6 << 2) | ((byte) i14)];
            cArr[i17] = cArr2[b5 & Utf8.REPLACEMENT_BYTE];
            i4++;
            i6 = i17 + 1;
            i5 = i9;
        }
        if (i2 == 8) {
            byte b10 = bArr[i5];
            byte b11 = (byte) (b10 & 3);
            int i18 = b10 & (-128);
            int i19 = b10 >> 2;
            if (i18 != 0) {
                i19 ^= 192;
            }
            int i20 = i6 + 1;
            char[] cArr3 = f3272j;
            cArr[i6] = cArr3[(byte) i19];
            int i21 = i20 + 1;
            cArr[i20] = cArr3[b11 << 4];
            cArr[i21] = f3270h;
            cArr[i21 + 1] = f3270h;
        } else if (i2 == 16) {
            byte b12 = bArr[i5];
            byte b13 = bArr[i5 + 1];
            byte b14 = (byte) (b13 & 15);
            byte b15 = (byte) (b12 & 3);
            int i22 = b12 & (-128);
            int i23 = b12 >> 2;
            if (i22 != 0) {
                i23 ^= 192;
            }
            byte b16 = (byte) i23;
            int i24 = b13 & (-128);
            int i25 = b13 >> 4;
            if (i24 != 0) {
                i25 ^= 240;
            }
            int i26 = i6 + 1;
            char[] cArr4 = f3272j;
            cArr[i6] = cArr4[b16];
            int i27 = i26 + 1;
            cArr[i26] = cArr4[((byte) i25) | (b15 << 4)];
            cArr[i27] = cArr4[b14 << 2];
            cArr[i27 + 1] = f3270h;
        }
        return new String(cArr);
    }

    private static boolean a(char c3) {
        return c3 == ' ' || c3 == '\r' || c3 == '\n' || c3 == '\t';
    }

    private static boolean b(char c3) {
        return c3 == '=';
    }

    private static boolean c(char c3) {
        return c3 < 128 && f3271i[c3] != -1;
    }

    public static byte[] a(String str) {
        int i2;
        if (str == null) {
            return null;
        }
        char[] charArray = str.toCharArray();
        if (charArray == null) {
            i2 = 0;
        } else {
            int length = charArray.length;
            i2 = 0;
            for (int i3 = 0; i3 < length; i3++) {
                char c3 = charArray[i3];
                if (!(c3 == ' ' || c3 == '\r' || c3 == '\n' || c3 == '\t')) {
                    charArray[i2] = c3;
                    i2++;
                }
            }
        }
        if (i2 % 4 != 0) {
            return null;
        }
        int i4 = i2 / 4;
        if (i4 == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i4 * 3];
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i5 < i4 - 1) {
            int i8 = i6 + 1;
            char c4 = charArray[i6];
            if (c(c4)) {
                int i9 = i8 + 1;
                char c5 = charArray[i8];
                if (c(c5)) {
                    int i10 = i9 + 1;
                    char c6 = charArray[i9];
                    if (c(c6)) {
                        int i11 = i10 + 1;
                        char c7 = charArray[i10];
                        if (c(c7)) {
                            byte[] bArr2 = f3271i;
                            byte b3 = bArr2[c4];
                            byte b4 = bArr2[c5];
                            byte b5 = bArr2[c6];
                            byte b6 = bArr2[c7];
                            int i12 = i7 + 1;
                            bArr[i7] = (byte) ((b3 << 2) | (b4 >> 4));
                            int i13 = i12 + 1;
                            bArr[i12] = (byte) (((b4 & 15) << 4) | ((b5 >> 2) & 15));
                            i7 = i13 + 1;
                            bArr[i13] = (byte) ((b5 << 6) | b6);
                            i5++;
                            i6 = i11;
                        }
                    }
                }
            }
            return null;
        }
        int i14 = i6 + 1;
        char c8 = charArray[i6];
        if (!c(c8)) {
            return null;
        }
        int i15 = i14 + 1;
        char c9 = charArray[i14];
        if (!c(c9)) {
            return null;
        }
        byte[] bArr3 = f3271i;
        byte b7 = bArr3[c8];
        byte b8 = bArr3[c9];
        int i16 = i15 + 1;
        char c10 = charArray[i15];
        char c11 = charArray[i16];
        if (c(c10) && c(c11)) {
            byte b9 = bArr3[c10];
            byte b10 = bArr3[c11];
            int i17 = i7 + 1;
            bArr[i7] = (byte) ((b7 << 2) | (b8 >> 4));
            bArr[i17] = (byte) (((b8 & 15) << 4) | ((b9 >> 2) & 15));
            bArr[i17 + 1] = (byte) (b10 | (b9 << 6));
            return bArr;
        }
        if (b(c10) && b(c11)) {
            if ((b8 & 15) != 0) {
                return null;
            }
            int i18 = i5 * 3;
            byte[] bArr4 = new byte[i18 + 1];
            System.arraycopy(bArr, 0, bArr4, 0, i18);
            bArr4[i7] = (byte) ((b7 << 2) | (b8 >> 4));
            return bArr4;
        }
        if (b(c10) || !b(c11)) {
            return null;
        }
        byte b11 = bArr3[c10];
        if ((b11 & 3) != 0) {
            return null;
        }
        int i19 = i5 * 3;
        byte[] bArr5 = new byte[i19 + 2];
        System.arraycopy(bArr, 0, bArr5, 0, i19);
        bArr5[i7] = (byte) ((b7 << 2) | (b8 >> 4));
        bArr5[i7 + 1] = (byte) (((b11 >> 2) & 15) | ((b8 & 15) << 4));
        return bArr5;
    }

    private static int a(char[] cArr) {
        if (cArr == null) {
            return 0;
        }
        int length = cArr.length;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            char c3 = cArr[i3];
            if (!(c3 == ' ' || c3 == '\r' || c3 == '\n' || c3 == '\t')) {
                cArr[i2] = c3;
                i2++;
            }
        }
        return i2;
    }
}
