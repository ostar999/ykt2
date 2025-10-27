package org.bouncycastle.crypto.modes.gcm;

import org.bouncycastle.crypto.util.Pack;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
abstract class GCMUtil {
    public static int[] asInts(byte[] bArr) {
        return new int[]{Pack.bigEndianToInt(bArr, 0), Pack.bigEndianToInt(bArr, 4), Pack.bigEndianToInt(bArr, 8), Pack.bigEndianToInt(bArr, 12)};
    }

    public static void multiply(byte[] bArr, byte[] bArr2) {
        byte[] bArrClone = Arrays.clone(bArr);
        byte[] bArr3 = new byte[16];
        for (int i2 = 0; i2 < 16; i2++) {
            byte b3 = bArr2[i2];
            for (int i3 = 7; i3 >= 0; i3--) {
                if (((1 << i3) & b3) != 0) {
                    xor(bArr3, bArrClone);
                }
                boolean z2 = (bArrClone[15] & 1) != 0;
                shiftRight(bArrClone);
                if (z2) {
                    bArrClone[0] = (byte) (bArrClone[0] ^ (-31));
                }
            }
        }
        System.arraycopy(bArr3, 0, bArr, 0, 16);
    }

    public static void multiplyP(int[] iArr) {
        boolean z2 = (iArr[3] & 1) != 0;
        shiftRight(iArr);
        if (z2) {
            iArr[0] = iArr[0] ^ (-520093696);
        }
    }

    public static void multiplyP8(int[] iArr) {
        for (int i2 = 8; i2 != 0; i2--) {
            multiplyP(iArr);
        }
    }

    public static byte[] oneAsBytes() {
        byte[] bArr = new byte[16];
        bArr[0] = -128;
        return bArr;
    }

    public static int[] oneAsInts() {
        int[] iArr = new int[4];
        iArr[0] = Integer.MIN_VALUE;
        return iArr;
    }

    public static void shiftRight(byte[] bArr) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = bArr[i2] & 255;
            bArr[i2] = (byte) (i3 | (i4 >>> 1));
            i2++;
            if (i2 == 16) {
                return;
            } else {
                i3 = (i4 & 1) << 7;
            }
        }
    }

    public static void shiftRight(int[] iArr) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = iArr[i2];
            iArr[i2] = i3 | (i4 >>> 1);
            i2++;
            if (i2 == 4) {
                return;
            } else {
                i3 = i4 << 31;
            }
        }
    }

    public static void xor(byte[] bArr, byte[] bArr2) {
        for (int i2 = 15; i2 >= 0; i2--) {
            bArr[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
    }

    public static void xor(int[] iArr, int[] iArr2) {
        for (int i2 = 3; i2 >= 0; i2--) {
            iArr[i2] = iArr[i2] ^ iArr2[i2];
        }
    }
}
