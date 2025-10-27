package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVSendSocketDataEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "sendSocketData";
    private String socketData;

    public static PLVSendSocketDataEvent fromJson(String str) {
        return (PLVSendSocketDataEvent) new Gson().fromJson(str, PLVSendSocketDataEvent.class);
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public String getSocketData() {
        return this.socketData;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }
}
