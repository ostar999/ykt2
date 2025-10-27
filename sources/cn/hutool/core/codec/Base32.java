package cn.hutool.core.codec;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class Base32 {
    public static byte[] decode(String str) {
        return Base32Codec.INSTANCE.decode((CharSequence) str);
    }

    public static byte[] decodeHex(String str) {
        return Base32Codec.INSTANCE.decode(str, true);
    }

    public static String decodeStr(String str) {
        return decodeStr(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String decodeStrHex(String str) {
        return decodeStrHex(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String encode(byte[] bArr) {
        return Base32Codec.INSTANCE.encode(bArr);
    }

    public static String encodeHex(byte[] bArr) {
        return Base32Codec.INSTANCE.encode(bArr, true);
    }

    public static String decodeStr(String str, Charset charset) {
        return StrUtil.str(decode(str), charset);
    }

    public static String decodeStrHex(String str, Charset charset) {
        return StrUtil.str(decodeHex(str), charset);
    }

    public static String encode(String str) {
        return encode(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String encodeHex(String str) {
        return encodeHex(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String encode(String str, Charset charset) {
        return encode(CharSequenceUtil.bytes(str, charset));
    }

    public static String encodeHex(String str, Charset charset) {
        return encodeHex(CharSequenceUtil.bytes(str, charset));
    }
}
