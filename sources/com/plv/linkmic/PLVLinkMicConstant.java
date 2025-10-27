package com.plv.linkmic;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVLinkMicConstant {
    public static final int MAX_VOLUME = 100;
    public static final int RTC_INVOKE_ERROR = -1;
    public static final int RTC_INVOKE_SUCCESS = 0;

    public static class Bitrate {
        public static final int BITRATE_HIGH = 2;
        public static final int BITRATE_STANDARD = 1;
        public static final int BITRATE_SUPER = 3;
        public static final int SERVER_BITRATE_HIGH = 360;
        public static final int SERVER_BITRATE_SUPER = 720;

        public static boolean isServerSupportResolution(int i2, int i3) {
            if (i3 >= 720) {
                return true;
            }
            return i3 >= 360 ? i2 < 3 : i2 < 2;
        }

        public static int mapFromServerResolution(int i2) {
            if (i2 >= 720) {
                return 3;
            }
            return i2 >= 360 ? 2 : 1;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BitrateType {
    }

    public static class NetQuality {
        public static final int NET_QUALITY_GOOD = 11;
        public static final int NET_QUALITY_MIDDLE = 12;
        public static final int NET_QUALITY_NO_CONNECTION = 14;
        public static final int NET_QUALITY_POOR = 13;

        public static boolean isNetMiddleOrWorse(int i2) {
            return i2 == 12 || i2 == 13 || i2 == 14;
        }

        public static boolean isNetPoor(int i2) {
            return i2 == 13 || i2 == 14;
        }

        public static boolean isNoConnection(int i2) {
            return i2 == 14;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetQualityType {
    }

    public static class PushPictureResolution {
        public static final int RESOLUTION_AUTO = 0;
        public static final int RESOLUTION_LANDSCAPE = 2;
        public static final int RESOLUTION_PORTRAIT = 1;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PushPictureResolutionType {
    }

    public enum PushResolutionRatio {
        RATIO_16_9,
        RATIO_4_3;

        public PushResolutionRatio next() {
            return values()[(ordinal() + 1) % values().length];
        }
    }

    public static class RenderMode {
        public static final int RENDER_MODE_FIT = 2;
        public static final int RENDER_MODE_HIDDEN = 1;
        public static final int RENDER_MODE_NONE = 10;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RenderModeType {
    }

    public static class RenderStreamType {
        public static final int STREAM_TYPE_CAMERA = 1;
        public static final int STREAM_TYPE_MIX = 0;
        public static final int STREAM_TYPE_SCREEN = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RenderStreamTypeAnnotation {
    }

    public static class RtcType {
        public static final String RTC_TYPE_A = "agora";
        public static final String RTC_TYPE_T = "trtc";
        public static final String RTC_TYPE_U = "urtc";
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RtcTypeAnnotation {
    }

    public static class VideoEncoder {
        public static final int VD_1280x720 = 5;
        public static final int VD_320x180 = 1;
        public static final int VD_320x240 = 2;
        public static final int VD_424x240 = 3;
        public static final int VD_840x480 = 4;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoEncoderType {
    }
}
