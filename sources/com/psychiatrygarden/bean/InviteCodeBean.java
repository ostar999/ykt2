package com.psychiatrygarden.bean;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.http.CodeParseJsonAdapter;

/* loaded from: classes5.dex */
public class InviteCodeBean {
    private DataBean data;
    private String message;
    private String server_time;

    @SerializedName("code")
    @JsonAdapter(CodeParseJsonAdapter.class)
    private boolean success;

    public static class DataBean {
        private String app_id;
        private String app_name;
        private IdentityIdBean identity_ids;
        private String message;
        private String reward_day;
        private String status;
        private String view_button;

        public String getApp_id() {
            return this.app_id;
        }

        public String getApp_name() {
            return this.app_name;
        }

        public IdentityIdBean getIdentity_ids() {
            return this.identity_ids;
        }

        public String getMessage() {
            return this.message;
        }

        public String getReward_day() {
            return this.reward_day;
        }

        public String getStatus() {
            return this.status;
        }

        public String getView_button() {
            return this.view_button;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public void setIdentity_ids(IdentityIdBean identity_ids) {
            this.identity_ids = identity_ids;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setReward_day(String reward_day) {
            this.reward_day = reward_day;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setView_button(String view_button) {
            this.view_button = view_button;
        }
    }

    public static class IdentityIdBean {
        private String first_identity_id;
        private String identity_id;
        private String second_identity_id;

        public String getFirst_identity_id() {
            return this.first_identity_id;
        }

        public String getIdentity_id() {
            return this.identity_id;
        }

        public String getSecond_identity_id() {
            return this.second_identity_id;
        }

        public void setFirst_identity_id(String first_identity_id) {
            this.first_identity_id = first_identity_id;
        }

        public void setIdentity_id(String identity_id) {
            this.identity_id = identity_id;
        }

        public void setSecond_identity_id(String second_identity_id) {
            this.second_identity_id = second_identity_id;
        }
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
