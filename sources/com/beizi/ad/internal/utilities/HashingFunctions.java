package com.beizi.ad.internal.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class HashingFunctions {
    private static String byteToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b3 : bArr) {
            int i2 = (b3 >>> 4) & 15;
            int i3 = 0;
            while (true) {
                sb.append((char) ((i2 < 0 || i2 > 9) ? (i2 - 10) + 97 : i2 + 48));
                i2 = b3 & 15;
                int i4 = i3 + 1;
                if (i3 >= 1) {
                    break;
                }
                i3 = i4;
            }
        }
        return sb.toString();
    }

    public static String md5(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            return byteToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            return "";
        }
    }

    public static String sha1(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes());
            return byteToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException unused) {
            return "";
        }
    }
}
