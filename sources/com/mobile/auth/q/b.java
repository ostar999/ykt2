package com.mobile.auth.q;

import android.content.Context;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static volatile JSONObject f10520a;

    /* renamed from: b, reason: collision with root package name */
    private static volatile long f10521b;

    public static synchronized JSONObject a(Context context) {
        try {
            if (System.currentTimeMillis() - f10521b > 1000 || f10520a == null) {
                f10520a = com.mobile.auth.e.a.a(context).c(context);
                f10521b = System.currentTimeMillis();
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
        return f10520a;
    }
}
