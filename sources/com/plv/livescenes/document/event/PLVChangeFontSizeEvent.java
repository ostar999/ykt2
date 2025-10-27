package com.plv.livescenes.document.event;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class PLVChangeFontSizeEvent extends PLVAbsDocumentEvent {
    public static final String TYPE = "changeFontSize";
    private int fontSize;

    public PLVChangeFontSizeEvent() {
    }

    public static PLVChangeFontSizeEvent fromJson(String str) {
        return (PLVChangeFontSizeEvent) new Gson().fromJson(str, PLVChangeFontSizeEvent.class);
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String getEventType() {
        return TYPE;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public PLVChangeFontSizeEvent setFontSize(int i2) {
        this.fontSize = i2;
        return this;
    }

    @Override // com.plv.livescenes.document.event.PLVAbsDocumentEvent
    public String toJson() {
        return new Gson().toJson(this);
    }

    public PLVChangeFontSizeEvent(int i2) {
        this.fontSize = i2;
    }
}
