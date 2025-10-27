package com.plv.socket.event.seminar;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVJoinSuccessEvent implements PLVFoundationVO {
    private String roomId;

    public String getRoomId() {
        return this.roomId;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public String toString() {
        return "PLVJoinSuccessEvent{roomId='" + this.roomId + CharPool.SINGLE_QUOTE + '}';
    }
}
