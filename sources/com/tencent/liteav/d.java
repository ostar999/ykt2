package com.tencent.liteav;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.os.Bundle;
import android.os.Looper;
import android.view.Surface;
import com.tencent.liteav.b;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.module.TXCEventRecorderProxy;
import com.tencent.liteav.basic.module.TXCKeyPointReportProxy;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.structs.TXSVideoFrame;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.beauty.TXBeautyManager;
import com.tencent.liteav.beauty.d;
import com.tencent.liteav.renderer.TXCGLSurfaceView;
import com.tencent.liteav.screencapture.a;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class d extends com.tencent.liteav.basic.module.a implements b.InterfaceC0327b, com.tencent.liteav.basic.b.b, com.tencent.liteav.beauty.f, l, com.tencent.liteav.videoencoder.f {
    private com.tencent.liteav.basic.structs.b E;
    private WeakReference<o> M;
    private com.tencent.liteav.basic.opengl.j Q;
    private com.tencent.liteav.beauty.b.k R;
    private final com.tencent.liteav.beauty.b W;
    private WeakReference<m> Y;

    /* renamed from: a, reason: collision with root package name */
    b f19184a;
    private com.tencent.liteav.basic.opengl.a aa;
    private WeakReference<com.tencent.liteav.basic.b.b> ab;

    /* renamed from: b, reason: collision with root package name */
    b f19185b;

    /* renamed from: f, reason: collision with root package name */
    private com.tencent.liteav.beauty.d f19189f;

    /* renamed from: i, reason: collision with root package name */
    private TXSVideoEncoderParam f19192i;

    /* renamed from: j, reason: collision with root package name */
    private com.tencent.liteav.videoencoder.c f19193j;

    /* renamed from: m, reason: collision with root package name */
    private TXSVideoEncoderParam f19196m;

    /* renamed from: p, reason: collision with root package name */
    private Context f19199p;

    /* renamed from: q, reason: collision with root package name */
    private g f19200q;

    /* renamed from: c, reason: collision with root package name */
    private final com.tencent.liteav.basic.util.c f19186c = new com.tencent.liteav.basic.util.c("capturer", (int) TimeUnit.SECONDS.toMillis(5));

    /* renamed from: d, reason: collision with root package name */
    private final com.tencent.liteav.basic.util.f f19187d = new com.tencent.liteav.basic.util.f(Looper.getMainLooper());

    /* renamed from: e, reason: collision with root package name */
    private k f19188e = null;

    /* renamed from: g, reason: collision with root package name */
    private boolean f19190g = false;

    /* renamed from: h, reason: collision with root package name */
    private boolean f19191h = false;

    /* renamed from: k, reason: collision with root package name */
    private int f19194k = 8;

    /* renamed from: l, reason: collision with root package name */
    private boolean f19195l = false;

    /* renamed from: n, reason: collision with root package name */
    private com.tencent.liteav.videoencoder.c f19197n = null;

    /* renamed from: o, reason: collision with root package name */
    private final Object f19198o = new Object();

    /* renamed from: r, reason: collision with root package name */
    private int f19201r = 0;

    /* renamed from: s, reason: collision with root package name */
    private int f19202s = 0;

    /* renamed from: t, reason: collision with root package name */
    private int f19203t = 0;

    /* renamed from: u, reason: collision with root package name */
    private boolean f19204u = false;

    /* renamed from: v, reason: collision with root package name */
    private int f19205v = 0;

    /* renamed from: w, reason: collision with root package name */
    private int f19206w = 0;

    /* renamed from: x, reason: collision with root package name */
    private boolean f19207x = false;

    /* renamed from: y, reason: collision with root package name */
    private TXCloudVideoView f19208y = null;

    /* renamed from: z, reason: collision with root package name */
    private final Object f19209z = new Object();
    private Surface A = null;
    private int B = 0;
    private int C = 0;
    private com.tencent.liteav.basic.opengl.g D = null;
    private int F = 0;
    private boolean G = false;
    private boolean H = false;
    private volatile boolean I = false;
    private long J = 0;
    private long K = 0;
    private int L = 2;
    private boolean N = false;
    private WeakReference<a> O = null;
    private com.tencent.liteav.basic.opengl.j P = null;
    private int S = -1;
    private boolean T = false;
    private boolean U = false;
    private boolean V = false;
    private boolean X = true;
    private com.tencent.liteav.basic.opengl.f Z = null;
    private int ac = 0;
    private int ad = 0;

    public interface a {
        void onBackgroudPushStop();

        void onEncVideo(TXSNALPacket tXSNALPacket);

        void onEncVideoFormat(MediaFormat mediaFormat);
    }

    public d(Context context) {
        this.f19189f = null;
        this.f19192i = null;
        this.f19193j = null;
        this.f19196m = null;
        this.f19199p = null;
        this.f19200q = null;
        this.f19199p = context.getApplicationContext();
        this.f19200q = new g();
        com.tencent.liteav.beauty.d dVar = new com.tencent.liteav.beauty.d(this.f19199p, true);
        this.f19189f = dVar;
        dVar.a((com.tencent.liteav.beauty.f) this);
        this.f19189f.a((com.tencent.liteav.basic.b.b) this);
        g gVar = this.f19200q;
        if (gVar.X) {
            this.f19189f.a(d.EnumC0332d.MODE_SAME_AS_OUTPUT);
        } else if (gVar.W) {
            this.f19189f.a(d.EnumC0332d.MODE_SAME_AS_INPUT);
        } else {
            this.f19189f.a(d.EnumC0332d.MODE_THRESHOLD);
        }
        TXSVideoEncoderParam tXSVideoEncoderParam = new TXSVideoEncoderParam();
        this.f19192i = tXSVideoEncoderParam;
        tXSVideoEncoderParam.encoderMode = 1;
        this.f19193j = null;
        TXSVideoEncoderParam tXSVideoEncoderParam2 = new TXSVideoEncoderParam();
        this.f19196m = tXSVideoEncoderParam2;
        tXSVideoEncoderParam2.encoderMode = 1;
        this.f19184a = new b(this);
        com.tencent.liteav.beauty.b bVar = new com.tencent.liteav.beauty.b(new com.tencent.liteav.basic.license.g(this.f19199p));
        this.W = bVar;
        bVar.setPreprocessor(this.f19189f);
        com.tencent.liteav.basic.c.c.a().a(this.f19199p);
    }

    private void A() {
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.a(new Runnable() { // from class: com.tencent.liteav.d.17
                @Override // java.lang.Runnable
                public void run() {
                    d dVar = d.this;
                    dVar.c(dVar.f19192i.width, d.this.f19192i.height);
                }
            });
        }
    }

    private void B() {
        com.tencent.liteav.beauty.d dVar = this.f19189f;
        if (dVar != null) {
            g gVar = this.f19200q;
            if (gVar.X) {
                dVar.a(d.EnumC0332d.MODE_SAME_AS_OUTPUT);
            } else if (gVar.W) {
                dVar.a(d.EnumC0332d.MODE_SAME_AS_INPUT);
            } else {
                dVar.a(d.EnumC0332d.MODE_THRESHOLD);
            }
        }
    }

    private void v() {
        TXCLog.i("TXCCaptureAndEnc", " startBlackStream");
        if (this.f19185b == null) {
            this.f19185b = new b(this);
        }
        this.f19185b.a(10, -1, null, 64, 64);
    }

    private void w() {
        TXCLog.i("TXCCaptureAndEnc", " stopBlackStream when enableBlackStream " + this.f19204u);
        b bVar = this.f19185b;
        if (bVar != null) {
            bVar.b();
        }
    }

    private void x() {
        int i2 = this.S;
        if (i2 != -1) {
            TXCLog.i("TXCCaptureAndEnc", "destroy FrameBuffer: %d", Integer.valueOf(i2));
            TXCOpenGlUtils.b(this.S);
            this.S = -1;
        }
        com.tencent.liteav.basic.opengl.f fVar = this.Z;
        if (fVar != null) {
            fVar.e();
            this.Z = null;
        }
        WeakReference<m> weakReference = this.Y;
        m mVar = weakReference != null ? weakReference.get() : null;
        if (mVar != null) {
            mVar.onGLContextReadyToDestory();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void y() {
        try {
            com.tencent.liteav.videoencoder.c cVar = this.f19197n;
            this.f19197n = null;
            if (cVar != null) {
                cVar.a();
                cVar.a((com.tencent.liteav.videoencoder.f) null);
            }
        } catch (Exception e2) {
            TXCLog.e("TXCCaptureAndEnc", "stop video encoder failed.", e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void z() {
        try {
            TXCLog.i("TXCCaptureAndEnc", "stopBigVideoEncoderInGLThread");
            com.tencent.liteav.videoencoder.c cVar = this.f19193j;
            this.f19193j = null;
            if (cVar != null) {
                cVar.a();
                cVar.a((com.tencent.liteav.videoencoder.f) null);
            }
            this.U = true;
        } catch (Exception e2) {
            TXCLog.e("TXCCaptureAndEnc", "stopBigVideoEncoder failed.", e2);
        }
    }

    @Override // com.tencent.liteav.beauty.f
    public void a(byte[] bArr, int i2, int i3, int i4, long j2) {
    }

    @Override // com.tencent.liteav.videoencoder.f
    public void m(int i2) {
    }

    @Override // com.tencent.liteav.basic.b.b
    public void onNotifyEvent(int i2, Bundle bundle) {
        if (bundle != null) {
            bundle.putString("EVT_USERID", getID());
        }
        if (i2 == -2311) {
            j(false);
        }
        com.tencent.liteav.basic.util.h.a(this.ab, i2, bundle);
        if (i2 == -1314) {
            TXCEventRecorderProxy.a(getID(), 2002, 5L, -1L, "", this.F);
        } else if (i2 == 1003) {
            if (this.f19188e != null) {
                TXCEventRecorderProxy.a(getID(), 4001, this.f19188e.l() ? 0L : 1L, -1L, "", this.F);
            }
        } else if (i2 == -1308) {
            k();
        }
        if (i2 == -1301 || i2 == -1314 || i2 == -1315 || i2 == -1316) {
            TXCKeyPointReportProxy.b(30003, i2);
            if (this.f19188e != null) {
                TXCEventRecorderProxy.a(getID(), 4002, this.f19188e.l() ? 0L : 1L, i2, "", this.F);
            }
        }
    }

    @Override // com.tencent.liteav.l
    public void r() {
        TXCLog.i("TXCCaptureAndEnc", "onCaptureDestroy->enter ");
        com.tencent.liteav.beauty.d dVar = this.f19189f;
        if (dVar != null) {
            dVar.b();
        }
        com.tencent.liteav.basic.opengl.j jVar = this.P;
        if (jVar != null) {
            jVar.d();
            this.P = null;
        }
        com.tencent.liteav.basic.opengl.j jVar2 = this.Q;
        if (jVar2 != null) {
            jVar2.d();
            this.Q = null;
        }
        com.tencent.liteav.beauty.b.k kVar = this.R;
        if (kVar != null) {
            kVar.d();
            this.R = null;
        }
        z();
        y();
        x();
    }

    public void s() {
        Runnable runnable = new Runnable() { // from class: com.tencent.liteav.d.13
            @Override // java.lang.Runnable
            public void run() {
                d.this.z();
                d.this.y();
            }
        };
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.a(runnable);
            return;
        }
        synchronized (this.f19198o) {
            runnable.run();
        }
    }

    @Override // com.tencent.liteav.basic.module.a
    public void setID(String str) {
        super.setID(str);
        com.tencent.liteav.videoencoder.c cVar = this.f19193j;
        if (cVar != null) {
            cVar.setID(str);
        }
        com.tencent.liteav.videoencoder.c cVar2 = this.f19197n;
        if (cVar2 != null) {
            cVar2.setID(str);
        }
        com.tencent.liteav.beauty.d dVar = this.f19189f;
        if (dVar != null) {
            dVar.setID(str);
        }
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.a(getID());
        }
        TXCLog.w("TXCCaptureAndEnc", "setID:" + str);
    }

    public void t() {
        if (this.f19193j == null) {
            return;
        }
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.a(new Runnable() { // from class: com.tencent.liteav.d.14
                @Override // java.lang.Runnable
                public void run() {
                    d.this.z();
                }
            });
        } else {
            z();
        }
    }

    public void u() {
        if (this.f19197n == null) {
            return;
        }
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.a(new Runnable() { // from class: com.tencent.liteav.d.15
                @Override // java.lang.Runnable
                public void run() {
                    d.this.y();
                }
            });
        } else {
            y();
        }
    }

    public int d() {
        if (i()) {
            TXCLog.w("TXCCaptureAndEnc", "ignore startPush when pushing, status:" + this.f19203t);
            return -2;
        }
        TXCDRApi.initCrashReport(this.f19199p);
        this.f19203t = 1;
        TXCLog.i("TXCCaptureAndEnc", "startWithoutAudio");
        B();
        return 0;
    }

    public void e() {
        if (!i()) {
            TXCLog.w("TXCCaptureAndEnc", "ignore stopPush when not pushing, status:" + this.f19203t);
            return;
        }
        TXCLog.i("TXCCaptureAndEnc", "stop");
        this.f19203t = 0;
        s();
        this.f19200q.S = false;
        b bVar = this.f19184a;
        if (bVar != null) {
            bVar.b();
        }
        c(false);
        this.E = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0045 A[PHI: r2 r4
      0x0045: PHI (r2v5 int) = (r2v4 int), (r2v4 int), (r2v3 int) binds: [B:18:0x004e, B:19:0x0050, B:14:0x0042] A[DONT_GENERATE, DONT_INLINE]
      0x0045: PHI (r4v3 int) = (r4v1 int), (r4v1 int), (r4v0 int) binds: [B:18:0x004e, B:19:0x0050, B:14:0x0042] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void f() {
        /*
            r9 = this;
            int r0 = r9.f19203t
            java.lang.String r1 = "TXCCaptureAndEnc"
            r2 = 1
            if (r0 == r2) goto L1e
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "ignore pause push when is not pushing, status:"
            r0.append(r2)
            int r2 = r9.f19203t
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            com.tencent.liteav.basic.log.TXCLog.w(r1, r0)
            return
        L1e:
            r0 = 2
            r9.f19203t = r0
            java.lang.String r3 = "pausePusher"
            com.tencent.liteav.basic.log.TXCLog.i(r1, r3)
            com.tencent.liteav.g r1 = r9.f19200q
            int r1 = r1.G
            r1 = r1 & r2
            if (r1 != r2) goto L65
            r9.s()
            com.tencent.liteav.b r3 = r9.f19184a
            if (r3 == 0) goto L5e
            com.tencent.liteav.g r1 = r9.f19200q
            boolean r2 = r1.P
            if (r2 != 0) goto L5e
            com.tencent.liteav.videoencoder.TXSVideoEncoderParam r2 = r9.f19192i
            int r4 = r2.width
            int r2 = r2.height
            if (r4 == 0) goto L48
            if (r2 != 0) goto L45
            goto L48
        L45:
            r8 = r2
            r7 = r4
            goto L55
        L48:
            int r2 = r1.f19327a
            int r4 = r1.f19328b
            int r5 = r1.f19341o
            if (r5 == 0) goto L45
            if (r5 != r0) goto L53
            goto L45
        L53:
            r7 = r2
            r8 = r4
        L55:
            int r4 = r1.F
            int r5 = r1.E
            android.graphics.Bitmap r6 = r1.D
            r3.a(r4, r5, r6, r7, r8)
        L5e:
            com.tencent.liteav.k r0 = r9.f19188e
            if (r0 == 0) goto L65
            r0.c()
        L65:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.d.f():void");
    }

    public void g() {
        if (this.f19203t != 2) {
            TXCLog.w("TXCCaptureAndEnc", "ignore resume push when is not pause, status:" + this.f19203t);
            return;
        }
        this.f19203t = 1;
        TXCLog.i("TXCCaptureAndEnc", "resumePusher");
        g gVar = this.f19200q;
        if ((gVar.G & 1) == 1) {
            b bVar = this.f19184a;
            if (bVar != null && !gVar.P) {
                bVar.b();
            }
            s();
            k kVar = this.f19188e;
            if (kVar != null) {
                kVar.b();
            }
            A();
        }
    }

    public boolean h() {
        return this.f19204u;
    }

    public boolean i() {
        return this.f19203t != 0;
    }

    public void j() {
        k kVar = this.f19188e;
        if (kVar == null) {
            return;
        }
        kVar.a(new Runnable() { // from class: com.tencent.liteav.d.23
            @Override // java.lang.Runnable
            public void run() {
                if (d.this.f19188e != null) {
                    d.this.f19188e.b(true);
                }
                d dVar = d.this;
                dVar.c(dVar.f19192i.width, d.this.f19192i.height);
                d dVar2 = d.this;
                dVar2.a(dVar2.ac);
            }
        });
    }

    public void k() {
        if (this.f19188e == null) {
            return;
        }
        this.W.a(true);
        k(true);
    }

    public boolean l() {
        k kVar = this.f19188e;
        if (kVar != null) {
            return kVar.h();
        }
        return false;
    }

    public boolean m() {
        k kVar = this.f19188e;
        if (kVar != null) {
            return kVar.i();
        }
        return false;
    }

    public boolean n() {
        k kVar = this.f19188e;
        if (kVar != null) {
            return kVar.j();
        }
        return false;
    }

    public boolean o() {
        k kVar = this.f19188e;
        if (kVar != null) {
            return kVar.k();
        }
        return false;
    }

    public int p() {
        k kVar = this.f19188e;
        if (kVar == null) {
            return 0;
        }
        return kVar.e();
    }

    public void q() {
        try {
            com.tencent.liteav.beauty.d dVar = this.f19189f;
            if (dVar != null) {
                dVar.b();
            }
            com.tencent.liteav.basic.opengl.j jVar = this.P;
            if (jVar != null) {
                jVar.d();
                this.P = null;
            }
            com.tencent.liteav.basic.opengl.j jVar2 = this.Q;
            if (jVar2 != null) {
                jVar2.d();
                this.Q = null;
            }
            z();
            y();
            x();
        } catch (Exception e2) {
            TXCLog.e("TXCCaptureAndEnc", "stop preprocessor and encoder failed.", e2);
        }
    }

    public TXBeautyManager b() {
        return this.W;
    }

    public g c() {
        return this.f19200q;
    }

    public void h(int i2) {
        if (this.f19205v != i2) {
            TXCLog.i("TXCCaptureAndEnc", "vrotation setRenderRotation " + i2);
        }
        this.f19205v = i2;
        k kVar = this.f19188e;
        if (kVar == null) {
            return;
        }
        kVar.b(i2);
    }

    public boolean i(int i2) {
        k kVar = this.f19188e;
        if (kVar == null) {
            return false;
        }
        return kVar.a(i2);
    }

    public void b(boolean z2) {
        this.X = z2;
        TXCLog.i("TXCCaptureAndEnc", "Is encoder need texture after glFinish: %b", Boolean.valueOf(z2));
    }

    public void c(boolean z2) {
        TXCLog.i("TXCCaptureAndEnc", "enableBlackStream " + z2);
        this.f19204u = z2;
        if (z2) {
            if (this.f19188e == null) {
                v();
                return;
            }
            return;
        }
        w();
    }

    public void j(int i2) {
        this.F = i2;
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.g(i2);
        }
    }

    @Override // com.tencent.liteav.videoencoder.f
    public void l(int i2) {
        if (!this.I) {
            TXSVideoEncoderParam tXSVideoEncoderParam = this.f19192i;
            int i3 = tXSVideoEncoderParam.width;
            int i4 = tXSVideoEncoderParam.height;
            if (i3 * i4 < 518400) {
                this.f19200q.f19339m = 0;
            } else if (i3 * i4 < 921600 && this.f19190g) {
                this.f19200q.f19339m = 0;
            }
        }
        if (i2 == 3) {
            u();
            return;
        }
        if (this.I) {
            com.tencent.liteav.basic.util.h.a(this.ab, TXLiteAVCode.ERR_HEVC_ENCODE_FAIL, getID());
        } else {
            this.f19190g = true;
        }
        j(false);
        t();
    }

    public void n(final int i2) {
        if (i2 < 1) {
            i2 = 1;
        }
        if (i2 > 2) {
            i2 = 2;
        }
        Runnable runnable = new Runnable() { // from class: com.tencent.liteav.d.18
            @Override // java.lang.Runnable
            public void run() {
                if (d.this.f19192i.encoderMode == i2) {
                    return;
                }
                d.this.f19192i.encoderMode = i2;
                d.this.f19196m.encoderMode = i2;
                d.this.z();
                d.this.y();
            }
        };
        k kVar = this.f19188e;
        if (kVar == null) {
            runnable.run();
        } else {
            kVar.a(runnable);
        }
    }

    private void k(final boolean z2) {
        k kVar = this.f19188e;
        if (kVar == null) {
            return;
        }
        kVar.a(new Runnable() { // from class: com.tencent.liteav.d.7
            @Override // java.lang.Runnable
            public void run() {
                if (d.this.f19189f != null) {
                    d.this.f19189f.b();
                }
            }
        });
        s();
        this.f19188e.a(z2);
        this.f19188e = null;
        TXCLog.i("TXCCaptureAndEnc", "stopped CaptureSource");
        final TXCloudVideoView tXCloudVideoView = this.f19208y;
        this.f19187d.post(new Runnable() { // from class: com.tencent.liteav.d.8
            @Override // java.lang.Runnable
            public void run() {
                TXCloudVideoView tXCloudVideoView2 = tXCloudVideoView;
                if (tXCloudVideoView2 != null) {
                    tXCloudVideoView2.stop(z2);
                }
            }
        });
        this.f19208y = null;
        synchronized (this.f19209z) {
            this.A = null;
            com.tencent.liteav.basic.opengl.g gVar = this.D;
            if (gVar != null) {
                gVar.a();
                this.D = null;
            }
        }
        if (this.f19184a.a()) {
            this.f19184a.b();
        }
        if (this.f19204u) {
            v();
        }
    }

    public void i(boolean z2) {
        this.H = z2;
    }

    public void b(int i2) {
        TXCLog.i("TXCCaptureAndEnc", "setLocalViewMirror " + i2);
        this.ad = i2;
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.d(i2);
        }
        com.tencent.liteav.basic.opengl.g gVar = this.D;
        if (gVar != null) {
            gVar.b(this.ad);
        }
    }

    public void j(final boolean z2) {
        TXCLog.i("TXCCaptureAndEnc", "mEnableHEVCEncode = " + z2);
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.a(new Runnable() { // from class: com.tencent.liteav.d.9
                @Override // java.lang.Runnable
                public void run() {
                    d.this.I = z2;
                }
            });
            return;
        }
        synchronized (this.f19198o) {
            this.I = z2;
        }
    }

    public void a(com.tencent.liteav.basic.opengl.a aVar) {
        this.aa = aVar;
        k kVar = this.f19188e;
        if (kVar == null || !(kVar instanceof i)) {
            return;
        }
        ((i) kVar).a(aVar);
    }

    public void d(final int i2) {
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.a(new Runnable() { // from class: com.tencent.liteav.d.21
                @Override // java.lang.Runnable
                public void run() {
                    com.tencent.liteav.videoencoder.c cVar = d.this.f19193j;
                    if (i2 <= 0 || cVar == null) {
                        return;
                    }
                    g gVar = d.this.f19200q;
                    int i3 = i2;
                    gVar.f19331e = i3;
                    cVar.c(i3);
                }
            });
            return;
        }
        com.tencent.liteav.videoencoder.c cVar = this.f19193j;
        if (i2 <= 0 || cVar == null) {
            return;
        }
        this.f19200q.f19331e = i2;
        cVar.c(i2);
    }

    public void h(boolean z2) {
        this.f19191h = z2;
    }

    public void c(final int i2) {
        k kVar = this.f19188e;
        if (kVar == null) {
            return;
        }
        kVar.a(new Runnable() { // from class: com.tencent.liteav.d.20
            @Override // java.lang.Runnable
            public void run() {
                if (d.this.f19193j != null) {
                    d.this.f19193j.d(i2);
                }
                d.this.f19194k = i2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(int i2, int i3) {
        g gVar = this.f19200q;
        float f2 = gVar.M;
        if (f2 != -1.0f) {
            com.tencent.liteav.beauty.d dVar = this.f19189f;
            if (dVar != null) {
                dVar.a(gVar.H, gVar.K, gVar.L, f2);
                return;
            }
            return;
        }
        com.tencent.liteav.beauty.d dVar2 = this.f19189f;
        if (dVar2 == null || i2 == 0 || i3 == 0) {
            return;
        }
        Bitmap bitmap = gVar.H;
        float f3 = i2;
        dVar2.a(bitmap, gVar.I / f3, gVar.J / i3, bitmap == null ? 0.0f : bitmap.getWidth() / f3);
    }

    public void a(a aVar) {
        this.O = new WeakReference<>(aVar);
    }

    public void b(int i2, int i3) {
        k kVar = this.f19188e;
        if (kVar == null) {
            return;
        }
        kVar.a(i2, i3);
    }

    public void e(final int i2) {
        k kVar = this.f19188e;
        if (kVar == null) {
            return;
        }
        kVar.a(new Runnable() { // from class: com.tencent.liteav.d.22
            @Override // java.lang.Runnable
            public void run() {
                if (d.this.f19193j != null) {
                    d.this.f19193j.e(i2);
                }
            }
        });
    }

    public void g(int i2) {
        TXCLog.i("TXCCaptureAndEnc", "setRenderMode " + i2);
        this.f19206w = i2;
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.c(i2);
        }
        com.tencent.liteav.basic.opengl.g gVar = this.D;
        if (gVar != null) {
            gVar.a(this.f19206w);
        }
    }

    public void a(o oVar, int i2) {
        this.L = i2;
        if (oVar != null) {
            this.M = new WeakReference<>(oVar);
        } else {
            this.M = null;
        }
    }

    public void d(boolean z2) {
        k(z2);
    }

    private void d(int i2, int i3) {
        c(i2, i3);
    }

    public int b(int i2, int i3, int i4, Object obj, long j2) {
        com.tencent.liteav.basic.structs.b bVar = new com.tencent.liteav.basic.structs.b();
        bVar.f18652a = i2;
        bVar.f18653b = 0;
        a(bVar, i3, i4, obj, j2);
        return 0;
    }

    public boolean e(boolean z2) {
        k kVar = this.f19188e;
        if (kVar == null) {
            return false;
        }
        return kVar.d(z2);
    }

    private void d(com.tencent.liteav.basic.structs.b bVar) {
        com.tencent.liteav.beauty.b.o oVar;
        TXCloudVideoView tXCloudVideoView;
        TXCGLSurfaceView gLSurfaceView;
        WeakReference<o> weakReference = this.M;
        o oVar2 = weakReference == null ? null : weakReference.get();
        if (oVar2 == null) {
            return;
        }
        TXSVideoFrame tXSVideoFrame = new TXSVideoFrame();
        tXSVideoFrame.width = bVar.f18656e;
        tXSVideoFrame.height = bVar.f18657f;
        tXSVideoFrame.pts = TXCTimeUtil.generatePtsMS();
        int iC = c(bVar);
        int i2 = this.L;
        if (i2 == 5) {
            tXSVideoFrame.textureId = iC;
            tXSVideoFrame.eglContext = this.f19189f.a();
            if (this.S == -1) {
                int iD = TXCOpenGlUtils.d();
                this.S = iD;
                TXCLog.i("TXCCaptureAndEnc", "create FrameBuffer: %d", Integer.valueOf(iD));
            }
            TXCOpenGlUtils.a(tXSVideoFrame.textureId, this.S);
            GLES20.glBindFramebuffer(36160, this.S);
            oVar2.onRenderVideoFrame(getID(), this.F, tXSVideoFrame);
            TXCOpenGlUtils.d(this.S);
            if (this.N) {
                byte[] bArr = tXSVideoFrame.data;
                ByteBuffer byteBufferWrap = bArr != null ? ByteBuffer.wrap(bArr) : tXSVideoFrame.buffer;
                byteBufferWrap.position(0);
                bVar.f18652a = TXCOpenGlUtils.a(byteBufferWrap, bVar.f18656e, bVar.f18657f, iC);
            }
        } else if (i2 == 2) {
            tXSVideoFrame.textureId = iC;
            tXSVideoFrame.eglContext = this.f19189f.a();
            oVar2.onRenderVideoFrame(getID(), this.F, tXSVideoFrame);
            if (this.N) {
                bVar.f18652a = tXSVideoFrame.textureId;
            }
        } else if (i2 == 1 || i2 == 4) {
            if (this.P == null) {
                if (i2 == 1) {
                    oVar = new com.tencent.liteav.beauty.b.o(1);
                } else {
                    oVar = new com.tencent.liteav.beauty.b.o(3);
                }
                oVar.a(true);
                if (oVar.a()) {
                    oVar.a(bVar.f18656e, bVar.f18657f);
                    this.P = oVar;
                } else {
                    TXCLog.i("TXCCaptureAndEnc", "init filter error ");
                    this.P = null;
                }
            }
            com.tencent.liteav.basic.opengl.j jVar = this.P;
            if (jVar != null) {
                GLES20.glViewport(0, 0, bVar.f18656e, bVar.f18657f);
                jVar.a(bVar.f18656e, bVar.f18657f);
                jVar.b(iC);
                GLES20.glBindFramebuffer(36160, jVar.m());
                oVar2.onRenderVideoFrame(getID(), this.F, tXSVideoFrame);
            }
            if (this.N && (tXSVideoFrame.data != null || tXSVideoFrame.buffer != null)) {
                int i3 = this.L;
                int i4 = (i3 == 1 || i3 != 4) ? 1 : 3;
                if (this.R == null) {
                    com.tencent.liteav.beauty.b.k kVar = new com.tencent.liteav.beauty.b.k(i4);
                    kVar.a(true);
                    if (!kVar.a()) {
                        TXCLog.w("TXCCaptureAndEnc", " init i420ToRGBA filter failed");
                    }
                    kVar.a(bVar.f18656e, bVar.f18657f);
                    this.R = kVar;
                }
                com.tencent.liteav.beauty.b.k kVar2 = this.R;
                GLES20.glViewport(0, 0, bVar.f18656e, bVar.f18657f);
                kVar2.a(bVar.f18656e, bVar.f18657f);
                byte[] bArr2 = tXSVideoFrame.data;
                if (bArr2 != null) {
                    kVar2.a(bArr2);
                } else {
                    kVar2.a(tXSVideoFrame.buffer);
                }
                bVar.f18652a = kVar2.r();
            }
        }
        if (!this.N || (tXCloudVideoView = this.f19208y) == null || (gLSurfaceView = tXCloudVideoView.getGLSurfaceView()) == null) {
            return;
        }
        gLSurfaceView.d();
    }

    private void l(final boolean z2) {
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.a(new Runnable() { // from class: com.tencent.liteav.d.16
                @Override // java.lang.Runnable
                public void run() {
                    k kVar2 = d.this.f19188e;
                    if (kVar2 == null) {
                        return;
                    }
                    kVar2.f(d.this.f19200q.f19337k);
                    kVar2.e(d.this.f19200q.f19341o);
                    kVar2.a(d.this.f19200q.f19340n);
                    kVar2.b(d.this.f19200q.f19327a, d.this.f19200q.f19328b);
                    kVar2.e(d.this.f19200q.X);
                    if (z2 && kVar2.d()) {
                        kVar2.b(false);
                    }
                }
            });
        }
    }

    private int c(com.tencent.liteav.basic.structs.b bVar) {
        boolean z2;
        if (this.N) {
            return bVar.f18652a;
        }
        int i2 = bVar.f18652a;
        boolean z3 = true;
        if (this.Q == null) {
            com.tencent.liteav.basic.opengl.j jVar = new com.tencent.liteav.basic.opengl.j();
            jVar.a();
            jVar.a(true);
            jVar.a(bVar.f18656e, bVar.f18657f);
            this.Q = jVar;
        }
        com.tencent.liteav.basic.opengl.j jVar2 = this.Q;
        if (jVar2 == null) {
            return i2;
        }
        GLES20.glViewport(0, 0, bVar.f18656e, bVar.f18657f);
        boolean z4 = bVar.f18660i;
        k kVar = this.f19188e;
        boolean zL = kVar != null ? kVar.l() : false;
        int i3 = this.ad;
        if (i3 == 1) {
            if (!zL) {
                z2 = bVar.f18660i;
                z4 = !z2;
            }
        } else if (i3 == 2 && zL) {
            z2 = bVar.f18660i;
            z4 = !z2;
        }
        int i4 = bVar.f18656e;
        int i5 = bVar.f18657f;
        float[] fArrA = jVar2.a(i4, i5, null, com.tencent.liteav.basic.util.h.a(i4, i5, i4, i5), 0);
        int i6 = (720 - this.f19205v) % 360;
        if (i6 != 90 && i6 != 270) {
            z3 = false;
        }
        int i7 = z3 ? bVar.f18657f : bVar.f18656e;
        int i8 = z3 ? bVar.f18656e : bVar.f18657f;
        jVar2.a(bVar.f18656e, bVar.f18657f);
        float f2 = i7 / i8;
        boolean z5 = z3 ? false : z4;
        if (!z3) {
            z4 = false;
        }
        jVar2.a(i4, i5, i6, fArrA, f2, z5, z4);
        return jVar2.b(i2);
    }

    private void e(int i2, int i3) {
        if (this.f19207x) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("EVT_USERID", getID());
        bundle.putInt("EVT_ID", 2003);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
        bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, "Renders the first video frame");
        bundle.putInt("EVT_PARAM1", i2);
        bundle.putInt("EVT_PARAM2", i3);
        com.tencent.liteav.basic.util.h.a(this.ab, 2003, bundle);
        TXCLog.i("TXCCaptureAndEnc", "trtc_render render first frame " + getID() + ", " + this.F);
        this.f19207x = true;
    }

    public void a(boolean z2) {
        this.N = z2;
    }

    public void f(int i2) {
        g gVar = this.f19200q;
        if (gVar.f19337k == i2) {
            return;
        }
        gVar.f19337k = i2;
        k kVar = this.f19188e;
        if (kVar != null && kVar.g() < i2) {
            int i3 = this.f19201r;
            if (i3 == 0) {
                l(true);
                s();
                return;
            } else {
                if (i3 != 1) {
                    return;
                }
                this.f19188e.f(i2);
                return;
            }
        }
        com.tencent.liteav.videoencoder.c cVar = this.f19193j;
        if (cVar != null) {
            cVar.b(this.f19200q.f19337k);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00de  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(com.tencent.liteav.g r9) {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.d.a(com.tencent.liteav.g):void");
    }

    @Override // com.tencent.liteav.l
    public void b(com.tencent.liteav.basic.structs.b bVar) {
        this.f19186c.a();
        if (!this.T) {
            this.T = true;
            TXCKeyPointReportProxy.b(30003, 0);
        }
        if (this.f19203t == 2) {
            return;
        }
        k kVar = this.f19188e;
        if (this.f19189f == null || this.f19200q.P || kVar == null) {
            return;
        }
        TXSVideoEncoderParam tXSVideoEncoderParam = this.f19192i;
        int i2 = tXSVideoEncoderParam.height;
        int i3 = bVar.f18659h;
        if (i2 != i3 || tXSVideoEncoderParam.width != bVar.f18658g) {
            d(bVar.f18658g, i3);
        }
        this.f19189f.a(kVar.f());
        this.f19189f.a(this.f19200q.f19341o);
        this.f19189f.a(bVar, bVar.f18653b, 0, 0L);
    }

    public void g(boolean z2) {
        if (this.I) {
            TXCLog.i("TXCCaptureAndEnc", "enableRPS when mEnableHEVCEncode = true");
            return;
        }
        if (this.G == z2) {
            return;
        }
        this.G = z2;
        TXCLog.i("TXCCaptureAndEnc", "trtc_api onVideoConfigChanged enableRps " + this.G);
        if (this.G) {
            this.f19200q.f19339m = 0;
        }
        s();
    }

    public boolean f(boolean z2) {
        this.f19200q.V = z2;
        k kVar = this.f19188e;
        if (kVar == null) {
            return false;
        }
        kVar.c(z2);
        return true;
    }

    public void k(int i2) {
        com.tencent.liteav.videoencoder.c cVar;
        if (i2 == 2) {
            cVar = this.f19193j;
        } else {
            cVar = i2 == 3 ? this.f19197n : null;
        }
        if (cVar != null) {
            cVar.b();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(int r12, int r13, java.lang.Object r14) {
        /*
            r11 = this;
            com.tencent.liteav.g r0 = r11.f19200q
            int r1 = r0.f19339m
            r2 = 1
            r3 = 2
            if (r1 == 0) goto L10
            if (r1 == r2) goto Lf
            if (r1 == r3) goto Ld
            goto L10
        Ld:
            r3 = 3
            goto L10
        Lf:
            r3 = r2
        L10:
            int r1 = r11.f19201r
            if (r1 != r2) goto L19
            int r1 = r11.f19202s
            if (r1 != 0) goto L19
            goto L1a
        L19:
            r2 = r3
        L1a:
            int r0 = r0.f19338l
            com.tencent.liteav.videoencoder.c r1 = r11.f19193j
            if (r1 == 0) goto L3e
            boolean r1 = r11.U
            if (r1 != 0) goto L3e
            com.tencent.liteav.videoencoder.TXSVideoEncoderParam r1 = r11.f19192i
            int r3 = r1.width
            if (r3 != r12) goto L3e
            int r3 = r1.height
            if (r3 != r13) goto L3e
            int r3 = r1.encodeType
            if (r3 != r2) goto L3e
            int r1 = r1.gop
            if (r1 != r0) goto L3e
            boolean r0 = r11.I
            com.tencent.liteav.videoencoder.TXSVideoEncoderParam r1 = r11.f19192i
            boolean r1 = r1.isH265EncoderEnabled
            if (r0 == r1) goto L4c
        L3e:
            com.tencent.liteav.g r0 = r11.f19200q
            int r9 = r0.f19337k
            boolean r10 = r0.S
            r4 = r11
            r5 = r12
            r6 = r13
            r7 = r2
            r8 = r14
            r4.a(r5, r6, r7, r8, r9, r10)
        L4c:
            com.tencent.liteav.videoencoder.c r12 = r11.f19197n
            if (r12 == 0) goto L56
            com.tencent.liteav.videoencoder.TXSVideoEncoderParam r12 = r11.f19196m
            int r12 = r12.encodeType
            if (r12 == r2) goto L61
        L56:
            boolean r12 = r11.f19195l
            if (r12 == 0) goto L61
            com.tencent.liteav.g r12 = r11.f19200q
            boolean r12 = r12.S
            r11.a(r14, r2, r12)
        L61:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.d.b(int, int, java.lang.Object):void");
    }

    public void a(com.tencent.liteav.basic.b.b bVar) {
        this.ab = new WeakReference<>(bVar);
    }

    public void a(m mVar) {
        this.Y = new WeakReference<>(mVar);
    }

    public void a(final int i2) {
        TXCLog.i("TXCCaptureAndEnc", "vrotation setVideoEncRotation " + i2);
        this.ac = i2;
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.a(new Runnable() { // from class: com.tencent.liteav.d.1
                @Override // java.lang.Runnable
                public void run() {
                    if (d.this.f19193j != null) {
                        d.this.f19193j.a(i2);
                    }
                    if (d.this.f19197n != null) {
                        d.this.f19197n.a(i2);
                    }
                }
            });
            return;
        }
        com.tencent.liteav.videoencoder.c cVar = this.f19193j;
        if (cVar != null) {
            cVar.a(i2);
        }
        com.tencent.liteav.videoencoder.c cVar2 = this.f19197n;
        if (cVar2 != null) {
            cVar2.a(i2);
        }
    }

    public void a(int i2, final int i3, final int i4, final int i5, final int i6, int i7, int i8, final boolean z2) {
        int i9;
        k kVar;
        if (i2 != 2 && i2 != 7) {
            TXSVideoEncoderParam tXSVideoEncoderParam = this.f19196m;
            if (tXSVideoEncoderParam != null && this.f19200q.f19345s && ((i3 != tXSVideoEncoderParam.width || i4 != tXSVideoEncoderParam.height) && (kVar = this.f19188e) != null)) {
                kVar.a(new Runnable() { // from class: com.tencent.liteav.d.19
                    @Override // java.lang.Runnable
                    public void run() {
                        TXCLog.w("TXCCaptureAndEnc", String.format("QOS restart big encoder old resolution %dx%d fps %d, new resolution %dx%d fps %d", Integer.valueOf(d.this.f19196m.width), Integer.valueOf(d.this.f19196m.height), Integer.valueOf(d.this.f19196m.fps), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)));
                        d.this.f19196m.width = i3;
                        d.this.f19196m.height = i4;
                        d.this.y();
                    }
                });
            }
            com.tencent.liteav.videoencoder.c cVar = this.f19197n;
            if (cVar != null) {
                cVar.b(i6, i7);
                cVar.b(i5);
                return;
            }
            return;
        }
        boolean z3 = this.f19200q.f19345s;
        if (!z3) {
            i3 = this.f19192i.width;
        }
        final int i10 = i3;
        if (!z3) {
            i4 = this.f19192i.height;
        }
        final int i11 = i4;
        if (i10 <= 0 || i11 <= 0) {
            return;
        }
        TXSVideoEncoderParam tXSVideoEncoderParam2 = this.f19192i;
        int i12 = tXSVideoEncoderParam2.width;
        if (!((i12 == 0 || (i9 = tXSVideoEncoderParam2.height) == 0 || (i10 == i12 && i11 == i9)) ? false : true) && i5 <= tXSVideoEncoderParam2.fps && z2 == this.I) {
            com.tencent.liteav.videoencoder.c cVar2 = this.f19193j;
            if (cVar2 != null) {
                cVar2.b(i6, i7);
                cVar2.b(i5);
            }
        } else {
            k kVar2 = this.f19188e;
            if (kVar2 != null) {
                kVar2.a(new Runnable() { // from class: com.tencent.liteav.d.12
                    @Override // java.lang.Runnable
                    public void run() {
                        boolean z4 = (i10 == d.this.f19192i.width && i11 == d.this.f19192i.height && i5 <= d.this.f19192i.fps) ? false : true;
                        if (!z2 && d.this.I) {
                            TXCLog.i("TXCCaptureAndEnc", "disable h265 encoder from QoS. prepare to restart.");
                            d.this.I = false;
                            z4 = true;
                        }
                        if (z4) {
                            TXCLog.i("TXCCaptureAndEnc", "restart encoder when QoS changed.");
                            int i13 = i10;
                            int i14 = i11;
                            if (i13 > i14) {
                                d.this.f19200q.f19341o = 0;
                            } else if (i13 < i14) {
                                d.this.f19200q.f19341o = 1;
                            }
                            d.this.f19200q.f19327a = Math.min(i10, i11);
                            d.this.f19200q.f19328b = Math.max(i10, i11);
                            k kVar3 = d.this.f19188e;
                            if (kVar3 != null) {
                                kVar3.a(com.tencent.liteav.basic.enums.c.RESOLUTION_TYPE_INVALID);
                                kVar3.b(d.this.f19200q.f19327a, d.this.f19200q.f19328b);
                                kVar3.e(d.this.f19200q.f19341o);
                            }
                            d.this.f19200q.f19331e = i6;
                            d.this.f19200q.f19337k = i5;
                            d.this.z();
                            TXCLog.e("TXCCaptureAndEnc", String.format("QOS restart big encoder old resolution %dx%d fps %d, new resolution %dx%d fps %d", Integer.valueOf(d.this.f19192i.width), Integer.valueOf(d.this.f19192i.height), Integer.valueOf(d.this.f19192i.fps), Integer.valueOf(i10), Integer.valueOf(i11), Integer.valueOf(i5)));
                        }
                    }
                });
            } else {
                if (this.I != z2) {
                    synchronized (this.f19198o) {
                        if (!z2) {
                            if (this.I) {
                                this.I = false;
                            }
                        }
                    }
                }
                com.tencent.liteav.videoencoder.c cVar3 = this.f19193j;
                if (cVar3 != null) {
                    cVar3.b(i6, i7);
                    cVar3.b(i5);
                }
            }
        }
        e(i8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void a(final TXCloudVideoView tXCloudVideoView) {
        com.tencent.liteav.basic.opengl.h hVar;
        if (this.f19200q.P) {
            TXCLog.e("TXCCaptureAndEnc", "enable pure audio push , so can not start preview!");
            return;
        }
        b bVar = this.f19184a;
        if (bVar != null) {
            bVar.b();
        }
        this.f19207x = false;
        boolean z2 = this.f19200q.Z;
        if (tXCloudVideoView != null) {
            final TXCGLSurfaceView[] tXCGLSurfaceViewArr = new TXCGLSurfaceView[1];
            a(new Runnable() { // from class: com.tencent.liteav.d.24
                @Override // java.lang.Runnable
                public void run() {
                    tXCGLSurfaceViewArr[0] = new TXCGLSurfaceView(tXCloudVideoView.getContext());
                    tXCloudVideoView.addVideoView(tXCGLSurfaceViewArr[0]);
                }
            });
            TXCGLSurfaceView tXCGLSurfaceView = tXCGLSurfaceViewArr[0];
            tXCGLSurfaceView.setNotifyListener(this);
            TXCLog.i("TXCCaptureAndEnc", "start camera preview with GLSurfaceView");
            hVar = tXCGLSurfaceView;
        } else {
            com.tencent.liteav.basic.opengl.h hVar2 = new com.tencent.liteav.basic.opengl.h();
            TXCLog.i("TXCCaptureAndEnc", "start camera preview with SurfaceTexture");
            z2 = false;
            hVar = hVar2;
        }
        this.f19201r = 0;
        this.f19188e = new c(this.f19199p, this.f19200q, hVar, z2);
        w();
        j(this.F);
        this.f19188e.a(getID());
        this.f19188e.a((l) this);
        this.f19188e.a((com.tencent.liteav.basic.b.b) this);
        this.f19188e.a();
        this.f19188e.b(this.f19205v);
        this.f19188e.c(this.f19206w);
        this.f19188e.d(this.ad);
        this.f19208y = tXCloudVideoView;
        this.f19187d.post(new Runnable() { // from class: com.tencent.liteav.d.2
            @Override // java.lang.Runnable
            public void run() {
                TXCloudVideoView tXCloudVideoView2 = tXCloudVideoView;
                if (tXCloudVideoView2 != null) {
                    tXCloudVideoView2.start(d.this.f19200q.N, d.this.f19200q.O, d.this.f19188e);
                }
            }
        });
        this.f19207x = false;
        TXCKeyPointReportProxy.a(30003);
    }

    public int a(boolean z2, int i2, int i3, int i4, int i5, int i6) {
        TXSVideoEncoderParam tXSVideoEncoderParam = this.f19196m;
        boolean z3 = (tXSVideoEncoderParam.width == i2 && tXSVideoEncoderParam.height == i3) ? false : true;
        tXSVideoEncoderParam.width = i2;
        tXSVideoEncoderParam.height = i3;
        tXSVideoEncoderParam.fps = i4;
        tXSVideoEncoderParam.gop = i6;
        tXSVideoEncoderParam.encoderProfile = 1;
        tXSVideoEncoderParam.realTime = this.f19200q.S;
        tXSVideoEncoderParam.streamType = 3;
        tXSVideoEncoderParam.bitrate = i5;
        tXSVideoEncoderParam.annexb = true;
        tXSVideoEncoderParam.bMultiRef = false;
        if (this.f19197n != null && (z3 || (this.f19195l && !z2))) {
            k kVar = this.f19188e;
            if (kVar != null) {
                kVar.a(new Runnable() { // from class: com.tencent.liteav.d.3
                    @Override // java.lang.Runnable
                    public void run() {
                        d.this.y();
                    }
                });
            } else {
                y();
            }
        }
        this.f19195l = z2;
        return 0;
    }

    public void a(Surface surface) {
        if (this.f19208y != null) {
            TXCLog.w("TXCCaptureAndEnc", "camera preview view is not null, can't set surface");
            return;
        }
        synchronized (this.f19209z) {
            if (this.A != surface) {
                TXCLog.i("TXCCaptureAndEnc", "surface-render: set surface " + surface);
                this.A = surface;
                com.tencent.liteav.basic.opengl.g gVar = this.D;
                if (gVar != null) {
                    gVar.a();
                    this.D = null;
                }
            } else {
                TXCLog.i("TXCCaptureAndEnc", "surface-render: set surface the same" + surface);
            }
        }
    }

    public void a(final int i2, final int i3) {
        synchronized (this.f19209z) {
            com.tencent.liteav.basic.opengl.g gVar = this.D;
            if (gVar != null) {
                gVar.a(new Runnable() { // from class: com.tencent.liteav.d.4
                    @Override // java.lang.Runnable
                    public void run() {
                        d.this.B = i2;
                        d.this.C = i3;
                        if (d.this.E == null || d.this.D == null) {
                            return;
                        }
                        d dVar = d.this;
                        dVar.a(dVar.E, true);
                    }
                });
            } else {
                this.B = i2;
                this.C = i3;
            }
        }
    }

    public void a(final com.tencent.liteav.basic.opengl.p pVar) {
        TXCloudVideoView tXCloudVideoView = this.f19208y;
        if (tXCloudVideoView != null) {
            TXCGLSurfaceView gLSurfaceView = tXCloudVideoView.getGLSurfaceView();
            if (gLSurfaceView != null) {
                gLSurfaceView.a(new com.tencent.liteav.basic.opengl.p() { // from class: com.tencent.liteav.d.5
                    @Override // com.tencent.liteav.basic.opengl.p
                    public void onTakePhotoComplete(Bitmap bitmap) {
                        com.tencent.liteav.basic.opengl.p pVar2 = pVar;
                        if (pVar2 != null) {
                            pVar2.onTakePhotoComplete(bitmap);
                        }
                    }
                });
                return;
            } else {
                if (pVar != null) {
                    pVar.onTakePhotoComplete(null);
                    return;
                }
                return;
            }
        }
        com.tencent.liteav.basic.opengl.g gVar = this.D;
        if (gVar != null) {
            gVar.a(new com.tencent.liteav.basic.opengl.p() { // from class: com.tencent.liteav.d.6
                @Override // com.tencent.liteav.basic.opengl.p
                public void onTakePhotoComplete(Bitmap bitmap) {
                    com.tencent.liteav.basic.opengl.p pVar2 = pVar;
                    if (pVar2 != null) {
                        pVar2.onTakePhotoComplete(bitmap);
                    }
                }
            });
        } else if (pVar != null) {
            pVar.onTakePhotoComplete(null);
        }
    }

    public void a(a.InterfaceC0337a interfaceC0337a) {
        if (TXCBuild.VersionInt() < 21) {
            Bundle bundle = new Bundle();
            bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "Screen recording failed, unsupported Android system version. system version should above 5.0");
            onNotifyEvent(-1309, bundle);
            TXLog.e("TXCCaptureAndEnc", "Screen capture need running on Android Lollipop or higher version, current:" + TXCBuild.VersionInt());
            return;
        }
        this.f19201r = 1;
        if (this.f19188e == null) {
            this.f19188e = new i(this.f19199p, this.f19200q, interfaceC0337a);
            TXCLog.i("TXCCaptureAndEnc", "create TXCScreenCaptureSource");
        }
        this.W.a(false);
        j(this.F);
        ((i) this.f19188e).a(this.aa);
        this.f19188e.a((com.tencent.liteav.basic.b.b) this);
        this.f19188e.a((l) this);
        this.f19188e.a();
        this.f19188e.a(getID());
        TXCDRApi.txReportDAU(this.f19199p, com.tencent.liteav.basic.datareport.a.aH);
    }

    public void a(Bitmap bitmap, float f2, float f3, float f4) {
        g gVar = this.f19200q;
        gVar.H = bitmap;
        gVar.K = f2;
        gVar.L = f3;
        gVar.M = f4;
        A();
    }

    private void a(int i2, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("EVT_USERID", getID());
        bundle.putInt("EVT_ID", i2);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
        if (str != null) {
            bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str);
        }
        com.tencent.liteav.basic.util.h.a(this.ab, i2, bundle);
        if (i2 == -1314) {
            TXCEventRecorderProxy.a(getID(), 2002, 5L, -1L, "", this.F);
        } else if (i2 == 1003 && this.f19188e != null) {
            TXCEventRecorderProxy.a(getID(), 4001, this.f19188e.l() ? 0L : 1L, -1L, "", this.F);
        }
        if (i2 == -1301 || i2 == -1314 || i2 == -1315 || i2 == -1316) {
            TXCKeyPointReportProxy.b(30003, i2);
            if (this.f19188e != null) {
                TXCEventRecorderProxy.a(getID(), 4002, this.f19188e.l() ? 0L : 1L, i2, "", this.F);
            }
        }
    }

    public int a(int i2, int i3, int i4, Object obj, long j2) {
        b bVar;
        synchronized (this.f19198o) {
            if (this.f19203t != 2 && ((bVar = this.f19185b) == null || !bVar.a())) {
                int iA = a(i3, i4, obj);
                if (iA != 0) {
                    return iA;
                }
                long jGeneratePtsMS = j2 == 0 ? TXCTimeUtil.generatePtsMS() : j2;
                com.tencent.liteav.videoencoder.c cVar = this.f19193j;
                if (cVar != null) {
                    cVar.a(this.f19200q.V);
                    cVar.a(i2, i3, i4, jGeneratePtsMS);
                }
                com.tencent.liteav.videoencoder.c cVar2 = this.f19197n;
                if (cVar2 != null) {
                    cVar2.a(this.f19200q.V);
                    cVar2.a(i2, i3, i4, jGeneratePtsMS);
                }
                return 0;
            }
            return 0;
        }
    }

    private void a(com.tencent.liteav.basic.structs.b bVar, int i2, int i3, Object obj, long j2) {
        b bVar2;
        synchronized (this.f19198o) {
            if (this.f19203t != 2 && ((bVar2 = this.f19185b) == null || !bVar2.a())) {
                bVar.f18656e = i2;
                bVar.f18657f = i3;
                g gVar = this.f19200q;
                bVar.f18660i = gVar.V;
                if (gVar.f19341o == 0) {
                    bVar.f18658g = gVar.f19328b;
                    bVar.f18659h = gVar.f19327a;
                } else {
                    bVar.f18658g = gVar.f19327a;
                    bVar.f18659h = gVar.f19328b;
                }
                bVar.f18663l = com.tencent.liteav.basic.util.h.a(i2, i3, bVar.f18658g, bVar.f18659h);
                try {
                    this.f19189f.a(this.f19200q.f19341o);
                    this.f19189f.a(obj);
                    this.f19189f.a(bVar, bVar.f18653b, 0, j2);
                } catch (Exception e2) {
                    TXCLog.e("TXCCaptureAndEnc", "send custom video frame failed." + e2.getMessage());
                }
            }
        }
    }

    @Override // com.tencent.liteav.beauty.f
    public int a(com.tencent.liteav.basic.structs.b bVar) {
        WeakReference<m> weakReference = this.Y;
        m mVar = weakReference != null ? weakReference.get() : null;
        if (mVar != null) {
            com.tencent.liteav.basic.opengl.f fVar = this.Z;
            if (fVar == null || fVar.c() != bVar.f18656e || this.Z.d() != bVar.f18657f) {
                com.tencent.liteav.basic.opengl.f fVar2 = this.Z;
                if (fVar2 != null) {
                    fVar2.e();
                }
                com.tencent.liteav.basic.opengl.f fVar3 = new com.tencent.liteav.basic.opengl.f(bVar.f18656e, bVar.f18657f);
                this.Z = fVar3;
                fVar3.a();
            }
            bVar.f18652a = mVar.onProcessVideoFrame(bVar.f18652a, bVar.f18656e, bVar.f18657f, this.Z.b());
            GLES20.glDisable(R2.attr.roundWidth);
            GLES20.glDisable(R2.attr.quantizeMotionPhase);
        }
        d(bVar);
        a(bVar, false);
        return bVar.f18652a;
    }

    @Override // com.tencent.liteav.beauty.f
    public void a(com.tencent.liteav.basic.structs.b bVar, long j2) {
        a(bVar.f18652a, bVar.f18656e, bVar.f18657f, j2);
    }

    @Override // com.tencent.liteav.videoencoder.f
    public void a(TXSNALPacket tXSNALPacket, int i2) {
        a aVar;
        if (i2 == 0) {
            if (tXSNALPacket.streamType == 2) {
                this.K = tXSNALPacket.gopIndex;
                this.J = tXSNALPacket.frameIndex;
            }
            WeakReference<a> weakReference = this.O;
            if (weakReference == null || (aVar = weakReference.get()) == null) {
                return;
            }
            aVar.onEncVideo(tXSNALPacket);
            return;
        }
        if ((i2 == 10000004 || i2 == 10000005 || i2 == 10000006) && this.f19192i.encodeType == 1) {
            TXCLog.i("TXCCaptureAndEnc", "onEncodeNal mEnableHEVCEncode " + this.I + " errCode= " + i2);
            if (this.I) {
                Monitor.a(2, String.format(Locale.getDefault(), "VideoEncoder: h265 hardware encoder error %d, switch to 264 encoder. %s, %d", Integer.valueOf(i2), TXCCommonUtil.getDeviceInfo(), Integer.valueOf(com.tencent.liteav.videoencoder.d.a(R2.attr.iconTint, R2.attr.color_hot_circle_one_end, 20) ? 1 : 0)), "", 0);
                k kVar = this.f19188e;
                if (kVar != null) {
                    kVar.a(new Runnable() { // from class: com.tencent.liteav.d.10
                        @Override // java.lang.Runnable
                        public void run() {
                            d.this.I = false;
                            d.this.z();
                            d.this.y();
                        }
                    });
                } else {
                    synchronized (this.f19198o) {
                        this.I = false;
                        z();
                        y();
                    }
                }
                com.tencent.liteav.basic.util.h.a(this.ab, TXLiteAVCode.ERR_HEVC_ENCODE_FAIL, getID());
                return;
            }
            Monitor.a(2, String.format("VideoEncoder: hardware encoder error %d, switch to software encoder", Integer.valueOf(i2)), "", 0);
            s();
            this.f19202s++;
            this.f19200q.f19339m = 0;
            a(1103, "Failed to enable hardware encoder, use software encoder");
        }
    }

    @Override // com.tencent.liteav.videoencoder.f
    public void a(MediaFormat mediaFormat) {
        a aVar;
        WeakReference<a> weakReference = this.O;
        if (weakReference == null || (aVar = weakReference.get()) == null) {
            return;
        }
        aVar.onEncVideoFormat(mediaFormat);
    }

    @Override // com.tencent.liteav.videoencoder.f
    public void a(int i2, long j2, long j3) {
        if (i2 == 2) {
            this.K = j2;
            this.J = j3;
        }
    }

    @Override // com.tencent.liteav.b.InterfaceC0327b
    public void a(final Bitmap bitmap, final ByteBuffer byteBuffer, final int i2, final int i3) {
        Runnable runnable = new Runnable() { // from class: com.tencent.liteav.d.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    if ((d.this.f19203t == 2 || d.this.f19204u) && bitmap != null && byteBuffer != null) {
                        boolean z2 = false;
                        if (!d.this.f19192i.isH265EncoderEnabled && d.this.f19192i.encodeType != 2) {
                            z2 = true;
                        }
                        if (d.this.f19193j == null || d.this.U || d.this.f19192i.width != i2 || d.this.f19192i.height != i3 || z2 || d.this.f19192i.gop != d.this.f19200q.f19338l || d.this.I != d.this.f19192i.isH265EncoderEnabled) {
                            if (d.this.f19192i.isH265EncoderEnabled) {
                                d dVar = d.this;
                                dVar.a(i2, i3, dVar.f19192i.encodeType, (Object) null, d.this.f19200q.F, true);
                            } else {
                                d dVar2 = d.this;
                                dVar2.a(i2, i3, 2, (Object) null, dVar2.f19200q.F, true);
                            }
                        }
                        if ((d.this.f19197n == null || d.this.f19196m.encodeType != 2) && d.this.f19195l) {
                            d.this.a((Object) null, 2, true);
                        }
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        com.tencent.liteav.videoencoder.c cVar = d.this.f19193j;
                        if (cVar != null) {
                            cVar.a(byteBuffer.array(), 2, width, height, TXCTimeUtil.generatePtsMS());
                        }
                        com.tencent.liteav.videoencoder.c cVar2 = d.this.f19197n;
                        if (cVar2 != null) {
                            cVar2.a(byteBuffer.array(), 2, width, height, TXCTimeUtil.generatePtsMS());
                        }
                    }
                } catch (Exception e2) {
                    TXCLog.e("TXCCaptureAndEnc", "onPushBitmap failed." + e2.getMessage());
                }
            }
        };
        k kVar = this.f19188e;
        if (kVar != null) {
            kVar.a(runnable);
        } else {
            runnable.run();
        }
    }

    @Override // com.tencent.liteav.b.InterfaceC0327b
    public void a() {
        a aVar;
        TXCLog.i("TXCCaptureAndEnc", "onPushEnd");
        WeakReference<a> weakReference = this.O;
        if (weakReference == null || (aVar = weakReference.get()) == null) {
            return;
        }
        aVar.onBackgroudPushStop();
    }

    @Override // com.tencent.liteav.l
    public void a(SurfaceTexture surfaceTexture) {
        com.tencent.liteav.beauty.d dVar = this.f19189f;
        if (dVar != null) {
            dVar.b();
        }
        WeakReference<m> weakReference = this.Y;
        m mVar = weakReference != null ? weakReference.get() : null;
        if (mVar != null) {
            mVar.onGLContextCreated();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, int i3, int i4, Object obj, int i5, boolean z2) {
        TXCLog.i("TXCCaptureAndEnc", "New encode size width = " + i2 + " height = " + i3 + " encType = " + i4 + " eglContext: " + obj);
        z();
        com.tencent.liteav.videoencoder.c cVar = new com.tencent.liteav.videoencoder.c(i4);
        TXCStatus.a(getID(), 4005, this.F, Integer.valueOf(i4));
        if (i4 == 1) {
            TXCEventRecorderProxy.a(getID(), 4004, 1L, -1L, "", this.F);
        } else {
            TXCEventRecorderProxy.a(getID(), 4004, 0L, -1L, "", this.F);
        }
        this.U = false;
        TXSVideoEncoderParam tXSVideoEncoderParam = this.f19192i;
        tXSVideoEncoderParam.encodeType = i4;
        tXSVideoEncoderParam.width = i2;
        tXSVideoEncoderParam.height = i3;
        tXSVideoEncoderParam.fps = i5;
        g gVar = this.f19200q;
        tXSVideoEncoderParam.gop = gVar.f19338l;
        tXSVideoEncoderParam.encoderProfile = gVar.f19343q;
        tXSVideoEncoderParam.glContext = obj != null ? obj : cVar.a(i2, i3);
        TXSVideoEncoderParam tXSVideoEncoderParam2 = this.f19192i;
        tXSVideoEncoderParam2.realTime = z2;
        tXSVideoEncoderParam2.streamType = this.F;
        tXSVideoEncoderParam2.annexb = this.H;
        tXSVideoEncoderParam2.bMultiRef = this.G;
        tXSVideoEncoderParam2.baseFrameIndex = this.J + 20;
        tXSVideoEncoderParam2.baseGopIndex = ((this.K + 1) % 255) + 1;
        tXSVideoEncoderParam2.bLimitFps = this.f19191h;
        tXSVideoEncoderParam2.record = this.V;
        tXSVideoEncoderParam2.encFmt = this.f19200q.ab;
        tXSVideoEncoderParam2.isH265EncoderEnabled = this.I;
        TXSVideoEncoderParam tXSVideoEncoderParam3 = this.f19192i;
        tXSVideoEncoderParam3.bitrate = this.f19200q.f19331e;
        tXSVideoEncoderParam3.usageType = this.f19201r != 1 ? 0 : 1;
        cVar.a((com.tencent.liteav.videoencoder.f) this);
        cVar.a((com.tencent.liteav.basic.b.b) this);
        cVar.a(this.f19192i);
        cVar.c(this.f19200q.f19331e);
        cVar.d(this.f19194k);
        cVar.setID(getID());
        cVar.a(this.ac);
        this.f19193j = cVar;
        String id = getID();
        int i6 = this.F;
        TXSVideoEncoderParam tXSVideoEncoderParam4 = this.f19192i;
        TXCStatus.a(id, 4003, i6, Integer.valueOf(tXSVideoEncoderParam4.height | (tXSVideoEncoderParam4.width << 16)));
        TXCStatus.a(getID(), R2.drawable.umeng_socialize_button_login_normal, this.F, Integer.valueOf(this.f19192i.gop * 1000));
        String id2 = getID();
        TXSVideoEncoderParam tXSVideoEncoderParam5 = this.f19192i;
        TXCEventRecorderProxy.a(id2, 4003, tXSVideoEncoderParam5.width, tXSVideoEncoderParam5.height, "", this.F);
        TXCKeyPointReportProxy.a(40036, this.f19192i.encodeType, this.F);
        TXSVideoEncoderParam tXSVideoEncoderParam6 = this.f19192i;
        TXCKeyPointReportProxy.a(40037, tXSVideoEncoderParam6.height | (tXSVideoEncoderParam6.width << 16), this.F);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj, int i2, boolean z2) {
        y();
        com.tencent.liteav.videoencoder.c cVar = new com.tencent.liteav.videoencoder.c(i2);
        TXCStatus.a(getID(), 4005, 3, Integer.valueOf(i2));
        if (i2 == 1) {
            TXCEventRecorderProxy.a(getID(), 4004, 1L, -1L, "", 3);
        } else {
            TXCEventRecorderProxy.a(getID(), 4004, 0L, -1L, "", 3);
        }
        TXSVideoEncoderParam tXSVideoEncoderParam = this.f19196m;
        tXSVideoEncoderParam.glContext = obj != null ? obj : cVar.a(tXSVideoEncoderParam.width, tXSVideoEncoderParam.height);
        TXSVideoEncoderParam tXSVideoEncoderParam2 = this.f19196m;
        tXSVideoEncoderParam2.encodeType = i2;
        tXSVideoEncoderParam2.realTime = z2;
        tXSVideoEncoderParam2.isH265EncoderEnabled = false;
        TXCLog.i("TXCCaptureAndEnc", "start small video encoder");
        cVar.a((com.tencent.liteav.videoencoder.f) this);
        cVar.a((com.tencent.liteav.basic.b.b) this);
        cVar.a(this.f19196m);
        cVar.c(this.f19196m.bitrate);
        cVar.setID(getID());
        cVar.a(this.ac);
        this.f19197n = cVar;
        String id = getID();
        TXSVideoEncoderParam tXSVideoEncoderParam3 = this.f19196m;
        TXCStatus.a(id, 4003, 3, Integer.valueOf(tXSVideoEncoderParam3.height | (tXSVideoEncoderParam3.width << 16)));
        TXCStatus.a(getID(), R2.drawable.umeng_socialize_button_login_normal, 3, Integer.valueOf(this.f19196m.gop * 1000));
    }

    private void a(int i2, int i3, int i4, long j2) {
        if (j2 == 0) {
            j2 = TXCTimeUtil.generatePtsMS();
        }
        b(i3, i4, this.f19189f.a());
        com.tencent.liteav.videoencoder.c cVar = this.f19193j;
        if (cVar != null) {
            cVar.b(this.X);
            cVar.a(i2, i3, i4, j2);
        }
        com.tencent.liteav.videoencoder.c cVar2 = this.f19197n;
        if (cVar2 != null) {
            cVar2.b(this.X);
            cVar2.a(i2, i3, i4, j2);
        }
    }

    private int a(int i2, int i3, Object obj) {
        g gVar = this.f19200q;
        int i4 = gVar.f19327a;
        int i5 = gVar.f19328b;
        int i6 = gVar.f19341o;
        if (i6 == 0 || i6 == 2) {
            i5 = i4;
            i4 = i5;
        }
        if (i4 > 0 && i5 > 0) {
            if (gVar.P) {
                z();
                return -1000;
            }
            b(i4, i5, obj);
            return 0;
        }
        TXCLog.e("TXCCaptureAndEnc", "sendCustomYUVData: invalid video encode resolution");
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.tencent.liteav.basic.structs.b bVar, boolean z2) {
        k kVar;
        k kVar2;
        e(bVar.f18656e, bVar.f18657f);
        this.E = bVar;
        if (this.f19208y != null) {
            k kVar3 = this.f19188e;
            if (kVar3 != null) {
                kVar3.a(bVar);
                return;
            }
            return;
        }
        synchronized (this.f19209z) {
            if (this.A != null && this.D == null && (kVar2 = this.f19188e) != null && kVar2.f() != null) {
                com.tencent.liteav.basic.opengl.g gVar = new com.tencent.liteav.basic.opengl.g();
                this.D = gVar;
                gVar.a(this.f19188e.f(), this.A);
                this.D.a(this.f19206w);
                this.D.b(this.ad);
            }
            com.tencent.liteav.basic.opengl.g gVar2 = this.D;
            if (gVar2 != null && (kVar = this.f19188e) != null) {
                gVar2.a(bVar.f18652a, bVar.f18660i, this.f19205v, this.B, this.C, bVar.f18656e, bVar.f18657f, z2, kVar.l());
            }
        }
    }

    private void a(Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.f19187d.a(runnable);
        } else {
            runnable.run();
        }
    }
}
