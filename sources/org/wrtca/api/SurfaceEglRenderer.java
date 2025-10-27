package org.wrtca.api;

import android.view.SurfaceHolder;
import core.interfaces.DataReceiver;
import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import org.wrtca.api.EglBase;
import org.wrtca.api.RendererCommon;
import org.wrtca.api.VideoFrame;
import org.wrtca.api.VideoRenderer;
import org.wrtca.log.Logging;
import org.wrtca.util.ThreadUtils;

/* loaded from: classes9.dex */
public class SurfaceEglRenderer extends EglRenderer implements SurfaceHolder.Callback {
    private static final String TAG = "SurfaceEglRenderer";
    private int frameRotation;
    private boolean isFirstFrameRendered;
    private boolean isRenderingPaused;
    private final Object layoutLock;
    private DataReceiver mDataReceiver;
    private RendererCommon.RendererEvents rendererEvents;
    private int rotatedFrameHeight;
    private int rotatedFrameWidth;

    public SurfaceEglRenderer(String str) {
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
            if (!this.isFirstFrameRendered) {
                this.isFirstFrameRendered = true;
            }
            if (this.rotatedFrameWidth != i420Frame.rotatedWidth() || this.rotatedFrameHeight != i420Frame.rotatedHeight() || this.frameRotation != i420Frame.rotationDegree) {
                logD("Reporting frame resolution changed to " + i420Frame.width + "x" + i420Frame.height + " with rotation " + i420Frame.rotationDegree);
                if (this.rendererEvents != null) {
                    c.h.a(TAG, "i420 onFrameResolutionChanged " + this.rendererEvents);
                    this.rendererEvents.onFrameResolutionChanged(i420Frame.width, i420Frame.height, i420Frame.rotationDegree);
                }
                this.rotatedFrameWidth = i420Frame.rotatedWidth();
                this.rotatedFrameHeight = i420Frame.rotatedHeight();
                this.frameRotation = i420Frame.rotationDegree;
            }
        }
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
        this.mDataReceiver = dataReceiver;
        synchronized (this.layoutLock) {
            c.h.a(TAG, "reset isFirstFrameRendered = false");
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

    @Override // org.wrtca.api.EglRenderer
    public void pauseVideo() {
        synchronized (this.layoutLock) {
            this.isRenderingPaused = true;
        }
        super.pauseVideo();
    }

    @Override // org.wrtca.api.EglRenderer
    public void release() {
        c.h.a(TAG, "release SurfaceEglRenderer");
        this.rendererEvents = null;
        super.release();
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

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
        ThreadUtils.checkIsOnMainThread();
        logD("surfaceChanged: format: " + i2 + " size: " + i3 + "x" + i4);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        ThreadUtils.checkIsOnMainThread();
        c.h.a(TAG, "SurfaceEglRenderer : surfaceCreated createEglSurface " + surfaceHolder.getSurface());
        createEglSurface(surfaceHolder.getSurface());
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        ThreadUtils.checkIsOnMainThread();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        c.h.a(TAG, "SurfaceEglRenderer : surfaceDestroyed release");
        releaseEglSurface(new m(countDownLatch));
        ThreadUtils.awaitUninterruptibly(countDownLatch);
    }

    @Override // org.wrtca.api.EglRenderer
    public void init(EglBase.Context context, int[] iArr, RendererCommon.GlDrawer glDrawer, RendererCommon.RendererEvents rendererEvents) {
        init(context, rendererEvents, iArr, glDrawer, this.mDataReceiver);
    }

    private void updateFrameDimensionsAndReportEvents(VideoFrame videoFrame) {
        synchronized (this.layoutLock) {
            if (this.isRenderingPaused) {
                return;
            }
            if (!this.isFirstFrameRendered) {
                this.isFirstFrameRendered = true;
            }
            if (this.rotatedFrameWidth != videoFrame.getRotatedWidth() || this.rotatedFrameHeight != videoFrame.getRotatedHeight() || this.frameRotation != videoFrame.getRotation()) {
                logD("Reporting frame resolution changed to " + videoFrame.getBuffer().getWidth() + "x" + videoFrame.getBuffer().getHeight() + " with rotation " + videoFrame.getRotation());
                if (this.rendererEvents != null) {
                    c.h.a(TAG, "videoframe onFrameResolutionChanged " + this.rendererEvents);
                    this.rendererEvents.onFrameResolutionChanged(videoFrame.getBuffer().getWidth(), videoFrame.getBuffer().getHeight(), videoFrame.getRotation());
                }
                this.rotatedFrameWidth = videoFrame.getRotatedWidth();
                this.rotatedFrameHeight = videoFrame.getRotatedHeight();
                this.frameRotation = videoFrame.getRotation();
            }
        }
    }
}
