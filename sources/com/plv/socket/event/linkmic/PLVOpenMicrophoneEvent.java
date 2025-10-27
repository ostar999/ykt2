package com.plv.socket.event.linkmic;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVOpenMicrophoneEvent implements PLVFoundationVO {
    public static final String STATUS_CLOSE = "close";
    public static final String STATUS_OPEN = "open";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_VIDEO = "video";
    private String EVENT;
    private String roomId;
    private String status;
    private String teacherId;
    private String type;
    private String userId;

    public String getEVENT() {
        return this.EVENT;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getStatus() {
        return this.status;
    }

    public String getTeacherId() {
        return this.teacherId;
    }

    public String getType() {
        return this.type;
    }

    public String getUserId() {
        return this.userId;
    }

    public boolean isMicrophoneEvent() {
        return this.teacherId != null;
    }

    public boolean isStopLinkMicEvent() {
        return this.userId != null;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setTeacherId(String str) {
        this.teacherId = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVOpenMicrophoneEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", teacherId='" + this.teacherId + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + '}';
    }
}
