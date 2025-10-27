package com.psychiatrygarden.activity.mine.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class MemberDetailBean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;
    private boolean success;

    public static class DataBean {
        private String count;
        private List<RewardItem> list;

        public String getCount() {
            return this.count;
        }

        public List<RewardItem> getList() {
            return this.list;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public void setList(List<RewardItem> list) {
            this.list = list;
        }
    }

    public static class RewardItem {
        private String app_id;
        private String created_at;
        private String id;
        private String reward_day;
        private String reward_str;
        private String reward_type;
        private String reward_type_str;
        private String updated_at;
        private String user_id;

        public String getApp_id() {
            return this.app_id;
        }

        public String getCreated_at() {
            return this.created_at;
        }

        public String getId() {
            return this.id;
        }

        public String getReward_day() {
            return this.reward_day;
        }

        public String getReward_str() {
            return this.reward_str;
        }

        public String getReward_type() {
            return this.reward_type;
        }

        public String getReward_type_str() {
            return this.reward_type_str;
        }

        public String getUpdated_at() {
            return this.updated_at;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setReward_day(String reward_day) {
            this.reward_day = reward_day;
        }

        public void setReward_str(String reward_str) {
            this.reward_str = reward_str;
        }

        public void setReward_type(String reward_type) {
            this.reward_type = reward_type;
        }

        public void setReward_type_str(String reward_type_str) {
            this.reward_type_str = reward_type_str;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

    public boolean isSuccess() {
        return this.success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataBean data) {
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
