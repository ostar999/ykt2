package com.alibaba.sdk.android.crashdefend.b;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private final ThreadFactory f2676a = new ThreadFactory() { // from class: com.alibaba.sdk.android.crashdefend.b.a.1
        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "safe_thread");
            thread.setDaemon(false);
            return thread;
        }
    };

    /* renamed from: b, reason: collision with root package name */
    private ExecutorService f2677b;

    public synchronized ExecutorService a() {
        if (this.f2677b == null) {
            this.f2677b = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 1L, TimeUnit.SECONDS, new SynchronousQueue(), this.f2676a);
        }
        return this.f2677b;
    }
}
