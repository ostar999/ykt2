package com.mobile.auth.y;

import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f10587a = false;

    public static void a() {
    }

    public static void a(String str) {
        try {
            Log.e("uniaccount", "6.1.3 ".concat(String.valueOf(str)));
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void a(boolean z2) {
        try {
            f10587a = z2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
