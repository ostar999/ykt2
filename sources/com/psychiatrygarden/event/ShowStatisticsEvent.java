package com.psychiatrygarden.event;

import com.psychiatrygarden.bean.StatisticsData;

/* loaded from: classes5.dex */
public class ShowStatisticsEvent {
    private StatisticsData data;

    public ShowStatisticsEvent(StatisticsData data) {
        this.data = data;
    }

    public StatisticsData getData() {
        return this.data;
    }

    public void setData(StatisticsData data) {
        this.data = data;
    }
}
