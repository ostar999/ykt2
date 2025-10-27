package org.wrtca.api;

import org.wrtca.api.EncodedImage;
import org.wrtca.jni.CalledByNative;

/* loaded from: classes9.dex */
public interface VideoEncoder {

    public static class BitrateAllocation {
        public final int[][] bitratesBbs;

        @CalledByNative("BitrateAllocation")
        public BitrateAllocation(int[][] iArr) {
            this.bitratesBbs = iArr;
        }

        public int getSum() {
            int i2 = 0;
            for (int[] iArr : this.bitratesBbs) {
                for (int i3 : iArr) {
                    i2 += i3;
                }
            }
            return i2;
        }
    }

    public interface Callback {
        void onEncodedFrame(EncodedImage encodedImage, CodecSpecificInfo codecSpecificInfo);
    }

    public static class CodecSpecificInfo {
    }

    public static class CodecSpecificInfoH264 extends CodecSpecificInfo {
    }

    public static class CodecSpecificInfoVP8 extends CodecSpecificInfo {
    }

    public static class CodecSpecificInfoVP9 extends CodecSpecificInfo {
    }

    public static class EncodeInfo {
        public final EncodedImage.FrameType[] frameTypes;

        @CalledByNative("EncodeInfo")
        public EncodeInfo(EncodedImage.FrameType[] frameTypeArr) {
            this.frameTypes = frameTypeArr;
        }
    }

    public static class Settings {
        public final boolean automaticResizeOn;
        public final int height;
        public final int maxFramerate;
        public final int numberOfCores;
        public final int startBitrate;
        public final int width;

        @CalledByNative("Settings")
        public Settings(int i2, int i3, int i4, int i5, int i6, boolean z2) {
            this.numberOfCores = i2;
            this.width = i3;
            this.height = i4;
            this.startBitrate = i5;
            this.maxFramerate = i6;
            this.automaticResizeOn = z2;
        }
    }

    @CalledByNative
    VideoCodecStatus encode(VideoFrame videoFrame, EncodeInfo encodeInfo);

    @CalledByNative
    String getImplementationName();

    @CalledByNative
    ScalingSettings getScalingSettings();

    @CalledByNative
    VideoCodecStatus initEncode(Settings settings, Callback callback);

    @CalledByNative
    VideoCodecStatus release();

    @CalledByNative
    VideoCodecStatus setChannelParameters(short s2, long j2);

    @CalledByNative
    VideoCodecStatus setRateAllocation(BitrateAllocation bitrateAllocation, int i2);

    public static class ScalingSettings {
        public static final ScalingSettings OFF = new ScalingSettings();
        public final Integer high;
        public final Integer low;
        public final boolean on;

        public ScalingSettings(int i2, int i3) {
            this.on = true;
            this.low = Integer.valueOf(i2);
            this.high = Integer.valueOf(i3);
        }

        private ScalingSettings() {
            this.on = false;
            this.low = null;
            this.high = null;
        }

        @Deprecated
        public ScalingSettings(boolean z2) {
            this.on = z2;
            this.low = null;
            this.high = null;
        }

        @Deprecated
        public ScalingSettings(boolean z2, int i2, int i3) {
            this.on = z2;
            this.low = Integer.valueOf(i2);
            this.high = Integer.valueOf(i3);
        }
    }
}
