package com.plv.livescenes.chatroom;

import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.login.PLVLoginEvent;

/* loaded from: classes4.dex */
class PLVChatRoomLoginOperation extends PLVChatRoomBaseOperation {
    public PLVChatRoomLoginOperation(PLVChatroomManager pLVChatroomManager) {
        super(pLVChatroomManager, "LOGIN");
    }

    @Override // com.plv.livescenes.chatroom.PLVChatRoomBaseOperation
    public void operate(String str, String str2) {
        PLVLoginEvent pLVLoginEvent = (PLVLoginEvent) PLVEventHelper.toEventModel(str, this.event, str2, PLVLoginEvent.class);
        if (pLVLoginEvent != null) {
            int onlineUserNumber = pLVLoginEvent.getOnlineUserNumber();
            PLVChatroomManager pLVChatroomManager = this.chatroomManager;
            if (pLVChatroomManager != null) {
                pLVChatroomManager.setOnlineCount(onlineUserNumber);
            }
        }
    }
}
