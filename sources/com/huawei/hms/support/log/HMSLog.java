package com.huawei.hms.support.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import cn.hutool.core.text.StrPool;
import com.huawei.hms.base.log.a;
import com.huawei.hms.base.log.d;

/* loaded from: classes4.dex */
public class HMSLog {

    /* renamed from: a, reason: collision with root package name */
    public static final a f8096a = new a();

    public static String a(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 16384);
                return "HMS-" + packageInfo.versionName + "(" + packageInfo.versionCode + ")";
            } catch (PackageManager.NameNotFoundException | RuntimeException unused) {
            }
        }
        return "HMS-[unknown-version]";
    }

    public static void d(String str, String str2) {
        f8096a.a(3, str, str2);
    }

    public static void e(String str, String str2) {
        f8096a.a(6, str, str2);
    }

    public static void i(String str, String str2) {
        f8096a.a(4, str, str2);
    }

    public static void init(Context context, int i2, String str) {
        a aVar = f8096a;
        aVar.a(context, i2, str);
        aVar.a(str, "============================================================================\n====== " + a(context) + "\n============================================================================");
    }

    public static boolean isErrorEnable() {
        return f8096a.a(6);
    }

    public static boolean isInfoEnable() {
        return f8096a.a(4);
    }

    public static boolean isWarnEnable() {
        return f8096a.a(5);
    }

    public static void setExtLogger(HMSExtLogger hMSExtLogger, boolean z2) throws IllegalArgumentException {
        if (hMSExtLogger == null) {
            throw new IllegalArgumentException("extLogger is not able to be null");
        }
        d dVar = new d(hMSExtLogger);
        if (z2) {
            f8096a.a(dVar);
        } else {
            f8096a.a().a(dVar);
        }
    }

    public static void w(String str, String str2) {
        f8096a.a(5, str, str2);
    }

    public static void e(String str, String str2, Throwable th) {
        f8096a.b(6, str, str2, th);
    }

    public static void e(String str, long j2, String str2) {
        f8096a.a(6, str, StrPool.BRACKET_START + j2 + "] " + str2);
    }

    public static void e(String str, long j2, String str2, Throwable th) {
        f8096a.b(6, str, StrPool.BRACKET_START + j2 + "] " + str2, th);
    }
}
