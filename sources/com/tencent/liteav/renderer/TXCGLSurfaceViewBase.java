package com.tencent.liteav.renderer;

import android.content.Context;
import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;
import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes6.dex */
public class TXCGLSurfaceViewBase extends SurfaceView implements SurfaceHolder.Callback {

    /* renamed from: a, reason: collision with root package name */
    private static final j f19670a = new j();

    /* renamed from: b, reason: collision with root package name */
    protected boolean f19671b;

    /* renamed from: c, reason: collision with root package name */
    protected boolean f19672c;

    /* renamed from: d, reason: collision with root package name */
    protected final WeakReference<TXCGLSurfaceViewBase> f19673d;

    /* renamed from: e, reason: collision with root package name */
    protected boolean f19674e;

    /* renamed from: f, reason: collision with root package name */
    protected boolean f19675f;

    /* renamed from: g, reason: collision with root package name */
    private i f19676g;

    /* renamed from: h, reason: collision with root package name */
    private GLSurfaceView.Renderer f19677h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f19678i;

    /* renamed from: j, reason: collision with root package name */
    private e f19679j;

    /* renamed from: k, reason: collision with root package name */
    private f f19680k;

    /* renamed from: l, reason: collision with root package name */
    private g f19681l;

    /* renamed from: m, reason: collision with root package name */
    private k f19682m;

    /* renamed from: n, reason: collision with root package name */
    private int f19683n;

    /* renamed from: o, reason: collision with root package name */
    private int f19684o;

    /* renamed from: p, reason: collision with root package name */
    private boolean f19685p;

