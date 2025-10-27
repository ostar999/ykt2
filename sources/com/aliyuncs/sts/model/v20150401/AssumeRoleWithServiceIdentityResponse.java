package com.aliyuncs.sts.model.v20150401;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.sts.transform.v20150401.AssumeRoleWithServiceIdentityResponseUnmarshaller;
import com.aliyuncs.transform.UnmarshallerContext;

/* loaded from: classes2.dex */
public class AssumeRoleWithServiceIdentityResponse extends AcsResponse {
    private AssumedRoleUser assumedRoleUser;
    private Credentials credentials;
    private String requestId;

    public static class AssumedRoleUser {
        private String arn;
        private String assumedRoleId;

        public String getArn() {
            return this.arn;
        }

        public String getAssumedRoleId() {
            return this.assumedRoleId;
        }

        public void setArn(String str) {
            this.arn = str;
        }

        public void setAssumedRoleId(String str) {
            this.assumedRoleId = str;
        }
    }

    public static class Credentials {
        private String accessKeyId;
        private String accessKeySecret;
        private String expiration;
        private String securityToken;

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
    }

    public AssumedRoleUser getAssumedRoleUser() {
        return this.assumedRoleUser;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setAssumedRoleUser(AssumedRoleUser assumedRoleUser) {
        this.assumedRoleUser = assumedRoleUser;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public AssumeRoleWithServiceIdentityResponse getInstance(UnmarshallerContext unmarshallerContext) {
        return AssumeRoleWithServiceIdentityResponseUnmarshaller.unmarshall(this, unmarshallerContext);
    }
}
