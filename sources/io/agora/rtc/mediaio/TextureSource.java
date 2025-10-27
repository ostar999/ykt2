package io.agora.rtc.mediaio;

import android.graphics.SurfaceTexture;
import io.agora.rtc.gl.EglBase;
import io.agora.rtc.mediaio.SurfaceTextureHelper;
import java.lang.ref.WeakReference;

/* loaded from: classes8.dex */
public abstract class TextureSource implements IVideoSource, SurfaceTextureHelper.OnTextureFrameAvailableListener {
    protected WeakReference<IVideoFrameConsumer> mConsumer;
    protected int mHeight;
    protected int mPixelFormat = 11;
    protected SurfaceTextureHelper mSurfaceTextureHelper;
    protected int mWidth;

    public TextureSource(EglBase.Context sharedContext, int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
        SurfaceTextureHelper surfaceTextureHelperCreate = SurfaceTextureHelper.create("TexCamThread", sharedContext);
        this.mSurfaceTextureHelper = surfaceTextureHelperCreate;
        surfaceTextureHelperCreate.getSurfaceTexture().setDefaultBufferSize(width, height);
        this.mSurfaceTextureHelper.startListening(this);
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public int getBufferType() {
        return 3;
    }

    public EglBase.Context getEglContext() {
        return this.mSurfaceTextureHelper.getEglContext();
    }

    public SurfaceTexture getSurfaceTexture() {
        return this.mSurfaceTextureHelper.getSurfaceTexture();
    }

    public abstract void onCapturerClosed();

    public abstract boolean onCapturerOpened();

    public abstract boolean onCapturerStarted();

    public abstract void onCapturerStopped();

    @Override // io.agora.rtc.mediaio.IVideoSource
    public void onDispose() {
        this.mConsumer = null;
        onCapturerClosed();
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public boolean onInitialize(IVideoFrameConsumer observer) {
        this.mConsumer = new WeakReference<>(observer);
        return onCapturerOpened();
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public boolean onStart() {
        return onCapturerStarted();
    }

    @Override // io.agora.rtc.mediaio.IVideoSource
    public void onStop() {
        onCapturerStopped();
    }

    public void onTextureFrameAvailable(int oesTextureId, float[] transformMatrix, long timestampNs) {
        this.mSurfaceTextureHelper.returnTextureFrame();
    }

    public void release() {
        this.mSurfaceTextureHelper.stopListening();
        this.mSurfaceTextureHelper.dispose();
        this.mSurfaceTextureHelper = null;
    }
}
