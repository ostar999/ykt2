package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatListBean {
    private String code;
    private List<DataDTO> data;
    private String message;
    private String server_time;

    public static class DataDTO {
        private String allow_invites;
        private String announcement;
        private String community_id;
        private String description;
        private String id;
        private String is_join;
        private String logo;
        private String max_users;
        private String member_count;
        private String members_only;
        private String name;
        private String new_message;
        private String owner;

        @SerializedName("public")
        private String publicX;

        public String getAllow_invites() {
            return this.allow_invites;
        }

        public String getAnnouncement() {
            return this.announcement;
        }

        public String getCommunity_id() {
            return this.community_id;
        }

        public String getDescription() {
            return this.description;
        }

        public String getId() {
            return this.id;
        }

        public String getIs_join() {
            return this.is_join;
        }

        public String getLogo() {
            return this.logo;
        }

        public String getMax_users() {
            return this.max_users;
        }

        public String getMember_count() {
            return this.member_count;
        }

        public String getMembers_only() {
            return this.members_only;
        }

        public String getName() {
            return this.name;
        }

        public String getNew_message() {
            return this.new_message;
        }

        public String getOwner() {
            return this.owner;
        }

        public String getPublicX() {
            return this.publicX;
        }

        public void setAllow_invites(String allow_invites) {
            this.allow_invites = allow_invites;
        }

        public void setAnnouncement(String announcement) {
            this.announcement = announcement;
        }

        public void setCommunity_id(String community_id) {
            this.community_id = community_id;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_join(String is_join) {
            this.is_join = is_join;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setMax_users(String max_users) {
            this.max_users = max_users;
        }

        public void setMember_count(String member_count) {
            this.member_count = member_count;
        }

        public void setMembers_only(String members_only) {
            this.members_only = members_only;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNew_message(String new_message) {
            this.new_message = new_message;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public void setPublicX(String publicX) {
            this.publicX = publicX;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataDTO> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
