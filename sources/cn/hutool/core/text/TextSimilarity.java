package cn.hutool.core.text;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class TextSimilarity {
    private static int[][] generateMatrix(String str, String str2) {
        int length = str.length();
        int length2 = str2.length();
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, length + 1, length2 + 1);
        for (int i2 = 1; i2 <= length; i2++) {
            for (int i3 = 1; i3 <= length2; i3++) {
                int i4 = i2 - 1;
                int i5 = i3 - 1;
                if (str.charAt(i4) == str2.charAt(i5)) {
                    iArr[i2][i3] = iArr[i4][i5] + 1;
                } else {
                    int[] iArr2 = iArr[i2];
                    iArr2[i3] = Math.max(iArr2[i5], iArr[i4][i3]);
                }
            }
        }
        return iArr;
    }

    private static boolean isValidChar(char c3) {
        return (c3 >= 19968 && c3 <= 40959) || (c3 >= 'a' && c3 <= 'z') || ((c3 >= 'A' && c3 <= 'Z') || (c3 >= '0' && c3 <= '9'));
    }

    public static String longestCommonSubstring(String str, String str2) {
        int[][] iArrGenerateMatrix = generateMatrix(str, str2);
        int length = str.length();
        int length2 = str2.length();
        int i2 = iArrGenerateMatrix[length][length2];
        char[] cArr = new char[i2];
        int i3 = i2 - 1;
        while (true) {
            int[] iArr = iArrGenerateMatrix[length];
            int i4 = iArr[length2];
            if (i4 == 0) {
                return new String(cArr);
            }
            if (i4 == iArr[length2 - 1]) {
                length2--;
            } else {
                int i5 = length - 1;
                if (i4 != iArrGenerateMatrix[i5][length2]) {
                    cArr[i3] = str.charAt(i5);
                    i3--;
                    length2--;
                }
                length--;
            }
        }
    }

    private static int longestCommonSubstringLength(String str, String str2) {
        int length = str.length();
        return generateMatrix(str, str2)[length][str2.length()];
    }

    private static String removeSign(String str) {
        int length = str.length();
        StringBuilder sbBuilder = StrUtil.builder(length);
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (isValidChar(cCharAt)) {
                sbBuilder.append(cCharAt);
            }
        }
        return sbBuilder.toString();
    }

    public static double similar(String str, String str2) {
        String strRemoveSign;
        String strRemoveSign2;
        if (str.length() < str2.length()) {
            strRemoveSign = removeSign(str2);
            strRemoveSign2 = removeSign(str);
        } else {
            String strRemoveSign3 = removeSign(str);
            String strRemoveSign4 = removeSign(str2);
            strRemoveSign = strRemoveSign3;
            strRemoveSign2 = strRemoveSign4;
        }
        int iMax = Math.max(strRemoveSign.length(), strRemoveSign2.length());
        if (iMax == 0) {
            return 1.0d;
        }
        return NumberUtil.div(longestCommonSubstringLength(strRemoveSign, strRemoveSign2), iMax);
    }

    public static String similar(String str, String str2, int i2) {
        return NumberUtil.formatPercent(similar(str, str2), i2);
    }
}
