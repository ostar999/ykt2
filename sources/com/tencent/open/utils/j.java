package com.tencent.open.utils;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public final class j {

    /* renamed from: c, reason: collision with root package name */
    private static Handler f20682c;

    /* renamed from: d, reason: collision with root package name */
    private static HandlerThread f20683d;

    /* renamed from: b, reason: collision with root package name */
    private static Object f20681b = new Object();

    /* renamed from: a, reason: collision with root package name */
    public static final Executor f20680a = c();

    public static class a implements Executor {

        /* renamed from: a, reason: collision with root package name */
        final Queue<Runnable> f20684a;

        /* renamed from: b, reason: collision with root package name */
        Runnable f20685b;

        private a() {
            this.f20684a = new LinkedList();
        }

        public synchronized void a() {
            Runnable runnablePoll = this.f20684a.poll();
            this.f20685b = runnablePoll;
            if (runnablePoll != null) {
                j.f20680a.execute(runnablePoll);
            }
        }

        @Override // java.util.concurrent.Executor
        public synchronized void execute(final Runnable runnable) {
            this.f20684a.offer(new Runnable() { // from class: com.tencent.open.utils.j.a.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        a.this.a();
                    }
                }
            });
            if (this.f20685b == null) {
                a();
            }
        }
    }

    public static void a(Runnable runnable) {
        try {
            f20680a.execute(runnable);
        } catch (RejectedExecutionException unused) {
        }
    }

    public static void b(Runnable runnable) {
        a().post(runnable);
    }

    private static Executor c() {
        return new ThreadPoolExecutor(0, 3, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue());
    }

    public static Handler a() {
        if (f20682c == null) {
            synchronized (j.class) {
                HandlerThread handlerThread = new HandlerThread("SDK_SUB");
                f20683d = handlerThread;
                handlerThread.start();
                f20682c = new Handler(f20683d.getLooper());
            }
        }
        return f20682c;
    }

    public static Executor b() {
        return new a();
    }
}
