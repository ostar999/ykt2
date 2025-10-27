package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class MockStatisticsBean {
    private String code;
    private MockStatisticsDataBean data;
    private String message;

    public class MockStatisticsDataBean {
        private String accuracy;
        private String answer_time;
        private String cover_img;
        private String description;
        private String knowledge_stat;
        private String own_rank;
        private String score;
        private String title;

        public MockStatisticsDataBean() {
        }

        public String getAccuracy() {
            return this.accuracy;
        }

        public String getAnswer_time() {
            return this.answer_time;
        }

        public String getCover_img() {
            return this.cover_img;
        }

        public String getDescription() {
            return this.description;
        }

        public String getKnowledge_stat() {
            return this.knowledge_stat;
        }

        public String getOwn_rank() {
            return this.own_rank;
        }

        public String getScore() {
            return this.score;
        }

        public String getTitle() {
            return this.title;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public void setAnswer_time(String answer_time) {
            this.answer_time = answer_time;
        }

        public void setCover_img(String cover_img) {
            this.cover_img = cover_img;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setKnowledge_stat(String knowledge_stat) {
            this.knowledge_stat = knowledge_stat;
        }

        public void setOwn_rank(String own_rank) {
            this.own_rank = own_rank;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getCode() {
        return this.code;
    }

    public MockStatisticsDataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(MockStatisticsDataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
