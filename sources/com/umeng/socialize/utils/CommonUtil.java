package com.umeng.socialize.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class CommonUtil {
    public static boolean checkAndroidManifest(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), str), 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean checkIntentFilterData(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse(SocializeProtocolConstants.PROTOCOL_KEY_TENCENT + str + ":"));
        List<ResolveInfo> listQueryIntentActivities = context.getPackageManager().queryIntentActivities(intent, 64);
        if (listQueryIntentActivities.size() > 0) {
            Iterator<ResolveInfo> it = listQueryIntentActivities.iterator();
            while (it.hasNext()) {
                ActivityInfo activityInfo = it.next().activityInfo;
                if (activityInfo != null && activityInfo.packageName.equals(context.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String checkKeyHash(Context context) {
        return "";
    }

    public static String checkMD5Sign(Context context) {
        return "";
    }

    public static boolean checkMetaData(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                if (applicationInfo.metaData.get(str) != null) {
                    return true;
                }
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public static boolean checkPath(String str) throws ClassNotFoundException {
        try {
            Class.forName(str);
            return true;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    public static boolean checkPermission(Context context, String str) {
        return context.getPackageManager().checkPermission(str, context.getPackageName()) == 0;
    }

    public static boolean checkResource(Context context, String str, String str2) {
        return context.getResources().getIdentifier(str, str2, context.getPackageName()) > 0;
    }

    public static String checkSHA1(Context context) {
        return "";
    }
}
