package com.yddmi.doctor.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes6.dex */
public class SecretUtil {
    private static String IV = "";
    private static String KEY = "";

    public static String desEncrypt(String str) {
        return desEncrypt(str, KEY, IV);
    }

    public static String encrypt(String str) {
        return encrypt(str, KEY, IV);
    }

    private static String desEncrypt(String str, String str2, String str3) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            new Base64();
            byte[] bArrDecode = Base64.decode(str);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(2, new SecretKeySpec(str2.getBytes(), "AES"), new IvParameterSpec(str3.getBytes()));
            return new String(cipher.doFinal(bArrDecode)).trim();
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String encrypt(String str, String str2, String str3) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] bytes = str.getBytes();
            int length = bytes.length;
            if (length % blockSize != 0) {
                length += blockSize - (length % blockSize);
            }
            byte[] bArr = new byte[length];
            System.arraycopy(bytes, 0, bArr, 0, bytes.length);
            cipher.init(1, new SecretKeySpec(str2.getBytes(), "AES"), new IvParameterSpec(str3.getBytes()));
            byte[] bArrDoFinal = cipher.doFinal(bArr);
            new Base64();
            return Base64.encode(bArrDoFinal);
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }
}
