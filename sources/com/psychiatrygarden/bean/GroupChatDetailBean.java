package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class GroupChatDetailBean {
    private String code;
    private DataDTO data;
    private String message;
    private String server_time;

    public static class DataDTO {
        private String allow_invites;
        private String announcement;
        private List<String> announcement_image;
        private String community_id;
        private List<DefaultMemberDTO> default_member;
        private String description;
        private String id;
        private String logo;
        private String max_users;
        private String member_count;
        private String members_only;
        private String name;
        private String owner;

        @SerializedName("public")
        private String publicX;
        private String user_identity;
        private String user_is_join;

        public static class DefaultMemberDTO implements Serializable {
            private String avatar;
            private String is_banned;
            private String nickname;
            private String user_id;
            private String user_uuid;
            private String is_owner = "";
            private boolean is_add = false;
            private boolean is_choice = false;

            public String getAvatar() {
                return this.avatar;
            }

            public String getIs_banned() {
                return this.is_banned;
            }

            public String getIs_owner() {
                return this.is_owner;
            }

            public String getNickname() {
                return this.nickname;
            }

            public String getUser_id() {
                return this.user_id;
            }

            public String getUser_uuid() {
                return this.user_uuid;
            }

            public boolean isIs_add() {
                return this.is_add;
            }

            public boolean isIs_choice() {
                return this.is_choice;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setIs_add(boolean is_add) {
                this.is_add = is_add;
            }

            public void setIs_banned(String is_banned) {
                this.is_banned = is_banned;
            }

            public void setIs_choice(boolean is_choice) {
                this.is_choice = is_choice;
            }

            public void setIs_owner(String is_owner) {
                this.is_owner = is_owner;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public void setUser_uuid(String user_uuid) {
                this.user_uuid = user_uuid;
            }
        }

        public String getAllow_invites() {
            return this.allow_invites;
        }

        public String getAnnouncement() {
            return this.announcement;
        }

        public List<String> getAnnouncement_image() {
            return this.announcement_image;
        }

        public String getCommunity_id() {
            return this.community_id;
        }

        public List<DefaultMemberDTO> getDefault_member() {
            return this.default_member;
        }

        public String getDescription() {
            return this.description;
        }

        public String getId() {
            return this.id;
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

        public String getOwner() {
            return this.owner;
        }

        public String getPublicX() {
            return this.publicX;
        }

        public String getUser_identity() {
            return this.user_identity;
        }

        public String getUser_is_join() {
            return this.user_is_join;
        }

        public void setAllow_invites(String allow_invites) {
            this.allow_invites = allow_invites;
        }

        public void setAnnouncement(String announcement) {
            this.announcement = announcement;
        }

        public void setAnnouncement_image(List<String> announcement_image) {
            this.announcement_image = announcement_image;
        }

        public void setCommunity_id(String community_id) {
            this.community_id = community_id;
        }

        public void setDefault_member(List<DefaultMemberDTO> default_member) {
            this.default_member = default_member;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setId(String id) {
            this.id = id;
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

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public void setPublicX(String publicX) {
            this.publicX = publicX;
        }

        public void setUser_identity(String user_identity) {
            this.user_identity = user_identity;
        }

        public void setUser_is_join(String user_is_join) {
            this.user_is_join = user_is_join;
        }
    }

    public String getCode() {
        return this.code;
    }

    public DataDTO getData() {
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

    public void setData(DataDTO data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
