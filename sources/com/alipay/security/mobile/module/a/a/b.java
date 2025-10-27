package com.alipay.security.mobile.module.a.a;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public final class b {
    public static String a(String str) throws NoSuchAlgorithmException {
        try {
            if (com.alipay.security.mobile.module.a.a.a(str)) {
                return null;
            }
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] bArrDigest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b3 : bArrDigest) {
                sb.append(String.format("%02x", Byte.valueOf(b3)));
            }
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }
}
