package cn.hutool.core.text;

import cn.hutool.core.util.CharUtil;

/* loaded from: classes.dex */
public class NamingCase {
    public static String toCamelCase(CharSequence charSequence) {
        return toCamelCase(charSequence, '_');
    }

    public static String toKebabCase(CharSequence charSequence) {
        return toSymbolCase(charSequence, CharPool.DASHED);
    }

    public static String toPascalCase(CharSequence charSequence) {
        return CharSequenceUtil.upperFirst(toCamelCase(charSequence));
    }

    public static String toSymbolCase(CharSequence charSequence, char c3) {
        if (charSequence == null) {
            return null;
        }
        int length = charSequence.length();
        StrBuilder strBuilder = new StrBuilder();
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            if (Character.isUpperCase(cCharAt)) {
                Character chValueOf = i2 > 0 ? Character.valueOf(charSequence.charAt(i2 - 1)) : null;
                Character chValueOf2 = i2 < charSequence.length() + (-1) ? Character.valueOf(charSequence.charAt(i2 + 1)) : null;
                if (chValueOf != null) {
                    if (c3 == chValueOf.charValue()) {
                        if (chValueOf2 == null || Character.isLowerCase(chValueOf2.charValue())) {
                            cCharAt = Character.toLowerCase(cCharAt);
                        }
                    } else if (Character.isLowerCase(chValueOf.charValue())) {
                        strBuilder.append(c3);
                        if (chValueOf2 == null || Character.isLowerCase(chValueOf2.charValue()) || CharUtil.isNumber(chValueOf2.charValue())) {
                            cCharAt = Character.toLowerCase(cCharAt);
                        }
                    } else if (chValueOf2 != null && Character.isLowerCase(chValueOf2.charValue())) {
                        strBuilder.append(c3);
                        cCharAt = Character.toLowerCase(cCharAt);
                    }
                } else if (chValueOf2 == null || Character.isLowerCase(chValueOf2.charValue())) {
                    cCharAt = Character.toLowerCase(cCharAt);
                }
            }
            strBuilder.append(cCharAt);
            i2++;
        }
        return strBuilder.toString();
    }

    public static String toUnderlineCase(CharSequence charSequence) {
        return toSymbolCase(charSequence, '_');
    }

    public static String toCamelCase(CharSequence charSequence, char c3) {
        return toCamelCase(charSequence, c3, true);
    }

    public static String toCamelCase(CharSequence charSequence, char c3, boolean z2) {
        if (charSequence == null) {
            return null;
        }
        String string = charSequence.toString();
        if (!CharSequenceUtil.contains(string, c3)) {
            return string;
        }
        int length = string.length();
        StringBuilder sb = new StringBuilder(length);
        boolean z3 = false;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = string.charAt(i2);
            if (cCharAt == c3) {
                z3 = true;
            } else if (z3) {
                sb.append(Character.toUpperCase(cCharAt));
                z3 = false;
            } else {
                if (z2) {
                    cCharAt = Character.toLowerCase(cCharAt);
                }
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }
}
