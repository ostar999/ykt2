package com.tencent.liteav.basic.opengl;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.HandlerThread;
import android.os.Message;
import com.google.android.exoplayer2.C;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.i;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
public class h implements i.a, n {

    /* renamed from: d, reason: collision with root package name */
    private o f18567d;

    /* renamed from: b, reason: collision with root package name */
    private volatile HandlerThread f18565b = null;

    /* renamed from: c, reason: collision with root package name */
    private volatile i f18566c = null;

    /* renamed from: e, reason: collision with root package name */
    private int[] f18568e = null;

    /* renamed from: f, reason: collision with root package name */
    private SurfaceTexture f18569f = null;

    /* renamed from: g, reason: collision with root package name */
    private boolean f18570g = false;

    /* renamed from: a, reason: collision with root package name */
    public int f18564a = 25;

    /* renamed from: h, reason: collision with root package name */
    private long f18571h = 0;

    /* renamed from: i, reason: collision with root package name */
    private long f18572i = 0;

    /* renamed from: j, reason: collision with root package name */
    private float[] f18573j = new float[16];

    private void f() {
        synchronized (this) {
            if (this.f18566c != null) {
                i.a(this.f18566c, this.f18565b);
                TXCLog.w("TXGLSurfaceTextureThread", "destroy gl thread");
            }
            this.f18566c = null;
            this.f18565b = null;
        }
    }

    private void g() {
        TXCLog.w("TXGLSurfaceTextureThread", "destroy surface texture ");
        o oVar = this.f18567d;
        if (oVar != null) {
            oVar.b(this.f18569f);
        }
        SurfaceTexture surfaceTexture = this.f18569f;
        if (surfaceTexture != null) {
            surfaceTexture.setOnFrameAvailableListener(null);
            this.f18569f.release();
            this.f18570g = false;
            this.f18569f = null;
        }
        int[] iArr = this.f18568e;
        if (iArr != null) {
            GLES20.glDeleteTextures(1, iArr, 0);
            this.f18568e = null;
        }
    }

