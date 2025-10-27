package org.wrtca.api;

import android.annotation.TargetApi;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.HandlerThread;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import org.wrtca.api.EglBase;
import org.wrtca.api.VideoFrame;
import org.wrtca.jni.CalledByNative;
import org.wrtca.log.Logging;
import org.wrtca.util.ThreadUtils;
import org.wrtca.video.TextureBufferImpl;

/* loaded from: classes9.dex */
public class SurfaceTextureHelper {
    private static final String TAG = "SurfaceTextureHelper";
    private final EglBase eglBase;
    private final Handler handler;
    private boolean hasPendingTexture;
    private boolean isQuitting;
    private volatile boolean isTextureInUse;
    private OnTextureFrameAvailableListener listener;
    private final int oesTextureId;
    private OnTextureFrameAvailableListener pendingListener;
    public final Runnable setListenerRunnable;
    private final SurfaceTexture surfaceTexture;
    private YuvConverter yuvConverter;

    public interface OnTextureFrameAvailableListener {
        void onTextureFrameAvailable(int i2, float[] fArr, long j2);
    }

    @CalledByNative
    public static SurfaceTextureHelper create(final String str, final EglBase.Context context) {
        HandlerThread handlerThread = new HandlerThread(str);
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper());
        return (SurfaceTextureHelper) ThreadUtils.invokeAtFrontUninterruptibly(handler, new Callable<SurfaceTextureHelper>() { // from class: org.wrtca.api.SurfaceTextureHelper.1
            @Override // java.util.concurrent.Callable
            public SurfaceTextureHelper call() {
                try {
                    c.h.a(SurfaceTextureHelper.TAG, "create surfacehelper " + context);
                    SurfaceTextureHelper surfaceTextureHelper = new SurfaceTextureHelper(context, handler);
                    if (j.d.d().p() != null) {
                        c.h.a(SurfaceTextureHelper.TAG, "call VideoFramePreProcessListener onGLContextCreated ");
                        j.d.d().p().onGLContextCreated();
                    }
                    return surfaceTextureHelper;
                } catch (RuntimeException e2) {
                    Logging.e(SurfaceTextureHelper.TAG, str + " create failure", e2);
                    return null;
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(SurfaceTexture surfaceTexture) {
        this.hasPendingTexture = true;
        tryDeliverTextureFrame();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$textureToYuv$1(VideoFrame.I420Buffer[] i420BufferArr, VideoFrame.TextureBuffer textureBuffer) {
        if (this.yuvConverter == null) {
            this.yuvConverter = new YuvConverter();
        }
        i420BufferArr[0] = this.yuvConverter.convert(textureBuffer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void release() {
        if (this.handler.getLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Wrong thread.");
        }
        if (this.isTextureInUse || !this.isQuitting) {
            throw new IllegalStateException("Unexpected release.");
        }
        YuvConverter yuvConverter = this.yuvConverter;
        if (yuvConverter != null) {
            yuvConverter.release();
        }
        GLES20.glDeleteTextures(1, new int[]{this.oesTextureId}, 0);
        if (j.d.d().p() != null) {
            c.h.a(TAG, "call VideoFramePreProcessListener onGLContextDestroy ");
            j.d.d().p().onGLContextDestroy();
        }
        this.surfaceTexture.release();
        this.eglBase.release();
        this.handler.getLooper().quit();
        c.h.a(TAG, "surfaceTexture helper camera released");
    }

    @TargetApi(21)
    private static void setOnFrameAvailableListener(SurfaceTexture surfaceTexture, SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener, Handler handler) {
        surfaceTexture.setOnFrameAvailableListener(onFrameAvailableListener, handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryDeliverTextureFrame() {
        if (this.handler.getLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("Wrong thread.");
        }
        if (this.isQuitting || !this.hasPendingTexture || this.isTextureInUse || this.listener == null) {
            return;
        }
        this.isTextureInUse = true;
        this.hasPendingTexture = false;
        updateTexImage();
        float[] fArr = new float[16];
        this.surfaceTexture.getTransformMatrix(fArr);
        this.listener.onTextureFrameAvailable(this.oesTextureId, fArr, this.surfaceTexture.getTimestamp());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTexImage() {
        synchronized (EglBase.lock) {
            this.surfaceTexture.updateTexImage();
        }
    }

    public VideoFrame.TextureBuffer createTextureBuffer(int i2, int i3, Matrix matrix) {
        return new TextureBufferImpl(i2, i3, VideoFrame.TextureBuffer.Type.OES, this.oesTextureId, matrix, this, new Runnable() { // from class: org.wrtca.api.SurfaceTextureHelper.7
            @Override // java.lang.Runnable
            public void run() {
                SurfaceTextureHelper.this.returnTextureFrame();
            }
        });
    }

    public VideoFrame.TextureBuffer createTextureBufferSpecified(int i2, int i3, VideoFrame.TextureBuffer.Type type, int i4, Matrix matrix) {
        return new TextureBufferImpl(i2, i3, type, i4, matrix, this, new Runnable() { // from class: org.wrtca.api.SurfaceTextureHelper.8
            @Override // java.lang.Runnable
            public void run() {
            }
        });
    }

    @CalledByNative
    public void dispose() {
        Logging.d(TAG, "dispose()");
        c.h.a(TAG, "surface texture dispose");
        ThreadUtils.invokeAtFrontUninterruptibly(this.handler, new Runnable() { // from class: org.wrtca.api.SurfaceTextureHelper.5
            @Override // java.lang.Runnable
            public void run() {
                SurfaceTextureHelper.this.isQuitting = true;
                if (SurfaceTextureHelper.this.isTextureInUse) {
                    return;
                }
                c.h.a(SurfaceTextureHelper.TAG, "surface texture release");
                SurfaceTextureHelper.this.release();
            }
        });
    }

    public Handler getHandler() {
        return this.handler;
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.surfaceTexture;
    }

    public boolean isTextureInUse() {
        return this.isTextureInUse;
    }

    @CalledByNative
    public void returnTextureFrame() {
        this.handler.post(new Runnable() { // from class: org.wrtca.api.SurfaceTextureHelper.4
            @Override // java.lang.Runnable
            public void run() {
                SurfaceTextureHelper.this.isTextureInUse = false;
                if (SurfaceTextureHelper.this.isQuitting) {
                    SurfaceTextureHelper.this.release();
                } else {
                    SurfaceTextureHelper.this.tryDeliverTextureFrame();
                }
            }
        });
    }

    public void startListening(OnTextureFrameAvailableListener onTextureFrameAvailableListener) {
        if (this.listener != null || this.pendingListener != null) {
            throw new IllegalStateException("SurfaceTextureHelper listener has already been set.");
        }
        this.pendingListener = onTextureFrameAvailableListener;
        this.handler.post(this.setListenerRunnable);
    }

    public void stopListening() {
        Logging.d(TAG, "stopListening()");
        this.handler.removeCallbacks(this.setListenerRunnable);
        ThreadUtils.invokeAtFrontUninterruptibly(this.handler, new Runnable() { // from class: org.wrtca.api.SurfaceTextureHelper.3
            @Override // java.lang.Runnable
            public void run() {
                SurfaceTextureHelper.this.listener = null;
                SurfaceTextureHelper.this.pendingListener = null;
            }
        });
    }

    @CalledByNative
    @Deprecated
    public void textureToYUV(final ByteBuffer byteBuffer, final int i2, final int i3, final int i4, final int i5, final float[] fArr) {
        if (i5 != this.oesTextureId) {
            throw new IllegalStateException("textureToByteBuffer called with unexpected textureId");
        }
        ThreadUtils.invokeAtFrontUninterruptibly(this.handler, new Runnable() { // from class: org.wrtca.api.SurfaceTextureHelper.6
            @Override // java.lang.Runnable
            public void run() {
                if (SurfaceTextureHelper.this.yuvConverter == null) {
                    SurfaceTextureHelper.this.yuvConverter = new YuvConverter();
                }
                c.h.a(SurfaceTextureHelper.TAG, "SurfaceTextureHelpertextureToYUV");
                SurfaceTextureHelper.this.yuvConverter.convert(byteBuffer, i2, i3, i4, i5, fArr);
            }
        });
    }

    public VideoFrame.I420Buffer textureToYuv(final VideoFrame.TextureBuffer textureBuffer) {
        final VideoFrame.I420Buffer[] i420BufferArr = new VideoFrame.I420Buffer[1];
        ThreadUtils.invokeAtFrontUninterruptibly(this.handler, new Runnable() { // from class: org.wrtca.api.n
            @Override // java.lang.Runnable
            public final void run() {
                this.f28122c.lambda$textureToYuv$1(i420BufferArr, textureBuffer);
            }
        });
        return i420BufferArr[0];
    }

    private SurfaceTextureHelper(EglBase.Context context, Handler handler) {
        this.hasPendingTexture = false;
        this.isTextureInUse = false;
        this.isQuitting = false;
        this.setListenerRunnable = new Runnable() { // from class: org.wrtca.api.SurfaceTextureHelper.2
            @Override // java.lang.Runnable
            public void run() {
                Logging.d(SurfaceTextureHelper.TAG, "Setting listener to " + SurfaceTextureHelper.this.pendingListener);
                SurfaceTextureHelper surfaceTextureHelper = SurfaceTextureHelper.this;
                surfaceTextureHelper.listener = surfaceTextureHelper.pendingListener;
                SurfaceTextureHelper.this.pendingListener = null;
                if (SurfaceTextureHelper.this.hasPendingTexture) {
                    SurfaceTextureHelper.this.updateTexImage();
                    SurfaceTextureHelper.this.hasPendingTexture = false;
                }
            }
        };
        if (handler.getLooper().getThread() != Thread.currentThread()) {
            throw new IllegalStateException("SurfaceTextureHelper must be created on the handler thread");
        }
        this.handler = handler;
        EglBase eglBaseC = a.c(context, EglBase.CONFIG_PIXEL_BUFFER);
        this.eglBase = eglBaseC;
        try {
            eglBaseC.createDummyPbufferSurface();
            eglBaseC.makeCurrent();
            int iGenerateTexture = GlUtil.generateTexture(36197);
            this.oesTextureId = iGenerateTexture;
            SurfaceTexture surfaceTexture = new SurfaceTexture(iGenerateTexture);
            this.surfaceTexture = surfaceTexture;
            setOnFrameAvailableListener(surfaceTexture, new SurfaceTexture.OnFrameAvailableListener() { // from class: org.wrtca.api.o
                @Override // android.graphics.SurfaceTexture.OnFrameAvailableListener
                public final void onFrameAvailable(SurfaceTexture surfaceTexture2) {
                    this.f28125c.lambda$new$0(surfaceTexture2);
                }
            }, handler);
        } catch (RuntimeException e2) {
            this.eglBase.release();
            handler.getLooper().quit();
            throw e2;
        }
    }
}
