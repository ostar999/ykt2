package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class UserSignInfo {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;
    private boolean success;

    public static class DataBean {
        private String is_sign;
        private String sign_is_open;

        public String getIs_sign() {
            return this.is_sign;
        }

        public String getSign_is_open() {
            return this.sign_is_open;
        }

        public void setIs_sign(String is_sign) {
            this.is_sign = is_sign;
        }

        public void setSign_is_open(String sign_is_open) {
            this.sign_is_open = sign_is_open;
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

    public boolean isSuccess() {
        return this.success;
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

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
