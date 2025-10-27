package com.plv.business.api.common.player;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVVideoType {
    public static final int ENCRYPTION_M3U8 = 3;
    public static final int MP4 = 1;
    public static final int SOURCE = 4;
    public static final int UNENCRYPTED_M3U8 = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoType {
    }
}
