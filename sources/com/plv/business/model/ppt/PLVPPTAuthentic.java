package com.plv.business.model.ppt;

import com.plv.business.model.PLVBaseVO;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class PLVPPTAuthentic implements PLVBaseVO {
    public static final String TYPE_AUDIO = "audio";
    public static final String TYPE_PAINT = "paint";
    public static final String TYPE_SPEAKER = "speaker";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_VOICE = "voice";
    private String EVENT;
    private int emitMode;
    private String roomId;
    private String sessionId;
    private String sign;
    private String status;
    private String type;
    private String userId;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionStatus {
        public static final String NO = "0";
        public static final String OK = "1";
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionType {
        public static final String AUDIO = "audio";
        public static final String BANNED = "banned";
        public static final String CAMERA = "camera";
        public static final String MICROPHONE = "microphone";
        public static final String PAINT = "paint";
        public static final String SCREEN_SHARE = "screenShare";
        public static final String TEACHER = "speaker";
        public static final String VIDEO = "video";
        public static final String VOICE = "voice";
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public int getEmitMode() {
        return this.emitMode;
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

    public boolean hasNoAthuentic() {
        return "0".equals(this.status);
    }

    public boolean hasPPTOrAboveType() {
        return "paint".equals(this.type) || "speaker".equals(this.type);
    }

    public boolean hasTeacherAuthentic() {
        return "speaker".equals(this.type) && "1".equals(this.status);
    }

    public boolean hasVoicePermission() {
        return "voice".equals(getType()) && "1".equals(getStatus());
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setEmitMode(int i2) {
        this.emitMode = i2;
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

    public void setType(String str) {
        this.type = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}
