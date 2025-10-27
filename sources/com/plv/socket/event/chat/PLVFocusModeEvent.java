package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVFocusModeEvent {
    public static final String EVENT = "FOCUS_SPECIAL_SPEAK";
    private String status;

    public PLVFocusModeEvent() {
        this(false);
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isOpen() {
        return "Y".equals(this.status);
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVFocusModeEvent{status='" + this.status + CharPool.SINGLE_QUOTE + '}';
    }

    public PLVFocusModeEvent(boolean z2) {
        this.status = z2 ? "Y" : "N";
    }
}
