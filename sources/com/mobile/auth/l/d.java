package com.mobile.auth.l;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public class d {
    public static String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            return a(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException unused) {
            return "";
        }
    }

    public static String a(byte[] bArr) throws NoSuchAlgorithmException {
        if (bArr == null) {
            return "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            return q.a(messageDigest.digest());
        } catch (Exception unused) {
            return "";
        }
    }
}
