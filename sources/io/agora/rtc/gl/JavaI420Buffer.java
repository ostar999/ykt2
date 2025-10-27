package io.agora.rtc.gl;

import io.agora.rtc.gl.VideoFrame;
import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public class JavaI420Buffer implements VideoFrame.I420Buffer {
    private final ByteBuffer dataU;
    private final ByteBuffer dataV;
    private final ByteBuffer dataY;
    private final int height;
    private final Runnable releaseCallback;
    private final int strideU;
    private final int strideV;
    private final int strideY;
    private final int width;
    private final Object refCountLock = new Object();
    private int refCount = 1;

    private JavaI420Buffer(int width, int height, ByteBuffer dataY, int strideY, ByteBuffer dataU, int strideU, ByteBuffer dataV, int strideV, Runnable releaseCallback) {
        this.width = width;
        this.height = height;
        this.dataY = dataY;
        this.dataU = dataU;
        this.dataV = dataV;
        this.strideY = strideY;
        this.strideU = strideU;
        this.strideV = strideV;
        this.releaseCallback = releaseCallback;
    }

    public static JavaI420Buffer allocate(int width, int height) {
        int i2 = (height + 1) / 2;
        int i3 = (width + 1) / 2;
        int i4 = width * height;
        int i5 = i4 + 0;
        int i6 = i3 * i2;
        int i7 = i5 + i6;
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i4 + (i3 * 2 * i2));
        byteBufferAllocateDirect.position(0);
        byteBufferAllocateDirect.limit(i5);
        ByteBuffer byteBufferSlice = byteBufferAllocateDirect.slice();
        byteBufferAllocateDirect.position(i5);
        byteBufferAllocateDirect.limit(i7);
        ByteBuffer byteBufferSlice2 = byteBufferAllocateDirect.slice();
        byteBufferAllocateDirect.position(i7);
        byteBufferAllocateDirect.limit(i7 + i6);
        return new JavaI420Buffer(width, height, byteBufferSlice, width, byteBufferSlice2, i3, byteBufferAllocateDirect.slice(), i3, null);
    }

    public static JavaI420Buffer createYUV(byte[] data, int width, int height) {
        if (data == null || data.length == 0) {
            return null;
        }
        JavaI420Buffer javaI420BufferAllocate = allocate(width, height);
        ByteBuffer dataY = javaI420BufferAllocate.getDataY();
        ByteBuffer dataU = javaI420BufferAllocate.getDataU();
        ByteBuffer dataV = javaI420BufferAllocate.getDataV();
        int i2 = (height + 1) / 2;
        int strideY = height * javaI420BufferAllocate.getStrideY();
        int strideU = javaI420BufferAllocate.getStrideU() * i2;
        int strideV = i2 * javaI420BufferAllocate.getStrideV();
        dataY.put(data, 0, strideY);
        dataU.put(data, strideY, strideU);
        dataV.put(data, strideY + strideU, strideV);
        return javaI420BufferAllocate;
    }

    public static JavaI420Buffer wrap(int width, int height, ByteBuffer dataY, int strideY, ByteBuffer dataU, int strideU, ByteBuffer dataV, int strideV, Runnable releaseCallback) {
        if (dataY == null || dataU == null || dataV == null) {
            throw new IllegalArgumentException("Data buffers cannot be null.");
        }
        if (!dataY.isDirect() || !dataU.isDirect() || !dataV.isDirect()) {
            throw new IllegalArgumentException("Data buffers must be direct byte buffers.");
        }
        ByteBuffer byteBufferSlice = dataY.slice();
        ByteBuffer byteBufferSlice2 = dataU.slice();
        ByteBuffer byteBufferSlice3 = dataV.slice();
        int i2 = (height + 1) / 2;
        int i3 = strideY * height;
        int i4 = strideU * i2;
        int i5 = i2 * strideV;
        if (byteBufferSlice.capacity() < i3) {
            throw new IllegalArgumentException("Y-buffer must be at least " + i3 + " bytes.");
        }
        if (byteBufferSlice2.capacity() < i4) {
            throw new IllegalArgumentException("U-buffer must be at least " + i4 + " bytes.");
        }
        if (byteBufferSlice3.capacity() >= i5) {
            return new JavaI420Buffer(width, height, byteBufferSlice, strideY, byteBufferSlice2, strideU, byteBufferSlice3, strideV, releaseCallback);
        }
        throw new IllegalArgumentException("V-buffer must be at least " + i5 + " bytes.");
    }

    @Override // io.agora.rtc.gl.VideoFrame.Buffer
    public VideoFrame.Buffer cropAndScale(int cropX, int cropY, int cropWidth, int cropHeight, int scaleWidth, int scaleHeight) {
        return VideoFrame.cropAndScaleI420(this, cropX, cropY, cropWidth, cropHeight, scaleWidth, scaleHeight);
    }

    @Override // io.agora.rtc.gl.VideoFrame.I420Buffer
    public ByteBuffer getDataU() {
        return this.dataU.slice();
    }

    @Override // io.agora.rtc.gl.VideoFrame.I420Buffer
    public ByteBuffer getDataV() {
        return this.dataV.slice();
    }

    @Override // io.agora.rtc.gl.VideoFrame.I420Buffer
    public ByteBuffer getDataY() {
        return this.dataY.slice();
    }

    @Override // io.agora.rtc.gl.VideoFrame.Buffer
    public int getHeight() {
        return this.height;
    }

    @Override // io.agora.rtc.gl.VideoFrame.I420Buffer
    public int getStrideU() {
        return this.strideU;
    }

    @Override // io.agora.rtc.gl.VideoFrame.I420Buffer
    public int getStrideV() {
        return this.strideV;
    }

    @Override // io.agora.rtc.gl.VideoFrame.I420Buffer
    public int getStrideY() {
        return this.strideY;
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
        retain();
        return this;
    }
}
