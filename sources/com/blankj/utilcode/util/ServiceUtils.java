package com.blankj.utilcode.util;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import androidx.annotation.NonNull;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public final class ServiceUtils {
    private ServiceUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void bindService(@NonNull String str, @NonNull ServiceConnection serviceConnection, int i2) {
        try {
            bindService(Class.forName(str), serviceConnection, i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static Set<String> getAllRunningServices() throws SecurityException {
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

    public static boolean isServiceRunning(@NonNull Class<?> cls) {
        return isServiceRunning(cls.getName());
    }

    public static void startService(@NonNull String str) {
        try {
            startService(Class.forName(str));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean stopService(@NonNull String str) {
        try {
            return stopService(Class.forName(str));
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void unbindService(@NonNull ServiceConnection serviceConnection) {
        Utils.getApp().unbindService(serviceConnection);
    }

    public static boolean isServiceRunning(@NonNull String str) throws SecurityException {
        try {
            List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) Utils.getApp().getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningServices(Integer.MAX_VALUE);
            if (runningServices != null && runningServices.size() != 0) {
                Iterator<ActivityManager.RunningServiceInfo> it = runningServices.iterator();
                while (it.hasNext()) {
                    if (str.equals(it.next().service.getClassName())) {
                        return true;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    public static void bindService(@NonNull Class<?> cls, @NonNull ServiceConnection serviceConnection, int i2) {
        bindService(new Intent(Utils.getApp(), cls), serviceConnection, i2);
    }

    public static void startService(@NonNull Class<?> cls) {
        startService(new Intent(Utils.getApp(), cls));
    }

    public static boolean stopService(@NonNull Class<?> cls) {
        return stopService(new Intent(Utils.getApp(), cls));
    }

    public static void bindService(@NonNull Intent intent, @NonNull ServiceConnection serviceConnection, int i2) {
        try {
            Utils.getApp().bindService(intent, serviceConnection, i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void startService(Intent intent) {
        try {
            intent.setFlags(32);
            if (Build.VERSION.SDK_INT >= 26) {
                Utils.getApp().startForegroundService(intent);
            } else {
                Utils.getApp().startService(intent);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static boolean stopService(@NonNull Intent intent) {
        try {
            return Utils.getApp().stopService(intent);
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }
}
