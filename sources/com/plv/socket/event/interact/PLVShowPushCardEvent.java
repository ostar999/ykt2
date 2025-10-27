package com.plv.socket.event.interact;

/* loaded from: classes5.dex */
public class PLVShowPushCardEvent {
    private PLVNewsPushStartEvent data;
    private final String event = "SHOW_PUSH_CARD";

    public PLVShowPushCardEvent(PLVNewsPushStartEvent pLVNewsPushStartEvent) {
        this.data = pLVNewsPushStartEvent;
    }

    public PLVNewsPushStartEvent getData() {
        return this.data;
    }

    public String getEvent() {
        return "SHOW_PUSH_CARD";
    }

    public void setData(PLVNewsPushStartEvent pLVNewsPushStartEvent) {
        this.data = pLVNewsPushStartEvent;
    }
}
