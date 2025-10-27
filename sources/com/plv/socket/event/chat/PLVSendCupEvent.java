package com.plv.socket.event.chat;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVSendCupEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "SEND_CUP";
    private OwnerBean owner;
    private String roomId;
    private String sessionId;

    public static class OwnerBean {
        private String nick;
        private int num;
        private String userId;

        public String getNick() {
            return this.nick;
        }

        public int getNum() {
            return this.num;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setNick(String str) {
            this.nick = str;
        }

        public void setNum(int i2) {
            this.num = i2;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public String toString() {
            return "OwnerBean{userId='" + this.userId + CharPool.SINGLE_QUOTE + ", nick='" + this.nick + CharPool.SINGLE_QUOTE + ", num=" + this.num + '}';
        }
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "SEND_CUP";
    }

    public OwnerBean getOwner() {
        return this.owner;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setOwner(OwnerBean ownerBean) {
        this.owner = ownerBean;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public String toString() {
        return "PLVSendCupEvent{EVENT='SEND_CUP', roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", owner=" + this.owner + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + '}';
    }
}
