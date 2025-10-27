package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CommentBean implements Serializable {
    public static final int ITEM = 0;
    public static final int SECTION = 1;
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private List<HotBean> adopt;
        private String adopt_is_more;
        private String adopt_total;
        private String more;
        private String show_tips;
        private List<HotBean> time_line;
        private String more_hot = "0";
        private String hot_total = "";
        private String time_line_total = "";
        private List<HotBean> hot = new ArrayList();

        public static class HotBean implements Serializable {
            private String app_type;
            private String avatar;
            private String content;
            private String ctime;
            private String ctime_timestamp;
            private String file_id;
            private String floor_num;
            private String id;
            private boolean isHot;
            private String is_rich_text;
            private String json_path;
            private String name;
            private String nickname;
            private String obj_id;
            private String parent_id;
            private List<ReplyBean> reply;
            private String school;
            private TarGetParamBean target_params;
            private String to_user_id;
            private String user_id;
            private int type = 0;
            private int otherView = 0;
            private String praise_num = "0";
            private String module_type = "1";
            private String is_del = "0";
            private String is_essence = "0";
            private String question_type = "0";
            private String is_vip = "0";
            private String is_svip = "0";
            private String collection_id = "0";
            private String to_from = "";
            private String is_adopt = "";
            private String is_anonymous = "0";
            private int is_header = 0;
            private String typeName = "最新评论";
            private String on_the_top = "";
            private String ads = "";
            private String is_logout = "";
            private String img_watermark = "";
            public String video_id = "";
            public String watch_permission = "";
            public String status = "";
            private List<String> img = new ArrayList();
            private ImagesBean c_imgs = new ImagesBean();
            private String is_author = "0";
            private String h5_path = "0";
            private String user_identity = "0";
            private String user_identity_color = "#333333";
            private String is_read = "1";
            private String oppose_num = "0";
            private String is_oppose = "0";
            private String reply_num = "0";
            private String reply_primary_id = "";
            private String is_authentication = "";
            private String delete_skill = "0";
            private boolean is_showValue = true;
            private String is_praise = "0";
            private String is_look = "1";

            public static class ReplyBean extends HotBean implements Serializable {
                private boolean is_zhankai;

                public boolean is_zhankai() {
                    return this.is_zhankai;
                }

                public void setIs_zhankai(boolean is_zhankai) {
                    this.is_zhankai = is_zhankai;
                }
            }

            public String getAds() {
                return this.ads;
            }

            public String getApp_type() {
                return this.app_type;
            }

            public String getAvatar() {
                return this.avatar;
            }

            public ImagesBean getC_imgs() {
                return this.c_imgs;
            }

            public String getCollection_id() {
                return this.collection_id;
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

            public String getFile_id() {
                return this.file_id;
            }

            public String getFloor_num() {
                return this.floor_num;
            }

            public String getH5_path() {
                return this.h5_path;
            }

            public String getId() {
                return this.id;
            }

            public List<String> getImg() {
                return this.img;
            }

            public String getImg_watermark() {
                return this.img_watermark;
            }

            public String getIs_adopt() {
                return this.is_adopt;
            }

            public String getIs_anonymous() {
                return this.is_anonymous;
            }

            public String getIs_authentication() {
                return this.is_authentication;
            }

            public String getIs_author() {
                return this.is_author;
            }

            public String getIs_del() {
                return this.is_del;
            }

            public String getIs_essence() {
                return this.is_essence;
            }

            public int getIs_header() {
                return this.is_header;
            }

            public String getIs_logout() {
                return this.is_logout;
            }

            public String getIs_look() {
                return this.is_look;
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

            public String getIs_rich_text() {
                return this.is_rich_text;
            }

            public String getIs_svip() {
                return this.is_svip;
            }

            public String getIs_vip() {
                return this.is_vip;
            }

            public String getJson_path() {
                return this.json_path;
            }

            public String getModule_type() {
                return this.module_type;
            }

            public String getName() {
                return this.name;
            }

            public String getNickname() {
                return this.nickname;
            }

            public String getObj_id() {
                return this.obj_id;
            }

            public String getOn_the_top() {
                return this.on_the_top;
            }

            public String getOppose_num() {
                return this.oppose_num;
            }

            public int getOtherView() {
                return this.otherView;
            }

            public String getParent_id() {
                return this.parent_id;
            }

            public String getPraise_num() {
                return this.praise_num;
            }

            public String getQuestion_type() {
                return this.question_type;
            }

            public List<ReplyBean> getReply() {
                return this.reply;
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

            public String getStatus() {
                return this.status;
            }

            public TarGetParamBean getTarget_params() {
                return this.target_params;
            }

            public String getTo_from() {
                return this.to_from;
            }

            public String getTo_user_id() {
                return this.to_user_id;
            }

            public int getType() {
                return this.type;
            }

            public String getTypeName() {
                return this.typeName;
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

            public String getVideo_id() {
                return this.video_id;
            }

            public String getWatch_permission() {
                return this.watch_permission;
            }

            public boolean isHot() {
                return this.isHot;
            }

            public boolean is_showValue() {
                return this.is_showValue;
            }

            public void setAds(String ads) {
                this.ads = ads;
            }

            public void setApp_type(String app_type) {
                this.app_type = app_type;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setC_imgs(ImagesBean c_imgs) {
                this.c_imgs = c_imgs;
            }

            public void setCollection_id(String collection_id) {
                this.collection_id = collection_id;
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

            public void setFile_id(String file_id) {
                this.file_id = file_id;
            }

            public void setFloor_num(String floor_num) {
                this.floor_num = floor_num;
            }

            public void setH5_path(String h5_path) {
                this.h5_path = h5_path;
            }

            public void setHot(boolean hot) {
                this.isHot = hot;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setImg(List<String> img) {
                this.img = img;
            }

            public void setImg_watermark(String img_watermark) {
                this.img_watermark = img_watermark;
            }

            public void setIs_adopt(String is_adopt) {
                this.is_adopt = is_adopt;
            }

            public void setIs_anonymous(String is_anonymous) {
                this.is_anonymous = is_anonymous;
            }

            public void setIs_authentication(String is_authentication) {
                this.is_authentication = is_authentication;
            }

            public void setIs_author(String is_author) {
                this.is_author = is_author;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
            }

            public void setIs_essence(String is_essence) {
                this.is_essence = is_essence;
            }

            public void setIs_header(int is_header) {
                this.is_header = is_header;
            }

            public void setIs_logout(String is_logout) {
                this.is_logout = is_logout;
            }

            public void setIs_look(String is_look) {
                this.is_look = is_look;
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

            public void setIs_rich_text(String is_rich_text) {
                this.is_rich_text = is_rich_text;
            }

            public void setIs_showValue(boolean is_showValue) {
                this.is_showValue = is_showValue;
            }

            public void setIs_svip(String is_svip) {
                this.is_svip = is_svip;
            }

            public void setIs_vip(String is_vip) {
                this.is_vip = is_vip;
            }

            public void setJson_path(String json_path) {
                this.json_path = json_path;
            }

            public void setModule_type(String module_type) {
                this.module_type = module_type;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setObj_id(String obj_id) {
                this.obj_id = obj_id;
            }

            public void setOn_the_top(String on_the_top) {
                this.on_the_top = on_the_top;
            }

            public void setOppose_num(String oppose_num) {
                this.oppose_num = oppose_num;
            }

            public HotBean setOtherView(int otherView) {
                this.otherView = otherView;
                return this;
            }

            public void setParent_id(String parent_id) {
                this.parent_id = parent_id;
            }

            public void setPraise_num(String praise_num) {
                this.praise_num = praise_num;
            }

            public void setQuestion_type(String question_type) {
                this.question_type = question_type;
            }

            public void setReply(List<ReplyBean> reply) {
                this.reply = reply;
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

            public void setStatus(String status) {
                this.status = status;
            }

            public void setTarget_params(TarGetParamBean target_params) {
                this.target_params = target_params;
            }

            public void setTo_from(String to_from) {
                this.to_from = to_from;
            }

            public void setTo_user_id(String to_user_id) {
                this.to_user_id = to_user_id;
            }

            public void setType(int type) {
                this.type = type;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
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

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public void setWatch_permission(String watch_permission) {
                this.watch_permission = watch_permission;
            }
        }

        public List<HotBean> getAdopt() {
            return this.adopt;
        }

        public String getAdopt_is_more() {
            return this.adopt_is_more;
        }

        public String getAdopt_total() {
            return this.adopt_total;
        }

        public List<HotBean> getHot() {
            return this.hot;
        }

        public String getHot_total() {
            return this.hot_total;
        }

        public String getMore() {
            return this.more;
        }

        public String getMore_hot() {
            return this.more_hot;
        }

        public String getShow_tips() {
            return this.show_tips;
        }

        public List<HotBean> getTime_line() {
            return this.time_line;
        }

        public String getTime_line_total() {
            return this.time_line_total;
        }

        public void setAdopt(List<HotBean> adopt) {
            this.adopt = adopt;
        }

        public void setAdopt_is_more(String adopt_is_more) {
            this.adopt_is_more = adopt_is_more;
        }

        public void setHot(List<HotBean> hot) {
            this.hot = hot;
        }

        public DataBean setHot_total(String hot_total) {
            this.hot_total = hot_total;
            return this;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public DataBean setMore_hot(String more_hot) {
            this.more_hot = more_hot;
            return this;
        }

        public DataBean setShow_tips(String show_tips) {
            this.show_tips = show_tips;
            return this;
        }

        public void setTime_line(List<HotBean> time_line) {
            this.time_line = time_line;
        }

        public DataBean setTime_line_total(String time_line_total) {
            this.time_line_total = time_line_total;
            return this;
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
