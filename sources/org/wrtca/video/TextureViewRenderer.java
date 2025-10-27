package org.wrtca.video;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.os.Looper;
import android.view.TextureView;
import android.view.View;
import c.h;
import cn.hutool.core.text.StrPool;
import core.data.StreamInfo;
import core.interfaces.DataReceiver;
import core.interfaces.FirstFrameRendered;
import java.util.Objects;
import org.wrtca.api.EglBase;
import org.wrtca.api.EglRenderer;
import org.wrtca.api.GlRectDrawer;
import org.wrtca.api.RendererCommon;
import org.wrtca.api.VideoFrame;
import org.wrtca.api.VideoRenderer;
import org.wrtca.api.VideoSink;
import org.wrtca.log.Logging;
import org.wrtca.record.MediaRecorderNative;
import org.wrtca.record.model.CameraParamObserver;
import org.wrtca.util.ThreadUtils;

/* loaded from: classes9.dex */
public class TextureViewRenderer implements TextureView.SurfaceTextureListener, VideoRenderer.Callbacks, VideoSink, RendererCommon.RendererEvents {
    private static final String TAG = "TextureViewRenderer";
    private final TextureEglRenderer eglRenderer;
    public boolean enableFixedSize;
    public FirstFrameRendered mFrameRenderedCallBack;
    private CameraParamObserver mParamObserver;
    public FirstFrameRendered mPeerConnectionCallBack;
    public StreamInfo mStreamInfo;
    public TextureView mTextureView;
    public RendererCommon.RendererEvents rendererEvents;
    public final String resourceName;
    public int rotatedFrameHeight;
    public int rotatedFrameWidth;
    public int surfaceHeight;
    public int surfaceWidth;
    public final RendererCommon.VideoLayoutMeasure videoLayoutMeasure = new RendererCommon.VideoLayoutMeasure();
    public int mScaleType = 1;

