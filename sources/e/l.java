package e;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import core.interfaces.DataProvider;
import core.interfaces.RtcNotification;
import h.f;
import j.d;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.wrtca.api.PeerConnectionFactory;

/* loaded from: classes8.dex */
public abstract class l implements f.d, f.c {

    /* renamed from: m, reason: collision with root package name */
    public static final String f26792m = "VirtualEngine";

    /* renamed from: n, reason: collision with root package name */
    public static l f26793n;

    /* renamed from: b, reason: collision with root package name */
    public g.b f26795b;

    /* renamed from: c, reason: collision with root package name */
    public g.a f26796c;

    /* renamed from: d, reason: collision with root package name */
    public g.d f26797d;

    /* renamed from: e, reason: collision with root package name */
    public h.f f26798e;

    /* renamed from: f, reason: collision with root package name */
    public Handler f26799f;

    /* renamed from: i, reason: collision with root package name */
    public ExecutorService f26802i;

    /* renamed from: j, reason: collision with root package name */
    public HandlerThread f26803j;

    /* renamed from: l, reason: collision with root package name */
    public PeerConnectionFactory.Options f26805l;

    /* renamed from: a, reason: collision with root package name */
    public c f26794a = null;

    /* renamed from: k, reason: collision with root package name */
    public Object f26804k = new Object();

    /* renamed from: g, reason: collision with root package name */
    public Map<String, e> f26800g = new HashMap();

    /* renamed from: h, reason: collision with root package name */
    public Map<String, g> f26801h = new HashMap();

    public static abstract class a {

        /* renamed from: a, reason: collision with root package name */
        public PeerConnectionFactory.Options f26806a;

        /* renamed from: b, reason: collision with root package name */
        public c f26807b;

        public abstract l a();

        public void a(PeerConnectionFactory.Options options) {
            this.f26806a = options;
        }

        public void a(c cVar) {
            this.f26807b = cVar;
        }
    }

    public l(PeerConnectionFactory.Options options) {
        HandlerThread handlerThread = new HandlerThread(f26792m);
        this.f26803j = handlerThread;
        handlerThread.start();
        this.f26802i = Executors.newCachedThreadPool();
        this.f26805l = options;
        o();
        p();
    }

    public static void c() {
        l lVar = f26793n;
        if (lVar != null) {
            lVar.s();
            f26793n = null;
        }
    }

    public static l f() {
        return f26793n;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void q() {
        this.f26794a.onFirstLocalVideoFrame();
    }

    public void a(Intent intent) {
        j.d.a(intent);
    }

    public Map<String, e> d() {
        return this.f26800g;
    }

    public Handler e() {
        return this.f26799f;
    }

    public g.b g() {
        return this.f26795b;
    }

    public c h() {
        return this.f26794a;
    }

    public g.a i() {
        return this.f26796c;
    }

    public g.d j() {
        return this.f26797d;
    }

    public Map<String, g> k() {
        return this.f26801h;
    }

    public ExecutorService l() {
        return this.f26802i;
    }

    public h.f m() {
        return this.f26798e;
    }

    public abstract void n();

    public abstract void o();

    public final void p() {
        synchronized (this.f26804k) {
            c.h.a(f26792m, "initWithLock start");
            j.d.d().b(this.f26805l);
            j.d.d().a(new d.e() { // from class: w1.b
                @Override // j.d.e
                public final void onFirstLocalVideoFrame() {
                    this.f28286a.q();
                }
            });
            n();
            this.f26804k.notifyAll();
            c.h.a(f26792m, "initWithLock finish");
        }
    }

    public abstract void r();

    public final void s() {
        try {
            this.f26799f.postDelayed(new Runnable() { // from class: w1.a
                @Override // java.lang.Runnable
                public final void run() {
                    this.f28285c.t();
                }
            }, 50L);
            j.d.d().a((d.e) null);
            synchronized (this.f26804k) {
                c.h.a(f26792m, "destroy virtual lock wait");
                this.f26804k.wait();
                c.h.a(f26792m, "destroy virtual lock awaked");
            }
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
    }

    public final void t() {
        synchronized (this.f26804k) {
            c.h.a("DESTROY", "destroy virtual engine start ");
            h.f fVar = this.f26798e;
            if (fVar != null) {
                fVar.e();
                this.f26798e = null;
            }
            r();
            this.f26801h.clear();
            this.f26800g.clear();
            ExecutorService executorService = this.f26802i;
            if (executorService != null) {
                executorService.shutdown();
                this.f26802i = null;
            }
            this.f26804k.notifyAll();
            c.h.a("DESTROY", "destroy virtual engine end ");
        }
    }

    public void a(DataProvider dataProvider) {
        j.d.a(dataProvider);
    }

    public void a(c cVar) {
        this.f26794a = cVar;
    }

    public void a(h.f fVar) {
        this.f26798e = fVar;
    }

    public void a(RtcNotification rtcNotification) {
        j.d.a(rtcNotification);
    }
}
