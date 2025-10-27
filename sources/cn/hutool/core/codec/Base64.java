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
public class Base64 {
    private static final Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;

    public static byte[] decode(CharSequence charSequence) {
        return Base64Decoder.decode(charSequence);
    }

    public static String decodeStr(CharSequence charSequence) {
        return Base64Decoder.decodeStr(charSequence);
    }

    public static String decodeStrGbk(CharSequence charSequence) {
        return Base64Decoder.decodeStr(charSequence, CharsetUtil.CHARSET_GBK);
    }

    public static File decodeToFile(CharSequence charSequence, File file) {
        return FileUtil.writeBytes(Base64Decoder.decode(charSequence), file);
    }

    public static void decodeToStream(CharSequence charSequence, OutputStream outputStream, boolean z2) throws IOException, IORuntimeException {
        IoUtil.write(outputStream, z2, Base64Decoder.decode(charSequence));
    }

    public static byte[] encode(byte[] bArr, boolean z2) {
        if (bArr == null) {
            return null;
        }
        return z2 ? java.util.Base64.getMimeEncoder().encode(bArr) : java.util.Base64.getEncoder().encode(bArr);
    }

    public static String encodeStr(byte[] bArr, boolean z2, boolean z3) {
        return StrUtil.str(encode(bArr, z2, z3), DEFAULT_CHARSET);
    }

    @Deprecated
    public static byte[] encodeUrlSafe(byte[] bArr, boolean z2) {
        return Base64Encoder.encodeUrlSafe(bArr, z2);
    }

    public static String encodeWithoutPadding(CharSequence charSequence, String str) {
        return encodeWithoutPadding(CharSequenceUtil.bytes(charSequence, str));
    }

    public static boolean isBase64(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() < 2) {
            return false;
        }
        byte[] bArrUtf8Bytes = CharSequenceUtil.utf8Bytes(charSequence);
        if (bArrUtf8Bytes.length != charSequence.length()) {
            return false;
        }
        return isBase64(bArrUtf8Bytes);
    }

    private static boolean isWhiteSpace(byte b3) {
        return b3 == 9 || b3 == 10 || b3 == 13 || b3 == 32;
    }

    public static byte[] decode(byte[] bArr) {
        return Base64Decoder.decode(bArr);
    }

    public static String decodeStr(CharSequence charSequence, String str) {
        return decodeStr(charSequence, CharsetUtil.charset(str));
    }

    public static String encodeUrlSafe(CharSequence charSequence) {
        return encodeUrlSafe(charSequence, DEFAULT_CHARSET);
    }

    public static String encodeWithoutPadding(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return java.util.Base64.getEncoder().withoutPadding().encodeToString(bArr);
    }

    public static String decodeStr(CharSequence charSequence, Charset charset) {
        return Base64Decoder.decodeStr(charSequence, charset);
    }

    public static String encode(CharSequence charSequence) {
        return encode(charSequence, DEFAULT_CHARSET);
    }

    @Deprecated
    public static String encodeUrlSafe(CharSequence charSequence, String str) {
        return encodeUrlSafe(charSequence, CharsetUtil.charset(str));
    }

    public static String encode(CharSequence charSequence, String str) {
        return encode(charSequence, CharsetUtil.charset(str));
    }

    public static String encodeUrlSafe(CharSequence charSequence, Charset charset) {
        return encodeUrlSafe(CharSequenceUtil.bytes(charSequence, charset));
    }

    public static String encode(CharSequence charSequence, Charset charset) {
        return encode(CharSequenceUtil.bytes(charSequence, charset));
    }

    public static String encodeUrlSafe(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return java.util.Base64.getUrlEncoder().withoutPadding().encodeToString(bArr);
    }

    public static boolean isBase64(byte[] bArr) {
        if (bArr == null || bArr.length < 3) {
            return false;
        }
        int length = bArr.length;
        int i2 = 0;
        boolean z2 = false;
        while (true) {
            boolean z3 = true;
            if (i2 >= length) {
                return true;
            }
            byte b3 = bArr[i2];
            if (z2) {
                if (61 != b3) {
                    return false;
                }
            } else if (61 == b3) {
                z2 = true;
            } else {
                if (!Base64Decoder.isBase64Code(b3) && !isWhiteSpace(b3)) {
                    z3 = false;
                }
                if (!z3) {
                    return false;
                }
            }
            i2++;
        }
    }

    public static String encode(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return java.util.Base64.getEncoder().encodeToString(bArr);
    }

    public static String encodeUrlSafe(InputStream inputStream) {
        return encodeUrlSafe(IoUtil.readBytes(inputStream));
    }

    public static String encode(InputStream inputStream) {
        return encode(IoUtil.readBytes(inputStream));
    }

    public static String encodeUrlSafe(File file) {
        return encodeUrlSafe(FileUtil.readBytes(file));
    }

    public static String encode(File file) {
        return encode(FileUtil.readBytes(file));
    }

    public static byte[] encode(byte[] bArr, boolean z2, boolean z3) {
        return Base64Encoder.encode(bArr, z2, z3);
    }
}
