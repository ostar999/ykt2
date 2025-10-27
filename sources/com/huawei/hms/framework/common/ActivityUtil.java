package com.huawei.hms.framework.common;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.List;

/* loaded from: classes4.dex */
public class ActivityUtil {
    private static final String TAG = "ActivityUtil";

    public static PendingIntent getActivities(Context context, int i2, Intent[] intentArr, int i3) {
        if (context == null) {
            Logger.w(TAG, "context is null");
            return null;
        }
        try {
            return PendingIntent.getActivities(context, i2, intentArr, i3);
        } catch (RuntimeException e2) {
            Logger.e(TAG, "dealType rethrowFromSystemServer:", e2);
            return null;
        }
    }

    public static boolean isForeground(Context context) {
        ActivityManager activityManager;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        if (context == null || (activityManager = (ActivityManager) ContextCompat.getSystemService(context, PushConstants.INTENT_ACTIVITY_NAME)) == null) {
            return false;
        }
        try {
            runningAppProcesses = activityManager.getRunningAppProcesses();
        } catch (RuntimeException e2) {
            Logger.w(TAG, "activityManager getRunningAppProcesses occur exception: ", e2);
            runningAppProcesses = null;
        }
        if (runningAppProcesses == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            String str = runningAppProcessInfo.processName;
            if (str != null && str.equals(context.getPackageName()) && runningAppProcessInfo.importance == 100) {
                Logger.v(TAG, "isForeground true");
                return true;
            }
        }
        return false;
    }
}
