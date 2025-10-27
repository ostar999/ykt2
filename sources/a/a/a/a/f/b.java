package a.a.a.a.f;

import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.util.Log;
import android.view.Surface;
import com.yikaobang.yixue.R2;

/* loaded from: classes.dex */
public final class b {

    /* renamed from: e, reason: collision with root package name */
    private static final String f1149e = "mqi";

    /* renamed from: f, reason: collision with root package name */
    public static final int f1150f = 1;

    /* renamed from: g, reason: collision with root package name */
    public static final int f1151g = 2;

    /* renamed from: h, reason: collision with root package name */
    private static final int f1152h = 12610;

    /* renamed from: a, reason: collision with root package name */
    private EGLDisplay f1153a;

    /* renamed from: b, reason: collision with root package name */
    private EGLContext f1154b;

    /* renamed from: c, reason: collision with root package name */
    private EGLConfig f1155c;

    /* renamed from: d, reason: collision with root package name */
    private int f1156d;

    public b() {
        this(null, 0);
    }

    private EGLConfig b(int i2, int i3) {
        int[] iArr = {R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_course_tags_bg, i3 >= 3 ? 68 : 4, R2.drawable.shape_coupon_record_bg, 0, R2.drawable.shape_coupon_record_bg};
        if ((i2 & 1) != 0) {
            iArr[10] = 12610;
            iArr[11] = 1;
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (EGL14.eglChooseConfig(this.f1153a, iArr, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
            return eGLConfigArr[0];
        }
        Log.w("mqi", "unable to find RGB8888 / " + i3 + " EGLConfig");
        return null;
    }

    public EGLSurface a(Object obj) {
        if (!(obj instanceof Surface) && !(obj instanceof SurfaceTexture)) {
            throw new RuntimeException("invalid surface: " + obj);
        }
        EGLSurface eGLSurfaceEglCreateWindowSurface = EGL14.eglCreateWindowSurface(this.f1153a, this.f1155c, obj, new int[]{R2.drawable.shape_coupon_record_bg}, 0);
        a("eglCreateWindowSurface");
        if (eGLSurfaceEglCreateWindowSurface != null) {
            return eGLSurfaceEglCreateWindowSurface;
        }
        throw new RuntimeException("surface was null");
    }

    public void c(EGLSurface eGLSurface) {
        EGL14.eglDestroySurface(this.f1153a, eGLSurface);
    }

    public void d() {
        EGLDisplay eGLDisplay = this.f1153a;
        EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
        if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public void e() {
        EGLDisplay eGLDisplay = this.f1153a;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroyContext(this.f1153a, this.f1154b);
            EGL14.eglReleaseThread();
            EGL14.eglTerminate(this.f1153a);
        }
        this.f1153a = EGL14.EGL_NO_DISPLAY;
        this.f1154b = EGL14.EGL_NO_CONTEXT;
        this.f1155c = null;
    }

    public void finalize() throws Throwable {
        try {
            if (this.f1153a != EGL14.EGL_NO_DISPLAY) {
                Log.w("mqi", "WARNING: EglCore was not explicitly released -- state may be leaked");
                e();
            }
        } finally {
            super.finalize();
        }
    }

    public b(EGLContext eGLContext, int i2) {
        EGLConfig eGLConfigB;
        EGLDisplay eGLDisplay = EGL14.EGL_NO_DISPLAY;
        this.f1153a = eGLDisplay;
        this.f1154b = EGL14.EGL_NO_CONTEXT;
        this.f1155c = null;
        this.f1156d = -1;
        if (eGLDisplay != EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("EGL already set up");
        }
        eGLContext = eGLContext == null ? EGL14.EGL_NO_CONTEXT : eGLContext;
        EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
        this.f1153a = eGLDisplayEglGetDisplay;
        if (eGLDisplayEglGetDisplay == EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL14 display");
        }
        int[] iArr = new int[2];
        if (!EGL14.eglInitialize(eGLDisplayEglGetDisplay, iArr, 0, iArr, 1)) {
            this.f1153a = null;
            throw new RuntimeException("unable to initialize EGL14");
        }
        if ((i2 & 2) != 0 && (eGLConfigB = b(i2, 3)) != null) {
            EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(this.f1153a, eGLConfigB, eGLContext, new int[]{R2.drawable.shape_light_yellow_bg, 3, R2.drawable.shape_coupon_record_bg}, 0);
            if (EGL14.eglGetError() == 12288) {
                this.f1155c = eGLConfigB;
                this.f1154b = eGLContextEglCreateContext;
                this.f1156d = 3;
            }
        }
        if (this.f1154b == EGL14.EGL_NO_CONTEXT) {
            EGLConfig eGLConfigB2 = b(i2, 2);
            if (eGLConfigB2 == null) {
                throw new RuntimeException("Unable to find a suitable EGLConfig");
            }
            EGLContext eGLContextEglCreateContext2 = EGL14.eglCreateContext(this.f1153a, eGLConfigB2, eGLContext, new int[]{R2.drawable.shape_light_yellow_bg, 2, R2.drawable.shape_coupon_record_bg}, 0);
            a("eglCreateContext");
            this.f1155c = eGLConfigB2;
            this.f1154b = eGLContextEglCreateContext2;
            this.f1156d = 2;
        }
        int[] iArr2 = new int[1];
        EGL14.eglQueryContext(this.f1153a, this.f1154b, R2.drawable.shape_light_yellow_bg, iArr2, 0);
        Log.d("mqi", "EGLContext created, client version " + iArr2[0]);
    }

    public int c() {
        return this.f1156d;
    }

    public EGLContext b() {
        return this.f1154b;
    }

    public boolean d(EGLSurface eGLSurface) {
        return EGL14.eglSwapBuffers(this.f1153a, eGLSurface);
    }

    public void b(EGLSurface eGLSurface) {
        if (this.f1153a == EGL14.EGL_NO_DISPLAY) {
            Log.d("mqi", "NOTE: makeCurrent w/o display");
        }
        if (!EGL14.eglMakeCurrent(this.f1153a, eGLSurface, eGLSurface, this.f1154b)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }

    public EGLSurface a(int i2, int i3) {
        EGLSurface eGLSurfaceEglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(this.f1153a, this.f1155c, new int[]{R2.drawable.shape_discuss_right_press, i2, R2.drawable.shape_discuss_right_default, i3, R2.drawable.shape_coupon_record_bg}, 0);
        a("eglCreatePbufferSurface");
        if (eGLSurfaceEglCreatePbufferSurface != null) {
            return eGLSurfaceEglCreatePbufferSurface;
        }
        throw new RuntimeException("surface was null");
    }

    public static void b(String str) {
        Log.i("mqi", "Current EGL (" + str + "): display=" + EGL14.eglGetCurrentDisplay() + ", context=" + EGL14.eglGetCurrentContext() + ", surface=" + EGL14.eglGetCurrentSurface(R2.drawable.shape_e25d49_180));
    }

    public void a(EGLSurface eGLSurface, EGLSurface eGLSurface2) {
        if (this.f1153a == EGL14.EGL_NO_DISPLAY) {
            Log.d("mqi", "NOTE: makeCurrent w/o display");
        }
        if (!EGL14.eglMakeCurrent(this.f1153a, eGLSurface, eGLSurface2, this.f1154b)) {
            throw new RuntimeException("eglMakeCurrent(draw,read) failed");
        }
    }

    public void a(EGLSurface eGLSurface, long j2) {
        EGLExt.eglPresentationTimeANDROID(this.f1153a, eGLSurface, j2);
    }

    public boolean a(EGLSurface eGLSurface) {
        return this.f1154b.equals(EGL14.eglGetCurrentContext()) && eGLSurface.equals(EGL14.eglGetCurrentSurface(R2.drawable.shape_e25d49_180));
    }

    public EGLSurface a() {
        if (this.f1154b.equals(EGL14.eglGetCurrentContext())) {
            return EGL14.eglGetCurrentSurface(R2.drawable.shape_e25d49_180);
        }
        return null;
    }

    public int a(EGLSurface eGLSurface, int i2) {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.f1153a, eGLSurface, i2, iArr, 0);
        return iArr[0];
    }

    public String a(int i2) {
        return EGL14.eglQueryString(this.f1153a, i2);
    }

    private void a(String str) {
        int iEglGetError = EGL14.eglGetError();
        if (iEglGetError == 12288) {
            return;
        }
        throw new RuntimeException(str + ": EGL error: 0x" + Integer.toHexString(iEglGetError));
    }
}
