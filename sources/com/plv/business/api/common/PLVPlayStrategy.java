package com.plv.business.api.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVPlayStrategy {
    public static final int DEFAULT = 1;
    public static final int LOCAL_PLAY = 3;
    public static final int NET_PLAY = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayStrategy {
    }
}
