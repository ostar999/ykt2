package com.plv.thirdpart.litepal.util.cipher;

import android.util.Base64;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes5.dex */
public final class AESCrypt {
    private static final String AES_MODE = "AES/CBC/PKCS7Padding";
    private static final String CHARSET = "UTF-8";
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String TAG = "AESCrypt";
    private static final byte[] ivBytes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static boolean DEBUG_LOG_ENABLED = false;

    private AESCrypt() {
    }

    private static String bytesToHex(byte[] bArr) {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] cArr2 = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = bArr[i2] & 255;
            int i4 = i2 * 2;
            cArr2[i4] = cArr[i3 >>> 4];
            cArr2[i4 + 1] = cArr[i3 & 15];
        }
        return new String(cArr2);
    }

    public static String decrypt(String str, String str2) throws GeneralSecurityException {
        try {
            SecretKeySpec secretKeySpecGenerateKey = generateKey(str);
            log("base64EncodedCipherText", str2);
            byte[] bArrDecode = Base64.decode(str2, 2);
            log("decodedCipherText", bArrDecode);
            byte[] bArrDecrypt = decrypt(secretKeySpecGenerateKey, ivBytes, bArrDecode);
            log("decryptedBytes", bArrDecrypt);
            String str3 = new String(bArrDecrypt, "UTF-8");
            log("message", str3);
            return str3;
        } catch (UnsupportedEncodingException e2) {
            if (DEBUG_LOG_ENABLED) {
                Log.e(TAG, "UnsupportedEncodingException ", e2);
            }
            throw new GeneralSecurityException(e2);
        }
    }

    public static String encrypt(String str, String str2) throws GeneralSecurityException {
        try {
            SecretKeySpec secretKeySpecGenerateKey = generateKey(str);
            log("message", str2);
            String strEncodeToString = Base64.encodeToString(encrypt(secretKeySpecGenerateKey, ivBytes, str2.getBytes("UTF-8")), 2);
            log("Base64.NO_WRAP", strEncodeToString);
            return strEncodeToString;
        } catch (UnsupportedEncodingException e2) {
            if (DEBUG_LOG_ENABLED) {
                Log.e(TAG, "UnsupportedEncodingException ", e2);
            }
            throw new GeneralSecurityException(e2);
        }
    }

    private static SecretKeySpec generateKey(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        byte[] bytes = str.getBytes("UTF-8");
        messageDigest.update(bytes, 0, bytes.length);
        byte[] bArrDigest = messageDigest.digest();
        log("SHA-256 key ", bArrDigest);
        return new SecretKeySpec(bArrDigest, "AES");
    }

    private static void log(String str, byte[] bArr) {
        if (DEBUG_LOG_ENABLED) {
            Log.d(TAG, str + StrPool.BRACKET_START + bArr.length + "] [" + bytesToHex(bArr) + StrPool.BRACKET_END);
        }
    }

    private static void log(String str, String str2) {
        if (DEBUG_LOG_ENABLED) {
            Log.d(TAG, str + StrPool.BRACKET_START + str2.length() + "] [" + str2 + StrPool.BRACKET_END);
        }
    }

    public static byte[] encrypt(SecretKeySpec secretKeySpec, byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(1, secretKeySpec, new IvParameterSpec(bArr));
        byte[] bArrDoFinal = cipher.doFinal(bArr2);
        log("cipherText", bArrDoFinal);
        return bArrDoFinal;
    }

    public static byte[] decrypt(SecretKeySpec secretKeySpec, byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        cipher.init(2, secretKeySpec, new IvParameterSpec(bArr));
        byte[] bArrDoFinal = cipher.doFinal(bArr2);
        log("decryptedBytes", bArrDoFinal);
        return bArrDoFinal;
    }
}
