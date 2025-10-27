package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class UserInfoBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class CodeShowBean implements Serializable {
        private ArrayList<String> desc;
        private String message;
        private int status;

        public ArrayList<String> getDesc() {
            return this.desc;
        }

        public String getMessage() {
            return this.message;
        }

        public int getStatus() {
            return this.status;
        }

        public void setDesc(ArrayList<String> desc) {
            this.desc = desc;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class DataBean implements Serializable {
        private String article_publish_count;
        private String article_read_count;
        private String collect_count;
        private String comment_count;
        private String comment_like_count;
        private CodeShowBean invite_code_show;
        private UserShowBean invite_user_show;
        private String mobile;
        private String login_status = "";
        private String user_id = "";
        private String open_id = "";
        private String avatar = "";
        private String nickname = "";
        private String username = "";
        private String user_uuid = "";
        private String sex = "";
        private String str_sex = "";
        private String token = "";
        private String secret = "";
        private String identity = "";
        private String identity_description = "";
        private String client_managers = "";
        private String target_id = "";
        private String target_name = "";
        private String target_major_id = "";
        private String target_major_name = "";
        private String now_id = "";
        private String now_name = "";
        private String now_major_id = "";
        private String now_major_name = "";
        private String exam_time = "";
        private String complete_user_info = "";
        private String department_id = "";
        private String department_name = "";
        private String hospital_id = "";
        private String hospital_name = "";
        private String work_time_id = "";
        private String work_time_name = "";
        private String is_vip = "";
        private String is_svip = "";
        private String vip_deadline = "";
        private String admin = "";
        private String ban = "";
        private String is_follow = "";
        private String follow_user = "";
        private String be_praise_count = "";
        private String to_follow_user = "";
        private String user_type_judged = "";
        private String identity_judged = "";
        private String is_authentication = "0";
        private String user_type = "";
        private String exam_count_down = "";
        private String reward_show_way = "";
        private String email = "";
        private String pre_test = "0";
        private String is_remove = "2";

        public String getAdmin() {
            return this.admin;
        }

        public String getArticle_publish_count() {
            return this.article_publish_count;
        }

        public String getArticle_read_count() {
            return this.article_read_count;
        }

        public String getAvatar() {
            return this.avatar;
        }

        public String getBan() {
            return this.ban;
        }

        public String getBe_praise_count() {
            return this.be_praise_count;
        }

        public String getClient_managers() {
            return this.client_managers;
        }

        public String getCollect_count() {
            return this.collect_count;
        }

        public String getComment_count() {
            return this.comment_count;
        }

        public String getComment_like_count() {
            return this.comment_like_count;
        }

        public String getComplete_user_info() {
            return this.complete_user_info;
        }

        public String getDepartment_id() {
            return this.department_id;
        }

        public String getDepartment_name() {
            return this.department_name;
        }

        public String getEmail() {
            return this.email;
        }

        public String getError_set() {
            return this.is_remove;
        }

        public String getExam_count_down() {
            return this.exam_count_down;
        }

        public String getExam_time() {
            return this.exam_time;
        }

        public String getFollow_user() {
            return this.follow_user;
        }

        public String getHospital_id() {
            return this.hospital_id;
        }

        public String getHospital_name() {
            return this.hospital_name;
        }

        public String getIdentity() {
            return this.identity;
        }

        public String getIdentity_description() {
            return this.identity_description;
        }

        public String getIdentity_judged() {
            return this.identity_judged;
        }

        public CodeShowBean getInvite_code_show() {
            return this.invite_code_show;
        }

        public UserShowBean getInvite_user_show() {
            return this.invite_user_show;
        }

        public String getIs_authentication() {
            return this.is_authentication;
        }

        public String getIs_follow() {
            return this.is_follow;
        }

        public String getIs_svip() {
            return this.is_svip;
        }

        public String getIs_vip() {
            return this.is_vip;
        }

        public String getLogin_status() {
            return this.login_status;
        }

        public String getMobile() {
            return this.mobile;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getNow_id() {
            return this.now_id;
        }

        public String getNow_major_id() {
            return this.now_major_id;
        }

        public String getNow_major_name() {
            return this.now_major_name;
        }

        public String getNow_name() {
            return this.now_name;
        }

        public String getOpen_id() {
            return this.open_id;
        }

        public String getPre_test() {
            return this.pre_test;
        }

        public String getReward_show_way() {
            return this.reward_show_way;
        }

        public String getSecret() {
            return this.secret;
        }

        public String getSex() {
            return this.sex;
        }

        public String getStr_sex() {
            return this.str_sex;
        }

        public String getTarget_id() {
            return this.target_id;
        }

        public String getTarget_major_id() {
            return this.target_major_id;
        }

        public String getTarget_major_name() {
            return this.target_major_name;
        }

        public String getTarget_name() {
            return this.target_name;
        }

        public String getTo_follow_user() {
            return this.to_follow_user;
        }

        public String getToken() {
            return this.token;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public String getUser_type() {
            return this.user_type;
        }

        public String getUser_type_judged() {
            return this.user_type_judged;
        }

        public String getUser_uuid() {
            return this.user_uuid;
        }

        public String getUsername() {
            return this.username;
        }

        public String getVip_deadline() {
            return this.vip_deadline;
        }

        public String getWork_time_id() {
            return this.work_time_id;
        }

        public String getWork_time_name() {
            return this.work_time_name;
        }

        public void setAdmin(String admin) {
            this.admin = admin;
        }

        public void setArticle_publish_count(String article_publish_count) {
            this.article_publish_count = article_publish_count;
        }

        public void setArticle_read_count(String article_read_count) {
            this.article_read_count = article_read_count;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setBan(String ban) {
            this.ban = ban;
        }

        public void setBe_praise_count(String be_praise_count) {
            this.be_praise_count = be_praise_count;
        }

        public void setClient_managers(String client_managers) {
            this.client_managers = client_managers;
        }

        public void setCollect_count(String collect_count) {
            this.collect_count = collect_count;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public void setComment_like_count(String comment_like_count) {
            this.comment_like_count = comment_like_count;
        }

        public void setComplete_user_info(String complete_user_info) {
            this.complete_user_info = complete_user_info;
        }

        public void setDepartment_id(String department_id) {
            this.department_id = department_id;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setError_set(String error_set) {
            this.is_remove = error_set;
        }

        public void setExam_count_down(String exam_count_down) {
            this.exam_count_down = exam_count_down;
        }

        public void setExam_time(String exam_time) {
            this.exam_time = exam_time;
        }

        public void setFollow_user(String follow_user) {
            this.follow_user = follow_user;
        }

        public void setHospital_id(String hospital_id) {
            this.hospital_id = hospital_id;
        }

        public void setHospital_name(String hospital_name) {
            this.hospital_name = hospital_name;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public void setIdentity_description(String identity_description) {
            this.identity_description = identity_description;
        }

        public void setIdentity_judged(String identity_judged) {
            this.identity_judged = identity_judged;
        }

        public void setInvite_code_show(CodeShowBean invite_code_show) {
            this.invite_code_show = invite_code_show;
        }

        public void setInvite_user_show(UserShowBean invite_user_show) {
            this.invite_user_show = invite_user_show;
        }

        public void setIs_authentication(String is_authentication) {
            this.is_authentication = is_authentication;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }

        public void setIs_svip(String is_svip) {
            this.is_svip = is_svip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public void setLogin_status(String login_status) {
            this.login_status = login_status;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setNow_id(String now_id) {
            this.now_id = now_id;
        }

        public void setNow_major_id(String now_major_id) {
            this.now_major_id = now_major_id;
        }

        public void setNow_major_name(String now_major_name) {
            this.now_major_name = now_major_name;
        }

        public void setNow_name(String now_name) {
            this.now_name = now_name;
        }

        public void setOpen_id(String open_id) {
            this.open_id = open_id;
        }

        public void setPre_test(String pre_test) {
            this.pre_test = pre_test;
        }

        public void setReward_show_way(String reward_show_way) {
            this.reward_show_way = reward_show_way;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setStr_sex(String str_sex) {
            this.str_sex = str_sex;
        }

        public void setTarget_id(String target_id) {
            this.target_id = target_id;
        }

        public void setTarget_major_id(String target_major_id) {
            this.target_major_id = target_major_id;
        }

        public void setTarget_major_name(String target_major_name) {
            this.target_major_name = target_major_name;
        }

        public void setTarget_name(String target_name) {
            this.target_name = target_name;
        }

        public void setTo_follow_user(String to_follow_user) {
            this.to_follow_user = to_follow_user;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public void setUser_type_judged(String user_type_judged) {
            this.user_type_judged = user_type_judged;
        }

        public void setUser_uuid(String user_uuid) {
            this.user_uuid = user_uuid;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setVip_deadline(String vip_deadline) {
            this.vip_deadline = vip_deadline;
        }

        public void setWork_time_id(String work_time_id) {
            this.work_time_id = work_time_id;
        }

        public void setWork_time_name(String work_time_name) {
            this.work_time_name = work_time_name;
        }
    }

    public static class UserShowBean implements Serializable {
        private ArrayList<String> desc;
        private String join_status;
        private String message;
        private int status;
        private String web_link;

        public ArrayList<String> getDesc() {
            return this.desc;
        }

        public String getJoin_status() {
            return this.join_status;
        }

        public String getMessage() {
            return this.message;
        }

        public int getStatus() {
            return this.status;
        }

        public String getWeb_link() {
            return this.web_link;
        }

        public void setDesc(ArrayList<String> desc) {
            this.desc = desc;
        }

        public void setJoin_status(String join_status) {
            this.join_status = join_status;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setWeb_link(String web_link) {
            this.web_link = web_link;
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
