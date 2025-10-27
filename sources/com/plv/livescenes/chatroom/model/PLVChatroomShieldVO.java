package com.plv.livescenes.chatroom.model;

import com.plv.socket.event.PLVEventConstant;

/* loaded from: classes4.dex */
public class PLVChatroomShieldVO extends PLVChatroomBaseActionVO {
    private String value;

    public PLVChatroomShieldVO(boolean z2) {
        setEVENT(z2 ? PLVEventConstant.Chatroom.MESSAGE_EVENT_SHIELD : PLVEventConstant.Chatroom.MESSAGE_EVENT_REMOVE_SHIELD);
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }
}
