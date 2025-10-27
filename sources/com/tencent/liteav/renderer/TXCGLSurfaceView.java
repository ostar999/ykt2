package com.tencent.liteav.renderer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import com.google.android.exoplayer2.ExoPlayer;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import com.tencent.liteav.basic.opengl.j;
import com.tencent.liteav.basic.opengl.l;
import com.tencent.liteav.basic.opengl.m;
import com.tencent.liteav.basic.opengl.n;
import com.tencent.liteav.basic.opengl.o;
import com.tencent.liteav.basic.opengl.p;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.rtmp.TXLiveConstants;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes6.dex */
public class TXCGLSurfaceView extends TXCGLSurfaceViewBase implements SurfaceTexture.OnFrameAvailableListener, GLSurfaceView.Renderer, n {
    private p A;
    private int B;
    private int C;
    private boolean D;
    private boolean E;
    private o F;
    private long G;
    private byte[] H;
    private long I;
    private int J;
    private int K;
    private final Queue<Runnable> L;

    /* renamed from: a, reason: collision with root package name */
    WeakReference<com.tencent.liteav.basic.b.b> f19636a;

    /* renamed from: g, reason: collision with root package name */
    private SurfaceTexture f19637g;

    /* renamed from: h, reason: collision with root package name */
    private EGLContext f19638h;

    /* renamed from: i, reason: collision with root package name */
    private j f19639i;

    /* renamed from: j, reason: collision with root package name */
    private int[] f19640j;

    /* renamed from: k, reason: collision with root package name */
    private float[] f19641k;

    /* renamed from: l, reason: collision with root package name */
    private int f19642l;

    /* renamed from: m, reason: collision with root package name */
    private boolean f19643m;

    /* renamed from: n, reason: collision with root package name */
    private float f19644n;

    /* renamed from: o, reason: collision with root package name */
    private float f19645o;

    /* renamed from: p, reason: collision with root package name */
    private int f19646p;

    /* renamed from: q, reason: collision with root package name */
    private long f19647q;

    /* renamed from: r, reason: collision with root package name */
    private long f19648r;

    /* renamed from: s, reason: collision with root package name */
    private int f19649s;

    /* renamed from: t, reason: collision with root package name */
    private boolean f19650t;

    /* renamed from: u, reason: collision with root package name */
    private boolean f19651u;

    /* renamed from: v, reason: collision with root package name */
    private Object f19652v;

    /* renamed from: w, reason: collision with root package name */
    private Handler f19653w;

    /* renamed from: x, reason: collision with root package name */
    private int f19654x;

    /* renamed from: y, reason: collision with root package name */
    private int f19655y;

    /* renamed from: z, reason: collision with root package name */
    private boolean f19656z;

    public TXCGLSurfaceView(Context context) {
        super(context);
        this.f19641k = new float[16];
        this.f19642l = 0;
        this.f19643m = false;
        this.f19644n = 1.0f;
        this.f19645o = 1.0f;
        this.f19646p = 20;
        this.f19647q = 0L;
        this.f19648r = 0L;
        this.f19649s = 12288;
        this.f19650t = true;
        this.f19651u = false;
        this.f19652v = new Object();
        this.f19654x = 0;
        this.f19655y = 0;
        this.f19656z = true;
        this.A = null;
        this.B = 0;
        this.C = 0;
        this.D = true;
        this.E = true;
        this.H = null;
        this.I = 0L;
        this.J = 0;
        this.K = 0;
        this.L = new LinkedList();
        setEGLContextClientVersion(2);
        a(8, 8, 8, 8, 16, 0);
        setRenderer(this);
    }

