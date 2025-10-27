package com.plv.livescenes.upload.model;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVPPTUploadTokenVO {
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public static class DataBean {
        private String accessId;
        private String accessKey;
        private String autoId;
        private String bucket;
        private String callback;
        private String convertStatus;
        private String dir;
        private String encodedCallback;
        private String endpoint;
        private String expiration;
        private String fileId;
        private String host;
        private String object;
        private String token;

        public String getAccessId() {
            return this.accessId;
        }

        public String getAccessKey() {
            return this.accessKey;
        }

        public String getAutoId() {
            return this.autoId;
        }

        public String getBucket() {
            return this.bucket;
        }

        public String getCallback() {
            return this.callback;
        }

        public String getConvertStatus() {
            return this.convertStatus;
        }

        public String getDir() {
            return this.dir;
        }

        public String getEncodedCallback() {
            return this.encodedCallback;
        }

        public String getEndpoint() {
            return this.endpoint;
        }

        public String getExpiration() {
            return this.expiration;
        }

        public String getFileId() {
            return this.fileId;
        }

        public String getHost() {
            return this.host;
        }

        public String getObject() {
            return this.object;
        }

        public String getToken() {
            return this.token;
        }

        public void setAccessId(String str) {
            this.accessId = str;
        }

        public void setAccessKey(String str) {
            this.accessKey = str;
        }

        public void setAutoId(String str) {
            this.autoId = str;
        }

        public void setBucket(String str) {
            this.bucket = str;
        }

        public void setCallback(String str) {
            this.callback = str;
        }

        public void setConvertStatus(String str) {
            this.convertStatus = str;
        }

        public void setDir(String str) {
            this.dir = str;
        }

        public void setEncodedCallback(String str) {
            this.encodedCallback = str;
        }

        public void setEndpoint(String str) {
            this.endpoint = str;
        }

        public void setExpiration(String str) {
            this.expiration = str;
        }

        public void setFileId(String str) {
            this.fileId = str;
        }

        public void setHost(String str) {
            this.host = str;
        }

        public void setObject(String str) {
            this.object = str;
        }

        public void setToken(String str) {
            this.token = str;
        }

        public String toString() {
            return "DataBean{encodedCallback='" + this.encodedCallback + CharPool.SINGLE_QUOTE + ", autoId='" + this.autoId + CharPool.SINGLE_QUOTE + ", expiration='" + this.expiration + CharPool.SINGLE_QUOTE + ", dir='" + this.dir + CharPool.SINGLE_QUOTE + ", token='" + this.token + CharPool.SINGLE_QUOTE + ", accessId='" + this.accessId + CharPool.SINGLE_QUOTE + ", bucket='" + this.bucket + CharPool.SINGLE_QUOTE + ", endpoint='" + this.endpoint + CharPool.SINGLE_QUOTE + ", accessKey='" + this.accessKey + CharPool.SINGLE_QUOTE + ", host='" + this.host + CharPool.SINGLE_QUOTE + ", callback='" + this.callback + CharPool.SINGLE_QUOTE + ", convertStatus='" + this.convertStatus + CharPool.SINGLE_QUOTE + ", fileId='" + this.fileId + CharPool.SINGLE_QUOTE + ", object='" + this.object + CharPool.SINGLE_QUOTE + '}';
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
        return "PLVPPTUploadTokenVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + '}';
    }
}
