package com.plv.livescenes.chatroom.model;

/* loaded from: classes4.dex */
public class PLVChatroomKickVO extends PLVChatroomBaseActionVO {
    private String userId;

    public PLVChatroomKickVO() {
        setEVENT("KICK");
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}
