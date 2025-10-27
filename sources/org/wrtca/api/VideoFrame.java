package org.wrtca.api;

import android.graphics.Matrix;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class VideoFrame {
    private final Buffer buffer;
    private final int rotation;
    private final long timestampNs;

    public interface Buffer {
        @CalledByNative("Buffer")
        Buffer cropAndScale(int i2, int i3, int i4, int i5, int i6, int i7);

        @CalledByNative("Buffer")
        int getHeight();

        @CalledByNative("Buffer")
        int getWidth();

        @CalledByNative("Buffer")
        void release();

        @CalledByNative("Buffer")
        void retain();

        @CalledByNative("Buffer")
        I420Buffer toI420();
    }

    public interface I420Buffer extends Buffer {
        @CalledByNative("I420Buffer")
        ByteBuffer getDataU();

        @CalledByNative("I420Buffer")
        ByteBuffer getDataV();

        @CalledByNative("I420Buffer")
        ByteBuffer getDataY();

        @CalledByNative("I420Buffer")
        int getStrideU();

        @CalledByNative("I420Buffer")
        int getStrideV();

        @CalledByNative("I420Buffer")
        int getStrideY();
    }

    public interface TextureBuffer extends Buffer {

        public enum Type {
            OES(36197),
            RGB(R2.attr.tab_indicator_height);

            private final int glTarget;

            Type(int i2) {
                this.glTarget = i2;
            }

            public int getGlTarget() {
                return this.glTarget;
            }
        }

        int getTextureId();

        Matrix getTransformMatrix();

        Type getType();
    }

    @CalledByNative
    public VideoFrame(Buffer buffer, int i2, long j2) {
        if (buffer == null) {
            throw new IllegalArgumentException("buffer not allowed to be null");
        }
        if (i2 % 90 != 0) {
            throw new IllegalArgumentException("rotation must be a multiple of 90");
        }
        this.buffer = buffer;
        this.rotation = i2;
        this.timestampNs = j2;
    }

    public static Buffer cropAndScaleI420(final I420Buffer i420Buffer, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (i4 != i6 || i5 != i7) {
            JavaI420Buffer javaI420BufferAllocate = JavaI420Buffer.allocate(i6, i7);
            nativeCropAndScaleI420(i420Buffer.getDataY(), i420Buffer.getStrideY(), i420Buffer.getDataU(), i420Buffer.getStrideU(), i420Buffer.getDataV(), i420Buffer.getStrideV(), i2, i3, i4, i5, javaI420BufferAllocate.getDataY(), javaI420BufferAllocate.getStrideY(), javaI420BufferAllocate.getDataU(), javaI420BufferAllocate.getStrideU(), javaI420BufferAllocate.getDataV(), javaI420BufferAllocate.getStrideV(), i6, i7);
            return javaI420BufferAllocate;
        }
        ByteBuffer dataY = i420Buffer.getDataY();
        ByteBuffer dataU = i420Buffer.getDataU();
        ByteBuffer dataV = i420Buffer.getDataV();
        dataY.position(i2 + (i420Buffer.getStrideY() * i3));
        int i8 = i2 / 2;
        int i9 = i3 / 2;
        dataU.position((i420Buffer.getStrideU() * i9) + i8);
        dataV.position(i8 + (i9 * i420Buffer.getStrideV()));
        i420Buffer.retain();
        return JavaI420Buffer.wrap(i420Buffer.getWidth(), i420Buffer.getHeight(), dataY.slice(), i420Buffer.getStrideY(), dataU.slice(), i420Buffer.getStrideU(), dataV.slice(), i420Buffer.getStrideV(), new Runnable() { // from class: org.wrtca.api.t
            @Override // java.lang.Runnable
            public final void run() {
                i420Buffer.release();
            }
        });
    }

    private static native void nativeCropAndScaleI420(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, int i5, int i6, int i7, int i8, ByteBuffer byteBuffer4, int i9, ByteBuffer byteBuffer5, int i10, ByteBuffer byteBuffer6, int i11, int i12, int i13);

    @CalledByNative
    public Buffer getBuffer() {
        return this.buffer;
    }

    public int getRotatedHeight() {
        return this.rotation % 180 == 0 ? this.buffer.getHeight() : this.buffer.getWidth();
    }

    public int getRotatedWidth() {
        return this.rotation % 180 == 0 ? this.buffer.getWidth() : this.buffer.getHeight();
    }

    @CalledByNative
    public int getRotation() {
        return this.rotation;
    }

    @CalledByNative
    public long getTimestampNs() {
        return this.timestampNs;
    }

    @CalledByNative
    public void release() {
        this.buffer.release();
    }

    public void retain() {
        this.buffer.retain();
    }
}
