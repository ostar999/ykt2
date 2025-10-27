package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVOpenPptEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "openPpt";
    private long autoId;

    public PLVOpenPptEvent() {
    }

    public static PLVOpenPptEvent fromJson(String str) {
        return (PLVOpenPptEvent) new Gson().fromJson(str, PLVOpenPptEvent.class);
    }

    public long getAutoId() {
        return this.autoId;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public PLVOpenPptEvent setAutoId(long j2) {
        this.autoId = j2;
        return this;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }

    public PLVOpenPptEvent(long j2) {
        this.autoId = j2;
    }
}
