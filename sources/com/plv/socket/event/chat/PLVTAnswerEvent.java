package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;
import com.plv.socket.user.PLVSocketUserBean;

/* loaded from: classes5.dex */
public class PLVTAnswerEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "T_ANSWER";
    public static final String TYPE_IMAGE = "image";
    private String content;
    private String msgType;
    private String roomId;
    private String s_userId;
    private Long time;
    private PLVSocketUserBean user;

    public String getContent() {
        return this.content;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "T_ANSWER";
    }

    public String getMsgType() {
        return this.msgType;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getS_userId() {
        return this.s_userId;
    }

    public Long getTime() {
        return this.time;
    }

    public PLVSocketUserBean getUser() {
        return this.user;
    }

    public String getUserId() {
        PLVSocketUserBean pLVSocketUserBean = this.user;
        if (pLVSocketUserBean == null) {
            return null;
        }
        return pLVSocketUserBean.getUserId();
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setMsgType(String str) {
        this.msgType = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setS_userId(String str) {
        this.s_userId = str;
    }

    public void setTime(Long l2) {
        this.time = l2;
    }

    public void setUser(PLVSocketUserBean pLVSocketUserBean) {
        this.user = pLVSocketUserBean;
    }

    public String toString() {
        return "PLVTAnswerEvent{content='" + this.content + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", s_userId='" + this.s_userId + CharPool.SINGLE_QUOTE + ", user=" + this.user + ", time=" + this.time + ", msgType='" + this.msgType + CharPool.SINGLE_QUOTE + '}';
    }
}
