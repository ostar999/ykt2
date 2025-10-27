package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import com.unity3d.player.p;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes6.dex */
final class q {

    /* renamed from: a, reason: collision with root package name */
    private UnityPlayer f24203a;

    /* renamed from: c, reason: collision with root package name */
    private a f24205c;

    /* renamed from: b, reason: collision with root package name */
    private Context f24204b = null;

    /* renamed from: d, reason: collision with root package name */
    private final Semaphore f24206d = new Semaphore(0);

    /* renamed from: e, reason: collision with root package name */
    private final Lock f24207e = new ReentrantLock();

    /* renamed from: f, reason: collision with root package name */
    private p f24208f = null;

    /* renamed from: g, reason: collision with root package name */
    private int f24209g = 2;

    /* renamed from: h, reason: collision with root package name */
    private boolean f24210h = false;

    /* renamed from: i, reason: collision with root package name */
    private boolean f24211i = false;

    /* renamed from: com.unity3d.player.q$1, reason: invalid class name */
    public class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f24212a;

        /* renamed from: b, reason: collision with root package name */
        final /* synthetic */ int f24213b;

        /* renamed from: c, reason: collision with root package name */
        final /* synthetic */ int f24214c;

        /* renamed from: d, reason: collision with root package name */
        final /* synthetic */ int f24215d;

        /* renamed from: e, reason: collision with root package name */
        final /* synthetic */ boolean f24216e;

        /* renamed from: f, reason: collision with root package name */
        final /* synthetic */ long f24217f;

        /* renamed from: g, reason: collision with root package name */
        final /* synthetic */ long f24218g;

        public AnonymousClass1(String str, int i2, int i3, int i4, boolean z2, long j2, long j3) {
            this.f24212a = str;
            this.f24213b = i2;
            this.f24214c = i3;
            this.f24215d = i4;
            this.f24216e = z2;
            this.f24217f = j2;
            this.f24218g = j3;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (q.this.f24208f != null) {
                f.Log(5, "Video already playing");
                q.this.f24209g = 2;
                q.this.f24206d.release();
            } else {
                q.this.f24208f = new p(q.this.f24204b, this.f24212a, this.f24213b, this.f24214c, this.f24215d, this.f24216e, this.f24217f, this.f24218g, new p.a() { // from class: com.unity3d.player.q.1.1
                    @Override // com.unity3d.player.p.a
                    public final void a(int i2) {
                        q.this.f24207e.lock();
                        q.this.f24209g = i2;
                        if (i2 == 3 && q.this.f24211i) {
                            q.this.runOnUiThread(new Runnable() { // from class: com.unity3d.player.q.1.1.1
                                @Override // java.lang.Runnable
                                public final void run() throws IllegalStateException {
                                    q.this.d();
                                    q.this.f24203a.resume();
                                }
                            });
                        }
                        if (i2 != 0) {
                            q.this.f24206d.release();
                        }
                        q.this.f24207e.unlock();
                    }
                });
                if (q.this.f24208f != null) {
                    q.this.f24203a.addView(q.this.f24208f);
                }
            }
        }
    }

    public interface a {
        void a();
    }

    public q(UnityPlayer unityPlayer) {
        this.f24203a = null;
        this.f24203a = unityPlayer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() throws IllegalStateException {
        p pVar = this.f24208f;
        if (pVar != null) {
            this.f24203a.removeViewFromPlayer(pVar);
            this.f24211i = false;
            this.f24208f.destroyPlayer();
            this.f24208f = null;
            a aVar = this.f24205c;
            if (aVar != null) {
                aVar.a();
            }
        }
    }

    public static /* synthetic */ boolean h(q qVar) {
        qVar.f24211i = true;
        return true;
    }

    public final void a() throws IllegalStateException {
        this.f24207e.lock();
        p pVar = this.f24208f;
        if (pVar != null) {
            if (this.f24209g == 0) {
                pVar.CancelOnPrepare();
            } else if (this.f24211i) {
                boolean zA = pVar.a();
                this.f24210h = zA;
                if (!zA) {
                    this.f24208f.pause();
                }
            }
        }
        this.f24207e.unlock();
    }

    public final boolean a(Context context, String str, int i2, int i3, int i4, boolean z2, long j2, long j3, a aVar) throws InterruptedException {
        this.f24207e.lock();
        this.f24205c = aVar;
        this.f24204b = context;
        this.f24206d.drainPermits();
        this.f24209g = 2;
        runOnUiThread(new AnonymousClass1(str, i2, i3, i4, z2, j2, j3));
        boolean z3 = false;
        try {
            this.f24207e.unlock();
            this.f24206d.acquire();
            this.f24207e.lock();
            if (this.f24209g != 2) {
                z3 = true;
            }
        } catch (InterruptedException unused) {
        }
        runOnUiThread(new Runnable() { // from class: com.unity3d.player.q.2
            @Override // java.lang.Runnable
            public final void run() {
                q.this.f24203a.pause();
            }
        });
        runOnUiThread((!z3 || this.f24209g == 3) ? new Runnable() { // from class: com.unity3d.player.q.4
            @Override // java.lang.Runnable
            public final void run() throws IllegalStateException {
                q.this.d();
                q.this.f24203a.resume();
            }
        } : new Runnable() { // from class: com.unity3d.player.q.3
            @Override // java.lang.Runnable
            public final void run() {
                if (q.this.f24208f != null) {
                    q.this.f24203a.addViewToPlayer(q.this.f24208f, true);
                    q.h(q.this);
                    q.this.f24208f.requestFocus();
                }
            }
        });
        this.f24207e.unlock();
        return z3;
    }

    public final void b() throws IllegalStateException {
        this.f24207e.lock();
        p pVar = this.f24208f;
        if (pVar != null && this.f24211i && !this.f24210h) {
            pVar.start();
        }
        this.f24207e.unlock();
    }

    public final void c() {
        this.f24207e.lock();
        p pVar = this.f24208f;
        if (pVar != null) {
            pVar.updateVideoLayout();
        }
        this.f24207e.unlock();
    }

    public final void runOnUiThread(Runnable runnable) {
        Context context = this.f24204b;
        if (context instanceof Activity) {
            ((Activity) context).runOnUiThread(runnable);
        } else {
            f.Log(5, "Not running from an Activity; Ignoring execution request...");
        }
    }
}
