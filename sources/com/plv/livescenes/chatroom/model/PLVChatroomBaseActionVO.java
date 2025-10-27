package com.plv.livescenes.chatroom.model;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVChatroomBaseActionVO {
    public static final String TYPE_IP = "ip";
    public static final String TYPE_USERID = "userId";
    private String EVENT;
    private String channelId;
    private String roomId;
    private String sign;
    private String type;

    public String getChannelId() {
        return this.channelId;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getSign() {
        return this.sign;
    }

    public String getType() {
        return this.type;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setSign(String str) {
        this.sign = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public String toString() {
        return "PLVChatroomBaseActionVO{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", sign='" + this.sign + CharPool.SINGLE_QUOTE + '}';
    }
}
