package com.huawei.secure.android.common.ssl.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/* loaded from: classes4.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static final String f8418a = "aegis";

    /* renamed from: b, reason: collision with root package name */
    private static SharedPreferences f8419b;

    public static long a(String str, long j2, Context context) {
        return b(context).getLong(str, j2);
    }

    public static synchronized SharedPreferences b(Context context) {
        if (f8419b == null) {
            if (Build.VERSION.SDK_INT >= 24) {
                f8419b = context.createDeviceProtectedStorageContext().getSharedPreferences(f8418a, 0);
            } else {
                f8419b = context.getApplicationContext().getSharedPreferences(f8418a, 0);
            }
        }
        return f8419b;
    }

    public static int a(String str, int i2, Context context) {
        return b(context).getInt(str, i2);
    }

    public static String a(String str, String str2, Context context) {
        return b(context).getString(str, str2);
    }

    public static void a(String str, Context context) {
        b(context).edit().remove(str).apply();
    }

    public static void a(Context context) {
        b(context).edit().clear().apply();
    }

    public static void b(String str, long j2, Context context) {
        b(context).edit().putLong(str, j2).apply();
    }

    public static void b(String str, int i2, Context context) {
        b(context).edit().putInt(str, i2).apply();
    }

    public static void b(String str, String str2, Context context) {
        b(context).edit().putString(str, str2).apply();
    }
}
