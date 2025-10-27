package com.xiaomi.push;

/* loaded from: classes6.dex */
public class av {

    /* renamed from: a, reason: collision with other field name */
    private static byte[] f199a;

    /* renamed from: a, reason: collision with root package name */
    private static final String f24620a = System.getProperty("line.separator");

    /* renamed from: a, reason: collision with other field name */
    private static char[] f200a = new char[64];

    static {
        char c3 = 'A';
        int i2 = 0;
        while (c3 <= 'Z') {
            f200a[i2] = c3;
            c3 = (char) (c3 + 1);
            i2++;
        }
        char c4 = 'a';
        while (c4 <= 'z') {
            f200a[i2] = c4;
            c4 = (char) (c4 + 1);
            i2++;
        }
        char c5 = '0';
        while (c5 <= '9') {
            f200a[i2] = c5;
            c5 = (char) (c5 + 1);
            i2++;
        }
        char[] cArr = f200a;
        cArr[i2] = '+';
        cArr[i2 + 1] = '/';
        f199a = new byte[128];
        int i3 = 0;
        while (true) {
            byte[] bArr = f199a;
            if (i3 >= bArr.length) {
                break;
            }
            bArr[i3] = -1;
            i3++;
        }
        for (int i4 = 0; i4 < 64; i4++) {
            f199a[f200a[i4]] = (byte) i4;
        }
    }

    public static byte[] a(String str) {
        return a(str.toCharArray());
    }

    public static byte[] a(char[] cArr) {
        return a(cArr, 0, cArr.length);
    }

    public static byte[] a(char[] cArr, int i2, int i3) {
        int i4;
        char c3;
        char c4;
        int i5;
        if (i3 % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        while (i3 > 0 && cArr[(i2 + i3) - 1] == '=') {
            i3--;
        }
        int i6 = (i3 * 3) / 4;
        byte[] bArr = new byte[i6];
        int i7 = i3 + i2;
        int i8 = 0;
        while (i2 < i7) {
            int i9 = i2 + 1;
            char c5 = cArr[i2];
            int i10 = i9 + 1;
            char c6 = cArr[i9];
            if (i10 < i7) {
                i4 = i10 + 1;
                c3 = cArr[i10];
            } else {
                i4 = i10;
                c3 = 'A';
            }
            if (i4 < i7) {
                i5 = i4 + 1;
                c4 = cArr[i4];
            } else {
                int i11 = i4;
                c4 = 'A';
                i5 = i11;
            }
            if (c5 > 127 || c6 > 127 || c3 > 127 || c4 > 127) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            byte[] bArr2 = f199a;
            byte b3 = bArr2[c5];
            byte b4 = bArr2[c6];
            byte b5 = bArr2[c3];
            byte b6 = bArr2[c4];
            if (b3 < 0 || b4 < 0 || b5 < 0 || b6 < 0) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            int i12 = (b3 << 2) | (b4 >>> 4);
            int i13 = ((b4 & 15) << 4) | (b5 >>> 2);
            int i14 = ((b5 & 3) << 6) | b6;
            int i15 = i8 + 1;
            bArr[i8] = (byte) i12;
            if (i15 < i6) {
                bArr[i15] = (byte) i13;
                i15++;
            }
            if (i15 < i6) {
                bArr[i15] = (byte) i14;
                i8 = i15 + 1;
            } else {
                i8 = i15;
            }
            i2 = i5;
        }
        return bArr;
    }

    public static char[] a(byte[] bArr) {
        return a(bArr, 0, bArr.length);
    }

    public static char[] a(byte[] bArr, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7 = ((i3 * 4) + 2) / 3;
        char[] cArr = new char[((i3 + 2) / 3) * 4];
        int i8 = i3 + i2;
        int i9 = 0;
        while (i2 < i8) {
            int i10 = i2 + 1;
            int i11 = bArr[i2] & 255;
            if (i10 < i8) {
                i4 = i10 + 1;
                i5 = bArr[i10] & 255;
            } else {
                i4 = i10;
                i5 = 0;
            }
            if (i4 < i8) {
                i6 = bArr[i4] & 255;
                i4++;
            } else {
                i6 = 0;
            }
            int i12 = i11 >>> 2;
            int i13 = ((i11 & 3) << 4) | (i5 >>> 4);
            int i14 = ((i5 & 15) << 2) | (i6 >>> 6);
            int i15 = i6 & 63;
            int i16 = i9 + 1;
            char[] cArr2 = f200a;
            cArr[i9] = cArr2[i12];
            int i17 = i16 + 1;
            cArr[i16] = cArr2[i13];
            char c3 = '=';
            cArr[i17] = i17 < i7 ? cArr2[i14] : '=';
            int i18 = i17 + 1;
            if (i18 < i7) {
                c3 = cArr2[i15];
            }
            cArr[i18] = c3;
            i9 = i18 + 1;
            i2 = i4;
        }
        return cArr;
    }
}
