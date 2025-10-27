package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.view.TextureView;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.structs.TXSVideoFrame;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.Queue;

/* loaded from: classes6.dex */
public class a extends e implements SurfaceTexture.OnFrameAvailableListener {
    private c A;
    private TXSVideoFrame B;
    private TXCYuvTextureRender C;
    private h F;
    private TXCYuvTextureRender G;

    /* renamed from: a, reason: collision with root package name */
    g f19736a;

    /* renamed from: b, reason: collision with root package name */
    InterfaceC0336a f19737b;

    /* renamed from: c, reason: collision with root package name */
    InterfaceC0336a f19738c;

    /* renamed from: v, reason: collision with root package name */
    private b f19744v;

    /* renamed from: w, reason: collision with root package name */
    private SurfaceTexture f19745w;

    /* renamed from: x, reason: collision with root package name */
    private c f19746x;

    /* renamed from: y, reason: collision with root package name */
    private boolean f19747y;

    /* renamed from: q, reason: collision with root package name */
    private final int f19739q = 0;

    /* renamed from: r, reason: collision with root package name */
    private final int f19740r = 0;

    /* renamed from: s, reason: collision with root package name */
    private final int f19741s = 0;

    /* renamed from: t, reason: collision with root package name */
    private final int f19742t = 0;

    /* renamed from: u, reason: collision with root package name */
    private Object f19743u = new Object();
    private Object D = null;
    private Object E = new Object();
    private final Queue<Runnable> H = new LinkedList();

    /* renamed from: z, reason: collision with root package name */
    private float[] f19748z = new float[16];

    /* renamed from: com.tencent.liteav.renderer.a$a, reason: collision with other inner class name */
    public interface InterfaceC0336a {
        void onTextureProcess(int i2, int i3, int i4, int i5);
    }

    private boolean e(int i2, int i3) {
        TXSVideoFrame tXSVideoFrame;
        TXCYuvTextureRender tXCYuvTextureRender;
        int iDrawToTexture;
        c cVar;
        synchronized (this) {
            boolean z2 = this.f19747y;
            if (z2) {
                this.f19747y = false;
                tXSVideoFrame = null;
            } else {
                TXSVideoFrame tXSVideoFrame2 = this.B;
                if (tXSVideoFrame2 == null) {
                    return false;
                }
                this.B = null;
                tXSVideoFrame = tXSVideoFrame2;
                z2 = false;
            }
            if (i2 <= 0 || i3 <= 0) {
                return false;
            }
            GLES20.glViewport(0, 0, i2, i3);
            Object objB = this.f19804l == 1 ? b() : null;
            InterfaceC0336a interfaceC0336a = this.f19738c;
            if (z2) {
                SurfaceTexture surfaceTexture = this.f19745w;
                if (surfaceTexture != null) {
                    surfaceTexture.updateTexImage();
                    this.f19745w.getTransformMatrix(this.f19748z);
                }
                g gVar = this.f19736a;
                if (gVar != null) {
                    c cVar2 = this.f19746x;
                    if (cVar2 != null) {
                        gVar.a(cVar2.a(), this.f19748z);
                    }
                } else if (this.f19746x != null) {
                    GLES20.glBindFramebuffer(36160, 0);
                    this.f19746x.a(this.f19745w);
                }
                if (interfaceC0336a != null) {
                    int iA = this.f19746x.a();
                    if (this.F == null) {
                        h hVar = new h(Boolean.TRUE);
                        this.F = hVar;
                        hVar.b();
                        this.F.a(true);
                        this.F.b(180);
                        this.F.a(h.f19844a);
                    }
                    this.F.a(this.f19748z);
                    this.F.b(this.f19800h, this.f19801i);
                    this.F.a(this.f19800h, this.f19801i);
                    interfaceC0336a.onTextureProcess(this.F.d(iA), h(), i(), this.f19803k);
                }
                if (this.f19804l == 1 && (cVar = this.f19746x) != null) {
                    a(objB, cVar.a(), this.f19748z, true);
                }
            } else if (tXSVideoFrame != null && (tXCYuvTextureRender = this.C) != null) {
                if (this.f19737b != null) {
                    tXCYuvTextureRender.setHasFrameBuffer(this.f19800h, this.f19801i);
                    iDrawToTexture = this.C.drawToTexture(tXSVideoFrame);
                    this.f19737b.onTextureProcess(iDrawToTexture, h(), i(), this.f19803k);
                } else {
                    if (this.f19804l == 0) {
                        GLES20.glBindFramebuffer(36160, 0);
                        this.C.drawFrame(tXSVideoFrame);
                    }
                    iDrawToTexture = -1;
                }
                if (this.f19804l == 1) {
                    if (iDrawToTexture == -1) {
                        this.C.setHasFrameBuffer(this.f19800h, this.f19801i);
                        iDrawToTexture = this.C.drawToTexture(tXSVideoFrame);
                    }
                    a(objB, iDrawToTexture, (float[]) null, false);
                }
                if (interfaceC0336a != null) {
                    if (this.G == null) {
                        TXCYuvTextureRender tXCYuvTextureRender2 = new TXCYuvTextureRender();
                        this.G = tXCYuvTextureRender2;
                        tXCYuvTextureRender2.createTexture();
                        this.G.flipVertical(false);
                    }
                    this.G.setHasFrameBuffer(this.f19800h, this.f19801i);
                    interfaceC0336a.onTextureProcess(this.G.drawToTexture(tXSVideoFrame), h(), i(), this.f19803k);
                }
            }
            return true;
        }
    }

