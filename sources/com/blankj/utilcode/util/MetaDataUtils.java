package com.blankj.utilcode.util;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;

/* loaded from: classes2.dex */
public final class MetaDataUtils {
    private MetaDataUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getMetaDataInActivity(@NonNull Activity activity, @NonNull String str) {
        return getMetaDataInActivity((Class<? extends Activity>) activity.getClass(), str);
    }

    public static String getMetaDataInApp(@NonNull String str) {
        try {
            return String.valueOf(Utils.getApp().getPackageManager().getApplicationInfo(Utils.getApp().getPackageName(), 128).metaData.get(str));
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String getMetaDataInReceiver(@NonNull BroadcastReceiver broadcastReceiver, @NonNull String str) {
        return getMetaDataInReceiver((Class<? extends BroadcastReceiver>) broadcastReceiver.getClass(), str);
    }

    public static String getMetaDataInService(@NonNull Service service, @NonNull String str) {
        return getMetaDataInService((Class<? extends Service>) service.getClass(), str);
    }

    public static String getMetaDataInActivity(@NonNull Class<? extends Activity> cls, @NonNull String str) {
        try {
            return String.valueOf(Utils.getApp().getPackageManager().getActivityInfo(new ComponentName(Utils.getApp(), cls), 128).metaData.get(str));
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String getMetaDataInReceiver(@NonNull Class<? extends BroadcastReceiver> cls, @NonNull String str) {
        try {
            return String.valueOf(Utils.getApp().getPackageManager().getReceiverInfo(new ComponentName(Utils.getApp(), cls), 128).metaData.get(str));
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String getMetaDataInService(@NonNull Class<? extends Service> cls, @NonNull String str) {
        try {
            return String.valueOf(Utils.getApp().getPackageManager().getServiceInfo(new ComponentName(Utils.getApp(), cls), 128).metaData.get(str));
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return "";
        }
    }
}
