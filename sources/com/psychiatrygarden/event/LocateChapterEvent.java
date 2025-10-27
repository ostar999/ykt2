package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class LocateChapterEvent {
    private String primaryId;

    public LocateChapterEvent(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getPrimaryId() {
        return this.primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }
}
