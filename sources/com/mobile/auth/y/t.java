package com.mobile.auth.y;

import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public final class t {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f10636a = false;

    /* renamed from: b, reason: collision with root package name */
    private static int f10637b = 2;

    /* renamed from: c, reason: collision with root package name */
    private static long f10638c;

    /* renamed from: d, reason: collision with root package name */
    private static int f10639d;

    public static void a() {
        try {
            f10639d = 0;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void a(String str) {
        try {
            b("\n" + str + "\n");
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void a(boolean z2) {
        try {
            f10636a = z2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void b() {
    }

    public static void b(String str) {
        try {
            f10639d++;
            System.currentTimeMillis();
            System.currentTimeMillis();
            f10638c = System.currentTimeMillis();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void c(String str) {
        try {
            if (f10636a) {
                Log.d("UniAccount", p.b() + " " + str);
                b(str);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void d(String str) {
        try {
            if (f10636a) {
                Log.e("UniAccount", p.b() + " " + str);
                b(str);
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void e(String str) {
        try {
            Log.e("UniAccount", p.b() + " " + str);
            b(str);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
