package com.alipay.sdk.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/* loaded from: classes2.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    public static final boolean f3345a = false;

    /* renamed from: b, reason: collision with root package name */
    public static final String f3346b = "mspstd";

    private static void a() {
    }

    private static void a(Object obj) {
        boolean z2 = obj instanceof Exception;
    }

    private static void b() {
    }

    private static void c() {
    }

    private static void d() {
    }

    private static void e() {
    }

    private static void f() {
    }

    private static void g() {
    }

    private static void h() {
    }

    private static String a(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
