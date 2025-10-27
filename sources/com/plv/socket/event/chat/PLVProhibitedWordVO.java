package com.plv.socket.event.chat;

/* loaded from: classes5.dex */
public class PLVProhibitedWordVO {
    private String message;
    private String status;
    private String value;

    public PLVProhibitedWordVO(String str, String str2, String str3) {
        this.value = str;
        this.message = str2;
        this.status = str3;
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
}
