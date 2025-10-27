package com.aliyun.player.aliyunplayerbase.bean;

import cn.hutool.core.text.CharPool;

/* loaded from: classes2.dex */
public class AliyunUserInfo {
    private int code;
    private UserDataBean data;
    private String message;

    public static class UserDataBean {
        private String avatarUrl;
        private String gmtCreate;
        private String gmtModified;
        private String id;
        private String nickName;
        private String token;
        private String userId;

        public String getAvatarUrl() {
            return this.avatarUrl;
        }

        public String getGmtCreate() {
            return this.gmtCreate;
        }

        public String getGmtModified() {
            return this.gmtModified;
        }

        public String getId() {
            return this.id;
        }

        public String getNickName() {
            return this.nickName;
        }

        public String getToken() {
            return this.token;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setAvatarUrl(String str) {
            this.avatarUrl = str;
        }

        public void setGmtCreate(String str) {
            this.gmtCreate = str;
        }

        public void setGmtModified(String str) {
            this.gmtModified = str;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setNickName(String str) {
            this.nickName = str;
        }

        public void setToken(String str) {
            this.token = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public String toString() {
            return "LittleUserInfo{id='" + this.id + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", nickName='" + this.nickName + CharPool.SINGLE_QUOTE + ", avatarUrl='" + this.avatarUrl + CharPool.SINGLE_QUOTE + ", gmtCreate='" + this.gmtCreate + CharPool.SINGLE_QUOTE + ", gmtModified='" + this.gmtModified + CharPool.SINGLE_QUOTE + ", token='" + this.token + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public int getCode() {
        return this.code;
    }

    public UserDataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(UserDataBean userDataBean) {
        this.data = userDataBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
