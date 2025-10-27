package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class MessageCenterBean implements Serializable {
    private int code;
    private List<MessageCenterItemBean> data;
    private String message;

    public class MessageCenterItemBean {
        private String content;
        private String count;
        private String date;
        private String title;
        private String type;

        public MessageCenterItemBean() {
        }

        public String getContent() {
            return this.content;
        }

        public String getCount() {
            return this.count;
        }

        public String getDate() {
            return this.date;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public int getCode() {
        return this.code;
    }

    public List<MessageCenterItemBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }
}
