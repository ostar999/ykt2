package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class QrDialogBean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean {
        private String qr_code;
        private String title;
        private String wechat;

        public String getQr_code() {
            return this.qr_code;
        }

        public String getTitle() {
            return this.title;
        }

        public String getWechat() {
            return this.wechat;
        }

        public void setQr_code(String qr_code) {
            this.qr_code = qr_code;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
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
