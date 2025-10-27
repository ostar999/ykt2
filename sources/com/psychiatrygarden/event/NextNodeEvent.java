package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class NextNodeEvent {
    private String id;
    private String title;

    public NextNodeEvent(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
