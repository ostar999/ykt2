package com.plv.foundationsdk.utils;

import android.graphics.Color;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes4.dex */
public class PLVFormatUtils {
    private static final boolean DEFAULT_VALUE_BOOLEAN = false;
    private static final int DEFAULT_VALUE_INT = 0;

    public static Boolean booleanValueOf(String str) {
        return booleanValueOf(str, false);
    }

    public static Double doubleValueOf(String str) {
        return doubleValueOf(str, 0.0d);
    }

    public static Float floatValueOf(String str) {
        return floatValueOf(str, 0.0f);
    }

    public static Integer integerValueOf(String str) {
        return integerValueOf(str, 0);
    }

    public static Long longValueOf(String str) {
        return longValueOf(str, 0L);
    }

    public static boolean parseBoolean(String str) {
        return parseBoolean(str, false);
    }

    @ColorInt
    public static int parseColor(@Nullable String str) {
        return parseColor(str, 0);
    }

    public static double parseDouble(String str) {
        return parseDouble(str, 0.0d);
    }

    public static float parseFloat(String str) {
        return parseFloat(str, 0.0f);
    }

    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    public static long parseLong(String str) {
        return parseLong(str, 0L);
    }

    public static Boolean booleanValueOf(String str, boolean z2) {
        try {
            return Boolean.valueOf(str);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return Boolean.valueOf(z2);
        }
    }

    public static Double doubleValueOf(String str, double d3) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return Double.valueOf(d3);
        }
    }

    public static Float floatValueOf(String str, float f2) {
        try {
            return Float.valueOf(str);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return Float.valueOf(f2);
        }
    }

    public static Integer integerValueOf(String str, int i2) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return Integer.valueOf(i2);
        }
    }

    public static Long longValueOf(String str, long j2) {
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return Long.valueOf(j2);
        }
    }

    public static boolean parseBoolean(String str, boolean z2) {
        try {
            return Boolean.parseBoolean(str);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return z2;
        }
    }

    @ColorInt
    public static int parseColor(@Nullable String str, int i2) {
        if (str == null) {
            return i2;
        }
        try {
            return Color.parseColor(str);
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return i2;
        }
    }

    public static double parseDouble(String str, double d3) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return d3;
        }
    }

    public static float parseFloat(String str, float f2) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return f2;
        }
    }

    public static int parseInt(String str, int i2) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return i2;
        }
    }

    public static long parseLong(String str, long j2) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e2) {
            PLVCommonLog.exception(e2);
            return j2;
        }
    }
}
