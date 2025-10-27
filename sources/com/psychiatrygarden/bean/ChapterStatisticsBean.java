package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class ChapterStatisticsBean {
    public String code;
    public List<ChapterStatisticsData> data;
    public String message;

    public class ChapterStatisticsData {
        private String brush_question_count;
        private List<ChapterStatisticsData> children;
        private String identity_id;
        private String question_count;
        private String right_rate;
        private String title;

        public ChapterStatisticsData() {
        }

        public String getBrush_question_count() {
            return this.brush_question_count;
        }

        public List<ChapterStatisticsData> getChildren() {
            return this.children;
        }

        public String getIdentity_id() {
            return this.identity_id;
        }

        public String getQuestion_count() {
            return this.question_count;
        }

        public String getRight_rate() {
            return this.right_rate;
        }

        public String getTitle() {
            return this.title;
        }

        public void setBrush_question_count(String brush_question_count) {
            this.brush_question_count = brush_question_count;
        }

        public void setChildren(List<ChapterStatisticsData> children) {
            this.children = children;
        }

        public void setIdentity_id(String identity_id) {
            this.identity_id = identity_id;
        }

        public void setQuestion_count(String question_count) {
            this.question_count = question_count;
        }

        public void setRight_rate(String right_rate) {
            this.right_rate = right_rate;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<ChapterStatisticsData> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<ChapterStatisticsData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
