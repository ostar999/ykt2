package org.wrtca.gl;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.os.Build;
import android.view.Surface;
import c.h;
import com.yikaobang.yixue.R2;
import org.wrtca.api.EglBase;
import org.wrtca.jni.CalledByNative;
import org.wrtca.log.Logging;

@TargetApi(18)
/* loaded from: classes9.dex */
public class EglBase14 implements EglBase {
    private static final int CURRENT_SDK_VERSION = Build.VERSION.SDK_INT;
    private static final int EGLExt_SDK_VERSION = 18;
    private static final String TAG = "EglBase14";
    private EGLConfig eglConfig;
    private EGLContext eglContext;
    private EGLDisplay eglDisplay;
    private EGLSurface eglSurface = EGL14.EGL_NO_SURFACE;

    public static class Context implements EglBase.Context {
        private final EGLContext egl14Context;

        public Context(EGLContext eGLContext) {
            this.egl14Context = eGLContext;
        }

        @CalledByNative("Context")
        public static boolean isEgl14Context(EglBase.Context context) {
            return context instanceof Context;
        }

        @Override // org.wrtca.api.EglBase.Context
        @TargetApi(21)
        public long getNativeEglContext() {
            return EglBase14.CURRENT_SDK_VERSION >= 21 ? this.egl14Context.getNativeHandle() : this.egl14Context.getHandle();
        }
    }

    public EglBase14(Context context, int[] iArr) {
        EGLDisplay eglDisplay = getEglDisplay();
        this.eglDisplay = eglDisplay;
        EGLConfig eglConfig = getEglConfig(eglDisplay, iArr);
        this.eglConfig = eglConfig;
        this.eglContext = createEglContext(context, this.eglDisplay, eglConfig);
        StringBuilder sb = new StringBuilder();
        sb.append("EglBase14create egl context: ");
        sb.append(this.eglContext);
        sb.append("sharedContext: ");
        sb.append(context != null ? context.egl14Context : context);
        h.a(TAG, sb.toString());
    }

    private void checkIsNotReleased() {
        if (this.eglDisplay == EGL14.EGL_NO_DISPLAY || this.eglContext == EGL14.EGL_NO_CONTEXT || this.eglConfig == null) {
            throw new RuntimeException("This object has been released");
        }
    }

    private static EGLContext createEglContext(Context context, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
        EGLContext eGLContextEglCreateContext;
        if (context != null && context.egl14Context == EGL14.EGL_NO_CONTEXT) {
            throw new RuntimeException("Invalid sharedContext");
        }
        int[] iArr = {R2.drawable.shape_light_yellow_bg, 2, R2.drawable.shape_coupon_record_bg};
        EGLContext eGLContext = context == null ? EGL14.EGL_NO_CONTEXT : context.egl14Context;
        synchronized (EglBase.lock) {
            eGLContextEglCreateContext = EGL14.eglCreateContext(eGLDisplay, eGLConfig, eGLContext, iArr, 0);
        }
        if (eGLContextEglCreateContext != EGL14.EGL_NO_CONTEXT) {
            return eGLContextEglCreateContext;
        }
        throw new RuntimeException("Failed to create EGL context: 0x" + Integer.toHexString(EGL14.eglGetError()));
    }

