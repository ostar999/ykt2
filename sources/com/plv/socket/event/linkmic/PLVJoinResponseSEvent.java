package com.plv.socket.event.linkmic;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;
import com.plv.socket.event.PLVEventHelper;

/* loaded from: classes5.dex */
public class PLVJoinResponseSEvent implements PLVFoundationVO {
    public static final int NEED_ANSWER_ONE = 1;
    public static final int NEED_ANSWER_ZERO = 0;
    private int needAnswer;
    private String roomId;
    private int toEmitAll;
    private UserBean user;
    private String value;

    public static class UserBean {
        private boolean banned;
        private String channelId;
        private String clientIp;
        private String nick;
        private String pic;
        private String uid;
        private String userId;
        private String userType;

        public String getChannelId() {
            return this.channelId;
        }

        public String getClientIp() {
            return this.clientIp;
        }

        public String getNick() {
            return this.nick;
        }

        public String getPic() {
            return PLVEventHelper.fixChatPic(this.pic);
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

        public void setBanned(boolean z2) {
            this.banned = z2;
        }

        public void setChannelId(String str) {
            this.channelId = str;
        }

        public void setClientIp(String str) {
            this.clientIp = str;
        }

        public void setNick(String str) {
            this.nick = str;
        }

        public void setPic(String str) {
            this.pic = str;
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
            return "UserBean{nick='" + this.nick + CharPool.SINGLE_QUOTE + ", pic='" + this.pic + CharPool.SINGLE_QUOTE + ", uid='" + this.uid + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", userType='" + this.userType + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public int getNeedAnswer() {
        return this.needAnswer;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public int getToEmitAll() {
        return this.toEmitAll;
    }

    public UserBean getUser() {
        return this.user;
    }

    public String getValue() {
        return this.value;
    }

    public boolean isNeedAnswer() {
        return 1 == this.needAnswer;
    }

    public void setNeedAnswer(int i2) {
        this.needAnswer = i2;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setToEmitAll(int i2) {
        this.toEmitAll = i2;
    }

    public void setUser(UserBean userBean) {
        this.user = userBean;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String toString() {
        return "PLVJoinResponseSEvent{roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", user=" + this.user + ", value='" + this.value + CharPool.SINGLE_QUOTE + '}';
    }
}
