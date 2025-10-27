package com.tencent.liteav.basic.opengl;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.opengl.GLES20;
import android.os.HandlerThread;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.i;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
public class g implements i.a {

    /* renamed from: a, reason: collision with root package name */
    private volatile HandlerThread f18530a = null;

    /* renamed from: b, reason: collision with root package name */
    private volatile i f18531b = null;

    /* renamed from: c, reason: collision with root package name */
    private j f18532c = null;

    /* renamed from: d, reason: collision with root package name */
    private int f18533d = 0;

    /* renamed from: e, reason: collision with root package name */
    private boolean f18534e = false;

    /* renamed from: f, reason: collision with root package name */
    private float f18535f = 1.0f;

    /* renamed from: g, reason: collision with root package name */
    private float f18536g = 1.0f;

    /* renamed from: h, reason: collision with root package name */
    private int f18537h = 0;

    /* renamed from: i, reason: collision with root package name */
    private int f18538i = 0;

    /* renamed from: j, reason: collision with root package name */
    private int f18539j = 0;

    /* renamed from: k, reason: collision with root package name */
    private int f18540k = 0;

    /* renamed from: l, reason: collision with root package name */
    private boolean f18541l = false;

    /* renamed from: m, reason: collision with root package name */
    private p f18542m = null;

    /* renamed from: n, reason: collision with root package name */
    private boolean f18543n = false;

    private void f() {
        synchronized (this) {
            if (this.f18531b != null) {
                i.a(this.f18531b, this.f18530a);
                TXCLog.w("TXGLSurfaceRenderThread", "surface-render: destroy gl thread");
            }
            this.f18531b = null;
            this.f18530a = null;
        }
    }

    @Override // com.tencent.liteav.basic.opengl.i.a
    public void c() {
        j jVar = new j();
        this.f18532c = jVar;
        if (jVar.a()) {
            this.f18532c.a(m.f18642e, m.a(l.NORMAL, false, false));
        }
    }

    @Override // com.tencent.liteav.basic.opengl.i.a
    public void d() {
    }

    @Override // com.tencent.liteav.basic.opengl.i.a
    public void e() {
        j jVar = this.f18532c;
        if (jVar != null) {
            jVar.d();
            this.f18532c = null;
        }
    }

    public Surface b() {
        Surface surfaceB;
        synchronized (this) {
            surfaceB = this.f18531b != null ? this.f18531b.b() : null;
        }
        return surfaceB;
    }

    public void a(Object obj, Surface surface) {
        TXCLog.i("TXGLSurfaceRenderThread", "surface-render: surface render start " + surface + ", " + this);
        b(obj, surface);
    }

    private void c(int i2) {
        synchronized (this) {
            if (this.f18531b != null) {
                this.f18531b.sendEmptyMessage(i2);
            }
        }
    }

    public void a() {
        TXCLog.i("TXGLSurfaceRenderThread", "surface-render: surface render stop " + this);
        f();
    }

    public void b(final int i2) {
        a(new Runnable() { // from class: com.tencent.liteav.basic.opengl.g.2
            @Override // java.lang.Runnable
            public void run() {
                g.this.f18538i = i2;
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(R2.id.ly_collection);
            }
        });
    }

    private void b(Object obj, Surface surface) {
        f();
        synchronized (this) {
            this.f18530a = new HandlerThread("TXGLSurfaceRenderThread");
            this.f18530a.start();
            this.f18531b = new i(this.f18530a.getLooper());
            this.f18531b.a(this);
            if (obj != null && !(obj instanceof EGLContext)) {
                this.f18531b.f18583d = true;
                this.f18531b.f18585f = (android.opengl.EGLContext) obj;
            } else {
                this.f18531b.f18583d = false;
                this.f18531b.f18587h = (EGLContext) obj;
            }
            this.f18531b.f18582c = surface;
            TXCLog.w("TXGLSurfaceRenderThread", "surface-render: create gl thread " + this.f18530a.getName());
        }
        c(100);
    }

    public void a(final int i2) {
        a(new Runnable() { // from class: com.tencent.liteav.basic.opengl.g.1
            @Override // java.lang.Runnable
            public void run() {
                g.this.f18537h = i2;
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(R2.id.ly_collection);
            }
        });
    }

    public void a(Runnable runnable) {
        synchronized (this) {
            if (this.f18531b != null) {
                this.f18531b.post(runnable);
            }
        }
    }

