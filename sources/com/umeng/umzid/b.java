package com.umeng.umzid;

import android.util.Log;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static volatile ScheduledThreadPoolExecutor f23878a;

    /* renamed from: b, reason: collision with root package name */
    public static ThreadFactory f23879b = new a();

    public static class a implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        public AtomicInteger f23880a = new AtomicInteger(0);

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("ZIDThreadPoolExecutor" + this.f23880a.addAndGet(1));
            return thread;
        }
    }

    public static ScheduledThreadPoolExecutor a() {
        if (f23878a == null) {
            synchronized (b.class) {
                if (f23878a == null) {
                    f23878a = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 4, f23879b);
                }
            }
        }
        return f23878a;
    }

    public static void a(Runnable runnable) {
        try {
            a().execute(runnable);
        } catch (Throwable unused) {
            Log.e("com.umeng.umzid.b", "UmengThreadPoolExecutorFactory execute exception");
        }
    }
}
