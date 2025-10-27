package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class MyMessageBean implements Serializable {
    private String code;
    private List<DataBean> data = new ArrayList();
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String content;
        private String img;
        private String jpush_type;
        private String message_id;
        private TarGetParamBean target_params;
        private String timestr;
        private String title;
        private String to_from;
        private int to_status;

        public String getContent() {
            return this.content;
        }

        public String getImg() {
            return this.img;
        }

        public String getJpush_type() {
            return this.jpush_type;
        }

        public String getMessage_id() {
            return this.message_id;
        }

        public TarGetParamBean getTarget_params() {
            return this.target_params;
        }

        public String getTimestr() {
            return this.timestr;
        }

        public String getTitle() {
            return this.title;
        }

        public String getTo_from() {
            return this.to_from;
        }

        public int getTo_status() {
            return this.to_status;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public void setJpush_type(String jpush_type) {
            this.jpush_type = jpush_type;
        }

        public void setMessage_id(String message_id) {
            this.message_id = message_id;
        }

        public void setTarget_params(TarGetParamBean target_params) {
            this.target_params = target_params;
        }

        public void setTimestr(String timestr) {
            this.timestr = timestr;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTo_from(String to_from) {
            this.to_from = to_from;
        }

        public void setTo_status(int to_status) {
            this.to_status = to_status;
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
