package com.aliyuncs.sts.model.v20150401;

import com.aliyuncs.RpcAcsRequest;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;

/* loaded from: classes2.dex */
public class AssumeRoleWithServiceIdentityRequest extends RpcAcsRequest<AssumeRoleWithServiceIdentityResponse> {
    private String assumeRoleFor;
    private Long durationSeconds;
    private String externalId;
    private String policy;
    private String roleArn;
    private String roleSessionName;
    private String serialNumber;
    private String tokenCode;

    public AssumeRoleWithServiceIdentityRequest() {
        super("Sts", "2015-04-01", "AssumeRoleWithServiceIdentity");
        setProtocol(ProtocolType.HTTPS);
        setMethod(MethodType.POST);
    }

    public String getAssumeRoleFor() {
        return this.assumeRoleFor;
    }

    public Long getDurationSeconds() {
        return this.durationSeconds;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public String getPolicy() {
        return this.policy;
    }

    public Class<AssumeRoleWithServiceIdentityResponse> getResponseClass() {
        return AssumeRoleWithServiceIdentityResponse.class;
    }

    public String getRoleArn() {
        return this.roleArn;
    }

    public String getRoleSessionName() {
        return this.roleSessionName;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public String getTokenCode() {
        return this.tokenCode;
    }

    public void setAssumeRoleFor(String str) {
        this.assumeRoleFor = str;
        putQueryParameter("AssumeRoleFor", str);
    }

    public void setDurationSeconds(Long l2) {
        this.durationSeconds = l2;
        putQueryParameter("DurationSeconds", String.valueOf(l2));
    }

    public void setExternalId(String str) {
        this.externalId = str;
        putQueryParameter("ExternalId", str);
    }

    public void setPolicy(String str) {
        this.policy = str;
        putQueryParameter("Policy", str);
    }

    public void setRoleArn(String str) {
        this.roleArn = str;
        putQueryParameter("RoleArn", str);
    }

    public void setRoleSessionName(String str) {
        this.roleSessionName = str;
        putQueryParameter("RoleSessionName", str);
    }

    public void setSerialNumber(String str) {
        this.serialNumber = str;
        putQueryParameter("SerialNumber", str);
    }

    public void setTokenCode(String str) {
        this.tokenCode = str;
        putQueryParameter("TokenCode", str);
    }
}
