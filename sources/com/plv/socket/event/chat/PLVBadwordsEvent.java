package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVBadwordsEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "SPEAK";
    public static final String STATUS_ERROR = "error";
    private String message;
    private String status;
    private String value;

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "SPEAK";
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public String getValue() {
        return this.value;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String toString() {
        return "PLVBadwordsEvent{EVENT='SPEAK', message='" + this.message + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", value='" + this.value + CharPool.SINGLE_QUOTE + '}';
    }
}
