package com.beizi.fusion.model;

import com.heytap.mcssdk.constant.b;

/* loaded from: classes2.dex */
public class S2SBiddingConfig {

    @JsonNode(key = b.f7201z)
    private String appKey;

    @JsonNode(key = "secret")
    private String secret;

    @JsonNode(key = "url")
    private String url;

    public String getAppKey() {
        return this.appKey;
    }

    public String getSecret() {
        return this.secret;
    }

    public String getUrl() {
        return this.url;
    }

    public void setAppKey(String str) {
        this.appKey = str;
    }

    public void setSecret(String str) {
        this.secret = str;
    }

    public void setUrl(String str) {
        this.url = str;
    }
}
