package cn.hutool.crypto;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.DESede;
import cn.hutool.crypto.symmetric.PBKDF2;
import cn.hutool.crypto.symmetric.RC4;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.crypto.symmetric.ZUC;
import cn.hutool.crypto.symmetric.fpe.FPE;
import java.io.File;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import org.bouncycastle.crypto.AlphabetMapper;
import org.eclipse.jetty.util.security.Constraint;

/* loaded from: classes.dex */
public class SecureUtil {
    public static final int DEFAULT_KEY_SIZE = 1024;

    public static void addProvider(Provider provider) {
        Security.insertProviderAt(provider, 0);
    }

    public static AES aes() {
        return new AES();
    }

    public static Cipher createCipher(String str) {
        Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();
        try {
            return provider == null ? Cipher.getInstance(str) : Cipher.getInstance(str, provider);
        } catch (Exception e2) {
            throw new CryptoException(e2);
        }
    }

    public static MessageDigest createJdkMessageDigest(String str) {
        try {
            return MessageDigest.getInstance(str);
        } catch (NoSuchAlgorithmException e2) {
            throw new CryptoException(e2);
        }
    }

    public static Mac createMac(String str) {
        Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();
        try {
            return provider == null ? Mac.getInstance(str) : Mac.getInstance(str, provider);
        } catch (NoSuchAlgorithmException e2) {
            throw new CryptoException(e2);
        }
    }

    public static MessageDigest createMessageDigest(String str) {
        Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();
        try {
            return provider == null ? MessageDigest.getInstance(str) : MessageDigest.getInstance(str, provider);
        } catch (NoSuchAlgorithmException e2) {
            throw new CryptoException(e2);
        }
    }

    public static Signature createSignature(String str) {
        Provider provider = GlobalBouncyCastleProvider.INSTANCE.getProvider();
        try {
            return provider == null ? Signature.getInstance(str) : Signature.getInstance(str, provider);
        } catch (NoSuchAlgorithmException e2) {
            throw new CryptoException(e2);
        }
    }

    public static byte[] decode(String str) {
        return Validator.isHex(str) ? HexUtil.decodeHex(str) : Base64.decode(str);
    }

    public static DES des() {
        return new DES();
    }

    public static DESede desede() {
        return new DESede();
    }

    public static void disableBouncyCastle() {
        GlobalBouncyCastleProvider.setUseBouncyCastle(false);
    }

    public static FPE fpe(FPE.FPEMode fPEMode, byte[] bArr, AlphabetMapper alphabetMapper, byte[] bArr2) {
        return new FPE(fPEMode, bArr, alphabetMapper, bArr2);
    }

    public static String generateAlgorithm(AsymmetricAlgorithm asymmetricAlgorithm, DigestAlgorithm digestAlgorithm) {
        return CharSequenceUtil.format("{}with{}", digestAlgorithm == null ? Constraint.NONE : digestAlgorithm.name(), asymmetricAlgorithm.getValue());
    }

    public static SecretKey generateDESKey(String str, byte[] bArr) {
        return KeyUtil.generateDESKey(str, bArr);
    }

    public static SecretKey generateKey(String str) {
        return KeyUtil.generateKey(str);
    }

    public static KeyPair generateKeyPair(String str) {
        return KeyUtil.generateKeyPair(str);
    }

    public static SecretKey generatePBEKey(String str, char[] cArr) {
        return KeyUtil.generatePBEKey(str, cArr);
    }

    public static PrivateKey generatePrivateKey(String str, byte[] bArr) {
        return KeyUtil.generatePrivateKey(str, bArr);
    }

    public static PublicKey generatePublicKey(String str, byte[] bArr) {
        return KeyUtil.generatePublicKey(str, bArr);
    }

    public static Signature generateSignature(AsymmetricAlgorithm asymmetricAlgorithm, DigestAlgorithm digestAlgorithm) {
        try {
            return Signature.getInstance(generateAlgorithm(asymmetricAlgorithm, digestAlgorithm));
        } catch (NoSuchAlgorithmException e2) {
            throw new CryptoException(e2);
        }
    }

    public static String getAlgorithmAfterWith(String str) {
        return KeyUtil.getAlgorithmAfterWith(str);
    }

    public static Certificate getCertificate(KeyStore keyStore, String str) {
        return KeyUtil.getCertificate(keyStore, str);
    }

    public static HMac hmac(HmacAlgorithm hmacAlgorithm, String str) {
        return hmac(hmacAlgorithm, CharSequenceUtil.isNotEmpty(str) ? CharSequenceUtil.utf8Bytes(str) : null);
    }

