package com.aliyun.sls.android.producer.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.List;

/* loaded from: classes2.dex */
public final class ProcessUtils {
    private static String sProcessName;

    public static String getCurrentProcessName(Context context) throws Throwable {
        if (!TextUtils.isEmpty(sProcessName)) {
            return sProcessName;
        }
        String processNameByApplication = getProcessNameByApplication();
        if (!TextUtils.isEmpty(processNameByApplication)) {
            sProcessName = processNameByApplication;
            return processNameByApplication;
        }
        String processNameViaLinuxFile = getProcessNameViaLinuxFile();
        if (!TextUtils.isEmpty(processNameViaLinuxFile)) {
            sProcessName = processNameViaLinuxFile;
            return processNameViaLinuxFile;
        }
        String processNameViaActivityManager = getProcessNameViaActivityManager(context);
        if (TextUtils.isEmpty(processNameViaActivityManager)) {
            return sProcessName;
        }
        sProcessName = processNameViaActivityManager;
        return processNameViaActivityManager;
    }

    private static String getProcessNameByApplication() {
        if (Build.VERSION.SDK_INT >= 28) {
            return Application.getProcessName();
        }
        return null;
    }

    private static String getProcessNameViaActivityManager(Context context) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        if (context == null) {
            return null;
        }
        int iMyPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (activityManager == null || (runningAppProcesses = activityManager.getRunningAppProcesses()) == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo != null && runningAppProcessInfo.pid == iMyPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String getProcessNameViaLinuxFile() throws java.lang.Throwable {
        /*
            int r0 = android.os.Process.myPid()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "/proc/"
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = "/cmdline"
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r1 = 1024(0x400, float:1.435E-42)
            byte[] r1 = new byte[r1]
            r2 = 0
            r3 = 0
            java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L41
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L41
            int r0 = r4.read(r1)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L42
            r4.close()     // Catch: java.io.IOException -> L2d
            goto L4d
        L2d:
            r4 = move-exception
            r4.printStackTrace()
            goto L4d
        L32:
            r0 = move-exception
            r2 = r4
            goto L36
        L35:
            r0 = move-exception
        L36:
            if (r2 == 0) goto L40
            r2.close()     // Catch: java.io.IOException -> L3c
            goto L40
        L3c:
            r1 = move-exception
            r1.printStackTrace()
        L40:
            throw r0
        L41:
            r4 = r2
        L42:
            if (r4 == 0) goto L4c
            r4.close()     // Catch: java.io.IOException -> L48
            goto L4c
        L48:
            r0 = move-exception
            r0.printStackTrace()
        L4c:
            r0 = r3
        L4d:
            if (r0 <= 0) goto L58
            java.lang.String r2 = new java.lang.String
            r2.<init>(r1, r3, r0)
            java.lang.String r2 = r2.trim()
        L58:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.aliyun.sls.android.producer.utils.ProcessUtils.getProcessNameViaLinuxFile():java.lang.String");
    }

    public static boolean isInProcess(Context context, String processName) {
        return processName != null && processName.equalsIgnoreCase(getCurrentProcessName(context));
    }

    public static boolean isMainProcess(Context context) {
        return isInProcess(context, context.getPackageName());
    }
}
