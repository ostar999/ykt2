package com.plv.foundationsdk.model.log;

/* loaded from: classes4.dex */
public class PLVVodElogRequestVO extends PLVElogRequestEntity {
    private String userId;

    public PLVVodElogRequestVO(String[] strArr, String str, int i2, String str2) {
        super(strArr, str, i2);
        this.userId = str2;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public PLVVodElogRequestVO(String str, String[] strArr, String str2, int i2, String str3) {
        super(str, strArr, str2, i2);
        this.userId = str3;
    }
}
