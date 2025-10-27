package com.google.android.exoplayer2.util;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import java.net.UnknownHostException;
import org.checkerframework.dataflow.qual.Pure;

/* loaded from: classes3.dex */
public final class Log {
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_ERROR = 3;
    public static final int LOG_LEVEL_INFO = 1;
    public static final int LOG_LEVEL_OFF = Integer.MAX_VALUE;
    public static final int LOG_LEVEL_WARNING = 2;
    private static int logLevel = 0;
    private static boolean logStackTraces = true;

    private Log() {
    }

    @Pure
    private static String appendThrowableString(String str, @Nullable Throwable th) {
        String throwableString = getThrowableString(th);
        if (TextUtils.isEmpty(throwableString)) {
            return str;
        }
        String strValueOf = String.valueOf(str);
        String strReplace = throwableString.replace("\n", "\n  ");
        StringBuilder sb = new StringBuilder(strValueOf.length() + 4 + String.valueOf(strReplace).length());
        sb.append(strValueOf);
        sb.append("\n  ");
        sb.append(strReplace);
        sb.append('\n');
        return sb.toString();
    }

    @Pure
    public static void d(String str, String str2) {
        if (logLevel == 0) {
            android.util.Log.d(str, str2);
        }
    }

    @Pure
    public static void e(String str, String str2) {
        if (logLevel <= 3) {
            android.util.Log.e(str, str2);
        }
    }

    @Pure
    public static int getLogLevel() {
        return logLevel;
    }

    @Nullable
    @Pure
    public static String getThrowableString(@Nullable Throwable th) {
        if (th == null) {
            return null;
        }
        return isCausedByUnknownHostException(th) ? "UnknownHostException (no network)" : !logStackTraces ? th.getMessage() : android.util.Log.getStackTraceString(th).trim().replace(StrPool.TAB, "    ");
    }

    @Pure
    public static void i(String str, String str2) {
        if (logLevel <= 1) {
            android.util.Log.i(str, str2);
        }
    }

    @Pure
    private static boolean isCausedByUnknownHostException(@Nullable Throwable th) {
        while (th != null) {
            if (th instanceof UnknownHostException) {
                return true;
            }
            th = th.getCause();
        }
        return false;
    }

    public static void setLogLevel(int i2) {
        logLevel = i2;
    }

    public static void setLogStackTraces(boolean z2) {
        logStackTraces = z2;
    }

    @Pure
    public static void w(String str, String str2) {
        if (logLevel <= 2) {
            android.util.Log.w(str, str2);
        }
    }

    @Pure
    public static void d(String str, String str2, @Nullable Throwable th) {
        d(str, appendThrowableString(str2, th));
    }

    @Pure
    public static void e(String str, String str2, @Nullable Throwable th) {
        e(str, appendThrowableString(str2, th));
    }

    @Pure
    public static void i(String str, String str2, @Nullable Throwable th) {
        i(str, appendThrowableString(str2, th));
    }

    @Pure
    public static void w(String str, String str2, @Nullable Throwable th) {
        w(str, appendThrowableString(str2, th));
    }
}
