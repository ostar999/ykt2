package com.psychiatrygarden.activity.purchase.beans;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class CommodityEvaluationBean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean {
        private CountBean count;
        private List<TimeLineBean> time_line;

        public static class CountBean {
            private String all;
            private String bad;
            private String fine;
            private String notBad;
            private String picture;

            public String getAll() {
                return this.all;
            }

            public String getBad() {
                return this.bad;
            }

            public String getFine() {
                return this.fine;
            }

            public String getNotBad() {
                return this.notBad;
            }

            public String getPicture() {
                return this.picture;
            }

            public void setAll(String all) {
                this.all = all;
            }

            public void setBad(String bad) {
                this.bad = bad;
            }

            public void setFine(String fine) {
                this.fine = fine;
            }

            public void setNotBad(String notBad) {
                this.notBad = notBad;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }
        }

        public static class TimeLineBean {
            private String avatar;
            private String buy_time;
            private String content;
            private String courseName;
            private String ctime;
            private String ctime_timestamp;
            private String delete_skill;
            private String floor_num;
            private String id;
            private String is_del;
            private String is_essence;
            private String is_oppose;
            private String is_praise;
            private String is_read;
            private String is_vip;
            private String module_type;
            private String nickname;
            private String obj_id;
            private String oppose_num;
            private String praise_num;
            private List<ReplyBean> reply;
            private String reply_num;
            private String school;
            private String user_id;
            private String grade = "5";
            private List<String> imgs = new ArrayList();

            public static class ReplyBean {
                private String avatar;
                private String content;
                private String ctime;
                private String delete_skill;
                private String floor_num;
                private String grade;
                private String id;
                private List<String> imgs;
                private String is_del;
                private String is_essence;
                private String is_oppose;
                private String is_praise;
                private String is_read;
                private String module_type;
                private String nickname;
                private String obj_id;
                private String oppose_num;
                private String praise_num;
                private String reply_num;
                private String school;
                private String user_id;

                public String getAvatar() {
                    return this.avatar;
                }

                public String getContent() {
                    return this.content;
                }

                public String getCtime() {
                    return this.ctime;
                }

                public String getDelete_skill() {
                    return this.delete_skill;
                }

                public String getFloor_num() {
                    return this.floor_num;
                }

                public String getGrade() {
                    return this.grade;
                }

                public String getId() {
                    return this.id;
                }

                public List<String> getImgs() {
                    return this.imgs;
                }

                public String getIs_del() {
                    return this.is_del;
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

                public String getModule_type() {
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

                public String getReply_num() {
                    return this.reply_num;
                }

                public String getSchool() {
                    return this.school;
                }

                public String getUser_id() {
                    return this.user_id;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public void setCtime(String ctime) {
                    this.ctime = ctime;
                }

                public void setDelete_skill(String delete_skill) {
                    this.delete_skill = delete_skill;
                }

                public void setFloor_num(String floor_num) {
                    this.floor_num = floor_num;
                }

                public void setGrade(String grade) {
                    this.grade = grade;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setImgs(List<String> imgs) {
                    this.imgs = imgs;
                }

                public void setIs_del(String is_del) {
                    this.is_del = is_del;
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

                public void setModule_type(String module_type) {
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

                public void setReply_num(String reply_num) {
                    this.reply_num = reply_num;
                }

                public void setSchool(String school) {
                    this.school = school;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }
            }

            public String getAvatar() {
                return this.avatar;
            }

            public String getBuy_time() {
                return this.buy_time;
            }

            public String getContent() {
                return this.content;
            }

            public String getCourseName() {
                return this.courseName;
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

            public String getGrade() {
                return this.grade;
            }

            public String getId() {
                return this.id;
            }

            public List<String> getImgs() {
                return this.imgs;
            }

            public String getIs_del() {
                return this.is_del;
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

            public String getObj_id() {
                return this.obj_id;
            }

            public String getOppose_num() {
                return this.oppose_num;
            }

            public String getPraise_num() {
                return this.praise_num;
            }

            public List<ReplyBean> getReply() {
                return this.reply;
            }

            public String getReply_num() {
                return this.reply_num;
            }

            public String getSchool() {
                return this.school;
            }

            public String getUser_id() {
                return this.user_id;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setBuy_time(String buy_time) {
                this.buy_time = buy_time;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
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

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setImgs(List<String> imgs) {
                this.imgs = imgs;
            }

            public void setIs_del(String is_del) {
                this.is_del = is_del;
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

            public void setIs_vip(String is_vip) {
                this.is_vip = is_vip;
            }

            public void setModule_type(String module_type) {
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

            public void setReply(List<ReplyBean> reply) {
                this.reply = reply;
            }

            public void setReply_num(String reply_num) {
                this.reply_num = reply_num;
            }

            public void setSchool(String school) {
                this.school = school;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }
        }

        public CountBean getCount() {
            return this.count;
        }

        public List<TimeLineBean> getTime_line() {
            return this.time_line;
        }

        public void setCount(CountBean count) {
            this.count = count;
        }

        public void setTime_line(List<TimeLineBean> time_line) {
            this.time_line = time_line;
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
