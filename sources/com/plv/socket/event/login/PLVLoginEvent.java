package com.plv.socket.event.login;

import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.user.PLVClassStatusBean;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes5.dex */
public class PLVLoginEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "LOGIN";
    private PLVClassStatusBean classStatus;
    private int onlineUserNumber;
    private long timeStamp;
    private PLVSocketUserBean user;

    public PLVClassStatusBean getClassStatus() {
        return this.classStatus;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "LOGIN";
    }

    public int getOnlineUserNumber() {
        return this.onlineUserNumber;
    }

    public long getTimeStamp() {
        return this.timeStamp;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public void setClassStatus(PLVClassStatusBean pLVClassStatusBean) {
        this.classStatus = pLVClassStatusBean;
    }

    public void setOnlineUserNumber(int i2) {
        this.onlineUserNumber = i2;
    }

    public void setTimeStamp(long j2) {
        this.timeStamp = j2;
    }

    public void setUser(PLVSocketUserBean pLVSocketUserBean) {
        this.user = pLVSocketUserBean;
    }

    public String toString() {
        return "PLVLoginEvent{onlineUserNumber=" + this.onlineUserNumber + ", timeStamp=" + this.timeStamp + ", user=" + this.user + ", classStatus=" + this.classStatus + '}';
    }
}
