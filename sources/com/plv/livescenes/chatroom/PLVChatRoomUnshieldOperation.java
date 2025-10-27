package com.plv.livescenes.chatroom;

import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVUnshieldEvent;
import com.plv.socket.user.PLVSocketUserBean;
import java.util.Iterator;

/* loaded from: classes4.dex */
class PLVChatRoomUnshieldOperation extends PLVChatRoomBaseOperation {
    public PLVChatRoomUnshieldOperation(PLVChatroomManager pLVChatroomManager) {
        super(pLVChatroomManager, "UNSHIELD");
    }

    @Override // com.plv.livescenes.chatroom.PLVChatRoomBaseOperation
    public void operate(String str, String str2) {
        PLVUnshieldEvent pLVUnshieldEvent = (PLVUnshieldEvent) PLVEventHelper.toEventModel(str, this.event, str2, PLVUnshieldEvent.class);
        if (pLVUnshieldEvent == null || pLVUnshieldEvent.getUserIds() == null) {
            return;
        }
        Iterator<PLVSocketUserBean> it = pLVUnshieldEvent.getUserIds().iterator();
        while (it.hasNext()) {
            if (PLVSocketWrapper.getInstance().getLoginVO().getUserId().equals(it.next().getUserId())) {
                PLVChatroomManager pLVChatroomManager = this.chatroomManager;
                if (pLVChatroomManager != null) {
                    pLVChatroomManager.isBanIp = false;
                    return;
                }
                return;
            }
        }
    }
}
