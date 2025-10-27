package cn.hutool.crypto.symmetric;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import java.io.InputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final /* synthetic */ class c {
    public static byte[] a(SymmetricDecryptor symmetricDecryptor, InputStream inputStream) throws IORuntimeException {
        return symmetricDecryptor.decrypt(IoUtil.readBytes(inputStream));
    }

    public static byte[] b(SymmetricDecryptor symmetricDecryptor, String str) {
        return symmetricDecryptor.decrypt(SecureUtil.decode(str));
    }

    public static String c(SymmetricDecryptor symmetricDecryptor, InputStream inputStream) {
        return symmetricDecryptor.decryptStr(inputStream, CharsetUtil.CHARSET_UTF_8);
    }

    public static String d(SymmetricDecryptor symmetricDecryptor, InputStream inputStream, Charset charset) {
        return StrUtil.str(symmetricDecryptor.decrypt(inputStream), charset);
    }

    public static String e(SymmetricDecryptor symmetricDecryptor, String str) {
        return symmetricDecryptor.decryptStr(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String f(SymmetricDecryptor symmetricDecryptor, String str, Charset charset) {
        return StrUtil.str(symmetricDecryptor.decrypt(str), charset);
    }

    public static String g(SymmetricDecryptor symmetricDecryptor, byte[] bArr) {
        return symmetricDecryptor.decryptStr(bArr, CharsetUtil.CHARSET_UTF_8);
    }

    public static String h(SymmetricDecryptor symmetricDecryptor, byte[] bArr, Charset charset) {
        return StrUtil.str(symmetricDecryptor.decrypt(bArr), charset);
    }
}
