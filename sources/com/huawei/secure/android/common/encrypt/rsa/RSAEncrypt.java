package com.huawei.secure.android.common.encrypt.rsa;

import android.text.TextUtils;
import android.util.Base64;
import com.huawei.secure.android.common.encrypt.utils.EncryptUtil;
import com.huawei.secure.android.common.encrypt.utils.b;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/* loaded from: classes4.dex */
public abstract class RSAEncrypt {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8256a = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    /* renamed from: b, reason: collision with root package name */
    private static final String f8257b = "RSAEncrypt";

    /* renamed from: c, reason: collision with root package name */
    private static final String f8258c = "UTF-8";

    /* renamed from: d, reason: collision with root package name */
    private static final String f8259d = "";

    /* renamed from: e, reason: collision with root package name */
    private static final int f8260e = 2048;

    /* renamed from: f, reason: collision with root package name */
    private static final String f8261f = "RSA";

    public static String decrypt(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            return decrypt(str, EncryptUtil.getPrivateKey(str2));
        }
        b.b(f8257b, "content or private key is null");
        return "";
    }

    public static String encrypt(String str, String str2) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            return encrypt(str, EncryptUtil.getPublicKey(str2));
        }
        b.b(f8257b, "content or public key is null");
        return "";
    }

    public static Map<String, Key> generateRSAKeyPair(int i2) throws NoSuchAlgorithmException {
        HashMap map = new HashMap(2);
        if (i2 < 2048) {
            b.b(f8257b, "generateRSAKeyPair: key length is too short");
            return map;
        }
        SecureRandom secureRandomGenSecureRandom = EncryptUtil.genSecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(f8261f);
        keyPairGenerator.initialize(i2, secureRandomGenSecureRandom);
        KeyPair keyPairGenerateKeyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPairGenerateKeyPair.getPublic();
        PrivateKey privateKey = keyPairGenerateKeyPair.getPrivate();
        map.put("publicKey", publicKey);
        map.put("privateKey", privateKey);
        return map;
    }

    public static boolean isPrivateKeyLengthRight(RSAPrivateKey rSAPrivateKey) {
        return rSAPrivateKey != null && rSAPrivateKey.getModulus().bitLength() >= 2048;
    }

    public static boolean isPublicKeyLengthRight(RSAPublicKey rSAPublicKey) {
        return rSAPublicKey != null && rSAPublicKey.getModulus().bitLength() >= 2048;
    }

    public static String decrypt(String str, PrivateKey privateKey) {
        if (!TextUtils.isEmpty(str) && privateKey != null && isPrivateKeyLengthRight((RSAPrivateKey) privateKey)) {
            try {
                return new String(decrypt(Base64.decode(str, 0), privateKey), "UTF-8");
            } catch (UnsupportedEncodingException e2) {
                b.b(f8257b, "RSA decrypt exception : " + e2.getMessage());
                return "";
            } catch (Exception e3) {
                b.b(f8257b, "exception : " + e3.getMessage());
                return "";
            }
        }
        b.b(f8257b, "content or privateKey is null , or length is too short");
        return "";
    }

    public static String encrypt(String str, PublicKey publicKey) {
        if (!TextUtils.isEmpty(str) && publicKey != null && isPublicKeyLengthRight((RSAPublicKey) publicKey)) {
            try {
                return Base64.encodeToString(encrypt(str.getBytes("UTF-8"), publicKey), 0);
            } catch (UnsupportedEncodingException unused) {
                b.b(f8257b, "encrypt: UnsupportedEncodingException");
                return "";
            } catch (Exception e2) {
                b.b(f8257b, "exception : " + e2.getMessage());
                return "";
            }
        }
        b.b(f8257b, "content or PublicKey is null , or length is too short");
        return "";
    }

    public static byte[] decrypt(byte[] bArr, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] bArr2 = new byte[0];
        if (bArr != null && privateKey != null && isPrivateKeyLengthRight((RSAPrivateKey) privateKey)) {
            try {
                Cipher cipher = Cipher.getInstance(f8256a);
                cipher.init(2, privateKey);
                return cipher.doFinal(bArr);
            } catch (GeneralSecurityException e2) {
                b.b(f8257b, "RSA decrypt exception : " + e2.getMessage());
                return bArr2;
            }
        }
        b.b(f8257b, "content or privateKey is null , or length is too short");
        return bArr2;
    }

    public static byte[] encrypt(byte[] bArr, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] bArr2 = new byte[0];
        if (bArr != null && publicKey != null && isPublicKeyLengthRight((RSAPublicKey) publicKey)) {
            try {
                Cipher cipher = Cipher.getInstance(f8256a);
                cipher.init(1, publicKey);
                return cipher.doFinal(bArr);
            } catch (GeneralSecurityException e2) {
                b.b(f8257b, "RSA encrypt exception : " + e2.getMessage());
                return bArr2;
            }
        }
        b.b(f8257b, "content or PublicKey is null , or length is too short");
        return bArr2;
    }
}
