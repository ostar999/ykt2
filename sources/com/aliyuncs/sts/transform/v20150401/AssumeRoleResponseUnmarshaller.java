package com.aliyuncs.sts.transform.v20150401;

import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.aliyuncs.transform.UnmarshallerContext;

/* loaded from: classes2.dex */
public class AssumeRoleResponseUnmarshaller {
    public static AssumeRoleResponse unmarshall(AssumeRoleResponse assumeRoleResponse, UnmarshallerContext unmarshallerContext) {
        assumeRoleResponse.setRequestId(unmarshallerContext.stringValue("AssumeRoleResponse.RequestId"));
        AssumeRoleResponse.Credentials credentials = new AssumeRoleResponse.Credentials();
        credentials.setSecurityToken(unmarshallerContext.stringValue("AssumeRoleResponse.Credentials.SecurityToken"));
        credentials.setAccessKeySecret(unmarshallerContext.stringValue("AssumeRoleResponse.Credentials.AccessKeySecret"));
        credentials.setAccessKeyId(unmarshallerContext.stringValue("AssumeRoleResponse.Credentials.AccessKeyId"));
        credentials.setExpiration(unmarshallerContext.stringValue("AssumeRoleResponse.Credentials.Expiration"));
        assumeRoleResponse.setCredentials(credentials);
        AssumeRoleResponse.AssumedRoleUser assumedRoleUser = new AssumeRoleResponse.AssumedRoleUser();
        assumedRoleUser.setArn(unmarshallerContext.stringValue("AssumeRoleResponse.AssumedRoleUser.Arn"));
        assumedRoleUser.setAssumedRoleId(unmarshallerContext.stringValue("AssumeRoleResponse.AssumedRoleUser.AssumedRoleId"));
        assumeRoleResponse.setAssumedRoleUser(assumedRoleUser);
        return assumeRoleResponse;
    }
}
