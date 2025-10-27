package com.tencent.liteav.basic.opengl;

import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f18496a = "b";

    /* renamed from: l, reason: collision with root package name */
    private static int[] f18497l = {R2.drawable.shape_computer_statistics_top_bg, 1, R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_color2a_corner12, 0, R2.drawable.shape_color2a_normal, 0, R2.drawable.shape_course_tags_bg, 4, R2.drawable.shape_coupon_record_bg};

    /* renamed from: m, reason: collision with root package name */
    private static int[] f18498m = {R2.drawable.shape_computer_statistics_top_bg, 4, R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_color2a_corner12, 0, R2.drawable.shape_color2a_normal, 0, R2.drawable.shape_course_tags_bg, 4, 12610, 1, R2.drawable.shape_coupon_record_bg};

    /* renamed from: b, reason: collision with root package name */
    private EGL10 f18499b;

    /* renamed from: c, reason: collision with root package name */
    private EGLDisplay f18500c;

    /* renamed from: d, reason: collision with root package name */
    private EGLConfig f18501d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f18502e;

    /* renamed from: f, reason: collision with root package name */
    private EGLContext f18503f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f18504g;

    /* renamed from: h, reason: collision with root package name */
    private EGLSurface f18505h;

    /* renamed from: i, reason: collision with root package name */
    private int f18506i = 0;

    /* renamed from: j, reason: collision with root package name */
    private int f18507j = 0;

    /* renamed from: k, reason: collision with root package name */
    private int[] f18508k = new int[2];

    private b() {
    }

    public static b a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface, int i2, int i3) {
        b bVar = new b();
        bVar.f18506i = i2;
        bVar.f18507j = i3;
        if (bVar.a(eGLConfig, eGLContext, surface)) {
            return bVar;
        }
        return null;
    }

    private void g() throws d {
        int iEglGetError = this.f18499b.eglGetError();
        if (iEglGetError != 12288) {
            throw new d(iEglGetError);
        }
    }

    public void b() {
        EGL10 egl10 = this.f18499b;
        EGLDisplay eGLDisplay = this.f18500c;
        EGLSurface eGLSurface = this.f18505h;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f18503f);
        e();
    }

    public void c() {
        EGL10 egl10 = this.f18499b;
        EGLDisplay eGLDisplay = this.f18500c;
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        EGLSurface eGLSurface2 = this.f18505h;
        if (eGLSurface2 != null) {
            this.f18499b.eglDestroySurface(this.f18500c, eGLSurface2);
        }
        EGLContext eGLContext = this.f18503f;
        if (eGLContext != null) {
            this.f18499b.eglDestroyContext(this.f18500c, eGLContext);
        }
        this.f18499b.eglTerminate(this.f18500c);
        e();
        this.f18505h = null;
        this.f18500c = null;
    }

    public EGLContext d() {
        return this.f18503f;
    }

    public void e() {
        int iEglGetError = this.f18499b.eglGetError();
        if (iEglGetError != 12288) {
            TXCLog.e(f18496a, "EGL error: 0x" + Integer.toHexString(iEglGetError));
        }
    }

    public com.tencent.liteav.basic.util.e f() {
        int[] iArr = new int[1];
        int[] iArr2 = new int[1];
        return (this.f18499b.eglQuerySurface(this.f18500c, this.f18505h, R2.drawable.shape_discuss_right_press, iArr) && this.f18499b.eglQuerySurface(this.f18500c, this.f18505h, R2.drawable.shape_discuss_right_default, iArr2)) ? new com.tencent.liteav.basic.util.e(iArr[0], iArr2[0]) : new com.tencent.liteav.basic.util.e(0, 0);
    }

    public boolean a() {
        boolean zEglSwapBuffers = this.f18499b.eglSwapBuffers(this.f18500c, this.f18505h);
        e();
        return zEglSwapBuffers;
    }

    private boolean a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface) {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        this.f18499b = egl10;
        EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.f18500c = eGLDisplayEglGetDisplay;
        this.f18499b.eglInitialize(eGLDisplayEglGetDisplay, this.f18508k);
        if (eGLConfig == null) {
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            this.f18499b.eglChooseConfig(this.f18500c, surface == null ? f18497l : f18498m, eGLConfigArr, 1, new int[1]);
            this.f18501d = eGLConfigArr[0];
            this.f18502e = true;
        } else {
            this.f18501d = eGLConfig;
        }
        if (eGLContext != null) {
            this.f18504g = true;
        }
        try {
            this.f18503f = a(this.f18500c, this.f18501d, 2, eGLContext);
        } catch (d unused) {
            TXCLog.i(f18496a, "failed to create EGLContext of OpenGL ES 2.0, try 3.0");
            try {
                this.f18503f = a(this.f18500c, this.f18501d, 3, eGLContext);
            } catch (d e2) {
                TXCLog.e(f18496a, "failed to create EGLContext of 3.0. " + e2);
                return false;
            }
        }
        int[] iArr = {R2.drawable.shape_discuss_right_press, this.f18506i, R2.drawable.shape_discuss_right_default, this.f18507j, R2.drawable.shape_coupon_record_bg};
        if (surface == null) {
            this.f18505h = this.f18499b.eglCreatePbufferSurface(this.f18500c, this.f18501d, iArr);
        } else {
            this.f18505h = this.f18499b.eglCreateWindowSurface(this.f18500c, this.f18501d, surface, null);
        }
        EGLSurface eGLSurface = this.f18505h;
        if (eGLSurface == EGL10.EGL_NO_SURFACE) {
            e();
            return false;
        }
        if (this.f18499b.eglMakeCurrent(this.f18500c, eGLSurface, eGLSurface, this.f18503f)) {
            return true;
        }
        e();
        return false;
    }

    private EGLContext a(EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i2, EGLContext eGLContext) throws d {
        int[] iArr = {R2.drawable.shape_light_yellow_bg, i2, R2.drawable.shape_coupon_record_bg};
        if (eGLContext == null) {
            eGLContext = EGL10.EGL_NO_CONTEXT;
        }
        EGLContext eGLContextEglCreateContext = this.f18499b.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr);
        g();
        return eGLContextEglCreateContext;
    }
}
