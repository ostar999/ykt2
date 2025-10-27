package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class ScrollEvent {
    private boolean isRefresh;
    private int scollY;

    public ScrollEvent(int scollY) {
        this.scollY = scollY;
    }

    public int getScollY() {
        return this.scollY;
    }

    public boolean isRefresh() {
        return this.isRefresh;
    }

    public void setRefresh(boolean refresh) {
        this.isRefresh = refresh;
    }

    public void setScollY(int scollY) {
        this.scollY = scollY;
    }

    public ScrollEvent(int scollY, boolean isRefresh) {
        this.scollY = scollY;
        this.isRefresh = isRefresh;
    }
}
