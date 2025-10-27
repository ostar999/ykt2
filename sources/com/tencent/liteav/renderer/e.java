package com.tencent.liteav.renderer;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.os.AsyncTask;
import android.view.Surface;
import android.view.TextureView;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.module.TXCKeyPointReportProxy;
import com.tencent.liteav.basic.opengl.p;
import com.tencent.liteav.basic.structs.TXSVideoFrame;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class e extends com.tencent.liteav.basic.module.a implements TextureView.SurfaceTextureListener {

    /* renamed from: a, reason: collision with root package name */
    private static final float[] f19793a = {1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f};
    private boolean G;

    /* renamed from: b, reason: collision with root package name */
    private SurfaceTexture f19794b;

    /* renamed from: d, reason: collision with root package name */
    protected TextureView f19796d;

    /* renamed from: e, reason: collision with root package name */
    protected d f19797e;

    /* renamed from: o, reason: collision with root package name */
    protected f f19807o;

    /* renamed from: p, reason: collision with root package name */
    WeakReference<com.tencent.liteav.basic.b.b> f19808p;

    /* renamed from: q, reason: collision with root package name */
    private com.tencent.liteav.basic.opengl.g f19809q;

    /* renamed from: r, reason: collision with root package name */
    private h f19810r;

    /* renamed from: s, reason: collision with root package name */
    private Surface f19811s;

    /* renamed from: u, reason: collision with root package name */
    private int f19813u;

    /* renamed from: f, reason: collision with root package name */
    protected int f19798f = 0;

    /* renamed from: g, reason: collision with root package name */
    protected int f19799g = 0;

    /* renamed from: h, reason: collision with root package name */
    protected int f19800h = 0;

    /* renamed from: i, reason: collision with root package name */
    protected int f19801i = 0;

    /* renamed from: j, reason: collision with root package name */
    protected int f19802j = 0;

    /* renamed from: c, reason: collision with root package name */
    private int f19795c = 800;

    /* renamed from: t, reason: collision with root package name */
    private int f19812t = 0;

    /* renamed from: k, reason: collision with root package name */
    protected int f19803k = 0;

    /* renamed from: v, reason: collision with root package name */
    private int f19814v = 2;

    /* renamed from: l, reason: collision with root package name */
    protected volatile int f19804l = -1;

    /* renamed from: m, reason: collision with root package name */
    protected int f19805m = 0;

    /* renamed from: n, reason: collision with root package name */
    protected int f19806n = 0;

    /* renamed from: w, reason: collision with root package name */
    private int[] f19815w = new int[5];

    /* renamed from: x, reason: collision with root package name */
    private int f19816x = 500;

    /* renamed from: y, reason: collision with root package name */
    private long f19817y = 0;

    /* renamed from: z, reason: collision with root package name */
    private long f19818z = 0;
    private long A = 0;
    private long B = 0;
    private long C = 0;
    private boolean D = false;
    private boolean E = false;
    private a H = new a();
    private boolean F = false;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public long f19829a;

        /* renamed from: b, reason: collision with root package name */
        public long f19830b;

        /* renamed from: c, reason: collision with root package name */
        public long f19831c;

        /* renamed from: d, reason: collision with root package name */
        public long f19832d;

        /* renamed from: e, reason: collision with root package name */
        public long f19833e;

        /* renamed from: f, reason: collision with root package name */
        public long f19834f;

        /* renamed from: g, reason: collision with root package name */
        public long f19835g;

        /* renamed from: h, reason: collision with root package name */
        public long f19836h;

        /* renamed from: i, reason: collision with root package name */
        public long f19837i;

        /* renamed from: j, reason: collision with root package name */
        public long f19838j;

        /* renamed from: k, reason: collision with root package name */
        public long f19839k;

        /* renamed from: l, reason: collision with root package name */
        public int f19840l;

        /* renamed from: m, reason: collision with root package name */
        public int f19841m;

        /* renamed from: n, reason: collision with root package name */
        public long f19842n;

        /* renamed from: o, reason: collision with root package name */
        public boolean f19843o = true;
    }

    public e() {
        this.G = false;
        this.G = TXCBuild.VersionInt() >= 21;
    }

    public SurfaceTexture a() {
        return null;
    }

    public void c(int i2, int i3) {
        a(i2, i3);
    }

    public void c(Object obj) {
    }

    public void d() {
    }

    public void d(int i2) {
        this.f19814v = i2;
        d dVar = this.f19797e;
        if (dVar != null) {
            if (i2 == 2) {
                dVar.a(false);
            } else {
                dVar.a(true);
            }
        }
    }

    public void e() {
        Monitor.a(2, String.format("Remote-VideoRender[%d]: Start [tinyID:%s] [streamType:%d]", Integer.valueOf(hashCode()), getID(), Integer.valueOf(this.f19802j)), "streamType: 2-big, 3-small, 7-sub", 0);
        this.E = true;
        this.F = false;
        l();
    }

    public int f() {
        TextureView textureView = this.f19796d;
        if (textureView != null) {
            return textureView.getWidth();
        }
        if (this.f19811s != null) {
            return this.f19805m;
        }
        return 0;
    }

    public int g() {
        TextureView textureView = this.f19796d;
        if (textureView != null) {
            return textureView.getHeight();
        }
        if (this.f19811s != null) {
            return this.f19806n;
        }
        return 0;
    }

    public int h() {
        return this.f19800h;
    }

    public int i() {
        return this.f19801i;
    }

    public void j() {
    }

    public void k() {
        synchronized (this) {
            if (this.f19809q != null) {
                TXCLog.i("TXCVideoRender", "surface-render: onRenderThreadEGLDestroy stop render thread " + this.f19809q);
                this.f19809q.a();
                this.f19809q = null;
            }
        }
        h hVar = this.f19810r;
        if (hVar != null) {
            hVar.c();
            this.f19810r = null;
        }
    }

    public void l() {
        m();
        a aVar = this.H;
        aVar.f19830b = 0L;
        aVar.f19831c = 0L;
        aVar.f19833e = 0L;
        aVar.f19834f = 0L;
        aVar.f19835g = 0L;
        aVar.f19836h = 0L;
        aVar.f19837i = 0L;
        aVar.f19839k = 0L;
        this.C = 0L;
        setStatusValue(6001, this.f19802j, 0L);
        setStatusValue(6003, this.f19802j, 0L);
        setStatusValue(6005, this.f19802j, 0L);
        setStatusValue(6006, this.f19802j, 0L);
        setStatusValue(6004, this.f19802j, 0L);
        setStatusValue(R2.dimen.abc_action_bar_stacked_max_height, this.f19802j, 0L);
    }

    public void m() {
        n();
        a aVar = this.H;
        aVar.f19829a = 0L;
        aVar.f19832d = 0L;
        aVar.f19838j = 0L;
        this.f19818z = 0L;
    }

    public void n() {
        a aVar = this.H;
        aVar.f19842n = 0L;
        this.B = 0L;
        this.A = 0L;
        aVar.f19840l = 0;
        aVar.f19841m = 0;
        setStatusValue(6002, this.f19802j, Double.valueOf(0.0d));
    }

    public void o() {
        a aVar = this.H;
        if (aVar.f19829a == 0) {
            aVar.f19829a = TXCTimeUtil.getTimeTick();
            return;
        }
        long timeTick = TXCTimeUtil.getTimeTick() - this.H.f19829a;
        if (timeTick >= 950) {
            setStatusValue(6002, this.f19802j, Double.valueOf(Double.valueOf(((r2.f19831c - r2.f19830b) * 1000.0d) / timeTick).doubleValue()));
            TXCKeyPointReportProxy.a(getID(), 40001, (int) r2, this.f19802j);
            a aVar2 = this.H;
            aVar2.f19830b = aVar2.f19831c;
            aVar2.f19829a += timeTick;
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
        TXCLog.w("TXCVideoRender", "play:vrender: texture available @" + surfaceTexture + "id " + getID() + StrPool.UNDERLINE + this.f19802j);
        this.f19798f = i2;
        this.f19799g = i3;
        d dVar = this.f19797e;
        if (dVar != null) {
            dVar.a(i2, i3);
        }
        if (this.f19794b != null) {
            try {
                if (TXCBuild.VersionInt() >= 16) {
                    SurfaceTexture surfaceTexture2 = this.f19796d.getSurfaceTexture();
                    SurfaceTexture surfaceTexture3 = this.f19794b;
                    if (surfaceTexture2 != surfaceTexture3) {
                        this.f19796d.setSurfaceTexture(surfaceTexture3);
                    }
                }
            } catch (Exception e2) {
                TXCLog.e("TXCVideoRender", "setSurfaceTexture failed.", e2);
                a(surfaceTexture);
            }
            this.f19794b = null;
        } else {
            a(surfaceTexture);
        }
        this.D = true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        try {
            this.D = false;
            TXCLog.w("TXCVideoRender", "play:vrender:  onSurfaceTextureDestroyed when need save texture : " + this.G + "id " + getID() + StrPool.UNDERLINE + this.f19802j);
            if (this.G) {
                this.f19794b = surfaceTexture;
            } else {
                this.H.f19829a = 0L;
                b(surfaceTexture);
                if (surfaceTexture == this.f19794b) {
                    this.f19794b = null;
                }
            }
        } catch (Exception e2) {
            TXCLog.e("TXCVideoRender", "onSurfaceTextureDestroyed failed.", e2);
        }
        return this.f19794b == null;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
        TXCLog.w("TXCVideoRender", "play:vrender: texture size change new:" + i2 + "," + i3 + " old:" + this.f19798f + "," + this.f19799g);
        if (!this.D) {
            TXCLog.w("TXCVideoRender", "play:vrender: onSurfaceCreate on onSurfaceTextureSizeChanged when onSurfaceTextureAvailable is not trigger");
            this.D = true;
            a(surfaceTexture);
        }
        this.f19798f = i2;
        this.f19799g = i3;
        d dVar = this.f19797e;
        if (dVar != null) {
            dVar.a(i2, i3);
        }
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void b(int i2) {
        if (i2 > 0) {
            this.f19795c = i2;
        }
    }

    public void c(int i2) {
        this.f19813u = i2;
        d dVar = this.f19797e;
        if (dVar != null) {
            dVar.a(i2);
        }
    }

    public void a(int i2) {
        this.f19802j = i2;
    }

    public void b(boolean z2) {
        this.G = z2;
    }

    private Bitmap a(Bitmap bitmap, int i2, int i3) {
        float height;
        float f2 = i3;
        float f3 = i2;
        if (f2 / f3 > bitmap.getHeight() / bitmap.getWidth()) {
            height = f3 / bitmap.getWidth();
        } else {
            height = f2 / bitmap.getHeight();
        }
        Matrix matrix = new Matrix();
        matrix.preScale(height, height);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        bitmap.recycle();
        return bitmapCreateBitmap;
    }

    public void b(SurfaceTexture surfaceTexture) {
        this.D = false;
    }

    private void b(TextureView textureView) {
        boolean z2 = false;
        if (textureView != null) {
            this.f19804l = 0;
        }
        TextureView textureView2 = this.f19796d;
        if ((textureView2 == null && textureView != null) || (textureView2 != null && !textureView2.equals(textureView))) {
            z2 = true;
        }
        TXCLog.w("TXCVideoRender", "play:vrender: set video view @old=" + this.f19796d + ",new=" + textureView + "id " + getID() + StrPool.UNDERLINE + this.f19802j);
        if (z2) {
            TextureView textureView3 = this.f19796d;
            if (textureView3 != null && this.f19794b == null) {
                b(textureView3.getSurfaceTexture());
                this.f19796d.setSurfaceTextureListener(null);
            }
            this.f19796d = textureView;
            if (textureView != null) {
                if (textureView.getWidth() != 0) {
                    this.f19798f = this.f19796d.getWidth();
                }
                if (this.f19796d.getHeight() != 0) {
                    this.f19799g = this.f19796d.getHeight();
                }
                d dVar = new d(this.f19796d);
                this.f19797e = dVar;
                dVar.b(this.f19800h, this.f19801i);
                this.f19797e.a(this.f19798f, this.f19799g);
                this.f19797e.a(this.f19813u);
                this.f19797e.c((this.f19812t + this.f19803k) % 360);
                d(this.f19814v);
                this.f19796d.setSurfaceTextureListener(this);
                if (this.f19794b != null) {
                    if (TXCBuild.VersionInt() >= 16 && this.f19796d.getSurfaceTexture() != this.f19794b) {
                        TXCLog.w("TXCVideoRender", "play:vrender: setSurfaceTexture " + this.f19796d + ", surfaceTexture " + this.f19794b);
                        try {
                            this.f19796d.setSurfaceTexture(this.f19794b);
                            return;
                        } catch (Exception e2) {
                            TXCLog.e("TXCVideoRender", "setSurfaceTexture error " + e2);
                            return;
                        }
                    }
                    TXCLog.w("TXCVideoRender", "play:vrender: not setSurfaceTexture old surfaceTexture " + this.f19796d.getSurfaceTexture() + ", new surfaceTexture " + this.f19794b);
                    return;
                }
                if (this.f19796d.isAvailable()) {
                    a(this.f19796d.getSurfaceTexture());
                }
            }
        }
    }

    public void c(boolean z2) {
        this.H.f19843o = z2;
    }

    public void d(final int i2, final int i3) {
        TXCLog.i("TXCVideoRender", "surface-render: set setSurfaceSize " + i2 + "*" + i3);
        if (i2 == this.f19805m && i3 == this.f19806n) {
            return;
        }
        if (this.f19809q != null && this.f19804l == 1 && this.f19815w != null) {
            this.f19809q.a(new Runnable() { // from class: com.tencent.liteav.renderer.e.3
                @Override // java.lang.Runnable
                public void run() {
                    e eVar = e.this;
                    eVar.f19805m = i2;
                    eVar.f19806n = i3;
                    if (eVar.f19809q != null) {
                        com.tencent.liteav.basic.opengl.g gVar = e.this.f19809q;
                        int i4 = e.this.f19815w[0];
                        boolean z2 = e.this.f19815w[3] == 1;
                        int i5 = e.this.f19815w[4];
                        e eVar2 = e.this;
                        gVar.a(i4, z2, i5, eVar2.f19805m, eVar2.f19806n, eVar2.f19815w[1], e.this.f19815w[2], true, false);
                    }
                }
            });
        } else {
            this.f19805m = i2;
            this.f19806n = i3;
        }
    }

    public void e(int i2) {
        this.f19812t = i2;
        d dVar = this.f19797e;
        if (dVar != null) {
            dVar.c((i2 + this.f19803k) % 360);
        }
    }

    public void f(int i2) {
        this.f19816x = i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap a(Matrix matrix, Bitmap bitmap, int i2, int i3) {
        Bitmap bitmapCreateBitmap;
        Bitmap bitmapA;
        int i4 = 360 - ((this.f19812t + this.f19803k) % 360);
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        if (i4 != 0) {
            Matrix matrix2 = new Matrix();
            matrix2.setRotate(i4);
            bitmapCreateBitmap = Bitmap.createBitmap(bitmapCreateBitmap2, 0, 0, bitmapCreateBitmap2.getWidth(), bitmapCreateBitmap2.getHeight(), matrix2, false);
            bitmapCreateBitmap2.recycle();
        } else {
            bitmapCreateBitmap = bitmapCreateBitmap2;
        }
        if (this.f19813u != 0) {
            return (i2 == bitmapCreateBitmap.getWidth() || i3 == bitmapCreateBitmap.getHeight()) ? bitmapCreateBitmap : a(bitmapCreateBitmap, i2, i3);
        }
        int width = bitmapCreateBitmap.getWidth();
        int height = bitmapCreateBitmap.getHeight();
        boolean z2 = i2 < i3;
        if (z2 != (width < height)) {
            if (z2) {
                float f2 = i2;
                float f3 = (height * f2) / i3;
                Matrix matrix3 = new Matrix();
                float f4 = f2 / f3;
                matrix3.preScale(f4, f4);
                bitmapA = Bitmap.createBitmap(bitmapCreateBitmap, (int) ((width - f3) * 0.5f), 0, (int) f3, height, matrix3, false);
                bitmapCreateBitmap.recycle();
            } else {
                float f5 = i3;
                float f6 = (width / i2) * f5;
                Matrix matrix4 = new Matrix();
                float f7 = f5 / f6;
                matrix4.preScale(f7, f7);
                Bitmap bitmapCreateBitmap3 = Bitmap.createBitmap(bitmapCreateBitmap, 0, (int) ((height - f6) * 0.5f), width, (int) f6, (Matrix) null, false);
                Bitmap bitmapCreateBitmap4 = Bitmap.createBitmap(bitmapCreateBitmap3, 0, 0, bitmapCreateBitmap3.getWidth(), bitmapCreateBitmap3.getHeight(), matrix4, false);
                bitmapCreateBitmap3.recycle();
                return bitmapCreateBitmap4;
            }
        } else {
            if (i2 == bitmapCreateBitmap.getWidth() || i3 == bitmapCreateBitmap.getHeight()) {
                return bitmapCreateBitmap;
            }
            bitmapA = a(bitmapCreateBitmap, i2, i3);
        }
        return bitmapA;
    }

    private void b(Surface surface) {
        TXCLog.i("TXCVideoRender", "surface-render: set surface " + surface);
        if (this.f19811s == surface) {
            TXCLog.i("TXCVideoRender", "surface-render: set the same surface, ignore ");
            return;
        }
        this.f19811s = surface;
        this.f19804l = 1;
        if (surface != null) {
            TXCLog.i("TXCVideoRender", "surface-render: set surface start render thread " + surface);
            c((Object) null);
            return;
        }
        synchronized (this) {
            if (this.f19809q != null) {
                TXCLog.i("TXCVideoRender", "surface-render: set surface stop render thread " + this.f19809q);
                this.f19809q.a();
                this.f19809q = null;
            }
        }
    }

    public void a(final p pVar) {
        final Bitmap bitmap;
        final TextureView textureView = this.f19796d;
        if (textureView != null) {
            try {
                bitmap = textureView.getBitmap();
            } catch (OutOfMemoryError unused) {
                bitmap = null;
            }
            if (bitmap != null) {
                final Matrix transform = textureView.getTransform(null);
                AsyncTask.execute(new Runnable() { // from class: com.tencent.liteav.renderer.e.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Bitmap bitmapA = null;
                        try {
                            bitmapA = e.this.a(transform, bitmap, textureView.getWidth(), textureView.getHeight());
                        } catch (Error e2) {
                            TXCLog.w("TXCVideoRender", "takePhoto error " + e2);
                        } catch (Exception e3) {
                            TXCLog.w("TXCVideoRender", "takePhoto error " + e3);
                        }
                        p pVar2 = pVar;
                        if (pVar2 != null) {
                            pVar2.onTakePhotoComplete(bitmapA);
                        }
                    }
                });
                return;
            }
            return;
        }
        com.tencent.liteav.basic.opengl.g gVar = this.f19809q;
        if (gVar != null) {
            gVar.a(new Runnable() { // from class: com.tencent.liteav.renderer.e.2
                @Override // java.lang.Runnable
                public void run() {
                    if (e.this.f19809q != null) {
                        e.this.f19809q.a(pVar);
                    }
                }
            });
        } else if (pVar != null) {
            pVar.onTakePhotoComplete(null);
        }
    }

    public void a(f fVar) {
        this.f19807o = fVar;
    }

    public void a(com.tencent.liteav.basic.b.b bVar) {
        this.f19808p = new WeakReference<>(bVar);
    }

    public void a(TextureView textureView) {
        b(textureView);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x02ea  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b() {
        /*
            Method dump skipped, instructions count: 757
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.e.b():void");
    }

    public void a(Surface surface) {
        b(surface);
    }

    public void a(TXSVideoFrame tXSVideoFrame, int i2, int i3, int i4) {
        if (i4 != this.f19803k) {
            this.f19803k = i4;
            e(this.f19812t);
        }
        a(i2, i3);
        b();
    }

    public void a(int i2, int i3, int i4, boolean z2, int i5) {
        a(i3, i4);
    }

    public void a(boolean z2) {
        l();
        if (this.E) {
            Object[] objArr = new Object[4];
            objArr[0] = Integer.valueOf(hashCode());
            objArr[1] = getID();
            objArr[2] = Integer.valueOf(this.f19802j);
            objArr[3] = z2 ? k.a.f27523u : k.a.f27524v;
            Monitor.a(2, String.format("Remote-VideoRender[%d]: Stop [tinyID:%s][streamType:%d][stopRendThread:%s]", objArr), "streamType: 2-big, 3-small, 7-sub", 0);
        }
        this.E = false;
        this.F = false;
        if (z2 && this.f19804l == 1) {
            this.f19804l = -1;
            TXCLog.w("TXCVideoRender", "play:vrender: quit render thread when stop");
            d();
            synchronized (this) {
                if (this.f19809q != null) {
                    TXCLog.i("TXCVideoRender", "surface-render:stop render thread " + this.f19809q);
                    this.f19809q.a();
                    this.f19809q = null;
                }
            }
        }
    }

    public void a(SurfaceTexture surfaceTexture) {
        this.D = true;
    }

    public void a(int i2, int i3) {
        int i4 = this.f19800h;
        if (i4 == i2 && this.f19801i == i3) {
            return;
        }
        if (i4 == i2 && this.f19801i == i3) {
            return;
        }
        this.f19800h = i2;
        this.f19801i = i3;
        d dVar = this.f19797e;
        if (dVar != null) {
            dVar.b(i2, i3);
        }
    }

    public void a(Object obj, int i2, float[] fArr, boolean z2) {
        Surface surfaceB;
        if (this.f19804l == 1) {
            int[] iArrA = a(i2, this.f19800h, this.f19801i, fArr, z2);
            int i3 = iArrA[0];
            int i4 = iArrA[1];
            int i5 = iArrA[2];
            System.arraycopy(iArrA, 0, this.f19815w, 0, 3);
            if (z2) {
                int[] iArr = this.f19815w;
                iArr[3] = 1;
                iArr[4] = 180;
            } else {
                int[] iArr2 = this.f19815w;
                iArr2[3] = 0;
                iArr2[4] = 0;
            }
            synchronized (this) {
                Surface surface = this.f19811s;
                if (surface != null) {
                    com.tencent.liteav.basic.opengl.g gVar = this.f19809q;
                    if (gVar != null && ((surfaceB = gVar.b()) != surface || (surfaceB != null && !surfaceB.isValid()))) {
                        TXCLog.i("TXCVideoRender", "surface-render: onDrawTextureToSurface surface change stop render thread " + this.f19809q + ", " + surfaceB + ", " + surface);
                        this.f19809q.a();
                        this.f19809q = null;
                    }
                    if (this.f19809q == null && this.f19804l == 1 && surface.isValid()) {
                        this.f19809q = new com.tencent.liteav.basic.opengl.g();
                        TXCLog.i("TXCVideoRender", "surface-render: onDrawTextureToSurface start render thread " + this.f19809q + "," + surface);
                        this.f19809q.a(obj, surface);
                    }
                    if (this.f19809q != null && this.f19804l == 1) {
                        if (z2) {
                            this.f19809q.a(i3, true, 180, this.f19805m, this.f19806n, i4, i5, false, false);
                        } else {
                            this.f19809q.a(i3, false, 0, this.f19805m, this.f19806n, i4, i5, false, false);
                        }
                    }
                } else if (this.f19809q != null) {
                    TXCLog.i("TXCVideoRender", "surface-render: onDrawTextureToSurface stop render thread " + this.f19809q);
                    this.f19809q.a();
                    this.f19809q = null;
                }
            }
        }
    }

    private int[] a(int i2, int i3, int i4, float[] fArr, boolean z2) {
        h hVar = this.f19810r;
        if (hVar != null && hVar.a() != z2) {
            this.f19810r.c();
            this.f19810r = null;
        }
        if (this.f19810r == null) {
            h hVar2 = new h(Boolean.valueOf(z2));
            this.f19810r = hVar2;
            hVar2.b();
        }
        if (fArr != null) {
            this.f19810r.a(fArr);
        } else {
            this.f19810r.a(f19793a);
        }
        int i5 = this.f19805m;
        int i6 = this.f19806n;
        if (this.f19813u == 0) {
            this.f19810r.a(h.f19844a);
        } else {
            this.f19810r.a(h.f19845b);
        }
        if (this.f19814v == 1) {
            this.f19810r.a(true);
        } else {
            this.f19810r.a(false);
        }
        int i7 = this.f19812t;
        int i8 = this.f19803k;
        int i9 = (i7 + i8) % 360;
        if (z2 && (i7 == 90 || i7 == 270)) {
            i9 = ((i7 + i8) + 180) % 360;
        }
        this.f19810r.b(i9);
        this.f19810r.b(i3, i4);
        this.f19810r.a(i5, i6);
        return new int[]{this.f19810r.d(i2), i5, i6};
    }

    private long a(long j2) {
        long timeTick = TXCTimeUtil.getTimeTick();
        if (j2 > timeTick) {
            return 0L;
        }
        return timeTick - j2;
    }
}
