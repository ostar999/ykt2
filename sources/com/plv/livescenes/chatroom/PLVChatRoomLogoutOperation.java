package com.plv.livescenes.chatroom;

import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.login.PLVLogoutEvent;

/* loaded from: classes4.dex */
class PLVChatRoomLogoutOperation extends PLVChatRoomBaseOperation {
    public PLVChatRoomLogoutOperation(PLVChatroomManager pLVChatroomManager) {
        super(pLVChatroomManager, "LOGOUT");
    }

    @Override // com.plv.livescenes.chatroom.PLVChatRoomBaseOperation
    public void operate(String str, String str2) {
        PLVLogoutEvent pLVLogoutEvent = (PLVLogoutEvent) PLVEventHelper.toEventModel(str, this.event, str2, PLVLogoutEvent.class);
        if (pLVLogoutEvent != null) {
            int onlineUserNumber = pLVLogoutEvent.getOnlineUserNumber();
            PLVChatroomManager pLVChatroomManager = this.chatroomManager;
            if (pLVChatroomManager != null) {
                pLVChatroomManager.setOnlineCount(onlineUserNumber);
            }
        }
    }
}
