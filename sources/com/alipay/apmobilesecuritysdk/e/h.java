package com.alipay.apmobilesecuritysdk.e;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.UUID;

/* loaded from: classes2.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private static String f3037a = "";

    public static long a(Context context) {
        String strA = com.alipay.security.mobile.module.c.a.a(context, "vkeyid_settings", "update_time_interval");
        if (!com.alipay.security.mobile.module.a.a.b(strA)) {
            return 86400000L;
        }
        try {
            return Long.parseLong(strA);
        } catch (Exception unused) {
            return 86400000L;
        }
    }

    public static void a(Context context, String str) {
        a(context, "update_time_interval", str);
    }

    public static void a(Context context, String str, long j2) {
        com.alipay.security.mobile.module.c.a.a(context, "vkeyid_settings", "vkey_valid" + str, String.valueOf(j2));
    }

    private static void a(Context context, String str, String str2) {
        com.alipay.security.mobile.module.c.a.a(context, "vkeyid_settings", str, str2);
    }

    public static void a(Context context, boolean z2) {
        a(context, "log_switch", z2 ? "1" : "0");
    }

    public static String b(Context context) {
        return com.alipay.security.mobile.module.c.a.a(context, "vkeyid_settings", "last_apdid_env");
    }

    public static void b(Context context, String str) {
        a(context, "last_machine_boot_time", str);
    }

    public static void c(Context context, String str) {
        a(context, "last_apdid_env", str);
    }

    public static boolean c(Context context) {
        String strA = com.alipay.security.mobile.module.c.a.a(context, "vkeyid_settings", "log_switch");
        return strA != null && "1".equals(strA);
    }

    public static String d(Context context) {
        return com.alipay.security.mobile.module.c.a.a(context, "vkeyid_settings", "dynamic_key");
    }

    public static void d(Context context, String str) {
        a(context, "agent_switch", str);
    }

    public static String e(Context context) {
        return com.alipay.security.mobile.module.c.a.a(context, "vkeyid_settings", "apse_degrade");
    }

    public static void e(Context context, String str) {
        a(context, "dynamic_key", str);
    }

    public static String f(Context context) {
        String str;
        SharedPreferences.Editor editorEdit;
        synchronized (h.class) {
            if (com.alipay.security.mobile.module.a.a.a(f3037a)) {
                String strA = com.alipay.security.mobile.module.c.e.a(context, "alipay_vkey_random", "random", "");
                f3037a = strA;
                if (com.alipay.security.mobile.module.a.a.a(strA)) {
                    String strA2 = com.alipay.security.mobile.module.a.a.b.a(UUID.randomUUID().toString());
                    f3037a = strA2;
                    if (strA2 != null && (editorEdit = context.getSharedPreferences("alipay_vkey_random", 0).edit()) != null) {
                        editorEdit.putString("random", strA2);
                        editorEdit.commit();
                    }
                }
            }
            str = f3037a;
        }
        return str;
    }

    public static void f(Context context, String str) {
        a(context, "webrtc_url", str);
    }

    public static void g(Context context, String str) {
        a(context, "apse_degrade", str);
    }

    public static long h(Context context, String str) {
        try {
            String strA = com.alipay.security.mobile.module.c.a.a(context, "vkeyid_settings", "vkey_valid" + str);
            if (com.alipay.security.mobile.module.a.a.a(strA)) {
                return 0L;
            }
            return Long.parseLong(strA);
        } catch (Throwable unused) {
            return 0L;
        }
    }
}
