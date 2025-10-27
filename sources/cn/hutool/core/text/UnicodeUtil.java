package cn.hutool.core.text;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.HexUtil;

/* loaded from: classes.dex */
public class UnicodeUtil {
    public static String toString(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return str;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        int i2 = 0;
        while (true) {
            int iIndexOfIgnoreCase = CharSequenceUtil.indexOfIgnoreCase(str, "\\u", i2);
            if (iIndexOfIgnoreCase == -1) {
                break;
            }
            sb.append((CharSequence) str, i2, iIndexOfIgnoreCase);
            if (iIndexOfIgnoreCase + 5 >= length) {
                i2 = iIndexOfIgnoreCase;
                break;
            }
            i2 = iIndexOfIgnoreCase + 2;
            int i3 = iIndexOfIgnoreCase + 6;
            try {
                sb.append((char) Integer.parseInt(str.substring(i2, i3), 16));
                i2 = i3;
            } catch (NumberFormatException unused) {
                sb.append((CharSequence) str, iIndexOfIgnoreCase, i2);
            }
        }
        if (i2 < length) {
            sb.append((CharSequence) str, i2, length);
        }
        return sb.toString();
    }

    public static String toUnicode(char c3) {
        return HexUtil.toUnicodeHex(c3);
    }

    public static String toUnicode(int i2) {
        return HexUtil.toUnicodeHex(i2);
    }

    public static String toUnicode(String str) {
        return toUnicode(str, true);
    }

    public static String toUnicode(String str, boolean z2) {
        if (CharSequenceUtil.isEmpty(str)) {
            return str;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(str.length() * 6);
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (z2 && CharUtil.isAsciiPrintable(cCharAt)) {
                sb.append(cCharAt);
            } else {
                sb.append(HexUtil.toUnicodeHex(cCharAt));
            }
        }
        return sb.toString();
    }
}
