package com.plv.socket.event.linkmic;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVMuteUserMediaEvent implements PLVFoundationVO {
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_VIDEO = "video";
    private String EVENT;
    private boolean mute;
    private String sessionId;
    private String teacherId;
    private String type;
    private String uid;
    private String userId;

    public String getEVENT() {
        return this.EVENT;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getTeacherId() {
        return this.teacherId;
    }

    public String getType() {
        return this.type;
    }

    public String getUid() {
        return this.uid;
    }

    public String getUserId() {
        return this.userId;
    }

    public boolean isMute() {
        return this.mute;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setMute(boolean z2) {
        this.mute = z2;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setTeacherId(String str) {
        this.teacherId = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVMuteUserMediaEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", mute=" + this.mute + ", teacherId='" + this.teacherId + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", uid='" + this.uid + CharPool.SINGLE_QUOTE + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + '}';
    }
}
