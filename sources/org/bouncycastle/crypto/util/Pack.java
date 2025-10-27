package org.bouncycastle.crypto.util;

import com.google.common.base.Ascii;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes9.dex */
public abstract class Pack {
    public static int bigEndianToInt(byte[] bArr, int i2) {
        int i3 = bArr[i2] << Ascii.CAN;
        int i4 = i2 + 1;
        int i5 = i3 | ((bArr[i4] & 255) << 16);
        int i6 = i4 + 1;
        return (bArr[i6 + 1] & 255) | i5 | ((bArr[i6] & 255) << 8);
    }

    public static long bigEndianToLong(byte[] bArr, int i2) {
        int iBigEndianToInt = bigEndianToInt(bArr, i2);
        return (bigEndianToInt(bArr, i2 + 4) & InternalZipConstants.ZIP_64_LIMIT) | ((iBigEndianToInt & InternalZipConstants.ZIP_64_LIMIT) << 32);
    }

    public static void intToBigEndian(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) (i2 >>> 24);
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i2 >>> 16);
        int i5 = i4 + 1;
        bArr[i5] = (byte) (i2 >>> 8);
        bArr[i5 + 1] = (byte) i2;
    }

    public static void intToLittleEndian(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        int i5 = i4 + 1;
        bArr[i5] = (byte) (i2 >>> 16);
        bArr[i5 + 1] = (byte) (i2 >>> 24);
    }

    public static int littleEndianToInt(byte[] bArr, int i2) {
        byte b3 = bArr[i2];
        int i3 = i2 + 1;
        int i4 = b3 | ((bArr[i3] & 255) << 8);
        int i5 = i3 + 1;
        return ((bArr[i5 + 1] & 255) << 24) | i4 | ((bArr[i5] & 255) << 16);
    }

    public static long littleEndianToLong(byte[] bArr, int i2) {
        return ((littleEndianToInt(bArr, i2 + 4) & InternalZipConstants.ZIP_64_LIMIT) << 32) | (littleEndianToInt(bArr, i2) & InternalZipConstants.ZIP_64_LIMIT);
    }

    public static void longToBigEndian(long j2, byte[] bArr, int i2) {
        intToBigEndian((int) (j2 >>> 32), bArr, i2);
        intToBigEndian((int) (j2 & InternalZipConstants.ZIP_64_LIMIT), bArr, i2 + 4);
    }

    public static void longToLittleEndian(long j2, byte[] bArr, int i2) {
        intToLittleEndian((int) (InternalZipConstants.ZIP_64_LIMIT & j2), bArr, i2);
        intToLittleEndian((int) (j2 >>> 32), bArr, i2 + 4);
    }
}
