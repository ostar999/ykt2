package com.plv.socket.event.login;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVLogoutEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "LOGOUT";
    private int onlineUserNumber;
    private String uid;
    private String userId;

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "LOGOUT";
    }

    public int getOnlineUserNumber() {
        return this.onlineUserNumber;
    }

    public String getUid() {
        return this.uid;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setOnlineUserNumber(int i2) {
        this.onlineUserNumber = i2;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVLogoutEvent{EVENT='LOGOUT', onlineUserNumber=" + this.onlineUserNumber + ", uid='" + this.uid + CharPool.SINGLE_QUOTE + '}';
    }
}
