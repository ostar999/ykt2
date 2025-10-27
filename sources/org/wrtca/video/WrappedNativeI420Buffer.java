package org.wrtca.video;

import java.nio.ByteBuffer;
import org.wrtca.api.VideoFrame;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JniCommon;

/* loaded from: classes9.dex */
class WrappedNativeI420Buffer implements VideoFrame.I420Buffer {
    private static final String TAG = "WrappedNativeI420Buffer";
    private final ByteBuffer dataU;
    private final ByteBuffer dataV;
    private final ByteBuffer dataY;
    private final int height;
    private final long nativeBuffer;
    private final int strideU;
    private final int strideV;
    private final int strideY;
    private final int width;

    @CalledByNative
    public WrappedNativeI420Buffer(int i2, int i3, ByteBuffer byteBuffer, int i4, ByteBuffer byteBuffer2, int i5, ByteBuffer byteBuffer3, int i6, long j2) {
        this.width = i2;
        this.height = i3;
        this.dataY = byteBuffer;
        this.strideY = i4;
        this.dataU = byteBuffer2;
        this.strideU = i5;
        this.dataV = byteBuffer3;
        this.strideV = i6;
        this.nativeBuffer = j2;
        retain();
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public VideoFrame.Buffer cropAndScale(int i2, int i3, int i4, int i5, int i6, int i7) {
        return VideoFrame.cropAndScaleI420(this, i2, i3, i4, i5, i6, i7);
    }

    @Override // org.wrtca.api.VideoFrame.I420Buffer
    public ByteBuffer getDataU() {
        return this.dataU.slice();
    }

    @Override // org.wrtca.api.VideoFrame.I420Buffer
    public ByteBuffer getDataV() {
        return this.dataV.slice();
    }

    @Override // org.wrtca.api.VideoFrame.I420Buffer
    public ByteBuffer getDataY() {
        return this.dataY.slice();
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public int getHeight() {
        return this.height;
    }

    @Override // org.wrtca.api.VideoFrame.I420Buffer
    public int getStrideU() {
        return this.strideU;
    }

    @Override // org.wrtca.api.VideoFrame.I420Buffer
    public int getStrideV() {
        return this.strideV;
    }

    @Override // org.wrtca.api.VideoFrame.I420Buffer
    public int getStrideY() {
        return this.strideY;
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public int getWidth() {
        return this.width;
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public void release() {
        JniCommon.nativeReleaseRef(this.nativeBuffer);
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public void retain() {
        JniCommon.nativeAddRef(this.nativeBuffer);
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public VideoFrame.I420Buffer toI420() {
        retain();
        return this;
    }
}
