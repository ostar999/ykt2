package com.umeng.commonsdk.internal.utils;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: classes6.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23238a = "info";

    /* renamed from: b, reason: collision with root package name */
    private static final String f23239b = "ua";

    public static void a(Context context, String str) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("info", 0);
        if (sharedPreferences != null) {
            sharedPreferences.edit().putString(f23239b, str).commit();
        }
    }

    public static String a(Context context) {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("info", 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getString(f23239b, null);
        }
        return null;
    }
}
