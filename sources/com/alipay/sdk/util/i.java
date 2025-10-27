package com.alipay.sdk.util;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/* loaded from: classes2.dex */
public final class i {

    /* renamed from: a, reason: collision with root package name */
    private static String f3382a;

    public static void a(Context context, String str) {
        try {
            PreferenceManager.getDefaultSharedPreferences(context).edit().remove(str).commit();
        } catch (Throwable unused) {
        }
    }

    private static boolean b(Context context, String str) {
        try {
            return PreferenceManager.getDefaultSharedPreferences(context).contains(str);
        } catch (Throwable unused) {
            return false;
        }
    }

    public static void a(Context context, String str, String str2) {
        try {
            String strA = com.alipay.sdk.encrypt.e.a(a(context), str2);
            if (!TextUtils.isEmpty(str2) && TextUtils.isEmpty(strA)) {
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3113c, com.alipay.sdk.app.statistic.c.f3133w, String.format("%s,%s", str, str2));
            }
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(str, strA).commit();
        } catch (Throwable unused) {
        }
    }

    public static String b(Context context, String str, String str2) {
        try {
            String string = PreferenceManager.getDefaultSharedPreferences(context).getString(str, str2);
            strB = TextUtils.isEmpty(string) ? null : com.alipay.sdk.encrypt.e.b(a(context), string);
            if (!TextUtils.isEmpty(string) && TextUtils.isEmpty(strB)) {
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3113c, com.alipay.sdk.app.statistic.c.f3132v, String.format("%s,%s", str, string));
            }
        } catch (Exception unused) {
        }
        return strB;
    }

    private static String a(Context context) {
        String packageName;
        if (TextUtils.isEmpty(f3382a)) {
            try {
                packageName = context.getApplicationContext().getPackageName();
            } catch (Throwable unused) {
                packageName = "";
            }
            f3382a = (packageName + "0000000000000000000000000000").substring(0, 24);
        }
        return f3382a;
    }
}
