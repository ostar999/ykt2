package com.cicada.player.utils;

import android.content.Context;
import android.util.Log;
import com.aliyun.utils.f;

/* loaded from: classes3.dex */
public class Logger {
    private static String TAG = "Logger";
    private static Context sAppContext;
    private static volatile Logger sInstance;
    private final Object logCallbackLock = new Object();
    private OnLogCallback mLogCallback = null;
    private boolean mEnableConsoleLog = true;
    private LogLevel mCurrentLogLevel = LogLevel.AF_LOG_LEVEL_INFO;

    public enum LogLevel {
        AF_LOG_LEVEL_NONE(0),
        AF_LOG_LEVEL_FATAL(8),
        AF_LOG_LEVEL_ERROR(16),
        AF_LOG_LEVEL_WARNING(24),
        AF_LOG_LEVEL_INFO(32),
        AF_LOG_LEVEL_DEBUG(48),
        AF_LOG_LEVEL_TRACE(56);

        private int mValue;

        LogLevel(int i2) {
            this.mValue = i2;
        }

        public static LogLevel convert(int i2) {
            LogLevel logLevel = AF_LOG_LEVEL_NONE;
            for (LogLevel logLevel2 : values()) {
                if (logLevel2.getValue() == i2) {
                    return logLevel2;
                }
            }
            return logLevel;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    public interface OnLogCallback {
        void onLog(LogLevel logLevel, String str);
    }

    static {
        f.b();
        sInstance = null;
        sAppContext = null;
    }

    private Logger() {
    }

    private void callback(LogLevel logLevel, String str) {
        synchronized (this.logCallbackLock) {
            OnLogCallback onLogCallback = this.mLogCallback;
            if (onLogCallback != null) {
                onLogCallback.onLog(logLevel, str);
            }
        }
    }

    public static void d(String str, String str2) {
        log(LogLevel.AF_LOG_LEVEL_DEBUG, str, str2);
    }

    public static void e(String str, String str2) {
        log(LogLevel.AF_LOG_LEVEL_ERROR, str, str2);
    }

    public static Logger getInstance(Context context) {
        if (sInstance == null) {
            synchronized (Logger.class) {
                if (sInstance == null) {
                    sInstance = new Logger();
                    sInstance.setLogLevel(LogLevel.AF_LOG_LEVEL_INFO);
                    if (context != null) {
                        sAppContext = context.getApplicationContext();
                    }
                }
            }
        }
        return sInstance;
    }

    private static LogLevel getLevel(int i2) {
        if (i2 == 0) {
            return LogLevel.AF_LOG_LEVEL_NONE;
        }
        if (i2 == 8) {
            return LogLevel.AF_LOG_LEVEL_FATAL;
        }
        if (i2 == 16) {
            return LogLevel.AF_LOG_LEVEL_ERROR;
        }
        if (i2 == 24) {
            return LogLevel.AF_LOG_LEVEL_WARNING;
        }
        if (i2 == 32) {
            return LogLevel.AF_LOG_LEVEL_INFO;
        }
        if (i2 != 48 && i2 == 56) {
            return LogLevel.AF_LOG_LEVEL_TRACE;
        }
        return LogLevel.AF_LOG_LEVEL_DEBUG;
    }

    public static void i(String str, String str2) {
        log(LogLevel.AF_LOG_LEVEL_INFO, str, str2);
    }

    public static void loadClass() {
    }

    private static void log(LogLevel logLevel, String str, String str2) {
        LogLevel logLevel2 = getInstance(sAppContext).mCurrentLogLevel;
        boolean z2 = getInstance(sAppContext).mEnableConsoleLog;
        if (logLevel2 == LogLevel.AF_LOG_LEVEL_NONE || logLevel2.getValue() < logLevel.getValue() || !z2) {
            return;
        }
        if (logLevel == LogLevel.AF_LOG_LEVEL_TRACE) {
            Log.v(str, str2);
            return;
        }
        if (logLevel == LogLevel.AF_LOG_LEVEL_DEBUG) {
            Log.d(str, str2);
            return;
        }
        if (logLevel == LogLevel.AF_LOG_LEVEL_INFO) {
            Log.i(str, str2);
        } else if (logLevel == LogLevel.AF_LOG_LEVEL_WARNING) {
            Log.w(str, str2);
        } else if (logLevel == LogLevel.AF_LOG_LEVEL_ERROR) {
            Log.e(str, str2);
        }
    }

    private static native void nEnableConsoleLog(boolean z2);

    private static native int nGetLogLevel();

    private static void nOnLogCallback(int i2, byte[] bArr) {
        LogLevel level = getLevel(i2);
        String strTrim = new String(bArr).trim();
        Context context = sAppContext;
        if (context != null) {
            getInstance(context).callback(level, strTrim);
        }
    }

    private static native void nSetLogLevel(int i2);

    public static void v(String str, String str2) {
        log(LogLevel.AF_LOG_LEVEL_TRACE, str, str2);
    }

    public static void w(String str, String str2) {
        log(LogLevel.AF_LOG_LEVEL_WARNING, str, str2);
    }

    public void enableConsoleLog(boolean z2) {
        this.mEnableConsoleLog = z2;
        nEnableConsoleLog(z2);
    }

    public OnLogCallback getLogCallback() {
        OnLogCallback onLogCallback;
        synchronized (this.logCallbackLock) {
            onLogCallback = this.mLogCallback;
        }
        return onLogCallback;
    }

    public LogLevel getLogLevel() {
        return LogLevel.convert(nGetLogLevel());
    }

    public void setLogCallback(OnLogCallback onLogCallback) {
        synchronized (this.logCallbackLock) {
            this.mLogCallback = onLogCallback;
        }
    }

    public void setLogLevel(LogLevel logLevel) {
        this.mCurrentLogLevel = logLevel;
        nSetLogLevel(logLevel.getValue());
    }
}
