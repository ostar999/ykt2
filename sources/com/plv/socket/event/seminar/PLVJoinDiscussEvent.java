package com.plv.socket.event.seminar;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;
import com.plv.socket.event.PLVEventConstant;

/* loaded from: classes5.dex */
public class PLVJoinDiscussEvent implements PLVFoundationVO {
    private final String EVENT = PLVEventConstant.Seminar.EVENT_JOIN_DISCUSS;
    private String groupId;

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String str) {
        this.groupId = str;
    }

    public String toString() {
        return "PLVJoinDiscussEvent{EVENT='joinDiscuss', groupId='" + this.groupId + CharPool.SINGLE_QUOTE + '}';
    }
}