    public void a(final int i2, final boolean z2, final int i3, final int i4, final int i5, final int i6, final int i7, final boolean z3, final boolean z4) {
        GLES20.glFinish();
        synchronized (this) {
            if (this.f18531b != null) {
                this.f18531b.post(new Runnable() { // from class: com.tencent.liteav.basic.opengl.g.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            g.this.b(i2, z2, i3, i4, i5, i6, i7, z3, z4);
                        } catch (Exception e2) {
                            TXCLog.e("TXGLSurfaceRenderThread", "surface-render: render texture error occurred!" + e2.getMessage());
                        }
                    }
                });
            }
        }
    }

    public void a(p pVar) {
        this.f18542m = pVar;
        this.f18541l = true;
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

    private void a(int i2, int i3) {
        int i4;
        if (this.f18541l) {
            int i5 = this.f18539j;
            if (i5 != 0 && (i4 = this.f18540k) != 0) {
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
                final p pVar = this.f18542m;
                if (pVar != null) {
                    final int i8 = i5;
                    final int i9 = i6;
                    new Thread(new Runnable() { // from class: com.tencent.liteav.basic.opengl.g.4
                        @Override // java.lang.Runnable
                        public void run() {
                            byteBufferAllocate.position(0);
                            bitmapCreateBitmap.copyPixelsFromBuffer(byteBufferAllocate);
                            Matrix matrix = new Matrix();
                            matrix.setScale(1.0f, -1.0f);
                            pVar.onTakePhotoComplete(Bitmap.createBitmap(bitmapCreateBitmap, 0, 0, i8, i9, matrix, false));
                            bitmapCreateBitmap.recycle();
                        }
                    }).start();
                }
            }
            this.f18542m = null;
            this.f18541l = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(int i2, boolean z2, int i3, int i4, int i5, int i6, int i7, boolean z3, boolean z4) {
        int i8;
        int i9;
        int i10;
        if (i6 == 0 || i7 == 0 || this.f18532c == null) {
            return;
        }
        if (this.f18543n) {
            this.f18543n = false;
            return;
        }
        if (z3) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(R2.id.ly_collection);
            GLES20.glBindFramebuffer(36160, 0);
            if (this.f18531b != null) {
                this.f18531b.c();
            }
            this.f18543n = true;
        }
        int i11 = i4 != 0 ? i4 : i6;
        int i12 = i5 != 0 ? i5 : i7;
        int i13 = this.f18537h;
        if (i13 != 0 && i13 == 1) {
            int i14 = (720 - i3) % 360;
            boolean z5 = i14 == 90 || i14 == 270;
            int[] iArrA = a(i11, i12, z5 ? i7 : i6, z5 ? i6 : i7);
            int i15 = iArrA[0];
            int i16 = iArrA[1];
            int i17 = iArrA[2];
            i9 = iArrA[3];
            i11 = i15;
            i12 = i16;
            i8 = i17;
        } else {
            i8 = 0;
            i9 = 0;
        }
        this.f18539j = i11;
        this.f18540k = i12;
        GLES20.glViewport(i8, i9, i11, i12);
        int i18 = this.f18538i;
        boolean z6 = (i18 != 1 ? !(i18 == 2 && z4) : z4) ? z2 : !z2;
        float f2 = i12 != 0 ? i11 / i12 : 1.0f;
        float f3 = i7 != 0 ? i6 / i7 : 1.0f;
        if (this.f18534e == z6 && this.f18533d == i3 && this.f18535f == f2 && this.f18536g == f3) {
            i10 = i8;
        } else {
            this.f18534e = z6;
            this.f18533d = i3;
            this.f18535f = f2;
            this.f18536g = f3;
            int i19 = (720 - i3) % 360;
            boolean z7 = i19 == 90 || i19 == 270;
            int i20 = z7 ? i12 : i11;
            if (!z7) {
                i11 = i12;
            }
            i10 = i8;
            this.f18532c.a(i6, i7, i19, m.a(l.NORMAL, false, true), i20 / i11, z7 ? false : this.f18534e, z7 ? this.f18534e : false);
            if (z7) {
                this.f18532c.g();
            } else {
                this.f18532c.h();
            }
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(R2.id.ly_collection);
        GLES20.glBindFramebuffer(36160, 0);
        this.f18532c.a(i2);
        a(i10, i9);
        if (this.f18531b != null) {
            this.f18531b.c();
        }
    }
}
