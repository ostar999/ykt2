package com.plv.livescenes.model;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVUploadTokenVO {
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class DataBean {
        private String accessid;
        private String callback;
        private String dir;
        private String expire;
        private String host;
        private String policy;
        private String signature;

        public String getAccessid() {
            return this.accessid;
        }

        public String getCallback() {
            return this.callback;
        }

        public String getDir() {
            return this.dir;
        }

        public String getExpire() {
            return this.expire;
        }

        public String getHost() {
            return this.host;
        }

        public String getPolicy() {
            return this.policy;
        }

        public String getSignature() {
            return this.signature;
        }

        public void setAccessid(String str) {
            this.accessid = str;
        }

        public void setCallback(String str) {
            this.callback = str;
        }

        public void setDir(String str) {
            this.dir = str;
        }

        public void setExpire(String str) {
            this.expire = str;
        }

        public void setHost(String str) {
            this.host = str;
        }

        public void setPolicy(String str) {
            this.policy = str;
        }

        public void setSignature(String str) {
            this.signature = str;
        }

        public String toString() {
            return "DataBean{accessid='" + this.accessid + CharPool.SINGLE_QUOTE + ", signature='" + this.signature + CharPool.SINGLE_QUOTE + ", expire='" + this.expire + CharPool.SINGLE_QUOTE + ", host='" + this.host + CharPool.SINGLE_QUOTE + ", callback='" + this.callback + CharPool.SINGLE_QUOTE + ", dir='" + this.dir + CharPool.SINGLE_QUOTE + ", policy='" + this.policy + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public int getCode() {
        return this.code;
    }

    public DataBean getData() {
        return (DataBean) this.data;
    }

    public Object getDataObj() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVUploadTokenVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
