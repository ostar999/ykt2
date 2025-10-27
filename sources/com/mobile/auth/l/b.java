package com.mobile.auth.l;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import android.util.Base64;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.HashMap;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static byte[] f10414a;

    public static String a(Context context, String str) {
        a();
        byte[] bArrB = b(context);
        if (bArrB != null) {
            return a.a(bArrB, str, f10414a);
        }
        a();
        return null;
    }

    public static void a() {
        k.a("AES_KEY");
    }

    private static boolean a(Context context) throws InterruptedException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", "AndroidKeyStore");
            keyGenerator.init(new KeyGenParameterSpec.Builder("CMCC_SDK_V1", 3).setDigests("SHA-256", "SHA-512").setBlockModes("CBC").setEncryptionPaddings("PKCS7Padding").setRandomizedEncryptionRequired(false).setKeySize(256).build());
            Thread.sleep(1000L);
            keyGenerator.generateKey();
            return true;
        } catch (Exception e2) {
            c.a("KeystoreUtil", e2.getMessage());
            return false;
        }
    }

    public static boolean a(Context context, boolean z2) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (keyStore.getKey("CMCC_SDK_V1", null) != null) {
                return true;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (z2) {
            return a(context);
        }
        return false;
    }

    private static String b() {
        return k.b("AES_KEY", "");
    }

    public static String b(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        byte[] bArrB = b(context);
        if (bArrB != null) {
            return a.b(bArrB, str, f10414a);
        }
        a();
        return null;
    }

    private static synchronized byte[] b(Context context) {
        Cipher cipher;
        String str;
        String str2;
        byte[] bArrDoFinal;
        Cipher cipher2;
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (!a(context, false)) {
                return null;
            }
            String strB = b();
            if (TextUtils.isEmpty(strB)) {
                bArrDoFinal = q.a();
                f10414a = q.a();
                Key key = keyStore.getKey("CMCC_SDK_V1", null);
                if (key instanceof SecretKey) {
                    c.b("KeystoreUtil", "随机生成aes秘钥");
                    cipher2 = Cipher.getInstance("AES/CBC/PKCS7Padding");
                    cipher2.init(1, key, new IvParameterSpec(f10414a));
                } else {
                    if (!(key instanceof PrivateKey)) {
                        return null;
                    }
                    PublicKey publicKey = keyStore.getCertificate("CMCC_SDK_V1").getPublicKey();
                    Cipher cipher3 = Cipher.getInstance("RSA/ECB/OAEPWithSHA256AndMGF1Padding");
                    c.b("KeystoreUtil", "生成rsa密");
                    cipher3.init(1, publicKey);
                    cipher2 = cipher3;
                }
                String strEncodeToString = Base64.encodeToString(cipher2.doFinal(bArrDoFinal), 0);
                String strEncodeToString2 = Base64.encodeToString(f10414a, 0);
                HashMap map = new HashMap();
                map.put("AES_IV", strEncodeToString2);
                map.put("AES_KEY", strEncodeToString);
                k.a(map);
            } else {
                f10414a = Base64.decode(c(), 0);
                byte[] bArrDecode = Base64.decode(strB, 0);
                Key key2 = keyStore.getKey("CMCC_SDK_V1", null);
                if (key2 == null) {
                    return null;
                }
                if (key2 instanceof SecretKey) {
                    cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                    cipher.init(2, key2, new IvParameterSpec(f10414a));
                    str = "KeystoreUtil";
                    str2 = "使用aes";
                } else {
                    if (!(key2 instanceof PrivateKey)) {
                        return null;
                    }
                    cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA256AndMGF1Padding");
                    cipher.init(2, key2);
                    str = "KeystoreUtil";
                    str2 = "使用rsa";
                }
                c.b(str, str2);
                bArrDoFinal = cipher.doFinal(bArrDecode);
                StringBuilder sb = new StringBuilder();
                sb.append("是否解密出秘钥：");
                sb.append(TextUtils.isEmpty(Base64.encodeToString(bArrDoFinal, 0)) ? false : true);
                c.b("KeystoreUtil", sb.toString());
            }
            return bArrDoFinal;
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static String c() {
        return k.b("AES_IV", "");
    }
}
