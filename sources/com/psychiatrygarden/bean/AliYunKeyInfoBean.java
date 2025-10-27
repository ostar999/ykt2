package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class AliYunKeyInfoBean {
    private String code;
    private AliyunKeyData data;
    private String message;

    public static class AliyunKeyData {
        private String akId;
        private String akSecret;
        private String st;

        public String getAkId() {
            return this.akId;
        }

        public String getAkSecret() {
            return this.akSecret;
        }

        public String getSt() {
            return this.st;
        }

        public void setAkId(String akId) {
            this.akId = akId;
        }

        public void setAkSecret(String akSecret) {
            this.akSecret = akSecret;
        }

        public void setSt(String st) {
            this.st = st;
        }
    }

    public String getCode() {
        return this.code;
    }

    public AliyunKeyData getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(AliyunKeyData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
