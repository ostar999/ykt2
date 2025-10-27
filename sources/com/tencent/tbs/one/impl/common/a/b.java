package com.tencent.tbs.one.impl.common.a;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public final class b {

    /* renamed from: a, reason: collision with root package name */
    public static final int f21961a;

    /* renamed from: b, reason: collision with root package name */
    public static final int f21962b;

    /* renamed from: c, reason: collision with root package name */
    public static final int f21963c;

    /* renamed from: d, reason: collision with root package name */
    public static final BlockingQueue<Runnable> f21964d;

    /* renamed from: e, reason: collision with root package name */
    public static final Executor f21965e;

    /* renamed from: f, reason: collision with root package name */
    public static final ThreadFactory f21966f;

    /* renamed from: g, reason: collision with root package name */
    public static final Object f21967g;

    /* renamed from: h, reason: collision with root package name */
    public static volatile Executor f21968h;

    /* renamed from: i, reason: collision with root package name */
    public static b f21969i;

    /* renamed from: com.tencent.tbs.one.impl.common.a.b$2, reason: invalid class name */
    public static class AnonymousClass2 implements Executor {

        /* renamed from: a, reason: collision with root package name */
        public final ArrayDeque<Runnable> f21971a = new ArrayDeque<>();

        /* renamed from: b, reason: collision with root package name */
        public Runnable f21972b;

        public final synchronized void a() {
            Runnable runnablePoll = this.f21971a.poll();
            this.f21972b = runnablePoll;
            if (runnablePoll != null) {
                b.f21965e.execute(this.f21972b);
            }
        }

        @Override // java.util.concurrent.Executor
        public final synchronized void execute(final Runnable runnable) {
            this.f21971a.offer(new Runnable() { // from class: com.tencent.tbs.one.impl.common.a.b.2.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        runnable.run();
                    } finally {
                        AnonymousClass2.this.a();
                    }
                }
            });
            if (this.f21972b == null) {
                a();
            }
        }
    }

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        f21961a = iAvailableProcessors;
        int iMax = Math.max(2, Math.min(iAvailableProcessors - 1, 4));
        f21962b = iMax;
        int i2 = (iAvailableProcessors * 2) + 1;
        f21963c = i2;
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(128);
        f21964d = linkedBlockingQueue;
        ThreadFactory threadFactory = new ThreadFactory() { // from class: com.tencent.tbs.one.impl.common.a.b.1

            /* renamed from: a, reason: collision with root package name */
            public final AtomicInteger f21970a = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                return new Thread(runnable, "StatisticExecutor #" + this.f21970a.getAndIncrement());
            }
        };
        f21966f = threadFactory;
        f21967g = new Object();
        f21968h = new AnonymousClass2();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(iMax, i2, 30L, TimeUnit.SECONDS, linkedBlockingQueue, threadFactory);
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        f21965e = threadPoolExecutor;
    }

    public static b a() {
        if (f21969i == null) {
            synchronized (f21967g) {
                if (f21969i == null) {
                    f21969i = new b();
                }
            }
        }
        return f21969i;
    }

    public static void a(Runnable runnable) {
        f21968h.execute(runnable);
    }
}
