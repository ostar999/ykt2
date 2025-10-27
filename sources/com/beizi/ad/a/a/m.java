package com.beizi.ad.a.a;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import com.caverock.androidsvg.SVGParser;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

/* loaded from: classes2.dex */
public class m {

    /* renamed from: a, reason: collision with root package name */
    private static String f3707a = "SystemUtil";

    public static long a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return 0L;
        }
    }

    public static long b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return 0L;
        }
    }

    public static String c(Context context) {
        return context.getPackageName();
    }

    public static String d(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return "";
        } catch (Exception e3) {
            e3.printStackTrace();
            return "";
        }
    }

    public static String e(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            return packageManager != null ? String.valueOf(packageManager.getPackageInfo("com.huawei.appmarket", 0).versionCode) : "";
        } catch (Throwable unused) {
            return "";
        }
    }

    public static String f(Context context) {
        return String.valueOf(context.getResources().getDisplayMetrics().widthPixels);
    }

    public static String g(Context context) {
        return String.valueOf(context.getResources().getDisplayMetrics().heightPixels);
    }

    public static String h(Context context) {
        return context.getResources().getConfiguration().orientation == 2 ? "2" : "1";
    }

    public static String i(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);
            return memoryInfo.totalMem + "";
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    public static long c() {
        Long lValueOf = 0L;
        switch (Calendar.getInstance().get(7)) {
            case 1:
            case 2:
            case 4:
            case 6:
                lValueOf = Long.valueOf(System.currentTimeMillis() / 1000);
                break;
            case 3:
            case 5:
            case 7:
                lValueOf = Long.valueOf(System.currentTimeMillis());
                break;
        }
        return lValueOf.longValue();
    }

    public static String a() {
        return (new File("/system/bin/su").exists() && a("/system/bin/su")) ? "yes" : (new File("/system/xbin/su").exists() && a("/system/xbin/su")) ? "yes" : SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO;
    }

    public static String b() {
        try {
            if (!Environment.getExternalStorageState().equals("mounted")) {
                return "";
            }
            StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long blockSize = statFs.getBlockSize();
            return (statFs.getBlockCount() * blockSize) + "";
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    private static boolean a(String str) {
        Process processExec = null;
        try {
            try {
                processExec = Runtime.getRuntime().exec("ls -l " + str);
                String line = new BufferedReader(new InputStreamReader(processExec.getInputStream())).readLine();
                if (line != null && line.length() >= 4) {
                    char cCharAt = line.charAt(3);
                    if (cCharAt == 's' || cCharAt == 'x') {
                        processExec.destroy();
                        return true;
                    }
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (processExec == null) {
                    return false;
                }
            }
            processExec.destroy();
            return false;
        } catch (Throwable th) {
            if (processExec != null) {
                processExec.destroy();
            }
            throw th;
        }
    }
}
