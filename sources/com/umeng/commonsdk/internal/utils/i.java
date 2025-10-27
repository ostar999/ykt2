package com.umeng.commonsdk.internal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23240a = "um_pri";

    /* renamed from: b, reason: collision with root package name */
    private static final String f23241b = "um_common_strength";

    /* renamed from: c, reason: collision with root package name */
    private static final String f23242c = "um_common_battery";

    public static String a(Context context) {
        SharedPreferences sharedPreferences;
        if (context == null || (sharedPreferences = context.getApplicationContext().getSharedPreferences(f23240a, 0)) == null) {
            return null;
        }
        return sharedPreferences.getString(f23242c, null);
    }

    public static void a(Context context, String str) {
        SharedPreferences sharedPreferences;
        if (context == null || TextUtils.isEmpty(str) || (sharedPreferences = context.getApplicationContext().getSharedPreferences(f23240a, 0)) == null) {
            return;
        }
        sharedPreferences.edit().putString(f23242c, str).commit();
    }
}
