package com.aliyuncs.sts.transform.v20150401;

import com.aliyuncs.sts.model.v20150401.AssumeRoleWithServiceIdentityResponse;
import com.aliyuncs.transform.UnmarshallerContext;

/* loaded from: classes2.dex */
public class AssumeRoleWithServiceIdentityResponseUnmarshaller {
    public static AssumeRoleWithServiceIdentityResponse unmarshall(AssumeRoleWithServiceIdentityResponse assumeRoleWithServiceIdentityResponse, UnmarshallerContext unmarshallerContext) {
        assumeRoleWithServiceIdentityResponse.setRequestId(unmarshallerContext.stringValue("AssumeRoleWithServiceIdentityResponse.RequestId"));
        AssumeRoleWithServiceIdentityResponse.Credentials credentials = new AssumeRoleWithServiceIdentityResponse.Credentials();
        credentials.setSecurityToken(unmarshallerContext.stringValue("AssumeRoleWithServiceIdentityResponse.Credentials.SecurityToken"));
        credentials.setAccessKeySecret(unmarshallerContext.stringValue("AssumeRoleWithServiceIdentityResponse.Credentials.AccessKeySecret"));
        credentials.setAccessKeyId(unmarshallerContext.stringValue("AssumeRoleWithServiceIdentityResponse.Credentials.AccessKeyId"));
        credentials.setExpiration(unmarshallerContext.stringValue("AssumeRoleWithServiceIdentityResponse.Credentials.Expiration"));
        assumeRoleWithServiceIdentityResponse.setCredentials(credentials);
        AssumeRoleWithServiceIdentityResponse.AssumedRoleUser assumedRoleUser = new AssumeRoleWithServiceIdentityResponse.AssumedRoleUser();
        assumedRoleUser.setArn(unmarshallerContext.stringValue("AssumeRoleWithServiceIdentityResponse.AssumedRoleUser.Arn"));
        assumedRoleUser.setAssumedRoleId(unmarshallerContext.stringValue("AssumeRoleWithServiceIdentityResponse.AssumedRoleUser.AssumedRoleId"));
        assumeRoleWithServiceIdentityResponse.setAssumedRoleUser(assumedRoleUser);
        return assumeRoleWithServiceIdentityResponse;
    }
}
