package com.psychiatrygarden.activity.answer.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.io.Serializable;
import java.util.List;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes5.dex */
public class AnalysisBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean implements MultiItemEntity, Serializable {
        private int agree_num;
        private String analysis;
        private String avatar;
        private int comment_number;
        private String created_at;
        private String created_at_str;
        private IdentityBean identity;
        private List<String> images;
        private String is_hidden;
        private String nickname;
        private int opposed_num;
        private String question_id;
        private String user_id;
        public int viewType = 1;
        public String typeStr = ServletHandler.__DEFAULT_SERVLET;
        private String id = "";
        private String school = "";
        private String is_vip = "";
        private String is_agree = "";
        private String is_opposed = "";
        private String subjective_answering = "";

        public static class IdentityBean implements Serializable {
            private String identity;
            private String identity_description;
            private String user_identity_color;

            public String getIdentity() {
                return this.identity;
            }

            public String getIdentity_description() {
                return this.identity_description;
            }

            public String getUser_identity_color() {
                return this.user_identity_color;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }

            public void setIdentity_description(String identity_description) {
                this.identity_description = identity_description;
            }

            public void setUser_identity_color(String user_identity_color) {
                this.user_identity_color = user_identity_color;
            }
        }

        public int getAgree_num() {
            return this.agree_num;
        }

        public String getAnalysis() {
            return this.analysis;
        }

        public String getAvatar() {
            return this.avatar;
        }

        public int getComment_number() {
            return this.comment_number;
        }

        public String getCreated_at() {
            return this.created_at;
        }

        public String getCreated_at_str() {
            return this.created_at_str;
        }

        public String getId() {
            return this.id;
        }

        public IdentityBean getIdentity() {
            return this.identity;
        }

        public List<String> getImages() {
            return this.images;
        }

        public String getIs_agree() {
            return this.is_agree;
        }

        public String getIs_hidden() {
            return this.is_hidden;
        }

        public String getIs_opposed() {
            return this.is_opposed;
        }

        public String getIs_vip() {
            return this.is_vip;
        }

        @Override // com.chad.library.adapter.base.entity.MultiItemEntity
        public int getItemType() {
            return this.viewType;
        }

        public String getNickname() {
            return this.nickname;
        }

        public int getOpposed_num() {
            return this.opposed_num;
        }

        public String getQuestion_id() {
            return this.question_id;
        }

        public String getSchool() {
            return this.school;
        }

        public String getSubjective_answering() {
            return this.subjective_answering;
        }

        public String getTypeStr() {
            return this.typeStr;
        }

        public String getUser_id() {
            return this.user_id;
        }

        public int getViewType() {
            return this.viewType;
        }

        public DataBean setAgree_num(int agree_num) {
            this.agree_num = agree_num;
            return this;
        }

        public void setAnalysis(String analysis) {
            this.analysis = analysis;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public DataBean setComment_number(int comment_number) {
            this.comment_number = comment_number;
            return this;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public void setCreated_at_str(String created_at_str) {
            this.created_at_str = created_at_str;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIdentity(IdentityBean identity) {
            this.identity = identity;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public void setIs_agree(String is_agree) {
            this.is_agree = is_agree;
        }

        public void setIs_hidden(String is_hidden) {
            this.is_hidden = is_hidden;
        }

        public void setIs_opposed(String is_opposed) {
            this.is_opposed = is_opposed;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public DataBean setOpposed_num(int opposed_num) {
            this.opposed_num = opposed_num;
            return this;
        }

        public void setQuestion_id(String question_id) {
            this.question_id = question_id;
        }

        public void setSchool(String school) {
            this.school = school;
        }

        public void setSubjective_answering(String subjective_answering) {
            this.subjective_answering = subjective_answering;
        }

        public DataBean setTypeStr(String typeStr) {
            this.typeStr = typeStr;
            return this;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public DataBean setViewType(int viewType) {
            this.viewType = viewType;
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
