package com.mobile.auth.y;

import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    public static String f10588a = "";

    /* renamed from: b, reason: collision with root package name */
    private static String f10589b = "";

    /* renamed from: c, reason: collision with root package name */
    private static String f10590c = "";

    /* renamed from: d, reason: collision with root package name */
    private static String f10591d = "";

    /* renamed from: e, reason: collision with root package name */
    private static long f10592e = 0;

    /* renamed from: f, reason: collision with root package name */
    private static long f10593f = 0;

    /* renamed from: g, reason: collision with root package name */
    private static String f10594g = "CU";

    public static void a(long j2) {
        try {
            f10593f = j2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void a(String str) {
        try {
            f10594g = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void b(long j2) {
        try {
            f10592e = j2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void b(String str) {
        try {
            f10590c = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void c(String str) {
        try {
            f10589b = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void d(String str) {
        try {
            f10591d = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static Boolean e(String str) {
        if (str != null) {
            try {
                if (str.length() != 0 && str.trim().length() != 0 && !"null".equals(str) && !str.equals("")) {
                    return Boolean.TRUE;
                }
            } catch (Throwable th) {
                ExceptionProcessor.processException(th);
                return null;
            }
        }
        return Boolean.FALSE;
    }
}
