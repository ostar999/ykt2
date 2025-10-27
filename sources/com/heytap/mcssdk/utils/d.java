package com.heytap.mcssdk.utils;

import android.util.Log;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static final String f7216a = "mcssdk---";

    /* renamed from: b, reason: collision with root package name */
    private static String f7217b = "MCS";

    /* renamed from: c, reason: collision with root package name */
    private static boolean f7218c = false;

    /* renamed from: d, reason: collision with root package name */
    private static boolean f7219d = false;

    /* renamed from: e, reason: collision with root package name */
    private static boolean f7220e = true;

    /* renamed from: f, reason: collision with root package name */
    private static boolean f7221f = true;

    /* renamed from: g, reason: collision with root package name */
    private static boolean f7222g = true;

    /* renamed from: h, reason: collision with root package name */
    private static String f7223h = "-->";

    /* renamed from: i, reason: collision with root package name */
    private static boolean f7224i = true;

    public static String a() {
        return f7217b;
    }

    public static void a(Exception exc) {
        if (!f7222g || exc == null) {
            return;
        }
        Log.e(f7216a, exc.getMessage());
    }

    public static void a(String str) {
        if (f7218c && f7224i) {
            Log.v(f7216a, f7217b + f7223h + str);
        }
    }

    public static void a(String str, String str2) {
        if (f7218c && f7224i) {
            Log.v(str, f7217b + f7223h + str2);
        }
    }

    public static void a(String str, Throwable th) {
        if (f7222g) {
            Log.e(str, th.toString());
        }
    }

    public static void a(boolean z2) {
        f7218c = z2;
    }

    public static void b(String str) {
        if (f7220e && f7224i) {
            Log.d(f7216a, f7217b + f7223h + str);
        }
    }

    public static void b(String str, String str2) {
        if (f7220e && f7224i) {
            Log.d(str, f7217b + f7223h + str2);
        }
    }

    public static void b(boolean z2) {
        f7220e = z2;
    }

    public static boolean b() {
        return f7218c;
    }

    public static void c(String str) {
        if (f7219d && f7224i) {
            Log.i(f7216a, f7217b + f7223h + str);
        }
    }

    public static void c(String str, String str2) {
        if (f7219d && f7224i) {
            Log.i(str, f7217b + f7223h + str2);
        }
    }

    public static void c(boolean z2) {
        f7219d = z2;
    }

    public static boolean c() {
        return f7220e;
    }

    public static void d(String str) {
        if (f7221f && f7224i) {
            Log.w(f7216a, f7217b + f7223h + str);
        }
    }

    public static void d(String str, String str2) {
        if (f7221f && f7224i) {
            Log.w(str, f7217b + f7223h + str2);
        }
    }

    public static void d(boolean z2) {
        f7221f = z2;
    }

    public static boolean d() {
        return f7219d;
    }

    public static void e(String str) {
        if (f7222g && f7224i) {
            Log.e(f7216a, f7217b + f7223h + str);
        }
    }

    public static void e(String str, String str2) {
        if (f7222g && f7224i) {
            Log.e(str, f7217b + f7223h + str2);
        }
    }

    public static void e(boolean z2) {
        f7222g = z2;
    }

    public static boolean e() {
        return f7221f;
    }

    public static void f(String str) {
        f7217b = str;
    }

    public static void f(boolean z2) {
        f7224i = z2;
        boolean z3 = z2;
        f7218c = z3;
        f7220e = z3;
        f7219d = z3;
        f7221f = z3;
        f7222g = z3;
    }

    public static boolean f() {
        return f7222g;
    }

    public static void g(String str) {
        f7223h = str;
    }

    public static boolean g() {
        return f7224i;
    }

    public static String h() {
        return f7223h;
    }
}