    public static HMac hmacMd5(String str) {
        return hmacMd5(CharSequenceUtil.isNotEmpty(str) ? CharSequenceUtil.utf8Bytes(str) : null);
    }

    public static HMac hmacSha1(String str) {
        return hmacSha1(CharSequenceUtil.isNotEmpty(str) ? CharSequenceUtil.utf8Bytes(str) : null);
    }

    public static HMac hmacSha256(String str) {
        return hmacSha256(CharSequenceUtil.isNotEmpty(str) ? CharSequenceUtil.utf8Bytes(str) : null);
    }

    public static MD5 md5() {
        return new MD5();
    }

    public static String pbkdf2(char[] cArr, byte[] bArr) {
        return new PBKDF2().encryptHex(cArr, bArr);
    }

    public static RC4 rc4(String str) {
        return new RC4(str);
    }

    public static Certificate readCertificate(String str, InputStream inputStream, char[] cArr, String str2) {
        return KeyUtil.readCertificate(str, inputStream, cArr, str2);
    }

    public static KeyStore readJKSKeyStore(InputStream inputStream, char[] cArr) {
        return KeyUtil.readJKSKeyStore(inputStream, cArr);
    }

    public static KeyStore readKeyStore(String str, InputStream inputStream, char[] cArr) {
        return KeyUtil.readKeyStore(str, inputStream, cArr);
    }

    public static Certificate readX509Certificate(InputStream inputStream, char[] cArr, String str) {
        return KeyUtil.readX509Certificate(inputStream, cArr, str);
    }

    public static RSA rsa() {
        return new RSA();
    }

    public static Digester sha1() {
        return new Digester(DigestAlgorithm.SHA1);
    }

    public static Digester sha256() {
        return new Digester(DigestAlgorithm.SHA256);
    }

    public static Sign sign(SignAlgorithm signAlgorithm) {
        return SignUtil.sign(signAlgorithm);
    }

    public static String signParams(SymmetricCrypto symmetricCrypto, Map<?, ?> map, String... strArr) {
        return SignUtil.signParams(symmetricCrypto, map, strArr);
    }

    public static String signParamsMd5(Map<?, ?> map, String... strArr) {
        return SignUtil.signParamsMd5(map, strArr);
    }

    public static String signParamsSha1(Map<?, ?> map, String... strArr) {
        return SignUtil.signParamsSha1(map, strArr);
    }

    public static String signParamsSha256(Map<?, ?> map, String... strArr) {
        return SignUtil.signParamsSha256(map, strArr);
    }

    public static ZUC zuc128(byte[] bArr, byte[] bArr2) {
        return new ZUC(ZUC.ZUCAlgorithm.ZUC_128, bArr, bArr2);
    }

    public static ZUC zuc256(byte[] bArr, byte[] bArr2) {
        return new ZUC(ZUC.ZUCAlgorithm.ZUC_256, bArr, bArr2);
    }

    public static AES aes(byte[] bArr) {
        return new AES(bArr);
    }

    public static DES des(byte[] bArr) {
        return new DES(bArr);
    }

    public static DESede desede(byte[] bArr) {
        return new DESede(bArr);
    }

    public static SecretKey generateKey(String str, int i2) {
        return KeyUtil.generateKey(str, i2);
    }

    public static KeyPair generateKeyPair(String str, int i2) {
        return KeyUtil.generateKeyPair(str, i2);
    }

    public static PrivateKey generatePrivateKey(String str, KeySpec keySpec) {
        return KeyUtil.generatePrivateKey(str, keySpec);
    }

    public static PublicKey generatePublicKey(String str, KeySpec keySpec) {
        return KeyUtil.generatePublicKey(str, keySpec);
    }

    public static HMac hmac(HmacAlgorithm hmacAlgorithm, byte[] bArr) {
        if (PrimitiveArrayUtil.isEmpty(bArr)) {
            bArr = generateKey(hmacAlgorithm.getValue()).getEncoded();
        }
        return new HMac(hmacAlgorithm, bArr);
    }

    public static HMac hmacMd5(byte[] bArr) {
        if (PrimitiveArrayUtil.isEmpty(bArr)) {
            bArr = generateKey(HmacAlgorithm.HmacMD5.getValue()).getEncoded();
        }
        return new HMac(HmacAlgorithm.HmacMD5, bArr);
    }

