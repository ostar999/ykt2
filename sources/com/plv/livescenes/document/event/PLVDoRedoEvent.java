package com.plv.livescenes.document.event;

/* loaded from: classes4.dex */
public class PLVDoRedoEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "doRedo";

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return "";
    }
}
