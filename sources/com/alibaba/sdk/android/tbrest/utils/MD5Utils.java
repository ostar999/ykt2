package com.alibaba.sdk.android.tbrest.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class MD5Utils {
    public static char[] hexChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static byte[] getMd5(byte[] bArr) throws NoSuchAlgorithmException {
        if (bArr == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getMd5Hex(byte[] bArr) throws NoSuchAlgorithmException {
        byte[] md5 = getMd5(bArr);
        return md5 != null ? toHexString(md5) : "0000000000000000";
    }

    public static String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            sb.append(hexChar[(bArr[i2] & 240) >>> 4]);
            sb.append(hexChar[bArr[i2] & 15]);
        }
        return sb.toString();
    }
}
