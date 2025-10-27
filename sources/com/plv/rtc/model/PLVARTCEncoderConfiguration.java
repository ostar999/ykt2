package com.plv.rtc.model;

/* loaded from: classes5.dex */
public class PLVARTCEncoderConfiguration {
    public int bitrate;
    public DEGRADATION_PREFERENCE degradationPrefer;
    public VideoDimensions dimensions;
    public int frameRate;
    public int minBitrate;
    public int minFrameRate;
    public int mirrorMode;
    public ORIENTATION_MODE orientationMode;

    public enum DEGRADATION_PREFERENCE {
        MAINTAIN_QUALITY(0),
        MAINTAIN_FRAMERATE(1),
        MAINTAIN_BALANCED(2);

        private int value;

        DEGRADATION_PREFERENCE(int i2) {
            this.value = i2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum FRAME_RATE {
        FRAME_RATE_FPS_1(1),
        FRAME_RATE_FPS_7(7),
        FRAME_RATE_FPS_10(10),
        FRAME_RATE_FPS_15(15),
        FRAME_RATE_FPS_24(24),
        FRAME_RATE_FPS_30(30);

        private int value;

        FRAME_RATE(int i2) {
            this.value = i2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public enum ORIENTATION_MODE {
        ORIENTATION_MODE_ADAPTIVE(0),
        ORIENTATION_MODE_FIXED_LANDSCAPE(1),
        ORIENTATION_MODE_FIXED_PORTRAIT(2);

        private int value;

        ORIENTATION_MODE(int i2) {
            this.value = i2;
        }

        public int getValue() {
            return this.value;
        }
    }

    public PLVARTCEncoderConfiguration() {
        this.dimensions = new VideoDimensions(640, 480);
        this.frameRate = FRAME_RATE.FRAME_RATE_FPS_15.getValue();
        this.minFrameRate = -1;
        this.bitrate = 0;
        this.minBitrate = -1;
        this.orientationMode = ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE;
        this.degradationPrefer = DEGRADATION_PREFERENCE.MAINTAIN_QUALITY;
        this.mirrorMode = 0;
    }

    public static class VideoDimensions {
        public int height;
        public int width;

        public VideoDimensions(int i2, int i3) {
            this.width = i2;
            this.height = i3;
        }

        public VideoDimensions() {
            this.width = 640;
            this.height = 480;
        }
    }

    public PLVARTCEncoderConfiguration(VideoDimensions videoDimensions, FRAME_RATE frame_rate, int i2, ORIENTATION_MODE orientation_mode) {
        this.dimensions = videoDimensions;
        this.frameRate = frame_rate.getValue();
        this.minFrameRate = -1;
        this.bitrate = i2;
        this.minBitrate = -1;
        this.orientationMode = orientation_mode;
        this.degradationPrefer = DEGRADATION_PREFERENCE.MAINTAIN_QUALITY;
        this.mirrorMode = 0;
    }

    public PLVARTCEncoderConfiguration(int i2, int i3, FRAME_RATE frame_rate, int i4, ORIENTATION_MODE orientation_mode) {
        this.dimensions = new VideoDimensions(i2, i3);
        this.frameRate = frame_rate.getValue();
        this.minFrameRate = -1;
        this.bitrate = i4;
        this.minBitrate = -1;
        this.orientationMode = orientation_mode;
        this.degradationPrefer = DEGRADATION_PREFERENCE.MAINTAIN_QUALITY;
        this.mirrorMode = 0;
    }
}
