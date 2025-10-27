package org.wrtca.api;

import org.wrtca.jni.JNINamespace;
import org.wrtca.video.WrappedNativeVideoEncoder;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public abstract class VideoEncoderFallback extends WrappedNativeVideoEncoder {
    private final VideoEncoder fallback;
    private final VideoEncoder primary;

    public VideoEncoderFallback(VideoEncoder videoEncoder, VideoEncoder videoEncoder2) {
        this.fallback = videoEncoder;
        this.primary = videoEncoder2;
    }

    private static native long nativeCreateEncoder(VideoEncoder videoEncoder, VideoEncoder videoEncoder2);

    public long createNativeEncoder() {
        return nativeCreateEncoder(this.fallback, this.primary);
    }

    public boolean isSoftwareEncoder() {
        return WrappedNativeVideoEncoder.isWrappedSoftwareEncoder(this.primary);
    }
}
