package com.psychiatrygarden.aliPlayer.utils;

import com.aliyun.player.alivcplayerexpand.listener.QualityValue;

/* loaded from: classes5.dex */
public enum VideoDefinition {
    FD(QualityValue.QUALITY_FLUENT, "流畅"),
    LD(QualityValue.QUALITY_LOW, "标清"),
    SD(QualityValue.QUALITY_STAND, "高清"),
    HD(QualityValue.QUALITY_HIGH, "超清"),
    TK(QualityValue.QUALITY_2K, QualityValue.QUALITY_2K),
    FK(QualityValue.QUALITY_4K, QualityValue.QUALITY_4K),
    OD(QualityValue.QUALITY_ORIGINAL, "原画");

    private String definition;
    private String label;

    VideoDefinition(String definition, String label) {
        this.definition = definition;
        this.label = label;
    }

    public String getDefinition() {
        return this.definition;
    }

    public String getLabel() {
        return this.label;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
