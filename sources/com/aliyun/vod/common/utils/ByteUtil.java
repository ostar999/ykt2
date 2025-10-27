package com.aliyun.vod.common.utils;

/* loaded from: classes2.dex */
public class ByteUtil {
    public static short[] byte2short(byte[] bArr) {
        int length = bArr.length / 2;
        short[] sArr = new short[length];
        for (int i2 = 0; i2 < length; i2++) {
            sArr[i2] = getShort(bArr, i2 * 2);
        }
        return sArr;
    }

    public static int byteArrayToInt(byte[] bArr) {
        return ((bArr[1] & 255) << 8) | (bArr[0] & 255);
    }

    public static long byteArrayToLong(byte[] bArr) {
        byte[] bArr2 = new byte[4];
        int i2 = 0;
        for (int i3 = 0; i3 < 4; i3++) {
            bArr2[i2] = bArr[i3];
            i2++;
        }
        long j2 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < 32; i5 += 8) {
            j2 |= (bArr2[i4] & 255) << i5;
            i4++;
        }
        return j2;
    }

    public static short byteArrayToShort(byte[] bArr) {
        return (short) ((((short) (bArr[1] & 255)) << 8) | ((short) (bArr[0] & 255)));
    }

    public static String byteArrayToString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length);
        for (byte b3 : bArr) {
            sb.append((char) b3);
        }
        return sb.toString();
    }

    public static byte[] doubleToByteArray(Double d3) {
        return longToByteArray(Double.doubleToLongBits(d3.doubleValue()));
    }

    public static byte[] floatToByteArray(Float f2) {
        return intToByteArray(Float.floatToIntBits(f2.floatValue()));
    }

    public static short getShort(byte[] bArr, int i2) {
        return (short) ((bArr[i2] & 255) | (bArr[i2 + 1] << 8));
    }

    public static byte[] intToByteArray(int i2) {
        return new byte[]{(byte) (i2 & 255), (byte) ((i2 >> 8) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 24) & 255)};
    }

    public static byte[] longToByteArray(long j2) {
        return new byte[]{(byte) (j2 & 255), (byte) ((j2 >> 8) & 255), (byte) ((j2 >> 16) & 255), (byte) ((j2 >> 24) & 255), (byte) ((j2 >> 32) & 255), (byte) ((j2 >> 40) & 255), (byte) ((j2 >> 48) & 255), (byte) ((j2 >> 56) & 255)};
    }

    public static void putShort(byte[] bArr, short s2, int i2) {
        bArr[i2 + 1] = (byte) (s2 >> 8);
        bArr[i2] = (byte) s2;
    }

    public static byte[] short2byte(short[] sArr) {
        byte[] bArr = new byte[sArr.length * 2];
        for (int i2 = 0; i2 < sArr.length; i2++) {
            putShort(bArr, sArr[i2], i2 * 2);
        }
        return bArr;
    }

    public static byte[] shortToByteArray(short s2) {
        return new byte[]{(byte) (s2 & 255), (byte) ((s2 >>> 8) & 255)};
    }
}
