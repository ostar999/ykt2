package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVZoomPercentChangeEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "zoomPercenChange";
    private Float zoomPercen;

    public static PLVZoomPercentChangeEvent fromJson(String str) {
        return (PLVZoomPercentChangeEvent) new Gson().fromJson(str, PLVZoomPercentChangeEvent.class);
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public Float getZoomPercent() {
        return this.zoomPercen;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }
}
