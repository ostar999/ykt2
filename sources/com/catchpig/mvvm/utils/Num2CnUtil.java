package com.catchpig.mvvm.utils;

/* loaded from: classes2.dex */
public class Num2CnUtil {
    private static String[] units = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千", "万亿"};
    private static char[] numArray = {38646, 19968, 20108, 19977, 22235, 20116, 20845, 19971, 20843, 20061};

    public static String convert2cn(int i2) throws NumberFormatException {
        char[] charArray = String.valueOf(i2).toCharArray();
        int length = charArray.length;
        StringBuilder sb = new StringBuilder();
        int i3 = 0;
        while (true) {
            if (i3 >= length) {
                break;
            }
            int i4 = Integer.parseInt(charArray[i3] + "");
            boolean z2 = i4 == 0;
            int i5 = (length - 1) - i3;
            String str = units[i5];
            if (z2) {
                boolean z3 = false;
                for (int i6 = i3; i6 < length; i6++) {
                    if (Integer.parseInt(charArray[i6] + "") != 0) {
                        z3 = true;
                    }
                }
                if (!z3) {
                    if (i5 % 4 == 0) {
                        sb.append(str);
                        break;
                    }
                } else if ('0' != charArray[i3 - 1]) {
                    if (i5 % 4 == 0) {
                        sb.append(str);
                    }
                    sb.append(numArray[i4]);
                }
            } else {
                sb.append(numArray[i4]);
                sb.append(str);
            }
            i3++;
        }
        return sb.lastIndexOf("一十") == 0 ? sb.substring(1) : "".equals(sb.toString()) ? "零" : sb.toString();
    }

    public static void main(String[] strArr) {
        for (int i2 = 0; i2 < 2555; i2++) {
            System.out.println(convert2cn(i2));
        }
    }
}
