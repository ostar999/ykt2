package com.ykb.ebook.model;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes7.dex */
public class QuestionStatDataBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private AnswerBean answer;
        private int collection_count;
        private int comment_count;
        private int error_correction_number;
        private List<String> explain_correction_avatar;
        private int explain_correction_number;
        private String explain_correction_tips;
        private int is_collection;
        private int is_comment;
        private int is_note;
        private int is_praise;
        private List<String> restore_correction_avatar;
        private int restore_correction_number;
        private String restore_correction_tips;
        private long right_count;
        private String stat_info;
        private int update_time;
        private long wrong_count;

        public static class AnswerBean implements Serializable {
            private int right_count;
            private int wrong_count;

            public int getRight_count() {
                return this.right_count;
            }

            public int getWrong_count() {
                return this.wrong_count;
            }

            public void setRight_count(int i2) {
                this.right_count = i2;
            }

            public void setWrong_count(int i2) {
                this.wrong_count = i2;
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

        public int getError_correction_number() {
            return this.error_correction_number;
        }

        public List<String> getExplain_correction_avatar() {
            return this.explain_correction_avatar;
        }

        public int getExplain_correction_number() {
            return this.explain_correction_number;
        }

        public String getExplain_correction_tips() {
            return this.explain_correction_tips;
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

        public List<String> getRestore_correction_avatar() {
            return this.restore_correction_avatar;
        }

        public int getRestore_correction_number() {
            return this.restore_correction_number;
        }

        public String getRestore_correction_tips() {
            return this.restore_correction_tips;
        }

        public long getRight_count() {
            return this.right_count;
        }

        public String getStat_info() {
            return this.stat_info;
        }

        public int getUpdate_time() {
            return this.update_time;
        }

        public long getWrong_count() {
            return this.wrong_count;
        }

        public void setAnswer(AnswerBean answerBean) {
            this.answer = answerBean;
        }

        public void setCollection_count(int i2) {
            this.collection_count = i2;
        }

        public void setComment_count(int i2) {
            this.comment_count = i2;
        }

        public void setError_correction_number(int i2) {
            this.error_correction_number = i2;
        }

        public void setExplain_correction_avatar(List<String> list) {
            this.explain_correction_avatar = list;
        }

        public void setExplain_correction_number(int i2) {
            this.explain_correction_number = i2;
        }

        public void setExplain_correction_tips(String str) {
            this.explain_correction_tips = str;
        }

        public void setIs_collection(int i2) {
            this.is_collection = i2;
        }

        public void setIs_comment(int i2) {
            this.is_comment = i2;
        }

        public void setIs_note(int i2) {
            this.is_note = i2;
        }

        public void setIs_praise(int i2) {
            this.is_praise = i2;
        }

        public void setRestore_correction_avatar(List<String> list) {
            this.restore_correction_avatar = list;
        }

        public void setRestore_correction_number(int i2) {
            this.restore_correction_number = i2;
        }

        public void setRestore_correction_tips(String str) {
            this.restore_correction_tips = str;
        }

        public void setRight_count(long j2) {
            this.right_count = j2;
        }

        public void setStat_info(String str) {
            this.stat_info = str;
        }

        public void setUpdate_time(int i2) {
            this.update_time = i2;
        }

        public void setWrong_count(long j2) {
            this.wrong_count = j2;
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

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setServer_time(String str) {
        this.server_time = str;
    }
}
