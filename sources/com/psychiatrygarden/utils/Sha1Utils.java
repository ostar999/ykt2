package com.psychiatrygarden.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes6.dex */
public class Sha1Utils {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String encode(String str) throws NoSuchAlgorithmException {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String getFormattedText(byte[] bytes) {
        int length = bytes.length;
        StringBuilder sb = new StringBuilder(length * 2);
        for (int i2 = 0; i2 < length; i2++) {
            char[] cArr = HEX_DIGITS;
            sb.append(cArr[(bytes[i2] >> 4) & 15]);
            sb.append(cArr[bytes[i2] & 15]);
        }
        return sb.toString();
    }
}
