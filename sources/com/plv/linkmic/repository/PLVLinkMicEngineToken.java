package com.plv.linkmic.repository;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVLinkMicEngineToken {
    private String TBizId;
    private String TSdkAppId;
    private String UAppToken;
    private String ZAppSign;
    private String appId;
    private String token;

    public PLVLinkMicEngineToken(String str, String str2) {
        this.token = str;
        this.appId = str2;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getTBizId() {
        return this.TBizId;
    }

    public String getTSdkAppId() {
        return this.TSdkAppId;
    }

    public String getToken() {
        return this.token;
    }

    public String getUAppToken() {
        return this.UAppToken;
    }

    public String getZAppSign() {
        return this.ZAppSign;
    }

    public void setTBizId(String str) {
        this.TBizId = str;
    }

    public void setTSdkAppId(String str) {
        this.TSdkAppId = str;
    }

    public void setUAppToken(String str) {
        this.UAppToken = str;
    }

    public void setZAppSign(String str) {
        this.ZAppSign = str;
    }

    public String toString() {
        return "PLVLinkMicEngineToken{token='" + this.token + CharPool.SINGLE_QUOTE + ", appId='" + this.appId + CharPool.SINGLE_QUOTE + ", appSign='" + this.ZAppSign + CharPool.SINGLE_QUOTE + '}';
    }
}
