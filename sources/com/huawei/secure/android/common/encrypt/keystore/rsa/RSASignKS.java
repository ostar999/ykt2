package com.huawei.secure.android.common.encrypt.keystore.rsa;

import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.huawei.secure.android.common.encrypt.utils.b;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/* loaded from: classes4.dex */
public abstract class RSASignKS {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8250a = "RSASignKS";

    /* renamed from: b, reason: collision with root package name */
    private static final String f8251b = "AndroidKeyStore";

    /* renamed from: c, reason: collision with root package name */
    private static final String f8252c = "SHA256withRSA/PSS";

    /* renamed from: d, reason: collision with root package name */
    private static final String f8253d = "";

    /* renamed from: e, reason: collision with root package name */
    private static final int f8254e = 2048;

    /* renamed from: f, reason: collision with root package name */
    private static final int f8255f = 3072;

    private static byte[] a(String str, byte[] bArr, boolean z2) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException, KeyStoreException, CertificateException {
        byte[] bArr2 = new byte[0];
        if (TextUtils.isEmpty(str) || bArr == null) {
            b.b(f8250a, "alias or content is null");
            return bArr2;
        }
        if (!isBuildVersionHigherThan22()) {
            b.b(f8250a, "sdk version is too low");
            return bArr2;
        }
        KeyStore.Entry entryB = b(str, z2);
        if (!(entryB instanceof KeyStore.PrivateKeyEntry)) {
            b.b(f8250a, "Not an instance of a PrivateKeyEntry");
            return bArr2;
        }
        try {
            Signature signature = Signature.getInstance(f8252c);
            signature.initSign(((KeyStore.PrivateKeyEntry) entryB).getPrivateKey());
            signature.update(bArr);
            return signature.sign();
        } catch (InvalidKeyException e2) {
            b.b(f8250a, "InvalidKeyException: " + e2.getMessage());
            return bArr2;
        } catch (NoSuchAlgorithmException e3) {
            b.b(f8250a, "NoSuchAlgorithmException: " + e3.getMessage());
            return bArr2;
        } catch (SignatureException e4) {
            b.b(f8250a, "SignatureException: " + e4.getMessage());
            return bArr2;
        } catch (Exception e5) {
            b.b(f8250a, "Exception: " + e5.getMessage());
            return bArr2;
        }
    }

    private static KeyStore.Entry b(String str, boolean z2) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        if (!a(str)) {
            a(str, z2);
        }
        try {
            KeyStore keyStore = KeyStore.getInstance(f8251b);
            keyStore.load(null);
            return keyStore.getEntry(str, null);
        } catch (IOException e2) {
            b.b(f8250a, "IOException: " + e2.getMessage());
            return null;
        } catch (KeyStoreException e3) {
            b.b(f8250a, "KeyStoreException: " + e3.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e4) {
            b.b(f8250a, "NoSuchAlgorithmException: " + e4.getMessage());
            return null;
        } catch (UnrecoverableEntryException e5) {
            b.b(f8250a, "UnrecoverableEntryException: " + e5.getMessage());
            return null;
        } catch (CertificateException e6) {
            b.b(f8250a, "CertificateException: " + e6.getMessage());
            return null;
        }
    }

    public static boolean isBuildVersionHigherThan22() {
        return true;
    }

    @Deprecated
    public static String sign(String str, String str2) {
        try {
            return Base64.encodeToString(sign(str, str2.getBytes("UTF-8")), 0);
        } catch (UnsupportedEncodingException e2) {
            Log.e(f8250a, "sign UnsupportedEncodingException : " + e2.getMessage());
            return "";
        }
    }

    public static String signNew(String str, String str2) {
        try {
            return Base64.encodeToString(signNew(str, str2.getBytes("UTF-8")), 0);
        } catch (UnsupportedEncodingException e2) {
            Log.e(f8250a, "sign UnsupportedEncodingException : " + e2.getMessage());
            return "";
        }
    }

    @Deprecated
    public static boolean verifySign(String str, String str2, String str3) {
        try {
            return verifySign(str, str2.getBytes("UTF-8"), Base64.decode(str3, 0));
        } catch (UnsupportedEncodingException e2) {
            Log.e(f8250a, "verifySign UnsupportedEncodingException: " + e2.getMessage());
            return false;
        } catch (Exception e3) {
            b.b(f8250a, "base64 decode Exception" + e3.getMessage());
            return false;
        }
    }

