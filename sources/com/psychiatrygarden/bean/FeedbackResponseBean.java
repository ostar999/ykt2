package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class FeedbackResponseBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;

    public class DataBean {
        private String avatar;
        private String content;
        private String date;
        private String image;
        private String nickname;
        private String type;
        private String user_id;

        public DataBean() {
        }

        public String getAvatar() {
            return this.avatar;
        }

        public String getContent() {
            return this.content;
        }

        public String getDate() {
            return this.date;
        }

        public String getImage() {
            return this.image;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getType() {
            return this.type;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setType(String type) {
            this.type = type;
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
}
