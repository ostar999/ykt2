package com.xiaomi.push.service;

import android.os.SystemClock;
import java.util.concurrent.RejectedExecutionException;

/* loaded from: classes6.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    private static long f25686a;

    /* renamed from: b, reason: collision with root package name */
    private static long f25687b;

    /* renamed from: c, reason: collision with root package name */
    private static long f25688c;

    /* renamed from: a, reason: collision with other field name */
    private final a f1063a;

    /* renamed from: a, reason: collision with other field name */
    private final c f1064a;

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private final c f25689a;

        public a(c cVar) {
            this.f25689a = cVar;
        }

        public void finalize() throws Throwable {
            try {
                synchronized (this.f25689a) {
                    this.f25689a.f25693c = true;
                    this.f25689a.notify();
                }
            } finally {
                super.finalize();
            }
        }
    }

    public static abstract class b implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        protected int f25690a;

        public b(int i2) {
            this.f25690a = i2;
        }
    }

    public static final class c extends Thread {

        /* renamed from: b, reason: collision with other field name */
        private boolean f1067b;

        /* renamed from: c, reason: collision with root package name */
        private boolean f25693c;

        /* renamed from: a, reason: collision with root package name */
        private volatile long f25691a = 0;

        /* renamed from: a, reason: collision with other field name */
        private volatile boolean f1066a = false;

        /* renamed from: b, reason: collision with root package name */
        private long f25692b = 50;

        /* renamed from: a, reason: collision with other field name */
        private a f1065a = new a();

        public static final class a {

            /* renamed from: a, reason: collision with root package name */
            private int f25694a;

            /* renamed from: a, reason: collision with other field name */
            private d[] f1068a;

            /* renamed from: b, reason: collision with root package name */
            private int f25695b;

            /* renamed from: c, reason: collision with root package name */
            private int f25696c;

            private a() {
                this.f25694a = 256;
                this.f1068a = new d[256];
                this.f25695b = 0;
                this.f25696c = 0;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public int a(d dVar) {
                int i2 = 0;
                while (true) {
                    d[] dVarArr = this.f1068a;
                    if (i2 >= dVarArr.length) {
                        return -1;
                    }
                    if (dVarArr[i2] == dVar) {
                        return i2;
                    }
                    i2++;
                }
            }

            private void c() {
                int i2 = this.f25695b - 1;
                int i3 = (i2 - 1) / 2;
                while (true) {
                    d[] dVarArr = this.f1068a;
                    d dVar = dVarArr[i2];
                    long j2 = dVar.f1069a;
                    d dVar2 = dVarArr[i3];
                    if (j2 >= dVar2.f1069a) {
                        return;
                    }
                    dVarArr[i2] = dVar2;
                    dVarArr[i3] = dVar;
                    int i4 = i3;
                    i3 = (i3 - 1) / 2;
                    i2 = i4;
                }
            }

            private void c(int i2) {
                int i3 = (i2 * 2) + 1;
                while (true) {
                    int i4 = this.f25695b;
                    if (i3 >= i4 || i4 <= 0) {
                        return;
                    }
                    int i5 = i3 + 1;
                    if (i5 < i4) {
                        d[] dVarArr = this.f1068a;
                        if (dVarArr[i5].f1069a < dVarArr[i3].f1069a) {
                            i3 = i5;
                        }
                    }
                    d[] dVarArr2 = this.f1068a;
                    d dVar = dVarArr2[i2];
                    long j2 = dVar.f1069a;
                    d dVar2 = dVarArr2[i3];
                    if (j2 < dVar2.f1069a) {
                        return;
                    }
                    dVarArr2[i2] = dVar2;
                    dVarArr2[i3] = dVar;
                    int i6 = i3;
                    i3 = (i3 * 2) + 1;
                    i2 = i6;
                }
            }

            public d a() {
                return this.f1068a[0];
            }

            /* renamed from: a, reason: collision with other method in class */
            public void m751a() {
                this.f1068a = new d[this.f25694a];
                this.f25695b = 0;
            }

            public void a(int i2) {
                for (int i3 = 0; i3 < this.f25695b; i3++) {
                    d dVar = this.f1068a[i3];
                    if (dVar.f25697a == i2) {
                        dVar.a();
                    }
                }
                b();
            }

            public void a(int i2, b bVar) {
                for (int i3 = 0; i3 < this.f25695b; i3++) {
                    d dVar = this.f1068a[i3];
                    if (dVar.f1070a == bVar) {
                        dVar.a();
                    }
                }
                b();
            }

            /* renamed from: a, reason: collision with other method in class */
            public void m752a(d dVar) {
                d[] dVarArr = this.f1068a;
                int length = dVarArr.length;
                int i2 = this.f25695b;
                if (length == i2) {
                    d[] dVarArr2 = new d[i2 * 2];
                    System.arraycopy(dVarArr, 0, dVarArr2, 0, i2);
                    this.f1068a = dVarArr2;
                }
                d[] dVarArr3 = this.f1068a;
                int i3 = this.f25695b;
                this.f25695b = i3 + 1;
                dVarArr3[i3] = dVar;
                c();
            }

            /* renamed from: a, reason: collision with other method in class */
            public boolean m753a() {
                return this.f25695b == 0;
            }

            /* renamed from: a, reason: collision with other method in class */
            public boolean m754a(int i2) {
                for (int i3 = 0; i3 < this.f25695b; i3++) {
                    if (this.f1068a[i3].f25697a == i2) {
                        return true;
                    }
                }
                return false;
            }

            public void b() {
                int i2 = 0;
                while (i2 < this.f25695b) {
                    if (this.f1068a[i2].f1072a) {
                        this.f25696c++;
                        b(i2);
                        i2--;
                    }
                    i2++;
                }
            }

            public void b(int i2) {
                int i3;
                if (i2 < 0 || i2 >= (i3 = this.f25695b)) {
                    return;
                }
                d[] dVarArr = this.f1068a;
                int i4 = i3 - 1;
                this.f25695b = i4;
                dVarArr[i2] = dVarArr[i4];
                dVarArr[i4] = null;
                c(i2);
            }
        }

        public c(String str, boolean z2) {
            setName(str);
            setDaemon(z2);
            start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(d dVar) {
            this.f1065a.m752a(dVar);
            notify();
        }

        public synchronized void a() {
            this.f1067b = true;
            this.f1065a.m751a();
            notify();
        }

        /* renamed from: a, reason: collision with other method in class */
        public boolean m750a() {
            return this.f1066a && SystemClock.uptimeMillis() - this.f25691a > 600000;
        }

        /* JADX WARN: Code restructure failed: missing block: B:50:0x008e, code lost:
        
            r10.f25691a = android.os.SystemClock.uptimeMillis();
            r10.f1066a = true;
            r2.f1070a.run();
            r10.f1066a = false;
         */
        /* JADX WARN: Code restructure failed: missing block: B:52:0x009f, code lost:
        
            r1 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x00a0, code lost:
        
            monitor-enter(r10);
         */
        /* JADX WARN: Code restructure failed: missing block: B:54:0x00a1, code lost:
        
            r10.f1067b = true;
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x00a4, code lost:
        
            throw r1;
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r10 = this;
            L0:
                monitor-enter(r10)
                boolean r0 = r10.f1067b     // Catch: java.lang.Throwable -> Lae
                if (r0 == 0) goto L7
                monitor-exit(r10)     // Catch: java.lang.Throwable -> Lae
                return
            L7:
                com.xiaomi.push.service.k$c$a r0 = r10.f1065a     // Catch: java.lang.Throwable -> Lae
                boolean r0 = r0.m753a()     // Catch: java.lang.Throwable -> Lae
                if (r0 == 0) goto L1a
                boolean r0 = r10.f25693c     // Catch: java.lang.Throwable -> Lae
                if (r0 == 0) goto L15
                monitor-exit(r10)     // Catch: java.lang.Throwable -> Lae
                return
            L15:
                r10.wait()     // Catch: java.lang.InterruptedException -> L18 java.lang.Throwable -> Lae
            L18:
                monitor-exit(r10)     // Catch: java.lang.Throwable -> Lae
                goto L0
            L1a:
                long r0 = com.xiaomi.push.service.k.a()     // Catch: java.lang.Throwable -> Lae
                com.xiaomi.push.service.k$c$a r2 = r10.f1065a     // Catch: java.lang.Throwable -> Lae
                com.xiaomi.push.service.k$d r2 = r2.a()     // Catch: java.lang.Throwable -> Lae
                java.lang.Object r3 = r2.f1071a     // Catch: java.lang.Throwable -> Lae
                monitor-enter(r3)     // Catch: java.lang.Throwable -> Lae
                boolean r4 = r2.f1072a     // Catch: java.lang.Throwable -> Lab
                r5 = 0
                if (r4 == 0) goto L33
                com.xiaomi.push.service.k$c$a r0 = r10.f1065a     // Catch: java.lang.Throwable -> Lab
                r0.b(r5)     // Catch: java.lang.Throwable -> Lab
                monitor-exit(r3)     // Catch: java.lang.Throwable -> Lab
                goto L18
            L33:
                long r6 = r2.f1069a     // Catch: java.lang.Throwable -> Lab
                long r6 = r6 - r0
                monitor-exit(r3)     // Catch: java.lang.Throwable -> Lab
                r0 = 0
                int r3 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
                r8 = 50
                if (r3 <= 0) goto L55
                long r0 = r10.f25692b     // Catch: java.lang.Throwable -> Lae
                int r2 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
                if (r2 <= 0) goto L46
                r6 = r0
            L46:
                long r0 = r0 + r8
                r10.f25692b = r0     // Catch: java.lang.Throwable -> Lae
                r2 = 500(0x1f4, double:2.47E-321)
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 <= 0) goto L51
                r10.f25692b = r2     // Catch: java.lang.Throwable -> Lae
            L51:
                r10.wait(r6)     // Catch: java.lang.InterruptedException -> L18 java.lang.Throwable -> Lae
                goto L18
            L55:
                r10.f25692b = r8     // Catch: java.lang.Throwable -> Lae
                java.lang.Object r3 = r2.f1071a     // Catch: java.lang.Throwable -> Lae
                monitor-enter(r3)     // Catch: java.lang.Throwable -> Lae
                com.xiaomi.push.service.k$c$a r4 = r10.f1065a     // Catch: java.lang.Throwable -> La8
                com.xiaomi.push.service.k$d r4 = r4.a()     // Catch: java.lang.Throwable -> La8
                long r6 = r4.f1069a     // Catch: java.lang.Throwable -> La8
                long r8 = r2.f1069a     // Catch: java.lang.Throwable -> La8
                int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r4 == 0) goto L6f
                com.xiaomi.push.service.k$c$a r4 = r10.f1065a     // Catch: java.lang.Throwable -> La8
                int r4 = com.xiaomi.push.service.k.c.a.a(r4, r2)     // Catch: java.lang.Throwable -> La8
                goto L70
            L6f:
                r4 = r5
            L70:
                boolean r6 = r2.f1072a     // Catch: java.lang.Throwable -> La8
                if (r6 == 0) goto L7f
                com.xiaomi.push.service.k$c$a r0 = r10.f1065a     // Catch: java.lang.Throwable -> La8
                int r1 = com.xiaomi.push.service.k.c.a.a(r0, r2)     // Catch: java.lang.Throwable -> La8
                r0.b(r1)     // Catch: java.lang.Throwable -> La8
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La8
                goto L18
            L7f:
                long r6 = r2.f1069a     // Catch: java.lang.Throwable -> La8
                r2.a(r6)     // Catch: java.lang.Throwable -> La8
                com.xiaomi.push.service.k$c$a r6 = r10.f1065a     // Catch: java.lang.Throwable -> La8
                r6.b(r4)     // Catch: java.lang.Throwable -> La8
                r2.f1069a = r0     // Catch: java.lang.Throwable -> La8
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La8
                monitor-exit(r10)     // Catch: java.lang.Throwable -> Lae
                r0 = 1
                long r3 = android.os.SystemClock.uptimeMillis()     // Catch: java.lang.Throwable -> L9f
                r10.f25691a = r3     // Catch: java.lang.Throwable -> L9f
                r10.f1066a = r0     // Catch: java.lang.Throwable -> L9f
                com.xiaomi.push.service.k$b r1 = r2.f1070a     // Catch: java.lang.Throwable -> L9f
                r1.run()     // Catch: java.lang.Throwable -> L9f
                r10.f1066a = r5     // Catch: java.lang.Throwable -> L9f
                goto L0
            L9f:
                r1 = move-exception
                monitor-enter(r10)
                r10.f1067b = r0     // Catch: java.lang.Throwable -> La5
                monitor-exit(r10)     // Catch: java.lang.Throwable -> La5
                throw r1
            La5:
                r0 = move-exception
                monitor-exit(r10)     // Catch: java.lang.Throwable -> La5
                throw r0
            La8:
                r0 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La8
                throw r0     // Catch: java.lang.Throwable -> Lae
            Lab:
                r0 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> Lab
                throw r0     // Catch: java.lang.Throwable -> Lae
            Lae:
                r0 = move-exception
                monitor-exit(r10)     // Catch: java.lang.Throwable -> Lae
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.k.c.run():void");
        }
    }

    public static class d {

        /* renamed from: a, reason: collision with root package name */
        int f25697a;

        /* renamed from: a, reason: collision with other field name */
        long f1069a;

        /* renamed from: a, reason: collision with other field name */
        b f1070a;

        /* renamed from: a, reason: collision with other field name */
        final Object f1071a = new Object();

        /* renamed from: a, reason: collision with other field name */
        boolean f1072a;

        /* renamed from: b, reason: collision with root package name */
        private long f25698b;

        public void a(long j2) {
            synchronized (this.f1071a) {
                this.f25698b = j2;
            }
        }

        public boolean a() {
            boolean z2;
            synchronized (this.f1071a) {
                z2 = !this.f1072a && this.f1069a > 0;
                this.f1072a = true;
            }
            return z2;
        }
    }

    static {
        long jElapsedRealtime = SystemClock.elapsedRealtime() > 0 ? SystemClock.elapsedRealtime() : 0L;
        f25686a = jElapsedRealtime;
        f25687b = jElapsedRealtime;
    }

    public k() {
        this(false);
    }

    public k(String str) {
        this(str, false);
    }

    public k(String str, boolean z2) {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        c cVar = new c(str, z2);
        this.f1064a = cVar;
        this.f1063a = new a(cVar);
    }

    public k(boolean z2) {
        this("Timer-" + b(), z2);
    }

    public static synchronized long a() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = f25687b;
        if (jElapsedRealtime > j2) {
            f25686a += jElapsedRealtime - j2;
        }
        f25687b = jElapsedRealtime;
        return f25686a;
    }

    private static synchronized long b() {
        long j2;
        j2 = f25688c;
        f25688c = 1 + j2;
        return j2;
    }

    private void b(b bVar, long j2) {
        synchronized (this.f1064a) {
            if (this.f1064a.f1067b) {
                throw new IllegalStateException("Timer was canceled");
            }
            long jA = j2 + a();
            if (jA < 0) {
                throw new IllegalArgumentException("Illegal delay to start the TimerTask: " + jA);
            }
            d dVar = new d();
            dVar.f25697a = bVar.f25690a;
            dVar.f1070a = bVar;
            dVar.f1069a = jA;
            this.f1064a.a(dVar);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m745a() {
        this.f1064a.a();
    }

    public void a(int i2) {
        synchronized (this.f1064a) {
            this.f1064a.f1065a.a(i2);
        }
    }

    public void a(int i2, b bVar) {
        synchronized (this.f1064a) {
            this.f1064a.f1065a.a(i2, bVar);
        }
    }

    public void a(b bVar) {
        if (com.xiaomi.channel.commonutils.logger.b.a() >= 1 || Thread.currentThread() == this.f1064a) {
            bVar.run();
        } else {
            com.xiaomi.channel.commonutils.logger.b.d("run job outside job job thread");
            throw new RejectedExecutionException("Run job outside job thread");
        }
    }

    public void a(b bVar, long j2) {
        if (j2 >= 0) {
            b(bVar, j2);
            return;
        }
        throw new IllegalArgumentException("delay < 0: " + j2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m746a() {
        return this.f1064a.m750a();
    }

    /* renamed from: a, reason: collision with other method in class */
    public boolean m747a(int i2) {
        boolean zM754a;
        synchronized (this.f1064a) {
            zM754a = this.f1064a.f1065a.m754a(i2);
        }
        return zM754a;
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m748b() {
        synchronized (this.f1064a) {
            this.f1064a.f1065a.m751a();
        }
    }
}
