package cn.hutool.crypto;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class KeyUtil {
    public static final String CERT_TYPE_X509 = "X.509";
    public static final int DEFAULT_KEY_SIZE = 1024;
    public static final String KEY_TYPE_JCEKS = "jceks";
    public static final String KEY_TYPE_JKS = "JKS";
    public static final String KEY_TYPE_PKCS12 = "pkcs12";
    public static final String SM2_DEFAULT_CURVE = "sm2p256v1";

    public static PublicKey decodeECPoint(String str, String str2) {
        return BCUtil.decodeECPoint(str, str2);
    }

    public static byte[] encodeECPublicKey(PublicKey publicKey) {
        return BCUtil.encodeECPublicKey(publicKey);
    }

    public static SecretKey generateDESKey(String str, byte[] bArr) {
        if (CharSequenceUtil.isBlank(str) || !str.startsWith("DES")) {
            throw new CryptoException("Algorithm [{}] is not a DES algorithm!", str);
        }
        if (bArr == null) {
            return generateKey(str);
        }
        try {
            return generateKey(str, str.startsWith("DESede") ? new DESedeKeySpec(bArr) : new DESKeySpec(bArr));
        } catch (InvalidKeyException e2) {
            throw new CryptoException(e2);
        }
    }

    public static SecretKey generateKey(String str) {
        return generateKey(str, -1);
    }

    public static KeyPair generateKeyPair(String str) {
        return generateKeyPair(str, "ECIES".equalsIgnoreCase(str) ? 256 : 1024);
    }

    public static SecretKey generatePBEKey(String str, char[] cArr) {
        if (CharSequenceUtil.isBlank(str) || !str.startsWith("PBE")) {
            throw new CryptoException("Algorithm [{}] is not a PBE algorithm!", str);
        }
        if (cArr == null) {
            cArr = RandomUtil.randomString(32).toCharArray();
        }
        return generateKey(str, new PBEKeySpec(cArr));
    }

    public static PrivateKey generatePrivateKey(String str, byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return generatePrivateKey(str, new PKCS8EncodedKeySpec(bArr));
    }

    public static PublicKey generatePublicKey(String str, byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return generatePublicKey(str, new X509EncodedKeySpec(bArr));
    }

    public static PrivateKey generateRSAPrivateKey(byte[] bArr) {
        return generatePrivateKey(AsymmetricAlgorithm.RSA.getValue(), bArr);
    }

    public static PublicKey generateRSAPublicKey(byte[] bArr) {
        return generatePublicKey(AsymmetricAlgorithm.RSA.getValue(), bArr);
    }

    public static String getAlgorithmAfterWith(String str) throws IllegalArgumentException {
        Assert.notNull(str, "algorithm must be not null !", new Object[0]);
        if (CharSequenceUtil.startWithIgnoreCase(str, "ECIESWith")) {
            return "EC";
        }
        int iLastIndexOfIgnoreCase = CharSequenceUtil.lastIndexOfIgnoreCase(str, "with");
        if (iLastIndexOfIgnoreCase > 0) {
            str = CharSequenceUtil.subSuf(str, iLastIndexOfIgnoreCase + 4);
        }
        return ("ECDSA".equalsIgnoreCase(str) || "SM2".equalsIgnoreCase(str) || "ECIES".equalsIgnoreCase(str)) ? "EC" : str;
    }

    public static Certificate getCertificate(KeyStore keyStore, String str) {
        try {
            return keyStore.getCertificate(str);
        } catch (Exception e2) {
            throw new CryptoException(e2);
        }
    }

    public static CertificateFactory getCertificateFactory(String str) {
        Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();
        try {
            return provider == null ? CertificateFactory.getInstance(str) : CertificateFactory.getInstance(str, provider);
        } catch (CertificateException e2) {
            throw new CryptoException(e2);
        }
    }

    public static KeyFactory getKeyFactory(String str) {
        Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();
        try {
            return provider == null ? KeyFactory.getInstance(getMainAlgorithm(str)) : KeyFactory.getInstance(getMainAlgorithm(str), provider);
        } catch (NoSuchAlgorithmException e2) {
            throw new CryptoException(e2);
        }
    }

    public static KeyGenerator getKeyGenerator(String str) {
        Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();
        try {
            return provider == null ? KeyGenerator.getInstance(getMainAlgorithm(str)) : KeyGenerator.getInstance(getMainAlgorithm(str), provider);
        } catch (NoSuchAlgorithmException e2) {
            throw new CryptoException(e2);
        }
    }

    public static KeyPair getKeyPair(String str, InputStream inputStream, char[] cArr, String str2) {
        return getKeyPair(readKeyStore(str, inputStream, cArr), cArr, str2);
    }

    public static KeyPairGenerator getKeyPairGenerator(String str) {
        Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();
        try {
            return provider == null ? KeyPairGenerator.getInstance(getMainAlgorithm(str)) : KeyPairGenerator.getInstance(getMainAlgorithm(str), provider);
        } catch (NoSuchAlgorithmException e2) {
            throw new CryptoException(e2);
        }
    }

    public static KeyStore getKeyStore(String str) {
        Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();
        try {
            return provider == null ? KeyStore.getInstance(str) : KeyStore.getInstance(str, provider);
        } catch (KeyStoreException e2) {
            throw new CryptoException(e2);
        }
    }

    public static String getMainAlgorithm(String str) throws IllegalArgumentException {
        Assert.notBlank(str, "Algorithm must be not blank!", new Object[0]);
        int iIndexOf = str.indexOf(47);
        return iIndexOf > 0 ? str.substring(0, iIndexOf) : str;
    }

    public static PublicKey getRSAPublicKey(PrivateKey privateKey) {
        if (!(privateKey instanceof RSAPrivateCrtKey)) {
            return null;
        }
        RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) privateKey;
        return getRSAPublicKey(rSAPrivateCrtKey.getModulus(), rSAPrivateCrtKey.getPublicExponent());
    }

    public static SecretKeyFactory getSecretKeyFactory(String str) {
        Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();
        try {
            return provider == null ? SecretKeyFactory.getInstance(getMainAlgorithm(str)) : SecretKeyFactory.getInstance(getMainAlgorithm(str), provider);
        } catch (NoSuchAlgorithmException e2) {
            throw new CryptoException(e2);
        }
    }

    public static Certificate readCertificate(String str, InputStream inputStream, char[] cArr, String str2) {
        try {
            return readKeyStore(str, inputStream, cArr).getCertificate(str2);
        } catch (KeyStoreException e2) {
            throw new CryptoException(e2);
        }
    }

    public static KeyStore readJKSKeyStore(File file, char[] cArr) {
        return readKeyStore(KEY_TYPE_JKS, file, cArr);
    }

    public static KeyStore readKeyStore(String str, File file, char[] cArr) throws Throwable {
        BufferedInputStream inputStream;
        try {
            inputStream = FileUtil.getInputStream(file);
        } catch (Throwable th) {
            th = th;
            inputStream = null;
        }
        try {
            KeyStore keyStore = readKeyStore(str, inputStream, cArr);
            IoUtil.close((Closeable) inputStream);
            return keyStore;
        } catch (Throwable th2) {
            th = th2;
            IoUtil.close((Closeable) inputStream);
            throw th;
        }
    }

    public static KeyStore readPKCS12KeyStore(File file, char[] cArr) {
        return readKeyStore(KEY_TYPE_PKCS12, file, cArr);
    }

    public static PublicKey readPublicKeyFromCert(InputStream inputStream) {
        Certificate x509Certificate = readX509Certificate(inputStream);
        if (x509Certificate != null) {
            return x509Certificate.getPublicKey();
        }
        return null;
    }

    public static Certificate readX509Certificate(InputStream inputStream, char[] cArr, String str) {
        return readCertificate(CERT_TYPE_X509, inputStream, cArr, str);
    }

    public static String toBase64(Key key) {
        return Base64.encode(key.getEncoded());
    }

    public static PublicKey decodeECPoint(byte[] bArr, String str) {
        return BCUtil.decodeECPoint(bArr, str);
    }

    public static SecretKey generateKey(String str, int i2) {
        return generateKey(str, i2, null);
    }

    public static PrivateKey generatePrivateKey(String str, KeySpec keySpec) {
        if (keySpec == null) {
            return null;
        }
        try {
            return getKeyFactory(getAlgorithmAfterWith(str)).generatePrivate(keySpec);
        } catch (Exception e2) {
            throw new CryptoException(e2);
        }
    }

    public static PublicKey generatePublicKey(String str, KeySpec keySpec) {
        if (keySpec == null) {
            return null;
        }
        try {
            return getKeyFactory(getAlgorithmAfterWith(str)).generatePublic(keySpec);
        } catch (Exception e2) {
            throw new CryptoException(e2);
        }
    }

    public static KeyStore readJKSKeyStore(InputStream inputStream, char[] cArr) {
        return readKeyStore(KEY_TYPE_JKS, inputStream, cArr);
    }

    public static KeyStore readPKCS12KeyStore(InputStream inputStream, char[] cArr) {
        return readKeyStore(KEY_TYPE_PKCS12, inputStream, cArr);
    }

    public static Certificate readX509Certificate(InputStream inputStream) {
        return readCertificate(CERT_TYPE_X509, inputStream);
    }

    public static SecretKey generateKey(String str, int i2, SecureRandom secureRandom) throws IllegalArgumentException {
        String mainAlgorithm = getMainAlgorithm(str);
        KeyGenerator keyGenerator = getKeyGenerator(mainAlgorithm);
        if (i2 <= 0 && SymmetricAlgorithm.AES.getValue().equals(mainAlgorithm)) {
            i2 = 128;
        }
        if (i2 > 0) {
            if (secureRandom == null) {
                keyGenerator.init(i2);
            } else {
                keyGenerator.init(i2, secureRandom);
            }
        }
        return keyGenerator.generateKey();
    }

    public static KeyPair generateKeyPair(String str, int i2) {
        return generateKeyPair(str, i2, (byte[]) null);
    }

    public static KeyPair getKeyPair(KeyStore keyStore, char[] cArr, String str) {
        try {
            return new KeyPair(keyStore.getCertificate(str).getPublicKey(), (PrivateKey) keyStore.getKey(str, cArr));
        } catch (Exception e2) {
            throw new CryptoException(e2);
        }
    }

    public static KeyPair generateKeyPair(String str, int i2, byte[] bArr) {
        if ("SM2".equalsIgnoreCase(str)) {
            return generateKeyPair(str, i2, bArr, new ECGenParameterSpec("sm2p256v1"));
        }
        return generateKeyPair(str, i2, bArr, (AlgorithmParameterSpec[]) null);
    }

    public static PublicKey getRSAPublicKey(String str, String str2) {
        return getRSAPublicKey(new BigInteger(str, 16), new BigInteger(str2, 16));
    }

    public static Certificate readCertificate(String str, InputStream inputStream) {
        try {
            return getCertificateFactory(str).generateCertificate(inputStream);
        } catch (CertificateException e2) {
            throw new CryptoException(e2);
        }
    }

    public static PrivateKey generatePrivateKey(KeyStore keyStore, String str, char[] cArr) {
        try {
            return (PrivateKey) keyStore.getKey(str, cArr);
        } catch (Exception e2) {
            throw new CryptoException(e2);
        }
    }

    public static PublicKey getRSAPublicKey(BigInteger bigInteger, BigInteger bigInteger2) {
        try {
            return getKeyFactory("RSA").generatePublic(new RSAPublicKeySpec(bigInteger, bigInteger2));
        } catch (InvalidKeySpecException e2) {
            throw new CryptoException(e2);
        }
    }

    public static KeyStore readKeyStore(String str, InputStream inputStream, char[] cArr) throws NoSuchAlgorithmException, IOException, CertificateException {
        KeyStore keyStore = getKeyStore(str);
        try {
            keyStore.load(inputStream, cArr);
            return keyStore;
        } catch (Exception e2) {
            throw new CryptoException(e2);
        }
    }

    public static KeyPair generateKeyPair(String str, AlgorithmParameterSpec algorithmParameterSpec) {
        return generateKeyPair(str, (byte[]) null, algorithmParameterSpec);
    }

    public static SecretKey generateKey(String str, byte[] bArr) throws IllegalArgumentException {
        Assert.notBlank(str, "Algorithm is blank!", new Object[0]);
        if (str.startsWith("PBE")) {
            return generatePBEKey(str, bArr == null ? null : StrUtil.utf8Str(bArr).toCharArray());
        }
        if (str.startsWith("DES")) {
            return generateDESKey(str, bArr);
        }
        return bArr == null ? generateKey(str) : new SecretKeySpec(bArr, str);
    }

    public static KeyPair generateKeyPair(String str, byte[] bArr, AlgorithmParameterSpec algorithmParameterSpec) {
        return generateKeyPair(str, 1024, bArr, algorithmParameterSpec);
    }

    public static KeyPair generateKeyPair(String str, int i2, byte[] bArr, AlgorithmParameterSpec... algorithmParameterSpecArr) {
        return generateKeyPair(str, i2, RandomUtil.createSecureRandom(bArr), algorithmParameterSpecArr);
    }

    public static KeyPair generateKeyPair(String str, int i2, SecureRandom secureRandom, AlgorithmParameterSpec... algorithmParameterSpecArr) throws IllegalArgumentException, InvalidAlgorithmParameterException {
        String algorithmAfterWith = getAlgorithmAfterWith(str);
        KeyPairGenerator keyPairGenerator = getKeyPairGenerator(algorithmAfterWith);
        if (i2 > 0) {
            if ("EC".equalsIgnoreCase(algorithmAfterWith) && i2 > 256) {
                i2 = 256;
            }
            if (secureRandom != null) {
                keyPairGenerator.initialize(i2, secureRandom);
            } else {
                keyPairGenerator.initialize(i2);
            }
        }
        if (ArrayUtil.isNotEmpty((Object[]) algorithmParameterSpecArr)) {
            for (AlgorithmParameterSpec algorithmParameterSpec : algorithmParameterSpecArr) {
                if (algorithmParameterSpec != null) {
                    if (secureRandom != null) {
                        try {
                            keyPairGenerator.initialize(algorithmParameterSpec, secureRandom);
                        } catch (InvalidAlgorithmParameterException e2) {
                            throw new CryptoException(e2);
                        }
                    } else {
                        keyPairGenerator.initialize(algorithmParameterSpec);
                    }
                }
            }
        }
        return keyPairGenerator.generateKeyPair();
    }

    public static SecretKey generateKey(String str, KeySpec keySpec) {
        try {
            return getSecretKeyFactory(str).generateSecret(keySpec);
        } catch (InvalidKeySpecException e2) {
            throw new CryptoException(e2);
        }
    }
}
