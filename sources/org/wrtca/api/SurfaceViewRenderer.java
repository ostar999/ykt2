package org.wrtca.api;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import core.data.StreamInfo;
import core.interfaces.DataReceiver;
import core.interfaces.FirstFrameRendered;
import org.wrtca.api.EglBase;
import org.wrtca.api.EglRenderer;
import org.wrtca.api.RendererCommon;
import org.wrtca.api.VideoRenderer;
import org.wrtca.log.Logging;
import org.wrtca.util.ThreadUtils;

/* loaded from: classes9.dex */
public class SurfaceViewRenderer extends SurfaceView implements SurfaceHolder.Callback, VideoRenderer.Callbacks, VideoSink, RendererCommon.RendererEvents {
    private static final String TAG = "SurfaceViewRenderer";
    private final SurfaceEglRenderer eglRenderer;
    private boolean enableFixedSize;
    public FirstFrameRendered mFrameRendered;
    public FirstFrameRendered mPeerConnectionCallBack;
    public int mScaleType;
    public StreamInfo mStreamInfo;
    private RendererCommon.RendererEvents rendererEvents;
    private final String resourceName;
    private int rotatedFrameHeight;
    private int rotatedFrameWidth;
    private int surfaceHeight;
    private int surfaceWidth;
    private final RendererCommon.VideoLayoutMeasure videoLayoutMeasure;

    public SurfaceViewRenderer(Context context) {
        super(context);
        this.videoLayoutMeasure = new RendererCommon.VideoLayoutMeasure();
        this.mScaleType = -1;
        String resourceName = getResourceName();
        this.resourceName = resourceName;
        SurfaceEglRenderer surfaceEglRenderer = new SurfaceEglRenderer(resourceName);
        this.eglRenderer = surfaceEglRenderer;
        getHolder().addCallback(this);
        getHolder().addCallback(surfaceEglRenderer);
    }

