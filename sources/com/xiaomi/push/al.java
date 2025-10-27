package com.xiaomi.push;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class al {

    /* renamed from: a, reason: collision with root package name */
    private int f24603a;

    /* renamed from: a, reason: collision with other field name */
    private Handler f186a;

    /* renamed from: a, reason: collision with other field name */
    private a f187a;

    /* renamed from: a, reason: collision with other field name */
    private volatile b f188a;

    /* renamed from: a, reason: collision with other field name */
    private volatile boolean f189a;

    /* renamed from: b, reason: collision with root package name */
    private final boolean f24604b;

    public class a extends Thread {

        /* renamed from: a, reason: collision with other field name */
        private final LinkedBlockingQueue<b> f190a;

        public a() {
            super("PackageProcessor");
            this.f190a = new LinkedBlockingQueue<>();
        }

        private void a(int i2, b bVar) {
            al.this.f186a.sendMessage(al.this.f186a.obtainMessage(i2, bVar));
        }

        public void a(b bVar) {
            try {
                this.f190a.add(bVar);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException {
            long j2 = al.this.f24603a > 0 ? al.this.f24603a : Long.MAX_VALUE;
            while (!al.this.f189a) {
                try {
                    b bVarPoll = this.f190a.poll(j2, TimeUnit.SECONDS);
                    al.this.f188a = bVarPoll;
                    if (bVarPoll != null) {
                        a(0, bVarPoll);
                        bVarPoll.b();
                        a(1, bVarPoll);
                    } else if (al.this.f24603a > 0) {
                        al.this.a();
                    }
                } catch (InterruptedException e2) {
                    com.xiaomi.channel.commonutils.logger.b.a(e2);
                }
            }
        }
    }

    public static abstract class b {
        public void a() {
        }

        public abstract void b();

        /* renamed from: c */
        public void mo328c() {
        }
    }

    public al() {
        this(false);
    }

    public al(boolean z2) {
        this(z2, 0);
    }

    public al(boolean z2, int i2) {
        this.f186a = null;
        this.f189a = false;
        this.f24603a = 0;
        this.f186a = new am(this, Looper.getMainLooper());
        this.f24604b = z2;
        this.f24603a = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a() {
        this.f187a = null;
        this.f189a = true;
    }

    public synchronized void a(b bVar) {
        if (this.f187a == null) {
            a aVar = new a();
            this.f187a = aVar;
            aVar.setDaemon(this.f24604b);
            this.f189a = false;
            this.f187a.start();
        }
        this.f187a.a(bVar);
    }

    public void a(b bVar, long j2) {
        this.f186a.postDelayed(new an(this, bVar), j2);
    }
}
