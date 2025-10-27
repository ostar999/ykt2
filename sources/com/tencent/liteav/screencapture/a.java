package com.tencent.liteav.screencapture;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import com.google.android.exoplayer2.C;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import com.tencent.liteav.basic.opengl.k;
import com.tencent.liteav.basic.opengl.l;
import com.tencent.liteav.basic.opengl.m;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.f;
import com.tencent.liteav.basic.util.h;
import com.tencent.liteav.screencapture.c;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.ref.WeakReference;
import java.util.Locale;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: j, reason: collision with root package name */
    private final boolean f19879j;

    /* renamed from: k, reason: collision with root package name */
    private final Context f19880k;

    /* renamed from: p, reason: collision with root package name */
    private WeakReference<InterfaceC0337a> f19885p;

    /* renamed from: b, reason: collision with root package name */
    protected volatile HandlerThread f19871b = null;

    /* renamed from: c, reason: collision with root package name */
    protected volatile b f19872c = null;

    /* renamed from: d, reason: collision with root package name */
    protected volatile WeakReference<com.tencent.liteav.screencapture.b> f19873d = null;

    /* renamed from: e, reason: collision with root package name */
    protected volatile int f19874e = 0;

    /* renamed from: f, reason: collision with root package name */
    protected int f19875f = 720;

    /* renamed from: g, reason: collision with root package name */
    protected int f19876g = 1280;

    /* renamed from: h, reason: collision with root package name */
    protected int f19877h = 20;

    /* renamed from: i, reason: collision with root package name */
    protected boolean f19878i = true;

    /* renamed from: l, reason: collision with root package name */
    private Object f19881l = null;

    /* renamed from: m, reason: collision with root package name */
    private int f19882m = 720;

    /* renamed from: n, reason: collision with root package name */
    private int f19883n = 1280;

    /* renamed from: o, reason: collision with root package name */
    private WeakReference<com.tencent.liteav.basic.b.b> f19884o = null;

    /* renamed from: q, reason: collision with root package name */
    private c.b f19886q = new c.b() { // from class: com.tencent.liteav.screencapture.a.1
        @Override // com.tencent.liteav.screencapture.c.b
        public void a(boolean z2, boolean z3) {
            if (z2) {
                a.this.b(106);
            } else {
                a.this.f19885p = null;
                h.a((WeakReference<com.tencent.liteav.basic.b.b>) a.this.f19884o, -1308, "Failed to share screen");
            }
        }

        @Override // com.tencent.liteav.screencapture.c.b
        public void a() {
            h.a((WeakReference<com.tencent.liteav.basic.b.b>) a.this.f19884o, -7001, "Screen recording stopped. It may be preempted by other apps");
            InterfaceC0337a interfaceC0337aD = a.this.d();
            a.this.f19885p = null;
            if (interfaceC0337aD != null) {
                interfaceC0337aD.onScreenCaptureStopped(1);
            }
        }

        @Override // com.tencent.liteav.screencapture.c.b
        public void a(boolean z2) {
            if (a.this.f19879j) {
                a.this.b(z2);
                a aVar = a.this;
                aVar.b(105, aVar.f19882m, a.this.f19883n);
            }
        }
    };

    /* renamed from: a, reason: collision with root package name */
    protected final Handler f19870a = new Handler(Looper.getMainLooper());

    /* renamed from: com.tencent.liteav.screencapture.a$a, reason: collision with other inner class name */
    public interface InterfaceC0337a {
        void onScreenCapturePaused();

        void onScreenCaptureResumed();

        void onScreenCaptureStarted();

        void onScreenCaptureStopped(int i2);
    }

    public a(Context context, boolean z2, InterfaceC0337a interfaceC0337a) {
        this.f19885p = new WeakReference<>(interfaceC0337a);
        this.f19880k = context.getApplicationContext();
        this.f19879j = z2;
    }

    private void c(int i2, int i3) {
        if (this.f19879j) {
            int iG = h.g(this.f19880k);
            if (iG == 0 || iG == 2) {
                if (i2 > i3) {
                    this.f19875f = i3;
                    this.f19876g = i2;
                } else {
                    this.f19875f = i2;
                    this.f19876g = i3;
                }
            } else if (i2 < i3) {
                this.f19875f = i3;
                this.f19876g = i2;
            } else {
                this.f19875f = i2;
                this.f19876g = i3;
            }
        } else {
            this.f19875f = i2;
            this.f19876g = i3;
        }
        this.f19882m = this.f19875f;
        this.f19883n = this.f19876g;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public InterfaceC0337a d() {
        WeakReference<InterfaceC0337a> weakReference = this.f19885p;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public void b() {
        synchronized (this) {
            this.f19874e++;
            if (this.f19872c != null) {
                final HandlerThread handlerThread = this.f19871b;
                final b bVar = this.f19872c;
                a(101, new Runnable() { // from class: com.tencent.liteav.screencapture.a.3
                    @Override // java.lang.Runnable
                    public void run() {
                        a.this.f19870a.post(new Runnable() { // from class: com.tencent.liteav.screencapture.a.3.1
                            @Override // java.lang.Runnable
                            public void run() {
                                Handler handler = bVar;
                                if (handler != null) {
                                    handler.removeCallbacksAndMessages(null);
                                }
                                if (handlerThread != null) {
                                    if (TXCBuild.VersionInt() >= 18) {
                                        handlerThread.quitSafely();
                                    } else {
                                        handlerThread.quit();
                                    }
                                }
                            }
                        });
                    }
                });
            }
            this.f19872c = null;
            this.f19871b = null;
        }
    }

    public class b extends Handler {

        /* renamed from: a, reason: collision with root package name */
        public int f19894a;

        /* renamed from: b, reason: collision with root package name */
        public int[] f19895b;

        /* renamed from: c, reason: collision with root package name */
        public Surface f19896c;

        /* renamed from: d, reason: collision with root package name */
        public SurfaceTexture f19897d;

        /* renamed from: e, reason: collision with root package name */
        public int f19898e;

        /* renamed from: f, reason: collision with root package name */
        public int f19899f;

        /* renamed from: g, reason: collision with root package name */
        public int f19900g;

        /* renamed from: h, reason: collision with root package name */
        protected boolean f19901h;

        /* renamed from: i, reason: collision with root package name */
        protected long f19902i;

        /* renamed from: j, reason: collision with root package name */
        protected long f19903j;

        /* renamed from: k, reason: collision with root package name */
        protected com.tencent.liteav.basic.opengl.b f19904k;

        /* renamed from: l, reason: collision with root package name */
        protected k f19905l;

        /* renamed from: m, reason: collision with root package name */
        float[] f19906m;

        /* renamed from: o, reason: collision with root package name */
        private boolean f19908o;

        public b(Looper looper, a aVar) {
            super(looper);
            this.f19894a = 0;
            this.f19895b = null;
            this.f19896c = null;
            this.f19897d = null;
            this.f19898e = 720;
            this.f19899f = 1280;
            this.f19900g = 25;
            this.f19901h = false;
            this.f19902i = 0L;
            this.f19903j = 0L;
            this.f19904k = null;
            this.f19905l = null;
            this.f19906m = new float[16];
            this.f19908o = true;
            TXCLog.i("TXCScreenCapture", "TXCScreenCaptureGLThreadHandler inited. hashCode: %d", Integer.valueOf(hashCode()));
        }

        public void a(Message message) {
            this.f19902i = 0L;
            this.f19903j = 0L;
            if (b()) {
                return;
            }
            c();
            a.this.b();
            a.this.c(20000003);
        }

        public void b(Message message) {
            a aVar = a.this;
            aVar.f19878i = false;
            InterfaceC0337a interfaceC0337aD = aVar.d();
            if (interfaceC0337aD != null) {
                interfaceC0337aD.onScreenCaptureStopped(0);
            }
            com.tencent.liteav.screencapture.b bVarC = a.this.c();
            if (bVarC != null) {
                bVarC.a(a.this.f19881l);
            }
            c();
        }

        public void c(Message message) {
            a.this.a(102, 5L);
            if (a.this.f19878i) {
                if (this.f19904k == null) {
                    TXCLog.e("TXCScreenCapture", "eglhelper is null");
                    return;
                }
                if (!this.f19901h) {
                    this.f19902i = 0L;
                    this.f19903j = System.nanoTime();
                    return;
                }
                long jNanoTime = System.nanoTime();
                long j2 = this.f19903j;
                if (jNanoTime < ((((this.f19902i * 1000) * 1000) * 1000) / this.f19900g) + j2) {
                    return;
                }
                if (j2 == 0) {
                    this.f19903j = jNanoTime;
                } else if (jNanoTime > j2 + C.NANOS_PER_SECOND) {
                    this.f19902i = 0L;
                    this.f19903j = jNanoTime;
                }
                this.f19902i++;
                SurfaceTexture surfaceTexture = this.f19897d;
                if (surfaceTexture == null || this.f19895b == null) {
                    return;
                }
                surfaceTexture.getTransformMatrix(this.f19906m);
                try {
                    this.f19897d.updateTexImage();
                } catch (Exception e2) {
                    TXCLog.e("TXCScreenCapture", "onMsgRend Exception " + e2.getMessage());
                }
                this.f19905l.a(this.f19906m);
                GLES20.glViewport(0, 0, this.f19898e, this.f19899f);
                a.this.a(0, this.f19904k.d(), this.f19905l.b(this.f19895b[0]), this.f19898e, this.f19899f, TXCTimeUtil.getTimeTick());
            }
        }

        public void d(Message message) {
            if (message == null) {
                return;
            }
            int i2 = message.arg1;
            if (i2 < 1) {
                i2 = 1;
            }
            this.f19900g = i2;
            this.f19902i = 0L;
            this.f19903j = 0L;
        }

        public void e(Message message) {
            if (message == null) {
                return;
            }
            this.f19898e = message.arg1;
            this.f19899f = message.arg2;
            d();
            this.f19905l.a(this.f19898e, this.f19899f);
            e();
            TXCLog.i("TXCScreenCapture", String.format("set screen capture size[%d/%d]", Integer.valueOf(a.this.f19882m), Integer.valueOf(a.this.f19883n)));
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message == null) {
                return;
            }
            if (this.f19894a == a.this.f19874e || 101 == message.what) {
                switch (message.what) {
                    case 100:
                        a(message);
                        break;
                    case 101:
                        b(message);
                        break;
                    case 102:
                        try {
                            c(message);
                            break;
                        } catch (Exception e2) {
                            TXCLog.e("TXCScreenCapture", "render failed.", e2);
                            break;
                        }
                    case 103:
                        d(message);
                        break;
                    case 105:
                        e(message);
                        break;
                    case 106:
                        a();
                        break;
                }
                Object obj = message.obj;
                if (obj != null) {
                    ((Runnable) obj).run();
                }
            }
        }

        public void d() {
            new f(Looper.getMainLooper()).a(new Runnable() { // from class: com.tencent.liteav.screencapture.a.b.1
                @Override // java.lang.Runnable
                public void run() {
                    c.a(a.this.f19880k).a(b.this.f19896c);
                }
            });
            Surface surface = this.f19896c;
            if (surface != null) {
                surface.release();
                this.f19896c = null;
            }
            SurfaceTexture surfaceTexture = this.f19897d;
            if (surfaceTexture != null) {
                surfaceTexture.setOnFrameAvailableListener(null);
                this.f19897d.release();
                this.f19901h = false;
                this.f19897d = null;
            }
            int[] iArr = this.f19895b;
            if (iArr != null) {
                GLES20.glDeleteTextures(1, iArr, 0);
                this.f19895b = null;
            }
        }

        public void a() {
            if (this.f19908o && this.f19904k != null) {
                Bundle bundle = new Bundle();
                bundle.putString(TXLiveConstants.EVT_DESCRIPTION, "Screen recording started successfully");
                h.a((WeakReference<com.tencent.liteav.basic.b.b>) a.this.f19884o, 1004, bundle);
                a.this.c(0);
            }
            this.f19908o = false;
        }

        public boolean b() {
            TXCLog.i("TXCScreenCapture", String.format("init egl size[%d/%d]", Integer.valueOf(this.f19898e), Integer.valueOf(this.f19899f)));
            com.tencent.liteav.basic.opengl.b bVarA = com.tencent.liteav.basic.opengl.b.a(null, null, null, this.f19898e, this.f19899f);
            this.f19904k = bVarA;
            if (bVarA == null) {
                return false;
            }
            k kVar = new k();
            this.f19905l = kVar;
            if (!kVar.a()) {
                return false;
            }
            this.f19905l.a(true);
            this.f19905l.a(this.f19898e, this.f19899f);
            this.f19905l.a(m.f18642e, m.a(l.NORMAL, false, false));
            e();
            return true;
        }

        public void e() {
            this.f19895b = new int[]{TXCOpenGlUtils.b()};
            if (this.f19895b[0] <= 0) {
                this.f19895b = null;
                return;
            }
            this.f19897d = new SurfaceTexture(this.f19895b[0]);
            this.f19896c = new Surface(this.f19897d);
            this.f19897d.setDefaultBufferSize(this.f19898e, this.f19899f);
            this.f19897d.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.screencapture.a.b.2
                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    a.this.a(104, new Runnable() { // from class: com.tencent.liteav.screencapture.a.b.2.1
                        @Override // java.lang.Runnable
                        public void run() {
                            b bVar = b.this;
                            bVar.f19901h = true;
                            a.this.b(102);
                        }
                    });
                    surfaceTexture.setOnFrameAvailableListener(null);
                }
            });
            new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.liteav.screencapture.a.b.3
                @Override // java.lang.Runnable
                public void run() {
                    c cVarA = c.a(a.this.f19880k);
                    b bVar = b.this;
                    cVarA.a(bVar.f19896c, bVar.f19898e, bVar.f19899f, a.this.f19886q);
                }
            });
        }

        public void c() {
            d();
            k kVar = this.f19905l;
            if (kVar != null) {
                kVar.d();
                this.f19905l = null;
            }
            com.tencent.liteav.basic.opengl.b bVar = this.f19904k;
            if (bVar != null) {
                bVar.c();
                this.f19904k = null;
            }
        }
    }

    public int a(int i2, int i3, int i4) {
        this.f19877h = i4;
        if (TXCBuild.VersionInt() < 21) {
            c(20000004);
            return 20000004;
        }
        c(i2, i3);
        a();
        TXCLog.i("TXCScreenCapture", "start screen capture");
        return 0;
    }

    public void a(Object obj) {
        TXCLog.i("TXCScreenCapture", "stop encode: " + obj);
        this.f19881l = obj;
        b();
    }

    public void b(int i2) {
        synchronized (this) {
            if (this.f19872c != null) {
                this.f19872c.sendEmptyMessage(i2);
            }
        }
    }

    public void a(final boolean z2) {
        synchronized (this) {
            Runnable runnable = new Runnable() { // from class: com.tencent.liteav.screencapture.a.2
                @Override // java.lang.Runnable
                public void run() {
                    InterfaceC0337a interfaceC0337aD = a.this.d();
                    boolean z3 = a.this.f19878i;
                    boolean z4 = z2;
                    if (z3 != z4 && interfaceC0337aD != null) {
                        if (z4) {
                            interfaceC0337aD.onScreenCaptureResumed();
                        } else {
                            interfaceC0337aD.onScreenCapturePaused();
                        }
                    }
                    a.this.f19878i = z2;
                }
            };
            if (this.f19872c != null) {
                this.f19872c.post(runnable);
            } else {
                runnable.run();
            }
        }
    }

    public void b(int i2, int i3) {
        synchronized (this) {
            if (this.f19872c != null) {
                Message message = new Message();
                message.what = i2;
                message.arg1 = i3;
                this.f19872c.sendMessage(message);
            }
        }
    }

    public com.tencent.liteav.screencapture.b c() {
        if (this.f19873d == null) {
            return null;
        }
        return this.f19873d.get();
    }

    public void c(int i2) {
        InterfaceC0337a interfaceC0337aD = d();
        if (interfaceC0337aD == null || i2 != 0) {
            return;
        }
        interfaceC0337aD.onScreenCaptureStarted();
    }

    public void a(com.tencent.liteav.screencapture.b bVar) {
        this.f19873d = new WeakReference<>(bVar);
    }

    public void a(com.tencent.liteav.basic.b.b bVar) {
        this.f19884o = new WeakReference<>(bVar);
    }

    public void a(int i2) {
        this.f19877h = i2;
        b(103, i2);
    }

    public void a(int i2, int i3) {
        c(i2, i3);
        b(105, i2, i3);
    }

    public void b(int i2, int i3, int i4) {
        synchronized (this) {
            if (this.f19872c != null) {
                Message message = new Message();
                message.what = i2;
                message.arg1 = i3;
                message.arg2 = i4;
                this.f19872c.sendMessage(message);
            }
        }
    }

    public synchronized void a(Runnable runnable) {
        if (this.f19872c != null) {
            this.f19872c.post(runnable);
        }
    }

    public void a() {
        b();
        synchronized (this) {
            this.f19871b = new HandlerThread("ScreenCaptureGLThread");
            this.f19871b.start();
            this.f19872c = new b(this.f19871b.getLooper(), this);
            int i2 = 1;
            this.f19874e++;
            this.f19872c.f19894a = this.f19874e;
            this.f19872c.f19898e = this.f19882m;
            this.f19872c.f19899f = this.f19883n;
            b bVar = this.f19872c;
            int i3 = this.f19877h;
            if (i3 >= 1) {
                i2 = i3;
            }
            bVar.f19900g = i2;
        }
        b(100);
    }

    public void b(boolean z2) {
        if (z2) {
            int i2 = this.f19875f;
            int i3 = this.f19876g;
            this.f19882m = i2 < i3 ? i2 : i3;
            if (i2 < i3) {
                i2 = i3;
            }
            this.f19883n = i2;
        } else {
            int i4 = this.f19875f;
            int i5 = this.f19876g;
            this.f19882m = i4 < i5 ? i5 : i4;
            if (i4 >= i5) {
                i4 = i5;
            }
            this.f19883n = i4;
        }
        TXCLog.i("TXCScreenCapture", String.format(Locale.ENGLISH, "reset screen capture isPortrait[%b] output size[%d/%d]", Boolean.valueOf(z2), Integer.valueOf(this.f19882m), Integer.valueOf(this.f19883n)));
    }

    public void a(int i2, long j2) {
        synchronized (this) {
            if (this.f19872c != null) {
                this.f19872c.sendEmptyMessageDelayed(i2, j2);
            }
        }
    }

    public void a(int i2, Runnable runnable) {
        synchronized (this) {
            if (this.f19872c != null) {
                Message message = new Message();
                message.what = i2;
                message.obj = runnable;
                this.f19872c.sendMessage(message);
            }
        }
    }

    public void a(int i2, EGLContext eGLContext, int i3, int i4, int i5, long j2) {
        com.tencent.liteav.screencapture.b bVarC = c();
        if (bVarC != null) {
            bVarC.a(i2, eGLContext, i3, i4, i5, j2);
        }
    }
}
