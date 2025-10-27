package com.plv.livescenes.streamer.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public class PLVStreamerConfig {

    public static class Bitrate {
        public static final int BITRATE_HIGH = 2;
        public static final int BITRATE_STANDARD = 1;
        public static final int BITRATE_SUPER = 3;

        public static String getText(int i2) {
            return i2 == 3 ? "超清" : i2 == 2 ? "高清" : i2 == 1 ? "标清" : "";
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BitrateType {
    }

    public static class MixStream {
        public static final int MIX_LAYOUT_TYPE_SINGLE = 1;
        public static final int MIX_LAYOUT_TYPE_SPEAKER = 3;
        public static final int MIX_LAYOUT_TYPE_TILE = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MixStreamType {
    }

    public static class NetQuality {
        public static final int NET_QUALITY_GOOD = 11;
        public static final int NET_QUALITY_MIDDLE = 12;
        public static final int NET_QUALITY_NO_CONNECTION = 14;
        public static final int NET_QUALITY_POOR = 13;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetQualityType {
    }

    public static class RenderMode {
        public static final int RENDER_MODE_FIT = 2;
        public static final int RENDER_MODE_HIDDEN = 1;
        public static final int RENDER_MODE_NONE = 10;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RenderModeType {
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
