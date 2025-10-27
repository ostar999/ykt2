package com.plv.thirdpart.blankj.utilcode.util;

import android.text.TextUtils;
import com.heytap.mcssdk.constant.a;

/* loaded from: classes5.dex */
public final class StringUtils {
    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        int length;
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || (length = charSequence.length()) != charSequence2.length()) {
            return false;
        }
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            return charSequence.equals(charSequence2);
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (charSequence.charAt(i2) != charSequence2.charAt(i2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsIgnoreCase(String str, String str2) {
        return str == null ? str2 == null : str.equalsIgnoreCase(str2);
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isTrimEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static int length(CharSequence charSequence) {
        if (charSequence == null) {
            return 0;
        }
        return charSequence.length();
    }

    public static String lowerFirstLetter(String str) {
        if (isEmpty(str) || !Character.isUpperCase(str.charAt(0))) {
            return str;
        }
        return String.valueOf((char) (str.charAt(0) + ' ')) + str.substring(1);
    }

    public static String null2Length0(String str) {
        return str == null ? "" : str;
    }

    public static String reverse(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int length = length(str);
        if (length <= 1) {
            return str;
        }
        int i2 = length >> 1;
        char[] charArray = str.toCharArray();
        for (int i3 = 0; i3 < i2; i3++) {
            char c3 = charArray[i3];
            int i4 = (length - i3) - 1;
            charArray[i3] = charArray[i4];
            charArray[i4] = c3;
        }
        return new String(charArray);
    }

    public static String toDBC(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i2 = 0; i2 < length; i2++) {
            char c3 = charArray[i2];
            if (c3 == 12288) {
                charArray[i2] = ' ';
            } else if (65281 > c3 || c3 > 65374) {
                charArray[i2] = c3;
            } else {
                charArray[i2] = (char) (c3 - 65248);
            }
        }
        return new String(charArray);
    }

    public static String toSBC(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        for (int i2 = 0; i2 < length; i2++) {
            char c3 = charArray[i2];
            if (c3 == ' ') {
                charArray[i2] = 12288;
            } else if ('!' > c3 || c3 > '~') {
                charArray[i2] = c3;
            } else {
                charArray[i2] = (char) (c3 + 65248);
            }
        }
        return new String(charArray);
    }

    public static String toWString(long j2) {
        if (j2 > a.f7153q) {
            return String.format("%.1f", Double.valueOf(j2 / 10000.0d)) + "w";
        }
        return j2 + "";
    }

    public static String upperFirstLetter(String str) {
        if (isEmpty(str) || !Character.isLowerCase(str.charAt(0))) {
            return str;
        }
        return String.valueOf((char) (str.charAt(0) - ' ')) + str.substring(1);
    }
}
