package com.ykb.ebook.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.io.Serializable;
import java.util.List;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes7.dex */
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

            public void setIdentity(String str) {
                this.identity = str;
            }

            public void setIdentity_description(String str) {
                this.identity_description = str;
            }

            public void setUser_identity_color(String str) {
                this.user_identity_color = str;
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

        public DataBean setAgree_num(int i2) {
            this.agree_num = i2;
            return this;
        }

        public void setAnalysis(String str) {
            this.analysis = str;
        }

        public void setAvatar(String str) {
            this.avatar = str;
        }

        public DataBean setComment_number(int i2) {
            this.comment_number = i2;
            return this;
        }

        public void setCreated_at(String str) {
            this.created_at = str;
        }

        public void setCreated_at_str(String str) {
            this.created_at_str = str;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setIdentity(IdentityBean identityBean) {
            this.identity = identityBean;
        }

        public void setImages(List<String> list) {
            this.images = list;
        }

        public void setIs_agree(String str) {
            this.is_agree = str;
        }

        public void setIs_hidden(String str) {
            this.is_hidden = str;
        }

        public void setIs_opposed(String str) {
            this.is_opposed = str;
        }

        public void setIs_vip(String str) {
            this.is_vip = str;
        }

        public void setNickname(String str) {
            this.nickname = str;
        }

        public DataBean setOpposed_num(int i2) {
            this.opposed_num = i2;
            return this;
        }

        public void setQuestion_id(String str) {
            this.question_id = str;
        }

        public void setSchool(String str) {
            this.school = str;
        }

        public void setSubjective_answering(String str) {
            this.subjective_answering = str;
        }

        public DataBean setTypeStr(String str) {
            this.typeStr = str;
            return this;
        }

        public void setUser_id(String str) {
            this.user_id = str;
        }

        public DataBean setViewType(int i2) {
            this.viewType = i2;
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

    public void setCode(String str) {
        this.code = str;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setServer_time(String str) {
        this.server_time = str;
    }
}
