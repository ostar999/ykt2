package com.yddmi.doctor.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

/* loaded from: classes6.dex */
public class RsaUtil {
    private static final int KEY_SIZE = 2048;
    private static final String RSA2_SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final String RSA_KEY_ALGORITHM = "RSA";
    private static final String RSA_SIGNATURE_ALGORITHM = "SHA1withRSA";

    public static String decryptByPrivateKey(String str, String str2) throws Exception {
        PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance(RSA_KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(str2)));
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(2, privateKeyGeneratePrivate);
        return new String(cipher.doFinal(Base64.decode(str)));
    }

    public static String decryptByPublicKey(String str, String str2) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(str2));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKeyGeneratePublic = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, publicKeyGeneratePublic);
        return new String(cipher.doFinal(Base64.decode(str)));
    }

    public static String encryptByPrivateKey(String str, String str2) throws Exception {
        PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(str2));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKeyGeneratePrivate = keyFactory.generatePrivate(pKCS8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, privateKeyGeneratePrivate);
        return Base64.encode(cipher.doFinal(str.getBytes()));
    }

    public static String encryptByPublicKey(String str, String str2) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(str2));
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKeyGeneratePublic = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicKeyGeneratePublic);
        return Base64.encode(cipher.doFinal(str.getBytes()));
    }

    public static Map<String, String> generateKey() throws NoSuchAlgorithmException {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.setSeed("Alian".getBytes());
            keyPairGenerator.initialize(2048, secureRandom);
            KeyPair keyPairGenKeyPair = keyPairGenerator.genKeyPair();
            String strEncode = Base64.encode(keyPairGenKeyPair.getPublic().getEncoded());
            String strEncode2 = Base64.encode(keyPairGenKeyPair.getPrivate().getEncoded());
            HashMap map = new HashMap();
            map.put("publicKeyStr", strEncode);
            map.put("privateKeyStr", strEncode2);
            return map;
        } catch (NoSuchAlgorithmException unused) {
            throw new RuntimeException("RSA初始化密钥出现错误,算法异常");
        }
    }

    public static String sign(byte[] bArr, byte[] bArr2, String str) throws Exception {
        PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance(RSA_KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(bArr2));
        Signature signature = Signature.getInstance(RSA_KEY_ALGORITHM.equals(str) ? RSA_SIGNATURE_ALGORITHM : RSA2_SIGNATURE_ALGORITHM);
        signature.initSign(privateKeyGeneratePrivate);
        signature.update(bArr);
        return Base64.encode(signature.sign());
    }

    public static boolean verify(byte[] bArr, byte[] bArr2, byte[] bArr3, String str) throws Exception {
        PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(RSA_KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(bArr3));
        Signature signature = Signature.getInstance(RSA_KEY_ALGORITHM.equals(str) ? RSA_SIGNATURE_ALGORITHM : RSA2_SIGNATURE_ALGORITHM);
        signature.initVerify(publicKeyGeneratePublic);
        signature.update(bArr);
        return signature.verify(bArr2);
    }
}
