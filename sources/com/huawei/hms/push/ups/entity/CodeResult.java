package com.huawei.hms.push.ups.entity;

/* loaded from: classes4.dex */
public class CodeResult {

    /* renamed from: a, reason: collision with root package name */
    public int f8047a;

    /* renamed from: b, reason: collision with root package name */
    public String f8048b;

    public CodeResult() {
    }

    public String getReason() {
        return this.f8048b;
    }

    public int getReturnCode() {
        return this.f8047a;
    }

    public void setReason(String str) {
        this.f8048b = str;
    }

    public void setReturnCode(int i2) {
        this.f8047a = i2;
    }

    public CodeResult(int i2) {
        this.f8047a = i2;
    }

    public CodeResult(int i2, String str) {
        this.f8047a = i2;
        this.f8048b = str;
    }
}
