package com.vivo.push.util;

import android.content.Context;
import com.caverock.androidsvg.SVGParser;

/* loaded from: classes6.dex */
public final class p {

    /* renamed from: a, reason: collision with root package name */
    public static final o f24466a = new n();

    /* renamed from: b, reason: collision with root package name */
    private static boolean f24467b;

    /* renamed from: c, reason: collision with root package name */
    private static boolean f24468c;

    static {
        b();
    }

    public static boolean a() {
        return f24467b && f24468c;
    }

    private static void b() {
        f24467b = z.b("persist.sys.log.ctrl", SVGParser.XML_STYLESHEET_ATTR_ALTERNATE_NO).equals("yes");
    }

    public static int c(String str, String str2) {
        return f24466a.c(str, str2);
    }

    public static int d(String str, String str2) {
        return f24466a.d(str, str2);
    }

    public static int e(String str, String str2) {
        return f24466a.e(str, str2);
    }

    public static int b(String str, String str2) {
        return f24466a.b(str, str2);
    }

    public static void c(Context context, String str) {
        f24466a.c(context, str);
    }

    public static void a(boolean z2) {
        b();
        f24468c = z2;
    }

    public static int b(String str, String str2, Throwable th) {
        return f24466a.b(str, str2, th);
    }

    public static void b(Context context, String str) {
        f24466a.b(context, str);
    }

    public static int a(String str, String str2) {
        return f24466a.a(str, str2);
    }

    public static int a(String str, Throwable th) {
        return f24466a.a(str, th);
    }

    public static int a(String str, String str2, Throwable th) {
        return f24466a.a(str, str2, th);
    }

    public static String a(Throwable th) {
        return f24466a.a(th);
    }

    public static void a(Context context, String str) {
        f24466a.a(context, str);
    }
}
