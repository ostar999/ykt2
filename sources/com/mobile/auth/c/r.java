package com.mobile.auth.c;

import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class r {

    /* renamed from: a, reason: collision with root package name */
    private static ThreadPoolExecutor f9674a;

    /* renamed from: b, reason: collision with root package name */
    private static r f9675b;

    public static abstract class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private boolean f9676a = false;

        public void a(boolean z2) {
            try {
                this.f9676a = z2;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }

        public boolean a() {
            try {
                return this.f9676a;
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                    return false;
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                    return false;
                }
            }
        }
    }

    private r() {
        b();
    }

    public static r a() {
        try {
            if (f9675b == null) {
                f9675b = new r();
            }
            return f9675b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private void b() {
        try {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 3L, TimeUnit.SECONDS, new LinkedBlockingQueue());
            f9674a = threadPoolExecutor;
            threadPoolExecutor.allowCoreThreadTimeOut(true);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(Runnable runnable) {
        try {
            f9674a.execute(runnable);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public Future b(Runnable runnable) {
        try {
            return f9674a.submit(runnable);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }
}
