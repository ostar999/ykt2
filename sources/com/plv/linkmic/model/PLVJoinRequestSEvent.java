package com.plv.linkmic.model;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVJoinRequestSEvent implements PLVFoundationVO {
    public static final String STATUS_WAIT = "wait";
    private String roomId;
    private PLVJoinInfoEvent user;

    public String getRoomId() {
        return this.roomId;
    }

    public PLVJoinInfoEvent getUser() {
        return this.user;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setUser(PLVJoinInfoEvent pLVJoinInfoEvent) {
        this.user = pLVJoinInfoEvent;
    }

    public String toString() {
        return "PLVJoinRequestSEvent{roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", user=" + this.user + '}';
    }
}
