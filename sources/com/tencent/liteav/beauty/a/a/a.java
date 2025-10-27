package com.tencent.liteav.beauty.a.a;

import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private EGL10 f18781a;

    /* renamed from: b, reason: collision with root package name */
    private final EGLContext f18782b;

    /* renamed from: c, reason: collision with root package name */
    private EGLDisplay f18783c;

    /* renamed from: d, reason: collision with root package name */
    private EGLConfig f18784d;

    /* renamed from: e, reason: collision with root package name */
    private EGLSurface f18785e;

    public a() {
        this(null);
    }

    private EGLConfig b() {
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (this.f18781a.eglChooseConfig(this.f18783c, new int[]{R2.drawable.shape_computer_statistics_top_bg, 1, R2.drawable.shape_color2a_corner12, 16, R2.drawable.shape_color2a_normal, 0, R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_course_tags_bg, 4, R2.drawable.shape_coupon_record_bg}, eGLConfigArr, 1, new int[1])) {
            return eGLConfigArr[0];
        }
        TXCLog.w("ImageEglSurface", "unable to find RGB8888  EGLConfig");
        return null;
    }

    public void a() {
        EGL10 egl10 = this.f18781a;
        EGLDisplay eGLDisplay = this.f18783c;
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        this.f18781a.eglDestroyContext(this.f18783c, this.f18782b);
        this.f18781a.eglTerminate(this.f18783c);
    }

    public a(EGLConfig eGLConfig) {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        this.f18781a = egl10;
        EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.f18783c = eGLDisplayEglGetDisplay;
        if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL10 display");
        }
        if (!this.f18781a.eglInitialize(eGLDisplayEglGetDisplay, new int[2])) {
            this.f18783c = null;
            throw new RuntimeException("unable to initialize EGL10");
        }
        if (eGLConfig != null) {
            this.f18784d = eGLConfig;
        } else {
            this.f18784d = b();
        }
        this.f18782b = this.f18781a.eglCreateContext(this.f18783c, this.f18784d, EGL10.EGL_NO_CONTEXT, new int[]{R2.drawable.shape_light_yellow_bg, 2, R2.drawable.shape_coupon_record_bg});
    }

    public void a(EGLSurface eGLSurface) {
        this.f18781a.eglDestroySurface(this.f18783c, eGLSurface);
    }

    public void b(EGLSurface eGLSurface) {
        EGLDisplay eGLDisplay = this.f18783c;
        if (eGLDisplay == EGL11.EGL_NO_DISPLAY) {
            TXCLog.i("EglCore", "NOTE: makeCurrent w/o display");
        }
        if (!this.f18781a.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f18782b)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public EGLSurface a(int i2, int i3) {
        this.f18785e = this.f18781a.eglCreatePbufferSurface(this.f18783c, this.f18784d, new int[]{R2.drawable.shape_discuss_right_press, i2, R2.drawable.shape_discuss_right_default, i3, R2.drawable.shape_coupon_record_bg});
        a("eglCreatePbufferSurface");
        EGLSurface eGLSurface = this.f18785e;
        if (eGLSurface != null) {
            return eGLSurface;
        }
        throw new RuntimeException("surface was null");
    }

    private void a(String str) {
        int iEglGetError = this.f18781a.eglGetError();
        if (iEglGetError == 12288) {
            return;
        }
        throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(iEglGetError));
    }
}
