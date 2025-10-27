package com.alipay.apmobilesecuritysdk.e;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public final class i {

    /* renamed from: a, reason: collision with root package name */
    private static String f3038a = "";

    /* renamed from: b, reason: collision with root package name */
    private static String f3039b = "";

    /* renamed from: c, reason: collision with root package name */
    private static String f3040c = "";

    /* renamed from: d, reason: collision with root package name */
    private static String f3041d = "";

    /* renamed from: e, reason: collision with root package name */
    private static String f3042e = "";

    /* renamed from: f, reason: collision with root package name */
    private static Map<String, String> f3043f = new HashMap();

    public static synchronized String a(String str) {
        String str2 = "apdidTokenCache" + str;
        if (f3043f.containsKey(str2)) {
            String str3 = f3043f.get(str2);
            if (com.alipay.security.mobile.module.a.a.b(str3)) {
                return str3;
            }
        }
        return "";
    }

    public static synchronized void a() {
    }

    public static synchronized void a(b bVar) {
        if (bVar != null) {
            f3038a = bVar.a();
            f3039b = bVar.b();
            f3040c = bVar.c();
        }
    }

    public static synchronized void a(c cVar) {
        if (cVar != null) {
            f3038a = cVar.a();
            f3039b = cVar.b();
            f3041d = cVar.d();
            f3042e = cVar.e();
            f3040c = cVar.c();
        }
    }

    public static synchronized void a(String str, String str2) {
        String str3 = "apdidTokenCache" + str;
        if (f3043f.containsKey(str3)) {
            f3043f.remove(str3);
        }
        f3043f.put(str3, str2);
    }

    public static synchronized boolean a(Context context, String str) {
        long j2 = 86400000;
        try {
            long jA = h.a(context);
            if (jA >= 0) {
                j2 = jA;
            }
        } catch (Throwable unused) {
        }
        try {
            if (Math.abs(System.currentTimeMillis() - h.h(context, str)) < j2) {
                return true;
            }
        } catch (Throwable th) {
            com.alipay.apmobilesecuritysdk.c.a.a(th);
        }
        return false;
    }

    public static synchronized String b() {
        return f3038a;
    }

    public static void b(String str) {
        f3038a = str;
    }

    public static synchronized String c() {
        return f3039b;
    }

    public static void c(String str) {
        f3039b = str;
    }

    public static synchronized String d() {
        return f3041d;
    }

    public static void d(String str) {
        f3040c = str;
    }

    public static synchronized String e() {
        return f3042e;
    }

    public static void e(String str) {
        f3041d = str;
    }

    public static synchronized String f() {
        return f3040c;
    }

    public static void f(String str) {
        f3042e = str;
    }

    public static synchronized c g() {
        return new c(f3038a, f3039b, f3040c, f3041d, f3042e);
    }

    public static void h() {
        f3043f.clear();
        f3038a = "";
        f3039b = "";
        f3041d = "";
        f3042e = "";
        f3040c = "";
    }
}
