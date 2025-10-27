package org.wrtca.video;

import java.nio.ByteBuffer;
import org.wrtca.api.JavaI420Buffer;
import org.wrtca.api.VideoFrame;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class NV21Buffer implements VideoFrame.Buffer {
    public static final String TAG = "NV21Buffer";
    private final byte[] data;
    private final int height;
    private boolean isNeedMirror;
    private final Runnable releaseCallback;
    private final int width;
    private final Object refCountLock = new Object();
    private int refCount = 1;

    public NV21Buffer(byte[] bArr, int i2, int i3, Runnable runnable) {
        this.data = bArr;
        this.width = i2;
        this.height = i3;
        this.releaseCallback = runnable;
    }

    private static native void nativeCropAndScale(int i2, int i3, int i4, int i5, int i6, int i7, byte[] bArr, int i8, int i9, ByteBuffer byteBuffer, int i10, ByteBuffer byteBuffer2, int i11, ByteBuffer byteBuffer3, int i12);

    private static native void nativeMirror(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, ByteBuffer byteBuffer4, int i5, ByteBuffer byteBuffer5, int i6, ByteBuffer byteBuffer6, int i7, int i8, int i9);

    @Override // org.wrtca.api.VideoFrame.Buffer
    public VideoFrame.Buffer cropAndScale(int i2, int i3, int i4, int i5, int i6, int i7) {
        JavaI420Buffer javaI420BufferAllocate = JavaI420Buffer.allocate(i6, i7);
        nativeCropAndScale(i2, i3, i4, i5, i6, i7, this.data, this.width, this.height, javaI420BufferAllocate.getDataY(), javaI420BufferAllocate.getStrideY(), javaI420BufferAllocate.getDataU(), javaI420BufferAllocate.getStrideU(), javaI420BufferAllocate.getDataV(), javaI420BufferAllocate.getStrideV());
        if (!this.isNeedMirror) {
            return javaI420BufferAllocate;
        }
        JavaI420Buffer javaI420BufferAllocate2 = JavaI420Buffer.allocate(i6, i7);
        nativeMirror(javaI420BufferAllocate.getDataY(), javaI420BufferAllocate.getStrideY(), javaI420BufferAllocate.getDataU(), javaI420BufferAllocate.getStrideU(), javaI420BufferAllocate.getDataV(), javaI420BufferAllocate.getStrideV(), javaI420BufferAllocate2.getDataY(), javaI420BufferAllocate2.getStrideY(), javaI420BufferAllocate2.getDataU(), javaI420BufferAllocate2.getStrideU(), javaI420BufferAllocate2.getDataV(), javaI420BufferAllocate2.getStrideV(), i6, i7);
        javaI420BufferAllocate.release();
        return javaI420BufferAllocate2;
    }

    public byte[] getData() {
        return this.data;
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

    public void setNeedMirror(boolean z2) {
        this.isNeedMirror = z2;
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public VideoFrame.I420Buffer toI420() {
        int i2 = this.width;
        int i3 = this.height;
        return (VideoFrame.I420Buffer) cropAndScale(0, 0, i2, i3, i2, i3);
    }
}
