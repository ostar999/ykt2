package com.plv.linkmic.model;

import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVJoinInfoEvent implements PLVFoundationVO {
    private String actor = "";
    private boolean banned;
    private String channelId;
    private ClassStatus classStatus;
    private String clientIp;
    private int cupNum;
    private String loginId;
    private boolean mute;
    private String nick;
    private String pic;
    private int pos;
    private String roomId;
    private String sessionId;
    private String status;
    private String uid;
    private String userId;
    private String userType;

    public static class ClassStatus {
        private int audio;
        private int banned;
        private int groupLeader;
        private int paint;
        private int speaker;
        private int voice;

        public int getBanned() {
            return this.banned;
        }

        public int getGroupLeader() {
            return this.groupLeader;
        }

        public boolean isAudio() {
            return this.audio == 1;
        }

        public boolean isBanned() {
            return 1 == this.banned;
        }

        public boolean isGroupLeader() {
            return 1 == this.groupLeader;
        }

        public boolean isPaint() {
            return this.paint == 1;
        }

        public boolean isSpeaker() {
            return this.speaker == 1;
        }

        public boolean isVoice() {
            return this.voice == 1;
        }

        public void setAudio(int i2) {
            this.audio = i2;
        }

        public void setBanned(int i2) {
            this.banned = i2;
        }

        public void setGroupLeader(int i2) {
            this.groupLeader = i2;
        }

        public void setPaint(int i2) {
            this.paint = i2;
        }

        public void setSpeaker(int i2) {
            this.speaker = i2;
        }

        public void setVoice(int i2) {
            this.voice = i2;
        }

        public String toString() {
            return "ClassStatus{groupLeader=" + this.groupLeader + ", banned=" + this.banned + ", paint=" + this.paint + ", speaker=" + this.speaker + ", voice=" + this.voice + ", audio=" + this.audio + '}';
        }
    }

    public String getActor() {
        return this.actor;
    }

    public String getChannelId() {
        return this.channelId;
    }

    public ClassStatus getClassStatus() {
        return this.classStatus;
    }

    public String getClientIp() {
        return this.clientIp;
    }

    public int getCupNum() {
        return this.cupNum;
    }

    public String getLoginId() {
        return TextUtils.isEmpty(this.loginId) ? this.userId : this.loginId;
    }

    public String getNick() {
        return this.nick;
    }

    public String getPic() {
        return this.pic;
    }

    public int getPos() {
        return this.pos;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getStatus() {
        return this.status;
    }

    public String getUid() {
        return this.uid;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserType() {
        return this.userType;
    }

    public boolean isBanned() {
        return this.banned;
    }

    public boolean isMute() {
        return this.mute;
    }

    public void setActor(String str) {
        this.actor = str;
    }

    public void setBanned(boolean z2) {
        this.banned = z2;
    }

    public void setChannelId(String str) {
        this.channelId = str;
    }

    public void setClassStatus(ClassStatus classStatus) {
        this.classStatus = classStatus;
    }

    public void setClientIp(String str) {
        this.clientIp = str;
    }

    public void setCupNum(int i2) {
        this.cupNum = i2;
    }

    public void setLoginId(String str) {
        this.loginId = str;
    }

    public void setMute(boolean z2) {
        this.mute = z2;
    }

    public void setNick(String str) {
        this.nick = str;
    }

    public void setPic(String str) {
        this.pic = str;
    }

    public void setPos(int i2) {
        this.pos = i2;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public void setUserType(String str) {
        this.userType = str;
    }

    public String toString() {
        return "PLVJoinInfoEvent{clientIp='" + this.clientIp + CharPool.SINGLE_QUOTE + ", nick='" + this.nick + CharPool.SINGLE_QUOTE + ", pic='" + this.pic + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", uid='" + this.uid + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", userType='" + this.userType + CharPool.SINGLE_QUOTE + ", classStatus=" + this.classStatus + ", loginId='" + this.loginId + CharPool.SINGLE_QUOTE + ", mute=" + this.mute + ", pos=" + this.pos + ", cupNum=" + this.cupNum + '}';
    }
}
