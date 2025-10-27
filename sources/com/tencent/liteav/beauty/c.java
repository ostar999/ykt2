package com.tencent.liteav.beauty;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.beauty.b.i;
import com.tencent.liteav.beauty.b.j;
import com.tencent.liteav.beauty.b.k;
import com.tencent.liteav.beauty.b.l;
import com.tencent.liteav.beauty.b.m;
import com.tencent.liteav.beauty.b.n;
import com.tencent.liteav.beauty.b.o;
import com.tencent.liteav.beauty.b.x;
import com.tencent.liteav.beauty.d;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
class c extends HandlerThread {
    private int A;
    private int B;
    private Context C;
    private boolean D;
    private boolean E;
    private d.e F;
    private d.f G;
    private int H;
    private int I;
    private int J;
    private int K;
    private int L;
    private int M;
    private float N;
    private int O;
    private int P;
    private int Q;
    private boolean R;
    private float[] S;
    private boolean T;
    private int U;
    private int V;
    private com.tencent.liteav.basic.opengl.a W;
    private Bitmap X;
    private k Y;
    private o Z;

    /* renamed from: a, reason: collision with root package name */
    boolean f19014a;
    private int aA;
    private int aB;
    private int aC;
    private int aD;
    private int aE;
    private boolean aF;
    private com.tencent.liteav.beauty.a.a.c aG;
    private com.tencent.liteav.beauty.a.a.a aH;
    private Bitmap aI;
    private List<d.f> aJ;
    private long aK;
    private int aL;
    private final int aM;
    private final float aN;
    private byte[] aO;
    private int[] aP;
    private boolean aQ;
    private byte[] aR;
    private int aS;
    private int aT;
    private int aU;
    private int aV;
    private e aW;
    private WeakReference<com.tencent.liteav.basic.b.b> aX;
    private com.tencent.liteav.beauty.b.a aY;
    private final com.tencent.liteav.basic.util.c aZ;
    private com.tencent.liteav.beauty.b.b aa;
    private com.tencent.liteav.beauty.b.a.a ab;
    private com.tencent.liteav.beauty.b.b.a ac;
    private com.tencent.liteav.beauty.b.c ad;
    private com.tencent.liteav.beauty.b.c.a ae;
    private Bitmap af;
    private Bitmap ag;
    private float ah;
    private float ai;
    private float aj;
    private m ak;
    private n al;
    private x am;
    private j an;
    private i ao;
    private com.tencent.liteav.basic.opengl.j ap;
    private l aq;
    private com.tencent.liteav.basic.opengl.k ar;
    private com.tencent.liteav.basic.opengl.j as;
    private final Queue<Runnable> at;
    private boolean au;
    private Object av;
    private Object aw;
    private Handler ax;
    private a ay;
    private float az;

    /* renamed from: b, reason: collision with root package name */
    protected int[] f19015b;
    private TXCOpenGlUtils.a ba;

    /* renamed from: c, reason: collision with root package name */
    protected int[] f19016c;

    /* renamed from: d, reason: collision with root package name */
    private int f19017d;

    /* renamed from: e, reason: collision with root package name */
    private int f19018e;

    /* renamed from: f, reason: collision with root package name */
    private int f19019f;

    /* renamed from: g, reason: collision with root package name */
    private int f19020g;

    /* renamed from: h, reason: collision with root package name */
    private int f19021h;

    /* renamed from: i, reason: collision with root package name */
    private int f19022i;

    /* renamed from: j, reason: collision with root package name */
    private int f19023j;

    /* renamed from: k, reason: collision with root package name */
    private int f19024k;

    /* renamed from: l, reason: collision with root package name */
    private int f19025l;

    /* renamed from: m, reason: collision with root package name */
    private int f19026m;

    /* renamed from: n, reason: collision with root package name */
    private int f19027n;

    /* renamed from: o, reason: collision with root package name */
    private int f19028o;

    /* renamed from: p, reason: collision with root package name */
    private int f19029p;

    /* renamed from: q, reason: collision with root package name */
    private int f19030q;

    /* renamed from: r, reason: collision with root package name */
    private int f19031r;

    /* renamed from: s, reason: collision with root package name */
    private int f19032s;

    /* renamed from: t, reason: collision with root package name */
    private int f19033t;

    /* renamed from: u, reason: collision with root package name */
    private int f19034u;

    /* renamed from: v, reason: collision with root package name */
    private int f19035v;

    /* renamed from: w, reason: collision with root package name */
    private int f19036w;

    /* renamed from: x, reason: collision with root package name */
    private int f19037x;

    /* renamed from: y, reason: collision with root package name */
    private int f19038y;

    /* renamed from: z, reason: collision with root package name */
    private int f19039z;

