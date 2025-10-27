package com.mobile.auth.d;

import android.text.TextUtils;
import com.mobile.auth.l.k;

/* loaded from: classes4.dex */
class d {
    public static int a(int i2) {
        return k.a("sso_config_xf", "maxFailedLogTimes", i2);
    }

    public static String a(String str) {
        String strA = k.a("sso_config_xf", "config_host", (String) null);
        return TextUtils.isEmpty(strA) ? str : strA;
    }

    public static boolean a() {
        return System.currentTimeMillis() >= k.a("sso_config_xf", "client_valid", 0L);
    }

    public static boolean a(boolean z2) {
        return "1".equals(k.a("sso_config_xf", "CLOSE_IPV4_LIST", !z2 ? "0" : "1"));
    }

    public static int b(int i2) {
        return k.a("sso_config_xf", "pauseTime", i2);
    }

    public static String b(String str) {
        String strA = k.a("sso_config_xf", "https_get_phone_scrip_host", (String) null);
        return TextUtils.isEmpty(strA) ? str : strA;
    }

    public static boolean b(boolean z2) {
        return "1".equals(k.a("sso_config_xf", "CLOSE_IPV6_LIST", !z2 ? "0" : "1"));
    }

    public static String c(String str) {
        String strA = k.a("sso_config_xf", "logHost", "");
        return TextUtils.isEmpty(strA) ? str : strA;
    }

    public static boolean c(boolean z2) {
        String str = !z2 ? "0" : "1";
        return "1".equals(k.a("sso_config_xf", "CLOSE_M008_APPID_LIST", str)) || "1".equals(k.a("sso_config_xf", "CLOSE_M008_SDKVERSION_LIST", str));
    }

    public static boolean d(boolean z2) {
        return k.a("sso_config_xf", "CLOSE_FRIEND_WAPKS", z2 ? "CU" : "").contains("CU");
    }

    public static boolean e(boolean z2) {
        return k.a("sso_config_xf", "CLOSE_FRIEND_WAPKS", z2 ? "CT" : "").contains("CT");
    }

    public static boolean f(boolean z2) {
        return "1".equals(k.a("sso_config_xf", "CLOSE_LOGS_VERSION", z2 ? "1" : "0"));
    }
}
