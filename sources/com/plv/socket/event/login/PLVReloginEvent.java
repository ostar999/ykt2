package com.plv.socket.event.login;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes5.dex */
public class PLVReloginEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "RELOGIN";
    private String channelId;
    private PLVSocketUserBean user;

    public String getChannelId() {
        return this.channelId;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "RELOGIN";
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setUser(PLVSocketUserBean pLVSocketUserBean) {
        this.user = pLVSocketUserBean;
    }

    public String toString() {
        return "PLVReloginEvent{EVENT='RELOGIN', channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", user=" + this.user + '}';
    }
}
