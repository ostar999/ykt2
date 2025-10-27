package com.aliyuncs.sts.model.v20150401;

import com.aliyuncs.RpcAcsRequest;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;

/* loaded from: classes2.dex */
public class GetFederationTokenRequest extends RpcAcsRequest<GetFederationTokenResponse> {
    private Long durationSeconds;
    private String name;
    private String policy;

    public GetFederationTokenRequest() {
        super("Sts", "2015-04-01", "GetFederationToken");
        setProtocol(ProtocolType.HTTPS);
        setMethod(MethodType.POST);
    }

    public Long getDurationSeconds() {
        return this.durationSeconds;
    }

    public String getName() {
        return this.name;
    }

    public String getPolicy() {
        return this.policy;
    }

    public Class<GetFederationTokenResponse> getResponseClass() {
        return GetFederationTokenResponse.class;
    }

    public void setDurationSeconds(Long l2) {
        this.durationSeconds = l2;
        putQueryParameter("DurationSeconds", String.valueOf(l2));
    }

    public void setName(String str) {
        this.name = str;
        putQueryParameter("Name", str);
    }

    public void setPolicy(String str) {
        this.policy = str;
        putQueryParameter("Policy", str);
    }
}
