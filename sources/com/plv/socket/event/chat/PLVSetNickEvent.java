package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVSetNickEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "SET_NICK";
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_SUCCESS = "success";
    private String message;
    private String nick;
    private String status;
    private String userId;

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "SET_NICK";
    }

    public String getMessage() {
        return this.message;
    }

    public String getNick() {
        return this.nick;
    }

    public String getStatus() {
        return this.status;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setNick(String str) {
        this.nick = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVSetNickEvent{message='" + this.message + CharPool.SINGLE_QUOTE + ", nick='" + this.nick + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + '}';
    }
}
