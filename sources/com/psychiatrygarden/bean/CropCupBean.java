package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class CropCupBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String group_name;
        private List<UserBean> user;

        public static class UserBean implements Serializable {
            private String avatar;
            private String nickname;
            private int type = 2;
            private String user_id;

            public String getAvatar() {
                return this.avatar;
            }

            public String getNickname() {
                return this.nickname;
            }

            public int getType() {
                return this.type;
            }

            public String getUser_id() {
                return this.user_id;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }

        public String getGroup_name() {
            return this.group_name;
        }

        public List<UserBean> getUser() {
            return this.user;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public void setUser(List<UserBean> user) {
            this.user = user;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
