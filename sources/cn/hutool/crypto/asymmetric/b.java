package cn.hutool.crypto.asymmetric;

import cn.hutool.core.codec.BCD;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import java.io.InputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final /* synthetic */ class b {
    public static byte[] a(AsymmetricEncryptor asymmetricEncryptor, InputStream inputStream, KeyType keyType) throws IORuntimeException {
        return asymmetricEncryptor.encrypt(IoUtil.readBytes(inputStream), keyType);
    }

    public static byte[] b(AsymmetricEncryptor asymmetricEncryptor, String str, KeyType keyType) {
        return asymmetricEncryptor.encrypt(CharSequenceUtil.utf8Bytes(str), keyType);
    }

    public static byte[] c(AsymmetricEncryptor asymmetricEncryptor, String str, String str2, KeyType keyType) {
        return asymmetricEncryptor.encrypt(CharSequenceUtil.bytes(str, str2), keyType);
    }

    public static byte[] d(AsymmetricEncryptor asymmetricEncryptor, String str, Charset charset, KeyType keyType) {
        return asymmetricEncryptor.encrypt(CharSequenceUtil.bytes(str, charset), keyType);
    }

    public static String e(AsymmetricEncryptor asymmetricEncryptor, InputStream inputStream, KeyType keyType) {
        return Base64.encode(asymmetricEncryptor.encrypt(inputStream, keyType));
    }

    public static String f(AsymmetricEncryptor asymmetricEncryptor, String str, KeyType keyType) {
        return Base64.encode(asymmetricEncryptor.encrypt(str, keyType));
    }

    public static String g(AsymmetricEncryptor asymmetricEncryptor, String str, Charset charset, KeyType keyType) {
        return Base64.encode(asymmetricEncryptor.encrypt(str, charset, keyType));
    }

    public static String h(AsymmetricEncryptor asymmetricEncryptor, byte[] bArr, KeyType keyType) {
        return Base64.encode(asymmetricEncryptor.encrypt(bArr, keyType));
    }

    @Deprecated
    public static String i(AsymmetricEncryptor asymmetricEncryptor, String str, KeyType keyType) {
        return asymmetricEncryptor.encryptBcd(str, keyType, CharsetUtil.CHARSET_UTF_8);
    }

    @Deprecated
    public static String j(AsymmetricEncryptor asymmetricEncryptor, String str, KeyType keyType, Charset charset) {
        return BCD.bcdToStr(asymmetricEncryptor.encrypt(str, charset, keyType));
    }

    public static String k(AsymmetricEncryptor asymmetricEncryptor, InputStream inputStream, KeyType keyType) {
        return HexUtil.encodeHexStr(asymmetricEncryptor.encrypt(inputStream, keyType));
    }

    public static String l(AsymmetricEncryptor asymmetricEncryptor, String str, KeyType keyType) {
        return HexUtil.encodeHexStr(asymmetricEncryptor.encrypt(str, keyType));
    }

    public static String m(AsymmetricEncryptor asymmetricEncryptor, String str, Charset charset, KeyType keyType) {
        return HexUtil.encodeHexStr(asymmetricEncryptor.encrypt(str, charset, keyType));
    }

    public static String n(AsymmetricEncryptor asymmetricEncryptor, byte[] bArr, KeyType keyType) {
        return HexUtil.encodeHexStr(asymmetricEncryptor.encrypt(bArr, keyType));
    }
}
