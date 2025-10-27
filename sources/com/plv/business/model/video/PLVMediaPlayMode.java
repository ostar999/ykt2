package com.plv.business.model.video;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVMediaPlayMode {
    private static final int MAX_MODE_VALUE = 1;
    private static final int MIN_MODE_VALUE = 0;
    public static final int MODE_AUDIO = 1;
    public static final int MODE_VIDEO = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public static int amendMode(int i2) {
        if (i2 < 0 || i2 > 1) {
            return 0;
        }
        return i2;
    }
}
