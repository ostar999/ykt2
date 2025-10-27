package com.plv.thirdpart.blankj.utilcode.util;

import android.app.ActivityManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public final class ProcessUtils {
    private ProcessUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static Set<String> getAllBackgroundProcesses() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) Utils.getApp().getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses();
        HashSet hashSet = new HashSet();
        Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
        while (it.hasNext()) {
            Collections.addAll(hashSet, it.next().pkgList);
        }
        return hashSet;
    }

    @Nullable
    @Deprecated
    public static String getForegroundProcessName() {
        return null;
    }

    public static Set<String> killAllBackgroundProcesses() {
        ActivityManager activityManager = (ActivityManager) Utils.getApp().getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        HashSet hashSet = new HashSet();
        Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            for (String str : it.next().pkgList) {
                activityManager.killBackgroundProcesses(str);
                hashSet.add(str);
            }
        }
        Iterator<ActivityManager.RunningAppProcessInfo> it2 = activityManager.getRunningAppProcesses().iterator();
        while (it2.hasNext()) {
            for (String str2 : it2.next().pkgList) {
                hashSet.remove(str2);
            }
        }
        return hashSet;
    }

    public static boolean killBackgroundProcesses(@NonNull String str) {
        ActivityManager activityManager = (ActivityManager) Utils.getApp().getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
        if (runningAppProcesses != null && runningAppProcesses.size() != 0) {
            Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
            while (it.hasNext()) {
                if (Arrays.asList(it.next().pkgList).contains(str)) {
                    activityManager.killBackgroundProcesses(str);
                }
            }
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses2 = activityManager.getRunningAppProcesses();
            if (runningAppProcesses2 != null && runningAppProcesses2.size() != 0) {
                Iterator<ActivityManager.RunningAppProcessInfo> it2 = runningAppProcesses2.iterator();
                while (it2.hasNext()) {
                    if (Arrays.asList(it2.next().pkgList).contains(str)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
