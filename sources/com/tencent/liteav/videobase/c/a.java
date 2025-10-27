package com.tencent.liteav.videobase.c;

import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* loaded from: classes6.dex */
public class a implements e<EGLContext> {

    /* renamed from: h, reason: collision with root package name */
    private static final int[] f19965h = {R2.drawable.shape_computer_statistics_top_bg, 1, R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_color2a_corner12, 0, R2.drawable.shape_color2a_normal, 0, R2.drawable.shape_course_tags_bg, 4, R2.drawable.shape_coupon_record_bg};

    /* renamed from: i, reason: collision with root package name */
    private static final int[] f19966i = {R2.drawable.shape_computer_statistics_top_bg, 4, R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_color2a_corner12, 0, R2.drawable.shape_color2a_normal, 0, R2.drawable.shape_course_tags_bg, 4, 12610, 1, R2.drawable.shape_coupon_record_bg};

    /* renamed from: a, reason: collision with root package name */
    private final int f19967a;

    /* renamed from: b, reason: collision with root package name */
    private final int f19968b;

    /* renamed from: c, reason: collision with root package name */
    private EGLDisplay f19969c = EGL10.EGL_NO_DISPLAY;

    /* renamed from: d, reason: collision with root package name */
    private EGLContext f19970d = EGL10.EGL_NO_CONTEXT;

    /* renamed from: e, reason: collision with root package name */
    private EGLSurface f19971e = EGL10.EGL_NO_SURFACE;

    /* renamed from: f, reason: collision with root package name */
    private EGL10 f19972f;

    /* renamed from: g, reason: collision with root package name */
    private EGLConfig f19973g;

    private a(int i2, int i3) {
        this.f19967a = i2;
        this.f19968b = i3;
    }

    public static a a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface, int i2, int i3) throws d {
        a aVar = new a(i2, i3);
        aVar.a(eGLConfig, eGLContext, surface);
        return aVar;
    }

    private void g() throws d {
        int iEglGetError = this.f19972f.eglGetError();
        if (iEglGetError != 12288) {
            throw new d(iEglGetError);
        }
    }

    public void b() throws d {
        if (this.f19971e != EGL10.EGL_NO_SURFACE) {
            d();
            if (!this.f19972f.eglDestroySurface(this.f19969c, this.f19971e)) {
                g();
            }
            this.f19971e = EGL10.EGL_NO_SURFACE;
        }
    }

    @Override // com.tencent.liteav.videobase.c.e
    public void c() throws d {
        if (this.f19969c != EGL10.EGL_NO_DISPLAY) {
            d();
            b();
            EGLContext eGLContext = this.f19970d;
            if (eGLContext != EGL10.EGL_NO_CONTEXT) {
                this.f19972f.eglDestroyContext(this.f19969c, eGLContext);
                this.f19970d = EGL10.EGL_NO_CONTEXT;
            }
            this.f19972f.eglTerminate(this.f19969c);
        }
        this.f19969c = EGL10.EGL_NO_DISPLAY;
    }

    @Override // com.tencent.liteav.videobase.c.e
    public void d() {
        EGLDisplay eGLDisplay = this.f19969c;
        if (eGLDisplay != EGL10.EGL_NO_DISPLAY) {
            EGL10 egl10 = this.f19972f;
            EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
            egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        }
    }

    @Override // com.tencent.liteav.videobase.c.e
    /* renamed from: e, reason: merged with bridge method [inline-methods] */
    public EGLContext f() {
        return this.f19970d;
    }

    @Override // com.tencent.liteav.videobase.c.e
    public void a() throws d {
        EGL10 egl10 = this.f19972f;
        EGLDisplay eGLDisplay = this.f19969c;
        EGLSurface eGLSurface = this.f19971e;
        if (egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f19970d)) {
            return;
        }
        g();
    }

    private void a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface) throws d {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        this.f19972f = egl10;
        EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.f19969c = eGLDisplayEglGetDisplay;
        this.f19972f.eglInitialize(eGLDisplayEglGetDisplay, new int[2]);
        if (eGLConfig == null) {
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            this.f19972f.eglChooseConfig(this.f19969c, surface == null ? f19965h : f19966i, eGLConfigArr, 1, new int[1]);
            this.f19973g = eGLConfigArr[0];
        } else {
            this.f19973g = eGLConfig;
        }
        try {
            this.f19970d = a(this.f19969c, this.f19973g, 2, eGLContext);
        } catch (d unused) {
            TXCLog.i("EGL10Helper", "failed to create EGLContext of OpenGL ES 2.0, try 3.0");
            this.f19970d = a(this.f19969c, this.f19973g, 3, eGLContext);
        }
        if (surface == null) {
            this.f19971e = this.f19972f.eglCreatePbufferSurface(this.f19969c, this.f19973g, new int[]{R2.drawable.shape_discuss_right_press, this.f19967a, R2.drawable.shape_discuss_right_default, this.f19968b, R2.drawable.shape_coupon_record_bg});
        } else {
            this.f19971e = this.f19972f.eglCreateWindowSurface(this.f19969c, this.f19973g, surface, null);
        }
        if (this.f19971e == EGL10.EGL_NO_SURFACE) {
            g();
        }
        EGL10 egl102 = this.f19972f;
        EGLDisplay eGLDisplay = this.f19969c;
        EGLSurface eGLSurface = this.f19971e;
        if (egl102.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f19970d)) {
            return;
        }
        g();
    }

    private EGLContext a(EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i2, EGLContext eGLContext) throws d {
        int[] iArr = {R2.drawable.shape_light_yellow_bg, i2, R2.drawable.shape_coupon_record_bg};
        if (eGLContext == null) {
            eGLContext = EGL10.EGL_NO_CONTEXT;
        }
        EGLContext eGLContextEglCreateContext = this.f19972f.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        g();
        return eGLContextEglCreateContext;
    }
}
