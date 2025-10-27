package com.plv.business.model.ppt;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVChangePPTVideoPositionVO {
    private String EVENT;
    private String roomId;
    private String sessionId;
    private Integer status;

    public String getEVENT() {
        return this.EVENT;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public PLVChangePPTVideoPositionVO setEVENT(String str) {
        this.EVENT = str;
        return this;
    }

    public PLVChangePPTVideoPositionVO setRoomId(String str) {
        this.roomId = str;
        return this;
    }

    public PLVChangePPTVideoPositionVO setSessionId(String str) {
        this.sessionId = str;
        return this;
    }

    public PLVChangePPTVideoPositionVO setStatus(Integer num) {
        this.status = num;
        return this;
    }

    public String toString() {
        return "PLVChangePPTVideoPositionVO{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + '}';
    }
}
