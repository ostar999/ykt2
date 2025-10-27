package com.umeng.analytics.pro;

import android.content.Context;
import android.os.Looper;

/* loaded from: classes6.dex */
public class aa {

    /* renamed from: a, reason: collision with root package name */
    private static z f22409a = null;

    /* renamed from: b, reason: collision with root package name */
    private static boolean f22410b = false;

    public static synchronized String a(Context context) {
        try {
            if (context == null) {
                throw new RuntimeException("Context is null");
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new IllegalStateException("Cannot be called from the main thread");
            }
            b(context);
            z zVar = f22409a;
            if (zVar != null) {
                try {
                    return zVar.a(context);
                } catch (Exception unused) {
                }
            }
            return null;
        } catch (Throwable th) {
            throw th;
        }
    }

    private static void b(Context context) {
        if (f22409a != null || f22410b) {
            return;
        }
        synchronized (aa.class) {
            if (f22409a == null && !f22410b) {
                f22409a = ac.a(context);
                f22410b = true;
            }
        }
    }
}
