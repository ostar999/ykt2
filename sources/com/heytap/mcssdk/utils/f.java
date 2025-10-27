package com.heytap.mcssdk.utils;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static final ExecutorService f7234a = Executors.newSingleThreadExecutor();

    /* renamed from: b, reason: collision with root package name */
    private static Handler f7235b = new Handler(Looper.getMainLooper());

    public static void a(Runnable runnable) {
        f7234a.execute(runnable);
    }

    public static void b(Runnable runnable) {
        f7235b.post(runnable);
    }
}
