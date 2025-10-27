package cn.hutool.core.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.text.TextSimilarity;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/* loaded from: classes.dex */
public class StrUtil extends CharSequenceUtil implements StrPool {
    public static StringBuilder builder() {
        return new StringBuilder();
    }

    public static String fill(String str, char c3, int i2, boolean z2) {
        int length = str.length();
        if (length > i2) {
            return str;
        }
        String strRepeat = CharSequenceUtil.repeat(c3, i2 - length);
        return z2 ? strRepeat.concat(str) : str.concat(strRepeat);
    }

    public static String fillAfter(String str, char c3, int i2) {
        return fill(str, c3, i2, false);
    }

    public static String fillBefore(String str, char c3, int i2) {
        return fill(str, c3, i2, true);
    }

    public static String format(CharSequence charSequence, Map<?, ?> map) {
        return format(charSequence, map, true);
    }

    public static StringReader getReader(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return new StringReader(charSequence.toString());
    }

    public static StringWriter getWriter() {
        return new StringWriter();
    }

    public static boolean isBlankIfStr(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof CharSequence) {
            return CharSequenceUtil.isBlank((CharSequence) obj);
        }
        return false;
    }

    public static boolean isEmptyIfStr(Object obj) {
        if (obj == null) {
            return true;
        }
        return (obj instanceof CharSequence) && ((CharSequence) obj).length() == 0;
    }

    public static String reverse(String str) {
        return new String(PrimitiveArrayUtil.reverse(str.toCharArray()));
    }

    public static double similar(String str, String str2) {
        return TextSimilarity.similar(str, str2);
    }

    @Deprecated
    public static String str(Object obj, String str) {
        return str(obj, Charset.forName(str));
    }

    public static StrBuilder strBuilder() {
        return StrBuilder.create();
    }

    public static String toString(Object obj) {
        return String.valueOf(obj);
    }

    public static String toStringOrNull(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public static void trim(String[] strArr) {
        if (strArr == null) {
            return;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            String str = strArr[i2];
            if (str != null) {
                strArr[i2] = CharSequenceUtil.trim(str);
            }
        }
    }

    public static String truncateByByteLength(String str, Charset charset, int i2, int i3, boolean z2) {
        if (str == null || str.length() * i3 <= i2) {
            return str;
        }
        byte[] bytes = str.getBytes(charset);
        if (bytes.length <= i2) {
            return str;
        }
        if (z2) {
            i2 -= "...".getBytes(charset).length;
        }
        ByteBuffer byteBufferWrap = ByteBuffer.wrap(bytes, 0, i2);
        CharBuffer charBufferAllocate = CharBuffer.allocate(i2);
        CharsetDecoder charsetDecoderNewDecoder = charset.newDecoder();
        charsetDecoderNewDecoder.onMalformedInput(CodingErrorAction.IGNORE);
        charsetDecoderNewDecoder.decode(byteBufferWrap, charBufferAllocate, true);
        charsetDecoderNewDecoder.flush(charBufferAllocate);
        String str2 = new String(charBufferAllocate.array(), 0, charBufferAllocate.position());
        if (!z2) {
            return str2;
        }
        return str2 + "...";
    }

    public static String truncateUtf8(String str, int i2) {
        return truncateByByteLength(str, StandardCharsets.UTF_8, i2, 4, true);
    }

    public static String utf8Str(Object obj) {
        return str(obj, CharsetUtil.CHARSET_UTF_8);
    }

    public static String uuid() {
        return IdUtil.randomUUID();
    }

    public static StringBuilder builder(int i2) {
        return new StringBuilder(i2);
    }

    public static String format(CharSequence charSequence, Map<?, ?> map, boolean z2) {
        return StrFormatter.format(charSequence, map, z2);
    }

    public static String similar(String str, String str2, int i2) {
        return TextSimilarity.similar(str, str2, i2);
    }

    public static String str(Object obj, Charset charset) {
        if (obj == null) {
            return null;
        }
        return obj instanceof String ? (String) obj : obj instanceof byte[] ? str((byte[]) obj, charset) : obj instanceof Byte[] ? str((Byte[]) obj, charset) : obj instanceof ByteBuffer ? str((ByteBuffer) obj, charset) : ArrayUtil.isArray(obj) ? ArrayUtil.toString(obj) : obj.toString();
    }

    public static StrBuilder strBuilder(int i2) {
        return StrBuilder.create(i2);
    }

    public static String str(byte[] bArr, String str) {
        return str(bArr, CharsetUtil.charset(str));
    }

    public static String str(byte[] bArr, Charset charset) {
        if (bArr == null) {
            return null;
        }
        if (charset == null) {
            return new String(bArr);
        }
        return new String(bArr, charset);
    }

    public static String str(Byte[] bArr, String str) {
        return str(bArr, CharsetUtil.charset(str));
    }

    public static String str(Byte[] bArr, Charset charset) {
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = new byte[bArr.length];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            Byte b3 = bArr[i2];
            bArr2[i2] = b3 == null ? (byte) -1 : b3.byteValue();
        }
        return str(bArr2, charset);
    }

    public static String str(ByteBuffer byteBuffer, String str) {
        if (byteBuffer == null) {
            return null;
        }
        return str(byteBuffer, CharsetUtil.charset(str));
    }

    public static String str(ByteBuffer byteBuffer, Charset charset) {
        if (charset == null) {
            charset = Charset.defaultCharset();
        }
        return charset.decode(byteBuffer).toString();
    }
}
