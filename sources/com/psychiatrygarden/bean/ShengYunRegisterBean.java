package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class ShengYunRegisterBean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean {
        private String sy_mobile;

        public String getSy_mobile() {
            return this.sy_mobile;
        }

        public void setSy_mobile(String sy_mobile) {
            this.sy_mobile = sy_mobile;
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