    private void g() {
        if (!this.E) {
            SurfaceTexture surfaceTexture = this.f19637g;
            if (surfaceTexture != null) {
                surfaceTexture.setOnFrameAvailableListener(null);
                return;
            }
            return;
        }
        if (this.f19637g != null) {
            if (TXCBuild.VersionInt() < 21) {
                this.f19637g.setOnFrameAvailableListener(this);
                return;
            }
            if (this.f19653w == null) {
                HandlerThread handlerThread = new HandlerThread("VideoCaptureThread");
                handlerThread.start();
                this.f19653w = new Handler(handlerThread.getLooper());
            }
            this.f19637g.setOnFrameAvailableListener(this, this.f19653w);
        }
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public EGLContext getGLContext() {
        return this.f19638h;
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public SurfaceTexture getSurfaceTexture() {
        return this.f19637g;
    }

    @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase, android.view.SurfaceView, android.view.View
    public void onDetachedFromWindow() {
        Handler handler;
        super.onDetachedFromWindow();
        if (TXCBuild.VersionInt() < 21 || (handler = this.f19653w) == null) {
            return;
        }
        handler.getLooper().quitSafely();
        this.f19653w = null;
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl10) throws InterruptedException {
        long jCurrentTimeMillis;
        long j2;
        boolean z2;
        byte[] bArr;
        boolean z3;
        int iE;
        a(this.L);
        boolean z4 = true;
        boolean z5 = true;
        while (true) {
            jCurrentTimeMillis = System.currentTimeMillis();
            long j3 = this.f19648r;
            if (j3 == 0 || jCurrentTimeMillis < j3) {
                this.f19648r = jCurrentTimeMillis;
            }
            j2 = this.f19648r;
            if (jCurrentTimeMillis - j2 >= (this.f19647q * 1000) / this.f19646p) {
                break;
            }
            a(15L);
            z5 = false;
        }
        if (jCurrentTimeMillis - j2 > 1000) {
            this.f19647q = 1L;
            this.f19648r = System.currentTimeMillis();
            z2 = true;
        } else {
            z2 = false;
        }
        if (this.f19650t) {
            return;
        }
        try {
            synchronized (this) {
                bArr = null;
                if (this.f19651u) {
                    byte[] bArr2 = this.H;
                    if (bArr2 != null) {
                        this.H = null;
                        SurfaceTexture surfaceTexture = this.f19637g;
                        if (surfaceTexture != null) {
                            surfaceTexture.updateTexImage();
                            this.f19637g.getTransformMatrix(this.f19641k);
                        }
                        bArr = bArr2;
                    } else {
                        SurfaceTexture surfaceTexture2 = this.f19637g;
                        if (surfaceTexture2 != null) {
                            surfaceTexture2.updateTexImage();
                            this.f19637g.getTransformMatrix(this.f19641k);
                        }
                    }
                    if (z2) {
                        this.f19647q = 1L;
                    } else {
                        this.f19647q++;
                    }
                    this.f19651u = false;
                    z5 = false;
                    z3 = false;
                } else {
                    z3 = true;
                }
            }
            if (true == z3) {
                if (true == z5) {
                    a(5L);
                    return;
                }
                return;
            }
            long jCurrentTimeMillis2 = System.currentTimeMillis();
            if (jCurrentTimeMillis2 > this.I + 1000.0d) {
                this.J = ((int) ((this.K * 1000.0d) / (jCurrentTimeMillis2 - r7))) + 1;
                this.I = jCurrentTimeMillis2;
                this.K = 0;
            }
            this.K++;
            o oVar = this.F;
            if (oVar != null) {
                if (bArr != null) {
                    oVar.a(bArr, this.f19641k);
                } else {
                    oVar.a(this.f19640j[0], this.f19641k);
                }
            }
            synchronized (this) {
                if (this.f19672c) {
                    z4 = false;
                }
            }
            if (!z4 || (iE = e()) == 12288 || System.currentTimeMillis() - this.G <= ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
                return;
            }
            TXCLog.w("TXCGLSurfaceView", "background capture swapBuffer error : " + iE);
            this.G = System.currentTimeMillis();
            Bundle bundle = new Bundle();
            bundle.putInt("EVT_PARAM1", iE);
            bundle.putInt("EVT_ID", 2110);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle.putLong(TXLiveConstants.EVT_UTC_TIME, TXCTimeUtil.getUtcTimeTick());
            bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, "Failed to render video");
            com.tencent.liteav.basic.util.h.a(this.f19636a, 2110, bundle);
        } catch (Exception e2) {
            TXCLog.e("TXCGLSurfaceView", "onDrawFrame failed", e2);
        }
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.f19650t = false;
        synchronized (this) {
            this.f19651u = true;
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl10, int i2, int i3) {
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        this.f19638h = ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
        this.f19640j = new int[]{TXCOpenGlUtils.b()};
        if (this.f19640j[0] <= 0) {
            this.f19640j = null;
            TXCLog.e("TXCGLSurfaceView", "create oes texture error!! at glsurfaceview");
            return;
        }
        this.f19637g = new SurfaceTexture(this.f19640j[0]);
        g();
        j jVar = new j();
        this.f19639i = jVar;
        if (jVar.a()) {
            this.f19639i.a(m.f18642e, m.a(l.NORMAL, false, false));
            o oVar = this.F;
            if (oVar != null) {
                oVar.a(this.f19637g);
            }
        }
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void setFPS(final int i2) {
        TXCLog.i("TXCGLSurfaceView", "TXCGLSurfaceView : setFPS ():" + i2);
        b(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceView.1
            @Override // java.lang.Runnable
            public void run() {
                TXCGLSurfaceView.this.f19646p = i2;
                if (TXCGLSurfaceView.this.f19646p <= 0) {
                    TXCGLSurfaceView.this.f19646p = 1;
                } else if (TXCGLSurfaceView.this.f19646p > 60) {
                    TXCGLSurfaceView.this.f19646p = 60;
                }
                TXCGLSurfaceView.this.f19648r = 0L;
                TXCGLSurfaceView.this.f19647q = 0L;
            }
        });
    }

    public void setNotifyListener(com.tencent.liteav.basic.b.b bVar) {
        this.f19636a = new WeakReference<>(bVar);
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void setRendMirror(final int i2) {
        b(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceView.3
            @Override // java.lang.Runnable
            public void run() {
                TXCGLSurfaceView.this.C = i2;
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(R2.id.ly_collection);
            }
        });
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void setRendMode(final int i2) {
        b(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceView.2
            @Override // java.lang.Runnable
            public void run() {
                TXCGLSurfaceView.this.B = i2;
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(R2.id.ly_collection);
            }
        });
    }

    @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase
    public void setRunInBackground(boolean z2) {
        if (!z2) {
            b(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceView.4
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (this) {
                        TXCLog.i("TXCGLSurfaceView", "background capture exit background");
                        TXCGLSurfaceView.this.f19672c = false;
                    }
                }
            });
            return;
        }
        synchronized (this) {
            TXCLog.i("TXCGLSurfaceView", "background capture enter background");
            this.f19672c = true;
        }
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void setSurfaceTextureListener(o oVar) {
        this.F = oVar;
    }

    @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase
    public int c() {
        if (this.f19649s != 12288) {
            TXCLog.e("TXCGLSurfaceView", "background capture swapbuffer error : " + this.f19649s);
        }
        return this.f19649s;
    }

    @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase
    public void b() {
        TXCLog.i("TXCGLSurfaceView", "onSurfaceDestroyed-->enter with mSurfaceTextureListener:" + this.F);
        o oVar = this.F;
        if (oVar != null) {
            oVar.b(this.f19637g);
        }
        SurfaceTexture surfaceTexture = this.f19637g;
        if (surfaceTexture != null) {
            surfaceTexture.release();
            this.f19637g = null;
        }
    }

    public void a(p pVar) {
        this.A = pVar;
        this.f19656z = true;
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a(int i2, boolean z2, int i3, int i4, int i5, boolean z3) {
        int i6;
        int i7;
        if (this.f19639i == null) {
            return;
        }
        synchronized (this) {
            if (this.f19672c) {
                return;
            }
            int width = getWidth();
            int height = getHeight();
            int i8 = this.B;
            if (i8 != 0 && i8 == 1) {
                int i9 = (720 - i3) % 360;
                boolean z4 = i9 == 90 || i9 == 270;
                int[] iArrA = a(width, height, z4 ? i5 : i4, z4 ? i4 : i5);
                int i10 = iArrA[0];
                int i11 = iArrA[1];
                i7 = iArrA[2];
                i6 = iArrA[3];
                width = i10;
                height = i11;
            } else {
                i6 = 0;
                i7 = 0;
            }
            this.f19654x = width;
            this.f19655y = height;
            GLES20.glViewport(i7, i6, width, height);
            int i12 = this.C;
            boolean z5 = (i12 != 1 ? !(i12 == 2 && z3) : z3) ? z2 : !z2;
            float f2 = height != 0 ? width / height : 1.0f;
            float f3 = i5 != 0 ? i4 / i5 : 1.0f;
            if (this.f19643m != z5 || this.f19642l != i3 || this.f19644n != f2 || this.f19645o != f3 || this.D != z3) {
                this.f19643m = z5;
                this.f19642l = i3;
                this.f19644n = f2;
                this.f19645o = f3;
                this.D = z3;
                int i13 = (720 - i3) % 360;
                boolean z6 = i13 == 90 || i13 == 270;
                int i14 = z6 ? height : width;
                if (!z6) {
                    width = height;
                }
                this.f19639i.a(i4, i5, i13, m.a(l.NORMAL, false, true), i14 / width, z6 ? false : this.f19643m, z6 ? this.f19643m : false);
                if (z6) {
                    this.f19639i.g();
                } else {
                    this.f19639i.h();
                }
            }
            GLES20.glBindFramebuffer(36160, 0);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(R2.id.ly_collection);
            this.f19639i.a(i2);
            a(i7, i6);
        }
    }

    public void b(Runnable runnable) {
        synchronized (this.L) {
            this.L.add(runnable);
        }
    }

    public TXCGLSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f19641k = new float[16];
        this.f19642l = 0;
        this.f19643m = false;
        this.f19644n = 1.0f;
        this.f19645o = 1.0f;
        this.f19646p = 20;
        this.f19647q = 0L;
        this.f19648r = 0L;
        this.f19649s = 12288;
        this.f19650t = true;
        this.f19651u = false;
        this.f19652v = new Object();
        this.f19654x = 0;
        this.f19655y = 0;
        this.f19656z = true;
        this.A = null;
        this.B = 0;
        this.C = 0;
        this.D = true;
        this.E = true;
        this.H = null;
        this.I = 0L;
        this.J = 0;
        this.K = 0;
        this.L = new LinkedList();
        setEGLContextClientVersion(2);
        a(8, 8, 8, 8, 16, 0);
        setRenderer(this);
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a(byte[] bArr) {
        synchronized (this) {
            this.H = bArr;
            this.f19650t = false;
            this.f19651u = true;
        }
    }

    private int[] a(int i2, int i3, int i4, int i5) {
        int i6;
        int i7;
        int i8;
        int[] iArr = new int[4];
        float f2 = i3;
        float f3 = i2;
        float f4 = i5 / i4;
        if (f2 / f3 > f4) {
            i8 = (int) (f3 * f4);
            i6 = (i3 - i8) / 2;
            i7 = 0;
        } else {
            int i9 = (int) (f2 / f4);
            i6 = 0;
            i7 = (i2 - i9) / 2;
            i2 = i9;
            i8 = i3;
        }
        iArr[0] = i2;
        iArr[1] = i8;
        iArr[2] = i7;
        iArr[3] = i6;
        return iArr;
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a(int i2, boolean z2) {
        this.f19646p = i2;
        if (i2 <= 0) {
            this.f19646p = 1;
        } else if (i2 > 60) {
            this.f19646p = 60;
        }
        this.A = null;
        this.f19656z = false;
        this.J = 0;
        this.I = 0L;
        this.K = 0;
        b(true);
        this.E = z2;
        this.G = 0L;
        g();
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a() {
        b(false);
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a(Runnable runnable) {
        synchronized (this.L) {
            this.L.add(runnable);
        }
    }

    private boolean a(Queue<Runnable> queue) {
        synchronized (queue) {
            if (queue.isEmpty()) {
                return false;
            }
            Runnable runnablePoll = queue.poll();
            if (runnablePoll == null) {
                return false;
            }
            runnablePoll.run();
            return true;
        }
    }

    private void a(int i2, int i3) {
        int i4;
        if (this.f19656z) {
            int i5 = this.f19654x;
            if (i5 != 0 && (i4 = this.f19655y) != 0) {
                boolean z2 = i5 <= i4;
                int i6 = i4 >= i5 ? i4 : i5;
                if (i4 < i5) {
                    i5 = i4;
                }
                if (!z2) {
                    int i7 = i6;
                    i6 = i5;
                    i5 = i7;
                }
                final ByteBuffer byteBufferAllocate = ByteBuffer.allocate(i5 * i6 * 4);
                final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i5, i6, Bitmap.Config.ARGB_8888);
                byteBufferAllocate.position(0);
                GLES20.glReadPixels(i2, i3, i5, i6, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, byteBufferAllocate);
                final p pVar = this.A;
                if (pVar != null) {
                    final int i8 = i5;
                    final int i9 = i6;
                    new Thread(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceView.5
                        @Override // java.lang.Runnable
                        public void run() {
                            Bitmap bitmapCreateBitmap2 = null;
                            try {
                                byteBufferAllocate.position(0);
                                bitmapCreateBitmap.copyPixelsFromBuffer(byteBufferAllocate);
                                Matrix matrix = new Matrix();
                                matrix.setScale(1.0f, -1.0f);
                                bitmapCreateBitmap2 = Bitmap.createBitmap(bitmapCreateBitmap, 0, 0, i8, i9, matrix, false);
                            } catch (Error e2) {
                                TXCLog.w("TXCGLSurfaceView", "takePhoto error " + e2);
                            } catch (Exception e3) {
                                TXCLog.w("TXCGLSurfaceView", "takePhoto error " + e3);
                            }
                            pVar.onTakePhotoComplete(bitmapCreateBitmap2);
                            bitmapCreateBitmap.recycle();
                        }
                    }).start();
                }
            }
            this.A = null;
            this.f19656z = false;
        }
    }

    @Override // com.tencent.liteav.basic.opengl.n
    public void a(boolean z2) {
        this.f19650t = true;
        if (z2) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16384);
            this.f19649s = e();
        }
        synchronized (this) {
            if (this.f19651u) {
                this.f19651u = false;
                SurfaceTexture surfaceTexture = this.f19637g;
                if (surfaceTexture != null) {
                    surfaceTexture.updateTexImage();
                }
            }
        }
    }

    private void a(long j2) throws InterruptedException {
        try {
            Thread.sleep(j2);
        } catch (Exception unused) {
        }
    }
}
