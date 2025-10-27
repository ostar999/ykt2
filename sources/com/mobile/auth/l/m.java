package com.mobile.auth.l;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.xiaomi.mipush.sdk.Constants;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class m {
    public static int a(Context context, boolean z2) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        try {
            connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            int type = activeNetworkInfo.getType();
            if (type != 1) {
                if (type == 0) {
                    c.b("TelephonyUtils", "流量");
                    return 1;
                }
                return 0;
            }
            c.b("TelephonyUtils", "WIFI");
            boolean zA = g.a(context, "android.permission.CHANGE_NETWORK_STATE");
            c.a("TelephonyUtils", "CHANGE_NETWORK_STATE=" + zA);
            if (!zA || !z2 || !a(connectivityManager, context)) {
                return 2;
            }
            c.b("TelephonyUtils", "流量数据 WIFI 同开");
            return 3;
        }
        return 0;
    }

    public static String a() {
        return Build.BRAND;
    }

    public static boolean a(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE);
        return telephonyManager == null || 1 != telephonyManager.getSimState();
    }

    private static boolean a(ConnectivityManager connectivityManager, Context context) throws NoSuchMethodException, SecurityException {
        TelephonyManager telephonyManager;
        try {
            if (Build.VERSION.SDK_INT >= 26 && (telephonyManager = (TelephonyManager) context.getSystemService(AliyunLogCommon.TERMINAL_TYPE)) != null) {
                return telephonyManager.isDataEnabled();
            }
            Method declaredMethod = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled", new Class[0]);
            declaredMethod.setAccessible(true);
            boolean zBooleanValue = ((Boolean) declaredMethod.invoke(connectivityManager, new Object[0])).booleanValue();
            c.b("TelephonyUtils", "data is on ---------" + zBooleanValue);
            return zBooleanValue;
        } catch (Exception unused) {
            c.a("TelephonyUtils", "isMobileEnabled ----反射出错-----");
            return false;
        }
    }

    public static String b() {
        return Build.MODEL;
    }

    public static String c() {
        return "android" + Build.VERSION.RELEASE;
    }

    public static boolean d() {
        return Build.VERSION.SDK_INT <= 28;
    }

    public static boolean e() {
        String str = Build.MANUFACTURER;
        c.a(Constants.PHONE_BRAND, str);
        return "HUAWEI".equalsIgnoreCase(str);
    }
}
