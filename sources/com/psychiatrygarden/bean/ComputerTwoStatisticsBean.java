package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class ComputerTwoStatisticsBean {
    private String code;
    private DataInfoBean data;
    private String message;

    public class DataInfoBean {
        private String exam_again;
        private String num_count;
        private String pass;
        private String right_count;
        private String score;
        private List<StatisticsBean> user_answer;

        public DataInfoBean() {
        }

        public String getExam_again() {
            return this.exam_again;
        }

        public String getNum_count() {
            return this.num_count;
        }

        public String getPass() {
            return this.pass;
        }

        public String getRight_count() {
            return this.right_count;
        }

        public String getScore() {
            return this.score;
        }

        public List<StatisticsBean> getUser_answer() {
            return this.user_answer;
        }

        public void setExam_again(String exam_again) {
            this.exam_again = exam_again;
        }

        public void setNum_count(String num_count) {
            this.num_count = num_count;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public void setRight_count(String right_count) {
            this.right_count = right_count;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setUser_answer(List<StatisticsBean> user_answer) {
            this.user_answer = user_answer;
        }
    }

    public class StatisticsBean {
        private String accuracy;
        private String question_type;
        private String right_count;
        private String score;
        private String score_total;

        public StatisticsBean() {
        }

        public String getAccuracy() {
            return this.accuracy;
        }

        public String getQuestion_type() {
            return this.question_type;
        }

        public String getRight_count() {
            return this.right_count;
        }

        public String getScore() {
            return this.score;
        }

        public String getScore_total() {
            return this.score_total;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public void setQuestion_type(String question_type) {
            this.question_type = question_type;
        }

        public void setRight_count(String right_count) {
            this.right_count = right_count;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setScore_total(String score_total) {
            this.score_total = score_total;
        }
    }

    public String getCode() {
        return this.code;
    }

    public DataInfoBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataInfoBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