    private void p() {
        this.f19746x = new c(true);
        this.C = new TXCYuvTextureRender();
        this.A = new c(false);
    }

    public void a(g gVar) {
        this.f19736a = gVar;
    }

    public void b(InterfaceC0336a interfaceC0336a) {
        TXCYuvTextureRender tXCYuvTextureRender;
        this.f19738c = interfaceC0336a;
        if (interfaceC0336a == null || (tXCYuvTextureRender = this.C) == null) {
            return;
        }
        tXCYuvTextureRender.setHasFrameBuffer(this.f19800h, this.f19801i);
    }

    public SurfaceTexture c() {
        TextureView textureView = this.f19796d;
        if (textureView != null) {
            return textureView.getSurfaceTexture();
        }
        return null;
    }

    @Override // com.tencent.liteav.renderer.e
    public void d() {
        synchronized (this.f19743u) {
            b bVar = this.f19744v;
            if (bVar != null) {
                bVar.b();
                this.f19744v.c();
                this.f19744v = null;
                TXCLog.w("TXCVideoRender", "play:vrender: quit render thread id" + getID() + ", " + this);
            }
        }
    }

    @Override // com.tencent.liteav.basic.module.a
    public void finalize() throws Throwable {
        super.finalize();
        TXCLog.w("TXCVideoRender", "play:vrender: quit render thread when finalize");
        try {
            d();
        } catch (Exception e2) {
            TXCLog.e("TXCVideoRender", "quit render thread failed.", e2);
        }
    }

