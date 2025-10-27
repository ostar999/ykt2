package com.plv.socket.event.linkmic;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;
import com.plv.socket.event.PLVEventHelper;

/* loaded from: classes5.dex */
public class PLVJoinLeaveSEvent implements PLVFoundationVO {
    private String roomId;
    private String token;
    private UserBean user;

    public static class UserBean {
        private String nick;
        private String pic;
        private String userId = "";
        private String userType;

        public String getNick() {
            return this.nick;
        }

        public String getPic() {
            return PLVEventHelper.fixChatPic(this.pic);
        }

        public String getUserId() {
            return this.userId;
        }

        public String getUserType() {
            return this.userType;
        }

        public void setNick(String str) {
            this.nick = str;
        }

        public void setPic(String str) {
            this.pic = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public void setUserType(String str) {
            this.userType = str;
        }

        public String toString() {
            return "UserBean{nick='" + this.nick + CharPool.SINGLE_QUOTE + ", pic='" + this.pic + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", userType='" + this.userType + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getToken() {
        return this.token;
    }

    public UserBean getUser() {
        return this.user;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public void setUser(UserBean userBean) {
        this.user = userBean;
    }

    public String toString() {
        return "PLVJoinLeaveSEvent{roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", user=" + this.user + '}';
    }
}
