package com.plv.linkmic.model;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVLinkMicJoinSuccess implements PLVFoundationVO {
    private String roomId;
    private String sessionId;
    private UserBean user;

    public static class UserBean {
        private String nick;
        private String pic;
        private String sessionId;
        private String userId;
        private String userType;

        public String getNick() {
            return this.nick;
        }

        public String getPic() {
            return this.pic;
        }

        public String getSessionId() {
            return this.sessionId;
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

        public void setSessionId(String str) {
            this.sessionId = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public void setUserType(String str) {
            this.userType = str;
        }
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public UserBean getUser() {
        return this.user;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setUser(UserBean userBean) {
        this.user = userBean;
    }
}
