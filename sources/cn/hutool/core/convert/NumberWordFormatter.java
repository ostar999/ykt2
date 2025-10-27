package cn.hutool.core.convert;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.vod.log.struct.AliyunLogKey;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes.dex */
public class NumberWordFormatter {
    private static final String[] NUMBER = {"", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};
    private static final String[] NUMBER_TEEN = {"TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN"};
    private static final String[] NUMBER_TEN = {"TEN", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY"};
    private static final String[] NUMBER_MORE = {"", "THOUSAND", "MILLION", "BILLION"};
    private static final String[] NUMBER_SUFFIX = {"k", "w", "", "m", "", "", "b", "", "", "t", "", "", "p", "", "", AliyunLogKey.KEY_EVENT};

    public static String format(Object obj) {
        return obj != null ? format(obj.toString()) : "";
    }

    public static String formatSimple(long j2) {
        return formatSimple(j2, true);
    }

    private static String parseFirst(String str) {
        return NUMBER[Integer.parseInt(str.substring(str.length() - 1))];
    }

    private static String parseMore(int i2) {
        return NUMBER_MORE[i2];
    }

    private static String parseTeen(String str) {
        return NUMBER_TEEN[Integer.parseInt(str) - 10];
    }

    private static String parseTen(String str) {
        return NUMBER_TEN[Integer.parseInt(str.substring(0, 1)) - 1];
    }

    private static String transThree(String str) {
        if (str.startsWith("0")) {
            return transTwo(str.substring(1));
        }
        if (TarConstants.VERSION_POSIX.equals(str.substring(1))) {
            return parseFirst(str.substring(0, 1)) + " HUNDRED";
        }
        return parseFirst(str.substring(0, 1)) + " HUNDRED AND " + transTwo(str.substring(1));
    }

    private static String transTwo(String str) {
        if (str.length() > 2) {
            str = str.substring(0, 2);
        } else if (str.length() < 2) {
            str = "0" + str;
        }
        if (str.startsWith("0")) {
            return parseFirst(str);
        }
        if (str.startsWith("1")) {
            return parseTeen(str);
        }
        if (str.endsWith("0")) {
            return parseTen(str);
        }
        return parseTen(str) + " " + parseFirst(str);
    }

    private static String format(String str) {
        String strSubstring;
        int iIndexOf = str.indexOf(StrPool.DOT);
        if (iIndexOf > -1) {
            String strSubstring2 = str.substring(0, iIndexOf);
            strSubstring = str.substring(iIndexOf + 1);
            str = strSubstring2;
        } else {
            strSubstring = "";
        }
        String strReverse = StrUtil.reverse(str);
        String[] strArr = new String[5];
        int length = strReverse.length() % 3;
        if (length == 1) {
            strReverse = strReverse + TarConstants.VERSION_POSIX;
        } else if (length == 2) {
            strReverse = strReverse + "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < strReverse.length() / 3; i2++) {
            int i3 = i2 * 3;
            String strReverse2 = StrUtil.reverse(strReverse.substring(i3, i3 + 3));
            strArr[i2] = strReverse2;
            if ("000".equals(strReverse2)) {
                sb.append(transThree(strArr[i2]));
            } else if (i2 != 0) {
                sb.insert(0, transThree(strArr[i2]) + " " + parseMore(i2) + " ");
            } else {
                sb = new StringBuilder(transThree(strArr[i2]));
            }
        }
        return sb.toString().trim() + " " + (iIndexOf > -1 ? "AND CENTS " + transTwo(strSubstring) + " " : "") + "ONLY";
    }

    public static String formatSimple(long j2, boolean z2) {
        if (j2 < 1000) {
            return String.valueOf(j2);
        }
        double d3 = j2;
        int i2 = -1;
        while (d3 > 10.0d && (!z2 || i2 < 1)) {
            if (d3 >= 1000.0d) {
                d3 /= 1000.0d;
                i2++;
            }
            if (d3 > 10.0d) {
                d3 /= 10.0d;
                i2++;
            }
        }
        return String.format("%s%s", NumberUtil.decimalFormat("#.##", d3), NUMBER_SUFFIX[i2]);
    }
}
