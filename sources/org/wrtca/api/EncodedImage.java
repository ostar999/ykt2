package org.wrtca.api;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public class EncodedImage {
    public final ByteBuffer buffer;
    public final long captureTimeMs;
    public final long captureTimeNs;
    public final boolean completeFrame;
    public final int encodedHeight;
    public final int encodedWidth;
    public final FrameType frameType;
    public final Integer qp;
    public final int rotation;

    public static class Builder {
        private ByteBuffer buffer;
        private long captureTimeNs;
        private boolean completeFrame;
        private int encodedHeight;
        private int encodedWidth;
        private FrameType frameType;
        private Integer qp;
        private int rotation;

        public EncodedImage createEncodedImage() {
            return new EncodedImage(this.buffer, this.encodedWidth, this.encodedHeight, this.captureTimeNs, this.frameType, this.rotation, this.completeFrame, this.qp);
        }

        public Builder setBuffer(ByteBuffer byteBuffer) {
            this.buffer = byteBuffer;
            return this;
        }

        @Deprecated
        public Builder setCaptureTimeMs(long j2) {
            this.captureTimeNs = TimeUnit.MILLISECONDS.toNanos(j2);
            return this;
        }

        public Builder setCaptureTimeNs(long j2) {
            this.captureTimeNs = j2;
            return this;
        }

        public Builder setCompleteFrame(boolean z2) {
            this.completeFrame = z2;
            return this;
        }

        public Builder setEncodedHeight(int i2) {
            this.encodedHeight = i2;
            return this;
        }

        public Builder setEncodedWidth(int i2) {
            this.encodedWidth = i2;
            return this;
        }

        public Builder setFrameType(FrameType frameType) {
            this.frameType = frameType;
            return this;
        }

        public Builder setQp(Integer num) {
            this.qp = num;
            return this;
        }

        public Builder setRotation(int i2) {
            this.rotation = i2;
            return this;
        }

        private Builder() {
        }
    }

    public enum FrameType {
        EmptyFrame(0),
        VideoFrameKey(3),
        VideoFrameDelta(4);

        private final int nativeIndex;

        FrameType(int i2) {
            this.nativeIndex = i2;
        }

        @CalledByNative("FrameType")
        public static FrameType fromNativeIndex(int i2) {
            for (FrameType frameType : values()) {
                if (frameType.getNative() == i2) {
                    return frameType;
                }
            }
            throw new IllegalArgumentException("Unknown native frame type: " + i2);
        }

        public int getNative() {
            return this.nativeIndex;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @CalledByNative
    private EncodedImage(ByteBuffer byteBuffer, int i2, int i3, long j2, FrameType frameType, int i4, boolean z2, Integer num) {
        this.buffer = byteBuffer;
        this.encodedWidth = i2;
        this.encodedHeight = i3;
        this.captureTimeMs = TimeUnit.NANOSECONDS.toMillis(j2);
        this.captureTimeNs = j2;
        this.frameType = frameType;
        this.rotation = i4;
        this.completeFrame = z2;
        this.qp = num;
    }
}
