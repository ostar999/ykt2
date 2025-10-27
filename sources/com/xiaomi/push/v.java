package com.xiaomi.push;

import android.content.Context;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class v {

    /* renamed from: a, reason: collision with root package name */
    private static Context f25728a;

    public static int a() throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName("miui.os.Build");
            if (cls.getField("IS_STABLE_VERSION").getBoolean(null)) {
                return 3;
            }
            return cls.getField("IS_DEVELOPMENT_VERSION").getBoolean(null) ? 2 : 1;
        } catch (Exception unused) {
            return 0;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static Context m770a() {
        return f25728a;
    }

    public static void a(Context context) {
        f25728a = context.getApplicationContext();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m771a() {
        return TextUtils.equals((String) at.a("android.os.SystemProperties", "get", "sys.boot_completed"), "1");
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m772a(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }

    public static boolean b() {
        try {
            return Class.forName("miui.os.Build").getField("IS_GLOBAL_BUILD").getBoolean(Boolean.FALSE);
        } catch (ClassNotFoundException unused) {
            com.xiaomi.channel.commonutils.logger.b.d("miui.os.Build ClassNotFound");
            return false;
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            return false;
        }
    }
}
