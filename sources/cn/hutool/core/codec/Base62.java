package cn.hutool.core.codec;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class Base62 {
    private static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;

    public static byte[] decode(CharSequence charSequence) {
        return decode(CharSequenceUtil.bytes(charSequence, DEFAULT_CHARSET));
    }

    public static byte[] decodeInverted(CharSequence charSequence) {
        return decodeInverted(CharSequenceUtil.bytes(charSequence, DEFAULT_CHARSET));
    }

    public static String decodeStr(CharSequence charSequence) {
        return decodeStr(charSequence, DEFAULT_CHARSET);
    }

    public static String decodeStrGbk(CharSequence charSequence) {
        return decodeStr(charSequence, CharsetUtil.CHARSET_GBK);
    }

    public static String decodeStrInverted(CharSequence charSequence) {
        return decodeStrInverted(charSequence, DEFAULT_CHARSET);
    }

    public static File decodeToFile(CharSequence charSequence, File file) {
        return FileUtil.writeBytes(decode(charSequence), file);
    }

    public static File decodeToFileInverted(CharSequence charSequence, File file) {
        return FileUtil.writeBytes(decodeInverted(charSequence), file);
    }

    public static void decodeToStream(CharSequence charSequence, OutputStream outputStream, boolean z2) throws IOException, IORuntimeException {
        IoUtil.write(outputStream, z2, decode(charSequence));
    }

    public static void decodeToStreamInverted(CharSequence charSequence, OutputStream outputStream, boolean z2) throws IOException, IORuntimeException {
        IoUtil.write(outputStream, z2, decodeInverted(charSequence));
    }

    public static String encode(CharSequence charSequence) {
        return encode(charSequence, DEFAULT_CHARSET);
    }

    public static String encodeInverted(CharSequence charSequence) {
        return encodeInverted(charSequence, DEFAULT_CHARSET);
    }

    public static byte[] decode(byte[] bArr) {
        return Base62Codec.INSTANCE.decode(bArr);
    }

    public static byte[] decodeInverted(byte[] bArr) {
        return Base62Codec.INSTANCE.decode(bArr, true);
    }

    public static String decodeStr(CharSequence charSequence, Charset charset) {
        return StrUtil.str(decode(charSequence), charset);
    }

    public static String decodeStrInverted(CharSequence charSequence, Charset charset) {
        return StrUtil.str(decodeInverted(charSequence), charset);
    }

    public static String encode(CharSequence charSequence, Charset charset) {
        return encode(CharSequenceUtil.bytes(charSequence, charset));
    }

    public static String encodeInverted(CharSequence charSequence, Charset charset) {
        return encodeInverted(CharSequenceUtil.bytes(charSequence, charset));
    }

    public static String encode(byte[] bArr) {
        return new String(Base62Codec.INSTANCE.encode(bArr));
    }

    public static String encodeInverted(byte[] bArr) {
        return new String(Base62Codec.INSTANCE.encode(bArr, true));
    }

    public static String encode(InputStream inputStream) {
        return encode(IoUtil.readBytes(inputStream));
    }

    public static String encodeInverted(InputStream inputStream) {
        return encodeInverted(IoUtil.readBytes(inputStream));
    }

    public static String encode(File file) {
        return encode(FileUtil.readBytes(file));
    }

    public static String encodeInverted(File file) {
        return encodeInverted(FileUtil.readBytes(file));
    }
}
