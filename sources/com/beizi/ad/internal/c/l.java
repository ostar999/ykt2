package com.beizi.ad.internal.c;

import com.beizi.ad.internal.utilities.HaoboLog;
import java.lang.Thread;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes2.dex */
class l {

    /* renamed from: a, reason: collision with root package name */
    private final o f4109a;

    /* renamed from: b, reason: collision with root package name */
    private final com.beizi.ad.internal.c.a f4110b;

    /* renamed from: f, reason: collision with root package name */
    private volatile Thread f4114f;

    /* renamed from: g, reason: collision with root package name */
    private volatile boolean f4115g;

    /* renamed from: c, reason: collision with root package name */
    private final Object f4111c = new Object();

    /* renamed from: d, reason: collision with root package name */
    private final Object f4112d = new Object();

    /* renamed from: h, reason: collision with root package name */
    private volatile int f4116h = -1;

    /* renamed from: e, reason: collision with root package name */
    private final AtomicInteger f4113e = new AtomicInteger();

    public class a implements Runnable {
        private a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            l.this.e();
        }
    }

    public l(o oVar, com.beizi.ad.internal.c.a aVar) {
        this.f4109a = (o) k.a(oVar);
        this.f4110b = (com.beizi.ad.internal.c.a) k.a(aVar);
    }

    private void b() throws m {
        int i2 = this.f4113e.get();
        if (i2 < 1) {
            return;
        }
        this.f4113e.set(0);
        throw new m("Error reading source " + i2 + " times");
    }

    private synchronized void c() throws m {
        boolean z2 = (this.f4114f == null || this.f4114f.getState() == Thread.State.TERMINATED) ? false : true;
        if (!this.f4115g && !this.f4110b.d() && !z2) {
            this.f4114f = new Thread(new a(), "Source reader for " + this.f4109a);
            this.f4114f.start();
        }
    }

    private void d() throws m {
        synchronized (this.f4111c) {
            try {
                try {
                    this.f4111c.wait(1000L);
                } catch (InterruptedException e2) {
                    throw new m("Waiting source data is interrupted!", e2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() {
        int iA;
        Throwable th;
        int iA2 = 0;
        try {
            iA2 = this.f4110b.a();
            this.f4109a.a(iA2);
            iA = this.f4109a.a();
        } catch (Throwable th2) {
            iA = -1;
            th = th2;
        }
        try {
            byte[] bArr = new byte[8192];
            while (true) {
                int iA3 = this.f4109a.a(bArr);
                if (iA3 == -1) {
                    g();
                    f();
                    break;
                }
                synchronized (this.f4112d) {
                    if (h()) {
                        return;
                    } else {
                        this.f4110b.a(bArr, iA3);
                    }
                }
                iA2 += iA3;
                b(iA2, iA);
            }
        } catch (Throwable th3) {
            th = th3;
            try {
                this.f4113e.incrementAndGet();
                a(th);
            } finally {
                i();
                b(iA2, iA);
            }
        }
    }

    private void f() {
        this.f4116h = 100;
        a(this.f4116h);
    }

    private void g() throws m {
        synchronized (this.f4112d) {
            if (!h() && this.f4110b.a() == this.f4109a.a()) {
                this.f4110b.c();
            }
        }
    }

    private boolean h() {
        return Thread.currentThread().isInterrupted() || this.f4115g;
    }

    private void i() {
        try {
            this.f4109a.b();
        } catch (m e2) {
            a(new m("Error closing source " + this.f4109a, e2));
        }
    }

    public void a(int i2) {
    }

    public int a(byte[] bArr, long j2, int i2) throws m {
        n.a(bArr, j2, i2);
        while (!this.f4110b.d() && this.f4110b.a() < i2 + j2 && !this.f4115g) {
            c();
            d();
            b();
        }
        int iA = this.f4110b.a(bArr, j2, i2);
        if (this.f4110b.d() && this.f4116h != 100) {
            this.f4116h = 100;
            a(100);
        }
        return iA;
    }

    private void b(long j2, long j3) {
        a(j2, j3);
        synchronized (this.f4111c) {
            this.f4111c.notifyAll();
        }
    }

    public void a() {
        synchronized (this.f4112d) {
            HaoboLog.d(HaoboLog.proxyCacheLogTag, "Shutdown proxy for " + this.f4109a);
            try {
                this.f4115g = true;
                if (this.f4114f != null) {
                    this.f4114f.interrupt();
                }
                this.f4110b.b();
            } catch (m e2) {
                a(e2);
            }
        }
    }

    public void a(long j2, long j3) {
        int i2 = (j3 > 0L ? 1 : (j3 == 0L ? 0 : -1)) == 0 ? 100 : (int) ((j2 * 100) / j3);
        boolean z2 = i2 != this.f4116h;
        if ((j3 >= 0) && z2) {
            a(i2);
        }
        this.f4116h = i2;
    }

    public final void a(Throwable th) {
        if (th instanceof i) {
            HaoboLog.d(HaoboLog.proxyCacheLogTag, "ProxyCache is interrupted");
        } else {
            HaoboLog.e(HaoboLog.proxyCacheLogTag, "ProxyCache error", th);
        }
    }
}
