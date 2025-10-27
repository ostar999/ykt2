package com.psychiatrygarden.activity.forum.bean;

import com.psychiatrygarden.bean.CourseList2Bean;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class ForumInfoBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String add_note;
        private String allow_join;
        private String article_count;
        private String audit_join;
        private String comment_count;
        private String edit_notice_permission;
        private String follow_count;
        private String id;
        private String is_follow;
        private String join_the_state;
        private List<LabelBean> labels;
        private String logo;
        private List<ModeratorsBean> moderators;
        private String name;
        private String notice;
        private String show_user;
        private List<TagsBean> tags;
        private List<UserBean> user;
        private String user_count;
        private WeChatBean wechat;
        private String introduction = "";
        private String edit_introduction_permission = "";
        private String is_top = "0";

        public static class LabelBean implements Serializable {
            public String icon = "";
            public String label = "";
            public String jump = "";
            public CourseList2Bean.DataBean.DataChildBean obj = new CourseList2Bean.DataBean.DataChildBean();

            public String getIcon() {
                return this.icon;
            }

            public String getJump() {
                return this.jump;
            }

            public String getLabel() {
                return this.label;
            }

            public CourseList2Bean.DataBean.DataChildBean getObj() {
                return this.obj;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setJump(String jump) {
                this.jump = jump;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setObj(CourseList2Bean.DataBean.DataChildBean obj) {
                this.obj = obj;
            }
        }

        public static class ModeratorsBean implements Serializable {
            private String avatar;
            private String create_at;
            private String group_id;
            private String id;
            private String mobile;
            private String nickname;
            private String status;
            private String user_id;

            public String getAvatar() {
                return this.avatar;
            }

            public String getCreate_at() {
                return this.create_at;
            }

            public String getGroup_id() {
                return this.group_id;
            }

            public String getId() {
                return this.id;
            }

            public String getMobile() {
                return this.mobile;
            }

            public String getNickname() {
                return this.nickname;
            }

            public String getStatus() {
                return this.status;
            }

            public String getUser_id() {
                return this.user_id;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public void setGroup_id(String group_id) {
                this.group_id = group_id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }

        public static class TagsBean implements Serializable {
            private String color;
            private String id;
            private String label;

            public String getColor() {
                return this.color;
            }

            public String getId() {
                return this.id;
            }

            public String getLabel() {
                return this.label;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }

        public static class UserBean implements Serializable {
            private String avatar;
            private String ctime;
            private String current_app_id;
            private String id;
            private String nickname;
            private String praise_num;
            private String sex;
            private String sortLetters;
            private String user_id;
            private String user_uuid;

            public String getAvatar() {
                return this.avatar;
            }

            public String getCtime() {
                return this.ctime;
            }

            public String getCurrent_app_id() {
                return this.current_app_id;
            }

            public String getId() {
                return this.id;
            }

            public String getNickname() {
                return this.nickname;
            }

            public String getPraise_num() {
                return this.praise_num;
            }

            public String getSex() {
                return this.sex;
            }

            public String getSortLetters() {
                return this.sortLetters;
            }

            public String getUser_id() {
                return this.user_id;
            }

            public String getUser_uuid() {
                return this.user_uuid;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setCtime(String ctime) {
                this.ctime = ctime;
            }

            public void setCurrent_app_id(String current_app_id) {
                this.current_app_id = current_app_id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setPraise_num(String praise_num) {
                this.praise_num = praise_num;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setSortLetters(String sortLetters) {
                this.sortLetters = sortLetters;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public void setUser_uuid(String user_uuid) {
                this.user_uuid = user_uuid;
            }
        }

        public static class WeChatBean implements Serializable {
            public String qrcode;
            public String wechat_id;

            public String getQrcode() {
                return this.qrcode;
            }

            public String getWechat_id() {
                return this.wechat_id;
            }

            public void setQrcode(String qrcode) {
                this.qrcode = qrcode;
            }

            public void setWechat_id(String wechat_id) {
                this.wechat_id = wechat_id;
            }
        }

        public String getAdd_note() {
            return this.add_note;
        }

        public String getAllow_join() {
            return this.allow_join;
        }

        public String getArticle_count() {
            return this.article_count;
        }

        public String getAudit_join() {
            return this.audit_join;
        }

        public String getComment_count() {
            return this.comment_count;
        }

        public String getEdit_introduction_permission() {
            return this.edit_introduction_permission;
        }

        public String getEdit_notice_permission() {
            return this.edit_notice_permission;
        }

        public String getFollow_count() {
            return this.follow_count;
        }

        public String getId() {
            return this.id;
        }

        public String getIntroduction() {
            return this.introduction;
        }

        public String getIs_follow() {
            return this.is_follow;
        }

        public String getIs_top() {
            return this.is_top;
        }

        public String getJoin_the_state() {
            return this.join_the_state;
        }

        public List<LabelBean> getLabels() {
            return this.labels;
        }

        public String getLogo() {
            return this.logo;
        }

        public List<ModeratorsBean> getModerators() {
            return this.moderators;
        }

        public String getName() {
            return this.name;
        }

        public String getNotice() {
            return this.notice;
        }

        public String getShow_user() {
            return this.show_user;
        }

        public List<TagsBean> getTags() {
            return this.tags;
        }

        public List<UserBean> getUser() {
            return this.user;
        }

        public String getUser_count() {
            return this.user_count;
        }

        public WeChatBean getWechat() {
            return this.wechat;
        }

        public void setAdd_note(String add_note) {
            this.add_note = add_note;
        }

        public void setAllow_join(String allow_join) {
            this.allow_join = allow_join;
        }

        public void setArticle_count(String article_count) {
            this.article_count = article_count;
        }

        public void setAudit_join(String audit_join) {
            this.audit_join = audit_join;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public void setEdit_introduction_permission(String edit_introduction_permission) {
            this.edit_introduction_permission = edit_introduction_permission;
        }

        public void setEdit_notice_permission(String edit_notice_permission) {
            this.edit_notice_permission = edit_notice_permission;
        }

        public void setFollow_count(String follow_count) {
            this.follow_count = follow_count;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }

        public void setIs_top(String is_top) {
            this.is_top = is_top;
        }

        public void setJoin_the_state(String join_the_state) {
            this.join_the_state = join_the_state;
        }

        public void setLabels(List<LabelBean> labels) {
            this.labels = labels;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public void setModerators(List<ModeratorsBean> moderators) {
            this.moderators = moderators;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public void setShow_user(String show_user) {
            this.show_user = show_user;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public void setUser(List<UserBean> user) {
            this.user = user;
        }

        public void setUser_count(String user_count) {
            this.user_count = user_count;
        }

        public void setWechat(WeChatBean wechat) {
            this.wechat = wechat;
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
}
