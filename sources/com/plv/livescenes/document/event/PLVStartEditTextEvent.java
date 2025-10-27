package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVStartEditTextEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "startEditText";
    private String content;
    private String strokeStyle;

    public static PLVStartEditTextEvent fromJson(String str) {
        return (PLVStartEditTextEvent) new Gson().fromJson(str, PLVStartEditTextEvent.class);
    }

    public String getContent() {
        return this.content;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public String getStrokeStyle() {
        return this.strokeStyle;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }
}
