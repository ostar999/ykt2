package com.xiaomi.push.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.xiaomi.push.ct;
import com.xiaomi.push.ii;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class j {
    public static void a(Context context, String str) {
        ArrayList<ii> arrayListM742a = g.a(context).m742a(str);
        if (arrayListM742a == null || arrayListM742a.size() < 1) {
            return;
        }
        if (g.a(context).b(str) == 0) {
            com.xiaomi.channel.commonutils.logger.b.m117a("appIsUninstalled. failed to delete geofencing with package name. name:" + str);
        }
        Iterator<ii> it = arrayListM742a.iterator();
        while (it.hasNext()) {
            ii next = it.next();
            if (next == null) {
                com.xiaomi.channel.commonutils.logger.b.m117a("appIsUninstalled. failed to find geofence with package name. name:" + str);
                return;
            }
            a(next.m520a(), context);
            if (i.a(context).b(next.m520a()) == 0) {
                com.xiaomi.channel.commonutils.logger.b.m117a("appIsUninstalled. failed to delete geoMessage with package name. name:" + str + ", geoId:" + next.m520a());
            }
        }
    }

    public static void a(Context context, boolean z2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("mipush_extra", 0).edit();
        editorEdit.putBoolean("geo_switch", z2);
        com.xiaomi.push.t.a(editorEdit);
    }

    public static void a(String str, Context context) {
        new ct(context).a(context, "com.xiaomi.xmsf", str);
    }

    public static boolean a(Context context) {
        return a(context, "com.xiaomi.metoknlp", 6);
    }

    public static boolean a(Context context, String str, int i2) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(str, 0);
        } catch (PackageManager.NameNotFoundException unused) {
            packageInfo = null;
        }
        return packageInfo != null && packageInfo.versionCode >= i2;
    }

    public static boolean b(Context context) {
        return a(context, "com.xiaomi.xmsf", 106) && (a(context, "com.xiaomi.metok", 20) || a(context, "com.xiaomi.metoknlp", 6));
    }

    public static boolean c(Context context) {
        return TextUtils.equals(context.getPackageName(), "com.xiaomi.xmsf");
    }

    public static boolean d(Context context) {
        return a(context);
    }

    public static boolean e(Context context) {
        return context.getSharedPreferences("mipush_extra", 4).getBoolean("geo_switch", false);
    }
}
