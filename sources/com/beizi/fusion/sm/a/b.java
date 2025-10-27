package com.beizi.fusion.sm.a;

import android.app.Application;

/* loaded from: classes2.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    private static volatile boolean f5314a = false;

    /* renamed from: b, reason: collision with root package name */
    private static volatile String f5315b;

    /* renamed from: c, reason: collision with root package name */
    private static volatile String f5316c;

    private b() {
    }

    public static void a(Application application) {
        if (f5314a) {
            return;
        }
        synchronized (b.class) {
            if (!f5314a) {
                a.a(application);
                f5314a = true;
            }
        }
    }
}
