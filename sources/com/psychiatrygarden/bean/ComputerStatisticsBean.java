package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class ComputerStatisticsBean {
    private String code;
    private DataInfoBean data;
    private String message;

    public class DataInfoBean {
        private String exam_again;
        private ScoreInfoBean ranking;
        private List<StatisticsBean> wong_center;
        private List<StatisticsBean> wong_type;

        public DataInfoBean() {
        }

        public String getExam_again() {
            return this.exam_again;
        }

        public ScoreInfoBean getRanking() {
            return this.ranking;
        }

        public List<StatisticsBean> getWong_center() {
            return this.wong_center;
        }

        public List<StatisticsBean> getWong_type() {
            return this.wong_type;
        }

        public void setExam_again(String exam_again) {
            this.exam_again = exam_again;
        }

        public void setRanking(ScoreInfoBean ranking) {
            this.ranking = ranking;
        }

        public void setWong_center(List<StatisticsBean> wong_center) {
            this.wong_center = wong_center;
        }

        public void setWong_type(List<StatisticsBean> wong_type) {
            this.wong_type = wong_type;
        }
    }

    public class ScoreInfoBean {
        private String exam_time;
        private String pass;
        private String rank;
        private String score;

        public ScoreInfoBean() {
        }

        public String getExam_time() {
            return this.exam_time;
        }

        public String getPass() {
            return this.pass;
        }

        public String getRank() {
            return this.rank;
        }

        public String getScore() {
            return this.score;
        }

        public void setExam_time(String exam_time) {
            this.exam_time = exam_time;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }

    public class StatisticsBean {
        private String chapter_name;
        private String outline;
        private String question_count;
        private String question_type;
        private String subject_name;
        private String wrong_count;
        private String wrong_percent;

        public StatisticsBean() {
        }

        public String getChapter_name() {
            return this.chapter_name;
        }

        public String getOutline() {
            return this.outline;
        }

        public String getQuestion_count() {
            return this.question_count;
        }

        public String getQuestion_type() {
            return this.question_type;
        }

        public String getSubject_name() {
            return this.subject_name;
        }

        public String getWrong_count() {
            return this.wrong_count;
        }

        public String getWrong_percent() {
            return this.wrong_percent;
        }

        public void setChapter_name(String chapter_name) {
            this.chapter_name = chapter_name;
        }

        public void setOutline(String outline) {
            this.outline = outline;
        }

        public void setQuestion_count(String question_count) {
            this.question_count = question_count;
        }

        public void setQuestion_type(String question_type) {
            this.question_type = question_type;
        }

        public void setSubject_name(String subject_name) {
            this.subject_name = subject_name;
        }

        public void setWrong_count(String wrong_count) {
            this.wrong_count = wrong_count;
        }

        public void setWrong_percent(String wrong_percent) {
            this.wrong_percent = wrong_percent;
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
