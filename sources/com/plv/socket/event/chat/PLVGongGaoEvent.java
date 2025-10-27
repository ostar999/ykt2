package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVGongGaoEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "GONGGAO";
    private String content;

    public String getContent() {
        return this.content;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "GONGGAO";
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String toString() {
        return "PLVGongGaoEvent{EVENT='GONGGAO', content='" + this.content + CharPool.SINGLE_QUOTE + '}';
    }
}
