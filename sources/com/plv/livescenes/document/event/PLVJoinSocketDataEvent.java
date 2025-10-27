package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVJoinSocketDataEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "joinSocketData";
    private String socketData;

    public PLVJoinSocketDataEvent() {
    }

    public static PLVJoinSocketDataEvent fromJson(String str) {
        return (PLVJoinSocketDataEvent) new Gson().fromJson(str, PLVJoinSocketDataEvent.class);
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public String getSocketData() {
        return this.socketData;
    }

    public PLVJoinSocketDataEvent setSocketData(String str) {
        this.socketData = str;
        return this;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }

    public PLVJoinSocketDataEvent(String str) {
        this.socketData = str;
    }
}
