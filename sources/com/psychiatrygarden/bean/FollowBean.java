package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class FollowBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String user_id = "";
        private String nickname = "";
        private String avatar = "";
        private String is_follow = "";
        private String follow_user = "";
        private String to_follow_user = "";
        private String is_vip = "";

        public String getAvatar() {
            return this.avatar;
        }

        public String getFollow_user() {
            return this.follow_user;
        }

        public String getIs_follow() {
            return this.is_follow;
        }

        public String getIs_vip() {
            return this.is_vip;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getTo_follow_user() {
            return this.to_follow_user;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setFollow_user(String follow_user) {
            this.follow_user = follow_user;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setTo_follow_user(String to_follow_user) {
            this.to_follow_user = to_follow_user;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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
