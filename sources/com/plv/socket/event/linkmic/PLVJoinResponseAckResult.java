package com.plv.socket.event.linkmic;

/* loaded from: classes5.dex */
public class PLVJoinResponseAckResult {
    private String EVENT;
    private String msg;
    private boolean status;

    public String getEVENT() {
        return this.EVENT;
    }

    public String getMsg() {
        return this.msg;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public void setStatus(boolean z2) {
        this.status = z2;
    }
}