    public TextureViewRenderer(TextureView textureView) {
        this.mTextureView = textureView;
        String resourceName = getResourceName();
        this.resourceName = resourceName;
        TextureEglRenderer textureEglRenderer = new TextureEglRenderer(resourceName);
        this.eglRenderer = textureEglRenderer;
        h.a(TAG, "TextureViewRenderer constructor create eglRender" + textureEglRenderer);
        this.mTextureView.setSurfaceTextureListener(textureEglRenderer);
        this.mTextureView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: org.wrtca.video.TextureViewRenderer.1
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(View view, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                h.a(TextureViewRenderer.TAG, "new width  " + (i4 - i2) + " old width: " + (i8 - i6));
                h.a(TextureViewRenderer.TAG, "new height  " + (i5 - i3) + " old height: " + (i9 - i7));
                TextureViewRenderer.this.initTextureViewMatrix();
            }
        });
        this.mParamObserver = MediaRecorderNative.cameraParamObserver;
    }

    private String getResourceName() {
        try {
            return this.mTextureView.getResources().getResourceEntryName(this.mTextureView.getId()) + StrPool.UNDERLINE + Integer.toHexString(this.mTextureView.hashCode());
        } catch (Resources.NotFoundException unused) {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTextureViewMatrix() {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        h.a(TAG, "TextureViewRendererscaleType: " + this.mScaleType);
        if (this.mScaleType == 2) {
            this.mTextureView.setTransform(new Matrix());
            return;
        }
        if (this.rotatedFrameWidth == 0 || this.rotatedFrameHeight == 0) {
            h.a(TAG, "initTextureViewMatrix: rotatedFrameWidth|rotatedFrameHeight = 0 ");
            return;
        }
        int width = this.mTextureView.getWidth();
        int height = this.mTextureView.getHeight();
        int i14 = this.mScaleType;
        if (i14 == 1) {
            RectF rectF = new RectF(0.0f, 0.0f, width, height);
            double d3 = this.rotatedFrameWidth / this.rotatedFrameHeight;
            double d4 = width;
            double d5 = height;
            double d6 = d4 / d5;
            h.a(TAG, "TextureViewRenderer : frameWidth:" + this.rotatedFrameWidth + " frameHeight: " + this.rotatedFrameHeight);
            h.a(TAG, "TextureViewRenderer : viewWidth:" + width + " viewHeight: " + height);
            if (d3 > 1.0d) {
                if (d6 > d3) {
                    i10 = (int) ((d4 / d3) + 0.5d);
                    i11 = (-(i10 - height)) / 2;
                    i12 = i11;
                    height = i10;
                    i13 = 0;
                } else {
                    i8 = (int) ((d5 * d3) + 0.5d);
                    i9 = (-(i8 - width)) / 2;
                    i12 = 0;
                    int i15 = i8;
                    i13 = i9;
                    width = i15;
                }
            } else if (d6 > d3) {
                i10 = (int) ((d4 / d3) + 0.5d);
                i11 = (-(i10 - height)) / 2;
                i12 = i11;
                height = i10;
                i13 = 0;
            } else {
                i8 = (int) ((d5 * d3) + 0.5d);
                i9 = (-(i8 - width)) / 2;
                i12 = 0;
                int i152 = i8;
                i13 = i9;
                width = i152;
            }
            h.a(TAG, "TextureViewRenderer : displayWidth:" + width + " displayHeight: " + height);
            h.a(TAG, "TextureViewRenderer : left:" + i13 + " top: " + i12);
            RectF rectF2 = new RectF((float) i13, (float) i12, (float) (width + i13), (float) (height + i12));
            Matrix matrix = new Matrix();
            matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.FILL);
            this.mTextureView.setTransform(matrix);
            return;
        }
        if (i14 == 0) {
            RectF rectF3 = new RectF(0.0f, 0.0f, width, height);
            double d7 = this.rotatedFrameWidth / this.rotatedFrameHeight;
            double d8 = width;
            double d9 = height;
            double d10 = d8 / d9;
            h.a(TAG, "TextureViewRenderer : frameWidth:" + this.rotatedFrameWidth + " frameHeight: " + this.rotatedFrameHeight);
            h.a(TAG, "TextureViewRenderer : viewWidth:" + width + " viewHeight: " + height);
            if (d7 > 1.0d) {
                if (d10 > d7) {
                    i4 = (int) ((d9 * d7) + 0.5d);
                    i5 = (width - i4) / 2;
                    i6 = 0;
                    int i16 = i4;
                    i7 = i5;
                    width = i16;
                } else {
                    i2 = (int) ((d8 / d7) + 0.5d);
                    i3 = (height - i2) / 2;
                    i6 = i3;
                    height = i2;
                    i7 = 0;
                }
            } else if (d10 > d7) {
                i4 = (int) ((d9 * d7) + 0.5d);
                i5 = (width - i4) / 2;
                i6 = 0;
                int i162 = i4;
                i7 = i5;
                width = i162;
            } else {
                i2 = (int) ((d8 / d7) + 0.5d);
                i3 = (height - i2) / 2;
                i6 = i3;
                height = i2;
                i7 = 0;
            }
            h.a(TAG, "TextureViewRenderer : displayWidth:" + width + " displayHeight: " + height);
            h.a(TAG, "TextureViewRenderer : left:" + i7 + " top: " + i6);
            RectF rectF4 = new RectF((float) i7, (float) i6, (float) (width + i7), (float) (height + i6));
            Matrix matrix2 = new Matrix();
            matrix2.setRectToRect(rectF3, rectF4, Matrix.ScaleToFit.FILL);
            this.mTextureView.setTransform(matrix2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onFrameResolutionChanged$1(int i2, int i3) {
        this.rotatedFrameWidth = i2;
        this.rotatedFrameHeight = i3;
        initTextureViewMatrix();
        this.mTextureView.requestLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setScaleType$0() {
        TextureView textureView = this.mTextureView;
        if (textureView != null && this.rotatedFrameWidth != 0 && this.rotatedFrameHeight != 0 && textureView.getWidth() > 0 && this.mTextureView.getHeight() > 0) {
            initTextureViewMatrix();
            this.mTextureView.requestLayout();
            return;
        }
        h.a(TAG, "TextureViewRendererinitTextureViewMatrix failed for " + this.mTextureView + "viewWidth: " + this.mTextureView.getWidth() + "viewHeight: " + this.mTextureView.getHeight() + "frameWidth: " + this.rotatedFrameWidth + "frameHeight: " + this.rotatedFrameHeight);
    }

    private void logD(String str) {
        Logging.d(TAG, this.resourceName + ": " + str);
    }

    private void postOrRun(Runnable runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            runnable.run();
        } else {
            this.mTextureView.post(runnable);
        }
    }

    private void updateSurfaceSize() {
        ThreadUtils.checkIsOnMainThread();
        if (!this.enableFixedSize || this.rotatedFrameWidth == 0 || this.rotatedFrameHeight == 0 || this.mTextureView.getWidth() == 0 || this.mTextureView.getHeight() == 0) {
            this.surfaceHeight = 0;
            this.surfaceWidth = 0;
            return;
        }
        float width = this.mTextureView.getWidth() / this.mTextureView.getHeight();
        int i2 = this.rotatedFrameWidth;
        float f2 = i2;
        int i3 = this.rotatedFrameHeight;
        float f3 = i3;
        if (f2 / f3 > width) {
            i2 = (int) (f3 * width);
        } else {
            i3 = (int) (f2 / width);
        }
        int iMin = Math.min(this.mTextureView.getWidth(), i2);
        int iMin2 = Math.min(this.mTextureView.getHeight(), i3);
        logD("updateSurfaceSize. Layout size: " + this.mTextureView.getWidth() + "x" + this.mTextureView.getHeight() + ", frame size: " + this.rotatedFrameWidth + "x" + this.rotatedFrameHeight + ", requested surface size: " + iMin + "x" + iMin2 + ", old surface size: " + this.surfaceWidth + "x" + this.surfaceHeight);
        if (iMin == this.surfaceWidth && iMin2 == this.surfaceHeight) {
            return;
        }
        this.surfaceWidth = iMin;
        this.surfaceHeight = iMin2;
    }

    public void addFrameListener(EglRenderer.FrameListener frameListener, float f2, RendererCommon.GlDrawer glDrawer) {
        this.eglRenderer.addFrameListener(frameListener, f2, glDrawer);
    }

    public void clearImage() {
        TextureView textureView = this.mTextureView;
        if (textureView != null) {
            textureView.post(new Runnable() { // from class: org.wrtca.video.TextureViewRenderer.3
                @Override // java.lang.Runnable
                public void run() {
                    TextureViewRenderer.this.eglRenderer.clearImage();
                }
            });
        }
    }

    public void createEglSurface(SurfaceTexture surfaceTexture) {
        this.eglRenderer.createEglSurface(surfaceTexture);
    }

    public void disableFpsReduction() {
        this.eglRenderer.disableFpsReduction();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mTextureView, ((TextureViewRenderer) obj).mTextureView);
    }

    public StreamInfo getStreamInfo() {
        return this.mStreamInfo;
    }

    public TextureView getTextureView() {
        return this.mTextureView;
    }

    public int hashCode() {
        return Objects.hash(this.mTextureView);
    }

    public void init(EglBase.Context context, RendererCommon.RendererEvents rendererEvents, DataReceiver dataReceiver) {
        init(context, rendererEvents, EglBase.CONFIG_PLAIN, new GlRectDrawer(), dataReceiver);
    }

    public void initEgl(final boolean z2, final boolean z3) {
        h.a(TAG, "TextureViewRenderer : mTextureView : " + this.mTextureView);
        TextureView textureView = this.mTextureView;
        if (textureView != null) {
            textureView.post(new Runnable() { // from class: org.wrtca.video.TextureViewRenderer.2
                @Override // java.lang.Runnable
                public void run() {
                    h.a(TextureViewRenderer.TAG, "start to init egl context； " + z2 + "_surfaceContext: " + z3);
                    if (!z2) {
                        h.a(TextureViewRenderer.TAG, "TextureViewRenderer : initEglBase:");
                        TextureViewRenderer.this.init(j.d.d().o(), null, null);
                    }
                    if (z3) {
                        return;
                    }
                    h.a(TextureViewRenderer.TAG, "TextureViewRenderer : initEglSurface:" + TextureViewRenderer.this.mTextureView.getSurfaceTexture());
                    if (TextureViewRenderer.this.mTextureView.getSurfaceTexture() == null) {
                        h.a(TextureViewRenderer.TAG, "TextureViewRenderer initEglSurface quit for texture is null");
                    } else {
                        TextureViewRenderer textureViewRenderer = TextureViewRenderer.this;
                        textureViewRenderer.createEglSurface(textureViewRenderer.mTextureView.getSurfaceTexture());
                    }
                }
            });
        } else {
            h.a(TAG, "TextureViewRenderer : mTextureView = null:");
        }
    }

    @Override // org.wrtca.api.RendererCommon.RendererEvents
    public void onFirstFrameRendered() {
        if (this.mFrameRenderedCallBack != null) {
            h.a(TAG, "TextureViewRendereronFirstFrameRendered: " + this.mStreamInfo + "view: " + this.mTextureView);
            this.mFrameRenderedCallBack.onFirstFrameRender(this.mStreamInfo, this.mTextureView);
        }
        FirstFrameRendered firstFrameRendered = this.mPeerConnectionCallBack;
        if (firstFrameRendered != null) {
            firstFrameRendered.onFirstFrameRender(this.mStreamInfo, this.mTextureView);
        }
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

    @Override // org.wrtca.api.RendererCommon.RendererEvents
    public void onFrameResolutionChanged(final int i2, int i3, int i4) {
        h.a(TAG, "onFrameResolutionChanged :" + this.mTextureView + " videoWidth:" + i2 + " videoHeight: " + i3 + " rotation: " + i4);
        final int i5 = (i4 == 0 || i4 == 180) ? i2 : i3;
        if (i4 == 0 || i4 == 180) {
            i2 = i3;
        }
        postOrRun(new Runnable() { // from class: org.wrtca.video.c
            @Override // java.lang.Runnable
            public final void run() {
                this.f28142c.lambda$onFrameResolutionChanged$1(i5, i2);
            }
        });
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
        h.a(TAG, "TextureViewRenderer onSurfaceTextureAvailable surface: " + surfaceTexture + " width: " + i2 + "height: " + i3);
        TextureEglRenderer textureEglRenderer = this.eglRenderer;
        if (textureEglRenderer != null) {
            textureEglRenderer.onSurfaceTextureAvailable(surfaceTexture, i2, i3);
            h.a(TAG, "TextureViewRenderer attach eglRender: " + this.eglRenderer);
            this.eglRenderer.attachRenderEvents(this);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        h.a(TAG, " TextureViewRenderer onSurfaceTextureDestroyed surface: " + surfaceTexture);
        this.rotatedFrameHeight = 0;
        this.rotatedFrameWidth = 0;
        TextureEglRenderer textureEglRenderer = this.eglRenderer;
        if (textureEglRenderer != null) {
            return textureEglRenderer.onSurfaceTextureDestroyed(surfaceTexture);
        }
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
        h.a(TAG, " TextureViewRenderer onSurfaceTextureSizeChanged surface: " + surfaceTexture);
        TextureEglRenderer textureEglRenderer = this.eglRenderer;
        if (textureEglRenderer != null) {
            textureEglRenderer.onSurfaceTextureSizeChanged(surfaceTexture, i2, i3);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        TextureEglRenderer textureEglRenderer = this.eglRenderer;
        if (textureEglRenderer != null) {
            textureEglRenderer.onSurfaceTextureUpdated(surfaceTexture);
        }
    }

    public void pauseVideo() {
        this.eglRenderer.pauseVideo();
    }

    public void release() {
        h.a(TAG, "TextureViewRenderer release: +mTextureView: " + this.mTextureView);
        if (this.mTextureView != null) {
            this.rendererEvents = null;
            this.eglRenderer.release();
        }
    }

    public void removeFrameListener(EglRenderer.FrameListener frameListener) {
        this.eglRenderer.removeFrameListener(frameListener);
    }

    @Override // org.wrtca.api.VideoRenderer.Callbacks
    public void renderFrame(VideoRenderer.I420Frame i420Frame) {
        this.eglRenderer.renderFrame(i420Frame);
    }

    public void requestEglState(EglRenderer.RequestEglInit requestEglInit) {
        this.eglRenderer.isInitEgl(requestEglInit);
    }

    public void setEnableHardwareScaler(boolean z2) {
        ThreadUtils.checkIsOnMainThread();
        this.enableFixedSize = z2;
    }

    public void setFpsReduction(float f2) {
        this.eglRenderer.setFpsReduction(f2);
    }

    public void setFrameRenderedCallBack(FirstFrameRendered firstFrameRendered) {
        this.mFrameRenderedCallBack = firstFrameRendered;
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

    public void setScaleType(int i2) {
        this.mScaleType = i2;
        postOrRun(new Runnable() { // from class: org.wrtca.video.d
            @Override // java.lang.Runnable
            public final void run() {
                this.f28145c.lambda$setScaleType$0();
            }
        });
        h.a(TAG, "TextureViewRenderersetScaleType: " + this.mScaleType);
    }

    public void setScalingType(RendererCommon.ScalingType scalingType) {
        ThreadUtils.checkIsOnMainThread();
        this.videoLayoutMeasure.setScalingType(scalingType);
    }

    public void setStreamInfo(StreamInfo streamInfo) {
        this.mStreamInfo = streamInfo;
        h.a(TAG, " tvrender" + this + " setStreamInfo " + streamInfo);
    }

    public String toString() {
        return "TextureViewRenderer{this: " + hashCode() + "mTextureView=" + this.mTextureView + "eglRender=" + this.eglRenderer + '}';
    }

    public void addFrameListener(EglRenderer.FrameListener frameListener, float f2) {
        this.eglRenderer.addFrameListener(frameListener, f2);
    }

    public void init(EglBase.Context context, RendererCommon.RendererEvents rendererEvents, int[] iArr, RendererCommon.GlDrawer glDrawer, DataReceiver dataReceiver) {
        ThreadUtils.checkIsOnMainThread();
        this.rendererEvents = rendererEvents;
        this.rotatedFrameWidth = 0;
        this.rotatedFrameHeight = 0;
        this.eglRenderer.init(context, this, iArr, glDrawer, dataReceiver);
    }

    public void setScalingType(RendererCommon.ScalingType scalingType, RendererCommon.ScalingType scalingType2) {
        ThreadUtils.checkIsOnMainThread();
        this.videoLayoutMeasure.setScalingType(scalingType, scalingType2);
    }
}
