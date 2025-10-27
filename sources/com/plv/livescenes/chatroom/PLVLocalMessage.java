package com.plv.livescenes.chatroom;

import com.mobile.auth.gatewayauth.ResultCode;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.chat.IPLVIdEvent;
import com.plv.socket.event.chat.IPLVManagerChatEvent;
import com.plv.socket.event.chat.PLVChatQuoteVO;
import com.plv.socket.event.chat.PLVProhibitedWordVO;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVLocalMessage extends PLVBaseEvent implements IPLVIdEvent, IPLVManagerChatEvent {
    public static final int SENDVALUE_BANIP = -6;
    public static final int SENDVALUE_CLOSEROOM = -4;
    public static final int SENDVALUE_EXCEPTION = -2;
    public static final int SENDVALUE_ILLEGALPARAM = -7;
    public static final int SENDVALUE_NOONLINE = -3;
    public static final int SENDVALUE_PARAMNULL = -1;
    public static final int SENDVALUE_SUCCESS = 1;
    private String id;
    private PLVProhibitedWordVO prohibitedWord;
    private PLVChatQuoteVO quote;
    private String source;
    private String speakMessage;
    private Long time;

    @Retention(RetentionPolicy.SOURCE)
    public @interface SendValue {
    }

    public PLVLocalMessage(String str) {
        this.speakMessage = str;
    }

    public static final String sendValueToDescribe(int i2) {
        return i2 != -7 ? i2 != -6 ? i2 != -4 ? i2 != -3 ? i2 != -2 ? i2 != -1 ? i2 != 1 ? "unknown" : ResultCode.MSG_SUCCESS : "参数异常" : "内部异常" : "已离线" : "房间已关闭" : "已被禁言" : "非法参数";
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return null;
    }

    @Override // com.plv.socket.event.chat.IPLVIdEvent
    public String getId() {
        return this.id;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getListenEvent() {
        return null;
    }

    public PLVProhibitedWordVO getProhibitedWord() {
        return this.prohibitedWord;
    }

    public PLVChatQuoteVO getQuote() {
        return this.quote;
    }

    public String getSource() {
        return this.source;
    }

    public String getSpeakMessage() {
        return this.speakMessage;
    }

    public Long getTime() {
        return this.time;
    }

    public String getUserId() {
        return PLVSocketWrapper.getInstance().getLoginVO().getUserId();
    }

    @Override // com.plv.socket.event.chat.IPLVManagerChatEvent
    public boolean isManagerChatMsg() {
        return "extend".equals(this.source);
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
            this.source = "";
        }
    }

    public void setProhibitedWord(PLVProhibitedWordVO pLVProhibitedWordVO) {
        this.prohibitedWord = pLVProhibitedWordVO;
    }

    public void setQuote(PLVChatQuoteVO pLVChatQuoteVO) {
        this.quote = pLVChatQuoteVO;
    }

    public void setSource(String str) {
        this.source = str;
    }

    public void setSpeakMessage(String str) {
        this.speakMessage = str;
    }

    public void setTime(Long l2) {
        this.time = l2;
    }
}
