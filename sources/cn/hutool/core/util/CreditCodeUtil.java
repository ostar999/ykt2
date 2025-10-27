package cn.hutool.core.util;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.map.SafeConcurrentHashMap;
import cn.hutool.core.text.CharSequenceUtil;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class CreditCodeUtil {
    private static final char[] BASE_CODE_ARRAY;
    private static final Map<Character, Integer> CODE_INDEX_MAP;
    public static final Pattern CREDIT_CODE_PATTERN = PatternPool.CREDIT_CODE;
    private static final int[] WEIGHT = {1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28};

    static {
        char[] charArray = "0123456789ABCDEFGHJKLMNPQRTUWXY".toCharArray();
        BASE_CODE_ARRAY = charArray;
        CODE_INDEX_MAP = new SafeConcurrentHashMap(charArray.length);
        int i2 = 0;
        while (true) {
            char[] cArr = BASE_CODE_ARRAY;
            if (i2 >= cArr.length) {
                return;
            }
            CODE_INDEX_MAP.put(Character.valueOf(cArr[i2]), Integer.valueOf(i2));
            i2++;
        }
    }

    private static int getParityBit(CharSequence charSequence) {
        int iIntValue = 0;
        for (int i2 = 0; i2 < 17; i2++) {
            Integer num = CODE_INDEX_MAP.get(Character.valueOf(charSequence.charAt(i2)));
            if (num == null) {
                return -1;
            }
            iIntValue += num.intValue() * WEIGHT[i2];
        }
        int i3 = 31 - (iIntValue % 31);
        if (i3 == 31) {
            return 0;
        }
        return i3;
    }

    public static boolean isCreditCode(CharSequence charSequence) {
        int parityBit;
        return isCreditCodeSimple(charSequence) && (parityBit = getParityBit(charSequence)) >= 0 && charSequence.charAt(17) == BASE_CODE_ARRAY[parityBit];
    }

    public static boolean isCreditCodeSimple(CharSequence charSequence) {
        if (CharSequenceUtil.isBlank(charSequence)) {
            return false;
        }
        return ReUtil.isMatch(CREDIT_CODE_PATTERN, charSequence);
    }

    public static String randomCreditCode() {
        int i2;
        int i3;
        StringBuilder sb = new StringBuilder(18);
        int i4 = 0;
        while (true) {
            i2 = 2;
            if (i4 >= 2) {
                break;
            }
            sb.append(Character.toUpperCase(BASE_CODE_ARRAY[RandomUtil.randomInt(r2.length - 1)]));
            i4++;
        }
        while (true) {
            if (i2 >= 8) {
                break;
            }
            sb.append(BASE_CODE_ARRAY[RandomUtil.randomInt(10)]);
            i2++;
        }
        for (i3 = 8; i3 < 17; i3++) {
            sb.append(BASE_CODE_ARRAY[RandomUtil.randomInt(r2.length - 1)]);
        }
        String string = sb.toString();
        return string + BASE_CODE_ARRAY[getParityBit(string)];
    }
}
