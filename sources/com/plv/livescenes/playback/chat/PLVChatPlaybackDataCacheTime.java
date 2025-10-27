package com.plv.livescenes.playback.chat;

import cn.hutool.core.text.CharPool;
import com.plv.thirdpart.litepal.crud.LitePalSupport;

/* loaded from: classes5.dex */
public class PLVChatPlaybackDataCacheTime extends LitePalSupport {
    private int endId;
    private int endTime;
    private int partId;
    private String sessionId;
    private int startId;
    private int startTime;

    public int getEndId() {
        return this.endId;
    }

    public int getEndTime() {
        return this.endTime;
    }

    public int getPartId() {
        return this.partId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public int getStartId() {
        return this.startId;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public void setEndId(int i2) {
        this.endId = i2;
    }

    public void setEndTime(int i2) {
        this.endTime = i2;
    }

    public void setPartId(int i2) {
        this.partId = i2;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setStartId(int i2) {
        this.startId = i2;
    }

    public void setStartTime(int i2) {
        this.startTime = i2;
    }

    public String toString() {
        return "PLVChatPlaybackDataCacheTime{partId=" + this.partId + ", startId=" + this.startId + ", endId=" + this.endId + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + '}';
    }
}
