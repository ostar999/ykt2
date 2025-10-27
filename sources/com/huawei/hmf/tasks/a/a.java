package com.huawei.hmf.tasks.a;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    static final int f7351a;

    /* renamed from: b, reason: collision with root package name */
    static final int f7352b;

    /* renamed from: c, reason: collision with root package name */
    private static final a f7353c = new a();

    /* renamed from: e, reason: collision with root package name */
    private static final int f7354e;

    /* renamed from: d, reason: collision with root package name */
    private final Executor f7355d = new ExecutorC0177a(0);

    /* renamed from: com.huawei.hmf.tasks.a.a$a, reason: collision with other inner class name */
    public static class ExecutorC0177a implements Executor {
        private ExecutorC0177a() {
        }

        public /* synthetic */ ExecutorC0177a(byte b3) {
            this();
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    static {
        int iAvailableProcessors = Runtime.getRuntime().availableProcessors();
        f7354e = iAvailableProcessors;
        f7351a = iAvailableProcessors + 1;
        f7352b = (iAvailableProcessors * 2) + 1;
    }

    public static ExecutorService a() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(f7351a, f7352b, 1L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    public static Executor b() {
        return f7353c.f7355d;
    }
}
