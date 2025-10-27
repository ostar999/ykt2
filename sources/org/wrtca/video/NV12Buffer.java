package org.wrtca.video;

import java.nio.ByteBuffer;
import org.wrtca.api.JavaI420Buffer;
import org.wrtca.api.VideoFrame;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class NV12Buffer implements VideoFrame.Buffer {
    private final ByteBuffer buffer;
    private final int height;
    private final Runnable releaseCallback;
    private final int sliceHeight;
    private final int stride;
    private final int width;
    private final Object refCountLock = new Object();
    private int refCount = 1;

    public NV12Buffer(int i2, int i3, int i4, int i5, ByteBuffer byteBuffer, Runnable runnable) {
        this.width = i2;
        this.height = i3;
        this.stride = i4;
        this.sliceHeight = i5;
        this.buffer = byteBuffer;
        this.releaseCallback = runnable;
    }

    private static native void nativeCropAndScale(int i2, int i3, int i4, int i5, int i6, int i7, ByteBuffer byteBuffer, int i8, int i9, int i10, int i11, ByteBuffer byteBuffer2, int i12, ByteBuffer byteBuffer3, int i13, ByteBuffer byteBuffer4, int i14);

    @Override // org.wrtca.api.VideoFrame.Buffer
    public VideoFrame.Buffer cropAndScale(int i2, int i3, int i4, int i5, int i6, int i7) {
        JavaI420Buffer javaI420BufferAllocate = JavaI420Buffer.allocate(i6, i7);
        nativeCropAndScale(i2, i3, i4, i5, i6, i7, this.buffer, this.width, this.height, this.stride, this.sliceHeight, javaI420BufferAllocate.getDataY(), javaI420BufferAllocate.getStrideY(), javaI420BufferAllocate.getDataU(), javaI420BufferAllocate.getStrideU(), javaI420BufferAllocate.getDataV(), javaI420BufferAllocate.getStrideV());
        return javaI420BufferAllocate;
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public int getHeight() {
        return this.height;
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
        int i2 = this.width;
        int i3 = this.height;
        return (VideoFrame.I420Buffer) cropAndScale(0, 0, i2, i3, i2, i3);
    }
}
