package com.alibaba.sdk.android.tbrest.utils;

/* loaded from: classes2.dex */
public class ByteUtils {
    public static String bytes2UTF8String(byte[] bArr) {
        try {
            return new String(bArr, "UTF-8");
        } catch (Exception unused) {
            return "";
        }
    }

    public static String bytes2UTF8string(byte[] bArr, int i2, int i3) {
        if (bArr == null || i2 < 0 || i3 < 0 || bArr.length < i2 + i3) {
            return "";
        }
        byte[] bArr2 = new byte[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            bArr2[i4] = bArr[i2];
            i2++;
        }
        return bytes2UTF8String(bArr2);
    }

    public static int bytesToInt(byte[] bArr, int i2, int i3) {
        if (bArr == null || i2 < 0 || i3 < 0 || bArr.length < i2 + i3) {
            return 0;
        }
        byte[] bArr2 = new byte[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            bArr2[i4] = bArr[i2];
            i2++;
        }
        return bytesToInt(bArr2);
    }

    public static byte[] intToBytes(int i2, int i3) {
        if (i3 > 4 || i3 < 1) {
            return null;
        }
        byte[] bArr = new byte[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            bArr[i4] = (byte) ((i2 >> (((i3 - i4) - 1) * 8)) & 255);
        }
        return bArr;
    }

    public static byte[] intToBytes1(int i2) {
        return new byte[]{(byte) (i2 & 255)};
    }

    public static byte[] intToBytes2(int i2) {
        return new byte[]{(byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
    }

    public static byte[] intToBytes3(int i2) {
        return new byte[]{(byte) ((i2 >> 16) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
    }

    public static byte[] intToBytes4(int i2) {
        return new byte[]{(byte) ((i2 >> 24) & 255), (byte) ((i2 >> 16) & 255), (byte) ((i2 >> 8) & 255), (byte) (i2 & 255)};
    }

    public static byte[] subBytes(byte[] bArr, int i2, int i3) {
        int i4;
        if (bArr == null || i2 < 0 || i3 < 0 || bArr.length < (i4 = i2 + i3)) {
            return null;
        }
        byte[] bArr2 = new byte[i3];
        for (int i5 = i2; i5 < i4; i5++) {
            bArr2[i5 - i2] = bArr[i5];
        }
        return bArr2;
    }

    public static int bytesToInt(byte[] bArr) {
        if (bArr == null || bArr.length > 4) {
            return 0;
        }
        int length = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            length |= (bArr[i2] & 255) << (((bArr.length - i2) - 1) * 8);
        }
        return length;
    }
}
