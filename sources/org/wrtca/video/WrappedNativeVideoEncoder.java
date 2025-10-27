package org.wrtca.video;

import org.wrtca.api.VideoCodecStatus;
import org.wrtca.api.VideoEncoder;
import org.wrtca.api.VideoFrame;
import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public abstract class WrappedNativeVideoEncoder implements VideoEncoder {
    @CalledByNative
    public static boolean isInstanceOf(VideoEncoder videoEncoder) {
        return videoEncoder instanceof WrappedNativeVideoEncoder;
    }

    @CalledByNative
    public static boolean isWrappedSoftwareEncoder(VideoEncoder videoEncoder) {
        return (videoEncoder instanceof WrappedNativeVideoEncoder) && ((WrappedNativeVideoEncoder) videoEncoder).isSoftwareEncoder();
    }

    @CalledByNative
    abstract long createNativeEncoder();

    @Override // org.wrtca.api.VideoEncoder
    public VideoCodecStatus encode(VideoFrame videoFrame, VideoEncoder.EncodeInfo encodeInfo) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override // org.wrtca.api.VideoEncoder
    public String getImplementationName() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override // org.wrtca.api.VideoEncoder
    public VideoEncoder.ScalingSettings getScalingSettings() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override // org.wrtca.api.VideoEncoder
    public VideoCodecStatus initEncode(VideoEncoder.Settings settings, VideoEncoder.Callback callback) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    abstract boolean isSoftwareEncoder();

    @Override // org.wrtca.api.VideoEncoder
    public VideoCodecStatus release() {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override // org.wrtca.api.VideoEncoder
    public VideoCodecStatus setChannelParameters(short s2, long j2) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override // org.wrtca.api.VideoEncoder
    public VideoCodecStatus setRateAllocation(VideoEncoder.BitrateAllocation bitrateAllocation, int i2) {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
