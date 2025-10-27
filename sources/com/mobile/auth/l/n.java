package com.mobile.auth.l;

import android.content.Context;
import java.lang.Thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    private static final ExecutorService f10433a = new ThreadPoolExecutor(0, 30, 60, TimeUnit.SECONDS, new SynchronousQueue());

    public static abstract class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private final Thread.UncaughtExceptionHandler f10434a;

        public a() {
            this.f10434a = new Thread.UncaughtExceptionHandler() { // from class: com.mobile.auth.l.n.a.1
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread thread, Throwable th) {
                    th.printStackTrace();
                }
            };
        }

        public a(final Context context, final com.cmic.sso.sdk.a aVar) {
            this.f10434a = new Thread.UncaughtExceptionHandler() { // from class: com.mobile.auth.l.n.a.2
                @Override // java.lang.Thread.UncaughtExceptionHandler
                public void uncaughtException(Thread thread, Throwable th) {
                    aVar.a().f6391a.add(th);
                    com.mobile.auth.e.e.b(context).a("200025", "发生未知错误", aVar, null);
                }
            };
        }

        public abstract void a();

        @Override // java.lang.Runnable
        public void run() {
            Thread.currentThread().setUncaughtExceptionHandler(this.f10434a);
            a();
            Thread.currentThread().setUncaughtExceptionHandler(null);
        }
    }

    public static void a(a aVar) {
        try {
            f10433a.execute(aVar);
        } catch (Exception e2) {
            aVar.f10434a.uncaughtException(Thread.currentThread(), e2);
        }
    }
}
