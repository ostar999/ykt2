package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVRefreshPptContainerTotalEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "refreshPptContainerTotal";
    private int total;

    public static PLVRefreshPptContainerTotalEvent fromJson(String str) {
        return (PLVRefreshPptContainerTotalEvent) new Gson().fromJson(str, PLVRefreshPptContainerTotalEvent.class);
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public int getTotal() {
        return this.total;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }
}
