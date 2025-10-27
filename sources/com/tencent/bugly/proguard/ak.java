package com.tencent.bugly.proguard;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public final class ak {

    /* renamed from: a, reason: collision with root package name */
    private static final AtomicInteger f17508a = new AtomicInteger(1);

    /* renamed from: b, reason: collision with root package name */
    private static ak f17509b;

    /* renamed from: c, reason: collision with root package name */
    private ScheduledExecutorService f17510c;

    public ak() {
        this.f17510c = null;
        ScheduledExecutorService scheduledExecutorServiceNewScheduledThreadPool = Executors.newScheduledThreadPool(3, new ThreadFactory() { // from class: com.tencent.bugly.proguard.ak.1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setName("BuglyThread-" + ak.f17508a.getAndIncrement());
                return thread;
            }
        });
        this.f17510c = scheduledExecutorServiceNewScheduledThreadPool;
        if (scheduledExecutorServiceNewScheduledThreadPool == null || scheduledExecutorServiceNewScheduledThreadPool.isShutdown()) {
            al.d("[AsyncTaskHandler] ScheduledExecutorService is not valiable!", new Object[0]);
        }
    }

    public static synchronized ak a() {
        if (f17509b == null) {
            f17509b = new ak();
        }
        return f17509b;
    }

    public final synchronized void b() {
        ScheduledExecutorService scheduledExecutorService = this.f17510c;
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            al.c("[AsyncTaskHandler] Close async handler.", new Object[0]);
            this.f17510c.shutdownNow();
        }
    }

    public final synchronized boolean c() {
        ScheduledExecutorService scheduledExecutorService = this.f17510c;
        if (scheduledExecutorService != null) {
            if (!scheduledExecutorService.isShutdown()) {
                return true;
            }
        }
        return false;
    }

    public final synchronized boolean a(Runnable runnable, long j2) {
        if (!c()) {
            al.d("[AsyncTaskHandler] Async handler was closed, should not post task.", new Object[0]);
            return false;
        }
        if (j2 <= 0) {
            j2 = 0;
        }
        al.c("[AsyncTaskHandler] Post a delay(time: %dms) task: %s", Long.valueOf(j2), runnable.getClass().getName());
        try {
            this.f17510c.schedule(runnable, j2, TimeUnit.MILLISECONDS);
            return true;
        } catch (Throwable th) {
            if (p.f17851c) {
                th.printStackTrace();
            }
            return false;
        }
    }

    public final synchronized boolean a(Runnable runnable) {
        if (!c()) {
            al.d("[AsyncTaskHandler] Async handler was closed, should not post task.", new Object[0]);
            return false;
        }
        if (runnable == null) {
            al.d("[AsyncTaskHandler] Task input is null.", new Object[0]);
            return false;
        }
        al.c("[AsyncTaskHandler] Post a normal task: %s", runnable.getClass().getName());
        try {
            this.f17510c.execute(runnable);
            return true;
        } catch (Throwable th) {
            if (p.f17851c) {
                th.printStackTrace();
            }
            return false;
        }
    }
}
