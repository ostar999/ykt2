package com.plv.livescenes.chatroom;

/* loaded from: classes4.dex */
abstract class PLVChatRoomBaseOperation {
    PLVChatroomManager chatroomManager;
    protected String event;

    public PLVChatRoomBaseOperation(PLVChatroomManager pLVChatroomManager, String str) {
        this.chatroomManager = pLVChatroomManager;
        this.event = str;
    }

    public abstract void operate(String str, String str2);
}
