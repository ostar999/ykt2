package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class UpdateCommentInfoEvent {
    public String id;
    public boolean isAdd;
    public String type;

    public UpdateCommentInfoEvent(String id, String type, boolean isAdd) {
        this.id = id;
        this.type = type;
        this.isAdd = isAdd;
    }
}
