package org.apache.commons.compress.archivers.cpio;

/* loaded from: classes9.dex */
class CpioUtil {
    public static long byteArray2long(byte[] bArr, boolean z2) {
        if (bArr.length % 2 != 0) {
            throw new UnsupportedOperationException();
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        if (!z2) {
            int i2 = 0;
            while (i2 < length) {
                byte b3 = bArr2[i2];
                int i3 = i2 + 1;
                bArr2[i2] = bArr2[i3];
                bArr2[i3] = b3;
                i2 = i3 + 1;
            }
        }
        long j2 = bArr2[0] & 255;
        for (int i4 = 1; i4 < length; i4++) {
            j2 = (j2 << 8) | (bArr2[i4] & 255);
        }
        return j2;
    }

    public static long fileType(long j2) {
        return j2 & 61440;
    }

    public static byte[] long2byteArray(long j2, int i2, boolean z2) {
        byte[] bArr = new byte[i2];
        if (i2 % 2 != 0 || i2 < 2) {
            throw new UnsupportedOperationException();
        }
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            bArr[i3] = (byte) (255 & j2);
            j2 >>= 8;
        }
        if (!z2) {
            int i4 = 0;
            while (i4 < i2) {
                byte b3 = bArr[i4];
                int i5 = i4 + 1;
                bArr[i4] = bArr[i5];
                bArr[i5] = b3;
                i4 = i5 + 1;
            }
        }
        return bArr;
    }
}
