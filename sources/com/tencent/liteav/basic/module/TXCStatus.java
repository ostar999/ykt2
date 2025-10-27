package com.tencent.liteav.basic.module;

/* loaded from: classes6.dex */
public class TXCStatus {
    public static void a(String str) {
        nativeStatusStartRecord(str);
    }

    public static void b(String str) {
        nativeStatusStopRecord(str);
    }

    public static int c(String str, int i2) {
        return c(str, i2, 0);
    }

    public static double d(String str, int i2) {
        return d(str, i2, 0);
    }

    private static native double nativeStatusGetDoubleValue(String str, int i2, int i3);

    private static native long nativeStatusGetIntValue(String str, int i2, int i3);

    private static native String nativeStatusGetStrValue(String str, int i2, int i3);

    private static native boolean nativeStatusSetDoubleValue(String str, int i2, int i3, double d3);

    private static native boolean nativeStatusSetIntValue(String str, int i2, int i3, long j2);

    private static native boolean nativeStatusSetStrValue(String str, int i2, int i3, String str2);

    private static native void nativeStatusStartRecord(String str);

    private static native void nativeStatusStopRecord(String str);

    public static boolean a(String str, int i2, Object obj) {
        return a(str, i2, 0, obj);
    }

    public static String b(String str, int i2) {
        return b(str, i2, 0);
    }

    public static int c(String str, int i2, int i3) {
        return (int) nativeStatusGetIntValue(str, i2, i3);
    }

    public static double d(String str, int i2, int i3) {
        return nativeStatusGetDoubleValue(str, i2, i3);
    }

    public static boolean a(String str, int i2, int i3, Object obj) {
        if (str == null || str.length() == 0 || obj == null) {
            return false;
        }
        if (obj instanceof Double) {
            return nativeStatusSetDoubleValue(str, i2, i3, ((Double) obj).doubleValue());
        }
        if (obj instanceof String) {
            return nativeStatusSetStrValue(str, i2, i3, (String) obj);
        }
        if (obj instanceof Long) {
            return nativeStatusSetIntValue(str, i2, i3, ((Long) obj).longValue());
        }
        return nativeStatusSetIntValue(str, i2, i3, ((Integer) obj).intValue());
    }

    public static String b(String str, int i2, int i3) {
        return nativeStatusGetStrValue(str, i2, i3);
    }

    public static long a(String str, int i2) {
        return a(str, i2, 0);
    }

    public static long a(String str, int i2, int i3) {
        return nativeStatusGetIntValue(str, i2, i3);
    }
}
