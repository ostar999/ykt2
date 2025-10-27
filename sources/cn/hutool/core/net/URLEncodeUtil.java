package cn.hutool.core.net;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public class URLEncodeUtil {
    public static String encode(String str) throws UtilException {
        return encode(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String encodeAll(String str) {
        return encodeAll(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String encodeFragment(String str) throws UtilException {
        return encodeFragment(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String encodePathSegment(String str) throws UtilException {
        return encodePathSegment(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String encodeQuery(String str) throws UtilException {
        return encodeQuery(str, CharsetUtil.CHARSET_UTF_8);
    }

    public static String encode(String str, Charset charset) {
        return RFC3986.PATH.encode(str, charset, new char[0]);
    }

    public static String encodeAll(String str, Charset charset) throws UtilException {
        return RFC3986.UNRESERVED.encode(str, charset, new char[0]);
    }

    public static String encodeFragment(String str, Charset charset) {
        return CharSequenceUtil.isEmpty(str) ? str : RFC3986.FRAGMENT.encode(str, charset, new char[0]);
    }

    public static String encodePathSegment(String str, Charset charset) {
        return CharSequenceUtil.isEmpty(str) ? str : RFC3986.SEGMENT.encode(str, charset, new char[0]);
    }

    public static String encodeQuery(String str, Charset charset) {
        return RFC3986.QUERY.encode(str, charset, new char[0]);
    }
}
