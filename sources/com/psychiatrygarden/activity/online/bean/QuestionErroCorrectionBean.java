package com.psychiatrygarden.activity.online.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionErroCorrectionBean implements Serializable {
    private String code;
    private List<DataDTO> data;
    private String message;
    private String server_time;

    public static class DataDTO implements Serializable {
        private String avatar;
        private String content;
        private String content_explain;
        private String ctime;
        private String error_type_id;
        private String error_type_title;
        private String give_vip;
        private String give_vip_str;
        private String id;
        private String is_adopt;
        private String is_oppose;
        private String is_praise;
        private String name;
        private String nickname;
        private String oppose_num;
        private String praise_num;
        private String question_id;
        private String reply_num;
        private String school;
        private String timeStr;
        private String user_id;
        private int type = 0;
        private int otherView = 0;
        private boolean is_showValue = true;
        private String is_del = "0";

        public String getAvatar() {
            return this.avatar;
        }

        public String getContent() {
            return this.content;
        }

        public String getContent_explain() {
            return this.content_explain;
        }

        public String getCtime() {
            return this.ctime;
        }

        public String getError_type_id() {
            return this.error_type_id;
        }

        public String getError_type_title() {
            return this.error_type_title;
        }

        public String getGive_vip() {
            return this.give_vip;
        }

        public String getGive_vip_str() {
            return this.give_vip_str;
        }

        public String getId() {
            return this.id;
        }

        public String getIs_adopt() {
            return this.is_adopt;
        }

        public String getIs_del() {
            return this.is_del;
        }

        public String getIs_oppose() {
            return this.is_oppose;
        }

        public String getIs_praise() {
            return this.is_praise;
        }

        public String getName() {
            return this.name;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getOppose_num() {
            return this.oppose_num;
        }

        public int getOtherView() {
            return this.otherView;
        }

        public String getPraise_num() {
            return this.praise_num;
        }

        public String getQuestion_id() {
            return this.question_id;
        }

        public String getReply_num() {
            return this.reply_num;
        }

        public String getSchool() {
            return this.school;
        }

        public String getTimeStr() {
            return this.timeStr;
        }

        public int getType() {
            return this.type;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public boolean is_showValue() {
            return this.is_showValue;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setContent_explain(String content_explain) {
            this.content_explain = content_explain;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setError_type_id(String error_type_id) {
            this.error_type_id = error_type_id;
        }

        public void setError_type_title(String error_type_title) {
            this.error_type_title = error_type_title;
        }

        public void setGive_vip(String give_vip) {
            this.give_vip = give_vip;
        }

        public void setGive_vip_str(String give_vip_str) {
            this.give_vip_str = give_vip_str;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_adopt(String is_adopt) {
            this.is_adopt = is_adopt;
        }

        public void setIs_del(String is_del) {
            this.is_del = is_del;
        }

        public void setIs_oppose(String is_oppose) {
            this.is_oppose = is_oppose;
        }

        public void setIs_praise(String is_praise) {
            this.is_praise = is_praise;
        }

        public void setIs_showValue(boolean is_showValue) {
            this.is_showValue = is_showValue;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setOppose_num(String oppose_num) {
            this.oppose_num = oppose_num;
        }

        public void setOtherView(int otherView) {
            this.otherView = otherView;
        }

        public void setPraise_num(String praise_num) {
            this.praise_num = praise_num;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public void setReply_num(String reply_num) {
            this.reply_num = reply_num;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setTimeStr(String timeStr) {
            this.timeStr = timeStr;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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
