package com.plv.livescenes.playback.video;

/* loaded from: classes5.dex */
public enum PLVPlaybackListType {
    PLAYBACK("2"),
    VOD("3"),
    TEMP_STORE("1");

    private final String statisticValuePb;

    PLVPlaybackListType(String str) {
        this.statisticValuePb = str;
    }

    public String getStatisticValuePb() {
        return this.statisticValuePb;
    }
}
