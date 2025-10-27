package cn.hutool.crypto.symmetric;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import java.io.InputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public final /* synthetic */ class d {
    public static byte[] a(SymmetricEncryptor symmetricEncryptor, InputStream inputStream) throws IORuntimeException {
        return symmetricEncryptor.encrypt(IoUtil.readBytes(inputStream));
    }

    public static byte[] b(SymmetricEncryptor symmetricEncryptor, String str) {
        return symmetricEncryptor.encrypt(CharSequenceUtil.bytes(str, CharsetUtil.CHARSET_UTF_8));
    }

    public static byte[] c(SymmetricEncryptor symmetricEncryptor, String str, String str2) {
        return symmetricEncryptor.encrypt(CharSequenceUtil.bytes(str, str2));
    }

    public static byte[] d(SymmetricEncryptor symmetricEncryptor, String str, Charset charset) {
        return symmetricEncryptor.encrypt(CharSequenceUtil.bytes(str, charset));
    }

    public static String e(SymmetricEncryptor symmetricEncryptor, InputStream inputStream) {
        return Base64.encode(symmetricEncryptor.encrypt(inputStream));
    }

    public static String f(SymmetricEncryptor symmetricEncryptor, String str) {
        return Base64.encode(symmetricEncryptor.encrypt(str));
    }

    public static String g(SymmetricEncryptor symmetricEncryptor, String str, String str2) {
        return Base64.encode(symmetricEncryptor.encrypt(str, str2));
    }

    public static String h(SymmetricEncryptor symmetricEncryptor, String str, Charset charset) {
        return Base64.encode(symmetricEncryptor.encrypt(str, charset));
    }

    public static String i(SymmetricEncryptor symmetricEncryptor, byte[] bArr) {
        return Base64.encode(symmetricEncryptor.encrypt(bArr));
    }

    public static String j(SymmetricEncryptor symmetricEncryptor, InputStream inputStream) {
        return HexUtil.encodeHexStr(symmetricEncryptor.encrypt(inputStream));
    }

    public static String k(SymmetricEncryptor symmetricEncryptor, String str) {
        return HexUtil.encodeHexStr(symmetricEncryptor.encrypt(str));
    }

    public static String l(SymmetricEncryptor symmetricEncryptor, String str, String str2) {
        return HexUtil.encodeHexStr(symmetricEncryptor.encrypt(str, str2));
    }

    public static String m(SymmetricEncryptor symmetricEncryptor, String str, Charset charset) {
        return HexUtil.encodeHexStr(symmetricEncryptor.encrypt(str, charset));
    }

    public static String n(SymmetricEncryptor symmetricEncryptor, byte[] bArr) {
        return HexUtil.encodeHexStr(symmetricEncryptor.encrypt(bArr));
    }
}
