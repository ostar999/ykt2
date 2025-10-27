package cn.hutool.core.text;

import cn.hutool.core.util.RandomUtil;
import com.plv.socket.user.PLVSocketUserConstant;
import com.psychiatrygarden.utils.CommonParameter;
import com.yikaobang.yixue.R2;

/* loaded from: classes.dex */
public class PasswdStrength {
    private static final String[] DICTIONARY = {CommonParameter.password, "abc123", "iloveyou", "adobe123", "123123", "sunshine", "1314520", "a1b2c3", "123qwe", "aaa111", "qweasd", PLVSocketUserConstant.ROLE_ADMIN, "passwd"};
    private static final int[] SIZE_TABLE = {9, 99, 999, R2.drawable.homepage_shangcheng_press, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE};

    public enum CHAR_TYPE {
        NUM,
        SMALL_LETTER,
        CAPITAL_LETTER,
        OTHER_CHAR
    }

    public enum PASSWD_LEVEL {
        EASY,
        MIDIUM,
        STRONG,
        VERY_STRONG,
        EXTREMELY_STRONG
    }

    public static int check(String str) throws NumberFormatException {
        if (str == null) {
            throw new IllegalArgumentException("password is empty");
        }
        int length = str.length();
        CHAR_TYPE char_type = CHAR_TYPE.NUM;
        int i2 = countLetter(str, char_type) > 0 ? 1 : 0;
        CHAR_TYPE char_type2 = CHAR_TYPE.SMALL_LETTER;
        if (countLetter(str, char_type2) > 0) {
            i2++;
        }
        if (length > 4 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) > 0) {
            i2++;
        }
        if (length > 6 && countLetter(str, CHAR_TYPE.OTHER_CHAR) > 0) {
            i2++;
        }
        if ((length > 4 && countLetter(str, char_type) > 0 && countLetter(str, char_type2) > 0) || ((countLetter(str, char_type) > 0 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) > 0) || ((countLetter(str, char_type) > 0 && countLetter(str, CHAR_TYPE.OTHER_CHAR) > 0) || ((countLetter(str, char_type2) > 0 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) > 0) || ((countLetter(str, char_type2) > 0 && countLetter(str, CHAR_TYPE.OTHER_CHAR) > 0) || (countLetter(str, CHAR_TYPE.CAPITAL_LETTER) > 0 && countLetter(str, CHAR_TYPE.OTHER_CHAR) > 0)))))) {
            i2++;
        }
        if ((length > 6 && countLetter(str, char_type) > 0 && countLetter(str, char_type2) > 0 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) > 0) || ((countLetter(str, char_type) > 0 && countLetter(str, char_type2) > 0 && countLetter(str, CHAR_TYPE.OTHER_CHAR) > 0) || ((countLetter(str, char_type) > 0 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) > 0 && countLetter(str, CHAR_TYPE.OTHER_CHAR) > 0) || (countLetter(str, char_type2) > 0 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) > 0 && countLetter(str, CHAR_TYPE.OTHER_CHAR) > 0)))) {
            i2++;
        }
        if (length > 8 && countLetter(str, char_type) > 0 && countLetter(str, char_type2) > 0 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) > 0 && countLetter(str, CHAR_TYPE.OTHER_CHAR) > 0) {
            i2++;
        }
        if ((length > 6 && countLetter(str, char_type) >= 3 && countLetter(str, char_type2) >= 3) || ((countLetter(str, char_type) >= 3 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) >= 3) || ((countLetter(str, char_type) >= 3 && countLetter(str, CHAR_TYPE.OTHER_CHAR) >= 2) || ((countLetter(str, char_type2) >= 3 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) >= 3) || ((countLetter(str, char_type2) >= 3 && countLetter(str, CHAR_TYPE.OTHER_CHAR) >= 2) || (countLetter(str, CHAR_TYPE.CAPITAL_LETTER) >= 3 && countLetter(str, CHAR_TYPE.OTHER_CHAR) >= 2)))))) {
            i2++;
        }
        if ((length > 8 && countLetter(str, char_type) >= 2 && countLetter(str, char_type2) >= 2 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) >= 2) || ((countLetter(str, char_type) >= 2 && countLetter(str, char_type2) >= 2 && countLetter(str, CHAR_TYPE.OTHER_CHAR) >= 2) || ((countLetter(str, char_type) >= 2 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) >= 2 && countLetter(str, CHAR_TYPE.OTHER_CHAR) >= 2) || (countLetter(str, char_type2) >= 2 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) >= 2 && countLetter(str, CHAR_TYPE.OTHER_CHAR) >= 2)))) {
            i2++;
        }
        if (length > 10 && countLetter(str, char_type) >= 2 && countLetter(str, char_type2) >= 2 && countLetter(str, CHAR_TYPE.CAPITAL_LETTER) >= 2 && countLetter(str, CHAR_TYPE.OTHER_CHAR) >= 2) {
            i2++;
        }
        CHAR_TYPE char_type3 = CHAR_TYPE.OTHER_CHAR;
        if (countLetter(str, char_type3) >= 3) {
            i2++;
        }
        if (countLetter(str, char_type3) >= 6) {
            i2++;
        }
        if (length > 12) {
            i2++;
            if (length >= 16) {
                i2++;
            }
        }
        if (RandomUtil.BASE_CHAR.indexOf(str) > 0 || "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(str) > 0) {
            i2--;
        }
        if ("qwertyuiop".indexOf(str) > 0 || "asdfghjkl".indexOf(str) > 0 || "zxcvbnm".indexOf(str) > 0) {
            i2--;
        }
        if (CharSequenceUtil.isNumeric(str) && ("01234567890".indexOf(str) > 0 || "09876543210".indexOf(str) > 0)) {
            i2--;
        }
        if (countLetter(str, char_type) == length || countLetter(str, char_type2) == length || countLetter(str, CHAR_TYPE.CAPITAL_LETTER) == length) {
            i2--;
        }
        if (length % 2 == 0) {
            int i3 = length / 2;
            String strSubstring = str.substring(0, i3);
            String strSubstring2 = str.substring(i3);
            if (strSubstring.equals(strSubstring2)) {
                i2--;
            }
            if (CharSequenceUtil.isCharEquals(strSubstring) && CharSequenceUtil.isCharEquals(strSubstring2)) {
                i2--;
            }
        }
        if (length % 3 == 0) {
            int i4 = length / 3;
            String strSubstring3 = str.substring(0, i4);
            int i5 = i4 * 2;
            String strSubstring4 = str.substring(i4, i5);
            Object objSubstring = str.substring(i5);
            if (strSubstring3.equals(strSubstring4) && strSubstring4.equals(objSubstring)) {
                i2--;
            }
        }
        if (CharSequenceUtil.isNumeric(str) && length >= 6 && length <= 8) {
            int i6 = (length == 8 || length == 6) ? Integer.parseInt(str.substring(0, length - 4)) : 0;
            int iSizeOfInt = sizeOfInt(i6);
            int i7 = iSizeOfInt + 2;
            int i8 = Integer.parseInt(str.substring(iSizeOfInt, i7));
            int i9 = Integer.parseInt(str.substring(i7, length));
            if (i6 >= 1950 && i6 < 2050 && i8 >= 1 && i8 <= 12 && i9 >= 1 && i9 <= 31) {
                i2--;
            }
        }
        for (String str2 : DICTIONARY) {
            if (str.equals(str2) || str2.contains(str)) {
                i2--;
                break;
            }
        }
        if (length <= 6) {
            i2--;
            if (length <= 4) {
                i2--;
                if (length <= 3) {
                    i2 = 0;
                }
            }
        }
        if (CharSequenceUtil.isCharEquals(str)) {
            i2 = 0;
        }
        if (i2 < 0) {
            return 0;
        }
        return i2;
    }

    private static CHAR_TYPE checkCharacterType(char c3) {
        return (c3 < '0' || c3 > '9') ? (c3 < 'A' || c3 > 'Z') ? (c3 < 'a' || c3 > 'z') ? CHAR_TYPE.OTHER_CHAR : CHAR_TYPE.SMALL_LETTER : CHAR_TYPE.CAPITAL_LETTER : CHAR_TYPE.NUM;
    }

    private static int countLetter(String str, CHAR_TYPE char_type) {
        int length;
        if (str == null || (length = str.length()) <= 0) {
            return 0;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            if (checkCharacterType(str.charAt(i3)) == char_type) {
                i2++;
            }
        }
        return i2;
    }

    public static PASSWD_LEVEL getLevel(String str) {
        switch (check(str)) {
            case 0:
            case 1:
            case 2:
            case 3:
                return PASSWD_LEVEL.EASY;
            case 4:
            case 5:
            case 6:
                return PASSWD_LEVEL.MIDIUM;
            case 7:
            case 8:
            case 9:
                return PASSWD_LEVEL.STRONG;
            case 10:
            case 11:
            case 12:
                return PASSWD_LEVEL.VERY_STRONG;
            default:
                return PASSWD_LEVEL.EXTREMELY_STRONG;
        }
    }

    private static int sizeOfInt(int i2) {
        int i3 = 0;
        while (i2 > SIZE_TABLE[i3]) {
            i3++;
        }
        return i3 + 1;
    }
}
