package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class MyMessageCommentBean {
    private int code;
    private List<DataBean> data;
    private String message;
    private int server_time;

    public static class DataBean {
        private String avatar;
        private ImagesBean c_imgs;
        private int comment_id;
        private int comment_type;
        private String content;
        private String is_anonymous;
        private String is_oppose;
        private String is_praise;
        private String nickname;
        private String obj_id;
        private String oppose_num;
        private String praise_num;
        private int push_type;
        private List<ReplayBean> reply;
        private String reply_num;
        private String reply_primary_id;
        private TarGetParamBean target_params;
        private String timestr;
        private String title;
        private String to_from;
        private String to_from_author;
        private String type;
        private String user_id;
        private int module_type = 0;
        private String is_read = "1";
        public String video_id = "";
        private String is_logout = "";

        public String getAvatar() {
            return this.avatar;
        }

        public ImagesBean getC_imgs() {
            return this.c_imgs;
        }

        public int getComment_id() {
            return this.comment_id;
        }

        public int getComment_type() {
            return this.comment_type;
        }

        public String getContent() {
            return this.content;
        }

        public String getIs_anonymous() {
            return this.is_anonymous;
        }

        public String getIs_logout() {
            return this.is_logout;
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

        public int getModule_type() {
            return this.module_type;
        }

        public String getNickname() {
            return this.nickname;
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

        public int getPush_type() {
            return this.push_type;
        }

        public List<ReplayBean> getReply() {
            return this.reply;
        }

        public String getReply_num() {
            return this.reply_num;
        }

        public String getReply_primary_id() {
            return this.reply_primary_id;
        }

        public TarGetParamBean getTarget_params() {
            return this.target_params;
        }

        public String getTimestr() {
            return this.timestr;
        }

        public String getTitle() {
            return this.title;
        }

        public String getTo_from() {
            return this.to_from;
        }

        public String getTo_from_author() {
            return this.to_from_author;
        }

        public String getType() {
            return this.type;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public String getVideo_id() {
            return this.video_id;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setC_imgs(ImagesBean c_imgs) {
            this.c_imgs = c_imgs;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public void setComment_type(int comment_type) {
            this.comment_type = comment_type;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setIs_anonymous(String is_anonymous) {
            this.is_anonymous = is_anonymous;
        }

        public void setIs_logout(String is_logout) {
            this.is_logout = is_logout;
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

        public void setModule_type(int module_type) {
            this.module_type = module_type;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public void setPush_type(int push_type) {
            this.push_type = push_type;
        }

        public void setReply(List<ReplayBean> reply) {
            this.reply = reply;
        }

        public void setReply_num(String reply_num) {
            this.reply_num = reply_num;
        }

        public void setReply_primary_id(String reply_primary_id) {
            this.reply_primary_id = reply_primary_id;
        }

        public void setTarget_params(TarGetParamBean target_params) {
            this.target_params = target_params;
        }

        public void setTimestr(String timestr) {
            this.timestr = timestr;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTo_from(String to_from) {
            this.to_from = to_from;
        }

        public void setTo_from_author(String to_from_author) {
            this.to_from_author = to_from_author;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }
    }

    public static class ReplayBean {
        private String avatar;
        private ImagesBean c_imgs;
        private String chapter_id;
        private String comment;
        private String content;
        private String ctime;
        private String id;
        private String is_oppose;
        private String is_praise;
        private String is_support;
        private String nickname;
        private String obj_id;
        private String oppose_num;
        private String opposition_count;
        private String paragraph_content;
        private String paragraph_id;
        private String praise_num;
        private String reply_count;
        private String reply_num;
        private String reply_primary_id;
        private String review_type;
        private String school;
        private String support_count;
        private String timestr;
        private String user_id;
        private String video_id;

        public String getAvatar() {
            return this.avatar;
        }

        public ImagesBean getC_imgs() {
            return this.c_imgs;
        }

        public String getChapter_id() {
            return this.chapter_id;
        }

        public String getComment() {
            return this.comment;
        }

        public String getContent() {
            return this.content;
        }

        public String getCtime() {
            return this.ctime;
        }

        public String getId() {
            return this.id;
        }

        public String getIs_oppose() {
            return this.is_oppose;
        }

        public String getIs_praise() {
            return this.is_praise;
        }

        public String getIs_support() {
            return this.is_support;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getObj_id() {
            return this.obj_id;
        }

        public String getOppose_num() {
            return this.oppose_num;
        }

        public String getOpposition_count() {
            return this.opposition_count;
        }

        public String getParagraph_content() {
            return this.paragraph_content;
        }

        public String getParagraph_id() {
            return this.paragraph_id;
        }

        public String getPraise_num() {
            return this.praise_num;
        }

        public String getReply_count() {
            return this.reply_count;
        }

        public String getReply_num() {
            return this.reply_num;
        }

        public String getReply_primary_id() {
            return this.reply_primary_id;
        }

        public String getReview_type() {
            return this.review_type;
        }

        public String getSchool() {
            return this.school;
        }

        public String getSupport_count() {
            return this.support_count;
        }

        public String getTimestr() {
            return this.timestr;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public String getVideo_id() {
            return this.video_id;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setC_imgs(ImagesBean c_imgs) {
            this.c_imgs = c_imgs;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_oppose(String is_oppose) {
            this.is_oppose = is_oppose;
        }

        public void setIs_praise(String is_praise) {
            this.is_praise = is_praise;
        }

        public void setIs_support(String is_support) {
            this.is_support = is_support;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setObj_id(String obj_id) {
            this.obj_id = obj_id;
        }

        public void setOppose_num(String oppose_num) {
            this.oppose_num = oppose_num;
        }

        public void setOpposition_count(String opposition_count) {
            this.opposition_count = opposition_count;
        }

        public void setParagraph_content(String paragraph_content) {
            this.paragraph_content = paragraph_content;
        }

        public void setParagraph_id(String paragraph_id) {
            this.paragraph_id = paragraph_id;
        }

        public void setPraise_num(String praise_num) {
            this.praise_num = praise_num;
        }

        public void setReply_count(String reply_count) {
            this.reply_count = reply_count;
        }

        public void setReply_num(String reply_num) {
            this.reply_num = reply_num;
        }

        public void setReply_primary_id(String reply_primary_id) {
            this.reply_primary_id = reply_primary_id;
        }

        public void setReview_type(String review_type) {
            this.review_type = review_type;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setSupport_count(String support_count) {
            this.support_count = support_count;
        }

        public void setTimestr(String timestr) {
            this.timestr = timestr;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }
    }

    public int getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getServer_time() {
        return this.server_time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }
}
