package com.plv.socket.event.seminar;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVSetLeaderEvent implements PLVFoundationVO {
    private String EVENT;
    private String groupId;
    private String nick;
    private String userId;

    public String getEVENT() {
        return this.EVENT;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getNick() {
        return this.nick;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setGroupId(String str) {
        this.groupId = str;
    }

    public void setNick(String str) {
        this.nick = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVSetLeaderEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", groupId='" + this.groupId + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", nick='" + this.nick + CharPool.SINGLE_QUOTE + '}';
    }
}
