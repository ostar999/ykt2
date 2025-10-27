package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class QuestionCollectEvent {
    private String id;
    private boolean isCollect;

    public QuestionCollectEvent(boolean isCollect, String id) {
        this.isCollect = isCollect;
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public boolean isCollect() {
        return this.isCollect;
    }

    public void setCollect(boolean collect) {
        this.isCollect = collect;
    }

    public void setId(String id) {
        this.id = id;
    }
}
