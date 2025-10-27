package com.plv.thirdpart.blankj.utilcode.util;

import com.plv.thirdpart.blankj.utilcode.constant.RegexConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public final class RegexUtils {
    private RegexUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static List<String> getMatches(String str, CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Matcher matcher = Pattern.compile(str).matcher(charSequence);
        while (matcher.find()) {
            arrayList.add(matcher.group());
        }
        return arrayList;
    }

    public static String getReplaceAll(String str, String str2, String str3) {
        if (str == null) {
            return null;
        }
        return Pattern.compile(str2).matcher(str).replaceAll(str3);
    }

    public static String getReplaceFirst(String str, String str2, String str3) {
        if (str == null) {
            return null;
        }
        return Pattern.compile(str2).matcher(str).replaceFirst(str3);
    }

    public static String[] getSplits(String str, String str2) {
        if (str == null) {
            return null;
        }
        return str.split(str2);
    }

    public static boolean isDate(CharSequence charSequence) {
        return isMatch("^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$", charSequence);
    }

    public static boolean isEmail(CharSequence charSequence) {
        return isMatch("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", charSequence);
    }

    public static boolean isIDCard15(CharSequence charSequence) {
        return isMatch("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$", charSequence);
    }

    public static boolean isIDCard18(CharSequence charSequence) {
        return isMatch("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$", charSequence);
    }

    public static boolean isIP(CharSequence charSequence) {
        return isMatch("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)", charSequence);
    }

    public static boolean isMatch(String str, CharSequence charSequence) {
        return charSequence != null && charSequence.length() > 0 && Pattern.matches(str, charSequence);
    }

    public static boolean isMobileExact(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_MOBILE_EXACT, charSequence);
    }

    public static boolean isMobileSimple(CharSequence charSequence) {
        return isMatch("^[1]\\d{10}$", charSequence);
    }

    public static boolean isTel(CharSequence charSequence) {
        return isMatch(RegexConstants.REGEX_TEL, charSequence);
    }

    public static boolean isURL(CharSequence charSequence) {
        return isMatch("[a-zA-z]+://[^\\s]*", charSequence);
    }

    public static boolean isUsername(CharSequence charSequence) {
        return isMatch("^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$", charSequence);
    }

    public static boolean isZh(CharSequence charSequence) {
        return isMatch("^[\\u4e00-\\u9fa5]+$", charSequence);
    }
}
