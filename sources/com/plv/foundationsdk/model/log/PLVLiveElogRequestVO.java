package com.plv.foundationsdk.model.log;

/* loaded from: classes4.dex */
public class PLVLiveElogRequestVO extends PLVElogRequestEntity {
    private String appId;
    private String timestamp;

    public PLVLiveElogRequestVO(String[] strArr, String str, int i2, String str2, String str3) {
        super(strArr, str, i2);
        this.appId = str2;
        this.timestamp = str3;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setAppId(String str) {
        this.appId = str;
    }

    public void setTimestamp(String str) {
        this.timestamp = str;
    }
}
