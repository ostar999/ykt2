package org.wrtca.api;

import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public interface VideoDecoder {

    public interface Callback {
        void onDecodedFrame(VideoFrame videoFrame, Integer num, Integer num2);
    }

    public static class DecodeInfo {
        public final boolean isMissingFrames;
        public final long renderTimeMs;

        public DecodeInfo(boolean z2, long j2) {
            this.isMissingFrames = z2;
            this.renderTimeMs = j2;
        }
    }

    public static class Settings {
        public final int height;
        public final int numberOfCores;
        public final int width;

        @CalledByNative("Settings")
        public Settings(int i2, int i3, int i4) {
            this.numberOfCores = i2;
            this.width = i3;
            this.height = i4;
        }
    }

    @CalledByNative
    VideoCodecStatus decode(EncodedImage encodedImage, DecodeInfo decodeInfo);

    @CalledByNative
    String getImplementationName();

    @CalledByNative
    boolean getPrefersLateDecoding();

    @CalledByNative
    VideoCodecStatus initDecode(Settings settings, Callback callback);

    @CalledByNative
    VideoCodecStatus release();
}
