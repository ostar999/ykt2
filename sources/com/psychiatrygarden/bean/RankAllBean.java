package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class RankAllBean {
    private String code;
    private RankAllData data;
    private String message;

    public class RankAllData {
        private String refresh_time;
        private List<RankBeanData> user_bbs_article_total;
        private List<RankBeanData> user_comment_total;
        private List<RankBeanData> user_continue_days;
        private List<RankBeanData> user_praise_total;
        private List<RankBeanData> user_sheet_total;
        private List<RankBeanData> user_total_days;

        public RankAllData() {
        }

        public String getRefresh_time() {
            return this.refresh_time;
        }

        public List<RankBeanData> getUser_bbs_article_total() {
            return this.user_bbs_article_total;
        }

        public List<RankBeanData> getUser_comment_total() {
            return this.user_comment_total;
        }

        public List<RankBeanData> getUser_continue_days() {
            return this.user_continue_days;
        }

        public List<RankBeanData> getUser_praise_total() {
            return this.user_praise_total;
        }

        public List<RankBeanData> getUser_sheet_total() {
            return this.user_sheet_total;
        }

        public List<RankBeanData> getUser_total_days() {
            return this.user_total_days;
        }

        public void setRefresh_time(String refresh_time) {
            this.refresh_time = refresh_time;
        }

        public void setUser_bbs_article_total(List<RankBeanData> user_bbs_article_total) {
            this.user_bbs_article_total = user_bbs_article_total;
        }

        public void setUser_comment_total(List<RankBeanData> user_comment_total) {
            this.user_comment_total = user_comment_total;
        }

        public void setUser_continue_days(List<RankBeanData> user_continue_days) {
            this.user_continue_days = user_continue_days;
        }

        public void setUser_praise_total(List<RankBeanData> user_praise_total) {
            this.user_praise_total = user_praise_total;
        }

        public void setUser_sheet_total(List<RankBeanData> user_sheet_total) {
            this.user_sheet_total = user_sheet_total;
        }

        public void setUser_total_days(List<RankBeanData> user_total_days) {
            this.user_total_days = user_total_days;
        }
    }

    public String getCode() {
        return this.code;
    }

    public RankAllData getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(RankAllData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
