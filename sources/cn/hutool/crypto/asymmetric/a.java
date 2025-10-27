package cn.hutool.crypto.asymmetric;

import cn.hutool.core.codec.BCD;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import java.io.InputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final /* synthetic */ class a {
    public static byte[] a(AsymmetricDecryptor asymmetricDecryptor, InputStream inputStream, KeyType keyType) throws IORuntimeException {
        return asymmetricDecryptor.decrypt(IoUtil.readBytes(inputStream), keyType);
    }

    public static byte[] b(AsymmetricDecryptor asymmetricDecryptor, String str, KeyType keyType) {
        return asymmetricDecryptor.decrypt(SecureUtil.decode(str), keyType);
    }

    public static byte[] c(AsymmetricDecryptor asymmetricDecryptor, String str, KeyType keyType) {
        return asymmetricDecryptor.decryptFromBcd(str, keyType, CharsetUtil.CHARSET_UTF_8);
    }

    @Deprecated
    public static byte[] d(AsymmetricDecryptor asymmetricDecryptor, String str, KeyType keyType, Charset charset) throws IllegalArgumentException {
        Assert.notNull(str, "Bcd string must be not null!", new Object[0]);
        return asymmetricDecryptor.decrypt(BCD.ascToBcd(CharSequenceUtil.bytes(str, charset)), keyType);
    }

    public static String e(AsymmetricDecryptor asymmetricDecryptor, String str, KeyType keyType) {
        return asymmetricDecryptor.decryptStr(str, keyType, CharsetUtil.CHARSET_UTF_8);
    }

    public static String f(AsymmetricDecryptor asymmetricDecryptor, String str, KeyType keyType, Charset charset) {
        return StrUtil.str(asymmetricDecryptor.decrypt(str, keyType), charset);
    }

    @Deprecated
    public static String g(AsymmetricDecryptor asymmetricDecryptor, String str, KeyType keyType) {
        return asymmetricDecryptor.decryptStrFromBcd(str, keyType, CharsetUtil.CHARSET_UTF_8);
    }

    @Deprecated
    public static String h(AsymmetricDecryptor asymmetricDecryptor, String str, KeyType keyType, Charset charset) {
        return StrUtil.str(asymmetricDecryptor.decryptFromBcd(str, keyType, charset), charset);
    }
}
