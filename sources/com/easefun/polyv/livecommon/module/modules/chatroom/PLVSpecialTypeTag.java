package com.easefun.polyv.livecommon.module.modules.chatroom;

import com.plv.livescenes.socket.PLVSocketWrapper;

/* loaded from: classes3.dex */
public class PLVSpecialTypeTag {
    private String userId;

    public PLVSpecialTypeTag(String userId) {
        this.userId = userId;
    }

    public boolean isMySelf() {
        String str = this.userId;
        return str != null && str.equals(PLVSocketWrapper.getInstance().getLoginVO().getUserId());
    }
}
