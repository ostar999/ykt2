package cn.hutool.crypto.digest;

import cn.hutool.core.util.CharsetUtil;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import javax.crypto.SecretKey;

/* loaded from: classes.dex */
public class DigestUtil {
    public static String bcrypt(String str) {
        return BCrypt.hashpw(str);
    }

    public static boolean bcryptCheck(String str, String str2) {
        return BCrypt.checkpw(str, str2);
    }

    public static Digester digester(DigestAlgorithm digestAlgorithm) {
        return new Digester(digestAlgorithm);
    }

    public static HMac hmac(HmacAlgorithm hmacAlgorithm, byte[] bArr) {
        return new HMac(hmacAlgorithm, bArr);
    }

    public static byte[] md5(byte[] bArr) {
        return new MD5().digest(bArr);
    }

    public static String md5Hex(byte[] bArr) {
        return new MD5().digestHex(bArr);
    }

    public static String md5Hex16(byte[] bArr) {
        return new MD5().digestHex16(bArr);
    }

    public static String md5HexTo16(String str) {
        return str.substring(8, 24);
    }

    public static byte[] sha1(byte[] bArr) {
        return new Digester(DigestAlgorithm.SHA1).digest(bArr);
    }

    public static String sha1Hex(byte[] bArr) {
        return new Digester(DigestAlgorithm.SHA1).digestHex(bArr);
    }

    public static byte[] sha256(byte[] bArr) {
        return new Digester(DigestAlgorithm.SHA256).digest(bArr);
    }

    public static String sha256Hex(byte[] bArr) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(bArr);
    }

    public static byte[] sha512(byte[] bArr) {
        return new Digester(DigestAlgorithm.SHA512).digest(bArr);
    }

    public static String sha512Hex(byte[] bArr) {
        return new Digester(DigestAlgorithm.SHA512).digestHex(bArr);
    }

    public static Digester digester(String str) {
        return new Digester(str);
    }

    public static HMac hmac(HmacAlgorithm hmacAlgorithm, SecretKey secretKey) {
        return new HMac(hmacAlgorithm, secretKey);
    }

    public static byte[] md5(String str, String str2) {
        return new MD5().digest(str, str2);
    }

    public static String md5Hex(String str, String str2) {
        return new MD5().digestHex(str, str2);
    }

    public static String md5Hex16(String str, Charset charset) {
        return new MD5().digestHex16(str, charset);
    }

    public static byte[] sha1(String str, String str2) {
        return new Digester(DigestAlgorithm.SHA1).digest(str, str2);
    }

    public static String sha1Hex(String str, String str2) {
        return new Digester(DigestAlgorithm.SHA1).digestHex(str, str2);
    }

    public static byte[] sha256(String str, String str2) {
        return new Digester(DigestAlgorithm.SHA256).digest(str, str2);
    }

    public static String sha256Hex(String str, String str2) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(str, str2);
    }

    public static byte[] sha512(String str, String str2) {
        return new Digester(DigestAlgorithm.SHA512).digest(str, str2);
    }

    public static String sha512Hex(String str, String str2) {
        return new Digester(DigestAlgorithm.SHA512).digestHex(str, str2);
    }

    public static byte[] md5(String str) {
        return md5(str, "UTF-8");
    }

    public static String md5Hex(String str, Charset charset) {
        return new MD5().digestHex(str, charset);
    }

    public static String md5Hex16(String str) {
        return md5Hex16(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static byte[] sha1(String str) {
        return sha1(str, "UTF-8");
    }

    public static String sha1Hex(String str) {
        return sha1Hex(str, "UTF-8");
    }

    public static byte[] sha256(String str) {
        return sha256(str, "UTF-8");
    }

    public static String sha256Hex(String str) {
        return sha256Hex(str, "UTF-8");
    }

    public static byte[] sha512(String str) {
        return sha512(str, "UTF-8");
    }

    public static String sha512Hex(String str) {
        return sha512Hex(str, "UTF-8");
    }

    public static byte[] md5(InputStream inputStream) {
        return new MD5().digest(inputStream);
    }

    public static String md5Hex(String str) {
        return md5Hex(str, "UTF-8");
    }

    public static String md5Hex16(InputStream inputStream) {
        return new MD5().digestHex16(inputStream);
    }

    public static byte[] sha1(InputStream inputStream) {
        return new Digester(DigestAlgorithm.SHA1).digest(inputStream);
    }

    public static String sha1Hex(InputStream inputStream) {
        return new Digester(DigestAlgorithm.SHA1).digestHex(inputStream);
    }

    public static byte[] sha256(InputStream inputStream) {
        return new Digester(DigestAlgorithm.SHA256).digest(inputStream);
    }

    public static String sha256Hex(InputStream inputStream) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(inputStream);
    }

    public static byte[] sha512(InputStream inputStream) {
        return new Digester(DigestAlgorithm.SHA512).digest(inputStream);
    }

    public static String sha512Hex(InputStream inputStream) {
        return new Digester(DigestAlgorithm.SHA512).digestHex(inputStream);
    }

    public static byte[] md5(File file) {
        return new MD5().digest(file);
    }

    public static String md5Hex(InputStream inputStream) {
        return new MD5().digestHex(inputStream);
    }

    public static String md5Hex16(File file) {
        return new MD5().digestHex16(file);
    }

    public static byte[] sha1(File file) {
        return new Digester(DigestAlgorithm.SHA1).digest(file);
    }

    public static String sha1Hex(File file) {
        return new Digester(DigestAlgorithm.SHA1).digestHex(file);
    }

    public static byte[] sha256(File file) {
        return new Digester(DigestAlgorithm.SHA256).digest(file);
    }

    public static String sha256Hex(File file) {
        return new Digester(DigestAlgorithm.SHA256).digestHex(file);
    }

    public static byte[] sha512(File file) {
        return new Digester(DigestAlgorithm.SHA512).digest(file);
    }

    public static String sha512Hex(File file) {
        return new Digester(DigestAlgorithm.SHA512).digestHex(file);
    }

    public static String md5Hex(File file) {
        return new MD5().digestHex(file);
    }
}
