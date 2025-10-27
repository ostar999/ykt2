package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class WXAuthEvent {
    private String code;

    public WXAuthEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
