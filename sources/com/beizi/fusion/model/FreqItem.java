package com.beizi.fusion.model;

import androidx.annotation.NonNull;
import cn.hutool.core.text.CharPool;
import com.umeng.analytics.pro.am;

/* loaded from: classes2.dex */
public class FreqItem {

    @JsonNode(key = "componentType")
    private int componentType;

    @JsonNode(key = "eventCode")
    private String eventCode;

    @JsonNode(key = am.aT)
    private long interval = Long.MAX_VALUE;

    @JsonNode(key = "count")
    private int count = Integer.MAX_VALUE;

    public int getComponentType() {
        return this.componentType;
    }

    public int getCount() {
        return this.count;
    }

    public String getEventCode() {
        return this.eventCode;
    }

    public long getInterval() {
        return this.interval;
    }

    public void setComponentType(int i2) {
        this.componentType = i2;
    }

    public void setCount(int i2) {
        this.count = i2;
    }

    public void setEventCode(String str) {
        this.eventCode = str;
    }

    public void setInterval(long j2) {
        this.interval = j2;
    }

    @NonNull
    public String toString() {
        return "FreqItem{interval=" + this.interval + ", count=" + this.count + ", eventCode='" + this.eventCode + CharPool.SINGLE_QUOTE + ", componentType=" + this.componentType + '}';
    }
}
