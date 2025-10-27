package com.beizi.ad.a.a;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static final int f3699a;

    /* renamed from: b, reason: collision with root package name */
    private static final int f3700b;

    /* renamed from: c, reason: collision with root package name */
    private static final LinkedBlockingQueue<Runnable> f3701c;

    /* renamed from: d, reason: collision with root package name */
    private static final LinkedBlockingQueue<Runnable> f3702d;

    /* renamed from: e, reason: collision with root package name */
    private static final ArrayBlockingQueue<Runnable> f3703e;

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        f3699a = iAvailableProcessors;
        f3700b = Math.max((iAvailableProcessors / 2) + 1, 4);
        f3701c = new LinkedBlockingQueue<>();
        f3702d = new LinkedBlockingQueue<>();
        f3703e = new ArrayBlockingQueue<>(50);
    }

    public static ThreadPoolExecutor a() {
        return new ThreadPoolExecutor(2, f3700b, 5L, TimeUnit.SECONDS, f3701c, new d(5, "BeiZi-adsdk-adrequest-thread-"), d());
    }

    public static ThreadPoolExecutor b() {
        return new ThreadPoolExecutor(0, 2, 5L, TimeUnit.SECONDS, f3702d, new d(5, "BeiZi-adsdk-heartbeat-thread-"), d());
    }

    public static ThreadPoolExecutor c() {
        return new ThreadPoolExecutor(2, 6, 20L, TimeUnit.SECONDS, f3703e, new d(5, "BeiZi-adsdk-file-log-upload-thread-"), d());
    }

    public static RejectedExecutionHandler d() {
        return new RejectedExecutionHandler() { // from class: com.beizi.ad.a.a.e.1
            @Override // java.util.concurrent.RejectedExecutionHandler
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            }
        };
    }
}
