package com.plv.socket.event.history;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVBaseEventImpl;
import com.plv.socket.event.chat.IPLVIdEvent;
import com.plv.socket.event.chat.PLVChatImgContent;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes5.dex */
public class PLVChatImgHistoryEvent extends PLVBaseEventImpl implements IPLVIdEvent {
    private PLVChatImgContent content;
    private String id;
    private String msgSource;
    private long time;
    private PLVSocketUserBean user;

    public PLVChatImgContent getContent() {
        return this.content;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public String getId() {
        return this.id;
    }

    public String getMsgSource() {
        return this.msgSource;
    }

    public long getTime() {
        return this.time;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public void setContent(PLVChatImgContent pLVChatImgContent) {
        this.content = pLVChatImgContent;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public void setId(String str) {
        this.id = str;
    }

    public void setMsgSource(String str) {
        this.msgSource = str;
    }

    public void setTime(long j2) {
        this.time = j2;
    }

    public void setUser(PLVSocketUserBean pLVSocketUserBean) {
        this.user = pLVSocketUserBean;
    }

    public String toString() {
        return "PLVChatImgHistoryEvent{content=" + this.content + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", msgSource='" + this.msgSource + CharPool.SINGLE_QUOTE + ", time=" + this.time + ", user=" + this.user + '}';
    }
}
