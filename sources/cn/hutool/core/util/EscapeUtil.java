package cn.hutool.core.util;

import cn.hutool.core.lang.Filter;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.escape.Html4Escape;
import cn.hutool.core.text.escape.Html4Unescape;
import cn.hutool.core.text.escape.XmlEscape;
import cn.hutool.core.text.escape.XmlUnescape;

/* loaded from: classes.dex */
public class EscapeUtil {
    private static final Filter<Character> JS_ESCAPE_FILTER = new Filter() { // from class: cn.hutool.core.util.n
        @Override // cn.hutool.core.lang.Filter
        public final boolean accept(Object obj) {
            return EscapeUtil.lambda$static$0((Character) obj);
        }
    };
    private static final String NOT_ESCAPE_CHARS = "*@-_+./";

    public static String escape(CharSequence charSequence) {
        return escape(charSequence, JS_ESCAPE_FILTER);
    }

    public static String escapeAll(CharSequence charSequence) {
        return escape(charSequence, new Filter() { // from class: cn.hutool.core.util.o
            @Override // cn.hutool.core.lang.Filter
            public final boolean accept(Object obj) {
                return EscapeUtil.lambda$escapeAll$1((Character) obj);
            }
        });
    }

    public static String escapeHtml4(CharSequence charSequence) {
        return new Html4Escape().replace(charSequence).toString();
    }

    public static String escapeXml(CharSequence charSequence) {
        return new XmlEscape().replace(charSequence).toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$escapeAll$1(Character ch) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$static$0(Character ch) {
        return !(Character.isDigit(ch.charValue()) || Character.isLowerCase(ch.charValue()) || Character.isUpperCase(ch.charValue()) || CharSequenceUtil.contains(NOT_ESCAPE_CHARS, ch.charValue()));
    }

    public static String safeUnescape(String str) {
        try {
            return unescape(str);
        } catch (Exception unused) {
            return str;
        }
    }

    public static String unescape(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length());
        int length = 0;
        while (length < str.length()) {
            int iIndexOf = str.indexOf("%", length);
            if (iIndexOf == length) {
                int i2 = iIndexOf + 1;
                if (str.charAt(i2) == 'u') {
                    int i3 = iIndexOf + 2;
                    iIndexOf += 6;
                    sb.append((char) Integer.parseInt(str.substring(i3, iIndexOf), 16));
                } else {
                    iIndexOf += 3;
                    sb.append((char) Integer.parseInt(str.substring(i2, iIndexOf), 16));
                }
            } else if (iIndexOf == -1) {
                sb.append(str.substring(length));
                length = str.length();
            } else {
                sb.append((CharSequence) str, length, iIndexOf);
            }
            length = iIndexOf;
        }
        return sb.toString();
    }

    public static String unescapeHtml4(CharSequence charSequence) {
        return new Html4Unescape().replace(charSequence).toString();
    }

    public static String unescapeXml(CharSequence charSequence) {
        return new XmlUnescape().replace(charSequence).toString();
    }

    public static String escape(CharSequence charSequence, Filter<Character> filter) {
        if (CharSequenceUtil.isEmpty(charSequence)) {
            return CharSequenceUtil.str(charSequence);
        }
        StringBuilder sb = new StringBuilder(charSequence.length() * 6);
        for (int i2 = 0; i2 < charSequence.length(); i2++) {
            char cCharAt = charSequence.charAt(i2);
            if (!filter.accept(Character.valueOf(cCharAt))) {
                sb.append(cCharAt);
            } else if (cCharAt < 256) {
                sb.append("%");
                if (cCharAt < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toString(cCharAt, 16));
            } else {
                sb.append("%u");
                if (cCharAt <= 4095) {
                    sb.append("0");
                }
                sb.append(Integer.toString(cCharAt, 16));
            }
        }
        return sb.toString();
    }
}
