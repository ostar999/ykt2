package com.plv.socket.event.chat;

import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVRemoveHistoryEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "REMOVE_HISTORY";

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "REMOVE_HISTORY";
    }

    public String toString() {
        return "PLVRemoveHistoryEvent{EVENT='REMOVE_HISTORY'}";
    }
}
