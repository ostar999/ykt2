package com.tencent.liteav.basic.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.h;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes6.dex */
public class TXCLog {
    public static final int LOG_ASYNC = 0;
    public static final int LOG_DEBUG = 1;
    public static final int LOG_ERROR = 4;
    public static final int LOG_FATAL = 5;
    public static final int LOG_INFO = 2;
    public static final int LOG_NONE = 6;
    public static final int LOG_SYNC = 1;
    public static final int LOG_VERBOSE = 0;
    public static final int LOG_WARNING = 3;
    private static boolean mEnableCallback = false;
    private static boolean mEnableCompress = true;
    private static boolean mEnableConsole = true;
    private static boolean mEnableLogToFile = true;
    private static boolean mHasInit = false;
    private static a mListener = null;
    private static String mLogCacheDir = "";
    public static String mLogDir = "";
    private static int mLogLevel;
    private static final Object mLogLock = new Object();

    public interface a {
        void a(int i2, String str, String str2);
    }

    public static void copyLogFile() {
        if (mHasInit) {
            Log.i("TXCLog", "TXCLog copyLogFile");
            nativeLogOpen(0, mLogDir, mLogCacheDir, "LiteAV", mEnableCompress);
        }
    }

    public static void d(String str, String str2, Object... objArr) {
        log(1, str, String.format(str2, objArr));
    }

    public static void e(String str, String str2, Object... objArr) {
        log(4, str, String.format(str2, objArr));
    }

    public static void i(String str, String str2, Object... objArr) {
        log(2, str, String.format(str2, objArr));
    }

    public static boolean init() {
        File externalFilesDir;
        if (mHasInit) {
            return true;
        }
        synchronized (mLogLock) {
            if (mHasInit) {
                return true;
            }
            boolean zD = h.d();
            Context appContext = TXCCommonUtil.getAppContext();
            if (zD && appContext != null) {
                if (TextUtils.isEmpty(mLogDir) && (externalFilesDir = appContext.getExternalFilesDir(null)) != null) {
                    mLogDir = externalFilesDir.getAbsolutePath() + "/log/liteav";
                }
                mLogCacheDir = appContext.getFilesDir().getAbsolutePath() + "/log/liteav";
                Log.i("TXCLog", "TXCLog init log file path : " + mLogDir + ", cache : " + mLogCacheDir);
                nativeLogInit();
                nativeLogSetLevel(mLogLevel);
                nativeLogSetConsole(mEnableConsole);
                nativeLogOpen(0, mLogDir, mLogCacheDir, "LiteAV", mEnableCompress);
                nativeEnableCallback(mEnableCallback);
                nativeLogToFileEnabled(mEnableLogToFile);
                mHasInit = true;
            }
            return mHasInit;
        }
    }

    public static void log(int i2, String str, String str2) {
        if (init()) {
            nativeLog(i2, str, "", 0, "", str2);
        }
        log_callback(i2, str, str2);
    }

    private static void log_callback(int i2, String str, String str2) {
        a aVar = mListener;
        if (aVar != null) {
            aVar.a(i2, str, str2);
        }
    }

    private static native void nativeEnableCallback(boolean z2);

    private static native void nativeLog(int i2, String str, String str2, int i3, String str3, String str4);

    private static native void nativeLogClose();

    private static native void nativeLogInit();

    private static native void nativeLogOpen(int i2, String str, String str2, String str3, boolean z2);

    private static native void nativeLogSetConsole(boolean z2);

    private static native void nativeLogSetLevel(int i2);

    private static native void nativeLogToFileEnabled(boolean z2);

    public static void setConsoleEnabled(boolean z2) {
        mEnableConsole = z2;
        if (mHasInit) {
            nativeLogSetConsole(z2);
        }
    }

    public static void setLevel(int i2) {
        mLogLevel = i2;
        if (mHasInit) {
            nativeLogSetLevel(i2);
        }
    }

    public static void setListener(a aVar) {
        mListener = aVar;
        boolean z2 = aVar != null;
        mEnableCallback = z2;
        if (mHasInit) {
            nativeEnableCallback(z2);
        }
    }

    public static void setLogCompressEnabled(boolean z2) {
        if (mEnableCompress != z2) {
            mEnableCompress = z2;
            if (mHasInit) {
                nativeLogClose();
                nativeLogOpen(0, mLogDir, mLogCacheDir, "LiteAV", mEnableCompress);
            }
        }
    }

    public static void setLogDirPath(String str) {
        if (TextUtils.isEmpty(str) || str.equalsIgnoreCase(mLogDir)) {
            return;
        }
        mLogDir = str;
        if (mHasInit) {
            Log.i("TXCLog", "TXCLog setLogDirPath " + str);
            nativeLogClose();
            nativeLogOpen(0, mLogDir, mLogCacheDir, "LiteAV", mEnableCompress);
        }
    }

    public static void setLogToFileEnabled(boolean z2) {
        mEnableLogToFile = z2;
        if (mHasInit) {
            nativeLogToFileEnabled(z2);
        }
    }

    public static void sliceLogFile() {
        if (mHasInit) {
            Log.i("TXCLog", "TXCLog sliceLogFile");
            nativeLogOpen(0, mLogDir, mLogCacheDir, "LiteAV", mEnableCompress);
        }
    }

    public static void v(String str, String str2, Object... objArr) {
        log(0, str, String.format(str2, objArr));
    }

    public static void w(String str, String str2, Object... objArr) {
        log(3, str, String.format(str2, objArr));
    }

    public static void d(String str, String str2) {
        log(1, str, str2);
    }

    public static void e(String str, String str2) {
        log(4, str, str2);
    }

    public static void i(String str, String str2) {
        log(2, str, str2);
    }

    public static void v(String str, String str2) {
        log(0, str, str2);
    }

    public static void w(String str, String str2) {
        log(3, str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        e(str, str2 + "\n" + stringWriter.toString());
    }
}
