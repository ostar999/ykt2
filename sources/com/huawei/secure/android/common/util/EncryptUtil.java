package com.huawei.secure.android.common.util;

import android.os.Build;
import android.util.Base64;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* loaded from: classes4.dex */
public class EncryptUtil {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8431a = "EncryptUtil";

    /* renamed from: b, reason: collision with root package name */
    private static final String f8432b = "RSA";

    @Deprecated
    public static byte[] generateSecureRandom(int i2) throws NoSuchAlgorithmException {
        byte[] bArr = new byte[i2];
        try {
        } catch (NoSuchAlgorithmException unused) {
            LogsUtil.e(f8431a, "getSecureRandomBytes: NoSuchAlgorithmException");
        }
        SecureRandom instanceStrong = Build.VERSION.SDK_INT >= 26 ? SecureRandom.getInstanceStrong() : null;
        if (instanceStrong == null) {
            try {
                instanceStrong = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException unused2) {
                LogsUtil.e(f8431a, "getSecureRandomBytes getInstance: NoSuchAlgorithmException");
                return new byte[0];
            } catch (Exception e2) {
                LogsUtil.e(f8431a, "getSecureRandomBytes getInstance: exception : " + e2.getMessage());
                return new byte[0];
            }
        }
        instanceStrong.nextBytes(bArr);
        return bArr;
    }

    @Deprecated
    public static String generateSecureRandomStr(int i2) {
        return HexUtil.byteArray2HexStr(generateSecureRandom(i2));
    }

    @Deprecated
    public static PrivateKey getPrivateKey(String str) {
        try {
            try {
                return KeyFactory.getInstance(f8432b).generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(str, 0)));
            } catch (GeneralSecurityException e2) {
                LogsUtil.e(f8431a, "load Key Exception:" + e2.getMessage(), true);
                return null;
            }
        } catch (IllegalArgumentException unused) {
            LogsUtil.e(f8431a, "base64 decode IllegalArgumentException", true);
            return null;
        } catch (Exception unused2) {
            LogsUtil.e(f8431a, "base64 decode Exception", true);
            return null;
        }
    }

    @Deprecated
    public static RSAPublicKey getPublicKey(String str) {
        try {
            try {
                return (RSAPublicKey) KeyFactory.getInstance(f8432b).generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
            } catch (GeneralSecurityException e2) {
                LogsUtil.e(f8431a, "load Key Exception:" + e2.getMessage(), true);
                return null;
            }
        } catch (IllegalArgumentException unused) {
            LogsUtil.e(f8431a, "base64 decode IllegalArgumentException", true);
            return null;
        } catch (Exception unused2) {
            LogsUtil.e(f8431a, "base64 decode Exception", true);
            return null;
        }
    }
}