    public c(Context context, boolean z2) {
        super("TXCFilterDrawer");
        this.f19017d = 0;
        this.f19018e = 0;
        this.f19019f = 0;
        this.f19020g = 0;
        this.f19021h = 0;
        this.f19022i = 0;
        this.f19023j = 0;
        this.f19024k = 0;
        this.f19025l = 0;
        this.f19026m = 0;
        this.f19027n = 0;
        this.f19028o = 0;
        this.f19029p = 0;
        this.f19030q = 0;
        this.f19031r = 0;
        this.f19032s = 0;
        this.f19033t = 0;
        this.f19034u = 0;
        this.f19035v = 0;
        this.f19036w = 0;
        this.f19037x = 0;
        this.f19038y = 0;
        this.f19039z = 0;
        this.A = 0;
        this.B = 0;
        this.C = null;
        this.D = true;
        this.E = false;
        this.F = new d.e();
        this.G = null;
        this.H = -1;
        this.I = -1;
        this.J = -1;
        this.K = -1;
        this.L = -1;
        this.M = -1;
        this.N = 1.0f;
        this.O = -1;
        this.P = -1;
        this.Q = 1;
        this.R = false;
        this.S = null;
        this.T = false;
        this.U = 0;
        this.V = 0;
        this.W = null;
        this.X = null;
        this.Y = null;
        this.Z = null;
        this.aa = null;
        this.ab = null;
        this.ac = null;
        this.ad = null;
        this.ae = null;
        this.al = null;
        this.am = null;
        this.an = null;
        this.ao = null;
        this.ap = null;
        this.aq = null;
        this.ar = null;
        this.as = null;
        this.at = new LinkedList();
        this.f19014a = false;
        this.av = new Object();
        this.aw = new Object();
        this.az = 0.5f;
        this.aA = 0;
        this.aB = 0;
        this.aC = 0;
        this.aD = 0;
        this.aE = 0;
        this.aF = false;
        this.aG = null;
        this.aH = null;
        this.aI = null;
        this.aJ = null;
        this.aK = 0L;
        this.aL = 0;
        this.aM = 100;
        this.aN = 1000.0f;
        this.aO = null;
        this.aP = null;
        this.aQ = false;
        this.aR = null;
        this.f19015b = null;
        this.f19016c = null;
        this.aS = -1;
        this.aT = 0;
        this.aU = 1;
        this.aV = -1;
        this.aW = null;
        this.aX = new WeakReference<>(null);
        this.aY = new com.tencent.liteav.beauty.b.a();
        this.ba = new TXCOpenGlUtils.a() { // from class: com.tencent.liteav.beauty.c.12
        };
        this.C = context;
        this.ax = new Handler(this.C.getMainLooper());
        this.au = z2;
        this.aZ = new com.tencent.liteav.basic.util.c("filter-drawer", (int) TimeUnit.SECONDS.toMillis(5L));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int B(int i2) {
        GLES20.glViewport(0, 0, this.H, this.I);
        return a(this.Y.r(), i2, 0L);
    }

    public void A(int i2) {
    }

    public void a(int i2) {
    }

    public void a(String str) {
    }

    public void a(String str, boolean z2) {
    }

    public void b(boolean z2) {
    }

    public void h(int i2) {
    }

    public void i(int i2) {
    }

    public void j(int i2) {
    }

    public void k(int i2) {
    }

    public void l(int i2) {
    }

    public void m(int i2) {
    }

    public void n(int i2) {
    }

    public void o(int i2) {
    }

    public void p(int i2) {
    }

    public void q(int i2) {
    }

    public void r(int i2) {
    }

    public void s(int i2) {
    }

    public void t(int i2) {
    }

    public void u(int i2) {
    }

    public void v(int i2) {
    }

    public void w(int i2) {
    }

    public void x(int i2) {
    }

    public void y(int i2) {
    }

    public void z(int i2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(d.b bVar) {
        TXCLog.i("TXCFilterDrawer", "come into initInternal");
        b();
        this.au = bVar.f19104j;
        this.H = bVar.f19098d;
        this.I = bVar.f19099e;
        this.W = bVar.f19107m;
        int i2 = bVar.f19101g;
        int i3 = bVar.f19100f;
        int i4 = bVar.f19102h;
        this.R = bVar.f19103i;
        int i5 = bVar.f19096b;
        this.O = i5;
        int i6 = bVar.f19097c;
        this.P = i6;
        int i7 = bVar.f19095a;
        this.J = i2;
        this.K = i3;
        if (i4 == 90 || i4 == 270) {
            this.J = i3;
            this.K = i2;
        }
        this.V = bVar.f19106l;
        this.U = bVar.f19105k;
        this.aO = new byte[i5 * i6 * 4];
        TXCLog.i("TXCFilterDrawer", "processWidth mPituScaleRatio is %f, process size: %d x %d", Float.valueOf(this.N), Integer.valueOf(this.J), Integer.valueOf(this.K));
        if (this.N != 1.0f) {
            int i8 = this.J;
            int i9 = this.K;
            if (i8 >= i9) {
                i8 = i9;
            }
            if (i8 > 368) {
                this.N = 432.0f / i8;
            }
            if (this.N > 1.0f) {
                this.N = 1.0f;
            }
        }
        float f2 = this.J;
        float f3 = this.N;
        int i10 = (int) (f2 * f3);
        this.L = i10;
        int i11 = (int) (this.K * f3);
        this.M = i11;
        a(i10, i11, this.aA);
        d.f fVar = this.G;
        if (fVar != null && fVar.f19124a != null && this.am == null) {
            TXCLog.i("TXCFilterDrawer", "reset water mark!");
            d.f fVar2 = this.G;
            a(fVar2.f19124a, fVar2.f19125b, fVar2.f19126c, fVar2.f19127d);
        }
        Bitmap bitmap = this.af;
        if ((bitmap != null || this.ag != null) && this.ak == null) {
            a(this.L, this.M, this.ah, bitmap, this.ai, this.ag, this.aj);
        }
        a(this.W, i2, i3, this.L, this.M, this.R, i4, this.U);
        a(this.J, this.K, this.O, this.P, i7);
        TXCLog.i("TXCFilterDrawer", "come out initInternal");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d(d.b bVar) {
        int i2 = bVar.f19105k;
        if ((1 == i2 || 3 == i2 || 2 == i2) && this.Y == null) {
            k kVar = new k(bVar.f19105k);
            this.Y = kVar;
            kVar.a(true);
            if (!this.Y.a()) {
                TXCLog.e("TXCFilterDrawer", "mI4202RGBAFilter init failed!!, break init");
                return false;
            }
            this.Y.a(bVar.f19098d, bVar.f19099e);
        }
        int i3 = bVar.f19106l;
        if ((1 == i3 || 3 == i3 || 2 == i3) && this.Z == null) {
            o oVar = new o(bVar.f19106l);
            this.Z = oVar;
            if (!oVar.a()) {
                TXCLog.e("TXCFilterDrawer", "mRGBA2I420Filter init failed!!, break init");
                return false;
            }
            this.Z.a(bVar.f19096b, bVar.f19097c);
        }
        return true;
    }

    public void e(final int i2) {
        this.aC = i2;
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.3
            @Override // java.lang.Runnable
            public void run() {
                if (i2 > 0) {
                    com.tencent.liteav.beauty.a.a().c();
                }
                if (c.this.aa == null || i2 < 0) {
                    return;
                }
                c.this.aa.d(i2);
            }
        });
    }

    public void f(final int i2) {
        this.aD = i2;
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.4
            @Override // java.lang.Runnable
            public void run() {
                if (i2 > 0) {
                    com.tencent.liteav.beauty.a.a().f();
                }
                if (c.this.aa == null || i2 < 0) {
                    return;
                }
                c.this.aa.f(i2);
            }
        });
    }

    public void g(final int i2) {
        this.aE = i2;
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.5
            @Override // java.lang.Runnable
            public void run() {
                if (i2 > 0) {
                    com.tencent.liteav.beauty.a.a().d();
                }
                if (c.this.aa == null || i2 < 0) {
                    return;
                }
                c.this.aa.e(i2);
            }
        });
    }

    public void b(final int i2) {
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.11
            @Override // java.lang.Runnable
            public void run() {
                c.this.V = i2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(byte[] bArr) {
        k kVar = this.Y;
        if (kVar == null) {
            TXCLog.e("TXCFilterDrawer", "mI4202RGBAFilter is null!");
        } else {
            kVar.a(bArr);
        }
    }

    public class a extends Handler {

        /* renamed from: b, reason: collision with root package name */
        private String f19075b;

        public a(Looper looper, Context context) {
            super(looper);
            this.f19075b = "EGLDrawThreadHandler";
        }

        private void a(Object obj) {
            TXCLog.i(this.f19075b, "come into InitEGL");
            d.b bVar = (d.b) obj;
            a();
            c.this.aH = new com.tencent.liteav.beauty.a.a.a();
            c cVar = c.this;
            cVar.aG = new com.tencent.liteav.beauty.a.a.c(cVar.aH, bVar.f19101g, bVar.f19100f, false);
            c.this.aG.b();
            if (c.this.c(bVar)) {
                TXCLog.i(this.f19075b, "come out InitEGL");
            } else {
                TXCLog.e(this.f19075b, "initInternal failed!");
            }
        }

        public void b() {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x0071  */
        @Override // android.os.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void handleMessage(android.os.Message r7) {
            /*
                r6 = this;
                super.handleMessage(r7)
                int r0 = r7.what
                r1 = 1
                if (r0 == 0) goto L65
                if (r0 == r1) goto L57
                r2 = 2
                if (r0 == r2) goto L4d
                r2 = 3
                if (r0 == r2) goto L45
                r2 = 4
                if (r0 == r2) goto L21
                r2 = 5
                if (r0 == r2) goto L17
                goto L63
            L17:
                java.lang.Object r7 = r7.obj
                com.tencent.liteav.beauty.d$b r7 = (com.tencent.liteav.beauty.d.b) r7
                com.tencent.liteav.beauty.c r0 = com.tencent.liteav.beauty.c.this
                com.tencent.liteav.beauty.c.b(r0, r7)
                goto L63
            L21:
                com.tencent.liteav.beauty.c r0 = com.tencent.liteav.beauty.c.this
                int r7 = r7.arg1
                double r2 = (double) r7
                r4 = 4636737291354636288(0x4059000000000000, double:100.0)
                double r2 = r2 / r4
                float r7 = (float) r2
                com.tencent.liteav.beauty.c.a(r0, r7)
                com.tencent.liteav.beauty.c r7 = com.tencent.liteav.beauty.c.this
                com.tencent.liteav.beauty.b.m r7 = com.tencent.liteav.beauty.c.a(r7)
                if (r7 == 0) goto L63
                com.tencent.liteav.beauty.c r7 = com.tencent.liteav.beauty.c.this
                com.tencent.liteav.beauty.b.m r7 = com.tencent.liteav.beauty.c.a(r7)
                com.tencent.liteav.beauty.c r0 = com.tencent.liteav.beauty.c.this
                float r0 = com.tencent.liteav.beauty.c.q(r0)
                r7.a(r0)
                goto L63
            L45:
                com.tencent.liteav.beauty.c r0 = com.tencent.liteav.beauty.c.this
                int r7 = r7.arg1
                com.tencent.liteav.beauty.c.b(r0, r7)
                goto L6f
            L4d:
                com.tencent.liteav.beauty.c r0 = com.tencent.liteav.beauty.c.this
                java.lang.Object r7 = r7.obj
                byte[] r7 = (byte[]) r7
                com.tencent.liteav.beauty.c.a(r0, r7)
                goto L63
            L57:
                r6.a()
                com.tencent.liteav.beauty.c r7 = com.tencent.liteav.beauty.c.this
                com.tencent.liteav.beauty.b.a r7 = com.tencent.liteav.beauty.c.p(r7)
                r7.a()
            L63:
                r7 = 0
                goto L70
            L65:
                java.lang.Object r7 = r7.obj
                r6.a(r7)
                com.tencent.liteav.beauty.c r7 = com.tencent.liteav.beauty.c.this
                com.tencent.liteav.beauty.c.b(r7, r1)
            L6f:
                r7 = r1
            L70:
                monitor-enter(r6)
                if (r1 != r7) goto L76
                r6.notify()     // Catch: java.lang.Throwable -> L78
            L76:
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L78
                return
            L78:
                r7 = move-exception
                monitor-exit(r6)     // Catch: java.lang.Throwable -> L78
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.beauty.c.a.handleMessage(android.os.Message):void");
        }

        public void a() {
            TXCLog.i(this.f19075b, "come into releaseEGL");
            c.this.b();
            if (c.this.aG != null) {
                c.this.aG.c();
                c.this.aG = null;
            }
            if (c.this.aH != null) {
                c.this.aH.a();
                c.this.aH = null;
            }
            c.this.aF = false;
            NativeLoad.nativeDeleteYuv2Yuv();
            TXCLog.i(this.f19075b, "come out releaseEGL");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        TXCLog.i("TXCFilterDrawer", "come into releaseInternal");
        this.aQ = false;
        k kVar = this.Y;
        if (kVar != null) {
            kVar.d();
            this.Y = null;
        }
        o oVar = this.Z;
        if (oVar != null) {
            oVar.d();
            this.Z = null;
        }
        c();
        m mVar = this.ak;
        if (mVar != null) {
            mVar.d();
            this.ak = null;
        }
        n nVar = this.al;
        if (nVar != null) {
            nVar.a();
            this.al = null;
        }
        com.tencent.liteav.basic.opengl.k kVar2 = this.ar;
        if (kVar2 != null) {
            kVar2.d();
            this.ar = null;
        }
        l lVar = this.aq;
        if (lVar != null) {
            lVar.d();
            this.aq = null;
        }
        com.tencent.liteav.basic.opengl.j jVar = this.ap;
        if (jVar != null) {
            jVar.d();
            this.ap = null;
        }
        x xVar = this.am;
        if (xVar != null) {
            xVar.d();
            this.am = null;
        }
        j jVar2 = this.an;
        if (jVar2 != null) {
            jVar2.a();
            this.an = null;
        }
        i iVar = this.ao;
        if (iVar != null) {
            iVar.d();
            this.ao = null;
        }
        com.tencent.liteav.basic.opengl.j jVar3 = this.as;
        if (jVar3 != null) {
            jVar3.d();
            this.as = null;
        }
        int[] iArr = this.f19015b;
        if (iArr != null) {
            GLES20.glDeleteFramebuffers(1, iArr, 0);
            this.f19015b = null;
        }
        int[] iArr2 = this.f19016c;
        if (iArr2 != null) {
            GLES20.glDeleteTextures(1, iArr2, 0);
            this.f19016c = null;
        }
        int[] iArr3 = this.aP;
        if (iArr3 != null && iArr3[0] > 0) {
            GLES20.glDeleteBuffers(1, iArr3, 0);
            this.aP = null;
        }
        TXCLog.i("TXCFilterDrawer", "come out releaseInternal");
    }

    public void d(final int i2) {
        if (this.aA == i2 || i2 > 3 || i2 < 0) {
            return;
        }
        this.aA = i2;
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.2
            @Override // java.lang.Runnable
            public void run() {
                c cVar = c.this;
                cVar.a(cVar.L, c.this.M, i2);
            }
        });
    }

    public synchronized boolean a(d.b bVar) {
        boolean zC;
        if (!bVar.f19104j) {
            if (this.ay == null) {
                start();
                this.ay = new a(getLooper(), this.C);
            }
            this.ay.obtainMessage(0, bVar).sendToTarget();
            this.ay.b();
            zC = true;
        } else {
            zC = c(bVar);
        }
        return zC;
    }

    public int a(int i2, int i3, long j2) {
        int iA;
        this.aZ.a();
        a(this.at);
        boolean z2 = this.N != 1.0f;
        GLES20.glViewport(0, 0, this.L, this.M);
        l lVar = this.aq;
        if (lVar != null) {
            if (4 == i3 || true == this.T) {
                lVar.a(this.S);
                this.aq.c(this.T);
            }
            i2 = this.aq.b(i2);
        }
        if (this.aa != null) {
            this.aa.f(Math.min(this.O, this.P) < 540 ? 0 : this.aD);
            if (this.aB > 0 || this.aC > 0 || this.aE > 0) {
                i2 = this.aa.b(i2);
            }
        }
        m mVar = this.ak;
        if (mVar != null) {
            i2 = mVar.b(i2);
        }
        GLES20.glViewport(0, 0, this.J, this.K);
        j jVar = this.an;
        if (jVar != null) {
            i2 = jVar.a(i2);
            z2 = false;
        }
        i iVar = this.ao;
        if (iVar != null) {
            i2 = iVar.b(i2);
            z2 = false;
        }
        if (z2) {
            b(this.J, this.K);
            if (this.as != null) {
                GLES20.glViewport(0, 0, this.J, this.K);
                i2 = this.as.b(i2);
            }
        }
        e eVar = this.aW;
        if (eVar != null && (iA = eVar.a(i2, this.J, this.K)) > 0) {
            i2 = iA;
        }
        GLES20.glViewport(0, 0, this.J, this.K);
        x xVar = this.am;
        if (xVar != null) {
            i2 = xVar.b(i2);
        }
        if (this.ap != null) {
            GLES20.glViewport(0, 0, this.O, this.P);
            i2 = this.ap.b(i2);
        }
        a(i2, j2);
        return i2;
    }

    public void c(final int i2) {
        this.aB = i2;
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.13
            @Override // java.lang.Runnable
            public void run() {
                if (i2 > 0) {
                    com.tencent.liteav.beauty.a.a().b();
                }
                if (c.this.aa == null || i2 < 0) {
                    return;
                }
                c.this.aa.c(i2);
            }
        });
    }

    private void c() {
        com.tencent.liteav.beauty.b.a.a aVar = this.ab;
        if (aVar != null) {
            aVar.d();
            this.ab = null;
        }
        com.tencent.liteav.beauty.b.b.a aVar2 = this.ac;
        if (aVar2 != null) {
            aVar2.d();
            this.ac = null;
        }
        com.tencent.liteav.beauty.b.c cVar = this.ad;
        if (cVar != null) {
            cVar.d();
            this.ad = null;
        }
        com.tencent.liteav.beauty.b.c.a aVar3 = this.ae;
        if (aVar3 != null) {
            aVar3.d();
            this.ae = null;
        }
        this.aa = null;
    }

    public boolean b(d.b bVar) {
        if (!this.au) {
            a aVar = this.ay;
            if (aVar == null) {
                TXCLog.e("TXCFilterDrawer", "mThreadHandler is null!");
                return false;
            }
            aVar.obtainMessage(5, 0, 0, bVar).sendToTarget();
            return true;
        }
        d(bVar);
        return true;
    }

    public int a(byte[] bArr, int i2) {
        a(bArr);
        if (!this.au) {
            byte[] bArr2 = (byte[]) bArr.clone();
            this.ay.obtainMessage(2, bArr2).sendToTarget();
            if (!this.aQ) {
                TXCLog.i("TXCFilterDrawer", "First Frame, clear queue");
                NativeLoad.nativeClearQueue();
            }
            this.ay.obtainMessage(3, i2, 0).sendToTarget();
            a(bArr2, this.aQ);
            this.aQ = true;
            return -1;
        }
        b(bArr);
        return B(i2);
    }

    private void b(int i2, int i3) {
        if (this.as == null) {
            TXCLog.i("TXCFilterDrawer", "createRecoverScaleFilter");
            com.tencent.liteav.basic.opengl.j jVar = new com.tencent.liteav.basic.opengl.j();
            this.as = jVar;
            if (true == jVar.a()) {
                this.as.a(true);
            } else {
                TXCLog.e("TXCFilterDrawer", "mRecoverScaleFilter init failed!");
            }
        }
        com.tencent.liteav.basic.opengl.j jVar2 = this.as;
        if (jVar2 != null) {
            jVar2.a(i2, i3);
        }
    }

    public void a(final float f2) {
        this.az = f2;
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.1
            @Override // java.lang.Runnable
            public void run() {
                if (c.this.ak != null) {
                    c.this.ak.a(f2);
                }
            }
        });
    }

    public void a(final float[] fArr) {
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.7
            @Override // java.lang.Runnable
            public void run() {
                c.this.S = fArr;
            }
        });
    }

    public void a(final boolean z2) {
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.9
            @Override // java.lang.Runnable
            public void run() {
                c.this.T = z2;
            }
        });
    }

    private void a(com.tencent.liteav.basic.opengl.a aVar, int i2, int i3, int i4, int i5, boolean z2, int i6, int i7) {
        if (this.aq == null) {
            TXCLog.i("TXCFilterDrawer", "Create CropFilter");
            if (4 == i7) {
                this.aq = new l("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 textureTransform;\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "#extension GL_OES_EGL_image_external : require\n\nvarying lowp vec2 textureCoordinate;\n \nuniform samplerExternalOES inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}", true);
            } else {
                this.aq = new l();
            }
            if (true == this.aq.a()) {
                this.aq.a(true);
            } else {
                TXCLog.e("TXCFilterDrawer", "mInputCropFilter init failed!");
            }
        }
        int i8 = i4;
        this.aq.a(i8, i5);
        float[] fArrA = this.aq.a(this.H, this.I, null, aVar, i7);
        int i9 = (720 - i6) % 360;
        int i10 = (i9 == 90 || i9 == 270) ? i5 : i8;
        if (i9 != 90 && i9 != 270) {
            i8 = i5;
        }
        this.aq.a(i2, i3, i9, fArrA, i10 / i8, z2, false);
    }

    private void a(int i2, int i3, int i4, int i5, int i6) {
        synchronized (this.aw) {
            int i7 = (i6 + 360) % 360;
            TXCLog.i("TXCFilterDrawer", "real outputAngle " + i7);
            if (this.ap == null) {
                if (i2 == i4 && i3 == i5 && i7 == 0) {
                    TXCLog.i("TXCFilterDrawer", "Don't need change output Image, don't create out filter!");
                    return;
                }
                com.tencent.liteav.basic.opengl.j jVar = new com.tencent.liteav.basic.opengl.j();
                this.ap = jVar;
                if (true == jVar.a()) {
                    this.ap.a(true);
                } else {
                    TXCLog.e("TXCFilterDrawer", "mOutputZoomFilter init failed!");
                }
            }
            this.ap.a(i4, i5);
            this.ap.a((720 - i7) % 360, (FloatBuffer) null);
        }
    }

    public void a(final Bitmap bitmap, final float f2, final float f3, final float f4) {
        if (this.G == null) {
            this.G = new d.f();
        }
        if (TXCCommonUtil.equals(this.G.f19124a, bitmap)) {
            d.f fVar = this.G;
            if (f2 == fVar.f19125b && f3 == fVar.f19126c && f4 == fVar.f19127d && this.am != null) {
                Log.d("TXCFilterDrawer", "Same Water Mark; don't set again");
                return;
            }
        }
        d.f fVar2 = this.G;
        fVar2.f19124a = bitmap;
        fVar2.f19125b = f2;
        fVar2.f19126c = f3;
        fVar2.f19127d = f4;
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.10
            @Override // java.lang.Runnable
            public void run() {
                if (bitmap != null) {
                    com.tencent.liteav.beauty.a.a().g();
                }
                if (bitmap == null) {
                    if (c.this.am != null) {
                        c.this.am.d();
                        c.this.am = null;
                        return;
                    }
                    return;
                }
                if (c.this.am == null) {
                    if (c.this.J <= 0 || c.this.K <= 0) {
                        TXCLog.e("TXCFilterDrawer", "output Width and Height is error!");
                        return;
                    }
                    c.this.am = new x();
                    c.this.am.a(true);
                    if (!c.this.am.a()) {
                        TXCLog.e("TXCFilterDrawer", "mWatermarkFilter.init failed!");
                        c.this.am.d();
                        c.this.am = null;
                        return;
                    }
                    c.this.am.a(c.this.J, c.this.K);
                }
                c.this.am.c(true);
                c.this.am.a(bitmap, f2, f3, f4);
            }
        });
    }

    public void a(e eVar) {
        TXCLog.i("TXCFilterDrawer", "set listener");
        this.aW = eVar;
    }

    public void a(com.tencent.liteav.basic.b.b bVar) {
        TXCLog.i("TXCFilterDrawer", "set notify");
        WeakReference<com.tencent.liteav.basic.b.b> weakReference = new WeakReference<>(bVar);
        this.aX = weakReference;
        j jVar = this.an;
        if (jVar != null) {
            jVar.a(weakReference.get());
        }
    }

    private int a(int i2, long j2) {
        int i3 = this.V;
        if (i3 == 0) {
            if (this.aW != null) {
                if (j2 == 0) {
                    j2 = TXCTimeUtil.generatePtsMS();
                }
                this.aW.a(i2, this.O, this.P, j2);
            }
            return i2;
        }
        if (1 != i3 && 3 != i3 && 2 != i3) {
            TXCLog.e("TXCFilterDrawer", "Don't support format!");
            return -1;
        }
        GLES20.glViewport(0, 0, this.O, this.P);
        if (this.Z == null) {
            TXCLog.e("TXCFilterDrawer", "mRGBA2I420Filter is null!");
            return i2;
        }
        if (this.f19015b == null) {
            int[] iArr = new int[1];
            this.f19015b = iArr;
            int[] iArr2 = new int[1];
            this.f19016c = iArr2;
            a(iArr, iArr2, this.O, this.P);
        }
        GLES20.glBindFramebuffer(36160, this.f19015b[0]);
        this.Z.a(i2);
        if (2 == this.V) {
            a(this.O, this.P);
        } else {
            a(this.O, (this.P * 3) / 8);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return i2;
    }

    private int a(int i2, int i3) {
        if (true == this.au) {
            if (this.aW != null) {
                NativeLoad.nativeGlReadPixs(i2, i3, this.aO);
                this.aW.a(this.aO, this.O, this.P, this.V, TXCTimeUtil.generatePtsMS());
            } else {
                byte[] bArr = this.aR;
                if (bArr != null) {
                    NativeLoad.nativeGlReadPixs(i2, i3, bArr);
                }
            }
        } else if (3 == TXCOpenGlUtils.a()) {
            if (0 == this.aK) {
                this.aK = TXCTimeUtil.getTimeTick();
            }
            int i4 = this.aL + 1;
            this.aL = i4;
            if (i4 >= 100) {
                TXCLog.i("TXCFilterDrawer", "Real fps " + (100.0f / ((TXCTimeUtil.getTimeTick() - this.aK) / 1000.0f)));
                this.aL = 0;
                this.aK = TXCTimeUtil.getTimeTick();
            }
            GLES20.glPixelStorei(R2.attr.srlTextFinish, 1);
            if (TXCBuild.VersionInt() >= 18) {
                GLES30.glReadBuffer(1029);
            }
            if (this.aP == null) {
                int[] iArr = new int[1];
                this.aP = iArr;
                TXCOpenGlUtils.a(i2, i3, iArr);
                TXCLog.i("TXCFilterDrawer", "opengl es 3.0, use PBO");
            }
            GLES20.glBindBuffer(35051, this.aP[0]);
            ByteBuffer byteBuffer = null;
            NativeLoad.nativeGlReadPixs(i2, i3, null);
            if (TXCBuild.VersionInt() >= 18 && (byteBuffer = (ByteBuffer) GLES30.glMapBufferRange(35051, 0, i2 * i3 * 4, 1)) == null) {
                TXCLog.e("TXCFilterDrawer", "glMapBufferRange is null");
                return -1;
            }
            NativeLoad.nativeGlMapBufferToQueue(i2, i3, byteBuffer);
            if (TXCBuild.VersionInt() >= 18) {
                GLES30.glUnmapBuffer(35051);
            }
            GLES20.glBindBuffer(35051, 0);
        } else {
            NativeLoad.nativeGlReadPixsToQueue(i2, i3);
        }
        return 0;
    }

    private void a(byte[] bArr, boolean z2) {
        if (!z2) {
            e eVar = this.aW;
            if (eVar != null) {
                eVar.a(bArr, this.O, this.P, this.V, TXCTimeUtil.generatePtsMS());
                return;
            } else {
                TXCLog.i("TXCFilterDrawer", "First Frame, don't process!");
                return;
            }
        }
        int i2 = this.P;
        int i3 = (i2 * 3) / 8;
        if (2 != this.V) {
            i2 = i3;
        }
        if (this.aW != null) {
            if (true == NativeLoad.nativeGlReadPixsFromQueue(this.O, i2, this.aO)) {
                this.aW.a(this.aO, this.O, this.P, this.V, TXCTimeUtil.generatePtsMS());
                return;
            } else {
                TXCLog.d("TXCFilterDrawer", "nativeGlReadPixsFromQueue Failed");
                this.aW.a(bArr, this.O, this.P, this.V, TXCTimeUtil.generatePtsMS());
                return;
            }
        }
        if (NativeLoad.nativeGlReadPixsFromQueue(this.O, i2, this.aR)) {
            return;
        }
        TXCLog.d("TXCFilterDrawer", "nativeGlReadPixsFromQueue Failed");
    }

    public void a(byte[] bArr) {
        this.aR = bArr;
    }

    public void a() {
        if (!this.au) {
            a aVar = this.ay;
            if (aVar != null) {
                aVar.obtainMessage(1).sendToTarget();
                try {
                    this.aY.b();
                    return;
                } catch (InterruptedException unused) {
                    return;
                }
            }
            return;
        }
        b();
    }

    private void a(int[] iArr, int[] iArr2, int i2, int i3) {
        GLES20.glGenFramebuffers(1, iArr, 0);
        iArr2[0] = TXCOpenGlUtils.a(i2, i3, R2.dimen.dm_200, R2.dimen.dm_200, iArr2);
        GLES20.glBindFramebuffer(36160, iArr[0]);
        GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, iArr2[0], 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    public void a(Bitmap bitmap) {
        a(1.0f, bitmap, this.az, (Bitmap) null, 0.0f);
    }

    public void a(final float f2, final Bitmap bitmap, final float f3, final Bitmap bitmap2, final float f4) {
        if (this.af == bitmap && this.ag == bitmap2) {
            if (this.ak != null) {
                if (this.ah == f2 && this.ai == f3 && this.aj == f4) {
                    return;
                }
                this.ah = f2;
                this.ai = f3;
                this.aj = f4;
                a(new Runnable() { // from class: com.tencent.liteav.beauty.c.8
                    @Override // java.lang.Runnable
                    public void run() {
                        c.this.ak.a(f2, f3, f4);
                    }
                });
                return;
            }
            return;
        }
        this.af = bitmap;
        this.ag = bitmap2;
        this.ah = f2;
        this.ai = f3;
        this.aj = f4;
        a(new Runnable() { // from class: com.tencent.liteav.beauty.c.6
            @Override // java.lang.Runnable
            public void run() {
                if (c.this.ak != null) {
                    com.tencent.liteav.beauty.a.a().e();
                }
                if (c.this.af == null && c.this.ag == null) {
                    if (c.this.ak != null) {
                        c.this.ak.d();
                        c.this.ak = null;
                        return;
                    }
                    return;
                }
                if (c.this.ak != null) {
                    c.this.ak.a(f2, bitmap, f3, bitmap2, f4);
                } else {
                    c cVar = c.this;
                    cVar.a(cVar.L, c.this.M, c.this.ah, c.this.af, c.this.ai, c.this.ag, c.this.aj);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, int i3, int i4) {
        TXCLog.i("TXCFilterDrawer", "create Beauty Filter!");
        if (i4 == 0) {
            if (this.ab == null) {
                this.ab = new com.tencent.liteav.beauty.b.a.a();
            }
            this.aa = this.ab;
            Log.i("TXCFilterDrawer", "0 BeautyFilter");
        } else if (1 == i4) {
            if (this.ac == null) {
                this.ac = new com.tencent.liteav.beauty.b.b.a();
            }
            this.aa = this.ac;
            Log.i("TXCFilterDrawer", "1 BeautyFilter");
        } else if (2 == i4) {
            if (this.ae == null) {
                this.ae = new com.tencent.liteav.beauty.b.c.a();
            }
            this.aa = this.ae;
            Log.i("TXCFilterDrawer", "2 BeautyFilter");
        } else if (3 == i4) {
            if (this.ad == null) {
                this.ad = new com.tencent.liteav.beauty.b.c();
            }
            this.aa = this.ad;
            Log.i("TXCFilterDrawer", "3 BeautyFilter");
        }
        com.tencent.liteav.beauty.b.b bVar = this.aa;
        if (bVar == null) {
            TXCLog.e("TXCFilterDrawer", "mBeautyFilter set error!");
            return;
        }
        bVar.a(true);
        if (true == this.aa.c(i2, i3)) {
            int i5 = this.aB;
            if (i5 > 0) {
                this.aa.c(i5);
            }
            int i6 = this.aC;
            if (i6 > 0) {
                this.aa.d(i6);
            }
            int i7 = this.aE;
            if (i7 > 0) {
                this.aa.e(i7);
            }
            int i8 = this.aD;
            if (i8 > 0) {
                this.aa.f(i8);
                return;
            }
            return;
        }
        TXCLog.e("TXCFilterDrawer", "mBeautyFilter init failed!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, int i3, float f2, Bitmap bitmap, float f3, Bitmap bitmap2, float f4) {
        if (this.ak == null) {
            TXCLog.i("TXCFilterDrawer", "createComLooKupFilter");
            m mVar = new m(f2, bitmap, f3, bitmap2, f4);
            this.ak = mVar;
            if (true == mVar.a()) {
                this.ak.a(true);
                this.ak.a(i2, i3);
            } else {
                TXCLog.e("TXCFilterDrawer", "mLookupFilterGroup init failed!");
            }
        }
    }

    private void a(Runnable runnable) {
        synchronized (this.at) {
            this.at.add(runnable);
        }
    }

    private void a(Queue<Runnable> queue) {
        Runnable runnablePoll;
        while (true) {
            synchronized (queue) {
                runnablePoll = !queue.isEmpty() ? queue.poll() : null;
            }
            if (runnablePoll == null) {
                return;
            } else {
                runnablePoll.run();
            }
        }
    }
}
