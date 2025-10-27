package com.alibaba.sdk.android.emas;

import android.text.TextUtils;
import com.alibaba.sdk.android.tbrest.utils.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public final class f {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f2693a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String a(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                return Base64.encodeBase64String(m25a(str, str2));
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static String b(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            try {
                return a(Base64.decode(str2), str);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    private static String c(String str, String str2) throws NoSuchAlgorithmException {
        if (str != null) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(str2);
                messageDigest.update(str.getBytes());
                return a(messageDigest.digest());
            } catch (Exception unused) {
            }
        }
        return "";
    }

    /* renamed from: a, reason: collision with other method in class */
    private static byte[] m25a(String str, String str2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(c(str, "MD5").substring(0, 16).getBytes("utf-8"), "AES/ECB/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, secretKeySpec);
        return cipher.doFinal(str2.getBytes("utf-8"));
    }

    private static String a(byte[] bArr, String str) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(c(str, "MD5").substring(0, 16).getBytes("utf-8"), "AES/ECB/PKCS5Padding");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, secretKeySpec);
        return new String(cipher.doFinal(bArr));
    }

    private static String a(byte[] bArr) {
        int length = bArr.length;
        StringBuilder sb = new StringBuilder(length * 2);
        for (int i2 = 0; i2 < length; i2++) {
            char[] cArr = f2693a;
            sb.append(cArr[(bArr[i2] >> 4) & 15]);
            sb.append(cArr[bArr[i2] & 15]);
        }
        return sb.toString();
    }
}
