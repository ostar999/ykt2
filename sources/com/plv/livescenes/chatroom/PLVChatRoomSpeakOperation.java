package com.plv.livescenes.chatroom;

import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVSpeakEvent;

/* loaded from: classes4.dex */
class PLVChatRoomSpeakOperation extends PLVChatRoomBaseOperation {
    public PLVChatRoomSpeakOperation(PLVChatroomManager pLVChatroomManager) {
        super(pLVChatroomManager, "SPEAK");
    }

    @Override // com.plv.livescenes.chatroom.PLVChatRoomBaseOperation
    public void operate(String str, String str2) {
        PLVChatroomManager pLVChatroomManager;
        PLVSpeakEvent pLVSpeakEvent = (PLVSpeakEvent) PLVEventHelper.toEventModel(str, this.event, str2, PLVSpeakEvent.class);
        if (pLVSpeakEvent == null || pLVSpeakEvent.getUser() != null || (pLVChatroomManager = this.chatroomManager) == null || pLVChatroomManager.prohibitedWordListener == null || !"error".equals(pLVSpeakEvent.getStatus())) {
            return;
        }
        this.chatroomManager.prohibitedWordListener.onSendProhibitedWord(pLVSpeakEvent.getValue(), pLVSpeakEvent.getMessage(), "error");
    }
}
