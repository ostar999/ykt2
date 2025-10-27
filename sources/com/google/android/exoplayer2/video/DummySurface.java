package com.google.android.exoplayer2.video;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Surface;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EGLSurfaceTexture;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.util.Log;

@RequiresApi(17)
/* loaded from: classes3.dex */
public final class DummySurface extends Surface {
    private static final String TAG = "DummySurface";
    private static int secureMode;
    private static boolean secureModeInitialized;
    public final boolean secure;
    private final DummySurfaceThread thread;
    private boolean threadReleased;

    public static class DummySurfaceThread extends HandlerThread implements Handler.Callback {
        private static final int MSG_INIT = 1;
        private static final int MSG_RELEASE = 2;
        private EGLSurfaceTexture eglSurfaceTexture;
        private Handler handler;

        @Nullable
        private Error initError;

        @Nullable
        private RuntimeException initException;

        @Nullable
        private DummySurface surface;

        public DummySurfaceThread() {
            super("ExoPlayer:DummySurface");
        }

        private void initInternal(int i2) {
            Assertions.checkNotNull(this.eglSurfaceTexture);
            this.eglSurfaceTexture.init(i2);
            this.surface = new DummySurface(this, this.eglSurfaceTexture.getSurfaceTexture(), i2 != 0);
        }

        private void releaseInternal() {
            Assertions.checkNotNull(this.eglSurfaceTexture);
            this.eglSurfaceTexture.release();
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i2 = message.what;
            try {
                if (i2 != 1) {
                    if (i2 != 2) {
                        return true;
                    }
                    try {
                        releaseInternal();
                    } finally {
                        try {
                            return true;
                        } finally {
                        }
                    }
                    return true;
                }
                try {
                    initInternal(message.arg1);
                    synchronized (this) {
                        notify();
                    }
                } catch (Error e2) {
                    Log.e(DummySurface.TAG, "Failed to initialize dummy surface", e2);
                    this.initError = e2;
                    synchronized (this) {
                        notify();
                    }
                } catch (RuntimeException e3) {
                    Log.e(DummySurface.TAG, "Failed to initialize dummy surface", e3);
                    this.initException = e3;
                    synchronized (this) {
                        notify();
                    }
                }
                return true;
            } catch (Throwable th) {
                synchronized (this) {
                    notify();
                    throw th;
                }
            }
        }

        public DummySurface init(int i2) {
            boolean z2;
            start();
            this.handler = new Handler(getLooper(), this);
            this.eglSurfaceTexture = new EGLSurfaceTexture(this.handler);
            synchronized (this) {
                z2 = false;
                this.handler.obtainMessage(1, i2, 0).sendToTarget();
                while (this.surface == null && this.initException == null && this.initError == null) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                        z2 = true;
                    }
                }
            }
            if (z2) {
                Thread.currentThread().interrupt();
            }
            RuntimeException runtimeException = this.initException;
            if (runtimeException != null) {
                throw runtimeException;
            }
            Error error = this.initError;
            if (error == null) {
                return (DummySurface) Assertions.checkNotNull(this.surface);
            }
            throw error;
        }

        public void release() {
            Assertions.checkNotNull(this.handler);
            this.handler.sendEmptyMessage(2);
        }
    }

    private static int getSecureMode(Context context) {
        if (GlUtil.isProtectedContentExtensionSupported(context)) {
            return GlUtil.isSurfacelessContextExtensionSupported() ? 1 : 2;
        }
        return 0;
    }

    public static synchronized boolean isSecureSupported(Context context) {
        if (!secureModeInitialized) {
            secureMode = getSecureMode(context);
            secureModeInitialized = true;
        }
        return secureMode != 0;
    }

    public static DummySurface newInstanceV17(Context context, boolean z2) {
        Assertions.checkState(!z2 || isSecureSupported(context));
        return new DummySurfaceThread().init(z2 ? secureMode : 0);
    }

    @Override // android.view.Surface
    public void release() {
        super.release();
        synchronized (this.thread) {
            if (!this.threadReleased) {
                this.thread.release();
                this.threadReleased = true;
            }
        }
    }

    private DummySurface(DummySurfaceThread dummySurfaceThread, SurfaceTexture surfaceTexture, boolean z2) {
        super(surfaceTexture);
        this.thread = dummySurfaceThread;
        this.secure = z2;
    }
}
