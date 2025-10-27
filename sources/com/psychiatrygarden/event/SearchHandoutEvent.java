package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class SearchHandoutEvent {
    private String content;
    private int type;

    public SearchHandoutEvent(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return this.content;
    }

    public int getType() {
        return this.type;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }
}