    @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        synchronized (this) {
            this.f19747y = true;
        }
        synchronized (this.f19743u) {
            b bVar = this.f19744v;
            if (bVar != null) {
                bVar.c();
            }
        }
    }

    @Override // com.tencent.liteav.renderer.e, android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void a(InterfaceC0336a interfaceC0336a) {
        TXCYuvTextureRender tXCYuvTextureRender;
        this.f19737b = interfaceC0336a;
        if (interfaceC0336a == null || (tXCYuvTextureRender = this.C) == null) {
            return;
        }
        tXCYuvTextureRender.setHasFrameBuffer(this.f19800h, this.f19801i);
    }

    @Override // com.tencent.liteav.renderer.e
    public void c(Object obj) {
        synchronized (this.f19743u) {
            if (this.f19744v == null) {
                b bVar = new b(new WeakReference(this));
                this.f19744v = bVar;
                bVar.a(obj);
                this.f19744v.start();
                this.f19744v.c();
                TXCLog.w("TXCVideoRender", "play:vrender: start render thread id " + getID() + ", glContext " + obj + ", " + this);
            } else {
                TXCLog.w("TXCVideoRender", "play:vrender: start render thread when running " + getID() + ", " + this);
            }
        }
    }

    public Object b() {
        Object objA;
        synchronized (this.f19743u) {
            b bVar = this.f19744v;
            objA = bVar != null ? bVar.a() : null;
        }
        return objA;
    }

    @Override // com.tencent.liteav.renderer.e
    public void a(TXSVideoFrame tXSVideoFrame, int i2, int i3, int i4) {
        synchronized (this) {
            TXSVideoFrame tXSVideoFrame2 = this.B;
            if (tXSVideoFrame2 != null) {
                tXSVideoFrame2.release();
            }
            this.B = tXSVideoFrame;
        }
        super.a(tXSVideoFrame, i2, i3, i4);
        synchronized (this.f19743u) {
            b bVar = this.f19744v;
            if (bVar != null) {
                bVar.c();
            }
        }
    }

    @Override // com.tencent.liteav.renderer.e
    public void b(SurfaceTexture surfaceTexture) {
        super.b(surfaceTexture);
        TXCLog.w("TXCVideoRender", "play:vrender: quit render thread when onSurfaceRelease");
        d();
    }

    public void b(Object obj) {
        synchronized (this.E) {
            if (this.D != obj) {
                TXCLog.w("TXCVideoRender", "play:vrender: TXCGLRender destroyTextureRender ignore when not the same gl thread " + this);
                return;
            }
            this.D = null;
            TXCLog.w("TXCVideoRender", "play:vrender: TXCGLRender destroyTextureRender " + this);
            try {
                f fVar = this.f19807o;
                if (fVar != null) {
                    fVar.onSurfaceTextureDestroy(this.f19745w);
                }
            } catch (Exception e2) {
                TXCLog.e("TXCVideoRender", "callback failed.", e2);
            }
            c cVar = this.f19746x;
            if (cVar != null) {
                cVar.c();
                this.f19746x = null;
            }
            TXCYuvTextureRender tXCYuvTextureRender = this.C;
            if (tXCYuvTextureRender != null) {
                tXCYuvTextureRender.onSurfaceDestroy();
                this.C = null;
            }
            c cVar2 = this.A;
            if (cVar2 != null) {
                cVar2.c();
                this.A = null;
            }
            this.f19745w = null;
            h hVar = this.F;
            if (hVar != null) {
                hVar.c();
                this.F = null;
            }
            TXCYuvTextureRender tXCYuvTextureRender2 = this.G;
            if (tXCYuvTextureRender2 != null) {
                tXCYuvTextureRender2.onSurfaceDestroy();
                this.G = null;
            }
        }
    }

    @Override // com.tencent.liteav.renderer.e
    public void a(int i2, int i3, int i4, boolean z2, int i5) {
        GLES20.glViewport(0, 0, f(), g());
        c cVar = this.A;
        if (cVar != null) {
            cVar.a(i2, z2, i5);
        }
        super.a(i2, i3, i4, z2, i5);
        synchronized (this.f19743u) {
            b bVar = this.f19744v;
            if (bVar != null) {
                bVar.c();
            }
        }
    }

    @Override // com.tencent.liteav.renderer.e
    public SurfaceTexture a() {
        return this.f19745w;
    }

    @Override // com.tencent.liteav.renderer.e
    public void a(SurfaceTexture surfaceTexture) {
        super.a(surfaceTexture);
        TXCLog.w("TXCVideoRender", "play:vrender: create render thread when onSurfaceCreate");
        c((Object) null);
    }

    @Override // com.tencent.liteav.renderer.e
    public void a(int i2, int i3) {
        super.a(i2, i3);
        TXCYuvTextureRender tXCYuvTextureRender = this.C;
        if (tXCYuvTextureRender != null) {
            tXCYuvTextureRender.setVideoSize(i2, i3);
        }
        c cVar = this.f19746x;
        if (cVar != null) {
            cVar.a(i2, i3);
        }
    }

    public void a(Object obj) {
        TXCYuvTextureRender tXCYuvTextureRender;
        synchronized (this.E) {
            this.D = obj;
            TXCLog.w("TXCVideoRender", "play:vrender: TXCGLRender initTextureRender " + this);
            p();
            d dVar = this.f19797e;
            if (dVar != null) {
                dVar.a(this.f19798f, this.f19799g);
                this.f19797e.b(this.f19800h, this.f19801i);
            }
            c cVar = this.f19746x;
            if (cVar != null) {
                cVar.b();
                SurfaceTexture surfaceTexture = new SurfaceTexture(this.f19746x.a());
                this.f19745w = surfaceTexture;
                surfaceTexture.setOnFrameAvailableListener(this);
            }
            TXCYuvTextureRender tXCYuvTextureRender2 = this.C;
            if (tXCYuvTextureRender2 != null) {
                tXCYuvTextureRender2.createTexture();
            }
            if (this.f19737b != null && (tXCYuvTextureRender = this.C) != null) {
                tXCYuvTextureRender.setHasFrameBuffer(this.f19800h, this.f19801i);
            }
            c cVar2 = this.A;
            if (cVar2 != null) {
                cVar2.b();
            }
            f fVar = this.f19807o;
            if (fVar != null) {
                fVar.onSurfaceTextureAvailable(this.f19745w);
            }
        }
    }

    public boolean b(int i2, int i3) {
        while (a(this.H)) {
        }
        return e(i2, i3);
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
}