    private String getResourceName() {
        try {
            return getResources().getResourceEntryName(getId());
        } catch (Resources.NotFoundException unused) {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrameResolutionChanged$0(int i2, int i3) {
        this.rotatedFrameWidth = i2;
        this.rotatedFrameHeight = i3;
        updateSurfaceSize();
        requestLayout();
    }

    private void logD(String str) {
        Logging.d(TAG, this.resourceName + ": " + str);
    }

    private void postOrRun(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    private void updateSurfaceSize() {
        ThreadUtils.checkIsOnMainThread();
        if (!this.enableFixedSize || this.rotatedFrameWidth == 0 || this.rotatedFrameHeight == 0 || getWidth() == 0 || getHeight() == 0) {
            this.surfaceHeight = 0;
            this.surfaceWidth = 0;
            getHolder().setSizeFromLayout();
            return;
        }
        float width = getWidth() / getHeight();
        int i2 = this.rotatedFrameWidth;
        float f2 = i2;
        int i3 = this.rotatedFrameHeight;
        float f3 = i3;
        if (f2 / f3 > width) {
            i2 = (int) (f3 * width);
        } else {
            i3 = (int) (f2 / width);
        }
        int iMin = Math.min(getWidth(), i2);
        int iMin2 = Math.min(getHeight(), i3);
        c.h.a(TAG, "SurfaceViewRendererupdateSurfaceSize. Layout size: " + getWidth() + "x" + getHeight() + ", frame size: " + this.rotatedFrameWidth + "x" + this.rotatedFrameHeight + ", requested surface size: " + iMin + "x" + iMin2 + ", old surface size: " + this.surfaceWidth + "x" + this.surfaceHeight);
        if (iMin == this.surfaceWidth && iMin2 == this.surfaceHeight) {
            return;
        }
        this.surfaceWidth = iMin;
        this.surfaceHeight = iMin2;
        getHolder().setFixedSize(iMin, iMin2);
    }

    public void addFrameListener(EglRenderer.FrameListener frameListener, float f2, RendererCommon.GlDrawer glDrawer) {
        this.eglRenderer.addFrameListener(frameListener, f2, glDrawer);
    }

    public void clearImage() {
        this.eglRenderer.clearImage();
    }

    public void disableFpsReduction() {
        this.eglRenderer.disableFpsReduction();
    }

    public StreamInfo getStreamInfo() {
        return this.mStreamInfo;
    }

    public void init(EglBase.Context context, RendererCommon.RendererEvents rendererEvents, DataReceiver dataReceiver) {
        init(context, rendererEvents, EglBase.CONFIG_PLAIN, new GlRectDrawer(), dataReceiver);
    }

    public void onFirstFrameRendered() {
        RendererCommon.RendererEvents rendererEvents = this.rendererEvents;
        if (rendererEvents == null || rendererEvents == this) {
            return;
        }
        rendererEvents.onFirstFrameRendered();
    }

    @Override // org.wrtca.api.VideoSink
    public void onFrame(VideoFrame videoFrame) {
        this.eglRenderer.onFrame(videoFrame);
    }

    public void onFrameResolutionChanged(final int i2, int i3, int i4) {
        RendererCommon.RendererEvents rendererEvents = this.rendererEvents;
        if (rendererEvents != null) {
            rendererEvents.onFrameResolutionChanged(i2, i3, i4);
        }
        final int i5 = (i4 == 0 || i4 == 180) ? i2 : i3;
        if (i4 == 0 || i4 == 180) {
            i2 = i3;
        }
        postOrRun(new Runnable() { // from class: org.wrtca.api.p
            @Override // java.lang.Runnable
            public final void run() {
                this.f28126c.lambda$onFrameResolutionChanged$0(i5, i2);
            }
        });
    }

    @Override // android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        ThreadUtils.checkIsOnMainThread();
        if (this.mScaleType == 2) {
            this.eglRenderer.setLayoutAspectRatio(-100.0f);
        } else {
            this.eglRenderer.setLayoutAspectRatio((i4 - i2) / (i5 - i3));
        }
        updateSurfaceSize();
    }

    @Override // android.view.SurfaceView, android.view.View
    public void onMeasure(int i2, int i3) {
        ThreadUtils.checkIsOnMainThread();
        Point pointMeasure = this.videoLayoutMeasure.measure(i2, i3, this.rotatedFrameWidth, this.rotatedFrameHeight);
        setMeasuredDimension(pointMeasure.x, pointMeasure.y);
        c.h.a(TAG, "SurfaceViewRendereronMeasure(). New size: " + pointMeasure.x + "x" + pointMeasure.y);
    }

    public void pauseVideo() {
        this.eglRenderer.pauseVideo();
    }

    public void release() {
        c.h.a(TAG, "release surfaceviewrender");
        this.rendererEvents = null;
        this.eglRenderer.release();
    }

    public void removeFrameListener(EglRenderer.FrameListener frameListener) {
        this.eglRenderer.removeFrameListener(frameListener);
    }

    @Override // org.wrtca.api.VideoRenderer.Callbacks
    public void renderFrame(VideoRenderer.I420Frame i420Frame) {
        this.eglRenderer.renderFrame(i420Frame);
    }

    public void setEnableHardwareScaler(boolean z2) {
        ThreadUtils.checkIsOnMainThread();
        this.enableFixedSize = z2;
        updateSurfaceSize();
    }

    public void setFpsReduction(float f2) {
        this.eglRenderer.setFpsReduction(f2);
    }

    public void setFrameRendered(FirstFrameRendered firstFrameRendered) {
        this.mFrameRendered = firstFrameRendered;
    }

    public void setMirror(boolean z2) {
        this.eglRenderer.setMirror(z2);
    }

    public void setMirrorOnlyRemote(boolean z2) {
        this.eglRenderer.setMirrorOnlyRemote(z2);
    }

    public void setPeerConnectionCallBack(FirstFrameRendered firstFrameRendered) {
        this.mPeerConnectionCallBack = firstFrameRendered;
    }

    public void setScalingType(RendererCommon.ScalingType scalingType) {
        ThreadUtils.checkIsOnMainThread();
        this.videoLayoutMeasure.setScalingType(scalingType);
        requestLayout();
    }

    public void setStreamInfo(StreamInfo streamInfo) {
        this.mStreamInfo = streamInfo;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        ThreadUtils.checkIsOnMainThread();
        this.surfaceHeight = 0;
        this.surfaceWidth = 0;
        updateSurfaceSize();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }

    public void addFrameListener(EglRenderer.FrameListener frameListener, float f2) {
        this.eglRenderer.addFrameListener(frameListener, f2);
    }

    public void init(EglBase.Context context, RendererCommon.RendererEvents rendererEvents, int[] iArr, RendererCommon.GlDrawer glDrawer, DataReceiver dataReceiver) {
        ThreadUtils.checkIsOnMainThread();
        this.rendererEvents = rendererEvents;
        c.h.a(TAG, "surfaceViewRenderer renderEvents = " + rendererEvents + "this: " + this);
        this.rotatedFrameWidth = 0;
        this.rotatedFrameHeight = 0;
        this.eglRenderer.init(context, this, iArr, glDrawer, dataReceiver);
    }

    public void setScalingType(RendererCommon.ScalingType scalingType, RendererCommon.ScalingType scalingType2) {
        ThreadUtils.checkIsOnMainThread();
        this.videoLayoutMeasure.setScalingType(scalingType, scalingType2);
        c.h.a(TAG, "surfaceview render setScaleType: " + scalingType2);
        requestLayout();
    }

    public SurfaceViewRenderer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.videoLayoutMeasure = new RendererCommon.VideoLayoutMeasure();
        this.mScaleType = -1;
        String resourceName = getResourceName();
        this.resourceName = resourceName;
        SurfaceEglRenderer surfaceEglRenderer = new SurfaceEglRenderer(resourceName);
        this.eglRenderer = surfaceEglRenderer;
        getHolder().addCallback(this);
        getHolder().addCallback(surfaceEglRenderer);
    }
}
