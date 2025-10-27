package com.plv.livescenes.chatroom.send.custom;

import cn.hutool.core.text.CharPool;
import com.plv.livescenes.chatroom.event.PLVEventHelper;
import com.plv.socket.user.PLVAuthorizationBean;

/* loaded from: classes4.dex */
public class PLVCustomEvent<DataBean> extends PLVBaseCustomEvent<DataBean> {
    public static final boolean JOIN_HISTORY_FALSE = false;
    public static final boolean JOIN_HISTORY_TRUE = true;
    public static final int VERSION_1 = 1;
    private String msgSource;
    private String msgType;
    private int roomId;
    private long time;
    private UserBean user;
    private int version;

    public static class UserBean {
        private String actor;
        private AuthorizationBean authorization;
        private boolean banned;
        private String channelId;
        private String clientIp;
        private String nick;
        private String pic;
        private String roomId;
        private String sessionId;
        private String uid;
        private String userId;
        private String userType;

        public static class AuthorizationBean {
            private String actor;
            private String bgColor;
            private String fColor;

            public String getActor() {
                return this.actor;
            }

            public String getBgColor() {
                return PLVAuthorizationBean.fitBgColor(this.bgColor);
            }

            public String getFColor() {
                return PLVAuthorizationBean.fitfColor(this.fColor);
            }

            public void setActor(String str) {
                this.actor = str;
            }

            public void setBgColor(String str) {
                this.bgColor = str;
            }

            public void setFColor(String str) {
                this.fColor = str;
            }

            public String toString() {
                return "AuthorizationBean{actor='" + this.actor + CharPool.SINGLE_QUOTE + ", bgColor='" + this.bgColor + CharPool.SINGLE_QUOTE + ", fColor='" + this.fColor + CharPool.SINGLE_QUOTE + '}';
            }
        }

        public String getActor() {
            return PLVEventHelper.fitActor(this.actor, this.userType);
        }

        public AuthorizationBean getAuthorization() {
            return this.authorization;
        }

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

        public String getRoomId() {
            return this.roomId;
        }

        public String getSessionId() {
            return this.sessionId;
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

        public void setActor(String str) {
            this.actor = str;
        }

        public void setAuthorization(AuthorizationBean authorizationBean) {
            this.authorization = authorizationBean;
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

        public void setRoomId(String str) {
            this.roomId = str;
        }

        public void setSessionId(String str) {
            this.sessionId = str;
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
            return "UserBean{authorization=" + this.authorization + ", actor='" + this.actor + CharPool.SINGLE_QUOTE + ", banned=" + this.banned + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", clientIp='" + this.clientIp + CharPool.SINGLE_QUOTE + ", nick='" + this.nick + CharPool.SINGLE_QUOTE + ", pic='" + this.pic + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + ", uid='" + this.uid + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", userType='" + this.userType + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public PLVCustomEvent(String str, DataBean databean) {
        super(str, databean);
    }

    public String getMsgSource() {
        return this.msgSource;
    }

    public String getMsgType() {
        return this.msgType;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public long getTime() {
        return this.time;
    }

    public UserBean getUser() {
        return this.user;
    }

    public int getVersion() {
        return this.version;
    }

    public void setMsgSource(String str) {
        this.msgSource = str;
    }

    public void setMsgType(String str) {
        this.msgType = str;
    }

    public void setRoomId(int i2) {
        this.roomId = i2;
    }

    public void setTime(long j2) {
        this.time = j2;
    }

    public void setUser(UserBean userBean) {
        this.user = userBean;
    }

    public void setVersion(int i2) {
        this.version = i2;
    }

    @Override // com.plv.livescenes.chatroom.send.custom.PLVBaseCustomEvent, com.plv.livescenes.chatroom.PLVBaseHolder
    public String toString() {
        return "PLVCustomEvent{version=" + this.version + ", roomId=" + this.roomId + ", id='" + this.id + CharPool.SINGLE_QUOTE + ", user=" + this.user + ", time=" + this.time + ", msgSource='" + this.msgSource + CharPool.SINGLE_QUOTE + ", msgType='" + this.msgType + CharPool.SINGLE_QUOTE + ", EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", tip='" + this.tip + CharPool.SINGLE_QUOTE + ", emitMode=" + this.emitMode + ", data=" + this.data + '}';
    }

    public PLVCustomEvent(String str, String str2, DataBean databean) {
        super(str, str2, databean);
    }

    public PLVCustomEvent(String str, int i2, DataBean databean) {
        super(str, i2, databean);
    }

    public PLVCustomEvent(String str, int i2, String str2, DataBean databean) {
        super(str, i2, str2, databean);
    }

    public PLVCustomEvent(String str, int i2, String str2, DataBean databean, boolean z2) {
        super(str, i2, str2, databean, z2);
    }
}
