package io.agora.rtc.gl;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.yikaobang.yixue.R2;
import io.agora.rtc.gl.EglBase;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* loaded from: classes8.dex */
public class EglBase10 extends EglBase {
    private static final int EGL_CONTEXT_CLIENT_VERSION = 12440;
    private EGLConfig eglConfig;
    private EGLContext eglContext;
    private EGLDisplay eglDisplay;
    private EGLSurface eglSurface = EGL10.EGL_NO_SURFACE;
    private final EGL10 egl = (EGL10) EGLContext.getEGL();

    public static class Context implements EglBase.Context {
        private final EGLContext eglContext;

        public Context(EGLContext eglContext) {
            this.eglContext = eglContext;
        }

        @Override // io.agora.rtc.gl.EglBase.Context
        public long getNativeEglContext() {
            return 0L;
        }
    }

    public EglBase10(Context sharedContext, int[] configAttributes) {
        EGLDisplay eglDisplay = getEglDisplay();
        this.eglDisplay = eglDisplay;
        EGLConfig eglConfig = getEglConfig(eglDisplay, configAttributes);
        this.eglConfig = eglConfig;
        this.eglContext = createEglContext(sharedContext, this.eglDisplay, eglConfig);
    }

    private void checkIsNotReleased() {
        if (this.eglDisplay == EGL10.EGL_NO_DISPLAY || this.eglContext == EGL10.EGL_NO_CONTEXT || this.eglConfig == null) {
            throw new RuntimeException("This object has been released");
        }
    }

    private EGLContext createEglContext(Context sharedContext, EGLDisplay eglDisplay, EGLConfig eglConfig) {
        EGLContext eGLContextEglCreateContext;
        if (sharedContext != null && sharedContext.eglContext == EGL10.EGL_NO_CONTEXT) {
            throw new RuntimeException("Invalid sharedContext");
        }
        int[] iArr = {12440, 2, R2.drawable.shape_coupon_record_bg};
        EGLContext eGLContext = sharedContext == null ? EGL10.EGL_NO_CONTEXT : sharedContext.eglContext;
        synchronized (EglBase.lock) {
            eGLContextEglCreateContext = this.egl.eglCreateContext(eglDisplay, eglConfig, eGLContext, iArr);
            if (this.egl.eglGetError() != 12288) {
                eGLContextEglCreateContext = this.egl.eglCreateContext(eglDisplay, eglConfig, eGLContext, new int[]{12440, 3, R2.drawable.shape_coupon_record_bg});
            }
        }
        if (eGLContextEglCreateContext != EGL10.EGL_NO_CONTEXT) {
            return eGLContextEglCreateContext;
        }
        throw new RuntimeException("Failed to create EGL context: 0x" + Integer.toHexString(this.egl.eglGetError()));
    }

