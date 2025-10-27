package com.plv.socket.event.ppt;

import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVOnSliceControlEvent extends PLVMessageBaseEvent {
    public static final String TYPE_CLOSE_CAMERA = "closeCamera";
    private String EVENT = "onSliceControl";
    private DataBean data;
    private String roomId;
    private String sessionId;

    public static class DataBean {
        private int isCamClosed;
        private long timeStamp;
        private String type;

        public int getIsCamClosed() {
            return this.isCamClosed;
        }

        public long getTimeStamp() {
            return this.timeStamp;
        }

        public String getType() {
            return this.type;
        }

        public void setIsCamClosed(int i2) {
            this.isCamClosed = i2;
        }

        public void setTimeStamp(long j2) {
            this.timeStamp = j2;
        }

        public void setType(String str) {
            this.type = str;
        }
    }

    public DataBean getData() {
        return this.data;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return this.EVENT;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }
}
