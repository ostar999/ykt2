package com.aliyun.player.aliyunplayerbase.bean;

/* loaded from: classes2.dex */
public class AliyunSts {
    private int code;
    private StsBean data;
    private String message;

    public class StsBean {
        private String accessKeyId;
        private String accessKeySecret;
        private String expiration;
        private String securityToken;
        private String videoId;

        public StsBean() {
        }

        public String getAccessKeyId() {
            return this.accessKeyId;
        }

        public String getAccessKeySecret() {
            return this.accessKeySecret;
        }

        public String getExpiration() {
            return this.expiration;
        }

        public String getSecurityToken() {
            return this.securityToken;
        }

        public String getVideoId() {
            return this.videoId;
        }

        public void setAccessKeyId(String str) {
            this.accessKeyId = str;
        }

        public void setAccessKeySecret(String str) {
            this.accessKeySecret = str;
        }

        public void setExpiration(String str) {
            this.expiration = str;
        }

        public void setSecurityToken(String str) {
            this.securityToken = str;
        }

        public void setVideoId(String str) {
            this.videoId = str;
        }
    }

    public int getCode() {
        return this.code;
    }

    public StsBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(StsBean stsBean) {
        this.data = stsBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }
}
