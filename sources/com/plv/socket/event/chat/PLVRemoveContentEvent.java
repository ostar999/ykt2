package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVRemoveContentEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "REMOVE_CONTENT";
    private String id;
    private String roomId;

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "REMOVE_CONTENT";
    }

    public String getId() {
        return this.id;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public String toString() {
        return "PLVRemoveContentEvent{EVENT='REMOVE_CONTENT', id='" + this.id + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + '}';
    }
}
