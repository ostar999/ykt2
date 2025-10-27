package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class CommentSearchBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String app_id;
        private String author_looked;
        private String avatar;
        private ImagesBean c_imgs;
        private String content;
        private String ctime;
        private String ctime_timestamp;
        private String delete_skill;
        private String floor_num;
        private String id;
        private String is_author;
        private String is_essence;
        private String is_oppose;
        private String is_praise;
        private String is_read;
        private String is_vip;
        private String nickname;
        private String no_access;
        private String obj_id;
        private String oppose_num;
        private String praise_num;
        private String reply_num;
        private String school;
        private String user_id;
        private String user_identity;
        private String user_identity_color;
        private String module_type = "1";
        private String reply_primary_id = "";
        private boolean is_showValue = true;
        private String is_authentication = "";
        private String img_watermark = "";

        public static class CImgsBean implements Serializable {
            private int b_height;
            private String b_img;
            private int b_width;
            private int s_height;
            private String s_img;
            private int s_width;

            public int getB_height() {
                return this.b_height;
            }

            public String getB_img() {
                return this.b_img;
            }

            public int getB_width() {
                return this.b_width;
            }

            public int getS_height() {
                return this.s_height;
            }

            public String getS_img() {
                return this.s_img;
            }

            public int getS_width() {
                return this.s_width;
            }

            public CImgsBean setB_height(int b_height) {
                this.b_height = b_height;
                return this;
            }

            public CImgsBean setB_img(String b_img) {
                this.b_img = b_img;
                return this;
            }

            public CImgsBean setB_width(int b_width) {
                this.b_width = b_width;
                return this;
            }

            public CImgsBean setS_height(int s_height) {
                this.s_height = s_height;
                return this;
            }

            public CImgsBean setS_img(String s_img) {
                this.s_img = s_img;
                return this;
            }

            public CImgsBean setS_width(int s_width) {
                this.s_width = s_width;
                return this;
            }
        }

        public String getApp_id() {
            return this.app_id;
        }

        public String getAuthor_looked() {
            return this.author_looked;
        }

        public String getAvatar() {
            return this.avatar;
        }

        public ImagesBean getC_imgs() {
            return this.c_imgs;
        }

        public String getContent() {
            return this.content;
        }

        public String getCtime() {
            return this.ctime;
        }

        public String getCtime_timestamp() {
            return this.ctime_timestamp;
        }

        public String getDelete_skill() {
            return this.delete_skill;
        }

        public String getFloor_num() {
            return this.floor_num;
        }

        public String getId() {
            return this.id;
        }

        public String getImg_watermark() {
            return this.img_watermark;
        }

        public String getIs_authentication() {
            return this.is_authentication;
        }

        public String getIs_author() {
            return this.is_author;
        }

        public String getIs_essence() {
            return this.is_essence;
        }

        public String getIs_oppose() {
            return this.is_oppose;
        }

        public String getIs_praise() {
            return this.is_praise;
        }

        public String getIs_read() {
            return this.is_read;
        }

        public String getIs_vip() {
            return this.is_vip;
        }

        public String getModule_type() {
            return this.module_type;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getNo_access() {
            return this.no_access;
        }

        public String getObj_id() {
            return this.obj_id;
        }

        public String getOppose_num() {
            return this.oppose_num;
        }

        public String getPraise_num() {
            return this.praise_num;
        }

        public String getReply_num() {
            return this.reply_num;
        }

        public String getReply_primary_id() {
            return this.reply_primary_id;
        }

        public String getSchool() {
            return this.school;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public String getUser_identity() {
            return this.user_identity;
        }

        public String getUser_identity_color() {
            return this.user_identity_color;
        }

        public boolean isIs_showValue() {
            return this.is_showValue;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setAuthor_looked(String author_looked) {
            this.author_looked = author_looked;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setC_imgs(ImagesBean c_imgs) {
            this.c_imgs = c_imgs;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setCtime_timestamp(String ctime_timestamp) {
            this.ctime_timestamp = ctime_timestamp;
        }

        public void setDelete_skill(String delete_skill) {
            this.delete_skill = delete_skill;
        }

        public void setFloor_num(String floor_num) {
            this.floor_num = floor_num;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setImg_watermark(String img_watermark) {
            this.img_watermark = img_watermark;
        }

        public void setIs_authentication(String is_authentication) {
            this.is_authentication = is_authentication;
        }

        public void setIs_author(String is_author) {
            this.is_author = is_author;
        }

        public void setIs_essence(String is_essence) {
            this.is_essence = is_essence;
        }

        public void setIs_oppose(String is_oppose) {
            this.is_oppose = is_oppose;
        }

        public void setIs_praise(String is_praise) {
            this.is_praise = is_praise;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public DataBean setIs_showValue(boolean is_showValue) {
            this.is_showValue = is_showValue;
            return this;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public void setModule_type(String module_type) {
            this.module_type = module_type;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setNo_access(String no_access) {
            this.no_access = no_access;
        }

        public void setObj_id(String obj_id) {
            this.obj_id = obj_id;
        }

        public void setOppose_num(String oppose_num) {
            this.oppose_num = oppose_num;
        }

        public void setPraise_num(String praise_num) {
            this.praise_num = praise_num;
        }

        public void setReply_num(String reply_num) {
            this.reply_num = reply_num;
        }

        public void setReply_primary_id(String reply_primary_id) {
            this.reply_primary_id = reply_primary_id;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setUser_identity(String user_identity) {
            this.user_identity = user_identity;
        }

        public void setUser_identity_color(String user_identity_color) {
            this.user_identity_color = user_identity_color;
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
}
