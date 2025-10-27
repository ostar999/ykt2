package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionAnsDataBEan implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String answer;
        private String question_id;
        private String re_do = "0";
        private String utime;

        public String getAnswer() {
            return this.answer;
        }

        public String getQuestion_id() {
            return this.question_id;
        }

        public String getRe_do() {
            return this.re_do;
        }

        public String getUtime() {
            return this.utime;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public void setRe_do(String re_do) {
            this.re_do = re_do;
        }

        public void setUtime(String utime) {
            this.utime = utime;
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
