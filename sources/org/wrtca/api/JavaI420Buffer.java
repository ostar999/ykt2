package org.wrtca.api;

import java.nio.ByteBuffer;
import org.wrtca.api.VideoFrame;
import org.wrtca.jni.JniCommon;

/* loaded from: classes9.dex */
public class JavaI420Buffer implements VideoFrame.I420Buffer {
    private static final String TAG = "JavaI420Buffer";
    private final ByteBuffer dataU;
    private final ByteBuffer dataV;
    private final ByteBuffer dataY;
    private final int height;
    private final Runnable releaseCallback;
    private boolean rtcExtend;
    private final int strideU;
    private final int strideV;
    private final int strideY;
    private final int width;
    private final Object refCountLock = new Object();
    private int refCount = 1;

    private JavaI420Buffer(int i2, int i3, ByteBuffer byteBuffer, int i4, ByteBuffer byteBuffer2, int i5, ByteBuffer byteBuffer3, int i6, Runnable runnable) {
        this.width = i2;
        this.height = i3;
        this.dataY = byteBuffer;
        this.dataU = byteBuffer2;
        this.dataV = byteBuffer3;
        this.strideY = i4;
        this.strideU = i5;
        this.strideV = i6;
        this.releaseCallback = runnable;
    }

    public static JavaI420Buffer allocate(ByteBuffer byteBuffer, int i2, int i3) {
        int i4 = (i2 + 1) / 2;
        int i5 = (i2 * i3) + 0;
        int i6 = ((i3 + 1) / 2) * i4;
        int i7 = i5 + i6;
        byteBuffer.position(0);
        byteBuffer.limit(i5);
        ByteBuffer byteBufferSlice = byteBuffer.slice();
        byteBuffer.position(i5);
        byteBuffer.limit(i7);
        ByteBuffer byteBufferSlice2 = byteBuffer.slice();
        byteBuffer.position(i7);
        byteBuffer.limit(i7 + i6);
        return new JavaI420Buffer(i2, i3, byteBufferSlice, i2, byteBufferSlice2, i4, byteBuffer.slice(), i4, new Runnable() { // from class: org.wrtca.api.k
            @Override // java.lang.Runnable
            public final void run() {
                JavaI420Buffer.lambda$allocate$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$allocate$0() {
    }

    public static JavaI420Buffer wrap(int i2, int i3, ByteBuffer byteBuffer, int i4, ByteBuffer byteBuffer2, int i5, ByteBuffer byteBuffer3, int i6, Runnable runnable) {
        if (byteBuffer == null || byteBuffer2 == null || byteBuffer3 == null) {
            throw new IllegalArgumentException("Data buffers cannot be null.");
        }
        if (!byteBuffer.isDirect() || !byteBuffer2.isDirect() || !byteBuffer3.isDirect()) {
            throw new IllegalArgumentException("Data buffers must be direct byte buffers.");
        }
        ByteBuffer byteBufferSlice = byteBuffer.slice();
        ByteBuffer byteBufferSlice2 = byteBuffer2.slice();
        ByteBuffer byteBufferSlice3 = byteBuffer3.slice();
        int i7 = (i3 + 1) / 2;
        int i8 = i4 * i3;
        int i9 = i5 * i7;
        int i10 = i7 * i6;
        if (byteBufferSlice.capacity() < i8) {
            throw new IllegalArgumentException("Y-buffer must be at least " + i8 + " bytes.");
        }
        if (byteBufferSlice2.capacity() < i9) {
            throw new IllegalArgumentException("U-buffer must be at least " + i9 + " bytes.");
        }
        if (byteBufferSlice3.capacity() >= i10) {
            return new JavaI420Buffer(i2, i3, byteBufferSlice, i4, byteBufferSlice2, i5, byteBufferSlice3, i6, runnable);
        }
        throw new IllegalArgumentException("V-buffer must be at least " + i10 + " bytes.");
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public VideoFrame.Buffer cropAndScale(int i2, int i3, int i4, int i5, int i6, int i7) {
        if (d.b.d() == 1 && e.a.a().w() != null) {
            int iF = e.a.a().w().f();
            int iB = e.a.a().w().b();
            if ((iF > 0 && iB > 0 && iF != i4) || iB != i5) {
                return VideoFrame.cropAndScaleI420(this, i2, i3, i4, i5, iF, iB);
            }
        }
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

    public void setRtcExtend(boolean z2) {
        this.rtcExtend = z2;
    }

    @Override // org.wrtca.api.VideoFrame.Buffer
    public VideoFrame.I420Buffer toI420() {
        retain();
        return this;
    }

    public static JavaI420Buffer allocate(byte[] bArr, int i2, int i3) {
        int i4 = (i3 + 1) / 2;
        int i5 = (i2 + 1) / 2;
        int i6 = i2 * i3;
        int i7 = i6 + 0;
        int i8 = i5 * i4;
        int i9 = i7 + i8;
        final ByteBuffer byteBufferNativeAllocateByteBuffer = JniCommon.nativeAllocateByteBuffer((i5 * 2 * i4) + i6);
        byteBufferNativeAllocateByteBuffer.position(0);
        byteBufferNativeAllocateByteBuffer.limit(i7);
        ByteBuffer byteBufferSlice = byteBufferNativeAllocateByteBuffer.slice();
        byteBufferSlice.put(bArr, 0, i6);
        byteBufferSlice.flip();
        byteBufferNativeAllocateByteBuffer.position(i7);
        byteBufferNativeAllocateByteBuffer.limit(i9);
        ByteBuffer byteBufferSlice2 = byteBufferNativeAllocateByteBuffer.slice();
        byteBufferSlice2.put(bArr, i7, i8);
        byteBufferSlice2.flip();
        byteBufferNativeAllocateByteBuffer.position(i9);
        byteBufferNativeAllocateByteBuffer.limit(i9 + i8);
        ByteBuffer byteBufferSlice3 = byteBufferNativeAllocateByteBuffer.slice();
        byteBufferSlice3.put(bArr, i9, i8);
        byteBufferSlice3.flip();
        return new JavaI420Buffer(i2, i3, byteBufferSlice, i2, byteBufferSlice2, i5, byteBufferSlice3, i5, new Runnable() { // from class: org.wrtca.api.l
            @Override // java.lang.Runnable
            public final void run() {
                JniCommon.nativeFreeByteBuffer(byteBufferNativeAllocateByteBuffer);
            }
        });
    }

    public static JavaI420Buffer allocate(int i2, int i3) {
        int i4 = (i3 + 1) / 2;
        int i5 = (i2 + 1) / 2;
        int i6 = i2 * i3;
        int i7 = i6 + 0;
        int i8 = i5 * i4;
        int i9 = i7 + i8;
        final ByteBuffer byteBufferNativeAllocateByteBuffer = JniCommon.nativeAllocateByteBuffer(i6 + (i5 * 2 * i4));
        byteBufferNativeAllocateByteBuffer.position(0);
        byteBufferNativeAllocateByteBuffer.limit(i7);
        ByteBuffer byteBufferSlice = byteBufferNativeAllocateByteBuffer.slice();
        byteBufferNativeAllocateByteBuffer.position(i7);
        byteBufferNativeAllocateByteBuffer.limit(i9);
        ByteBuffer byteBufferSlice2 = byteBufferNativeAllocateByteBuffer.slice();
        byteBufferNativeAllocateByteBuffer.position(i9);
        byteBufferNativeAllocateByteBuffer.limit(i9 + i8);
        return new JavaI420Buffer(i2, i3, byteBufferSlice, i2, byteBufferSlice2, i5, byteBufferNativeAllocateByteBuffer.slice(), i5, new Runnable() { // from class: org.wrtca.api.j
            @Override // java.lang.Runnable
            public final void run() {
                JniCommon.nativeFreeByteBuffer(byteBufferNativeAllocateByteBuffer);
            }
        });
    }
}