    private void createSurfaceInternal(Object nativeWindow) {
        if (!(nativeWindow instanceof SurfaceHolder) && !(nativeWindow instanceof SurfaceTexture)) {
            throw new IllegalStateException("Input must be either a SurfaceHolder or SurfaceTexture");
        }
        checkIsNotReleased();
        if (this.eglSurface != EGL10.EGL_NO_SURFACE) {
            throw new RuntimeException("Already has an EGLSurface");
        }
        EGLSurface eGLSurfaceEglCreateWindowSurface = this.egl.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, nativeWindow, new int[]{R2.drawable.shape_coupon_record_bg});
        this.eglSurface = eGLSurfaceEglCreateWindowSurface;
        if (eGLSurfaceEglCreateWindowSurface != EGL10.EGL_NO_SURFACE) {
            return;
        }
        throw new RuntimeException("Failed to create window surface: 0x" + Integer.toHexString(this.egl.eglGetError()));
    }

    private EGLConfig getEglConfig(EGLDisplay eglDisplay, int[] configAttributes) {
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        int[] iArr = new int[1];
        if (!this.egl.eglChooseConfig(eglDisplay, configAttributes, eGLConfigArr, 1, iArr)) {
            throw new RuntimeException("eglChooseConfig failed: 0x" + Integer.toHexString(this.egl.eglGetError()));
        }
        if (iArr[0] <= 0) {
            throw new RuntimeException("Unable to find any matching EGL config");
        }
        EGLConfig eGLConfig = eGLConfigArr[0];
        if (eGLConfig != null) {
            return eGLConfig;
        }
        throw new RuntimeException("eglChooseConfig returned null");
    }

    private EGLDisplay getEglDisplay() {
        EGLDisplay eGLDisplayEglGetDisplay = this.egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("Unable to get EGL10 display: 0x" + Integer.toHexString(this.egl.eglGetError()));
        }
        if (this.egl.eglInitialize(eGLDisplayEglGetDisplay, new int[2])) {
            return eGLDisplayEglGetDisplay;
        }
        throw new RuntimeException("Unable to initialize EGL10: 0x" + Integer.toHexString(this.egl.eglGetError()));
    }

    @Override // io.agora.rtc.gl.EglBase
    public void createDummyPbufferSurface() {
        createPbufferSurface(1, 1);
    }

    @Override // io.agora.rtc.gl.EglBase
    public void createPbufferSurface(int width, int height) {
        checkIsNotReleased();
        if (this.eglSurface != EGL10.EGL_NO_SURFACE) {
            throw new RuntimeException("Already has an EGLSurface");
        }
        EGLSurface eGLSurfaceEglCreatePbufferSurface = this.egl.eglCreatePbufferSurface(this.eglDisplay, this.eglConfig, new int[]{R2.drawable.shape_discuss_right_press, width, R2.drawable.shape_discuss_right_default, height, R2.drawable.shape_coupon_record_bg});
        this.eglSurface = eGLSurfaceEglCreatePbufferSurface;
        if (eGLSurfaceEglCreatePbufferSurface != EGL10.EGL_NO_SURFACE) {
            return;
        }
        throw new RuntimeException("Failed to create pixel buffer surface with size " + width + "x" + height + ": 0x" + Integer.toHexString(this.egl.eglGetError()));
    }

    @Override // io.agora.rtc.gl.EglBase
    public void createSurface(Surface surface) {
        createSurfaceInternal(new SurfaceHolder(surface) { // from class: io.agora.rtc.gl.EglBase10.1FakeSurfaceHolder
            private final Surface surface;

            {
                this.surface = surface;
            }

            @Override // android.view.SurfaceHolder
            public void addCallback(SurfaceHolder.Callback callback) {
            }

            @Override // android.view.SurfaceHolder
            public Surface getSurface() {
                return this.surface;
            }

            @Override // android.view.SurfaceHolder
            public Rect getSurfaceFrame() {
                return null;
            }

            @Override // android.view.SurfaceHolder
            public boolean isCreating() {
                return false;
            }

            @Override // android.view.SurfaceHolder
            public Canvas lockCanvas() {
                return null;
            }

            @Override // android.view.SurfaceHolder
            public Canvas lockCanvas(Rect rect) {
                return null;
            }

            @Override // android.view.SurfaceHolder
            public void removeCallback(SurfaceHolder.Callback callback) {
            }

            @Override // android.view.SurfaceHolder
            public void setFixedSize(int i2, int i22) {
            }

            @Override // android.view.SurfaceHolder
            public void setFormat(int i2) {
            }

            @Override // android.view.SurfaceHolder
            public void setKeepScreenOn(boolean b3) {
            }

            @Override // android.view.SurfaceHolder
            public void setSizeFromLayout() {
            }

            @Override // android.view.SurfaceHolder
            @Deprecated
            public void setType(int i2) {
            }

            @Override // android.view.SurfaceHolder
            public void unlockCanvasAndPost(Canvas canvas) {
            }
        });
    }

    @Override // io.agora.rtc.gl.EglBase
    public void detachCurrent() {
        synchronized (EglBase.lock) {
            EGL10 egl10 = this.egl;
            EGLDisplay eGLDisplay = this.eglDisplay;
            EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
            if (!egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT)) {
                throw new RuntimeException("eglDetachCurrent failed: 0x" + Integer.toHexString(this.egl.eglGetError()));
            }
        }
    }

    @Override // io.agora.rtc.gl.EglBase
    public EglBase.Context getEglBaseContext() {
        return new Context(this.eglContext);
    }

    @Override // io.agora.rtc.gl.EglBase
    public boolean hasSurface() {
        return this.eglSurface != EGL10.EGL_NO_SURFACE;
    }

    @Override // io.agora.rtc.gl.EglBase
    public void makeCurrent() {
        checkIsNotReleased();
        if (this.eglSurface == EGL10.EGL_NO_SURFACE) {
            throw new RuntimeException("No EGLSurface - can't make current");
        }
        synchronized (EglBase.lock) {
            EGL10 egl10 = this.egl;
            EGLDisplay eGLDisplay = this.eglDisplay;
            EGLSurface eGLSurface = this.eglSurface;
            if (!egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext)) {
                throw new RuntimeException("eglMakeCurrent failed: 0x" + Integer.toHexString(this.egl.eglGetError()));
            }
        }
    }

    @Override // io.agora.rtc.gl.EglBase
    public void release() {
        checkIsNotReleased();
        releaseSurface();
        detachCurrent();
        this.egl.eglDestroyContext(this.eglDisplay, this.eglContext);
        this.egl.eglTerminate(this.eglDisplay);
        this.eglContext = EGL10.EGL_NO_CONTEXT;
        this.eglDisplay = EGL10.EGL_NO_DISPLAY;
        this.eglConfig = null;
    }

    @Override // io.agora.rtc.gl.EglBase
    public void releaseSurface() {
        EGLSurface eGLSurface = this.eglSurface;
        if (eGLSurface != EGL10.EGL_NO_SURFACE) {
            this.egl.eglDestroySurface(this.eglDisplay, eGLSurface);
            this.eglSurface = EGL10.EGL_NO_SURFACE;
        }
    }

    @Override // io.agora.rtc.gl.EglBase
    public int surfaceHeight() {
        int[] iArr = new int[1];
        this.egl.eglQuerySurface(this.eglDisplay, this.eglSurface, R2.drawable.shape_discuss_right_default, iArr);
        return iArr[0];
    }

    @Override // io.agora.rtc.gl.EglBase
    public int surfaceWidth() {
        int[] iArr = new int[1];
        this.egl.eglQuerySurface(this.eglDisplay, this.eglSurface, R2.drawable.shape_discuss_right_press, iArr);
        return iArr[0];
    }

    @Override // io.agora.rtc.gl.EglBase
    public void swapBuffers() {
        checkIsNotReleased();
        if (this.eglSurface == EGL10.EGL_NO_SURFACE) {
            throw new RuntimeException("No EGLSurface - can't swap buffers");
        }
        synchronized (EglBase.lock) {
            this.egl.eglSwapBuffers(this.eglDisplay, this.eglSurface);
        }
    }

    @Override // io.agora.rtc.gl.EglBase
    public void createSurface(SurfaceTexture surfaceTexture) {
        createSurfaceInternal(surfaceTexture);
    }

    @Override // io.agora.rtc.gl.EglBase
    public void swapBuffers(long timeStampNs) {
        swapBuffers();
    }
}
