package io.agora.rtc.internal;

import android.util.Log;
import cn.hutool.core.text.StrPool;

/* loaded from: classes8.dex */
public class Logging {
    private static final int LOG_DEBUG = 2048;
    private static final int LOG_ERROR = 4;
    private static final int LOG_INFO = 1;
    private static final int LOG_WARN = 2;

    public static void d(String message) {
        RtcEngineImpl.nativeLog(2048, message);
    }

    public static void e(String message) {
        RtcEngineImpl.nativeLog(4, message);
    }

    public static void i(String message) {
        RtcEngineImpl.nativeLog(1, message);
    }

    public static void log(int level, String tag, String message) {
        RtcEngineImpl.nativeLog(level, StrPool.BRACKET_START + tag + "] " + message);
    }

    public static void w(String message) {
        RtcEngineImpl.nativeLog(2, message);
    }

    public static void d(String tag, String message) {
        log(2048, tag, message);
    }

    public static void e(String tag, String message) {
        log(4, tag, message);
    }

    public static void i(String tag, String message) {
        log(1, tag, message);
    }

    public static void w(String tag, String message) {
        log(2, tag, message);
    }

    public static void e(String tag, String message, Throwable e2) {
        log(4, tag, message);
        log(4, tag, e2.toString());
        log(4, tag, Log.getStackTraceString(e2));
    }
}
