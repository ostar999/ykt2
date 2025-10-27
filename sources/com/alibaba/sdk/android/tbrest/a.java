package com.alibaba.sdk.android.tbrest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static ScheduledExecutorService f2872a = null;

    /* renamed from: a, reason: collision with other field name */
    public static final AtomicInteger f62a = new AtomicInteger();

    /* renamed from: e, reason: collision with root package name */
    public static int f2873e = 1;

    /* renamed from: a, reason: collision with other field name */
    public Integer f63a = 2;

    /* renamed from: com.alibaba.sdk.android.tbrest.a$a, reason: collision with other inner class name */
    public static class ThreadFactoryC0023a implements ThreadFactory {
        private int priority;

        public ThreadFactoryC0023a(int i2) {
            this.priority = i2;
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "RestSend:" + a.f62a.getAndIncrement());
            thread.setPriority(this.priority);
            return thread;
        }
    }

    public synchronized void a(Runnable runnable) {
        try {
            if (f2872a == null) {
                f2872a = Executors.newScheduledThreadPool(this.f63a.intValue(), new ThreadFactoryC0023a(f2873e));
            }
            f2872a.submit(runnable);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
