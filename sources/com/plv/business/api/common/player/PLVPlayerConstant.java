package com.plv.business.api.common.player;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public final class PLVPlayerConstant {

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
}
