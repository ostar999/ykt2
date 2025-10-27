package com.mobile.auth.gatewayauth.utils;

import android.text.TextUtils;
import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.nirvana.tools.core.ExecutorManager;

/* loaded from: classes4.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f10308a = false;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f10309b = true;

    public static void a(String str) {
        try {
            if (!f10308a || TextUtils.isEmpty(str)) {
                return;
            }
            Log.d("AuthSDK", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void a(Throwable th) {
        try {
            if (!f10308a || th == null) {
                return;
            }
            Log.e("AuthSDK", ExecutorManager.getErrorInfoFromException(th));
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
            }
        }
    }

    public static void a(boolean z2) {
        try {
            f10308a = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static boolean a() {
        try {
            return f10308a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static void b(String str) {
        try {
            if (!f10308a || TextUtils.isEmpty(str)) {
                return;
            }
            Log.w("AuthSDK", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void b(boolean z2) {
        try {
            f10309b = z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static boolean b() {
        try {
            return f10309b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static void c(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            Log.e("AuthSDK", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static void d(String str) {
        try {
            if (!f10308a || TextUtils.isEmpty(str)) {
                return;
            }
            Log.i("AuthSDK", str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }
}
