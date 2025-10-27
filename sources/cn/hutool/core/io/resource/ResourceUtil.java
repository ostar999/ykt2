package cn.hutool.core.io.resource;

import cn.hutool.core.collection.EnumerationIter;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.lang.Filter;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ObjectUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/* loaded from: classes.dex */
public class ResourceUtil {
    public static BufferedReader getReader(String str, Charset charset) {
        return getResourceObj(str).getReader(charset);
    }

    public static URL getResource(String str) throws IORuntimeException {
        return getResource(str, null);
    }

    public static EnumerationIter<URL> getResourceIter(String str) {
        return getResourceIter(str, null);
    }

    public static Resource getResourceObj(String str) {
        return (CharSequenceUtil.isNotBlank(str) && (str.startsWith("file:") || FileUtil.isAbsolutePath(str))) ? new FileResource(str) : new ClassPathResource(str);
    }

    public static List<URL> getResources(String str) {
        return getResources(str, null);
    }

    public static InputStream getStream(String str) throws NoResourceException {
        return getResourceObj(str).getStream();
    }

    public static InputStream getStreamSafe(String str) {
        try {
            return getResourceObj(str).getStream();
        } catch (NoResourceException unused) {
            return null;
        }
    }

    public static BufferedReader getUtf8Reader(String str) {
        return getReader(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static byte[] readBytes(String str) {
        return getResourceObj(str).readBytes();
    }

    public static String readStr(String str, Charset charset) {
        return getResourceObj(str).readStr(charset);
    }

    public static String readUtf8Str(String str) {
        return getResourceObj(str).readUtf8Str();
    }

    public static URL getResource(String str, Class<?> cls) {
        String strNullToEmpty = CharSequenceUtil.nullToEmpty(str);
        return cls != null ? cls.getResource(strNullToEmpty) : ClassLoaderUtil.getClassLoader().getResource(strNullToEmpty);
    }

    public static EnumerationIter<URL> getResourceIter(String str, ClassLoader classLoader) {
        try {
            return new EnumerationIter<>(((ClassLoader) ObjectUtil.defaultIfNull(classLoader, new cn.hutool.core.compiler.b())).getResources(str));
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static List<URL> getResources(String str, Filter<URL> filter) {
        return IterUtil.filterToList(getResourceIter(str), filter);
    }
}