    private void h() {
        TXCLog.w("TXGLSurfaceTextureThread", "init surface texture ");
        this.f18568e = new int[]{TXCOpenGlUtils.b()};
        if (this.f18568e[0] <= 0) {
            this.f18568e = null;
            return;
        }
        SurfaceTexture surfaceTexture = new SurfaceTexture(this.f18568e[0]);
        this.f18569f = surfaceTexture;
        surfaceTexture.setDefaultBufferSize(1280, 720);
        this.f18569f.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.basic.opengl.h.3
            @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
            public void onFrameAvailable(SurfaceTexture surfaceTexture2) {
                h.this.a(103, new Runnable() { // from class: com.tencent.liteav.basic.opengl.h.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        h.this.f18570g = true;
                        h.this.a(102);
                    }
                });
                surfaceTexture2.setOnFrameAvailableListener(null);
            }
        });
        o oVar = this.f18567d;
        if (oVar != null) {
            oVar.a(this.f18569f);
        }
    }

    private boolean i() {
        if (!this.f18570g) {
            this.f18571h = 0L;
            this.f18572i = System.nanoTime();
            return false;
        }
        long jNanoTime = System.nanoTime();
        long j2 = this.f18572i;
        if (jNanoTime < ((((this.f18571h * 1000) * 1000) * 1000) / this.f18564a) + j2) {
            return false;
        }
        if (j2 == 0) {
            this.f18572i = jNanoTime;
        } else if (jNanoTime > j2 + C.NANOS_PER_SECOND) {
            this.f18571h = 0L;
            this.f18572i = jNanoTime;
        }
        this.f18571h++;
        return true;
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a(int i2, boolean z2, int i3, int i4, int i5, boolean z3) {
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a(byte[] bArr) {
    }

    @Override // com.tencent.liteav.basic.opengl.i.a
    public void c() {
        h();
    }

    @Override // com.tencent.liteav.basic.opengl.i.a
    public void d() {
        SurfaceTexture surfaceTexture;
        a(102, 5L);
        if (!i() || (surfaceTexture = this.f18569f) == null || this.f18568e == null) {
            return;
        }
        try {
            surfaceTexture.updateTexImage();
            this.f18569f.getTransformMatrix(this.f18573j);
        } catch (Exception e2) {
            TXCLog.e("TXGLSurfaceTextureThread", "onMsgRend Exception " + e2.getMessage());
        }
        o oVar = this.f18567d;
        if (oVar != null) {
            oVar.a(this.f18568e[0], this.f18573j);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.i.a
    public void e() {
        g();
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public EGLContext getGLContext() {
        EGLContext eGLContextA;
        synchronized (this) {
            eGLContextA = this.f18566c != null ? this.f18566c.a() : null;
        }
        return eGLContextA;
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public SurfaceTexture getSurfaceTexture() {
        return this.f18569f;
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void setFPS(final int i2) {
        synchronized (this) {
            if (this.f18566c == null) {
                return;
            }
            this.f18566c.post(new Runnable() { // from class: com.tencent.liteav.basic.opengl.h.2
                @Override // java.lang.Runnable
                public void run() {
                    h hVar = h.this;
                    int i3 = i2;
                    hVar.f18564a = i3;
                    if (i3 <= 0) {
                        hVar.f18564a = 1;
                    } else if (i3 > 60) {
                        hVar.f18564a = 60;
                    }
                    hVar.f18571h = 0L;
                    h.this.f18572i = 0L;
                }
            });
        }
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void setRendMirror(int i2) {
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void setRendMode(int i2) {
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void setSurfaceTextureListener(o oVar) {
        this.f18567d = oVar;
    }

    private void b() {
        f();
        synchronized (this) {
            this.f18565b = new HandlerThread("TXGLSurfaceTextureThread");
            this.f18565b.start();
            this.f18566c = new i(this.f18565b.getLooper());
            this.f18566c.a(this);
            this.f18566c.f18580a = 1280;
            this.f18566c.f18581b = 720;
            TXCLog.w("TXGLSurfaceTextureThread", "create gl thread " + this.f18565b.getName());
        }
        a(100);
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a(int i2, boolean z2) {
        this.f18564a = i2;
        b();
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a() {
        f();
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a(Runnable runnable) {
        synchronized (this) {
            if (this.f18566c != null) {
                this.f18566c.post(runnable);
            }
        }
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a(boolean z2) {
        SurfaceTexture surfaceTexture;
        synchronized (this) {
            try {
                try {
                    if (this.f18566c != null) {
                        this.f18566c.removeCallbacksAndMessages(null);
                    }
                    this.f18570g = false;
                    surfaceTexture = this.f18569f;
                } catch (Exception e2) {
                    TXCLog.e("TXGLSurfaceTextureThread", "updateTexImage failed." + e2.getMessage());
                }
                if (surfaceTexture != null && this.f18568e != null) {
                    surfaceTexture.updateTexImage();
                    this.f18569f.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() { // from class: com.tencent.liteav.basic.opengl.h.1
                        @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                        public void onFrameAvailable(SurfaceTexture surfaceTexture2) {
                            h.this.a(103, new Runnable() { // from class: com.tencent.liteav.basic.opengl.h.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    h.this.f18570g = true;
                                    h.this.a(102);
                                }
                            });
                            surfaceTexture2.setOnFrameAvailableListener(null);
                        }
                    });
                }
            } finally {
            }
        }
    }

    private void a(int i2, long j2) {
        synchronized (this) {
            if (this.f18566c != null) {
                this.f18566c.sendEmptyMessageDelayed(i2, j2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2) {
        synchronized (this) {
            if (this.f18566c != null) {
                this.f18566c.sendEmptyMessage(i2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2, Runnable runnable) {
        synchronized (this) {
            if (this.f18566c != null) {
                Message message = new Message();
                message.what = i2;
                message.obj = runnable;
                this.f18566c.sendMessage(message);
            }
        }
    }
}
