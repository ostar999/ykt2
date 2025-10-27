package org.wrtca.video;

import android.graphics.SurfaceTexture;
import android.view.TextureView;
import c.h;
import core.interfaces.DataReceiver;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import org.wrtca.api.EglBase;
import org.wrtca.api.EglRenderer;
import org.wrtca.api.JavaI420Buffer;
import org.wrtca.api.RendererCommon;
import org.wrtca.api.VideoFrame;
import org.wrtca.api.VideoRenderer;
import org.wrtca.api.YuvHelper;
import org.wrtca.api.m;
import org.wrtca.log.Logging;
import org.wrtca.util.ThreadUtils;

/* loaded from: classes9.dex */
public class TextureEglRenderer extends EglRenderer implements TextureView.SurfaceTextureListener {
    private static final String TAG = "TextureEglRenderer";
    private int frameRotation;
    private boolean isRenderingPaused;
    private final Object layoutLock;
    private DataReceiver mDataReceiver;
    private int rotatedFrameHeight;
    private int rotatedFrameWidth;

    public TextureEglRenderer(String str) {
        super(str);
        this.layoutLock = new Object();
        this.isRenderingPaused = false;
    }

    private void logD(String str) {
        Logging.d(TAG, this.name + ": " + str);
    }

    private void updateFrameDimensionsAndReportEvents(VideoRenderer.I420Frame i420Frame) {
        synchronized (this.layoutLock) {
            if (this.isRenderingPaused) {
                return;
            }
            if (this.rotatedFrameWidth != i420Frame.rotatedWidth() || this.rotatedFrameHeight != i420Frame.rotatedHeight() || this.frameRotation != i420Frame.rotationDegree) {
                h.a(TAG, "textureEglRender: " + this + "Reporting frame resolution changed to " + i420Frame.width + "x" + i420Frame.height + " with rotation " + i420Frame.rotationDegree + " rendererEvents: " + this.rendererEvents);
                RendererCommon.RendererEvents rendererEvents = this.rendererEvents;
                if (rendererEvents != null) {
                    rendererEvents.onFrameResolutionChanged(i420Frame.width, i420Frame.height, i420Frame.rotationDegree);
                }
                this.rotatedFrameWidth = i420Frame.rotatedWidth();
                this.rotatedFrameHeight = i420Frame.rotatedHeight();
                this.frameRotation = i420Frame.rotationDegree;
            }
        }
    }

    public void attachRenderEvents(RendererCommon.RendererEvents rendererEvents) {
        this.rendererEvents = rendererEvents;
        h.a(TAG, "textureEglRender " + this + " attach " + this.rendererEvents);
    }

    @Override // org.wrtca.api.EglRenderer
    public void disableFpsReduction() {
        synchronized (this.layoutLock) {
            this.isRenderingPaused = false;
        }
        super.disableFpsReduction();
    }

    public void init(EglBase.Context context, RendererCommon.RendererEvents rendererEvents, int[] iArr, RendererCommon.GlDrawer glDrawer, DataReceiver dataReceiver) {
        ThreadUtils.checkIsOnMainThread();
        this.rendererEvents = rendererEvents;
        h.a(TAG, "TextureEglRenderer: " + this + " rendererEvents: " + rendererEvents);
        this.mDataReceiver = dataReceiver;
        synchronized (this.layoutLock) {
            this.isFirstFrameRendered = false;
            this.rotatedFrameWidth = 0;
            this.rotatedFrameHeight = 0;
            this.frameRotation = 0;
        }
        super.init(context, iArr, glDrawer, rendererEvents);
    }

