package com.plv.livescenes.chatroom.playback;

import cn.hutool.core.text.CharPool;
import com.plv.livescenes.chatroom.PLVBaseHolder;
import com.plv.livescenes.chatroom.PLVChatUser;
import com.plv.socket.event.chat.IPLVQuoteEvent;
import com.plv.socket.event.chat.PLVChatQuoteVO;

/* loaded from: classes4.dex */
public class PLVChatPlaybackBase extends PLVBaseHolder implements IPLVQuoteEvent {
    String fontColor;
    String fontMode;
    String fontSize;
    int id;
    String msg;
    String msgType;
    String origin;
    Object param2;
    PLVChatQuoteVO quote;
    Object sessionId;
    String time;
    String timestamp;
    PLVChatUser user;

    public String getFontColor() {
        return this.fontColor;
    }

    public String getFontMode() {
        return this.fontMode;
    }

    public String getFontSize() {
        return this.fontSize;
    }

    public int getId() {
        return this.id;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getMsgType() {
        return this.msgType;
    }

    public String getOrigin() {
        return this.origin;
    }

    public Object getParam2() {
        return this.param2;
    }

    @Override // com.plv.socket.event.chat.IPLVQuoteEvent
    public PLVChatQuoteVO getQuote() {
        return this.quote;
    }

    public Object getSessionId() {
        return this.sessionId;
    }

    public String getTime() {
        return this.time;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public PLVChatUser getUser() {
        return this.user;
    }

    public void setFontColor(String str) {
        this.fontColor = str;
    }

    public void setFontMode(String str) {
        this.fontMode = str;
    }

    public void setFontSize(String str) {
        this.fontSize = str;
    }

    public void setId(int i2) {
        this.id = i2;
    }

    public void setMsg(String str) {
        this.msg = str;
    }

    public void setMsgType(String str) {
        this.msgType = str;
    }

    public void setOrigin(String str) {
        this.origin = str;
    }

    public void setParam2(Object obj) {
        this.param2 = obj;
    }

    public void setQuote(PLVChatQuoteVO pLVChatQuoteVO) {
        this.quote = pLVChatQuoteVO;
    }

    public void setSessionId(Object obj) {
        this.sessionId = obj;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }

    public void setUser(PLVChatUser pLVChatUser) {
        this.user = pLVChatUser;
    }

    @Override // com.plv.livescenes.chatroom.PLVBaseHolder
    public String toString() {
        return "PLVChatPlaybackBase{id=" + this.id + ", msg='" + this.msg + CharPool.SINGLE_QUOTE + ", time='" + this.time + CharPool.SINGLE_QUOTE + ", fontSize='" + this.fontSize + CharPool.SINGLE_QUOTE + ", fontMode='" + this.fontMode + CharPool.SINGLE_QUOTE + ", fontColor='" + this.fontColor + CharPool.SINGLE_QUOTE + ", timestamp='" + this.timestamp + CharPool.SINGLE_QUOTE + ", sessionId=" + this.sessionId + ", param2=" + this.param2 + ", msgType='" + this.msgType + CharPool.SINGLE_QUOTE + ", user=" + this.user + ", origin='" + this.origin + CharPool.SINGLE_QUOTE + ", quote=" + this.quote.toString() + '}';
    }
}
