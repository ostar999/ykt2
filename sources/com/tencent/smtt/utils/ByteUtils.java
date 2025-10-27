package com.tencent.smtt.utils;

/* loaded from: classes6.dex */
public class ByteUtils {
    public static void Word2Byte(byte[] bArr, int i2, short s2) {
        bArr[i2] = (byte) (s2 >> 8);
        bArr[i2 + 1] = (byte) s2;
    }

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if ((bArr[i2] & 255) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toString(bArr[i2] & 255, 16));
        }
        return stringBuffer.toString();
    }

    public static byte[] subByte(byte[] bArr, int i2, int i3) {
        int length = bArr.length;
        if (i2 < 0 || i2 + i3 > length) {
            return null;
        }
        if (i3 < 0) {
            i3 = bArr.length - i2;
        }
        byte[] bArr2 = new byte[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            bArr2[i4] = bArr[i4 + i2];
        }
        return bArr2;
    }
}
