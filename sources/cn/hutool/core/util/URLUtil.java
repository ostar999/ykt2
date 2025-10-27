package cn.hutool.core.util;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.net.url.UrlQuery;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import com.just.agentweb.DefaultWebClient;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.jar.JarFile;

/* loaded from: classes.dex */
public class URLUtil extends URLEncodeUtil {
    public static final String CLASSPATH_URL_PREFIX = "classpath:";
    public static final String FILE_URL_PREFIX = "file:";
    public static final String JAR_URL_PREFIX = "jar:";
    public static final String JAR_URL_SEPARATOR = "!/";
    public static final String URL_PROTOCOL_FILE = "file";
    public static final String URL_PROTOCOL_JAR = "jar";
    public static final String URL_PROTOCOL_VFS = "vfs";
    public static final String URL_PROTOCOL_VFSFILE = "vfsfile";
    public static final String URL_PROTOCOL_VFSZIP = "vfszip";
    public static final String URL_PROTOCOL_WSJAR = "wsjar";
    public static final String URL_PROTOCOL_ZIP = "zip";
    public static final String WAR_URL_PREFIX = "war:";
    public static final String WAR_URL_SEPARATOR = "*/";

    public static String buildQuery(Map<String, ?> map, Charset charset) {
        return UrlQuery.of(map).build(charset);
    }

    public static String completeUrl(String str, String str2) {
        String strNormalize = normalize(str, false);
        if (CharSequenceUtil.isBlank(strNormalize)) {
            return null;
        }
        try {
            return new URL(new URL(strNormalize), str2).toString();
        } catch (MalformedURLException e2) {
            throw new UtilException(e2);
        }
    }

    public static String decode(String str) throws UtilException {
        return decode(str, "UTF-8");
    }

