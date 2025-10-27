package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes5.dex */
public class MessageNoticeTypeBean {
    private int code;
    private MessageNoticeTypeDataBean data;
    private String message;
    private int server_time;

    public class MessageNoticeTypeDataBean {
        private String chat;
        private String comment;

        @SerializedName("course_update")
        private String courseUpdate;
        private String friends;
        private String live;
        private String official_survey;
        private String praise;
        private String service;
        private String system;

        public MessageNoticeTypeDataBean() {
        }

        public String getChat() {
            return this.chat;
        }

        public String getComment() {
            return this.comment;
        }

        public String getCourseUpdate() {
            return this.courseUpdate;
        }

        public String getFriends() {
            return this.friends;
        }

        public String getLive() {
            return this.live;
        }

        public String getOfficial_survey() {
            return this.official_survey;
        }

        public String getPraise() {
            return this.praise;
        }

        public String getService() {
            return this.service;
        }

        public String getSystem() {
            return this.system;
        }

        public void setChat(String chat) {
            this.chat = chat;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setCourseUpdate(String courseUpdate) {
            this.courseUpdate = courseUpdate;
        }

        public void setFriends(String friends) {
            this.friends = friends;
        }

        public void setLive(String live) {
            this.live = live;
        }

        public void setOfficial_survey(String official_survey) {
            this.official_survey = official_survey;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public void setService(String service) {
            this.service = service;
        }

        public void setSystem(String system) {
            this.system = system;
        }
    }

    public int getCode() {
        return this.code;
    }

    public MessageNoticeTypeDataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getServer_time() {
        return this.server_time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(MessageNoticeTypeDataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }
}
