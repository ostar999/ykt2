package com.aliyuncs.sts.model.v20150401;

import com.aliyuncs.AcsResponse;
import com.aliyuncs.sts.transform.v20150401.GetFederationTokenResponseUnmarshaller;
import com.aliyuncs.transform.UnmarshallerContext;

/* loaded from: classes2.dex */
public class GetFederationTokenResponse extends AcsResponse {
    private Credentials credentials;
    private FederatedUser federatedUser;
    private String requestId;

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

    public static class FederatedUser {
        private String arn;
        private String federatedUserId;

        public String getArn() {
            return this.arn;
        }

        public String getFederatedUserId() {
            return this.federatedUserId;
        }

        public void setArn(String str) {
            this.arn = str;
        }

        public void setFederatedUserId(String str) {
            this.federatedUserId = str;
        }
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public FederatedUser getFederatedUser() {
        return this.federatedUser;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setFederatedUser(FederatedUser federatedUser) {
        this.federatedUser = federatedUser;
    }

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public GetFederationTokenResponse getInstance(UnmarshallerContext unmarshallerContext) {
        return GetFederationTokenResponseUnmarshaller.unmarshall(this, unmarshallerContext);
    }
}
