package com.psychiatrygarden.bean;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.http.CodeParseJsonAdapter;
import java.util.List;

/* loaded from: classes5.dex */
public class InvitePageBean {
    private DataBean data;
    private String message;

    @SerializedName("code")
    @JsonAdapter(CodeParseJsonAdapter.class)
    private boolean success;

    public static class DataBean {
        private List<String> desc;
        private String download_h5;
        private String invite_code;
        private String invite_image;
        private InviteInfoBean invite_info;
        private String join_status;
        private String poster_image;

        public List<String> getDesc() {
            return this.desc;
        }

        public String getDownload_h5() {
            return this.download_h5;
        }

        public String getInvite_code() {
            return this.invite_code;
        }

        public String getInvite_image() {
            return this.invite_image;
        }

        public InviteInfoBean getInvite_info() {
            return this.invite_info;
        }

        public String getJoin_status() {
            return this.join_status;
        }

        public String getPoster_image() {
            return this.poster_image;
        }

        public void setDesc(List<String> desc) {
            this.desc = desc;
        }

        public void setDownload_h5(String download_h5) {
            this.download_h5 = download_h5;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public void setInvite_image(String invite_image) {
            this.invite_image = invite_image;
        }

        public void setInvite_info(InviteInfoBean invite_info) {
            this.invite_info = invite_info;
        }

        public void setJoin_status(String join_status) {
            this.join_status = join_status;
        }

        public void setPoster_image(String poster_image) {
            this.poster_image = poster_image;
        }
    }

    public static class InviteInfoBean {
        private String code_multiple;
        private String left_invite_num;
        private String message;

        public String getCode_multiple() {
            return this.code_multiple;
        }

        public String getLeft_invite_num() {
            return this.left_invite_num;
        }

        public String getMessage() {
            return this.message;
        }

        public void setCode_multiple(String code_multiple) {
            this.code_multiple = code_multiple;
        }

        public void setLeft_invite_num(String left_invite_num) {
            this.left_invite_num = left_invite_num;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public DataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
