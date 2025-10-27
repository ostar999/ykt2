package com.tencent.smtt.sdk.stat;

import com.google.common.base.Ascii;
import java.lang.reflect.Array;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: f, reason: collision with root package name */
    private static final int[] f21278f = {58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};

    /* renamed from: g, reason: collision with root package name */
    private static final int[] f21279g = {40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25};

    /* renamed from: h, reason: collision with root package name */
    private static final int[] f21280h = {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};

    /* renamed from: i, reason: collision with root package name */
    private static final int[] f21281i = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};

    /* renamed from: j, reason: collision with root package name */
    private static final int[] f21282j = {32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};

    /* renamed from: k, reason: collision with root package name */
    private static final int[] f21283k = {16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};

    /* renamed from: l, reason: collision with root package name */
    private static final int[][][] f21284l = {new int[][]{new int[]{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7}, new int[]{0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8}, new int[]{4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0}, new int[]{15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}}, new int[][]{new int[]{15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10}, new int[]{3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5}, new int[]{0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15}, new int[]{13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}}, new int[][]{new int[]{10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8}, new int[]{13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1}, new int[]{13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7}, new int[]{1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}}, new int[][]{new int[]{7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15}, new int[]{13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9}, new int[]{10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4}, new int[]{3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}}, new int[][]{new int[]{2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9}, new int[]{14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6}, new int[]{4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14}, new int[]{11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}}, new int[][]{new int[]{12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11}, new int[]{10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8}, new int[]{9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6}, new int[]{4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}}, new int[][]{new int[]{4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1}, new int[]{13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6}, new int[]{1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2}, new int[]{6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}}, new int[][]{new int[]{13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7}, new int[]{1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2}, new int[]{7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8}, new int[]{2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}}};

    /* renamed from: m, reason: collision with root package name */
    private static final int[] f21285m = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    /* renamed from: a, reason: collision with root package name */
    public static final byte[] f21273a = {98, -24, 57, -84, -115, 117, TarConstants.LF_CONTIG, 121};

    /* renamed from: b, reason: collision with root package name */
    public static final byte[] f21274b = {-25, -101, -115, 1, 47, 7, -27, -59, Ascii.DC2, -128, 123, 79, -44, 37, 46, 115};

    /* renamed from: c, reason: collision with root package name */
    public static final byte[] f21275c = {37, -110, 60, 127, 42, -27, -17, -110};

    /* renamed from: d, reason: collision with root package name */
    public static final byte[] f21276d = {-122, -8, -23, -84, -125, 113, 84, 99};

    /* renamed from: e, reason: collision with root package name */
    public static final byte[] f21277e = "AL!#$AC9Ahg@KLJ1".getBytes();

    private static void a(int[] iArr, int i2) {
        int[] iArr2 = new int[28];
        int[] iArr3 = new int[28];
        int[] iArr4 = new int[28];
        int[] iArr5 = new int[28];
        for (int i3 = 0; i3 < 28; i3++) {
            iArr2[i3] = iArr[i3];
            iArr3[i3] = iArr[i3 + 28];
        }
        if (i2 == 1) {
            int i4 = 0;
            while (i4 < 27) {
                int i5 = i4 + 1;
                iArr4[i4] = iArr2[i5];
                iArr5[i4] = iArr3[i5];
                i4 = i5;
            }
            iArr4[27] = iArr2[0];
            iArr5[27] = iArr3[0];
        } else if (i2 == 2) {
            for (int i6 = 0; i6 < 26; i6++) {
                int i7 = i6 + 2;
                iArr4[i6] = iArr2[i7];
                iArr5[i6] = iArr3[i7];
            }
            iArr4[26] = iArr2[0];
            iArr5[26] = iArr3[0];
            iArr4[27] = iArr2[1];
            iArr5[27] = iArr3[1];
        }
        for (int i8 = 0; i8 < 28; i8++) {
            iArr[i8] = iArr4[i8];
            iArr[i8 + 28] = iArr5[i8];
        }
    }

    private static void a(int[] iArr, int i2, int i3, int[][] iArr2) {
        int[] iArr3 = new int[32];
        int[] iArr4 = new int[32];
        int[] iArr5 = new int[32];
        int[] iArr6 = new int[32];
        int[] iArr7 = new int[48];
        int[][] iArr8 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 8, 6);
        int[] iArr9 = new int[8];
        int[] iArr10 = new int[32];
        int[] iArr11 = new int[32];
        int i4 = 0;
        for (int i5 = 0; i5 < 32; i5++) {
            iArr3[i5] = iArr[i5];
            iArr4[i5] = iArr[i5 + 32];
        }
        for (int i6 = 0; i6 < 48; i6++) {
            int i7 = iArr4[f21282j[i6] - 1];
            iArr7[i6] = i7;
            int i8 = i7 + iArr2[i2][i6];
            iArr7[i6] = i8;
            if (i8 == 2) {
                iArr7[i6] = 0;
            }
        }
        int i9 = 0;
        int i10 = 8;
        while (i9 < i10) {
            for (int i11 = i4; i11 < 6; i11++) {
                iArr8[i9][i11] = iArr7[(i9 * 6) + i11];
            }
            int[][] iArr12 = f21284l[i9];
            int[] iArr13 = iArr8[i9];
            iArr9[i9] = iArr12[(iArr13[i4] << 1) + iArr13[5]][(iArr13[1] << 3) + (iArr13[2] << 2) + (iArr13[3] << 1) + iArr13[4]];
            for (int i12 = 0; i12 < 4; i12++) {
                iArr10[((i9 * 4) + 3) - i12] = iArr9[i9] % 2;
                iArr9[i9] = iArr9[i9] / 2;
            }
            i9++;
            i10 = 8;
            i4 = 0;
        }
        for (int i13 = 0; i13 < 32; i13++) {
            iArr11[i13] = iArr10[f21283k[i13] - 1];
            iArr5[i13] = iArr4[i13];
            int i14 = iArr3[i13] + iArr11[i13];
            iArr6[i13] = i14;
            if (i14 == 2) {
                iArr6[i13] = 0;
            }
            if ((i3 == 0 && i2 == 0) || (i3 == 1 && i2 == 15)) {
                iArr[i13] = iArr6[i13];
                iArr[i13 + 32] = iArr5[i13];
            } else {
                iArr[i13] = iArr5[i13];
                iArr[i13 + 32] = iArr6[i13];
            }
        }
    }

    private static void a(int[] iArr, byte[] bArr) {
        for (int i2 = 0; i2 < 8; i2++) {
            for (int i3 = 0; i3 < 8; i3++) {
                bArr[i2] = (byte) (bArr[i2] + (iArr[(i2 << 3) + i3] << (7 - i3)));
            }
        }
    }

    private static void a(int[] iArr, int[][] iArr2) {
        int[] iArr3 = new int[56];
        for (int i2 = 0; i2 < 56; i2++) {
            iArr3[i2] = iArr[f21280h[i2] - 1];
        }
        for (int i3 = 0; i3 < 16; i3++) {
            a(iArr3, f21285m[i3]);
            for (int i4 = 0; i4 < 48; i4++) {
                iArr2[i3][i4] = iArr3[f21281i[i4] - 1];
            }
        }
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, int i2) {
        boolean z2;
        if (bArr2 != null && bArr != null) {
            try {
                byte[] bArrC = c(bArr);
                byte[] bArrB = b(bArr2);
                int length = bArrB.length;
                int i3 = length / 8;
                byte[] bArr3 = new byte[length];
                for (int i4 = 0; i4 < i3; i4++) {
                    byte[] bArr4 = new byte[8];
                    byte[] bArr5 = new byte[8];
                    System.arraycopy(bArrC, 0, bArr4, 0, 8);
                    int i5 = i4 * 8;
                    System.arraycopy(bArrB, i5, bArr5, 0, 8);
                    System.arraycopy(b(bArr4, bArr5, i2), 0, bArr3, i5, 8);
                }
                if (i2 != 0) {
                    return bArr3;
                }
                int length2 = bArr2.length;
                byte[] bArr6 = new byte[length2];
                System.arraycopy(bArr3, 0, bArr6, 0, length2);
                int i6 = length2 - 1;
                int i7 = bArr6[i6];
                if (i7 <= 0 || i7 > 8) {
                    return bArr3;
                }
                int i8 = 0;
                while (true) {
                    if (i8 >= i7) {
                        z2 = true;
                        break;
                    }
                    if (i7 != bArr6[i6 - i8]) {
                        z2 = false;
                        break;
                    }
                    i8++;
                }
                if (!z2) {
                    return bArr3;
                }
                int i9 = length2 - i7;
                byte[] bArr7 = new byte[i9];
                System.arraycopy(bArr6, 0, bArr7, 0, i9);
                return bArr7;
            } catch (Exception unused) {
            }
        }
        return bArr2;
    }

    private static byte[] a(int[] iArr, int i2, int[][] iArr2) {
        byte[] bArr = new byte[8];
        int[] iArr3 = new int[64];
        int[] iArr4 = new int[64];
        for (int i3 = 0; i3 < 64; i3++) {
            iArr3[i3] = iArr[f21278f[i3] - 1];
        }
        if (i2 == 1) {
            for (int i4 = 0; i4 < 16; i4++) {
                a(iArr3, i4, i2, iArr2);
            }
        } else if (i2 == 0) {
            for (int i5 = 15; i5 > -1; i5--) {
                a(iArr3, i5, i2, iArr2);
            }
        }
        for (int i6 = 0; i6 < 64; i6++) {
            iArr4[i6] = iArr3[f21279g[i6] - 1];
        }
        a(iArr4, bArr);
        return bArr;
    }

    private static int[] a(byte[] bArr) {
        int[] iArr = new int[8];
        for (int i2 = 0; i2 < 8; i2++) {
            byte b3 = bArr[i2];
            iArr[i2] = b3;
            if (b3 < 0) {
                int i3 = b3 + 256;
                iArr[i2] = i3;
                iArr[i2] = i3 % 256;
            }
        }
        int[] iArr2 = new int[64];
        for (int i4 = 0; i4 < 8; i4++) {
            for (int i5 = 0; i5 < 8; i5++) {
                iArr2[((i4 * 8) + 7) - i5] = iArr[i4] % 2;
                iArr[i4] = iArr[i4] / 2;
            }
        }
        return iArr2;
    }

    private static byte[] b(byte[] bArr) {
        int length = bArr.length;
        int i2 = 8 - (length % 8);
        int i3 = length + i2;
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        while (length < i3) {
            bArr2[length] = (byte) i2;
            length++;
        }
        return bArr2;
    }

    private static byte[] b(byte[] bArr, byte[] bArr2, int i2) {
        if (bArr.length != 8 || bArr2.length != 8 || (i2 != 1 && i2 != 0)) {
            throw new RuntimeException("Data Format Error !");
        }
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 16, 48);
        int[] iArrA = a(bArr);
        int[] iArrA2 = a(bArr2);
        a(iArrA, iArr);
        return a(iArrA2, i2, iArr);
    }

    private static byte[] c(byte[] bArr) {
        byte[] bArr2 = new byte[8];
        for (int i2 = 0; i2 < 8; i2++) {
            bArr2[i2] = 0;
        }
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length <= 8 ? bArr.length : 8);
        return bArr2;
    }
}
