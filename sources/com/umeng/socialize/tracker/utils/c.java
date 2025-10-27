package com.umeng.socialize.tracker.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final int f23850a = 1;

    /* renamed from: b, reason: collision with root package name */
    private static ExecutorService f23851b = Executors.newFixedThreadPool(1);

    public static void a(Runnable runnable) {
        if (runnable != null) {
            f23851b.execute(runnable);
        }
    }
}
