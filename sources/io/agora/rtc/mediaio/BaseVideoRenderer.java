package io.agora.rtc.mediaio;

import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import io.agora.rtc.gl.EglBase;
import io.agora.rtc.gl.EglRenderer;
import io.agora.rtc.gl.GlRectDrawer;
import io.agora.rtc.gl.JavaI420Buffer;
import io.agora.rtc.gl.RendererCommon;
import io.agora.rtc.gl.RgbaBuffer;
import io.agora.rtc.gl.TextureBufferImpl;
import io.agora.rtc.gl.VideoFrame;
import io.agora.rtc.mediaio.MediaIO;
import io.agora.rtc.utils.ThreadUtils;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes8.dex */
public class BaseVideoRenderer implements SurfaceHolder.Callback, TextureView.SurfaceTextureListener {
    private static final String ERROR_EGL = "Only one egl surface allowed";
    private static final String TAG = "BaseVideoRenderer";
    private final EglRenderer eglRenderer;
    private Surface mSurface;
    private SurfaceTexture mSurfaceTexture;
    private TextureView.SurfaceTextureListener mSurfaceTextureListener;
    private SurfaceView mSurfaceView;
    private SurfaceHolder.Callback mSurfaceViewListener;
    private TextureView mTextureView;
    private int mBufferType = -1;
    private int mPixelFormat = -1;
    private boolean mHasEglSurface = false;
    private boolean mStarted = false;

    public BaseVideoRenderer(String name) {
        this.eglRenderer = new EglRenderer(name);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseBuffer(ByteBuffer buffer) {
    }

    private void rendI420Frame(ByteBuffer data, int format, int width, int height, int rotation, long ts) {
        if (data == null) {
            return;
        }
        int iRemaining = data.remaining();
        byte[] bArr = new byte[iRemaining];
        data.get(bArr, 0, iRemaining);
        JavaI420Buffer javaI420BufferCreateYUV = JavaI420Buffer.createYUV(bArr, width, height);
        if (javaI420BufferCreateYUV == null) {
            return;
        }
        VideoFrame videoFrame = new VideoFrame(javaI420BufferCreateYUV, rotation, ts);
        this.eglRenderer.renderFrame(videoFrame);
        videoFrame.release();
    }

    private void rendRGBAFrame(final ByteBuffer data, int format, int width, int height, int rotation, long ts) {
        if (data == null) {
            return;
        }
        VideoFrame videoFrame = new VideoFrame(new RgbaBuffer(data, width, height, new Runnable() { // from class: io.agora.rtc.mediaio.BaseVideoRenderer.2
            @Override // java.lang.Runnable
            public void run() {
                BaseVideoRenderer.this.releaseBuffer(data);
            }
        }), rotation, ts);
        this.eglRenderer.renderFrame(videoFrame);
        videoFrame.release();
    }

    private void rendTextureFrame(int texId, VideoFrame.TextureBuffer.Type type, int width, int height, int rotation, long ts, float[] matrix) {
        VideoFrame videoFrame = new VideoFrame(new TextureBufferImpl(width, height, type, texId, RendererCommon.convertMatrixToAndroidGraphicsMatrix(matrix), null, new Runnable() { // from class: io.agora.rtc.mediaio.BaseVideoRenderer.1
            @Override // java.lang.Runnable
            public void run() {
            }
        }), rotation, ts);
        this.eglRenderer.renderFrame(videoFrame);
        videoFrame.release();
    }

    public void consume(int texId, int format, int width, int height, int rotation, long ts, float[] matrix) {
        VideoFrame.TextureBuffer.Type type;
        if (this.mStarted) {
            if (format == 11) {
                type = VideoFrame.TextureBuffer.Type.OES;
            } else if (format != 10) {
                return;
            } else {
                type = VideoFrame.TextureBuffer.Type.RGB;
            }
            rendTextureFrame(texId, type, width, height, rotation, ts, matrix);
        }
    }

    public int getBufferType() {
        int i2 = this.mBufferType;
        if (i2 != -1) {
            return i2;
        }
        throw new IllegalArgumentException("Buffer type is not set");
    }

    public long getEGLContextHandle() {
        return this.eglRenderer.getEglContext().getNativeEglContext();
    }

    public EglRenderer getEglRender() {
        return this.eglRenderer;
    }

    public int getPixelFormat() {
        int i2 = this.mPixelFormat;
        if (i2 != -1) {
            return i2;
        }
        throw new IllegalArgumentException("Pixel format is not set");
    }

    public void init(EglBase.Context sharedContext) {
        init(sharedContext, EglBase.CONFIG_PLAIN, new GlRectDrawer());
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        ThreadUtils.checkIsOnMainThread();
        this.eglRenderer.createEglSurface(surface);
        this.mHasEglSurface = true;
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mSurfaceTextureListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureAvailable(surface, width, height);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        ThreadUtils.checkIsOnMainThread();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.eglRenderer.releaseEglSurface(new Runnable() { // from class: io.agora.rtc.mediaio.BaseVideoRenderer.5
            @Override // java.lang.Runnable
            public void run() {
                countDownLatch.countDown();
            }
        });
        ThreadUtils.awaitUninterruptibly(countDownLatch);
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mSurfaceTextureListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureDestroyed(surface);
        }
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.e(TAG, "onSurfaceTextureSizeChanged: width- " + width + ", height: " + height);
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mSurfaceTextureListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureSizeChanged(surface, width, height);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        TextureView.SurfaceTextureListener surfaceTextureListener = this.mSurfaceTextureListener;
        if (surfaceTextureListener != null) {
            surfaceTextureListener.onSurfaceTextureUpdated(surface);
        }
    }

