package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class OnRefreshEvent {
    private int refreshStatus;

    public OnRefreshEvent(int refreshStatus) {
        this.refreshStatus = refreshStatus;
    }

    public int getRefreshStatus() {
        return this.refreshStatus;
    }

    public void setRefreshStatus(int refreshStatus) {
        this.refreshStatus = refreshStatus;
    }
}
