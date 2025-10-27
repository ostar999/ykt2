package com.alipay.sdk.auth;

/* loaded from: classes2.dex */
public class APAuthInfo {

    /* renamed from: a, reason: collision with root package name */
    private String f3137a;

    /* renamed from: b, reason: collision with root package name */
    private String f3138b;

    /* renamed from: c, reason: collision with root package name */
    private String f3139c;

    /* renamed from: d, reason: collision with root package name */
    private String f3140d;

    public APAuthInfo(String str, String str2, String str3) {
        this(str, str2, str3, null);
    }

    public String getAppId() {
        return this.f3137a;
    }

    public String getPid() {
        return this.f3139c;
    }

    public String getProductId() {
        return this.f3138b;
    }

    public String getRedirectUri() {
        return this.f3140d;
    }

    public APAuthInfo(String str, String str2, String str3, String str4) {
        this.f3137a = str;
        this.f3138b = str2;
        this.f3140d = str3;
        this.f3139c = str4;
    }
}
