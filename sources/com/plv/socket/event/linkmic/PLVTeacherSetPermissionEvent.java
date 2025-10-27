package com.plv.socket.event.linkmic;

import cn.hutool.core.text.CharPool;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVMessageBaseEvent;

/* loaded from: classes5.dex */
public class PLVTeacherSetPermissionEvent extends PLVMessageBaseEvent {
    public static final String STATUS_ONE = "1";
    public static final String STATUS_ZERO = "0";
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_CUP = "cup";
    public static final String TYPE_PAINT = "paint";
    public static final String TYPE_RAISE_HAND = "raiseHand";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_VOICE = "voice";
    public static final String USER_ID_ALL = "all";
    public final String EVENT = PLVEventConstant.LinkMic.TEACHER_SET_PERMISSION;
    private int emitMode;
    private String emitUid;
    private int raiseHandCount;
    private int raiseHandTime;
    private String roomId;
    private String sessionId;
    private String sign;
    private String status;
    private boolean toAll;
    private String type;
    private String userId;

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return PLVEventConstant.LinkMic.TEACHER_SET_PERMISSION;
    }

    public int getEmitMode() {
        return this.emitMode;
    }

    public String getEmitUid() {
        return this.emitUid;
    }

    public int getRaiseHandCount() {
        return this.raiseHandCount;
    }

    public int getRaiseHandTime() {
        return this.raiseHandTime;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getSign() {
        return this.sign;
    }

    public String getStatus() {
        return this.status;
    }

    public String getType() {
        return this.type;
    }

    public String getUserId() {
        return this.userId;
    }

    public boolean isToAll() {
        return this.toAll;
    }

    public void setEmitMode(int i2) {
        this.emitMode = i2;
    }

    public void setEmitUid(String str) {
        this.emitUid = str;
    }

    public void setRaiseHandCount(int i2) {
        this.raiseHandCount = i2;
    }

    public void setRaiseHandTime(int i2) {
        this.raiseHandTime = i2;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setSign(String str) {
        this.sign = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setToAll(boolean z2) {
        this.toAll = z2;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVTeacherSetPermissionEvent{EVENT='TEACHER_SET_PERMISSION', roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + ", emitMode=" + this.emitMode + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", raiseHandTime=" + this.raiseHandTime + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", emitUid='" + this.emitUid + CharPool.SINGLE_QUOTE + ", sign='" + this.sign + CharPool.SINGLE_QUOTE + ", raiseHandCount=" + this.raiseHandCount + ", toAll=" + this.toAll + '}';
    }
}
