package com.huawei.hms.hatool;

/* loaded from: classes4.dex */
public class y {

    /* renamed from: a, reason: collision with root package name */
    public static z f7885a = new z();

    public static void a(int i2) {
        f7885a.a(i2);
    }

    public static void a(String str, String str2) {
        if (!a() || str == null || str2 == null) {
            return;
        }
        f7885a.b(3, str, str2);
    }

    public static void a(String str, String str2, Object... objArr) {
        if (!c() || str == null || str2 == null) {
            return;
        }
        f7885a.b(4, str, String.format(str2, objArr));
    }

    public static boolean a() {
        return f7885a.b(3);
    }

    public static void b(String str, String str2) {
        if (!b() || str == null || str2 == null) {
            return;
        }
        f7885a.b(6, str, str2);
    }

    public static void b(String str, String str2, Object... objArr) {
        d(str, String.format(str2, objArr));
    }

    public static boolean b() {
        return f7885a.b(6);
    }

    public static void c(String str, String str2) {
        if (!c() || str == null || str2 == null) {
            return;
        }
        f7885a.b(4, str, str2);
    }

    public static boolean c() {
        return f7885a.b(4);
    }

    public static void d(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        f7885a.b(4, str, str2);
    }

    public static boolean d() {
        return f7885a.b(5);
    }

    public static void e(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        f7885a.b(5, str, str2);
    }

    public static void f(String str, String str2) {
        if (!d() || str == null || str2 == null) {
            return;
        }
        f7885a.b(5, str, str2);
    }
}
