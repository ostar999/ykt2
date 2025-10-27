package cn.hutool.core.util;

import cn.hutool.core.text.ASCIIStrCache;
import cn.hutool.core.text.CharPool;
import com.yikaobang.yixue.R2;

/* loaded from: classes.dex */
public class CharUtil implements CharPool {
    public static int digit16(int i2) {
        return Character.digit(i2, 16);
    }

    public static boolean equals(char c3, char c4, boolean z2) {
        return z2 ? Character.toLowerCase(c3) == Character.toLowerCase(c4) : c3 == c4;
    }

    public static int getType(int i2) {
        return Character.getType(i2);
    }

    public static boolean isAscii(char c3) {
        return c3 < 128;
    }

    public static boolean isAsciiControl(char c3) {
        return c3 < ' ' || c3 == 127;
    }

    public static boolean isAsciiPrintable(char c3) {
        return c3 >= ' ' && c3 < 127;
    }

    public static boolean isBlankChar(char c3) {
        return isBlankChar((int) c3);
    }

    public static boolean isChar(Object obj) {
        return (obj instanceof Character) || obj.getClass() == Character.TYPE;
    }

    public static boolean isCharClass(Class<?> cls) {
        return cls == Character.class || cls == Character.TYPE;
    }

    public static boolean isEmoji(char c3) {
        return !(c3 == 0 || c3 == '\t' || c3 == '\n' || c3 == '\r' || (c3 >= ' ' && c3 <= 55295) || ((c3 >= 57344 && c3 <= 65533) || (c3 >= 0 && c3 <= 65535)));
    }

    public static boolean isFileSeparator(char c3) {
        return '/' == c3 || '\\' == c3;
    }

    public static boolean isHexChar(char c3) {
        return isNumber(c3) || (c3 >= 'a' && c3 <= 'f') || (c3 >= 'A' && c3 <= 'F');
    }

    public static boolean isLetter(char c3) {
        return isLetterUpper(c3) || isLetterLower(c3);
    }

    public static boolean isLetterLower(char c3) {
        return c3 >= 'a' && c3 <= 'z';
    }

    public static boolean isLetterOrNumber(char c3) {
        return isLetter(c3) || isNumber(c3);
    }

    public static boolean isLetterUpper(char c3) {
        return c3 >= 'A' && c3 <= 'Z';
    }

    public static boolean isNumber(char c3) {
        return c3 >= '0' && c3 <= '9';
    }

    public static char toCloseByNumber(int i2) {
        if (i2 <= 20) {
            return (char) ((i2 + R2.drawable.ease_btn_cancel_pressed_shape) - 1);
        }
        throw new IllegalArgumentException("Number must be [1-20]");
    }

    public static char toCloseChar(char c3) {
        int i2;
        int i3;
        int i4 = 49;
        if (c3 < '1' || c3 > '9') {
            i4 = 65;
            if (c3 < 'A' || c3 > 'Z') {
                i4 = 97;
                i3 = c3;
                if (c3 >= 'a') {
                    i3 = c3;
                    if (c3 <= 'z') {
                        i2 = c3 + R2.drawable.ease_timestampe_bg;
                    }
                }
                return (char) i3;
            }
            i2 = c3 + R2.drawable.ease_icon_no_conversation;
        } else {
            i2 = c3 + R2.drawable.ease_btn_cancel_pressed_shape;
        }
        i3 = i2 - i4;
        return (char) i3;
    }

    public static String toString(char c3) {
        return ASCIIStrCache.toString(c3);
    }

    public static boolean isBlankChar(int i2) {
        return Character.isWhitespace(i2) || Character.isSpaceChar(i2) || i2 == 65279 || i2 == 8234 || i2 == 0 || i2 == 12644 || i2 == 10240 || i2 == 6158;
    }
}
