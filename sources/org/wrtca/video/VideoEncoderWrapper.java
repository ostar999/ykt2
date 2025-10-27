package org.wrtca.video;

import java.nio.ByteBuffer;
import org.wrtca.api.EncodedImage;
import org.wrtca.api.VideoEncoder;
import org.wrtca.jni.CalledByNative;
import org.wrtca.util.NativeClassQualifiedName;

/* loaded from: classes9.dex */
class VideoEncoderWrapper {
    @CalledByNative
    public static VideoEncoder.Callback createEncoderCallback(final long j2) {
        return new VideoEncoder.Callback() { // from class: org.wrtca.video.f
            @Override // org.wrtca.api.VideoEncoder.Callback
            public final void onEncodedFrame(EncodedImage encodedImage, VideoEncoder.CodecSpecificInfo codecSpecificInfo) {
                VideoEncoderWrapper.lambda$createEncoderCallback$0(j2, encodedImage, codecSpecificInfo);
            }
        };
    }

    @CalledByNative
    public static Integer getScalingSettingsHigh(VideoEncoder.ScalingSettings scalingSettings) {
        return scalingSettings.high;
    }

    @CalledByNative
    public static Integer getScalingSettingsLow(VideoEncoder.ScalingSettings scalingSettings) {
        return scalingSettings.low;
    }

    @CalledByNative
    public static boolean getScalingSettingsOn(VideoEncoder.ScalingSettings scalingSettings) {
        return scalingSettings.on;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$createEncoderCallback$0(long j2, EncodedImage encodedImage, VideoEncoder.CodecSpecificInfo codecSpecificInfo) {
        nativeOnEncodedFrame(j2, encodedImage.buffer, encodedImage.encodedWidth, encodedImage.encodedHeight, encodedImage.captureTimeNs, encodedImage.frameType.getNative(), encodedImage.rotation, encodedImage.completeFrame, encodedImage.qp);
    }

    @NativeClassQualifiedName("webrtc::jni::VideoEncoderWrapper")
    private static native void nativeOnEncodedFrame(long j2, ByteBuffer byteBuffer, int i2, int i3, long j3, int i4, int i5, boolean z2, Integer num);
}
