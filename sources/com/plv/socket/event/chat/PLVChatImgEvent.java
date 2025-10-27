package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.user.PLVSocketUserBean;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVChatImgEvent extends PLVMessageBaseEvent implements IPLVIdEvent, IPLVManagerChatEvent {
    public static final String EVENT = "CHAT_IMG";
    private String id;
    private boolean result;
    private String source;
    private long time;
    private PLVSocketUserBean user;
    private List<PLVChatImgContent> values;

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "CHAT_IMG";
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public String getId() {
        return this.id;
    }

    public String getSource() {
        return this.source;
    }

    public long getTime() {
        return this.time;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public List<PLVChatImgContent> getValues() {
        return this.values;
    }

    @Override // com.plv.socket.event.chat.IPLVManagerChatEvent
    public boolean isManagerChatMsg() {
        return "extend".equals(this.source);
    }

    public boolean isResult() {
        return this.result;
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

    public void setResult(boolean z2) {
        this.result = z2;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public void setTime(long j2) {
        this.time = j2;
    }

    public void setUser(PLVSocketUserBean pLVSocketUserBean) {
        this.user = pLVSocketUserBean;
    }

    public void setValues(List<PLVChatImgContent> list) {
        this.values = list;
    }

    public String toString() {
        return "PLVChatImgEvent{id='" + this.id + CharPool.SINGLE_QUOTE + ", result=" + this.result + ", source='" + this.source + CharPool.SINGLE_QUOTE + ", time=" + this.time + ", user=" + this.user + ", values=" + this.values + '}';
    }
}
