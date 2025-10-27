package com.alibaba.sdk.android.tbrest.utils;

import java.util.Map;

/* loaded from: classes2.dex */
public class StringUtils {
    public static String convertMapToString(Map<String, String> map) {
        if (map == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        boolean z2 = true;
        for (String str : map.keySet()) {
            String str2 = map.get(str);
            if (str2 != null && str != null) {
                if (z2) {
                    if ("--invalid--".equals(str2)) {
                        stringBuffer.append(str);
                    } else {
                        stringBuffer.append(str + "=" + str2);
                    }
                    z2 = false;
                } else if ("--invalid--".equals(str2)) {
                    stringBuffer.append(",");
                    stringBuffer.append(str);
                } else {
                    stringBuffer.append(",");
                    stringBuffer.append(str + "=" + str2);
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String convertObjectToString(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Integer) {
            return "" + ((Integer) obj).intValue();
        }
        if (obj instanceof Long) {
            return "" + ((Long) obj).longValue();
        }
        if (obj instanceof Double) {
            return "" + ((Double) obj).doubleValue();
        }
        if (obj instanceof Float) {
            return "" + ((Float) obj).floatValue();
        }
        if (obj instanceof Short) {
            return "" + ((int) ((Short) obj).shortValue());
        }
        if (!(obj instanceof Byte)) {
            return obj instanceof Boolean ? ((Boolean) obj).toString() : obj instanceof Character ? ((Character) obj).toString() : obj.toString();
        }
        return "" + ((int) ((Byte) obj).byteValue());
    }

    public static String defaultString(String str, String str2) {
        return isBlank(str) ? str2 : str;
    }

    public static int hashCode(String str) {
        if (str.length() <= 0) {
            return 0;
        }
        int i2 = 0;
        for (char c3 : str.toCharArray()) {
            i2 = (i2 * 31) + c3;
        }
        return i2;
    }

    public static boolean isBlank(CharSequence charSequence) {
        int length;
        if (charSequence != null && (length = charSequence.length()) != 0) {
            for (int i2 = 0; i2 < length; i2++) {
                if (!Character.isWhitespace(charSequence.charAt(i2))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() <= 0;
    }

    public static boolean isNotBlank(CharSequence charSequence) {
        return !isBlank(charSequence);
    }
}
