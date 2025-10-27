package org.wrtca.video;

import android.graphics.Matrix;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.api.VideoFrame;

/* loaded from: classes9.dex */
public class TextureBufferImpl implements VideoFrame.TextureBuffer {
    private static final String TAG = "TextureBufferImpl";
    private final int height;
    private final int id;
    private final Runnable releaseCallback;
    private final SurfaceTextureHelper surfaceTextureHelper;
    private final Matrix transformMatrix;
    private final VideoFrame.TextureBuffer.Type type;
    private final int width;
    private final Object refCountLock = new Object();
    private int refCount = 1;

    public TextureBufferImpl(int i2, int i3, VideoFrame.TextureBuffer.Type type, int i4, Matrix matrix, SurfaceTextureHelper surfaceTextureHelper, Runnable runnable) {
        this.width = i2;
        this.height = i3;
        this.type = type;
        this.id = i4;
        this.transformMatrix = matrix;
        this.surfaceTextureHelper = surfaceTextureHelper;
        this.releaseCallback = runnable;
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public VideoFrame.Buffer cropAndScale(int i2, int i3, int i4, int i5, int i6, int i7) {
        retain();
        Matrix matrix = new Matrix(this.transformMatrix);
        matrix.postScale(i4 / this.width, i5 / this.height);
        matrix.postTranslate(i2 / this.width, i3 / this.height);
        return new TextureBufferImpl(i6, i7, this.type, this.id, matrix, this.surfaceTextureHelper, new Runnable() { // from class: org.wrtca.video.TextureBufferImpl.1
            @Override // java.lang.Runnable
            public void run() {
                TextureBufferImpl.this.release();
            }
        });
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public int getHeight() {
        return this.height;
    }

    @Override // org.wrtca.api.VideoFrame.TextureBuffer
    public int getTextureId() {
        return this.id;
    }

    @Override // org.wrtca.api.VideoFrame.TextureBuffer
    public Matrix getTransformMatrix() {
        return this.transformMatrix;
    }

    @Override // org.wrtca.api.VideoFrame.TextureBuffer
    public VideoFrame.TextureBuffer.Type getType() {
        return this.type;
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public int getWidth() {
        return this.width;
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
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

    @Override // org.wrtca.api.VideoFrame.Buffer
    public void retain() {
        synchronized (this.refCountLock) {
            this.refCount++;
        }
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public VideoFrame.I420Buffer toI420() {
        return this.surfaceTextureHelper.textureToYuv(this);
    }
}
