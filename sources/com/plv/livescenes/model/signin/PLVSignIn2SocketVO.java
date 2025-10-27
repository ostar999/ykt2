package com.plv.livescenes.model.signin;

import com.plv.livescenes.model.PLVInteractiveCallbackVO;

/* loaded from: classes5.dex */
public class PLVSignIn2SocketVO {
    private String EVENT = PLVInteractiveCallbackVO.EVENT_SIGN;
    private String checkinId;
    private String roomId;
    private UserBean user;

    public static class UserBean {
        private String nick;
        private String userId;

        public UserBean(String str, String str2) {
            this.nick = str;
            this.userId = str2;
        }

        public String getNick() {
            return this.nick;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setNick(String str) {
            this.nick = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }
    }

    public String getCheckinId() {
        return this.checkinId;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public UserBean getUser() {
        return this.user;
    }

    public void setCheckinId(String str) {
        this.checkinId = str;
    }

    public void setRoomId(String str) {
        this.roomId = str;
    }

    public void setUser(UserBean userBean) {
        this.user = userBean;
    }
}
