package com.aliyuncs.sts.transform.v20150401;

import com.aliyuncs.sts.model.v20150401.GetFederationTokenResponse;
import com.aliyuncs.transform.UnmarshallerContext;

/* loaded from: classes2.dex */
public class GetFederationTokenResponseUnmarshaller {
    public static GetFederationTokenResponse unmarshall(GetFederationTokenResponse getFederationTokenResponse, UnmarshallerContext unmarshallerContext) {
        getFederationTokenResponse.setRequestId(unmarshallerContext.stringValue("GetFederationTokenResponse.RequestId"));
        GetFederationTokenResponse.Credentials credentials = new GetFederationTokenResponse.Credentials();
        credentials.setSecurityToken(unmarshallerContext.stringValue("GetFederationTokenResponse.Credentials.SecurityToken"));
        credentials.setAccessKeySecret(unmarshallerContext.stringValue("GetFederationTokenResponse.Credentials.AccessKeySecret"));
        credentials.setAccessKeyId(unmarshallerContext.stringValue("GetFederationTokenResponse.Credentials.AccessKeyId"));
        credentials.setExpiration(unmarshallerContext.stringValue("GetFederationTokenResponse.Credentials.Expiration"));
        getFederationTokenResponse.setCredentials(credentials);
        GetFederationTokenResponse.FederatedUser federatedUser = new GetFederationTokenResponse.FederatedUser();
        federatedUser.setArn(unmarshallerContext.stringValue("GetFederationTokenResponse.FederatedUser.Arn"));
        federatedUser.setFederatedUserId(unmarshallerContext.stringValue("GetFederationTokenResponse.FederatedUser.FederatedUserId"));
        getFederationTokenResponse.setFederatedUser(federatedUser);
        return getFederationTokenResponse;
    }
}