    public static HMac hmacSha1(byte[] bArr) {
        if (PrimitiveArrayUtil.isEmpty(bArr)) {
            bArr = generateKey(HmacAlgorithm.HmacMD5.getValue()).getEncoded();
        }
        return new HMac(HmacAlgorithm.HmacSHA1, bArr);
    }

    public static HMac hmacSha256(byte[] bArr) {
        if (PrimitiveArrayUtil.isEmpty(bArr)) {
            bArr = generateKey(HmacAlgorithm.HmacMD5.getValue()).getEncoded();
        }
        return new HMac(HmacAlgorithm.HmacSHA256, bArr);
    }

    public static String md5(String str) {
        return new MD5().digestHex(str);
    }

    public static Certificate readCertificate(String str, InputStream inputStream) {
        return KeyUtil.readCertificate(str, inputStream);
    }

    public static Certificate readX509Certificate(InputStream inputStream) {
        return KeyUtil.readX509Certificate(inputStream);
    }

    public static RSA rsa(String str, String str2) {
        return new RSA(str, str2);
    }

    public static String sha1(String str) {
        return new Digester(DigestAlgorithm.SHA1).digestHex(str);
    }

    public static String sha256(String str) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(str);
    }

    public static Sign sign(SignAlgorithm signAlgorithm, String str, String str2) {
        return SignUtil.sign(signAlgorithm, str, str2);
    }

    public static String signParams(SymmetricCrypto symmetricCrypto, Map<?, ?> map, String str, String str2, boolean z2, String... strArr) {
        return SignUtil.signParams(symmetricCrypto, map, str, str2, z2, strArr);
    }

    public static SecretKey generateKey(String str, byte[] bArr) {
        return KeyUtil.generateKey(str, bArr);
    }

    public static KeyPair generateKeyPair(String str, int i2, byte[] bArr) {
        return KeyUtil.generateKeyPair(str, i2, bArr);
    }

    public static PrivateKey generatePrivateKey(KeyStore keyStore, String str, char[] cArr) {
        return KeyUtil.generatePrivateKey(keyStore, str, cArr);
    }

    public static String md5(InputStream inputStream) {
        return new MD5().digestHex(inputStream);
    }

    public static RSA rsa(byte[] bArr, byte[] bArr2) {
        return new RSA(bArr, bArr2);
    }

    public static String sha1(InputStream inputStream) {
        return new Digester(DigestAlgorithm.SHA1).digestHex(inputStream);
    }

    public static String sha256(InputStream inputStream) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(inputStream);
    }

    public static Sign sign(SignAlgorithm signAlgorithm, byte[] bArr, byte[] bArr2) {
        return SignUtil.sign(signAlgorithm, bArr, bArr2);
    }

    public static String signParams(DigestAlgorithm digestAlgorithm, Map<?, ?> map, String... strArr) {
        return SignUtil.signParams(digestAlgorithm, map, strArr);
    }

    public static SecretKey generateKey(String str, KeySpec keySpec) {
        return KeyUtil.generateKey(str, keySpec);
    }

    public static KeyPair generateKeyPair(String str, AlgorithmParameterSpec algorithmParameterSpec) {
        return KeyUtil.generateKeyPair(str, algorithmParameterSpec);
    }

    public static String md5(File file) {
        return new MD5().digestHex(file);
    }

    public static String sha1(File file) {
        return new Digester(DigestAlgorithm.SHA1).digestHex(file);
    }

    public static String sha256(File file) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(file);
    }

    public static String signParams(DigestAlgorithm digestAlgorithm, Map<?, ?> map, String str, String str2, boolean z2, String... strArr) {
        return SignUtil.signParams(digestAlgorithm, map, str, str2, z2, strArr);
    }

    public static KeyPair generateKeyPair(String str, byte[] bArr, AlgorithmParameterSpec algorithmParameterSpec) {
        return KeyUtil.generateKeyPair(str, bArr, algorithmParameterSpec);
    }

    public static HMac hmac(HmacAlgorithm hmacAlgorithm, SecretKey secretKey) {
        if (ObjectUtil.isNull(secretKey)) {
            secretKey = generateKey(hmacAlgorithm.getValue());
        }
        return new HMac(hmacAlgorithm, secretKey);
    }

    public static HMac hmacMd5() {
        return new HMac(HmacAlgorithm.HmacMD5);
    }

    public static HMac hmacSha1() {
        return new HMac(HmacAlgorithm.HmacSHA1);
    }

    public static HMac hmacSha256() {
        return new HMac(HmacAlgorithm.HmacSHA256);
    }
}
