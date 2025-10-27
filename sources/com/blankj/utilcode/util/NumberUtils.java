package com.blankj.utilcode.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/* loaded from: classes2.dex */
public final class NumberUtils {
    private static final ThreadLocal<DecimalFormat> DF_THREAD_LOCAL = new ThreadLocal<DecimalFormat>() { // from class: com.blankj.utilcode.util.NumberUtils.1
        @Override // java.lang.ThreadLocal
        public DecimalFormat initialValue() {
            return (DecimalFormat) NumberFormat.getInstance();
        }
    };

    private NumberUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static double float2Double(float f2) {
        return new BigDecimal(String.valueOf(f2)).doubleValue();
    }

    public static String format(float f2, int i2) {
        return format(f2, false, 1, i2, true);
    }

    public static DecimalFormat getSafeDecimalFormat() {
        return DF_THREAD_LOCAL.get();
    }

    public static String format(float f2, int i2, boolean z2) {
        return format(f2, false, 1, i2, z2);
    }

    public static String format(float f2, int i2, int i3, boolean z2) {
        return format(f2, false, i2, i3, z2);
    }

    public static String format(float f2, boolean z2, int i2) {
        return format(f2, z2, 1, i2, true);
    }

    public static String format(float f2, boolean z2, int i2, boolean z3) {
        return format(f2, z2, 1, i2, z3);
    }

    public static String format(float f2, boolean z2, int i2, int i3, boolean z3) {
        return format(float2Double(f2), z2, i2, i3, z3);
    }

    public static String format(double d3, int i2) {
        return format(d3, false, 1, i2, true);
    }

    public static String format(double d3, int i2, boolean z2) {
        return format(d3, false, 1, i2, z2);
    }

    public static String format(double d3, int i2, int i3, boolean z2) {
        return format(d3, false, i2, i3, z2);
    }

    public static String format(double d3, boolean z2, int i2) {
        return format(d3, z2, 1, i2, true);
    }

    public static String format(double d3, boolean z2, int i2, boolean z3) {
        return format(d3, z2, 1, i2, z3);
    }

    public static String format(double d3, boolean z2, int i2, int i3, boolean z3) {
        DecimalFormat safeDecimalFormat = getSafeDecimalFormat();
        safeDecimalFormat.setGroupingUsed(z2);
        safeDecimalFormat.setRoundingMode(z3 ? RoundingMode.HALF_UP : RoundingMode.DOWN);
        safeDecimalFormat.setMinimumIntegerDigits(i2);
        safeDecimalFormat.setMinimumFractionDigits(i3);
        safeDecimalFormat.setMaximumFractionDigits(i3);
        return safeDecimalFormat.format(d3);
    }
}
