package io.agora.rtc.gl;

import android.graphics.Matrix;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public class VideoFrame {
    private final Buffer buffer;
    private final int rotation;
    private final long timestampNs;

    public interface Buffer {
        Buffer cropAndScale(int cropX, int cropY, int cropWidth, int cropHeight, int scaleWidth, int scaleHeight);

        int getHeight();

        int getWidth();

        void release();

        void retain();

        I420Buffer toI420();
    }

    public interface I420Buffer extends Buffer {
        ByteBuffer getDataU();

        ByteBuffer getDataV();

        ByteBuffer getDataY();

        int getStrideU();

        int getStrideV();

        int getStrideY();
    }

    public interface TextureBuffer extends Buffer {

        public enum Type {
            OES(36197),
            RGB(R2.attr.tab_indicator_height);

            private final int glTarget;

            Type(final int glTarget) {
                this.glTarget = glTarget;
            }

            public int getGlTarget() {
                return this.glTarget;
            }
        }

        int getTextureId();

        Matrix getTransformMatrix();

        Type getType();
    }

    public VideoFrame(Buffer buffer, int rotation, long timestampNs) {
        if (buffer == null) {
            throw new IllegalArgumentException("buffer not allowed to be null");
        }
        if (rotation % 90 != 0) {
            throw new IllegalArgumentException("rotation must be a multiple of 90");
        }
        this.buffer = buffer;
        this.rotation = rotation;
        this.timestampNs = timestampNs;
    }

    public static Buffer cropAndScaleI420(final I420Buffer buffer, int cropX, int cropY, int cropWidth, int cropHeight, int scaleWidth, int scaleHeight) {
        if (cropWidth != scaleWidth || cropHeight != scaleHeight) {
            JavaI420Buffer javaI420BufferAllocate = JavaI420Buffer.allocate(scaleWidth, scaleHeight);
            nativeCropAndScaleI420(buffer.getDataY(), buffer.getStrideY(), buffer.getDataU(), buffer.getStrideU(), buffer.getDataV(), buffer.getStrideV(), cropX, cropY, cropWidth, cropHeight, javaI420BufferAllocate.getDataY(), javaI420BufferAllocate.getStrideY(), javaI420BufferAllocate.getDataU(), javaI420BufferAllocate.getStrideU(), javaI420BufferAllocate.getDataV(), javaI420BufferAllocate.getStrideV(), scaleWidth, scaleHeight);
            return javaI420BufferAllocate;
        }
        ByteBuffer dataY = buffer.getDataY();
        ByteBuffer dataU = buffer.getDataU();
        ByteBuffer dataV = buffer.getDataV();
        dataY.position(cropX + (buffer.getStrideY() * cropY));
        int i2 = cropX / 2;
        int i3 = cropY / 2;
        dataU.position((buffer.getStrideU() * i3) + i2);
        dataV.position(i2 + (i3 * buffer.getStrideV()));
        buffer.retain();
        return JavaI420Buffer.wrap(buffer.getWidth(), buffer.getHeight(), dataY.slice(), buffer.getStrideY(), dataU.slice(), buffer.getStrideU(), dataV.slice(), buffer.getStrideV(), new Runnable() { // from class: io.agora.rtc.gl.VideoFrame.1
            @Override // java.lang.Runnable
            public void run() {
                buffer.release();
            }
        });
    }

    private static native void nativeCropAndScaleI420(ByteBuffer srcY, int srcStrideY, ByteBuffer srcU, int srcStrideU, ByteBuffer srcV, int srcStrideV, int cropX, int cropY, int cropWidth, int cropHeight, ByteBuffer dstY, int dstStrideY, ByteBuffer dstU, int dstStrideU, ByteBuffer dstV, int dstStrideV, int scaleWidth, int scaleHeight);

    public Buffer getBuffer() {
        return this.buffer;
    }

    public int getRotatedHeight() {
        return this.rotation % 180 == 0 ? this.buffer.getHeight() : this.buffer.getWidth();
    }

    public int getRotatedWidth() {
        return this.rotation % 180 == 0 ? this.buffer.getWidth() : this.buffer.getHeight();
    }

    public int getRotation() {
        return this.rotation;
    }

    public long getTimestampNs() {
        return this.timestampNs;
    }

    public void release() {
        this.buffer.release();
    }

    public void retain() {
        this.buffer.retain();
    }
}
