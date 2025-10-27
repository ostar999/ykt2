package com.aliyun.vod.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/* loaded from: classes2.dex */
public class ManifestUtils {
    public static String getAppName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int i2 = applicationInfo.labelRes;
        return i2 == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(i2);
    }

    public static String getChannelNo(Context context, String str) {
        return getMetaData(context, str);
    }

    public static String getMetaData(Context context, String str) {
        String string;
        try {
            string = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData.getString(str);
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            string = "";
        }
        return StringUtils.isEmpty(string) ? "" : string;
    }

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static String getVersionName(Context context) {
        String str;
        try {
            str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
            str = "";
        }
        return StringUtils.isEmpty(str) ? "" : str;
    }
}
