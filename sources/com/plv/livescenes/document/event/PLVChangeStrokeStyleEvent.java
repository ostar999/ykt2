package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVChangeStrokeStyleEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "changeStrokeStyle";
    private String strokeStyle;

    public PLVChangeStrokeStyleEvent() {
    }

    public static PLVChangeStrokeStyleEvent fromJson(String str) {
        return (PLVChangeStrokeStyleEvent) new Gson().fromJson(str, PLVChangeStrokeStyleEvent.class);
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public String getStrokeStyle() {
        return this.strokeStyle;
    }

    public PLVChangeStrokeStyleEvent setStrokeStyle(String str) {
        this.strokeStyle = str;
        return this;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }

    public PLVChangeStrokeStyleEvent(String str) {
        this.strokeStyle = str;
    }
}
