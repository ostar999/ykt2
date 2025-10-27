package com.plv.thirdpart.blankj.utilcode.util;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.ServiceConnection;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes5.dex */
public final class ServiceUtils {
    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void bindService(String str, ServiceConnection serviceConnection, int i2) {
        try {
            bindService(Class.forName(str), serviceConnection, i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static Set getAllRunningService() throws SecurityException {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) Utils.getApp().getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningServices(Integer.MAX_VALUE);
        HashSet hashSet = new HashSet();
        if (runningServices == null || runningServices.size() == 0) {
            return null;
        }
        Iterator<ActivityManager.RunningServiceInfo> it = runningServices.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().service.getClassName());
        }
        return hashSet;
    }

    public static boolean isServiceRunning(String str) throws SecurityException {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) Utils.getApp().getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningServices(Integer.MAX_VALUE);
        if (runningServices != null && runningServices.size() != 0) {
            Iterator<ActivityManager.RunningServiceInfo> it = runningServices.iterator();
            while (it.hasNext()) {
                if (str.equals(it.next().service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void startService(String str) {
        try {
            startService(Class.forName(str));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean stopService(String str) {
        try {
            return stopService(Class.forName(str));
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void unbindService(ServiceConnection serviceConnection) {
        Utils.getApp().unbindService(serviceConnection);
    }

    public static void bindService(Class<?> cls, ServiceConnection serviceConnection, int i2) {
        Utils.getApp().bindService(new Intent(Utils.getApp(), cls), serviceConnection, i2);
    }

    public static void startService(Class<?> cls) {
        Utils.getApp().startService(new Intent(Utils.getApp(), cls));
    }

    public static boolean stopService(Class<?> cls) {
        return Utils.getApp().stopService(new Intent(Utils.getApp(), cls));
    }
}
