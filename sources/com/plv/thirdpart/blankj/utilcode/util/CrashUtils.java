package com.plv.thirdpart.blankj.utilcode.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import androidx.annotation.NonNull;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.io.File;
import java.io.IOException;
import java.lang.Thread;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes5.dex */
public final class CrashUtils {
    private static final String CRASH_HEAD;
    private static final Thread.UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER;
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH-mm-ss", Locale.getDefault());
    private static final Thread.UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER;
    private static String defaultDir;
    private static String dir;
    private static ExecutorService sExecutor;
    private static int versionCode;
    private static String versionName;

    static {
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(Utils.getApp().getPackageName(), 0);
            if (packageInfo != null) {
                versionName = packageInfo.versionName;
                versionCode = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        CRASH_HEAD = "************* Crash Log Head ****************\nDevice Manufacturer: " + Build.MANUFACTURER + "\nDevice Model       : " + Build.MODEL + "\nAndroid Version    : " + Build.VERSION.RELEASE + "\nAndroid SDK        : " + Build.VERSION.SDK_INT + "\nApp VersionName    : " + versionName + "\nApp VersionCode    : " + versionCode + "\n************* Crash Log Head ****************\n\n";
        DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();
        UNCAUGHT_EXCEPTION_HANDLER = new Thread.UncaughtExceptionHandler() { // from class: com.plv.thirdpart.blankj.utilcode.util.CrashUtils.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, final Throwable th) {
                if (th == null) {
                    Process.killProcess(Process.myPid());
                    System.exit(0);
                    return;
                }
                String str = CrashUtils.FORMAT.format(new Date(System.currentTimeMillis())) + ".txt";
                StringBuilder sb = new StringBuilder();
                sb.append(CrashUtils.dir == null ? CrashUtils.defaultDir : CrashUtils.dir);
                sb.append(str);
                final String string = sb.toString();
                if (CrashUtils.createOrExistsFile(string)) {
                    if (CrashUtils.sExecutor == null) {
                        ExecutorService unused = CrashUtils.sExecutor = Executors.newSingleThreadExecutor();
                    }
                    CrashUtils.sExecutor.execute(new Runnable() { // from class: com.plv.thirdpart.blankj.utilcode.util.CrashUtils.1.1
                        /* JADX WARN: Removed duplicated region for block: B:20:0x0041  */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void run() throws java.lang.Throwable {
                            /*
                                r6 = this;
                                r0 = 0
                                java.io.PrintWriter r1 = new java.io.PrintWriter     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L31
                                java.io.FileWriter r2 = new java.io.FileWriter     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L31
                                java.lang.String r3 = r2     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L31
                                r4 = 0
                                r2.<init>(r3, r4)     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L31
                                r1.<init>(r2)     // Catch: java.lang.Throwable -> L2c java.io.IOException -> L31
                                java.lang.String r0 = com.plv.thirdpart.blankj.utilcode.util.CrashUtils.access$500()     // Catch: java.io.IOException -> L2a java.lang.Throwable -> L3e
                                r1.write(r0)     // Catch: java.io.IOException -> L2a java.lang.Throwable -> L3e
                                java.lang.Throwable r0 = r3     // Catch: java.io.IOException -> L2a java.lang.Throwable -> L3e
                                r0.printStackTrace(r1)     // Catch: java.io.IOException -> L2a java.lang.Throwable -> L3e
                                java.lang.Throwable r0 = r3     // Catch: java.io.IOException -> L2a java.lang.Throwable -> L3e
                                java.lang.Throwable r0 = r0.getCause()     // Catch: java.io.IOException -> L2a java.lang.Throwable -> L3e
                            L20:
                                if (r0 == 0) goto L3a
                                r0.printStackTrace(r1)     // Catch: java.io.IOException -> L2a java.lang.Throwable -> L3e
                                java.lang.Throwable r0 = r0.getCause()     // Catch: java.io.IOException -> L2a java.lang.Throwable -> L3e
                                goto L20
                            L2a:
                                r0 = move-exception
                                goto L35
                            L2c:
                                r1 = move-exception
                                r5 = r1
                                r1 = r0
                                r0 = r5
                                goto L3f
                            L31:
                                r1 = move-exception
                                r5 = r1
                                r1 = r0
                                r0 = r5
                            L35:
                                r0.printStackTrace()     // Catch: java.lang.Throwable -> L3e
                                if (r1 == 0) goto L3d
                            L3a:
                                r1.close()
                            L3d:
                                return
                            L3e:
                                r0 = move-exception
                            L3f:
                                if (r1 == 0) goto L44
                                r1.close()
                            L44:
                                throw r0
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.plv.thirdpart.blankj.utilcode.util.CrashUtils.AnonymousClass1.RunnableC02331.run():void");
                        }
                    });
                    if (CrashUtils.DEFAULT_UNCAUGHT_EXCEPTION_HANDLER != null) {
                        CrashUtils.DEFAULT_UNCAUGHT_EXCEPTION_HANDLER.uncaughtException(thread, th);
                    }
                }
            }
        };
    }

    private CrashUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? !file.mkdirs() : !file.isDirectory());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean createOrExistsFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void init() {
        init("");
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            if (!Character.isWhitespace(str.charAt(i2))) {
                return false;
            }
        }
        return true;
    }

    public static void init(@NonNull File file) {
        init(file.getAbsolutePath());
    }

    public static void init(String str) {
        if (isSpace(str)) {
            dir = null;
        } else {
            String str2 = FILE_SEP;
            if (!str.endsWith(str2)) {
                str = str + str2;
            }
            dir = str;
        }
        if ("mounted".equals(Environment.getExternalStorageState()) && Utils.getApp().getExternalCacheDir() != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(Utils.getApp().getExternalCacheDir());
            String str3 = FILE_SEP;
            sb.append(str3);
            sb.append(CrashHianalyticsData.EVENT_ID_CRASH);
            sb.append(str3);
            defaultDir = sb.toString();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Utils.getApp().getCacheDir());
            String str4 = FILE_SEP;
            sb2.append(str4);
            sb2.append(CrashHianalyticsData.EVENT_ID_CRASH);
            sb2.append(str4);
            defaultDir = sb2.toString();
        }
        Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EXCEPTION_HANDLER);
    }
}
