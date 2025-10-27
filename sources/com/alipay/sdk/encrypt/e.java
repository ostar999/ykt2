package com.alipay.sdk.encrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public final class e {

    /* renamed from: a, reason: collision with root package name */
    private static String f3274a = "DESede/CBC/PKCS5Padding";

    public static byte[] a(String str, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "DESede");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[8]);
            Cipher cipher = Cipher.getInstance(f3274a);
            cipher.init(1, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] b(String str, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "DESede");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(new byte[8]);
            Cipher cipher = Cipher.getInstance(f3274a);
            cipher.init(2, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(bArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String a(String str, String str2) {
        try {
            return a.a(a(str, str2.getBytes()));
        } catch (Exception unused) {
            return null;
        }
    }

    public static String b(String str, String str2) {
        try {
            return new String(b(str, a.a(str2)));
        } catch (Exception unused) {
            return null;
        }
    }
}
