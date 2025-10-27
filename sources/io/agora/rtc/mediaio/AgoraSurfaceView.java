package io.agora.rtc.mediaio;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import io.agora.rtc.gl.EglBase;
import io.agora.rtc.gl.RendererCommon;
import io.agora.rtc.mediaio.MediaIO;
import io.agora.rtc.utils.ThreadUtils;
import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public class AgoraSurfaceView extends SurfaceView implements IVideoSink, SurfaceHolder.Callback {
    private static final String TAG = "AgoraSurfaceView";
    private int[] mConfigAttributes;
    private RendererCommon.GlDrawer mDrawer;
    private EglBase.Context mEglContext;
    private BaseVideoRenderer mRender;

    public AgoraSurfaceView(Context context) {
        super(context);
        BaseVideoRenderer baseVideoRenderer = new BaseVideoRenderer(TAG);
        this.mRender = baseVideoRenderer;
        baseVideoRenderer.setRenderView(this, this);
    }

    @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
    public void consumeByteArrayFrame(byte[] data, int pixelFormat, int width, int height, int rotation, long ts) {
        this.mRender.consume(data, pixelFormat, width, height, rotation, ts);
    }

    @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
    public void consumeByteBufferFrame(ByteBuffer buffer, int format, int width, int height, int rotation, long ts) {
        this.mRender.consume(buffer, format, width, height, rotation, ts);
    }

    @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
    public void consumeTextureFrame(int texId, int pixelFormat, int width, int height, int rotation, long ts, float[] matrix) {
        this.mRender.consume(texId, pixelFormat, width, height, rotation, ts, matrix);
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public int getBufferType() {
        int bufferType = this.mRender.getBufferType();
        if (bufferType != -1) {
            return bufferType;
        }
        throw new IllegalArgumentException("Buffer type is not set");
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public long getEGLContextHandle() {
        return this.mRender.getEGLContextHandle();
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public int getPixelFormat() {
        int pixelFormat = this.mRender.getPixelFormat();
        if (pixelFormat != -1) {
            return pixelFormat;
        }
        throw new IllegalArgumentException("Pixel format is not set");
    }

    public void init(EglBase.Context sharedContext) {
        this.mEglContext = sharedContext;
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public void onDispose() {
        this.mRender.release();
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public boolean onInitialize() {
        RendererCommon.GlDrawer glDrawer;
        int[] iArr = this.mConfigAttributes;
        if (iArr == null || (glDrawer = this.mDrawer) == null) {
            this.mRender.init(this.mEglContext);
            return true;
        }
        this.mRender.init(this.mEglContext, iArr, glDrawer);
        return true;
    }

    @Override // android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        ThreadUtils.checkIsOnMainThread();
        this.mRender.getEglRender().setLayoutAspectRatio((right - left) / (bottom - top2));
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public boolean onStart() {
        return this.mRender.start();
    }

    @Override // io.agora.rtc.mediaio.IVideoSink
    public void onStop() {
        this.mRender.stop();
    }

    public void setBufferType(MediaIO.BufferType type) {
        this.mRender.setBufferType(type);
    }

    public void setMirror(final boolean mirror) {
        this.mRender.getEglRender().setMirror(mirror);
    }

    public void setPixelFormat(MediaIO.PixelFormat pixelFormat) {
        this.mRender.setPixelFormat(pixelFormat);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i(TAG, "surfaceChanged: format: " + format + " size: " + width + "x" + height);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(final SurfaceHolder holder) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public void init(final EglBase.Context sharedContext, final int[] configAttributes, RendererCommon.GlDrawer drawer) {
        this.mEglContext = sharedContext;
        this.mConfigAttributes = configAttributes;
        this.mDrawer = drawer;
    }

    public AgoraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        BaseVideoRenderer baseVideoRenderer = new BaseVideoRenderer(TAG);
        this.mRender = baseVideoRenderer;
        baseVideoRenderer.setRenderView(this, this);
    }
}
