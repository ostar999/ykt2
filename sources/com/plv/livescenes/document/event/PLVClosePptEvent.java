package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVClosePptEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "closePpt";
    private long autoId;

    public PLVClosePptEvent() {
    }

    public static PLVClosePptEvent fromJson(String str) {
        return (PLVClosePptEvent) new Gson().fromJson(str, PLVClosePptEvent.class);
    }

    public long getAutoId() {
        return this.autoId;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public PLVClosePptEvent setAutoId(long j2) {
        this.autoId = j2;
        return this;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }

    public PLVClosePptEvent(long j2) {
        this.autoId = j2;
    }
}
