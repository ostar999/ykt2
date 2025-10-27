package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class SubjectiveListBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String answer_status;
        private String number;
        private String question_id;
        private String questionurl;
        private String subject_id;

        public String getAnswer_status() {
            return this.answer_status;
        }

        public String getNumber() {
            return this.number;
        }

        public String getQuestion_id() {
            return this.question_id;
        }

        public String getQuestionurl() {
            return this.questionurl;
        }

        public String getSubject_id() {
            return this.subject_id;
        }

        public void setAnswer_status(String answer_status) {
            this.answer_status = answer_status;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public void setQuestionurl(String questionurl) {
            this.questionurl = questionurl;
        }

        public void setSubject_id(String subject_id) {
            this.subject_id = subject_id;
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
