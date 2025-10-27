package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVCustomerMessageEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "CUSTOMER_MESSAGE";
    private String content;
    private String image;
    private int roomId;

    public String getContent() {
        return this.content;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "CUSTOMER_MESSAGE";
    }

    public String getImage() {
        return this.image;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public void setRoomId(int i2) {
        this.roomId = i2;
    }

    public String toString() {
        return "PLVCustomerMessageEvent{EVENT='CUSTOMER_MESSAGE', roomId=" + this.roomId + ", image='" + this.image + CharPool.SINGLE_QUOTE + ", content='" + this.content + CharPool.SINGLE_QUOTE + '}';
    }
}
