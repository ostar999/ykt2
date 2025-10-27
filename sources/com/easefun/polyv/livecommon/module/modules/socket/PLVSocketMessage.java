package com.easefun.polyv.livecommon.module.modules.socket;

/* loaded from: classes3.dex */
public class PLVSocketMessage {
    private String event;
    private String listenEvent;
    private String message;

    public PLVSocketMessage(String listenEvent, String message, String event) {
        this.listenEvent = listenEvent;
        this.message = message;
        this.event = event;
    }

    public String getEvent() {
        return this.event;
    }

    public String getListenEvent() {
        return this.listenEvent;
    }

    public String getMessage() {
        return this.message;
    }
}
