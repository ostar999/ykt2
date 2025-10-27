package com.tencent.bugly.proguard;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

/* loaded from: classes6.dex */
public final class bg extends Thread {

    /* renamed from: a, reason: collision with root package name */
    public bf f17694a;

    /* renamed from: g, reason: collision with root package name */
    private a f17700g;

    /* renamed from: c, reason: collision with root package name */
    private boolean f17696c = false;

    /* renamed from: d, reason: collision with root package name */
    private boolean f17697d = true;

    /* renamed from: e, reason: collision with root package name */
    private boolean f17698e = false;

    /* renamed from: f, reason: collision with root package name */
    private int f17699f = 1;

    /* renamed from: b, reason: collision with root package name */
    public boolean f17695b = true;

    public interface a {
    }

    public final boolean a() {
        this.f17696c = true;
        if (!isAlive()) {
            return false;
        }
        try {
            interrupt();
        } catch (Exception e2) {
            al.b(e2);
        }
        al.d("MainHandlerChecker is reset to null.", new Object[0]);
        this.f17694a = null;
        return true;
    }

    public final boolean b() {
        Handler handler = new Handler(Looper.getMainLooper());
        bf bfVar = this.f17694a;
        if (bfVar != null) {
            bfVar.f17688b = 5000L;
        } else {
            this.f17694a = new bf(handler, handler.getLooper().getThread().getName());
        }
        if (isAlive()) {
            return false;
        }
        try {
            start();
            return true;
        } catch (Exception e2) {
            al.b(e2);
            return false;
        }
    }

    public final synchronized void c() {
        this.f17697d = false;
        al.c("Record stack trace is disabled.", new Object[0]);
    }

    public final synchronized void d() {
        this.f17698e = true;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public final void run() {
        bf bfVar;
        boolean z2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        while (!this.f17696c) {
            try {
                bfVar = this.f17694a;
                z2 = false;
            } catch (Exception e2) {
                al.b(e2);
            } catch (OutOfMemoryError e3) {
                al.b(e3);
            }
            if (bfVar == null) {
                al.c("Main handler checker is null. Stop thread monitor.", new Object[0]);
                return;
            }
            if (bfVar.f17689c) {
                bfVar.f17689c = false;
                bfVar.f17690d = SystemClock.uptimeMillis();
                bfVar.f17687a.post(bfVar);
            }
            a(bfVar);
            if (this.f17695b && this.f17697d) {
                long jB = bfVar.b();
                if (jB > 1510 && jB < 199990) {
                    boolean z3 = true;
                    if (jB <= 5010) {
                        this.f17699f = 1;
                        al.c("timeSinceMsgSent in [2s, 5s], record stack", new Object[0]);
                    } else {
                        int i2 = this.f17699f + 1;
                        this.f17699f = i2;
                        if ((i2 & (i2 - 1)) != 0) {
                            z3 = false;
                        }
                        if (z3) {
                            al.c("timeSinceMsgSent in (5s, 200s), should record stack:true", new Object[0]);
                        }
                    }
                    z2 = z3;
                }
            }
            if (z2) {
                bfVar.d();
            }
            if (this.f17700g != null && this.f17697d) {
                bfVar.a();
                bfVar.b();
            }
            ap.b(500 - ((System.currentTimeMillis() - jCurrentTimeMillis) % 500));
        }
    }

    private synchronized void a(bf bfVar) {
        if (this.f17697d) {
            return;
        }
        if (this.f17698e && !bfVar.a()) {
            al.c("Restart getting main stack trace.", new Object[0]);
            this.f17697d = true;
            this.f17698e = false;
        }
    }
}
