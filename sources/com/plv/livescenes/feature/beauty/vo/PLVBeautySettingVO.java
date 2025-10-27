package com.plv.livescenes.feature.beauty.vo;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVBeautySettingVO {
    private Integer code;
    private Object data;
    private boolean encryption;
    private Error error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class Data {
        private String key;
        private String materialMd5;
        private String materialUrl;
        private String nonce;
        private String packageName;
        private String secret;
        private String userId;

        public String getKey() {
            return this.key;
        }

        public String getMaterialMd5() {
            return this.materialMd5;
        }

        public String getMaterialUrl() {
            return this.materialUrl;
        }

        public String getNonce() {
            return this.nonce;
        }

        public String getPackageName() {
            return this.packageName;
        }

        public String getSecret() {
            return this.secret;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setKey(String str) {
            this.key = str;
        }

        public void setMaterialMd5(String str) {
            this.materialMd5 = str;
        }

        public void setMaterialUrl(String str) {
            this.materialUrl = str;
        }

        public void setNonce(String str) {
            this.nonce = str;
        }

        public void setPackageName(String str) {
            this.packageName = str;
        }

        public void setSecret(String str) {
            this.secret = str;
        }

        public void setUserId(String str) {
            this.userId = str;
        }

        public String toString() {
            return "Data{key='" + this.key + CharPool.SINGLE_QUOTE + ", materialMd5='" + this.materialMd5 + CharPool.SINGLE_QUOTE + ", materialUrl='" + this.materialUrl + CharPool.SINGLE_QUOTE + ", nonce='" + this.nonce + CharPool.SINGLE_QUOTE + ", packageName='" + this.packageName + CharPool.SINGLE_QUOTE + ", secret='" + this.secret + CharPool.SINGLE_QUOTE + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public static class Error {
        private Integer code;
        private String desc;

        public Integer getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setCode(Integer num) {
            this.code = num;
        }

        public void setDesc(String str) {
            this.desc = str;
        }

        public String toString() {
            return "Error{code=" + this.code + ", desc='" + this.desc + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public Data getData() {
        return (Data) this.data;
    }

    public Object getDataObj() {
        return this.data;
    }

    public Error getError() {
        return this.error;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getStatus() {
        return this.status;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setSuccess(Boolean bool) {
        this.success = bool;
    }

    public String toString() {
        return "PLVBeautySettingVO{code=" + this.code + ", data=" + this.data + ", error=" + this.error + ", requestId='" + this.requestId + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", success=" + this.success + ", encryption=" + this.encryption + '}';
    }
}
