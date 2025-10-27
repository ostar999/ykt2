package com.xiaomi.push;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes6.dex */
public class ax {
    private static String a(byte b3) {
        int i2 = (b3 & 127) + (b3 < 0 ? 128 : 0);
        StringBuilder sb = new StringBuilder();
        sb.append(i2 < 16 ? "0" : "");
        sb.append(Integer.toHexString(i2).toLowerCase());
        return sb.toString();
    }

    public static String a(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            StringBuffer stringBuffer = new StringBuffer();
            messageDigest.update(str.getBytes(), 0, str.length());
            for (byte b3 : messageDigest.digest()) {
                stringBuffer.append(a(b3));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }

    public static String b(String str) {
        return a(str).subSequence(8, 24).toString();
    }
}
