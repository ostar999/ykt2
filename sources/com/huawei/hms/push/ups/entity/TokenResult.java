package com.huawei.hms.push.ups.entity;

/* loaded from: classes4.dex */
public class TokenResult extends CodeResult {

    /* renamed from: c, reason: collision with root package name */
    public String f8049c;

    public TokenResult() {
    }

    public String getToken() {
        return this.f8049c;
    }

    public void setToken(String str) {
        this.f8049c = str;
    }

    public TokenResult(int i2) {
        super(i2);
    }

    public TokenResult(int i2, String str) {
        super(i2, str);
    }

    public TokenResult(String str) {
        this.f8049c = str;
    }
}