    public void release() {
        this.eglRenderer.release();
    }

    public void setBufferType(MediaIO.BufferType type) {
        this.mBufferType = type.intValue();
    }

    public void setPixelFormat(MediaIO.PixelFormat fmt) {
        this.mPixelFormat = fmt.intValue();
    }

    public void setRenderSurface(Surface surface) {
        ThreadUtils.checkIsOnMainThread();
        if (this.mHasEglSurface) {
            throw new IllegalStateException(ERROR_EGL);
        }
        this.mSurface = surface;
        this.eglRenderer.createEglSurface(surface);
        this.mHasEglSurface = true;
    }

    public void setRenderView(SurfaceView view, SurfaceHolder.Callback listener) {
        ThreadUtils.checkIsOnMainThread();
        if (this.mHasEglSurface) {
            throw new IllegalStateException(ERROR_EGL);
        }
        this.mSurfaceView = view;
        this.mSurfaceViewListener = listener;
        view.getHolder().addCallback(this);
    }

    public boolean start() {
        this.mStarted = true;
        return true;
    }

    public void stop() {
        this.mStarted = false;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        ThreadUtils.checkIsOnMainThread();
        Log.e(TAG, "surfaceChanged: format: " + format + " size: " + width + "x" + height);
        SurfaceHolder.Callback callback = this.mSurfaceViewListener;
        if (callback != null) {
            callback.surfaceChanged(holder, format, width, height);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder holder) {
        ThreadUtils.checkIsOnMainThread();
        this.eglRenderer.createEglSurface(holder.getSurface());
        this.mHasEglSurface = true;
        SurfaceHolder.Callback callback = this.mSurfaceViewListener;
        if (callback != null) {
            callback.surfaceCreated(holder);
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
        ThreadUtils.checkIsOnMainThread();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.eglRenderer.releaseEglSurface(new Runnable() { // from class: io.agora.rtc.mediaio.BaseVideoRenderer.4
            @Override // java.lang.Runnable
            public void run() {
                countDownLatch.countDown();
            }
        });
        ThreadUtils.awaitUninterruptibly(countDownLatch);
        SurfaceHolder.Callback callback = this.mSurfaceViewListener;
        if (callback != null) {
            callback.surfaceDestroyed(holder);
        }
    }

    public void init(final EglBase.Context sharedContext, final int[] configAttributes, RendererCommon.GlDrawer drawer) {
        this.eglRenderer.init(sharedContext, configAttributes, drawer);
    }

    private void rendRGBAFrame(byte[] data, int format, int width, int height, int rotation, long ts) {
        if (data == null || data.length == 0) {
            return;
        }
        final ByteBuffer byteBufferWrap = ByteBuffer.wrap(data);
        VideoFrame videoFrame = new VideoFrame(new RgbaBuffer(byteBufferWrap, width, height, new Runnable() { // from class: io.agora.rtc.mediaio.BaseVideoRenderer.3
            @Override // java.lang.Runnable
            public void run() {
                BaseVideoRenderer.this.releaseBuffer(byteBufferWrap);
            }
        }), rotation, ts);
        this.eglRenderer.renderFrame(videoFrame);
        videoFrame.release();
    }

    public void consume(ByteBuffer buffer, int format, int width, int height, int rotation, long ts) {
        if (this.mStarted) {
            if (format == 1) {
                rendI420Frame(buffer, format, width, height, rotation, ts);
            } else if (format == 4) {
                rendRGBAFrame(buffer, format, width, height, rotation, ts);
            }
        }
    }

    private void rendI420Frame(byte[] data, int format, int width, int height, int rotation, long ts) {
        JavaI420Buffer javaI420BufferCreateYUV;
        if (data == null || data.length == 0 || (javaI420BufferCreateYUV = JavaI420Buffer.createYUV(data, width, height)) == null) {
            return;
        }
        VideoFrame videoFrame = new VideoFrame(javaI420BufferCreateYUV, rotation, ts);
        this.eglRenderer.renderFrame(videoFrame);
        videoFrame.release();
    }

    public void setRenderSurface(SurfaceTexture st) {
        ThreadUtils.checkIsOnMainThread();
        if (!this.mHasEglSurface) {
            this.mSurfaceTexture = st;
            this.eglRenderer.createEglSurface(st);
            this.mHasEglSurface = true;
            return;
        }
        throw new IllegalStateException(ERROR_EGL);
    }

    public void setRenderView(TextureView view, TextureView.SurfaceTextureListener listener) {
        ThreadUtils.checkIsOnMainThread();
        if (!this.mHasEglSurface) {
            this.mTextureView = view;
            this.mSurfaceTextureListener = listener;
            view.setSurfaceTextureListener(this);
            return;
        }
        throw new IllegalStateException(ERROR_EGL);
    }

    public void consume(byte[] data, int format, int width, int height, int rotation, long ts) {
        if (this.mStarted) {
            if (format == 1) {
                rendI420Frame(data, format, width, height, rotation, ts);
            } else if (format == 4) {
                rendRGBAFrame(data, format, width, height, rotation, ts);
            }
        }
    }
}
