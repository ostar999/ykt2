package com.plv.business.model.ppt;

import com.plv.business.model.PLVBaseVO;

/* loaded from: classes4.dex */
public class PLVPPTInfo implements PLVBaseVO {
    private String content;
    private int id;
    private String roomId;
    private String sessionId;
    private long time;

    public String getContent() {
        return this.content;
    }

    public int getId() {
        return this.id;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public long getTime() {
        return this.time;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setId(int i2) {
        this.id = i2;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setTime(long j2) {
        this.time = j2;
    }
}
