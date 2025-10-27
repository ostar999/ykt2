package com.aliyun.player.source;

import com.aliyun.player.alivcplayerexpand.listener.QualityValue;

/* loaded from: classes2.dex */
public enum Definition {
    DEFINITION_FD(QualityValue.QUALITY_FLUENT),
    DEFINITION_LD(QualityValue.QUALITY_LOW),
    DEFINITION_SD(QualityValue.QUALITY_STAND),
    DEFINITION_HD(QualityValue.QUALITY_HIGH),
    DEFINITION_OD(QualityValue.QUALITY_ORIGINAL),
    DEFINITION_2K(QualityValue.QUALITY_2K),
    DEFINITION_4K(QualityValue.QUALITY_4K),
    DEFINITION_SQ(QualityValue.QUALITY_SQ),
    DEFINITION_HQ(QualityValue.QUALITY_HQ),
    DEFINITION_AUTO("AUTO");

    private String mName;

    Definition(String str) {
        this.mName = str;
    }

    public String getName() {
        return this.mName;
    }
}
