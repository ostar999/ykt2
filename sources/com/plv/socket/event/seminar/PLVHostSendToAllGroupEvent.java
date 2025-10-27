package com.plv.socket.event.seminar;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;
import com.plv.socket.event.PLVEventConstant;

/* loaded from: classes5.dex */
public class PLVHostSendToAllGroupEvent implements PLVFoundationVO {
    private final String EVENT = PLVEventConstant.Seminar.EVENT_HOST_SEND_TO_ALL_GROUP;
    private String content;
    private String groupId;

    public String getContent() {
        return this.content;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setGroupId(String str) {
        this.groupId = str;
    }

    public String toString() {
        return "PLVHostSendToAllGroupEvent{EVENT='hostSendToAllGroup', groupId='" + this.groupId + CharPool.SINGLE_QUOTE + ", content='" + this.content + CharPool.SINGLE_QUOTE + '}';
    }
}
