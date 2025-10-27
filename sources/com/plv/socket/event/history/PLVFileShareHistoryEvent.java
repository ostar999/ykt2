package com.plv.socket.event.history;

import androidx.annotation.Nullable;
import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVBaseEventImpl;
import com.plv.socket.event.chat.IPLVIdEvent;
import com.plv.socket.event.chat.IPLVQuoteEvent;
import com.plv.socket.event.chat.PLVChatQuoteVO;
import com.plv.socket.event.ppt.PLVPptShareFileVO;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes5.dex */
public class PLVFileShareHistoryEvent extends PLVBaseEventImpl implements IPLVIdEvent, IPLVQuoteEvent {
    private String content;

    @Nullable
    private transient PLVPptShareFileVO fileData;
    private String id;
    private String msgSource;
    private PLVChatQuoteVO quote;
    private long time;
    private PLVSocketUserBean user;

    public String getContent() {
        return this.content;
    }

    public PLVPptShareFileVO getFileData() {
        return this.fileData;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public String getId() {
        return this.id;
    }

    public String getMsgSource() {
        return this.msgSource;
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

    public PLVFileShareHistoryEvent setFileData(PLVPptShareFileVO pLVPptShareFileVO) {
        this.fileData = pLVPptShareFileVO;
        return this;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public void setId(String str) {
        this.id = str;
    }

    public PLVFileShareHistoryEvent setMsgSource(String str) {
        this.msgSource = str;
        return this;
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
        return "PLVFileShareHistoryEvent{msgSource='" + this.msgSource + CharPool.SINGLE_QUOTE + ", content='" + this.content + CharPool.SINGLE_QUOTE + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", time=" + this.time + ", user=" + this.user + ", quote=" + this.quote + ", fileData=" + this.fileData + '}';
    }
}