    @Override // org.wrtca.api.EglRenderer, org.wrtca.api.VideoSink
    public void onFrame(VideoFrame videoFrame) {
        updateFrameDimensionsAndReportEvents(videoFrame);
        if (this.mDataReceiver == null) {
            super.onFrame(videoFrame);
            return;
        }
        VideoFrame.Buffer buffer = videoFrame.getBuffer();
        if (buffer instanceof JavaI420Buffer) {
            JavaI420Buffer javaI420Buffer = (JavaI420Buffer) buffer;
            int type = ((this.mDataReceiver.getType() & 240) >> 4) * javaI420Buffer.getWidth() * javaI420Buffer.getHeight();
            ByteBuffer cacheBuffer = this.mDataReceiver.getCacheBuffer();
            cacheBuffer.limit(type);
            YuvHelper.I420ToRgba(this.mDataReceiver.getType(), buffer.getWidth(), buffer.getHeight(), javaI420Buffer.getDataY(), javaI420Buffer.getStrideY(), javaI420Buffer.getDataU(), javaI420Buffer.getStrideU(), javaI420Buffer.getDataV(), javaI420Buffer.getStrideV(), cacheBuffer);
            this.mDataReceiver.onReceiveRGBAData(cacheBuffer, javaI420Buffer.getWidth(), javaI420Buffer.getHeight());
        }
        videoFrame.release();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
        h.a(TAG, " TextureEglRenderer onSurfaceTextureAvailable surface: " + surfaceTexture + "width: " + i2 + " height: " + i3);
        ThreadUtils.checkIsOnMainThread();
        createEglSurface(surfaceTexture);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        h.a(TAG, " TextureEglRenderer onSurfaceTextureDestroyed surface: " + surfaceTexture);
        ThreadUtils.checkIsOnMainThread();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        releaseEglSurface(new m(countDownLatch));
        h.a(TAG, "TextureEglRenderer: " + this + " rendererEvents clear: " + this.rendererEvents);
        this.rotatedFrameHeight = 0;
        this.rotatedFrameWidth = 0;
        this.rendererEvents = null;
        ThreadUtils.awaitUninterruptibly(countDownLatch);
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
        h.a(TAG, " TextureEglRenderer onSurfaceTextureSizeChanged surface: " + surfaceTexture + "width: " + i2 + " height: " + i3);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    @Override // org.wrtca.api.EglRenderer
    public void pauseVideo() {
        synchronized (this.layoutLock) {
            this.isRenderingPaused = true;
        }
        super.pauseVideo();
    }

    @Override // org.wrtca.api.EglRenderer, org.wrtca.api.VideoRenderer.Callbacks
    public void renderFrame(VideoRenderer.I420Frame i420Frame) {
        updateFrameDimensionsAndReportEvents(i420Frame);
        super.renderFrame(i420Frame);
    }

    @Override // org.wrtca.api.EglRenderer
    public void setFpsReduction(float f2) {
        synchronized (this.layoutLock) {
            this.isRenderingPaused = f2 == 0.0f;
        }
        super.setFpsReduction(f2);
    }

    private void updateFrameDimensionsAndReportEvents(VideoFrame videoFrame) {
        synchronized (this.layoutLock) {
            if (this.isRenderingPaused) {
                return;
            }
            if (this.rotatedFrameWidth != videoFrame.getRotatedWidth() || this.rotatedFrameHeight != videoFrame.getRotatedHeight() || this.frameRotation != videoFrame.getRotation()) {
                logD("Reporting frame resolution changed to " + videoFrame.getBuffer().getWidth() + "x" + videoFrame.getBuffer().getHeight() + " with rotation " + videoFrame.getRotation());
                RendererCommon.RendererEvents rendererEvents = this.rendererEvents;
                if (rendererEvents != null) {
                    rendererEvents.onFrameResolutionChanged(videoFrame.getBuffer().getWidth(), videoFrame.getBuffer().getHeight(), videoFrame.getRotation());
                }
                this.rotatedFrameWidth = videoFrame.getRotatedWidth();
                this.rotatedFrameHeight = videoFrame.getRotatedHeight();
                this.frameRotation = videoFrame.getRotation();
            }
        }
    }

    @Override // org.wrtca.api.EglRenderer
    public void init(EglBase.Context context, int[] iArr, RendererCommon.GlDrawer glDrawer, RendererCommon.RendererEvents rendererEvents) {
        init(context, rendererEvents, iArr, glDrawer, this.mDataReceiver);
    }
}
