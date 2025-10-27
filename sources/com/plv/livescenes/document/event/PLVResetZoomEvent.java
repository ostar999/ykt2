package com.plv.livescenes.document.event;

/* loaded from: classes4.dex */
public class PLVResetZoomEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "resetZoom";

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return "";
    }
}