    public static boolean verifySignNew(String str, String str2, String str3) {
        try {
            return verifySignNew(str, str2.getBytes("UTF-8"), Base64.decode(str3, 0));
        } catch (UnsupportedEncodingException e2) {
            Log.e(f8250a, "verifySign UnsupportedEncodingException: " + e2.getMessage());
            return false;
        } catch (Exception e3) {
            b.b(f8250a, "base64 decode Exception" + e3.getMessage());
            return false;
        }
    }

    @Deprecated
    public static byte[] sign(String str, byte[] bArr) {
        return a(str, bArr, false);
    }

    public static byte[] signNew(String str, byte[] bArr) {
        return a(str, bArr, true);
    }

    @Deprecated
    public static boolean verifySign(String str, byte[] bArr, byte[] bArr2) {
        return a(str, bArr, bArr2, false);
    }

    public static boolean verifySignNew(String str, byte[] bArr, byte[] bArr2) {
        return a(str, bArr, bArr2, true);
    }

    private static boolean a(String str, byte[] bArr, byte[] bArr2, boolean z2) throws NoSuchAlgorithmException, SignatureException, IOException, InvalidKeyException, KeyStoreException, CertificateException {
        if (!TextUtils.isEmpty(str) && bArr != null && bArr2 != null) {
            if (!isBuildVersionHigherThan22()) {
                b.b(f8250a, "sdk version is too low");
                return false;
            }
            KeyStore.Entry entryB = b(str, z2);
            if (!(entryB instanceof KeyStore.PrivateKeyEntry)) {
                b.b(f8250a, "Not an instance of a PrivateKeyEntry");
                return false;
            }
            try {
                Signature signature = Signature.getInstance(f8252c);
                signature.initVerify(((KeyStore.PrivateKeyEntry) entryB).getCertificate());
                signature.update(bArr);
                return signature.verify(bArr2);
            } catch (InvalidKeyException e2) {
                b.b(f8250a, "InvalidKeyException: " + e2.getMessage());
                return false;
            } catch (NoSuchAlgorithmException e3) {
                b.b(f8250a, "NoSuchAlgorithmException: " + e3.getMessage());
                return false;
            } catch (SignatureException e4) {
                b.b(f8250a, "SignatureException: " + e4.getMessage());
                return false;
            } catch (Exception e5) {
                b.b(f8250a, "Exception: " + e5.getMessage());
                return false;
            }
        }
        b.b(f8250a, "alias or content or sign value is null");
        return false;
    }

    private static synchronized KeyPair a(String str, boolean z2) {
        KeyPair keyPairGenerateKeyPair = null;
        if (a(str)) {
            b.b(f8250a, "Key pair exits");
            return null;
        }
        try {
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", f8251b);
                if (!z2) {
                    keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(str, 12).setDigests("SHA-256", "SHA-512").setSignaturePaddings("PSS").setKeySize(2048).build());
                } else {
                    keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(str, 12).setDigests("SHA-256", "SHA-512").setSignaturePaddings("PSS").setKeySize(3072).build());
                }
                keyPairGenerateKeyPair = keyPairGenerator.generateKeyPair();
            } catch (InvalidAlgorithmParameterException e2) {
                b.b(f8250a, "InvalidAlgorithmParameterException: " + e2.getMessage());
            } catch (NoSuchProviderException e3) {
                b.b(f8250a, "NoSuchProviderException: " + e3.getMessage());
            }
        } catch (NoSuchAlgorithmException e4) {
            b.b(f8250a, "NoSuchAlgorithmException: " + e4.getMessage());
        }
        return keyPairGenerateKeyPair;
    }

    private static boolean a(String str) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException {
        try {
            KeyStore keyStore = KeyStore.getInstance(f8251b);
            keyStore.load(null);
            return keyStore.getKey(str, null) != null;
        } catch (IOException e2) {
            b.b(f8250a, "IOException: " + e2.getMessage());
            return false;
        } catch (KeyStoreException e3) {
            b.b(f8250a, "KeyStoreException: " + e3.getMessage());
            return false;
        } catch (NoSuchAlgorithmException e4) {
            b.b(f8250a, "NoSuchAlgorithmException: " + e4.getMessage());
            return false;
        } catch (UnrecoverableKeyException e5) {
            b.b(f8250a, "UnrecoverableKeyException: " + e5.getMessage());
            return false;
        } catch (CertificateException e6) {
            b.b(f8250a, "CertificateException: " + e6.getMessage());
            return false;
        }
    }
}
