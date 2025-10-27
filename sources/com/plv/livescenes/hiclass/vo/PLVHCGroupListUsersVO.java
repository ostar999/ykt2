package com.plv.livescenes.hiclass.vo;

import cn.hutool.core.text.CharPool;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVHCGroupListUsersVO {
    private Integer code;
    private List<DataBean> data;
    private String message;
    private String status;

    public static class DataBean {
        private String groupId;
        private String groupLeader;
        private List<ListBean> list;

        public static class ListBean {
            private Boolean banned;
            private String channelId;
            private String clientIp;
            private String nick;
            private String pic;
            private String roomId;
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
                return this.pic;
            }

            public String getRoomId() {
                return this.roomId;
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

            public Boolean isBanned() {
                return this.banned;
            }

            public void setBanned(Boolean bool) {
                this.banned = bool;
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
                return "ListBean{banned=" + this.banned + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + ", clientIp='" + this.clientIp + CharPool.SINGLE_QUOTE + ", nick='" + this.nick + CharPool.SINGLE_QUOTE + ", pic='" + this.pic + CharPool.SINGLE_QUOTE + ", roomId='" + this.roomId + CharPool.SINGLE_QUOTE + ", uid='" + this.uid + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", userType='" + this.userType + CharPool.SINGLE_QUOTE + '}';
            }
        }

        public String getGroupId() {
            return this.groupId;
        }

        public String getGroupLeader() {
            return this.groupLeader;
        }

        public List<ListBean> getList() {
            return this.list;
        }

        public void setGroupId(String str) {
            this.groupId = str;
        }

        public void setGroupLeader(String str) {
            this.groupLeader = str;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public String toString() {
            return "DataBean{groupId='" + this.groupId + CharPool.SINGLE_QUOTE + ", groupLeader='" + this.groupLeader + CharPool.SINGLE_QUOTE + ", list=" + this.list + '}';
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVHCGroupListUsersVO{status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", code=" + this.code + ", data=" + this.data + '}';
    }
}
