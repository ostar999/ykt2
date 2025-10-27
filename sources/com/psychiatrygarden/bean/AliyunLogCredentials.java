package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class AliyunLogCredentials {
    private String AccessKeyId;
    private String AccessKeySecret;
    private String Expiration;
    private String SecurityToken;
    private String endpoint;
    private String logstore;
    private String project;

    public String getAccessKeyId() {
        return this.AccessKeyId;
    }

    public String getAccessKeySecret() {
        return this.AccessKeySecret;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public String getExpiration() {
        return this.Expiration;
    }

    public String getLogstore() {
        return this.logstore;
    }

    public String getProject() {
        return this.project;
    }

    public String getSecurityToken() {
        return this.SecurityToken;
    }

    public void setAccessKeyId(String AccessKeyId) {
        this.AccessKeyId = AccessKeyId;
    }

    public void setAccessKeySecret(String AccessKeySecret) {
        this.AccessKeySecret = AccessKeySecret;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setExpiration(String Expiration) {
        this.Expiration = Expiration;
    }

    public void setLogstore(String logstore) {
        this.logstore = logstore;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setSecurityToken(String SecurityToken) {
        this.SecurityToken = SecurityToken;
    }
}
