package com.plv.socket.event.chat;

import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.impl.PLVSocketManager;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes5.dex */
public class PLVChatEmotionEvent extends PLVMessageBaseEvent implements IPLVIdEvent {
    public static final String EVENT = "emotion";
    private String id;
    private String messageId;
    private String source;
    private Long time;
    private PLVSocketUserBean user;

    public PLVChatEmotionEvent() {
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "emotion";
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public String getId() {
        return this.id;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public String getSource() {
        return this.source;
    }

    public Long getTime() {
        return this.time;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public String getUserId() {
        PLVSocketUserBean pLVSocketUserBean = this.user;
        return pLVSocketUserBean != null ? pLVSocketUserBean.getUserId() : PLVSocketManager.getInstance().getLoginVO().getUserId();
    }

    public boolean isLocal() {
        return this.user == null;
    }

    public boolean isSpecialTypeOrMe() {
        PLVSocketUserBean pLVSocketUserBean = this.user;
        return pLVSocketUserBean == null || pLVSocketUserBean.getUserId() == null || PLVEventHelper.isSpecialType(this.user.getUserType()) || this.user.getUserId().equals(PLVSocketManager.getInstance().getLoginVO().getUserId());
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public void setId(String str) {
        this.id = str;
    }

    public void setMessageId(String str) {
        this.messageId = str;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public void setTime(Long l2) {
        this.time = l2;
    }

    public void setUser(PLVSocketUserBean pLVSocketUserBean) {
        this.user = pLVSocketUserBean;
    }

    public PLVChatEmotionEvent(String str) {
        this.id = str;
    }

    public PLVChatEmotionEvent(String str, String str2) {
        this.id = str;
        this.source = str2;
    }
}
