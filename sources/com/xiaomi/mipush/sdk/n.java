package com.xiaomi.mipush.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    private static int f24575a = -1;

    public static aq a(Context context) {
        try {
            return (context.getPackageManager().getServiceInfo(new ComponentName("com.huawei.hwid", "com.huawei.hms.core.service.HMSCoreService"), 128) == null || !a()) ? aq.OTHER : aq.HUAWEI;
        } catch (Exception unused) {
            return aq.OTHER;
        }
    }

    private static boolean a() {
        try {
            String str = (String) com.xiaomi.push.at.a("android.os.SystemProperties", "get", "ro.build.hw_emui_api_level", "");
            if (!TextUtils.isEmpty(str)) {
                if (Integer.parseInt(str) >= 9) {
                    return true;
                }
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
        }
        return false;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m179a(Context context) {
        Object objA = com.xiaomi.push.at.a(com.xiaomi.push.at.a("com.google.android.gms.common.GoogleApiAvailability", "getInstance", new Object[0]), "isGooglePlayServicesAvailable", context);
        Object objA2 = com.xiaomi.push.at.a("com.google.android.gms.common.ConnectionResult", com.alipay.security.mobile.module.http.model.c.f3449g);
        if (objA2 == null || !(objA2 instanceof Integer)) {
            com.xiaomi.channel.commonutils.logger.b.c("google service is not avaliable");
            f24575a = 0;
            return false;
        }
        int iIntValue = ((Integer) Integer.class.cast(objA2)).intValue();
        if (objA != null) {
            if (objA instanceof Integer) {
                f24575a = ((Integer) Integer.class.cast(objA)).intValue() == iIntValue ? 1 : 0;
            } else {
                f24575a = 0;
                com.xiaomi.channel.commonutils.logger.b.c("google service is not avaliable");
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("is google service can be used");
        sb.append(f24575a > 0);
        com.xiaomi.channel.commonutils.logger.b.c(sb.toString());
        return f24575a > 0;
    }

    public static boolean b(Context context) {
        boolean zBooleanValue = false;
        Object objA = com.xiaomi.push.at.a("com.xiaomi.assemble.control.COSPushManager", "isSupportPush", context);
        if (objA != null && (objA instanceof Boolean)) {
            zBooleanValue = ((Boolean) Boolean.class.cast(objA)).booleanValue();
        }
        com.xiaomi.channel.commonutils.logger.b.c("color os push  is avaliable ? :" + zBooleanValue);
        return zBooleanValue;
    }
}
