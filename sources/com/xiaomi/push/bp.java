package com.xiaomi.push;

import android.content.Context;
import android.telephony.TelephonyManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.hjq.permissions.Permission;

/* loaded from: classes6.dex */
public class bp {

    /* renamed from: a, reason: collision with root package name */
    private static Context f24647a;

    /* renamed from: a, reason: collision with other field name */
    private static TelephonyManager f230a;

    public static String a() {
        TelephonyManager telephonyManager = f230a;
        if (telephonyManager != null) {
            return telephonyManager.getNetworkOperator();
        }
        return null;
    }

    public static void a(Context context) {
        f24647a = context;
        f230a = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
    }

    public static String b() {
        TelephonyManager telephonyManager;
        String deviceId = null;
        try {
            Context context = f24647a;
            if (context != null && context.getPackageManager().checkPermission(Permission.READ_PHONE_STATE, f24647a.getPackageName()) == 0 && (telephonyManager = f230a) != null) {
                deviceId = telephonyManager.getDeviceId();
            }
        } catch (Exception unused) {
        }
        return deviceId != null ? deviceId : "UNKNOWN";
    }
}
