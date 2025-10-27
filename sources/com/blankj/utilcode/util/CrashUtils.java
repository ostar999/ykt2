package com.blankj.utilcode.util;

import androidx.annotation.NonNull;
import com.blankj.utilcode.util.UtilsBridge;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.lang.Thread;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/* loaded from: classes2.dex */
public final class CrashUtils {
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final Thread.UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

    public static final class CrashInfo {
        private UtilsBridge.FileHead mFileHeadProvider;
        private Throwable mThrowable;

        public final void addExtraHead(Map<String, String> map) {
            this.mFileHeadProvider.append(map);
        }

        public final Throwable getThrowable() {
            return this.mThrowable;
        }

        public String toString() {
            return this.mFileHeadProvider.toString() + UtilsBridge.getFullStackTrace(this.mThrowable);
        }

        private CrashInfo(String str, Throwable th) {
            this.mThrowable = th;
            UtilsBridge.FileHead fileHead = new UtilsBridge.FileHead("Crash");
            this.mFileHeadProvider = fileHead;
            fileHead.addFirst("Time Of Crash", str);
        }

        public final void addExtraHead(String str, String str2) {
            this.mFileHeadProvider.append(str, str2);
        }
    }

    public interface OnCrashListener {
        void onCrash(CrashInfo crashInfo);
    }

    private CrashUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static Thread.UncaughtExceptionHandler getUncaughtExceptionHandler(final String str, final OnCrashListener onCrashListener) {
        return new Thread.UncaughtExceptionHandler() { // from class: com.blankj.utilcode.util.CrashUtils.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(@NonNull Thread thread, @NonNull Throwable th) {
                String str2 = new SimpleDateFormat("yyyy_MM_dd-HH_mm_ss").format(new Date());
                CrashInfo crashInfo = new CrashInfo(str2, th);
                UtilsBridge.writeFileFromString(str + str2 + ".txt", crashInfo.toString(), true);
                if (CrashUtils.DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                    CrashUtils.DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(thread, th);
                }
                OnCrashListener onCrashListener2 = onCrashListener;
                if (onCrashListener2 != null) {
                    onCrashListener2.onCrash(crashInfo);
                }
            }
        };
    }

    public static void init() {
        init("");
    }

    public static void init(@NonNull File file) {
        init(file.getAbsolutePath(), (OnCrashListener) null);
    }

    public static void init(String str) {
        init(str, (OnCrashListener) null);
    }

    public static void init(OnCrashListener onCrashListener) {
        init("", onCrashListener);
    }

    public static void init(@NonNull File file, OnCrashListener onCrashListener) {
        init(file.getAbsolutePath(), onCrashListener);
    }

    public static void init(String str, OnCrashListener onCrashListener) {
        if (UtilsBridge.isSpace(str)) {
            if (UtilsBridge.isSDCardEnableByEnvironment() && Utils.getApp().getExternalFilesDir(null) != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(Utils.getApp().getExternalFilesDir(null));
                String str2 = FILE_SEP;
                sb.append(str2);
                sb.append(CrashHianalyticsData.EVENT_ID_CRASH);
                sb.append(str2);
                str = sb.toString();
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Utils.getApp().getFilesDir());
                String str3 = FILE_SEP;
                sb2.append(str3);
                sb2.append(CrashHianalyticsData.EVENT_ID_CRASH);
                sb2.append(str3);
                str = sb2.toString();
            }
        } else {
            String str4 = FILE_SEP;
            if (!str.endsWith(str4)) {
                str = str + str4;
            }
        }
        Thread.setDefaultUncaughtExceptionHandler(getUncaughtExceptionHandler(str, onCrashListener));
    }
}
