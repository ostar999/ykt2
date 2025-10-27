package com.psychiatrygarden.bean;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.http.CodeParseJsonAdapter;
import java.util.List;

/* loaded from: classes5.dex */
public class InviteListBean {
    private DataBean data;
    private String message;

    @SerializedName("code")
    @JsonAdapter(CodeParseJsonAdapter.class)
    private boolean success;

    public static class DataBean {
        private String invite_num;
        private String invite_reward;
        private String more;
        private List<TimeLineBean> time_line;

        public String getInvite_num() {
            return this.invite_num;
        }

        public String getInvite_reward() {
            return this.invite_reward;
        }

        public String getMore() {
            return this.more;
        }

        public List<TimeLineBean> getTime_line() {
            return this.time_line;
        }

        public void setInvite_num(String invite_num) {
            this.invite_num = invite_num;
        }

        public void setInvite_reward(String invite_reward) {
            this.invite_reward = invite_reward;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public void setTime_line(List<TimeLineBean> time_line) {
            this.time_line = time_line;
        }
    }

    public static class TimeLineBean {
        private String auth_label;
        private String authentication_state;
        private String avatar;
        private String create_at;
        private String id;
        private String nickname;
        private String reward_total;
        private String user_id;

        public String getAuth_label() {
            return this.auth_label;
        }

        public String getAuthentication_state() {
            return this.authentication_state;
        }

        public String getAvatar() {
            return this.avatar;
        }

        public String getCreate_at() {
            return this.create_at;
        }

        public String getId() {
            return this.id;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getReward_total() {
            return this.reward_total;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public void setAuth_label(String auth_label) {
            this.auth_label = auth_label;
        }

        public void setAuthentication_state(String authentication_state) {
            this.authentication_state = authentication_state;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setReward_total(String reward_total) {
            this.reward_total = reward_total;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }

    public DataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
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

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
