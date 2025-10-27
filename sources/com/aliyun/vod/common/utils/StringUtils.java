package com.aliyun.vod.common.utils;

import android.webkit.URLUtil;
import cn.hutool.core.text.StrPool;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class StringUtils {
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char cCharAt = str.charAt(0);
        if (!Character.isLetter(cCharAt) || Character.isUpperCase(cCharAt)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        sb.append(Character.toUpperCase(cCharAt));
        sb.append(str.substring(1));
        return sb.toString();
    }

    public static String fullWidthToHalfWidth(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            char c3 = charArray[i2];
            if (c3 == 12288) {
                charArray[i2] = ' ';
            } else if (c3 < 65281 || c3 > 65374) {
                charArray[i2] = c3;
            } else {
                charArray[i2] = (char) (c3 - 65248);
            }
        }
        return new String(charArray);
    }

    public static String getHrefInnerHtml(String str) {
        if (isEmpty(str)) {
            return "";
        }
        Matcher matcher = Pattern.compile(".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*", 2).matcher(str);
        return matcher.matches() ? matcher.group(1) : str;
    }

    public static String halfWidthToFullWidth(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        for (int i2 = 0; i2 < charArray.length; i2++) {
            char c3 = charArray[i2];
            if (c3 == ' ') {
                charArray[i2] = 12288;
            } else if (c3 < '!' || c3 > '~') {
                charArray[i2] = c3;
            } else {
                charArray[i2] = (char) (c3 + 65248);
            }
        }
        return new String(charArray);
    }

    public static String htmlEscapeCharsToString(String str) {
        return isEmpty(str) ? str : str.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0) && isBlank(str);
    }

    public static boolean isUriPath(String str) {
        return URLUtil.isValidUrl(str);
    }

    public static int length(CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        return charSequence.length();
    }

    public static String nullStrToEmpty(Object obj) {
        return obj == null ? "" : obj instanceof String ? (String) obj : obj.toString();
    }

    public static String sqliteEscape(String str) {
        return str.replace("/", "//").replace("'", "''").replace(StrPool.BRACKET_START, "/[").replace(StrPool.BRACKET_END, "/]").replace("%", "/%").replace("&", "/&").replace(StrPool.UNDERLINE, "/_").replace("(", "/(").replace(")", "/)");
    }

    public static String subString(String str) {
        int iLastIndexOf = str.lastIndexOf(47);
        return iLastIndexOf >= 0 ? str.substring(iLastIndexOf + 1) : str;
    }

    public static String utf8Encode(String str) {
        if (isEmpty(str) || str.getBytes().length == str.length()) {
            return str;
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("UnsupportedEncodingException occurred. ", e2);
        }
    }

    public static String utf8Encode(String str, String str2) {
        if (isEmpty(str) || str.getBytes().length == str.length()) {
            return str;
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str2;
        }
    }
}