    public static String encodeBlank(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = charSequence.charAt(i2);
            if (CharUtil.isBlankChar(cCharAt)) {
                sb.append("%20");
            } else {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    public static long getContentLength(URL url) throws IORuntimeException {
        if (url == null) {
            return -1L;
        }
        URLConnection uRLConnectionOpenConnection = null;
        try {
            try {
                uRLConnectionOpenConnection = url.openConnection();
                return uRLConnectionOpenConnection.getContentLengthLong();
            } catch (IOException e2) {
                throw new IORuntimeException(e2);
            }
        } finally {
            if (uRLConnectionOpenConnection instanceof HttpURLConnection) {
                ((HttpURLConnection) uRLConnectionOpenConnection).disconnect();
            }
        }
    }

    public static String getDataUri(String str, String str2, String str3) {
        return getDataUri(str, null, str2, str3);
    }

    public static String getDataUriBase64(String str, String str2) {
        return getDataUri(str, null, "base64", str2);
    }

    public static String getDecodedPath(URL url) {
        String path = null;
        if (url == null) {
            return null;
        }
        try {
            path = toURI(url).getPath();
        } catch (UtilException unused) {
        }
        return path != null ? path : url.getPath();
    }

    public static URI getHost(URL url) {
        if (url == null) {
            return null;
        }
        try {
            return new URI(url.getProtocol(), url.getHost(), null, null);
        } catch (URISyntaxException e2) {
            throw new UtilException(e2);
        }
    }

    public static JarFile getJarFile(URL url) {
        try {
            return ((JarURLConnection) url.openConnection()).getJarFile();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static String getPath(String str) {
        return toURI(str).getPath();
    }

    public static BufferedReader getReader(URL url, Charset charset) {
        return IoUtil.getReader(getStream(url), charset);
    }

    public static InputStream getStream(URL url) throws IllegalArgumentException {
        Assert.notNull(url, "URL must be not null", new Object[0]);
        try {
            return url.openStream();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static URI getStringURI(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return URI.create(CharSequenceUtil.addPrefixIfNot(charSequence, "string:///"));
    }

    public static URL getURL(String str) {
        return ResourceUtil.getResource(str);
    }

    public static URL[] getURLs(File... fileArr) {
        URL[] urlArr = new URL[fileArr.length];
        for (int i2 = 0; i2 < fileArr.length; i2++) {
            try {
                urlArr[i2] = fileArr[i2].toURI().toURL();
            } catch (MalformedURLException e2) {
                throw new UtilException(e2, "Error occured when get URL!", new Object[0]);
            }
        }
        return urlArr;
    }

    public static boolean isFileURL(URL url) throws IllegalArgumentException {
        Assert.notNull(url, "URL must be not null", new Object[0]);
        String protocol = url.getProtocol();
        return "file".equals(protocol) || URL_PROTOCOL_VFSFILE.equals(protocol) || URL_PROTOCOL_VFS.equals(protocol);
    }

    public static boolean isJarFileURL(URL url) throws IllegalArgumentException {
        Assert.notNull(url, "URL must be not null", new Object[0]);
        return "file".equals(url.getProtocol()) && url.getPath().toLowerCase().endsWith(".jar");
    }

    public static boolean isJarURL(URL url) throws IllegalArgumentException {
        Assert.notNull(url, "URL must be not null", new Object[0]);
        String protocol = url.getProtocol();
        return "jar".equals(protocol) || "zip".equals(protocol) || URL_PROTOCOL_VFSZIP.equals(protocol) || URL_PROTOCOL_WSJAR.equals(protocol);
    }

    public static String normalize(String str) {
        return normalize(str, false);
    }

    public static long size(URL url) throws IOException {
        if (isFileURL(url)) {
            File file = FileUtil.file(url);
            long length = file.length();
            if (length != 0 || file.exists()) {
                return length;
            }
            throw new IORuntimeException("File not exist or size is zero!");
        }
        try {
            URLConnection uRLConnectionOpenConnection = url.openConnection();
            useCachesIfNecessary(uRLConnectionOpenConnection);
            if (uRLConnectionOpenConnection instanceof HttpURLConnection) {
                ((HttpURLConnection) uRLConnectionOpenConnection).setRequestMethod("HEAD");
            }
            return uRLConnectionOpenConnection.getContentLengthLong();
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        }
    }

    public static URI toURI(URL url) throws UtilException {
        return toURI(url, false);
    }

    public static URL toUrlForHttp(String str) {
        return toUrlForHttp(str, null);
    }

    public static URL url(URI uri) throws UtilException {
        if (uri == null) {
            return null;
        }
        try {
            return uri.toURL();
        } catch (MalformedURLException e2) {
            throw new UtilException(e2);
        }
    }

    public static void useCachesIfNecessary(URLConnection uRLConnection) {
        uRLConnection.setUseCaches(uRLConnection.getClass().getSimpleName().startsWith("JNLP"));
    }

    public static String decode(String str, Charset charset) {
        return URLDecoder.decode(str, charset);
    }

    public static String getDataUri(String str, Charset charset, String str2, String str3) {
        StringBuilder sbBuilder = CharSequenceUtil.builder("data:");
        if (CharSequenceUtil.isNotBlank(str)) {
            sbBuilder.append(str);
        }
        if (charset != null) {
            sbBuilder.append(";charset=");
            sbBuilder.append(charset.name());
        }
        if (CharSequenceUtil.isNotBlank(str2)) {
            sbBuilder.append(';');
            sbBuilder.append(str2);
        }
        sbBuilder.append(',');
        sbBuilder.append(str3);
        return sbBuilder.toString();
    }

    public static URL getURL(String str, Class<?> cls) {
        return ResourceUtil.getResource(str, cls);
    }

    public static String normalize(String str, boolean z2) {
        return normalize(str, z2, false);
    }

    public static URI toURI(URL url, boolean z2) throws UtilException {
        if (url == null) {
            return null;
        }
        return toURI(url.toString(), z2);
    }

    public static URL toUrlForHttp(String str, URLStreamHandler uRLStreamHandler) throws IllegalArgumentException {
        Assert.notBlank(str, "Url is blank !", new Object[0]);
        try {
            return new URL((URL) null, encodeBlank(str), uRLStreamHandler);
        } catch (MalformedURLException e2) {
            throw new UtilException(e2);
        }
    }

    public static String decode(String str, Charset charset, boolean z2) {
        return URLDecoder.decode(str, charset, z2);
    }

    public static URL getURL(File file) throws IllegalArgumentException {
        Assert.notNull(file, "File is null !", new Object[0]);
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e2) {
            throw new UtilException(e2, "Error occured when get URL!", new Object[0]);
        }
    }

    public static String normalize(String str, boolean z2, boolean z3) throws UtilException {
        String strSubPre;
        String strSubSuf;
        if (CharSequenceUtil.isBlank(str)) {
            return str;
        }
        int iIndexOf = str.indexOf("://");
        if (iIndexOf > 0) {
            int i2 = iIndexOf + 3;
            strSubPre = CharSequenceUtil.subPre(str, i2);
            str = CharSequenceUtil.subSuf(str, i2);
        } else {
            strSubPre = DefaultWebClient.HTTP_SCHEME;
        }
        int iIndexOf2 = CharSequenceUtil.indexOf(str, '?');
        String strEncode = null;
        if (iIndexOf2 > 0) {
            strSubSuf = CharSequenceUtil.subSuf(str, iIndexOf2);
            str = CharSequenceUtil.subPre(str, iIndexOf2);
        } else {
            strSubSuf = null;
        }
        if (CharSequenceUtil.isNotEmpty(str)) {
            str = str.replaceAll("^[\\\\/]+", "").replace(StrPool.BACKSLASH, "/");
            if (z3) {
                str = str.replaceAll("//+", "/");
            }
        }
        int iIndexOf3 = CharSequenceUtil.indexOf(str, '/');
        if (iIndexOf3 > 0) {
            String strSubPre2 = CharSequenceUtil.subPre(str, iIndexOf3);
            strEncode = CharSequenceUtil.subSuf(str, iIndexOf3);
            str = strSubPre2;
        }
        if (z2) {
            strEncode = URLEncodeUtil.encode(strEncode);
        }
        return strSubPre + str + CharSequenceUtil.nullToEmpty(strEncode) + CharSequenceUtil.nullToEmpty(strSubSuf);
    }

    public static URI toURI(String str) throws UtilException {
        return toURI(str, false);
    }

    public static URL url(String str) {
        return url(str, null);
    }

    public static String decode(String str, String str2) throws UtilException {
        return decode(str, CharSequenceUtil.isEmpty(str2) ? null : CharsetUtil.charset(str2));
    }

    public static URI toURI(String str, boolean z2) throws UtilException {
        if (z2) {
            str = URLEncodeUtil.encode(str);
        }
        try {
            return new URI(CharSequenceUtil.trim(str));
        } catch (URISyntaxException e2) {
            throw new UtilException(e2);
        }
    }

    public static URL url(String str, URLStreamHandler uRLStreamHandler) {
        if (str == null) {
            return null;
        }
        if (str.startsWith(CLASSPATH_URL_PREFIX)) {
            return ClassLoaderUtil.getClassLoader().getResource(str.substring(10));
        }
        try {
            return new URL((URL) null, str, uRLStreamHandler);
        } catch (MalformedURLException e2) {
            try {
                return new File(str).toURI().toURL();
            } catch (MalformedURLException unused) {
                throw new UtilException(e2);
            }
        }
    }
}
