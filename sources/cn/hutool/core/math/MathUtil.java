package cn.hutool.core.math;

import java.util.List;

/* loaded from: classes.dex */
public class MathUtil {
    public static long arrangementCount(int i2, int i3) {
        return Arrangement.count(i2, i3);
    }

    public static List<String[]> arrangementSelect(String[] strArr, int i2) {
        return new Arrangement(strArr).select(i2);
    }

    public static double centToYuan(long j2) {
        return new Money(j2 / 100, (int) (j2 % 100)).getAmount().doubleValue();
    }

    public static long combinationCount(int i2, int i3) {
        return Combination.count(i2, i3);
    }

    public static List<String[]> combinationSelect(String[] strArr, int i2) {
        return new Combination(strArr).select(i2);
    }

    public static long yuanToCent(double d3) {
        return new Money(d3).getCent();
    }

    public static long arrangementCount(int i2) {
        return Arrangement.count(i2);
    }

    public static List<String[]> arrangementSelect(String[] strArr) {
        return new Arrangement(strArr).select();
    }
}