    private void createSurfaceInternal(Object obj) {
        h.a(TAG, "createSurfaceInternal start: " + obj);
        if (!(obj instanceof Surface) && !(obj instanceof SurfaceTexture)) {
            throw new IllegalStateException("Input must be either a Surface or SurfaceTexture");
        }
        checkIsNotReleased();
        if (this.eglSurface != EGL14.EGL_NO_SURFACE) {
            throw new RuntimeException("Already has an EGLSurface");
        }
        EGLSurface eGLSurfaceEglCreateWindowSurface = EGL14.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, obj, new int[]{R2.drawable.shape_coupon_record_bg}, 0);
        this.eglSurface = eGLSurfaceEglCreateWindowSurface;
        if (eGLSurfaceEglCreateWindowSurface != EGL14.EGL_NO_SURFACE) {
            h.a(TAG, "createSurfaceInternal finish: " + obj);
            return;
        }
        h.a(TAG, "create surface failed");
        throw new RuntimeException("Failed to create window surface: 0x" + Integer.toHexString(EGL14.eglGetError()));
    }

    private static EGLConfig getEglConfig(EGLDisplay eGLDisplay, int[] iArr) {
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        int[] iArr2 = new int[1];
        if (!EGL14.eglChooseConfig(eGLDisplay, iArr, 0, eGLConfigArr, 0, 1, iArr2, 0)) {
            throw new RuntimeException("eglChooseConfig failed: 0x" + Integer.toHexString(EGL14.eglGetError()));
        }
        if (iArr2[0] <= 0) {
            throw new RuntimeException("Unable to find any matching EGL config");
        }
        EGLConfig eGLConfig = eGLConfigArr[0];
        if (eGLConfig != null) {
            return eGLConfig;
        }
        throw new RuntimeException("eglChooseConfig returned null");
    }

    private static EGLDisplay getEglDisplay() {
        EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
        if (eGLDisplayEglGetDisplay == EGL14.EGL_NO_DISPLAY) {
            throw new RuntimeException("Unable to get EGL14 display: 0x" + Integer.toHexString(EGL14.eglGetError()));
        }
        int[] iArr = new int[2];
        if (EGL14.eglInitialize(eGLDisplayEglGetDisplay, iArr, 0, iArr, 1)) {
            return eGLDisplayEglGetDisplay;
        }
        throw new RuntimeException("Unable to initialize EGL14: 0x" + Integer.toHexString(EGL14.eglGetError()));
    }

    public static boolean isEGL14Supported() {
        StringBuilder sb = new StringBuilder();
        sb.append("SDK version: ");
        int i2 = CURRENT_SDK_VERSION;
        sb.append(i2);
        sb.append(". isEGL14Supported: ");
        sb.append(i2 >= 18);
        Logging.d(TAG, sb.toString());
        return i2 >= 18;
    }

    @Override // org.wrtca.api.EglBase
    public void createDummyPbufferSurface() {
        createPbufferSurface(1, 1);
    }

    @Override // org.wrtca.api.EglBase
    public void createPbufferSurface(int i2, int i3) {
        checkIsNotReleased();
        if (this.eglSurface != EGL14.EGL_NO_SURFACE) {
            throw new RuntimeException("Already has an EGLSurface");
        }
        EGLSurface eGLSurfaceEglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(this.eglDisplay, this.eglConfig, new int[]{R2.drawable.shape_discuss_right_press, i2, R2.drawable.shape_discuss_right_default, i3, R2.drawable.shape_coupon_record_bg}, 0);
        this.eglSurface = eGLSurfaceEglCreatePbufferSurface;
        if (eGLSurfaceEglCreatePbufferSurface != EGL14.EGL_NO_SURFACE) {
            return;
        }
        throw new RuntimeException("Failed to create pixel buffer surface with size " + i2 + "x" + i3 + ": 0x" + Integer.toHexString(EGL14.eglGetError()));
    }

    @Override // org.wrtca.api.EglBase
    public void createSurface(Surface surface) {
        createSurfaceInternal(surface);
    }

    @Override // org.wrtca.api.EglBase
    public void detachCurrent() {
        synchronized (EglBase.lock) {
            EGLDisplay eGLDisplay = this.eglDisplay;
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT)) {
                throw new RuntimeException("eglDetachCurrent failed: 0x" + Integer.toHexString(EGL14.eglGetError()));
            }
        }
    }

    @Override // org.wrtca.api.EglBase
    public boolean hasSurface() {
        return this.eglSurface != EGL14.EGL_NO_SURFACE;
    }

    @Override // org.wrtca.api.EglBase
    public void makeCurrent() {
        checkIsNotReleased();
        if (this.eglSurface == EGL14.EGL_NO_SURFACE) {
            throw new RuntimeException("No EGLSurface - can't make current");
        }
        synchronized (EglBase.lock) {
            EGLDisplay eGLDisplay = this.eglDisplay;
            EGLSurface eGLSurface = this.eglSurface;
            if (!EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext)) {
                throw new RuntimeException("eglMakeCurrent failed: 0x" + Integer.toHexString(EGL14.eglGetError()));
            }
        }
    }

    @Override // org.wrtca.api.EglBase
    public void release() {
        checkIsNotReleased();
        releaseSurface();
        detachCurrent();
        EGL14.eglDestroyContext(this.eglDisplay, this.eglContext);
        EGL14.eglReleaseThread();
        EGL14.eglTerminate(this.eglDisplay);
        this.eglContext = EGL14.EGL_NO_CONTEXT;
        this.eglDisplay = EGL14.EGL_NO_DISPLAY;
        this.eglConfig = null;
    }

    @Override // org.wrtca.api.EglBase
    public void releaseSurface() {
        if (this.eglSurface != EGL14.EGL_NO_SURFACE) {
            h.a(TAG, "releaseSurface " + this.eglSurface);
            EGL14.eglDestroySurface(this.eglDisplay, this.eglSurface);
            this.eglSurface = EGL14.EGL_NO_SURFACE;
        }
    }

    @Override // org.wrtca.api.EglBase
    public int surfaceHeight() {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.eglDisplay, this.eglSurface, R2.drawable.shape_discuss_right_default, iArr, 0);
        return iArr[0];
    }

    @Override // org.wrtca.api.EglBase
    public int surfaceWidth() {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(this.eglDisplay, this.eglSurface, R2.drawable.shape_discuss_right_press, iArr, 0);
        return iArr[0];
    }

    @Override // org.wrtca.api.EglBase
    public void swapBuffers() {
        checkIsNotReleased();
        if (this.eglSurface == EGL14.EGL_NO_SURFACE) {
            throw new RuntimeException("No EGLSurface - can't swap buffers");
        }
        synchronized (EglBase.lock) {
            EGL14.eglSwapBuffers(this.eglDisplay, this.eglSurface);
        }
    }

    @Override // org.wrtca.api.EglBase
    public void createSurface(SurfaceTexture surfaceTexture) {
        createSurfaceInternal(surfaceTexture);
    }

    @Override // org.wrtca.api.EglBase
    public Context getEglBaseContext() {
        return new Context(this.eglContext);
    }

    @Override // org.wrtca.api.EglBase
    public void swapBuffers(long j2) {
        checkIsNotReleased();
        if (this.eglSurface != EGL14.EGL_NO_SURFACE) {
            synchronized (EglBase.lock) {
                EGLExt.eglPresentationTimeANDROID(this.eglDisplay, this.eglSurface, j2);
                EGL14.eglSwapBuffers(this.eglDisplay, this.eglSurface);
            }
            return;
        }
        throw new RuntimeException("No EGLSurface - can't swap buffers");
    }
}
