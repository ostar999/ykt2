package io.agora.rtc.gl;

import android.graphics.Matrix;
import io.agora.rtc.gl.VideoFrame;
import io.agora.rtc.mediaio.SurfaceTextureHelper;

/* loaded from: classes8.dex */
public class TextureBufferImpl implements VideoFrame.TextureBuffer {
    private final int height;
    private final int id;
    private final Runnable releaseCallback;
    private final SurfaceTextureHelper surfaceTextureHelper;
    private final Matrix transformMatrix;
    private final VideoFrame.TextureBuffer.Type type;
    private final int width;
    private final Object refCountLock = new Object();
    private int refCount = 1;

    public TextureBufferImpl(int width, int height, VideoFrame.TextureBuffer.Type type, int id, Matrix transformMatrix, SurfaceTextureHelper surfaceTextureHelper, Runnable releaseCallback) {
        this.width = width;
        this.height = height;
        this.type = type;
        this.id = id;
        this.transformMatrix = transformMatrix;
        this.surfaceTextureHelper = surfaceTextureHelper;
        this.releaseCallback = releaseCallback;
    }

    @Override // io.agora.rtc.gl.VideoFrame.Buffer
    public VideoFrame.Buffer cropAndScale(int cropX, int cropY, int cropWidth, int cropHeight, int scaleWidth, int scaleHeight) {
        retain();
        Matrix matrix = new Matrix(this.transformMatrix);
        matrix.postScale(cropWidth / this.width, cropHeight / this.height);
        matrix.postTranslate(cropX / this.width, cropY / this.height);
        return new TextureBufferImpl(scaleWidth, scaleHeight, this.type, this.id, matrix, this.surfaceTextureHelper, new Runnable() { // from class: io.agora.rtc.gl.TextureBufferImpl.1
            @Override // java.lang.Runnable
            public void run() {
                TextureBufferImpl.this.release();
            }
        });
    }

    @Override // io.agora.rtc.gl.VideoFrame.Buffer
    public int getHeight() {
        return this.height;
    }

    @Override // io.agora.rtc.gl.VideoFrame.TextureBuffer
    public int getTextureId() {
        return this.id;
    }

    @Override // io.agora.rtc.gl.VideoFrame.TextureBuffer
    public Matrix getTransformMatrix() {
        return this.transformMatrix;
    }

    @Override // io.agora.rtc.gl.VideoFrame.TextureBuffer
    public VideoFrame.TextureBuffer.Type getType() {
        return this.type;
    }

    @Override // io.agora.rtc.gl.VideoFrame.Buffer
    public int getWidth() {
        return this.width;
    }

    @Override // io.agora.rtc.gl.VideoFrame.Buffer
    public void release() {
        Runnable runnable;
        synchronized (this.refCountLock) {
            int i2 = this.refCount - 1;
            this.refCount = i2;
            if (i2 == 0 && (runnable = this.releaseCallback) != null) {
                runnable.run();
            }
        }
    }

    @Override // io.agora.rtc.gl.VideoFrame.Buffer
    public void retain() {
        synchronized (this.refCountLock) {
            this.refCount++;
        }
    }

    @Override // io.agora.rtc.gl.VideoFrame.Buffer
    public VideoFrame.I420Buffer toI420() {
        return this.surfaceTextureHelper.textureToYuv(this);
    }
}
