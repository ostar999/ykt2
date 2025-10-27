package com.plv.socket.event.login;

import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVTokenExpiredEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "TOKEN_EXPIRED";

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "TOKEN_EXPIRED";
    }
}
