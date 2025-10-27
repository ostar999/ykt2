package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVFinishEditTextEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "finishEditText";
    private String content;

    public PLVFinishEditTextEvent() {
    }

    public static PLVFinishEditTextEvent fromJson(String str) {
        return (PLVFinishEditTextEvent) new Gson().fromJson(str, PLVFinishEditTextEvent.class);
    }

    public String getContent() {
        return this.content;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public void setContent(String str) {
        this.content = str;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }

    public PLVFinishEditTextEvent(String str) {
        this.content = str;
    }
}
