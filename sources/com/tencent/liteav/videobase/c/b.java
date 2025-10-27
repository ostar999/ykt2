package com.tencent.liteav.videobase.c;

import android.annotation.TargetApi;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;

@TargetApi(18)
/* loaded from: classes6.dex */
public class b implements e<EGLContext> {

    /* renamed from: g, reason: collision with root package name */
    private static final int[] f19974g = {R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_color2a_corner12, 0, R2.drawable.shape_color2a_normal, 0, R2.drawable.shape_course_tags_bg, 4, 12610, 1, R2.drawable.shape_coupon_record_bg};

    /* renamed from: h, reason: collision with root package name */
    private static final int[] f19975h = {R2.drawable.shape_computer_statistics_top_bg, 1, R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_color2a_corner12, 0, R2.drawable.shape_color2a_normal, 0, R2.drawable.shape_course_tags_bg, 4, 12610, 1, R2.drawable.shape_coupon_record_bg};

    /* renamed from: a, reason: collision with root package name */
    private final int f19976a;

    /* renamed from: b, reason: collision with root package name */
    private final int f19977b;

    /* renamed from: c, reason: collision with root package name */
    private EGLConfig f19978c = null;

    /* renamed from: d, reason: collision with root package name */
    private EGLDisplay f19979d = EGL14.EGL_NO_DISPLAY;

    /* renamed from: e, reason: collision with root package name */
    private EGLContext f19980e = EGL14.EGL_NO_CONTEXT;

    /* renamed from: f, reason: collision with root package name */
    private EGLSurface f19981f;

    private b(int i2, int i3) {
        this.f19976a = i2;
        this.f19977b = i3;
    }

    public static b a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface, int i2, int i3) throws d {
        b bVar = new b(i2, i3);
        bVar.a(eGLConfig, eGLContext, surface);
        return bVar;
    }

    private static void e() throws d {
        int iEglGetError = EGL14.eglGetError();
        if (iEglGetError != 12288) {
            throw new d(iEglGetError);
        }
    }

    @Override // com.tencent.liteav.videobase.c.e
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public EGLContext f() {
        return this.f19980e;
    }

    @Override // com.tencent.liteav.videobase.c.e
    public void c() {
        EGLDisplay eGLDisplay = this.f19979d;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
            EGLSurface eGLSurface2 = this.f19981f;
            if (eGLSurface2 != EGL14.EGL_NO_SURFACE) {
                EGL14.eglDestroySurface(this.f19979d, eGLSurface2);
                this.f19981f = EGL14.EGL_NO_SURFACE;
            }
            EGLContext eGLContext = this.f19980e;
            if (eGLContext != EGL14.EGL_NO_CONTEXT) {
                EGL14.eglDestroyContext(this.f19979d, eGLContext);
                this.f19980e = EGL14.EGL_NO_CONTEXT;
            }
            EGL14.eglReleaseThread();
            EGL14.eglTerminate(this.f19979d);
        }
        this.f19979d = EGL14.EGL_NO_DISPLAY;
    }

    @Override // com.tencent.liteav.videobase.c.e
    public void d() {
        EGLDisplay eGLDisplay = this.f19979d;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
        }
    }

    @Override // com.tencent.liteav.videobase.c.e
    public void a() throws d {
        EGLDisplay eGLDisplay = this.f19979d;
        EGLSurface eGLSurface = this.f19981f;
        if (EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f19980e)) {
            return;
        }
        e();
    }

    private void a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface) throws d {
        EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
        this.f19979d = eGLDisplayEglGetDisplay;
        if (eGLDisplayEglGetDisplay != EGL14.EGL_NO_DISPLAY) {
            int[] iArr = new int[2];
            if (EGL14.eglInitialize(eGLDisplayEglGetDisplay, iArr, 0, iArr, 1)) {
                if (eGLConfig != null) {
                    this.f19978c = eGLConfig;
                } else {
                    EGLConfig[] eGLConfigArr = new EGLConfig[1];
                    if (EGL14.eglChooseConfig(this.f19979d, surface == null ? f19975h : f19974g, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
                        this.f19978c = eGLConfigArr[0];
                    } else {
                        throw new d(0);
                    }
                }
                try {
                    this.f19980e = a(this.f19979d, this.f19978c, 2, eGLContext);
                } catch (d unused) {
                    TXCLog.i("EGL14Helper", "failed to create EGLContext of OpenGL ES 2.0, try 3.0");
                    this.f19980e = a(this.f19979d, this.f19978c, 3, eGLContext);
                }
                if (surface == null) {
                    this.f19981f = EGL14.eglCreatePbufferSurface(this.f19979d, this.f19978c, new int[]{R2.drawable.shape_discuss_right_press, this.f19976a, R2.drawable.shape_discuss_right_default, this.f19977b, R2.drawable.shape_coupon_record_bg}, 0);
                } else {
                    this.f19981f = EGL14.eglCreateWindowSurface(this.f19979d, this.f19978c, surface, new int[]{R2.drawable.shape_coupon_record_bg}, 0);
                }
                e();
                EGLDisplay eGLDisplay = this.f19979d;
                EGLSurface eGLSurface = this.f19981f;
                if (EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f19980e)) {
                    return;
                }
                e();
                return;
            }
            this.f19979d = null;
            TXCLog.e("EGL14Helper", "unable to initialize EGL14");
            throw new d(0);
        }
        TXCLog.e("EGL14Helper", "unable to get EGL14 display");
        throw new d(0);
    }

    private static EGLContext a(EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i2, EGLContext eGLContext) throws d {
        int[] iArr = {R2.drawable.shape_light_yellow_bg, i2, R2.drawable.shape_coupon_record_bg};
        if (eGLContext == null) {
            eGLContext = EGL14.EGL_NO_CONTEXT;
        }
        EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr, 0);
        e();
        return eGLContextEglCreateContext;
    }
}
