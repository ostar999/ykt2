package net.sourceforge.pinyin4j;

import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/* loaded from: classes9.dex */
public class PinyinHelper {
    private PinyinHelper() {
    }

    private static String[] convertToGwoyeuRomatzyhStringArray(char c3) {
        String[] unformattedHanyuPinyinStringArray = getUnformattedHanyuPinyinStringArray(c3);
        if (unformattedHanyuPinyinStringArray == null) {
            return null;
        }
        String[] strArr = new String[unformattedHanyuPinyinStringArray.length];
        for (int i2 = 0; i2 < unformattedHanyuPinyinStringArray.length; i2++) {
            strArr[i2] = GwoyeuRomatzyhTranslator.convertHanyuPinyinToGwoyeuRomatzyh(unformattedHanyuPinyinStringArray[i2]);
        }
        return strArr;
    }

    private static String[] convertToTargetPinyinStringArray(char c3, PinyinRomanizationType pinyinRomanizationType) {
        String[] unformattedHanyuPinyinStringArray = getUnformattedHanyuPinyinStringArray(c3);
        if (unformattedHanyuPinyinStringArray == null) {
            return null;
        }
        String[] strArr = new String[unformattedHanyuPinyinStringArray.length];
        for (int i2 = 0; i2 < unformattedHanyuPinyinStringArray.length; i2++) {
            strArr[i2] = PinyinRomanizationTranslator.convertRomanizationSystem(unformattedHanyuPinyinStringArray[i2], PinyinRomanizationType.HANYU_PINYIN, pinyinRomanizationType);
        }
        return strArr;
    }

    private static String getFirstHanyuPinyinString(char c3, HanyuPinyinOutputFormat hanyuPinyinOutputFormat) throws BadHanyuPinyinOutputFormatCombination {
        String[] formattedHanyuPinyinStringArray = getFormattedHanyuPinyinStringArray(c3, hanyuPinyinOutputFormat);
        if (formattedHanyuPinyinStringArray == null || formattedHanyuPinyinStringArray.length <= 0) {
            return null;
        }
        return formattedHanyuPinyinStringArray[0];
    }

    private static String[] getFormattedHanyuPinyinStringArray(char c3, HanyuPinyinOutputFormat hanyuPinyinOutputFormat) throws BadHanyuPinyinOutputFormatCombination {
        String[] unformattedHanyuPinyinStringArray = getUnformattedHanyuPinyinStringArray(c3);
        if (unformattedHanyuPinyinStringArray == null) {
            return null;
        }
        for (int i2 = 0; i2 < unformattedHanyuPinyinStringArray.length; i2++) {
            unformattedHanyuPinyinStringArray[i2] = PinyinFormatter.formatHanyuPinyin(unformattedHanyuPinyinStringArray[i2], hanyuPinyinOutputFormat);
        }
        return unformattedHanyuPinyinStringArray;
    }

    private static String[] getUnformattedHanyuPinyinStringArray(char c3) {
        return ChineseToPinyinResource.getInstance().getHanyuPinyinStringArray(c3);
    }

    public static String[] toGwoyeuRomatzyhStringArray(char c3) {
        return convertToGwoyeuRomatzyhStringArray(c3);
    }

    public static String toHanyuPinyinString(String str, HanyuPinyinOutputFormat hanyuPinyinOutputFormat, String str2) throws BadHanyuPinyinOutputFormatCombination {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < str.length(); i2++) {
            String firstHanyuPinyinString = getFirstHanyuPinyinString(str.charAt(i2), hanyuPinyinOutputFormat);
            if (firstHanyuPinyinString != null) {
                stringBuffer.append(firstHanyuPinyinString);
                if (i2 != str.length() - 1) {
                    stringBuffer.append(str2);
                }
            } else {
                stringBuffer.append(str.charAt(i2));
            }
        }
        return stringBuffer.toString();
    }

    public static String[] toHanyuPinyinStringArray(char c3) {
        return getUnformattedHanyuPinyinStringArray(c3);
    }

    public static String[] toHanyuPinyinStringArray(char c3, HanyuPinyinOutputFormat hanyuPinyinOutputFormat) throws BadHanyuPinyinOutputFormatCombination {
        return getFormattedHanyuPinyinStringArray(c3, hanyuPinyinOutputFormat);
    }

    public static String[] toMPS2PinyinStringArray(char c3) {
        return convertToTargetPinyinStringArray(c3, PinyinRomanizationType.MPS2_PINYIN);
    }

    public static String[] toTongyongPinyinStringArray(char c3) {
        return convertToTargetPinyinStringArray(c3, PinyinRomanizationType.TONGYONG_PINYIN);
    }

    public static String[] toWadeGilesPinyinStringArray(char c3) {
        return convertToTargetPinyinStringArray(c3, PinyinRomanizationType.WADEGILES_PINYIN);
    }

    public static String[] toYalePinyinStringArray(char c3) {
        return convertToTargetPinyinStringArray(c3, PinyinRomanizationType.YALE_PINYIN);
    }
}
