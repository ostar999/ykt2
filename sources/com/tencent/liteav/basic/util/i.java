package com.tencent.liteav.basic.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* loaded from: classes6.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private Handler f18745a;

    /* renamed from: b, reason: collision with root package name */
    private Looper f18746b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f18747c;

    /* renamed from: d, reason: collision with root package name */
    private Thread f18748d;

    public i(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        this.f18747c = true;
        handlerThread.start();
        this.f18746b = handlerThread.getLooper();
        this.f18745a = new Handler(this.f18746b);
        this.f18748d = handlerThread;
    }

    public void b(Runnable runnable) {
        this.f18745a.post(runnable);
    }

    public void finalize() throws Throwable {
        if (this.f18747c) {
            this.f18745a.getLooper().quit();
        }
        super.finalize();
    }

    public Handler a() {
        return this.f18745a;
    }

    public void a(final Runnable runnable) {
        final boolean[] zArr = new boolean[1];
        if (Thread.currentThread().equals(this.f18748d)) {
            runnable.run();
            return;
        }
        synchronized (this.f18745a) {
            zArr[0] = false;
            this.f18745a.post(new Runnable() { // from class: com.tencent.liteav.basic.util.i.1
                @Override // java.lang.Runnable
                public void run() {
                    runnable.run();
                    zArr[0] = true;
                    synchronized (i.this.f18745a) {
                        i.this.f18745a.notifyAll();
                    }
                }
            });
            while (!zArr[0]) {
                try {
                    this.f18745a.wait();
                } catch (Exception unused) {
                }
            }
        }
    }

    public void a(Runnable runnable, long j2) {
        this.f18745a.postDelayed(runnable, j2);
    }
}
