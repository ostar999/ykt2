package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import java.util.List;

/* loaded from: classes6.dex */
public class ex {
    public static boolean a(Context context, String str) {
        try {
            ServiceInfo[] serviceInfoArr = context.getPackageManager().getPackageInfo(str, 4).services;
            if (serviceInfoArr == null) {
                return false;
            }
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                if (serviceInfo.exported && serviceInfo.enabled && "com.xiaomi.mipush.sdk.PushMessageHandler".equals(serviceInfo.name) && !context.getPackageName().equals(serviceInfo.packageName)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }

    public static boolean a(Context context, String str, String str2) {
        try {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent(str2);
            intent.setPackage(str);
            List<ResolveInfo> listQueryIntentServices = packageManager.queryIntentServices(intent, 32);
            if (listQueryIntentServices != null) {
                return !listQueryIntentServices.isEmpty();
            }
            return false;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }

    public static boolean b(Context context, String str) {
        boolean z2 = false;
        try {
            List<ProviderInfo> listQueryContentProviders = context.getPackageManager().queryContentProviders((String) null, 0, 8);
            if (listQueryContentProviders != null && !listQueryContentProviders.isEmpty()) {
                for (ProviderInfo providerInfo : listQueryContentProviders) {
                    if (providerInfo.enabled && providerInfo.exported && providerInfo.authority.equals(str)) {
                        z2 = true;
                    }
                }
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
        return z2;
    }

    public static boolean b(Context context, String str, String str2) {
        try {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent(str2);
            intent.setPackage(str);
            List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 32);
            if (listQueryIntentActivities != null) {
                return !listQueryIntentActivities.isEmpty();
            }
            return false;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }
}
