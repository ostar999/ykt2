package com.tencent.liteav.renderer;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.TXCOpenGlUtils;
import java.lang.ref.WeakReference;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
class b extends Thread {

    /* renamed from: a, reason: collision with root package name */
    private WeakReference<a> f19749a;

    /* renamed from: b, reason: collision with root package name */
    private volatile boolean f19750b = false;

    /* renamed from: c, reason: collision with root package name */
    private int f19751c = 1280;

    /* renamed from: d, reason: collision with root package name */
    private int f19752d = 720;

    /* renamed from: e, reason: collision with root package name */
    private final Semaphore f19753e = new Semaphore(0);

    /* renamed from: f, reason: collision with root package name */
    private com.tencent.liteav.basic.opengl.c f19754f = null;

    /* renamed from: g, reason: collision with root package name */
    private com.tencent.liteav.basic.opengl.b f19755g = null;

    /* renamed from: h, reason: collision with root package name */
    private Object f19756h = null;

    public b(WeakReference<a> weakReference) {
        this.f19749a = weakReference;
    }

    private com.tencent.liteav.basic.util.e f() {
        com.tencent.liteav.basic.opengl.b bVar = this.f19755g;
        if (bVar != null) {
            return bVar.f();
        }
        com.tencent.liteav.basic.opengl.c cVar = this.f19754f;
        if (cVar != null) {
            return cVar.c();
        }
        TXCOpenGlUtils.a("getSurfaceSize");
        return new com.tencent.liteav.basic.util.e(0, 0);
    }

    private void g() {
        try {
            a aVar = this.f19749a.get();
            if (aVar != null) {
                aVar.a(this);
            }
        } catch (Exception e2) {
            TXCLog.e("TXCVideoRenderThread", "init texture render failed.", e2);
        }
    }

    private void h() {
        try {
            a aVar = this.f19749a.get();
            if (aVar != null) {
                aVar.b(this);
            }
        } catch (Exception e2) {
            TXCLog.e("TXCVideoRenderThread", "destroy texture render failed", e2);
        }
    }

    private void i() {
        a aVar;
        WeakReference<a> weakReference = this.f19749a;
        if (weakReference == null || (aVar = weakReference.get()) == null) {
            return;
        }
        aVar.j();
    }

    private void j() {
        a aVar;
        WeakReference<a> weakReference = this.f19749a;
        if (weakReference == null || (aVar = weakReference.get()) == null) {
            return;
        }
        aVar.k();
    }

    private void k() {
        a aVar = this.f19749a.get();
        if (aVar == null) {
            return;
        }
        SurfaceTexture surfaceTextureC = aVar.c();
        Surface surface = surfaceTextureC != null ? new Surface(surfaceTextureC) : null;
        Object obj = this.f19756h;
        if (obj == null || (obj instanceof EGLContext)) {
            this.f19755g = com.tencent.liteav.basic.opengl.b.a(null, (EGLContext) obj, surface, this.f19751c, this.f19752d);
        } else {
            this.f19754f = com.tencent.liteav.basic.opengl.c.a(null, (android.opengl.EGLContext) obj, surface, this.f19751c, this.f19752d);
        }
        TXCLog.w("TXCVideoRenderThread", "vrender: init egl share context " + this.f19756h + ", create context" + a());
        e();
    }

    private void l() {
        TXCLog.w("TXCVideoRenderThread", "vrender: uninit egl " + a());
        com.tencent.liteav.basic.opengl.b bVar = this.f19755g;
        if (bVar != null) {
            bVar.c();
            this.f19755g = null;
        }
        com.tencent.liteav.basic.opengl.c cVar = this.f19754f;
        if (cVar != null) {
            cVar.d();
            this.f19754f = null;
        }
    }

    public void a(Object obj) {
        this.f19756h = obj;
    }

    public void b() {
        this.f19750b = false;
        c();
    }

    public void c() {
        this.f19753e.release();
    }

    public void d() {
        com.tencent.liteav.basic.opengl.b bVar = this.f19755g;
        if (bVar != null) {
            bVar.a();
        }
        com.tencent.liteav.basic.opengl.c cVar = this.f19754f;
        if (cVar != null) {
            cVar.e();
        }
    }

    public void e() {
        com.tencent.liteav.basic.opengl.b bVar = this.f19755g;
        if (bVar != null) {
            bVar.b();
        }
        com.tencent.liteav.basic.opengl.c cVar = this.f19754f;
        if (cVar != null) {
            cVar.b();
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        setName("VRender-" + getId());
        try {
            this.f19750b = true;
            k();
            g();
            i();
            while (this.f19750b) {
                com.tencent.liteav.basic.util.e eVarF = f();
                if (a(eVarF.f18712a, eVarF.f18713b)) {
                    WeakReference<a> weakReference = this.f19749a;
                    a aVar = weakReference == null ? null : weakReference.get();
                    if (aVar != null && aVar.c() != null) {
                        d();
                    }
                }
                while (this.f19750b && !this.f19753e.tryAcquire(500L, TimeUnit.MILLISECONDS)) {
                }
            }
            j();
            h();
            l();
        } catch (Exception e2) {
            TXCLog.e("TXCVideoRenderThread", "render failed.", e2);
        }
    }

    public Object a() {
        com.tencent.liteav.basic.opengl.b bVar = this.f19755g;
        if (bVar != null) {
            return bVar.d();
        }
        com.tencent.liteav.basic.opengl.c cVar = this.f19754f;
        if (cVar != null) {
            return cVar.f();
        }
        return null;
    }

    private boolean a(int i2, int i3) {
        a aVar;
        try {
            WeakReference<a> weakReference = this.f19749a;
            if (weakReference == null || (aVar = weakReference.get()) == null) {
                return false;
            }
            return aVar.b(i2, i3);
        } catch (Exception e2) {
            TXCLog.e("TXCVideoRenderThread", "drawFrame failed." + e2.getMessage());
            return false;
        }
    }
}
