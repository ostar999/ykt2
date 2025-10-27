package com.ta.a.e;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Process;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes6.dex */
public class a {
    public static int a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.targetSdkVersion;
        } catch (Exception e2) {
            h.a("", e2, new Object[0]);
            return 0;
        }
    }

    public static String c(Context context) {
        PackageInfo packageInfoM105a = m105a(context);
        return packageInfoM105a != null ? packageInfoM105a.packageName : "";
    }

    public static String d(Context context) {
        try {
            int iMyPid = Process.myPid();
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == iMyPid) {
                    return runningAppProcessInfo.processName;
                }
            }
        } catch (Exception unused) {
        }
        return null;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static PackageInfo m105a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e2) {
            h.a("", e2, new Object[0]);
            return null;
        }
    }

    /* renamed from: d, reason: collision with other method in class */
    public static boolean m106d(Context context) {
        String strD = d(context);
        String strC = c(context);
        return TextUtils.isEmpty(strD) || TextUtils.isEmpty(strC) || strD.equals(strC);
    }
}
