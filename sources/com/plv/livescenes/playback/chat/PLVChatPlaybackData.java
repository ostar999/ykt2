package com.plv.livescenes.playback.chat;

import com.plv.livescenes.chatroom.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVChatImgContent;
import com.plv.socket.event.chat.PLVChatQuoteVO;
import com.plv.thirdpart.litepal.annotation.Column;
import com.plv.thirdpart.litepal.crud.LitePalSupport;

/* loaded from: classes5.dex */
public class PLVChatPlaybackData extends LitePalSupport {
    private String actor;

    @Column(ignore = true)
    private PLVChatImgContent chatImgContent;

    @Column(ignore = true)
    private PLVChatQuoteVO chatQuoteVO;
    private String content;
    private int id;
    private String msgType;
    private String nick;

    @Column(ignore = true)
    private Object obj1;

    @Column(ignore = true)
    private Object obj2;

    @Column(ignore = true)
    private Object obj3;

    @Column(ignore = true)
    private Object obj4;

    @Column(ignore = true)
    private Object obj5;

    @Column(ignore = true)
    private Object[] objects;
    private String pic;
    private String quote;
    private int relativeTime;
    private String sessionId;
    private String userId;
    private String userType;

    public String getActor() {
        return this.actor;
    }

    public PLVChatImgContent getChatImgContent() {
        return this.chatImgContent;
    }

    public PLVChatQuoteVO getChatQuoteVO() {
        return this.chatQuoteVO;
    }

    public String getContent() {
        return this.content;
    }

    public int getId() {
        return this.id;
    }

    public String getMsgType() {
        return this.msgType;
    }

    public String getNick() {
        return this.nick;
    }

    public Object getObj1() {
        return this.obj1;
    }

    public Object getObj2() {
        return this.obj2;
    }

    public Object getObj3() {
        return this.obj3;
    }

    public Object getObj4() {
        return this.obj4;
    }

    public Object getObj5() {
        return this.obj5;
    }

    public Object[] getObjects() {
        return this.objects;
    }

    public String getPic() {
        return this.pic;
    }

    public String getQuote() {
        return this.quote;
    }

    public int getRelativeTime() {
        return this.relativeTime;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserType() {
        return this.userType;
    }

    public boolean isImgMsg() {
        return "chatImg".equals(this.msgType);
    }

    public void setActor(String str) {
        this.actor = str;
    }

    public void setChatImgContent(PLVChatImgContent pLVChatImgContent) {
        this.chatImgContent = pLVChatImgContent;
    }

    public void setChatQuoteVO(PLVChatQuoteVO pLVChatQuoteVO) {
        this.chatQuoteVO = pLVChatQuoteVO;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setMsgType(String str) {
        this.msgType = str;
    }

    public void setNick(String str) {
        this.nick = str;
    }

    public void setObj1(Object obj) {
        this.obj1 = obj;
    }

    public void setObj2(Object obj) {
        this.obj2 = obj;
    }

    public void setObj3(Object obj) {
        this.obj3 = obj;
    }

    public void setObj4(Object obj) {
        this.obj4 = obj;
    }

    public void setObj5(Object obj) {
        this.obj5 = obj;
    }

    public void setObjects(Object[] objArr) {
        this.objects = objArr;
    }

    public void setPic(String str) {
        this.pic = PLVEventHelper.fixChatPic(str);
    }

    public void setQuote(String str) {
        this.quote = str;
    }

    public void setRelativeTime(int i2) {
        this.relativeTime = i2;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setUserType(String str) {
        this.userType = str;
    }
}
