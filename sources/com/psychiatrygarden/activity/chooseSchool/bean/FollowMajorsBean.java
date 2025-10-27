package com.psychiatrygarden.activity.chooseSchool.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class FollowMajorsBean {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;
    private boolean success;

    public static class DataBean {
        private boolean editing_state;
        private String follow_count;
        private String heat;
        private String id;
        private boolean item_select;
        private String major_code;
        private String major_id;
        private String major_type;
        private String pre_rank;
        private String rank;
        private String rank_type;
        private String recent_7days_follow;
        private String student_count;
        private String title;
        private String view_count;

        public String getFollow_count() {
            return this.follow_count;
        }

        public String getHeat() {
            return this.heat;
        }

        public String getId() {
            return this.id;
        }

        public String getMajor_code() {
            return this.major_code;
        }

        public String getMajor_id() {
            return this.major_id;
        }

        public String getMajor_type() {
            return this.major_type;
        }

        public String getPre_rank() {
            return this.pre_rank;
        }

        public String getRank() {
            return this.rank;
        }

        public String getRank_type() {
            return this.rank_type;
        }

        public String getRecent_7days_follow() {
            return this.recent_7days_follow;
        }

        public String getStudent_count() {
            return this.student_count;
        }

        public String getTitle() {
            return this.title;
        }

        public String getView_count() {
            return this.view_count;
        }

        public boolean isEditing_state() {
            return this.editing_state;
        }

        public boolean isItem_select() {
            return this.item_select;
        }

        public void setEditing_state(boolean editing_state) {
            this.editing_state = editing_state;
        }

        public void setFollow_count(String follow_count) {
            this.follow_count = follow_count;
        }

        public void setHeat(String heat) {
            this.heat = heat;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setItem_select(boolean item_select) {
            this.item_select = item_select;
        }

        public void setMajor_code(String major_code) {
            this.major_code = major_code;
        }

        public void setMajor_id(String major_id) {
            this.major_id = major_id;
        }

        public void setMajor_type(String major_type) {
            this.major_type = major_type;
        }

        public void setPre_rank(String pre_rank) {
            this.pre_rank = pre_rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public void setRank_type(String rank_type) {
            this.rank_type = rank_type;
        }

        public void setRecent_7days_follow(String recent_7days_follow) {
            this.recent_7days_follow = recent_7days_follow;
        }

        public void setStudent_count(String student_count) {
            this.student_count = student_count;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
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

    public boolean isSuccess() {
        return this.success;
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

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
