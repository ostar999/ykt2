package com.plv.livescenes.chatroom;

import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVCloseRoomEvent;

/* loaded from: classes4.dex */
class PLVChatRoomCloseOperation extends PLVChatRoomBaseOperation {
    public PLVChatRoomCloseOperation(PLVChatroomManager pLVChatroomManager) {
        super(pLVChatroomManager, "CLOSEROOM");
    }

    @Override // com.plv.livescenes.chatroom.PLVChatRoomBaseOperation
    public void operate(String str, String str2) {
        PLVChatroomManager pLVChatroomManager;
        PLVCloseRoomEvent pLVCloseRoomEvent = (PLVCloseRoomEvent) PLVEventHelper.toEventModel(str, this.event, str2, PLVCloseRoomEvent.class);
        if (pLVCloseRoomEvent == null || pLVCloseRoomEvent.getValue() == null || (pLVChatroomManager = this.chatroomManager) == null) {
            return;
        }
        pLVChatroomManager.isReceiveCloseRoomEvent = true;
        pLVChatroomManager.isCloseRoom = pLVCloseRoomEvent.getValue().isClosed();
        PLVChatroomManager pLVChatroomManager2 = this.chatroomManager;
        pLVChatroomManager2.callRoomStatusListenerStatus(pLVChatroomManager2.isCloseRoom);
    }
}
