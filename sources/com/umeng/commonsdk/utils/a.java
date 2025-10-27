package com.umeng.commonsdk.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/* loaded from: classes6.dex */
public abstract class a {

    /* renamed from: e, reason: collision with root package name */
    private static final int f23509e = 1;

    /* renamed from: a, reason: collision with root package name */
    private final long f23510a;

    /* renamed from: b, reason: collision with root package name */
    private final long f23511b;

    /* renamed from: c, reason: collision with root package name */
    private long f23512c;

    /* renamed from: f, reason: collision with root package name */
    private HandlerThread f23514f;

    /* renamed from: g, reason: collision with root package name */
    private Handler f23515g;

    /* renamed from: d, reason: collision with root package name */
    private boolean f23513d = false;

    /* renamed from: h, reason: collision with root package name */
    private Handler.Callback f23516h = new Handler.Callback() { // from class: com.umeng.commonsdk.utils.a.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            synchronized (a.this) {
                if (a.this.f23513d) {
                    return true;
                }
                long jElapsedRealtime = a.this.f23512c - SystemClock.elapsedRealtime();
                if (jElapsedRealtime <= 0) {
                    a.this.c();
                    if (a.this.f23514f != null) {
                        a.this.f23514f.quit();
                    }
                } else if (jElapsedRealtime < a.this.f23511b) {
                    a.this.f23515g.sendMessageDelayed(a.this.f23515g.obtainMessage(1), jElapsedRealtime);
                } else {
                    long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                    a.this.a(jElapsedRealtime);
                    long jElapsedRealtime3 = (jElapsedRealtime2 + a.this.f23511b) - SystemClock.elapsedRealtime();
                    while (jElapsedRealtime3 < 0) {
                        jElapsedRealtime3 += a.this.f23511b;
                    }
                    a.this.f23515g.sendMessageDelayed(a.this.f23515g.obtainMessage(1), jElapsedRealtime3);
                }
                return false;
            }
        }
    };

    public a(long j2, long j3) {
        this.f23510a = j2;
        this.f23511b = j3;
        if (d()) {
            this.f23515g = new Handler(this.f23516h);
            return;
        }
        HandlerThread handlerThread = new HandlerThread("CountDownTimerThread");
        this.f23514f = handlerThread;
        handlerThread.start();
        this.f23515g = new Handler(this.f23514f.getLooper(), this.f23516h);
    }

    public abstract void a(long j2);

    public abstract void c();

    private boolean d() {
        return Looper.getMainLooper().getThread().equals(Thread.currentThread());
    }

    public final synchronized void a() {
        this.f23513d = true;
        this.f23515g.removeMessages(1);
    }

    public final synchronized a b() {
        this.f23513d = false;
        if (this.f23510a <= 0) {
            c();
            return this;
        }
        this.f23512c = SystemClock.elapsedRealtime() + this.f23510a;
        Handler handler = this.f23515g;
        handler.sendMessage(handler.obtainMessage(1));
        return this;
    }
}
