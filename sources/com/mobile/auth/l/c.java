package com.mobile.auth.l;

import android.util.Log;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final c f10415a = new c();

    /* renamed from: b, reason: collision with root package name */
    private static boolean f10416b = false;

    public static void a(String str, String str2) {
        if (f10416b) {
            Log.e("CMCC-SDK:" + str, "" + str2);
        }
    }

    public static void a(boolean z2) {
        f10416b = z2;
    }

    public static void b(String str, String str2) {
        if (f10416b) {
            Log.d("CMCC-SDK:" + str, "" + str2);
        }
    }
}
