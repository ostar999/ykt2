package com.plv.livescenes.chatroom.model;

import com.plv.socket.event.PLVEventConstant;

/* loaded from: classes4.dex */
public class PLVChatroomUnKickVO extends PLVChatroomBaseActionVO {
    private String userId;
    private String value;

    public PLVChatroomUnKickVO() {
        setEVENT(PLVEventConstant.MESSAGE_EVENT_UNKICK);
    }

    public String getUserId() {
        return this.userId;
    }

    public String getValue() {
        return this.value;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
