package com.huawei.hms.hatool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes4.dex */
public class o0 {

    /* renamed from: b, reason: collision with root package name */
    public static o0 f7834b;

    /* renamed from: c, reason: collision with root package name */
    public static o0 f7835c;

    /* renamed from: d, reason: collision with root package name */
    public static o0 f7836d;

    /* renamed from: a, reason: collision with root package name */
    public ThreadPoolExecutor f7837a = new ThreadPoolExecutor(0, 1, 60000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(5000), new b());

    public static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        public Runnable f7838a;

        public a(Runnable runnable) {
            this.f7838a = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            Runnable runnable = this.f7838a;
            if (runnable != null) {
                try {
                    runnable.run();
                } catch (Exception unused) {
                    y.e("hmsSdk", "InnerTask : Exception has happened,From internal operations!");
                }
            }
        }
    }

    public static class b implements ThreadFactory {

        /* renamed from: d, reason: collision with root package name */
        public static final AtomicInteger f7839d = new AtomicInteger(1);

        /* renamed from: a, reason: collision with root package name */
        public final ThreadGroup f7840a;

        /* renamed from: b, reason: collision with root package name */
        public final AtomicInteger f7841b = new AtomicInteger(1);

        /* renamed from: c, reason: collision with root package name */
        public final String f7842c;

        public b() {
            SecurityManager securityManager = System.getSecurityManager();
            this.f7840a = securityManager != null ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.f7842c = "FormalHASDK-base-" + f7839d.getAndIncrement();
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            return new Thread(this.f7840a, runnable, this.f7842c + this.f7841b.getAndIncrement(), 0L);
        }
    }

    static {
        new o0();
        new o0();
        f7834b = new o0();
        f7835c = new o0();
        f7836d = new o0();
    }

    public static o0 a() {
        return f7836d;
    }

    public static o0 b() {
        return f7835c;
    }

    public static o0 c() {
        return f7834b;
    }

    public void a(n0 n0Var) {
        try {
            this.f7837a.execute(new a(n0Var));
        } catch (RejectedExecutionException unused) {
            y.e("hmsSdk", "addToQueue() Exception has happened!Form rejected execution");
        }
    }
}
