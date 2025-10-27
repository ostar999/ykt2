package com.plv.socket.event;

/* loaded from: classes5.dex */
public abstract class PLVMessageBaseEvent extends PLVBaseEvent {
    public static final String LISTEN_EVENT = "message";

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getListenEvent() {
        return "message";
    }
}
