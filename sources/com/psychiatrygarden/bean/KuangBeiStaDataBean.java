package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class KuangBeiStaDataBean implements Serializable {
    private String code;
    private List<DataBean> data = new ArrayList();
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private AnswerBean answer;
        private long question_id = 0;
        private int right_count = 0;
        private int wrong_count = 0;
        private int comment_count = 0;
        private int collection_count = 0;
        private String is_praise = "0";
        private String is_comment = "0";

        public static class AnswerBean {
            private int right_count = 0;
            private int wrong_count = 0;

            public int getRight_count() {
                return this.right_count;
            }

            public int getWrong_count() {
                return this.wrong_count;
            }

            public void setRight_count(int right_count) {
                this.right_count = right_count;
            }

            public void setWrong_count(int wrong_count) {
                this.wrong_count = wrong_count;
            }
        }

        public AnswerBean getAnswer() {
            return this.answer;
        }

        public int getCollection_count() {
            return this.collection_count;
        }

        public int getComment_count() {
            return this.comment_count;
        }

        public String getIs_comment() {
            return this.is_comment;
        }

        public String getIs_praise() {
            return this.is_praise;
        }

        public long getQuestion_id() {
            return this.question_id;
        }

        public int getRight_count() {
            return this.right_count;
        }

        public int getWrong_count() {
            return this.wrong_count;
        }

        public void setAnswer(AnswerBean answer) {
            this.answer = answer;
        }

        public void setCollection_count(int collection_count) {
            this.collection_count = collection_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public void setIs_comment(String is_comment) {
            this.is_comment = is_comment;
        }

        public void setIs_praise(String is_praise) {
            this.is_praise = is_praise;
        }

        public void setQuestion_id(long question_id) {
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
