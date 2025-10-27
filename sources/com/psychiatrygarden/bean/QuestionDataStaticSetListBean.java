package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionDataStaticSetListBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String question_id;
        private int right_count = 0;
        private int wrong_count = 0;
        private int comment_count = 0;
        private int collection_count = 0;
        private int is_collection = 0;
        private int is_comment = 0;
        private int is_praise = 0;
        private int is_note = 0;
        private AnswerBean answer = new AnswerBean();

        public static class AnswerBean implements Serializable {
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

        public int getIs_collection() {
            return this.is_collection;
        }

        public int getIs_comment() {
            return this.is_comment;
        }

        public int getIs_note() {
            return this.is_note;
        }

        public int getIs_praise() {
            return this.is_praise;
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

        public void setAnswer(AnswerBean answer) {
            this.answer = answer;
        }

        public void setCollection_count(int collection_count) {
            this.collection_count = collection_count;
        }

        public void setComment_count(int comment_count) {
            this.comment_count = comment_count;
        }

        public void setIs_collection(int is_collection) {
            this.is_collection = is_collection;
        }

        public void setIs_comment(int is_comment) {
            this.is_comment = is_comment;
        }

        public void setIs_note(int is_note) {
            this.is_note = is_note;
        }

        public void setIs_praise(int is_praise) {
            this.is_praise = is_praise;
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
