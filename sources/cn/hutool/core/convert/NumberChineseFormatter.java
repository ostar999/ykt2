package cn.hutool.core.convert;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.PrimitiveArrayUtil;
import com.google.android.exoplayer2.MediaPeriodQueue;

/* loaded from: classes.dex */
public class NumberChineseFormatter {
    private static final char[] DIGITS = {38646, 19968, 22777, 20108, 36144, 19977, 21441, 22235, 32902, 20116, 20237, 20845, 38470, 19971, 26578, 20843, 25420, 20061, 29590};
    private static final ChineseUnit[] CHINESE_NAME_VALUE = {new ChineseUnit(' ', 1, false), new ChineseUnit(21313, 10, false), new ChineseUnit(25342, 10, false), new ChineseUnit(30334, 100, false), new ChineseUnit(20336, 100, false), new ChineseUnit(21315, 1000, false), new ChineseUnit(20191, 1000, false), new ChineseUnit(19975, 10000, true), new ChineseUnit(20159, 100000000, true)};

    public static class ChineseUnit {
        private final char name;
        private final boolean secUnit;
        private final int value;

        public ChineseUnit(char c3, int i2, boolean z2) {
            this.name = c3;
            this.value = i2;
            this.secUnit = z2;
        }
    }

    private static void addPreZero(StringBuilder sb) {
        if (CharSequenceUtil.isEmpty(sb) || 38646 == sb.charAt(0)) {
            return;
        }
        sb.insert(0, (char) 38646);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.math.BigDecimal chineseMoneyToNumber(java.lang.String r7) {
        /*
            boolean r0 = cn.hutool.core.text.CharSequenceUtil.isBlank(r7)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            java.lang.String r0 = "元"
            int r0 = r7.indexOf(r0)
            r2 = -1
            if (r0 != r2) goto L17
            java.lang.String r0 = "圆"
            int r0 = r7.indexOf(r0)
        L17:
            java.lang.String r2 = "角"
            int r2 = r7.indexOf(r2)
            java.lang.String r3 = "分"
            int r3 = r7.indexOf(r3)
            r4 = 0
            if (r0 <= 0) goto L2b
            java.lang.String r5 = r7.substring(r4, r0)
            goto L2c
        L2b:
            r5 = r1
        L2c:
            if (r2 <= 0) goto L3e
            if (r0 < 0) goto L39
            if (r2 <= r0) goto L3e
            int r6 = r0 + 1
            java.lang.String r6 = r7.substring(r6, r2)
            goto L3f
        L39:
            java.lang.String r6 = r7.substring(r4, r2)
            goto L3f
        L3e:
            r6 = r1
        L3f:
            if (r3 <= 0) goto L5b
            if (r2 < 0) goto L4c
            if (r3 <= r2) goto L5b
            int r2 = r2 + 1
            java.lang.String r1 = r7.substring(r2, r3)
            goto L5b
        L4c:
            if (r0 <= 0) goto L57
            if (r3 <= r0) goto L5b
            int r0 = r0 + 1
            java.lang.String r1 = r7.substring(r0, r3)
            goto L5b
        L57:
            java.lang.String r1 = r7.substring(r4, r3)
        L5b:
            boolean r7 = cn.hutool.core.text.CharSequenceUtil.isNotBlank(r5)
            if (r7 == 0) goto L66
            int r7 = chineseToNumber(r5)
            goto L67
        L66:
            r7 = r4
        L67:
            boolean r0 = cn.hutool.core.text.CharSequenceUtil.isNotBlank(r6)
            if (r0 == 0) goto L72
            int r0 = chineseToNumber(r6)
            goto L73
        L72:
            r0 = r4
        L73:
            boolean r2 = cn.hutool.core.text.CharSequenceUtil.isNotBlank(r1)
            if (r2 == 0) goto L7d
            int r4 = chineseToNumber(r1)
        L7d:
            java.math.BigDecimal r1 = new java.math.BigDecimal
            r1.<init>(r7)
            long r2 = (long) r0
            java.math.BigDecimal r7 = java.math.BigDecimal.valueOf(r2)
            java.math.BigDecimal r0 = java.math.BigDecimal.TEN
            java.math.RoundingMode r2 = java.math.RoundingMode.HALF_UP
            r3 = 2
            java.math.BigDecimal r7 = r7.divide(r0, r3, r2)
            java.math.BigDecimal r7 = r1.add(r7)
            long r0 = (long) r4
            java.math.BigDecimal r0 = java.math.BigDecimal.valueOf(r0)
            r1 = 100
            java.math.BigDecimal r1 = java.math.BigDecimal.valueOf(r1)
            java.math.RoundingMode r2 = java.math.RoundingMode.HALF_UP
            java.math.BigDecimal r0 = r0.divide(r1, r3, r2)
            java.math.BigDecimal r7 = r7.add(r0)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.hutool.core.convert.NumberChineseFormatter.chineseMoneyToNumber(java.lang.String):java.math.BigDecimal");
    }

    public static int chineseToNumber(String str) {
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        ChineseUnit chineseUnitChineseToUnit = null;
        for (int i5 = 0; i5 < length; i5++) {
            char cCharAt = str.charAt(i5);
            int iChineseToNumber = chineseToNumber(cCharAt);
            if (iChineseToNumber >= 0) {
                if (iChineseToNumber == 0) {
                    if (i2 > 0 && chineseUnitChineseToUnit != null) {
                        i4 += i2 * (chineseUnitChineseToUnit.value / 10);
                    }
                    chineseUnitChineseToUnit = null;
                } else if (i2 > 0) {
                    throw new IllegalArgumentException(CharSequenceUtil.format("Bad number '{}{}' at: {}", Character.valueOf(str.charAt(i5 - 1)), Character.valueOf(cCharAt), Integer.valueOf(i5)));
                }
                i2 = iChineseToNumber;
            } else {
                chineseUnitChineseToUnit = chineseToUnit(cCharAt);
                if (chineseUnitChineseToUnit == null) {
                    throw new IllegalArgumentException(CharSequenceUtil.format("Unknown unit '{}' at: {}", Character.valueOf(cCharAt), Integer.valueOf(i5)));
                }
                if (chineseUnitChineseToUnit.secUnit) {
                    i3 += (i4 + i2) * chineseUnitChineseToUnit.value;
                    i4 = 0;
                } else {
                    if (i2 == 0 && i5 == 0) {
                        i2 = 1;
                    }
                    i4 += i2 * chineseUnitChineseToUnit.value;
                }
                i2 = 0;
            }
        }
        if (i2 > 0 && chineseUnitChineseToUnit != null) {
            i2 *= chineseUnitChineseToUnit.value / 10;
        }
        return i3 + i4 + i2;
    }

    private static ChineseUnit chineseToUnit(char c3) {
        for (ChineseUnit chineseUnit : CHINESE_NAME_VALUE) {
            if (chineseUnit.name == c3) {
                return chineseUnit;
            }
        }
        return null;
    }

    public static String format(double d3, boolean z2) {
        return format(d3, z2, false);
    }

    public static String formatSimple(long j2) {
        if (j2 < com.heytap.mcssdk.constant.a.f7153q && j2 > -10000) {
            return String.valueOf(j2);
        }
        if (j2 < 100000000 && j2 > -100000000) {
            return NumberUtil.div(j2, 10000.0f, 2) + "万";
        }
        if (j2 >= MediaPeriodQueue.INITIAL_RENDERER_POSITION_OFFSET_US || j2 <= -1000000000000L) {
            return NumberUtil.div(j2, 1.0E12f, 2) + "万亿";
        }
        return NumberUtil.div(j2, 1.0E8f, 2) + "亿";
    }

    public static String formatThousand(int i2, boolean z2) {
        Assert.checkBetween(i2, -999, 999, "Number support only: (-999 ~ 999)！", new Object[0]);
        String strThousandToChinese = thousandToChinese(i2, z2);
        return (i2 >= 20 || i2 < 10) ? strThousandToChinese : strThousandToChinese.substring(1);
    }

    private static String getUnitName(int i2, boolean z2) {
        return i2 == 0 ? "" : String.valueOf(CHINESE_NAME_VALUE[(i2 * 2) - (!z2 ? 1 : 0)].name);
    }

    private static String longToChinese(long j2, boolean z2) {
        if (0 == j2) {
            return "零";
        }
        int[] iArr = new int[4];
        int i2 = 0;
        while (j2 != 0) {
            iArr[i2] = (int) (j2 % com.heytap.mcssdk.constant.a.f7153q);
            j2 /= com.heytap.mcssdk.constant.a.f7153q;
            i2++;
        }
        StringBuilder sb = new StringBuilder();
        int i3 = iArr[0];
        if (i3 > 0) {
            sb.insert(0, thousandToChinese(i3, z2));
            if (i3 < 1000) {
                addPreZero(sb);
            }
        }
        int i4 = iArr[1];
        if (i4 > 0) {
            if (i4 % 10 == 0 && iArr[0] > 0) {
                addPreZero(sb);
            }
            sb.insert(0, thousandToChinese(i4, z2) + "万");
            if (i4 < 1000) {
                addPreZero(sb);
            }
        } else {
            addPreZero(sb);
        }
        int i5 = iArr[2];
        if (i5 > 0) {
            if (i5 % 10 == 0 && iArr[1] > 0) {
                addPreZero(sb);
            }
            sb.insert(0, thousandToChinese(i5, z2) + "亿");
            if (i5 < 1000) {
                addPreZero(sb);
            }
        } else {
            addPreZero(sb);
        }
        int i6 = iArr[3];
        if (i6 > 0) {
            if (iArr[2] == 0) {
                sb.insert(0, "亿");
            }
            sb.insert(0, thousandToChinese(i6, z2) + "万");
        }
        return (CharSequenceUtil.isNotEmpty(sb) && 38646 == sb.charAt(0)) ? sb.substring(1) : sb.toString();
    }

    public static String numberCharToChinese(char c3, boolean z2) {
        return (c3 < '0' || c3 > '9') ? String.valueOf(c3) : String.valueOf(numberToChinese(c3 - '0', z2));
    }

    private static char numberToChinese(int i2, boolean z2) {
        return i2 == 0 ? DIGITS[0] : DIGITS[(i2 * 2) - (!z2 ? 1 : 0)];
    }

    private static String thousandToChinese(int i2, boolean z2) {
        if (i2 == 0) {
            return String.valueOf(DIGITS[0]);
        }
        StringBuilder sb = new StringBuilder();
        int i3 = 0;
        boolean z3 = true;
        while (i2 > 0) {
            int i4 = i2 % 10;
            if (i4 == 0) {
                if (!z3) {
                    sb.insert(0, "零");
                }
                z3 = true;
            } else {
                sb.insert(0, numberToChinese(i4, z2) + getUnitName(i3, z2));
                z3 = false;
            }
            i2 /= 10;
            i3++;
        }
        return sb.toString();
    }

    public static String format(double d3, boolean z2, boolean z3, String str, String str2) {
        double d4;
        if (0.0d == d3) {
            return "零";
        }
        Assert.checkBetween(d3, -9.999999999999998E13d, 9.999999999999998E13d, "Number support only: (-99999999999999.99 ~ 99999999999999.99)！", new Object[0]);
        StringBuilder sb = new StringBuilder();
        if (d3 < 0.0d) {
            sb.append(CharSequenceUtil.isNullOrUndefined(str) ? "负" : str);
            d4 = -d3;
        } else {
            d4 = d3;
        }
        long jRound = Math.round(d4 * 100.0d);
        int i2 = (int) (jRound % 10);
        long j2 = jRound / 10;
        int i3 = (int) (j2 % 10);
        long j3 = j2 / 10;
        if (!z3 || 0 != j3) {
            sb.append(longToChinese(j3, z2));
            if (z3) {
                sb.append(CharSequenceUtil.isNullOrUndefined(str2) ? "元" : str2);
            }
        }
        if (i3 == 0 && i2 == 0) {
            if (z3) {
                sb.append("整");
            }
            return sb.toString();
        }
        if (!z3) {
            sb.append("点");
        }
        if (0 != j3 || i3 != 0) {
            sb.append(numberToChinese(i3, z2));
            if (z3 && i3 != 0) {
                sb.append("角");
            }
        } else if (!z3) {
            sb.append("零");
        }
        if (i2 != 0) {
            sb.append(numberToChinese(i2, z2));
            if (z3) {
                sb.append("分");
            }
        }
        return sb.toString();
    }

    private static int chineseToNumber(char c3) {
        if (20004 == c3) {
            c3 = 20108;
        }
        int iIndexOf = PrimitiveArrayUtil.indexOf(DIGITS, c3);
        return iIndexOf > 0 ? (iIndexOf + 1) / 2 : iIndexOf;
    }

    public static String format(double d3, boolean z2, boolean z3) {
        return format(d3, z2, z3, "负", "元");
    }

    public static String format(long j2, boolean z2) {
        if (0 == j2) {
            return "零";
        }
        Assert.checkBetween(j2, -9.999999999999998E13d, 9.999999999999998E13d, "Number support only: (-99999999999999.99 ~ 99999999999999.99)！", new Object[0]);
        StringBuilder sb = new StringBuilder();
        if (j2 < 0) {
            sb.append("负");
            j2 = -j2;
        }
        sb.append(longToChinese(j2, z2));
        return sb.toString();
    }
}
