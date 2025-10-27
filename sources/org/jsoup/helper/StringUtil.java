package org.jsoup.helper;

import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes9.dex */
public final class StringUtil {
    private static final String[] padding = {"", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          "};

    public static void appendNormalisedWhitespace(StringBuilder sb, String str, boolean z2) {
        int length = str.length();
        int iCharCount = 0;
        boolean z3 = false;
        boolean z4 = false;
        while (iCharCount < length) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (!isWhitespace(iCodePointAt)) {
                sb.appendCodePoint(iCodePointAt);
                z4 = false;
                z3 = true;
            } else if ((!z2 || z3) && !z4) {
                sb.append(' ');
                z4 = true;
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
    }

    public static boolean in(String str, String... strArr) {
        for (String str2 : strArr) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlank(String str) {
        if (str != null && str.length() != 0) {
            int length = str.length();
            for (int i2 = 0; i2 < length; i2++) {
                if (!isWhitespace(str.codePointAt(i2))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isDigit(str.codePointAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isWhitespace(int i2) {
        return i2 == 32 || i2 == 9 || i2 == 10 || i2 == 12 || i2 == 13;
    }

    public static String join(Collection collection, String str) {
        return join(collection.iterator(), str);
    }

    public static String normaliseWhitespace(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        appendNormalisedWhitespace(sb, str, false);
        return sb.toString();
    }

    public static String padding(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("width must be > 0");
        }
        String[] strArr = padding;
        if (i2 < strArr.length) {
            return strArr[i2];
        }
        char[] cArr = new char[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            cArr[i3] = ' ';
        }
        return String.valueOf(cArr);
    }

    public static String join(Iterator it, String str) {
        if (!it.hasNext()) {
            return "";
        }
        String string = it.next().toString();
        if (!it.hasNext()) {
            return string;
        }
        StringBuilder sb = new StringBuilder(64);
        sb.append(string);
        while (it.hasNext()) {
            sb.append(str);
            sb.append(it.next());
        }
        return sb.toString();
    }
}
