package cn.hutool.core.util;

import cn.hutool.core.io.CharsetDetector;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/* loaded from: classes.dex */
public class CharsetUtil {
    public static final Charset CHARSET_GBK;
    public static final Charset CHARSET_ISO_8859_1 = StandardCharsets.ISO_8859_1;
    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;
    public static final String GBK = "GBK";
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String UTF_8 = "UTF-8";

    static {
        Charset charsetForName;
        try {
            charsetForName = Charset.forName(GBK);
        } catch (UnsupportedCharsetException unused) {
            charsetForName = null;
        }
        CHARSET_GBK = charsetForName;
    }

    public static Charset charset(String str) throws UnsupportedCharsetException {
        return CharSequenceUtil.isBlank(str) ? Charset.defaultCharset() : Charset.forName(str);
    }

    public static String convert(String str, String str2, String str3) {
        return convert(str, Charset.forName(str2), Charset.forName(str3));
    }

    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }

    public static String defaultCharsetName() {
        return defaultCharset().name();
    }

    public static Charset parse(String str) {
        return parse(str, Charset.defaultCharset());
    }

    public static Charset systemCharset() {
        return FileUtil.isWindows() ? CHARSET_GBK : defaultCharset();
    }

    public static String systemCharsetName() {
        return systemCharset().name();
    }

    public static String convert(String str, Charset charset, Charset charset2) {
        if (charset == null) {
            charset = StandardCharsets.ISO_8859_1;
        }
        if (charset2 == null) {
            charset2 = StandardCharsets.UTF_8;
        }
        return (CharSequenceUtil.isBlank(str) || charset.equals(charset2)) ? str : new String(str.getBytes(charset), charset2);
    }

    public static Charset defaultCharset(InputStream inputStream, Charset... charsetArr) {
        return CharsetDetector.detect(inputStream, charsetArr);
    }

    public static Charset parse(String str, Charset charset) {
        if (CharSequenceUtil.isBlank(str)) {
            return charset;
        }
        try {
            return Charset.forName(str);
        } catch (UnsupportedCharsetException unused) {
            return charset;
        }
    }

    public static Charset defaultCharset(int i2, InputStream inputStream, Charset... charsetArr) {
        return CharsetDetector.detect(i2, inputStream, charsetArr);
    }

    public static File convert(File file, Charset charset, Charset charset2) {
        return FileUtil.writeString(FileUtil.readString(file, charset), file, charset2);
    }
}
