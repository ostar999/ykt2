package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVCloseRoomEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "CLOSEROOM";
    private ValueBean value;

    public static class ValueBean {
        private boolean closed;
        private String roomId;

        public String getRoomId() {
            return this.roomId;
        }

        public boolean isClosed() {
            return this.closed;
        }

        public void setClosed(boolean z2) {
            this.closed = z2;
        }

        public void setRoomId(String str) {
            this.roomId = str;
        }

        public String toString() {
            return "ValueBean{closed=" + this.closed + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + '}';
        }
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "CLOSEROOM";
    }

    public ValueBean getValue() {
        return this.value;
    }

    public void setValue(ValueBean valueBean) {
        this.value = valueBean;
    }

    public String toString() {
        return "PLVCloseRoomEvent{EVENT='CLOSEROOM', value=" + this.value + '}';
    }
}
