package com.mobile.auth.gatewayauth;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private Runnable f9971a;

    /* renamed from: b, reason: collision with root package name */
    private AtomicBoolean f9972b;

    /* renamed from: c, reason: collision with root package name */
    private Handler f9973c;

    /* renamed from: d, reason: collision with root package name */
    private long f9974d;

    /* renamed from: e, reason: collision with root package name */
    private Runnable f9975e;

    /* renamed from: f, reason: collision with root package name */
    private volatile boolean f9976f;

    public c(long j2, Runnable runnable) {
        this(j2, runnable, Looper.getMainLooper());
    }

    public c(long j2, Runnable runnable, Looper looper) {
        this.f9972b = new AtomicBoolean(false);
        this.f9976f = false;
        this.f9974d = j2;
        this.f9975e = runnable;
        this.f9973c = new Handler(looper);
    }

    public static /* synthetic */ boolean a(c cVar) {
        try {
            return cVar.f9976f;
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

    public static /* synthetic */ AtomicBoolean b(c cVar) {
        try {
            return cVar.f9972b;
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

    public static /* synthetic */ Runnable c(c cVar) {
        try {
            return cVar.f9975e;
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

    public void a() {
        try {
            if (this.f9975e == null || this.f9974d <= 0) {
                return;
            }
            Runnable runnable = new Runnable() { // from class: com.mobile.auth.gatewayauth.c.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        synchronized (c.this) {
                            if (!c.a(c.this)) {
                                c.b(c.this).set(true);
                                c.c(c.this).run();
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            ExceptionProcessor.processException(th);
                        } catch (Throwable th2) {
                            ExceptionProcessor.processException(th2);
                        }
                    }
                }
            };
            this.f9971a = runnable;
            this.f9973c.postDelayed(runnable, this.f9974d);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public synchronized void b() {
        try {
            this.f9976f = true;
            Runnable runnable = this.f9971a;
            if (runnable != null) {
                this.f9973c.removeCallbacks(runnable);
            }
            this.f9975e = null;
            this.f9971a = null;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean c() {
        try {
            return this.f9972b.get();
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

    public synchronized boolean d() {
        boolean zC;
        try {
            zC = c();
            b();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
        return !zC;
    }
}
