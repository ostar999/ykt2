package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class StatDataBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;

    public static class DataBean implements Serializable {
        private String comment_count;
        private String question_id;
        private int right_count;
        private int wrong_count;

        public String getComment_count() {
            return this.comment_count;
        }

        public String getQuestion_id() {
            return this.question_id;
        }

        public int getRight_count() {
            return this.right_count;
        }

        public int getWrong_count() {
            return this.wrong_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public void setRight_count(int right_count) {
            this.right_count = right_count;
        }

        public void setWrong_count(int wrong_count) {
            this.wrong_count = wrong_count;
        }
    }

    public String getCode() {
        return this.code;
    }

    public DataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
