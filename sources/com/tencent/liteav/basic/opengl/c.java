package com.tencent.liteav.basic.opengl;

import android.annotation.TargetApi;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.util.Log;
import android.view.Surface;
import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;

@TargetApi(17)
/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static int f18509a = 2;

    /* renamed from: b, reason: collision with root package name */
    private static final String f18510b = "c";

    /* renamed from: k, reason: collision with root package name */
    private static int[] f18511k;

    /* renamed from: l, reason: collision with root package name */
    private static int[] f18512l;

    /* renamed from: h, reason: collision with root package name */
    private boolean f18518h;

    /* renamed from: i, reason: collision with root package name */
    private EGLSurface f18519i;

    /* renamed from: c, reason: collision with root package name */
    private EGLDisplay f18513c = EGL14.EGL_NO_DISPLAY;

    /* renamed from: d, reason: collision with root package name */
    private EGLContext f18514d = EGL14.EGL_NO_CONTEXT;

    /* renamed from: e, reason: collision with root package name */
    private EGLConfig f18515e = null;

    /* renamed from: f, reason: collision with root package name */
    private int f18516f = 0;

    /* renamed from: g, reason: collision with root package name */
    private int f18517g = 0;

    /* renamed from: j, reason: collision with root package name */
    private int f18520j = -1;

    static {
        int[] iArr = new int[17];
        iArr[0] = 12324;
        iArr[1] = 8;
        iArr[2] = 12323;
        iArr[3] = 8;
        iArr[4] = 12322;
        iArr[5] = 8;
        iArr[6] = 12321;
        iArr[7] = 8;
        iArr[8] = 12325;
        iArr[9] = 0;
        iArr[10] = 12326;
        iArr[11] = 0;
        iArr[12] = 12352;
        int i2 = f18509a;
        iArr[13] = i2 == 2 ? 4 : 68;
        iArr[14] = 12610;
        iArr[15] = 1;
        iArr[16] = 12344;
        f18511k = iArr;
        int[] iArr2 = new int[19];
        iArr2[0] = 12339;
        iArr2[1] = 1;
        iArr2[2] = 12324;
        iArr2[3] = 8;
        iArr2[4] = 12323;
        iArr2[5] = 8;
        iArr2[6] = 12322;
        iArr2[7] = 8;
        iArr2[8] = 12321;
        iArr2[9] = 8;
        iArr2[10] = 12325;
        iArr2[11] = 0;
        iArr2[12] = 12326;
        iArr2[13] = 0;
        iArr2[14] = 12352;
        iArr2[15] = i2 != 2 ? 68 : 4;
        iArr2[16] = 12610;
        iArr2[17] = 1;
        iArr2[18] = 12344;
        f18512l = iArr2;
    }

    public static c a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface, int i2, int i3) {
        c cVar = new c();
        cVar.f18516f = i2;
        cVar.f18517g = i3;
        if (cVar.a(eGLConfig, eGLContext, surface)) {
            return cVar;
        }
        return null;
    }

    private static void g() throws d {
        int iEglGetError = EGL14.eglGetError();
        if (iEglGetError != 12288) {
            throw new d(iEglGetError);
        }
    }

    public void b() {
        if (this.f18513c == EGL14.EGL_NO_DISPLAY) {
            Log.d(f18510b, "NOTE: makeCurrent w/o display");
        }
        EGLDisplay eGLDisplay = this.f18513c;
        EGLSurface eGLSurface = this.f18519i;
        if (EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f18514d)) {
            return;
        }
        TXCLog.e(f18510b, "eglMakeCurrent failed");
    }

    public com.tencent.liteav.basic.util.e c() {
        int[] iArr = new int[2];
        return (EGL14.eglQuerySurface(this.f18513c, this.f18519i, R2.drawable.shape_discuss_right_press, iArr, 0) && EGL14.eglQuerySurface(this.f18513c, this.f18519i, R2.drawable.shape_discuss_right_default, iArr, 1)) ? new com.tencent.liteav.basic.util.e(iArr[0], iArr[1]) : new com.tencent.liteav.basic.util.e(0, 0);
    }

    public void d() {
        EGLDisplay eGLDisplay = this.f18513c;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroySurface(this.f18513c, this.f18519i);
            EGL14.eglDestroyContext(this.f18513c, this.f18514d);
            this.f18514d = EGL14.EGL_NO_CONTEXT;
            EGL14.eglReleaseThread();
            EGL14.eglTerminate(this.f18513c);
        }
        this.f18513c = EGL14.EGL_NO_DISPLAY;
    }

    public boolean e() {
        return EGL14.eglSwapBuffers(this.f18513c, this.f18519i);
    }

    public EGLContext f() {
        return this.f18514d;
    }

    public void a() {
        int iEglGetError = EGL14.eglGetError();
        if (iEglGetError != 12288) {
            TXCLog.e(f18510b, "EGL error:" + iEglGetError);
        }
    }

    private boolean a(EGLConfig eGLConfig, EGLContext eGLContext, Surface surface) {
        EGLContext eGLContext2;
        EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
        this.f18513c = eGLDisplayEglGetDisplay;
        if (eGLDisplayEglGetDisplay == EGL14.EGL_NO_DISPLAY) {
            TXCLog.e(f18510b, "unable to get EGL14 display");
        }
        int[] iArr = new int[2];
        if (!EGL14.eglInitialize(this.f18513c, iArr, 0, iArr, 1)) {
            this.f18513c = null;
            TXCLog.e(f18510b, "unable to initialize EGL14");
        }
        if (eGLConfig != null) {
            this.f18515e = eGLConfig;
        } else {
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            if (!EGL14.eglChooseConfig(this.f18513c, surface == null ? f18512l : f18511k, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
                return false;
            }
            this.f18515e = eGLConfigArr[0];
        }
        if (eGLContext != null) {
            this.f18518h = true;
            eGLContext2 = eGLContext;
        } else {
            eGLContext2 = EGL14.EGL_NO_CONTEXT;
        }
        try {
            this.f18514d = a(this.f18513c, this.f18515e, 2, eGLContext2);
        } catch (d unused) {
            TXCLog.i(f18510b, "failed to create EGLContext of OpenGL ES 2.0, try 3.0");
            try {
                this.f18514d = a(this.f18513c, this.f18515e, 3, eGLContext2);
            } catch (d e2) {
                TXCLog.e(f18510b, "failed to create EGLContext of 3.0. " + e2);
                return false;
            }
        }
        int[] iArr2 = {R2.drawable.shape_coupon_record_bg};
        if (surface == null) {
            this.f18519i = EGL14.eglCreatePbufferSurface(this.f18513c, this.f18515e, new int[]{R2.drawable.shape_discuss_right_press, this.f18516f, R2.drawable.shape_discuss_right_default, this.f18517g, R2.drawable.shape_coupon_record_bg}, 0);
        } else {
            this.f18519i = EGL14.eglCreateWindowSurface(this.f18513c, this.f18515e, surface, iArr2, 0);
        }
        a();
        EGLDisplay eGLDisplay = this.f18513c;
        EGLSurface eGLSurface = this.f18519i;
        if (EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.f18514d)) {
            return true;
        }
        a();
        return false;
    }

    private static EGLContext a(EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i2, EGLContext eGLContext) throws d {
        EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, new int[]{R2.drawable.shape_light_yellow_bg, i2, R2.drawable.shape_coupon_record_bg}, 0);
        g();
        return eGLContextEglCreateContext;
    }

    public void a(long j2) {
        EGLExt.eglPresentationTimeANDROID(this.f18513c, this.f18519i, j2);
    }
}
