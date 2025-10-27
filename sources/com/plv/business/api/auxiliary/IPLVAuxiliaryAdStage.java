package com.plv.business.api.auxiliary;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public interface IPLVAuxiliaryAdStage {
    public static final int PLAY_STAGE_HEADAD = 1;
    public static final int PLAY_STAGE_NONE = 0;
    public static final int PLAY_STAGE_TAILAD = 3;
    public static final int PLAY_STAGE_TAILAD_FINISH = 33;
    public static final int PLAY_STAGE_TEASER = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface AdStage {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PlayStage {
    }
}
