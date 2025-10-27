package com.plv.socket.event.chat;

import androidx.annotation.Nullable;
import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.event.ppt.PLVPptShareFileVO;
import com.plv.socket.user.PLVSocketUserBean;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVSpeakEvent extends PLVMessageBaseEvent implements IPLVIdEvent, IPLVManagerChatEvent, IPLVQuoteEvent {
    public static final String EVENT = "SPEAK";

    @Nullable
    private transient PLVPptShareFileVO fileData;
    private String id;
    private String message;
    private String msgSource;
    private PLVChatQuoteVO quote;
    private String source;
    private String status;
    private long time;
    private PLVSocketUserBean user;
    private String value;
    private List<String> values;

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "SPEAK";
    }

    public PLVPptShareFileVO getFileData() {
        return this.fileData;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public String getId() {
        return this.id;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMsgSource() {
        return this.msgSource;
    }

    @Override // com.plv.socket.event.chat.IPLVQuoteEvent
    public PLVChatQuoteVO getQuote() {
        return this.quote;
    }

    public String getSource() {
        return this.source;
    }

    public String getStatus() {
        return this.status;
    }

    public long getTime() {
        return this.time;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public String getValue() {
        return this.value;
    }

    public List<String> getValues() {
        return this.values;
    }

    public boolean isFileShareEvent() {
        return "file".equals(this.msgSource);
    }

    @Override // com.plv.socket.event.chat.IPLVManagerChatEvent
    public boolean isManagerChatMsg() {
        return "extend".equals(this.source);
    }

    public PLVSpeakEvent setFileData(PLVPptShareFileVO pLVPptShareFileVO) {
        this.fileData = pLVPptShareFileVO;
        return this;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public void setId(String str) {
        this.id = str;
    }

    @Override // com.plv.socket.event.chat.IPLVManagerChatEvent
    public void setIsManagerChatMsg(boolean z2) {
        if (z2) {
            this.source = "extend";
        } else {
            this.source = null;
        }
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public PLVSpeakEvent setMsgSource(String str) {
        this.msgSource = str;
        return this;
    }

    public void setQuote(PLVChatQuoteVO pLVChatQuoteVO) {
        this.quote = pLVChatQuoteVO;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setTime(long j2) {
        this.time = j2;
    }

    public void setUser(PLVSocketUserBean pLVSocketUserBean) {
        this.user = pLVSocketUserBean;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public void setValues(List<String> list) {
        this.values = list;
    }

    public String toString() {
        return "PLVSpeakEvent{id='" + this.id + CharPool.SINGLE_QUOTE + ", time=" + this.time + ", source='" + this.source + CharPool.SINGLE_QUOTE + ", msgSource='" + this.msgSource + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", value='" + this.value + CharPool.SINGLE_QUOTE + ", user=" + this.user + ", values=" + this.values + ", fileData=" + this.fileData + ", quote=" + this.quote + '}';
    }
}
