package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class ShareEvent {
    private String activityId;

    public ShareEvent(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return this.activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
