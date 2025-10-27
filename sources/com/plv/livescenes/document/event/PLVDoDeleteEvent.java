package com.plv.livescenes.document.event;

/* loaded from: classes4.dex */
public class PLVDoDeleteEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "doDelete";

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return "";
    }
}
