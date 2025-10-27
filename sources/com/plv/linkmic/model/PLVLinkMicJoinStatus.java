package com.plv.linkmic.model;

import android.text.TextUtils;
import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVLinkMicJoinStatus implements PLVFoundationVO {
    private List<PLVJoinInfoEvent> joinList;

    /* renamed from: master, reason: collision with root package name */
    private String f10770master;
    private String microphoneStatus;
    private String type;
    private List<WaitListBean> waitList;

    public static class WaitListBean {
        private String actor;
        private String clientIp;
        private int cupNum;
        private String loginId;
        private String nick;
        private String pic;
        private String roomId;
        private String sessionId;
        private String status;
        private String uid;
        private String userId;
        private String userType;

        public String getActor() {
            return this.actor;
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

        public void setActor(String str) {
            this.actor = str;
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

        public void setNick(String str) {
            this.nick = str;
        }

        public void setPic(String str) {
            this.pic = str;
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
    }

    public List<PLVJoinInfoEvent> getJoinList() {
        return this.joinList;
    }

    public String getMaster() {
        return this.f10770master;
    }

    public String getMicrophoneStatus() {
        return this.microphoneStatus;
    }

    public String getType() {
        return this.type;
    }

    public List<WaitListBean> getWaitList() {
        return this.waitList;
    }

    public void setJoinList(List<PLVJoinInfoEvent> list) {
        this.joinList = list;
    }

    public void setMaster(String str) {
        this.f10770master = str;
    }

    public void setMicrophoneStatus(String str) {
        this.microphoneStatus = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setWaitList(List<WaitListBean> list) {
        this.waitList = list;
    }

    public String toString() {
        return "PLVLinkMicJoinStatus{master='" + this.f10770master + CharPool.SINGLE_QUOTE + ", microphoneStatus='" + this.microphoneStatus + CharPool.SINGLE_QUOTE + ", type='" + this.type + CharPool.SINGLE_QUOTE + ", joinList=" + this.joinList + ", waitList=" + this.waitList + '}';
    }
}
