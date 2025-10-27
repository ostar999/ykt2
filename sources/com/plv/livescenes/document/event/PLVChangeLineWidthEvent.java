package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVChangeLineWidthEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "changeLineWidth";
    private int lineWidth;

    public PLVChangeLineWidthEvent() {
    }

    public static PLVChangeLineWidthEvent fromJson(String str) {
        return (PLVChangeLineWidthEvent) new Gson().fromJson(str, PLVChangeLineWidthEvent.class);
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public int getLineWidth() {
        return this.lineWidth;
    }

    public PLVChangeLineWidthEvent setLineWidth(int i2) {
        this.lineWidth = i2;
        return this;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }

    public PLVChangeLineWidthEvent(int i2) {
        this.lineWidth = i2;
    }
}
