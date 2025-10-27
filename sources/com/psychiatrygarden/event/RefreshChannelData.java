package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshChannelData {
    private String channelId;
    private boolean isRefresh;

    public RefreshChannelData(String channelId, boolean isRefresh) {
        this.channelId = channelId;
        this.isRefresh = isRefresh;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public boolean isRefresh() {
        return this.isRefresh;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setRefresh(boolean refresh) {
        this.isRefresh = refresh;
    }

    public RefreshChannelData(String channelId) {
        this.isRefresh = true;
        this.channelId = channelId;
    }
}
