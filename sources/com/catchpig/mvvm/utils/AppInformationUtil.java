package com.catchpig.mvvm.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import com.catchpig.mvvm.R;
import com.catchpig.utils.manager.ContextManager;
import com.hjq.toast.Toaster;

/* loaded from: classes2.dex */
public class AppInformationUtil {
    private static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String getUMChannel() throws PackageManager.NameNotFoundException {
        try {
            ContextManager.Companion companion = ContextManager.INSTANCE;
            ApplicationInfo applicationInfo = companion.getInstance().getContext().getPackageManager().getApplicationInfo(companion.getInstance().getContext().getPackageName(), 128);
            Log.i("H0 channel:", applicationInfo.metaData.getString("UMENG_CHANNEL"));
            return applicationInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static int getVersionCode() {
        return getPackageInfo(ContextManager.INSTANCE.getInstance().getContext()).versionCode;
    }

    public static String getVersionName() {
        return getPackageInfo(ContextManager.INSTANCE.getInstance().getContext()).versionName;
    }

    public static boolean isApkDebugable() {
        try {
            return (ContextManager.INSTANCE.getInstance().getContext().getApplicationInfo().flags & 2) != 0;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isHarmonyOS() throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("com.huawei.system.BuildEx");
            return "harmony".equals(cls.getMethod("getOsBrand", new Class[0]).invoke(cls, new Object[0]));
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static void openBrowser(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        if (intent.resolveActivity(context.getPackageManager()) == null) {
            Toaster.show(R.string.common_browser_error);
        } else {
            intent.resolveActivity(context.getPackageManager());
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.common_browser)));
        }
    }
}
