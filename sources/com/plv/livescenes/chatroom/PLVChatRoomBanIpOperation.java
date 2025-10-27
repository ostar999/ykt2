package com.plv.livescenes.chatroom;

import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVBanIpEvent;
import com.plv.socket.user.PLVSocketUserBean;
import java.util.Iterator;

/* loaded from: classes4.dex */
class PLVChatRoomBanIpOperation extends PLVChatRoomBaseOperation {
    public PLVChatRoomBanIpOperation(PLVChatroomManager pLVChatroomManager) {
        super(pLVChatroomManager, "BANIP");
    }

    @Override // com.plv.livescenes.chatroom.PLVChatRoomBaseOperation
    public void operate(String str, String str2) {
        PLVBanIpEvent pLVBanIpEvent = (PLVBanIpEvent) PLVEventHelper.toEventModel(str, this.event, str2, PLVBanIpEvent.class);
        if (pLVBanIpEvent == null || pLVBanIpEvent.getUserIds() == null) {
            return;
        }
        Iterator<PLVSocketUserBean> it = pLVBanIpEvent.getUserIds().iterator();
        while (it.hasNext()) {
            if (PLVSocketWrapper.getInstance().getLoginVO().getUserId().equals(it.next().getUserId())) {
                PLVChatroomManager pLVChatroomManager = this.chatroomManager;
                if (pLVChatroomManager != null) {
                    pLVChatroomManager.isBanIp = true;
                    return;
                }
                return;
            }
        }
    }
}
