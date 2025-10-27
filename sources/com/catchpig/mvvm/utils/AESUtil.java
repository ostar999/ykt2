package com.catchpig.mvvm.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class AESUtil {
    public static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final String AES_CFB = "AES/CFB/PKCS5Padding";
    private static final String AES_ECB = "AES/ECB/PKCS5Padding";
    private static final Integer IV_LENGTH = 16;

    public static String decrypt(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if (isEmpty(str) || isEmpty(str2)) {
            return null;
        }
        byte[] bArrDecode = Base64.decode(str);
        try {
            Cipher cipher = Cipher.getInstance(AES_ECB);
            cipher.init(2, getSecretKeySpec(str2));
            return new String(cipher.doFinal(bArrDecode), StandardCharsets.UTF_8);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String encrypt(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if (isEmpty(str) || isEmpty(str2)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(AES_ECB);
            cipher.init(1, getSecretKeySpec(str2));
            return Base64.encode(cipher.doFinal(getBytes(str)));
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public static byte[] getBytes(String str) {
        if (isEmpty(str)) {
            return null;
        }
        try {
            return str.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String getIV() {
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < IV_LENGTH.intValue(); i2++) {
            stringBuffer.append("syyxydd20240530yddsyyx".charAt(random.nextInt(22)));
        }
        return stringBuffer.toString();
    }

    public static SecretKeySpec getSecretKeySpec(String str) {
        return new SecretKeySpec(getBytes(str), "AES");
    }

    public static boolean isEmpty(Object obj) {
        return obj == null || "".equals(obj);
    }

    private void test(String[] strArr) {
    }

    public static String encrypt(String str, String str2, String str3, String str4) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (isEmpty(str) || isEmpty(str2) || isEmpty(str3)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(str4);
            cipher.init(1, getSecretKeySpec(str2), new IvParameterSpec(getBytes(str3)));
            return Base64.encode(cipher.doFinal(getBytes(str)));
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }

    public static String decrypt(String str, String str2, String str3, String str4) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (isEmpty(str) || isEmpty(str2) || isEmpty(str3)) {
            return null;
        }
        byte[] bArrDecode = Base64.decode(str);
        try {
            Cipher cipher = Cipher.getInstance(str4);
            cipher.init(2, getSecretKeySpec(str2), new IvParameterSpec(getBytes(str3)));
            return new String(cipher.doFinal(bArrDecode), StandardCharsets.UTF_8);
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }
    }
}
