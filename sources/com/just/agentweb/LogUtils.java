package com.just.agentweb;

import android.util.Log;

/* loaded from: classes4.dex */
class LogUtils {
    private static final String PREFIX = "agentweb-";

    public static void e(String str, String str2) {
        if (isDebug()) {
            Log.e(PREFIX.concat(str), str2);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        Log.e(str, str2, th);
    }

    public static void i(String str, String str2) {
        if (isDebug()) {
            Log.i(PREFIX.concat(str), str2);
        }
    }

    public static boolean isDebug() {
        return AgentWebConfig.DEBUG;
    }

    public static void safeCheckCrash(String str, String str2, Throwable th) {
        if (!isDebug()) {
            Log.e(PREFIX.concat(str), str2, th);
            return;
        }
        throw new RuntimeException(PREFIX.concat(str) + " " + str2, th);
    }

    public static void v(String str, String str2) {
        if (isDebug()) {
            Log.v(PREFIX.concat(str), str2);
        }
    }
}
