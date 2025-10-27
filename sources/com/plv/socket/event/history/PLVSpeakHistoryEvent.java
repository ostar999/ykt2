package com.plv.socket.event.history;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVBaseEventImpl;
import com.plv.socket.event.chat.IPLVIdEvent;
import com.plv.socket.event.chat.IPLVQuoteEvent;
import com.plv.socket.event.chat.PLVChatQuoteVO;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes5.dex */
public class PLVSpeakHistoryEvent extends PLVBaseEventImpl implements IPLVIdEvent, IPLVQuoteEvent {
    private String content;
    private String id;
    private PLVChatQuoteVO quote;
    private long time;
    private PLVSocketUserBean user;

    public String getContent() {
        return this.content;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public String getId() {
        return this.id;
    }

    @Override // com.plv.socket.event.chat.IPLVQuoteEvent
    public PLVChatQuoteVO getQuote() {
        return this.quote;
    }

    public long getTime() {
        return this.time;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public void setContent(String str) {
        this.content = str;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public void setId(String str) {
        this.id = str;
    }

    public void setQuote(PLVChatQuoteVO pLVChatQuoteVO) {
        this.quote = pLVChatQuoteVO;
    }

    public void setTime(long j2) {
        this.time = j2;
    }

    public void setUser(PLVSocketUserBean pLVSocketUserBean) {
        this.user = pLVSocketUserBean;
    }

    public String toString() {
        return "PLVSpeakHistoryEvent{content='" + this.content + CharPool.SINGLE_QUOTE + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", time=" + this.time + ", user=" + this.user + '}';
    }
}
