package com.meizu.cloud.pushsdk.c.b.a;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static ExecutorService f9290a = null;

    /* renamed from: b, reason: collision with root package name */
    private static int f9291b = 2;

    public static ExecutorService a() {
        synchronized (b.class) {
            if (f9290a == null) {
                f9290a = Executors.newScheduledThreadPool(f9291b);
            }
        }
        return f9290a;
    }

    public static Future a(Callable callable) {
        return a().submit(callable);
    }

    public static void a(int i2) {
        f9291b = i2;
    }

    public static void a(Runnable runnable) {
        a().execute(runnable);
    }
}
