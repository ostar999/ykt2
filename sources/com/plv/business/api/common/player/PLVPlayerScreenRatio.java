package com.plv.business.api.common.player;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVPlayerScreenRatio {
    public static final int AR_16_9_FIT_PARENT = 4;
    public static final int AR_4_3_FIT_PARENT = 5;
    public static final int AR_ASPECT_FILL_PARENT = 1;
    public static final int AR_ASPECT_FIT_PARENT = 0;
    public static final int AR_ASPECT_WRAP_CONTENT = 2;
    public static final int AR_MATCH_PARENT = 3;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RenderScreenRatio {
    }
}
