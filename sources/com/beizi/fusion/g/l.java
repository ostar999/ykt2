package com.beizi.fusion.g;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    private static final int f5222a;

    /* renamed from: b, reason: collision with root package name */
    private static final int f5223b;

    /* renamed from: c, reason: collision with root package name */
    private static final LinkedBlockingQueue<Runnable> f5224c;

    /* renamed from: d, reason: collision with root package name */
    private static final LinkedBlockingQueue<Runnable> f5225d;

    /* renamed from: e, reason: collision with root package name */
    private static final LinkedBlockingQueue<Runnable> f5226e;

    /* renamed from: f, reason: collision with root package name */
    private static final ArrayBlockingQueue<Runnable> f5227f;

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        f5222a = iAvailableProcessors;
        f5223b = Math.max((iAvailableProcessors / 2) + 1, 4);
        f5224c = new LinkedBlockingQueue<>();
        f5225d = new LinkedBlockingQueue<>();
        f5226e = new LinkedBlockingQueue<>();
        f5227f = new ArrayBlockingQueue<>(200);
    }

    public static ThreadPoolExecutor a() {
        return new ThreadPoolExecutor(2, f5223b, 5L, TimeUnit.SECONDS, f5224c, new k(5, "afAd-"), e());
    }

    public static ThreadPoolExecutor b() {
        return new ThreadPoolExecutor(2, f5223b, 5L, TimeUnit.SECONDS, f5225d, new k(5, "afHb-"), e());
    }

    public static ThreadPoolExecutor c() {
        return new ThreadPoolExecutor(2, 20, 20L, TimeUnit.SECONDS, f5227f, new k(5, "afFu-"), e());
    }

    public static ThreadPoolExecutor d() {
        return new ThreadPoolExecutor(2, f5223b, 20L, TimeUnit.SECONDS, f5226e, new k(5, "afIt-"), e());
    }

    public static RejectedExecutionHandler e() {
        return new RejectedExecutionHandler() { // from class: com.beizi.fusion.g.l.1
            @Override // java.util.concurrent.RejectedExecutionHandler
            public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            }
        };
    }
}