    public interface e {
        EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay);
    }

    public interface f {
        EGLContext a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig);

        void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext);
    }

    public interface g {
        EGLSurface a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj);

        void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface);
    }

    public static class i extends Thread {

        /* renamed from: a, reason: collision with root package name */
        private boolean f19707a;

        /* renamed from: b, reason: collision with root package name */
        private boolean f19708b;

        /* renamed from: c, reason: collision with root package name */
        private boolean f19709c;

        /* renamed from: d, reason: collision with root package name */
        private boolean f19710d;

        /* renamed from: e, reason: collision with root package name */
        private boolean f19711e;

        /* renamed from: f, reason: collision with root package name */
        private boolean f19712f;

        /* renamed from: g, reason: collision with root package name */
        private boolean f19713g;

        /* renamed from: h, reason: collision with root package name */
        private boolean f19714h;

        /* renamed from: i, reason: collision with root package name */
        private boolean f19715i;

        /* renamed from: j, reason: collision with root package name */
        private boolean f19716j;

        /* renamed from: k, reason: collision with root package name */
        private boolean f19717k;

        /* renamed from: p, reason: collision with root package name */
        private boolean f19722p;

        /* renamed from: s, reason: collision with root package name */
        private h f19725s;

        /* renamed from: t, reason: collision with root package name */
        private WeakReference<TXCGLSurfaceViewBase> f19726t;

        /* renamed from: q, reason: collision with root package name */
        private ArrayList<Runnable> f19723q = new ArrayList<>();

        /* renamed from: r, reason: collision with root package name */
        private boolean f19724r = true;

        /* renamed from: l, reason: collision with root package name */
        private int f19718l = 0;

        /* renamed from: m, reason: collision with root package name */
        private int f19719m = 0;

        /* renamed from: o, reason: collision with root package name */
        private boolean f19721o = true;

        /* renamed from: n, reason: collision with root package name */
        private int f19720n = 1;

        public i(WeakReference<TXCGLSurfaceViewBase> weakReference) {
            this.f19726t = weakReference;
        }

        /* JADX WARN: Removed duplicated region for block: B:165:0x022d A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private void j() throws java.lang.InterruptedException {
            /*
                Method dump skipped, instructions count: 568
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.renderer.TXCGLSurfaceViewBase.i.j():void");
        }

        private void k() {
            if (this.f19715i) {
                this.f19715i = false;
                this.f19725s.g();
            }
        }

        private void l() {
            if (this.f19714h) {
                this.f19725s.h();
                this.f19714h = false;
                TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f19726t.get();
                if (tXCGLSurfaceViewBase != null) {
                    tXCGLSurfaceViewBase.f19675f = false;
                }
                TXCGLSurfaceViewBase.f19670a.c(this);
            }
        }

        private boolean m() {
            return !this.f19710d && this.f19711e && !this.f19712f && this.f19718l > 0 && this.f19719m > 0 && (this.f19721o || this.f19720n == 1);
        }

        public int b() {
            return this.f19725s.d();
        }

        public h c() {
            return this.f19725s;
        }

        public boolean d() {
            return this.f19714h && this.f19715i && m();
        }

        public int e() {
            int i2;
            synchronized (TXCGLSurfaceViewBase.f19670a) {
                i2 = this.f19720n;
            }
            return i2;
        }

        public void f() {
            synchronized (TXCGLSurfaceViewBase.f19670a) {
                this.f19711e = true;
                this.f19716j = false;
                TXCGLSurfaceViewBase.f19670a.notifyAll();
                while (this.f19713g && !this.f19716j && !this.f19708b) {
                    try {
                        TXCGLSurfaceViewBase.f19670a.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void g() {
            synchronized (TXCGLSurfaceViewBase.f19670a) {
                this.f19711e = false;
                TXCGLSurfaceViewBase.f19670a.notifyAll();
                while (!this.f19713g && !this.f19708b) {
                    try {
                        TXCGLSurfaceViewBase.f19670a.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void h() {
            synchronized (TXCGLSurfaceViewBase.f19670a) {
                this.f19707a = true;
                TXCGLSurfaceViewBase.f19670a.notifyAll();
                while (!this.f19708b) {
                    try {
                        TXCGLSurfaceViewBase.f19670a.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void i() {
            this.f19717k = true;
            TXCGLSurfaceViewBase.f19670a.notifyAll();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            setName("GLThread " + getId());
            try {
                j();
            } catch (InterruptedException unused) {
            } catch (Throwable th) {
                TXCGLSurfaceViewBase.f19670a.a(this);
                throw th;
            }
            TXCGLSurfaceViewBase.f19670a.a(this);
        }

        public boolean a() {
            return this.f19725s.c();
        }

        public void a(int i2) {
            if (i2 >= 0 && i2 <= 1) {
                synchronized (TXCGLSurfaceViewBase.f19670a) {
                    this.f19720n = i2;
                    TXCGLSurfaceViewBase.f19670a.notifyAll();
                }
                return;
            }
            throw new IllegalArgumentException("renderMode");
        }

        public void a(int i2, int i3) {
            synchronized (TXCGLSurfaceViewBase.f19670a) {
                this.f19718l = i2;
                this.f19719m = i3;
                this.f19724r = true;
                this.f19721o = true;
                this.f19722p = false;
                TXCGLSurfaceViewBase.f19670a.notifyAll();
                while (!this.f19708b && !this.f19710d && !this.f19722p && d()) {
                    try {
                        TXCGLSurfaceViewBase.f19670a.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void a(Runnable runnable) {
            if (runnable != null) {
                synchronized (TXCGLSurfaceViewBase.f19670a) {
                    this.f19723q.add(runnable);
                    TXCGLSurfaceViewBase.f19670a.notifyAll();
                }
                return;
            }
            throw new IllegalArgumentException("r must not be null");
        }
    }

    public interface k {
        GL a(GL gl);
    }

    public static class l extends Writer {

        /* renamed from: a, reason: collision with root package name */
        private StringBuilder f19734a = new StringBuilder();

        private void a() {
            if (this.f19734a.length() > 0) {
                TXCLog.v("TXCGLSurfaceViewBase", this.f19734a.toString());
                StringBuilder sb = this.f19734a;
                sb.delete(0, sb.length());
            }
        }

        @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            a();
        }

        @Override // java.io.Writer, java.io.Flushable
        public void flush() {
            a();
        }

        @Override // java.io.Writer
        public void write(char[] cArr, int i2, int i3) {
            for (int i4 = 0; i4 < i3; i4++) {
                char c3 = cArr[i2 + i4];
                if (c3 == '\n') {
                    a();
                } else {
                    this.f19734a.append(c3);
                }
            }
        }
    }

    public class m extends b {
        public m(boolean z2) {
            super(8, 8, 8, 0, z2 ? 16 : 0, 0);
        }
    }

    public TXCGLSurfaceViewBase(Context context) {
        super(context);
        this.f19671b = false;
        this.f19672c = false;
        this.f19673d = new WeakReference<>(this);
        a();
    }

    public void b() {
    }

    public int c() {
        return 0;
    }

    public void finalize() throws Throwable {
        try {
            i iVar = this.f19676g;
            if (iVar != null) {
                iVar.h();
            }
        } finally {
            super.finalize();
        }
    }

    public int getDebugFlags() {
        return this.f19683n;
    }

    public h getEGLHelper() {
        return this.f19676g.c();
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.f19685p;
    }

    public int getRenderMode() {
        return this.f19676g.e();
    }

    @Override // android.view.SurfaceView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.f19678i && this.f19677h != null) {
            i iVar = this.f19676g;
            int iE = iVar != null ? iVar.e() : 1;
            i iVar2 = new i(this.f19673d);
            this.f19676g = iVar2;
            if (iE != 1) {
                iVar2.a(iE);
            }
            this.f19676g.start();
        }
        this.f19678i = false;
    }

    @Override // android.view.SurfaceView, android.view.View
    public void onDetachedFromWindow() {
        if (this.f19671b && this.f19676g != null) {
            TXCLog.w("TXCGLSurfaceViewBase", "background capture destroy surface when onDetachedFromWindow");
            this.f19676g.a(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceViewBase.3
                @Override // java.lang.Runnable
                public void run() {
                    TXCGLSurfaceViewBase.this.b();
                }
            });
            this.f19676g.g();
        }
        i iVar = this.f19676g;
        if (iVar != null) {
            iVar.h();
        }
        this.f19678i = true;
        super.onDetachedFromWindow();
    }

    public void setDebugFlags(int i2) {
        this.f19683n = i2;
    }

    public void setEGLConfigChooser(e eVar) {
        g();
        this.f19679j = eVar;
    }

    public void setEGLContextClientVersion(int i2) {
        g();
        this.f19684o = i2;
    }

    public void setEGLContextFactory(f fVar) {
        g();
        this.f19680k = fVar;
    }

    public void setEGLWindowSurfaceFactory(g gVar) {
        g();
        this.f19681l = gVar;
    }

    public void setGLWrapper(k kVar) {
        this.f19682m = kVar;
    }

    public void setPreserveEGLContextOnPause(boolean z2) {
        this.f19685p = z2;
    }

    public void setRenderMode(int i2) {
        this.f19676g.a(i2);
    }

    public void setRenderer(GLSurfaceView.Renderer renderer) {
        g();
        if (this.f19679j == null) {
            this.f19679j = new m(true);
        }
        if (this.f19680k == null) {
            this.f19680k = new c();
        }
        if (this.f19681l == null) {
            this.f19681l = new d();
        }
        this.f19677h = renderer;
        i iVar = new i(this.f19673d);
        this.f19676g = iVar;
        iVar.start();
        TXCLog.i("TXCGLSurfaceViewBase", "setRenderer-->mGLThread.start");
    }

    public void setRunInBackground(boolean z2) {
        this.f19672c = z2;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
        this.f19676g.a(i3, i4);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.f19676g.f();
        setRunInBackground(false);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        setRunInBackground(true);
        if (this.f19671b) {
            return;
        }
        this.f19676g.a(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceViewBase.1
            @Override // java.lang.Runnable
            public void run() {
                TXCGLSurfaceViewBase.this.b();
            }
        });
        this.f19676g.g();
    }

    private void a() {
        getHolder().addCallback(this);
    }

    private void g() {
        if (this.f19676g != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    public void b(boolean z2) {
        this.f19671b = z2;
        if (z2 || !this.f19672c || this.f19676g == null) {
            return;
        }
        TXCLog.w("TXCGLSurfaceViewBase", "background capture destroy surface when not enable background run");
        this.f19676g.a(new Runnable() { // from class: com.tencent.liteav.renderer.TXCGLSurfaceViewBase.2
            @Override // java.lang.Runnable
            public void run() {
                TXCGLSurfaceViewBase.this.b();
            }
        });
        this.f19676g.g();
    }

    public boolean d() {
        return this.f19676g.a();
    }

    public int e() {
        return this.f19676g.b();
    }

    public static class d implements g {
        private d() {
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.g
        public EGLSurface a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj) {
            try {
                return egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, null);
            } catch (IllegalArgumentException e2) {
                TXCLog.e("TXCGLSurfaceViewBase", "eglCreateWindowSurface");
                TXCLog.e("TXCGLSurfaceViewBase", e2.toString());
                return null;
            }
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.g
        public void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
            egl10.eglDestroySurface(eGLDisplay, eGLSurface);
        }
    }

    public static class j {

        /* renamed from: a, reason: collision with root package name */
        private static String f19727a = "GLThreadManager";

        /* renamed from: b, reason: collision with root package name */
        private boolean f19728b;

        /* renamed from: c, reason: collision with root package name */
        private int f19729c;

        /* renamed from: d, reason: collision with root package name */
        private boolean f19730d;

        /* renamed from: e, reason: collision with root package name */
        private boolean f19731e;

        /* renamed from: f, reason: collision with root package name */
        private boolean f19732f;

        /* renamed from: g, reason: collision with root package name */
        private i f19733g;

        private j() {
        }

        public synchronized void a(i iVar) {
            iVar.f19708b = true;
            if (this.f19733g == iVar) {
                this.f19733g = null;
            }
            notifyAll();
        }

        public boolean b(i iVar) {
            i iVar2 = this.f19733g;
            if (iVar2 == iVar || iVar2 == null) {
                this.f19733g = iVar;
                notifyAll();
                return true;
            }
            c();
            if (this.f19731e) {
                return true;
            }
            i iVar3 = this.f19733g;
            if (iVar3 == null) {
                return false;
            }
            iVar3.i();
            return false;
        }

        public void c(i iVar) {
            if (this.f19733g == iVar) {
                this.f19733g = null;
            }
            notifyAll();
        }

        private void c() {
            this.f19729c = 131072;
            this.f19731e = true;
            this.f19728b = true;
        }

        public synchronized boolean a() {
            return this.f19732f;
        }

        public synchronized void a(GL10 gl10) {
            if (!this.f19730d) {
                c();
                String strGlGetString = gl10.glGetString(R2.dimen.material_divider_thickness);
                if (this.f19729c < 131072) {
                    this.f19731e = !strGlGetString.startsWith("Q3Dimension MSM7500 ");
                    notifyAll();
                }
                this.f19732f = this.f19731e ? false : true;
                this.f19730d = true;
            }
        }

        public synchronized boolean b() {
            c();
            return !this.f19731e;
        }
    }

    public void setEGLConfigChooser(boolean z2) {
        setEGLConfigChooser(new m(z2));
    }

    public class c implements f {

        /* renamed from: b, reason: collision with root package name */
        private int f19700b;

        private c() {
            this.f19700b = R2.drawable.shape_light_yellow_bg;
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.f
        public EGLContext a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
            int[] iArr = {this.f19700b, TXCGLSurfaceViewBase.this.f19684o, R2.drawable.shape_coupon_record_bg};
            EGLContext eGLContext = EGL10.EGL_NO_CONTEXT;
            if (TXCGLSurfaceViewBase.this.f19684o == 0) {
                iArr = null;
            }
            return egl10.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.f
        public void a(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
            if (egl10.eglDestroyContext(eGLDisplay, eGLContext)) {
                return;
            }
            TXCLog.e("DefaultContextFactory", "display:" + eGLDisplay + " context: " + eGLContext);
            h.a("eglDestroyContex", egl10.eglGetError());
        }
    }

    public void a(int i2, int i3, int i4, int i5, int i6, int i7) {
        setEGLConfigChooser(new b(i2, i3, i4, i5, i6, i7));
    }

    public TXCGLSurfaceViewBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f19671b = false;
        this.f19672c = false;
        this.f19673d = new WeakReference<>(this);
        a();
    }

    public abstract class a implements e {

        /* renamed from: a, reason: collision with root package name */
        protected int[] f19689a;

        public a(int[] iArr) {
            this.f19689a = a(iArr);
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.e
        public EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            if (!egl10.eglChooseConfig(eGLDisplay, this.f19689a, null, 0, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }
            int i2 = iArr[0];
            if (i2 <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            EGLConfig[] eGLConfigArr = new EGLConfig[i2];
            if (!egl10.eglChooseConfig(eGLDisplay, this.f19689a, eGLConfigArr, i2, iArr)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            EGLConfig eGLConfigA = a(egl10, eGLDisplay, eGLConfigArr);
            if (eGLConfigA != null) {
                return eGLConfigA;
            }
            throw new IllegalArgumentException("No config chosen");
        }

        public abstract EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr);

        private int[] a(int[] iArr) {
            if (TXCGLSurfaceViewBase.this.f19684o != 2) {
                return iArr;
            }
            int length = iArr.length;
            int[] iArr2 = new int[length + 2];
            int i2 = length - 1;
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            iArr2[i2] = 12352;
            iArr2[length] = 4;
            iArr2[length + 1] = 12344;
            return iArr2;
        }
    }

    public class b extends a {

        /* renamed from: c, reason: collision with root package name */
        protected int f19691c;

        /* renamed from: d, reason: collision with root package name */
        protected int f19692d;

        /* renamed from: e, reason: collision with root package name */
        protected int f19693e;

        /* renamed from: f, reason: collision with root package name */
        protected int f19694f;

        /* renamed from: g, reason: collision with root package name */
        protected int f19695g;

        /* renamed from: h, reason: collision with root package name */
        protected int f19696h;

        /* renamed from: j, reason: collision with root package name */
        private int[] f19698j;

        public b(int i2, int i3, int i4, int i5, int i6, int i7) {
            super(new int[]{R2.drawable.shape_color2a_bottom_corner12, i2, R2.drawable.shape_color22_top_corner12, i3, R2.drawable.shape_color22_normal, i4, R2.drawable.shape_color22_corner12, i5, R2.drawable.shape_color2a_corner12, i6, R2.drawable.shape_color2a_normal, i7, R2.drawable.shape_coupon_record_bg});
            this.f19698j = new int[1];
            this.f19691c = i2;
            this.f19692d = i3;
            this.f19693e = i4;
            this.f19694f = i5;
            this.f19695g = i6;
            this.f19696h = i7;
        }

        @Override // com.tencent.liteav.renderer.TXCGLSurfaceViewBase.a
        public EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            for (EGLConfig eGLConfig : eGLConfigArr) {
                int iA = a(egl10, eGLDisplay, eGLConfig, R2.drawable.shape_color2a_corner12, 0);
                int iA2 = a(egl10, eGLDisplay, eGLConfig, R2.drawable.shape_color2a_normal, 0);
                if (iA >= this.f19695g && iA2 >= this.f19696h) {
                    int iA3 = a(egl10, eGLDisplay, eGLConfig, R2.drawable.shape_color2a_bottom_corner12, 0);
                    int iA4 = a(egl10, eGLDisplay, eGLConfig, R2.drawable.shape_color22_top_corner12, 0);
                    int iA5 = a(egl10, eGLDisplay, eGLConfig, R2.drawable.shape_color22_normal, 0);
                    int iA6 = a(egl10, eGLDisplay, eGLConfig, R2.drawable.shape_color22_corner12, 0);
                    if (iA3 == this.f19691c && iA4 == this.f19692d && iA5 == this.f19693e && iA6 == this.f19694f) {
                        return eGLConfig;
                    }
                }
            }
            return null;
        }

        private int a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i2, int i3) {
            return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i2, this.f19698j) ? this.f19698j[0] : i3;
        }
    }

    public static class h {

        /* renamed from: a, reason: collision with root package name */
        EGL10 f19701a;

        /* renamed from: b, reason: collision with root package name */
        EGLDisplay f19702b;

        /* renamed from: c, reason: collision with root package name */
        EGLSurface f19703c;

        /* renamed from: d, reason: collision with root package name */
        EGLConfig f19704d;

        /* renamed from: e, reason: collision with root package name */
        EGLContext f19705e;

        /* renamed from: f, reason: collision with root package name */
        private WeakReference<TXCGLSurfaceViewBase> f19706f;

        public h(WeakReference<TXCGLSurfaceViewBase> weakReference) {
            this.f19706f = weakReference;
        }

        private void i() {
            EGLSurface eGLSurface;
            EGLSurface eGLSurface2 = this.f19703c;
            if (eGLSurface2 == null || eGLSurface2 == (eGLSurface = EGL10.EGL_NO_SURFACE)) {
                return;
            }
            this.f19701a.eglMakeCurrent(this.f19702b, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
            TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f19706f.get();
            if (tXCGLSurfaceViewBase != null) {
                tXCGLSurfaceViewBase.f19681l.a(this.f19701a, this.f19702b, this.f19703c);
                tXCGLSurfaceViewBase.f19674e = false;
            }
            this.f19703c = null;
        }

        public void a() {
            EGL10 egl10 = (EGL10) EGLContext.getEGL();
            this.f19701a = egl10;
            EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            this.f19702b = eGLDisplayEglGetDisplay;
            if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (!this.f19701a.eglInitialize(eGLDisplayEglGetDisplay, new int[2])) {
                throw new RuntimeException("eglInitialize failed");
            }
            TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f19706f.get();
            if (tXCGLSurfaceViewBase == null) {
                this.f19704d = null;
                this.f19705e = null;
                TXCLog.w("TXCGLSurfaceViewBase", "start() error when view is null ");
            } else {
                this.f19704d = tXCGLSurfaceViewBase.f19679j.a(this.f19701a, this.f19702b);
                this.f19705e = tXCGLSurfaceViewBase.f19680k.a(this.f19701a, this.f19702b, this.f19704d);
            }
            EGLContext eGLContext = this.f19705e;
            if (eGLContext == null || eGLContext == EGL10.EGL_NO_CONTEXT) {
                this.f19705e = null;
                a("createContext");
            }
            if (tXCGLSurfaceViewBase != null) {
                tXCGLSurfaceViewBase.f19675f = true;
            }
            this.f19703c = null;
        }

        public boolean b() {
            if (this.f19701a == null) {
                throw new RuntimeException("egl not initialized");
            }
            if (this.f19702b == null) {
                throw new RuntimeException("eglDisplay not initialized");
            }
            if (this.f19704d == null) {
                throw new RuntimeException("mEglConfig not initialized");
            }
            i();
            TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f19706f.get();
            if (tXCGLSurfaceViewBase != null) {
                this.f19703c = tXCGLSurfaceViewBase.f19681l.a(this.f19701a, this.f19702b, this.f19704d, tXCGLSurfaceViewBase.getHolder());
            } else {
                this.f19703c = null;
            }
            EGLSurface eGLSurface = this.f19703c;
            if (eGLSurface == null || eGLSurface == EGL10.EGL_NO_SURFACE) {
                if (this.f19701a.eglGetError() == 12299) {
                    TXCLog.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                }
                return false;
            }
            if (!this.f19701a.eglMakeCurrent(this.f19702b, eGLSurface, eGLSurface, this.f19705e)) {
                a("EGLHelper", "eglMakeCurrent", this.f19701a.eglGetError());
                return false;
            }
            if (tXCGLSurfaceViewBase != null) {
                tXCGLSurfaceViewBase.f19674e = true;
            }
            return true;
        }

        public boolean c() {
            EGL10 egl10 = this.f19701a;
            EGLDisplay eGLDisplay = this.f19702b;
            EGLSurface eGLSurface = this.f19703c;
            if (egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f19705e)) {
                return true;
            }
            a("EGLHelper", "eglMakeCurrent", this.f19701a.eglGetError());
            return false;
        }

        public int d() {
            return f();
        }

        public GL e() {
            GL gl = this.f19705e.getGL();
            TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f19706f.get();
            if (tXCGLSurfaceViewBase == null) {
                return gl;
            }
            if (tXCGLSurfaceViewBase.f19682m != null) {
                gl = tXCGLSurfaceViewBase.f19682m.a(gl);
            }
            if ((tXCGLSurfaceViewBase.f19683n & 3) != 0) {
                return GLDebugHelper.wrap(gl, (tXCGLSurfaceViewBase.f19683n & 1) == 0 ? 0 : 1, (tXCGLSurfaceViewBase.f19683n & 2) != 0 ? new l() : null);
            }
            return gl;
        }

        public int f() {
            if (this.f19701a.eglSwapBuffers(this.f19702b, this.f19703c)) {
                return 12288;
            }
            return this.f19701a.eglGetError();
        }

        public void g() {
            i();
        }

        public void h() {
            if (this.f19705e != null) {
                TXCGLSurfaceViewBase tXCGLSurfaceViewBase = this.f19706f.get();
                if (tXCGLSurfaceViewBase != null) {
                    tXCGLSurfaceViewBase.f19680k.a(this.f19701a, this.f19702b, this.f19705e);
                }
                this.f19705e = null;
            }
            EGLDisplay eGLDisplay = this.f19702b;
            if (eGLDisplay != null) {
                this.f19701a.eglTerminate(eGLDisplay);
                this.f19702b = null;
            }
        }

        private void a(String str) {
            a(str, this.f19701a.eglGetError());
        }

        public static void a(String str, int i2) {
            throw new RuntimeException(b(str, i2));
        }

        public static String b(String str, int i2) {
            return str + " failed: " + i2;
        }

        public static void a(String str, String str2, int i2) {
            TXCLog.w(str, b(str2, i2));
        }
    }
}
