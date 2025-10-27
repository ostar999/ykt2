package com.plv.socket.event;

/* loaded from: classes5.dex */
public class PLVBaseEventImpl extends PLVBaseEvent {
    private String EVENT;
    private String listenEvent;

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return this.EVENT;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getListenEvent() {
        return this.listenEvent;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setListenEvent(String str) {
        this.listenEvent = str;
    }
}
