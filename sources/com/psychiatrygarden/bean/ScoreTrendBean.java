package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class ScoreTrendBean {
    private String code;
    private ScoreTrendDataBean data;
    private String message;

    public class ScoreTrendDataBean {
        private String all_score;
        private String describe;
        private String estimated_score;
        private String estimated_score_trend;
        private List<TrendItemBean> report;
        private String time;
        private String trend;

        public ScoreTrendDataBean() {
        }

        public String getAll_score() {
            return this.all_score;
        }

        public String getDescribe() {
            return this.describe;
        }

        public String getEstimated_score() {
            return this.estimated_score;
        }

        public String getEstimated_score_trend() {
            return this.estimated_score_trend;
        }

        public String getTime() {
            return this.time;
        }

        public String getTrend() {
            return this.trend;
        }

        public List<TrendItemBean> getWeek() {
            return this.report;
        }

        public void setAll_score(String all_score) {
            this.all_score = all_score;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public void setEstimated_score(String estimated_score) {
            this.estimated_score = estimated_score;
        }

        public void setEstimated_score_trend(String estimated_score_trend) {
            this.estimated_score_trend = estimated_score_trend;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setTrend(String trend) {
            this.trend = trend;
        }

        public void setWeek(List<TrendItemBean> week) {
            this.report = week;
        }
    }

    public static class TrendItemBean implements Serializable {
        private String allScore;
        private String end_time;
        private String estimated_score;
        private String estimated_score_trend;
        private String id;
        private String start_time;
        private String trend;
        private List<TrendItemChildBean> trend_data;

        public String getAllScore() {
            return this.allScore;
        }

        public String getEnd_time() {
            return this.end_time;
        }

        public String getEstimated_score() {
            return this.estimated_score;
        }

        public String getEstimated_score_trend() {
            return this.estimated_score_trend;
        }

        public String getId() {
            return this.id;
        }

        public String getStart_time() {
            return this.start_time;
        }

        public String getTrend() {
            return this.trend;
        }

        public List<TrendItemChildBean> getTrend_data() {
            return this.trend_data;
        }

        public void setAllScore(String allScore) {
            this.allScore = allScore;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public void setEstimated_score(String estimated_score) {
            this.estimated_score = estimated_score;
        }

        public void setEstimated_score_trend(String estimated_score_trend) {
            this.estimated_score_trend = estimated_score_trend;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public void setTrend(String trend) {
            this.trend = trend;
        }

        public void setTrend_data(List<TrendItemChildBean> trend_data) {
            this.trend_data = trend_data;
        }
    }

    public class TrendItemChildBean {
        private String name;
        private String value;

        public TrendItemChildBean() {
        }

        public String getName() {
            return this.name;
        }

        public String getValue() {
            return this.value;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public String getCode() {
        return this.code;
    }

    public ScoreTrendDataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(ScoreTrendDataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
