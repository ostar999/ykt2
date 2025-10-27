package com.beizi.ad.internal.utilities;

/* loaded from: classes2.dex */
public class Hex {
    public static byte[] hexStringToByteArray(String str) {
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i2 = 0; i2 < length; i2 += 2) {
            bArr[i2 / 2] = (byte) ((Character.digit(str.charAt(i2), 16) << 4) + Character.digit(str.charAt(i2 + 1), 16));
        }
        return bArr;
    }
}
