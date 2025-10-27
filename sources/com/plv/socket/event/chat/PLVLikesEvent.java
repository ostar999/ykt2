package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes5.dex */
public class PLVLikesEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "LIKES";
    private int count;
    private String nick;
    private PLVSocketUserBean user;
    private String userId;

    public int getCount() {
        return Math.max(this.count, 1);
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "LIKES";
    }

    public String getNick() {
        return this.nick;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setCount(int i2) {
        this.count = i2;
    }

    public void setNick(String str) {
        this.nick = str;
    }

    public void setUser(PLVSocketUserBean pLVSocketUserBean) {
        this.user = pLVSocketUserBean;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVLikesEvent{EVENT='LIKES', count=" + this.count + ", nick='" + this.nick + CharPool.SINGLE_QUOTE + ", user=" + this.user + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + '}';
    }
}
