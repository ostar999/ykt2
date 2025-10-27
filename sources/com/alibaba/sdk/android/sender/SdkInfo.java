package com.alibaba.sdk.android.sender;

import java.util.Map;

/* loaded from: classes2.dex */
public class SdkInfo {

    /* renamed from: a, reason: collision with root package name */
    Map<String, String> f2867a;

    /* renamed from: b, reason: collision with root package name */
    private String f2868b;

    /* renamed from: c, reason: collision with root package name */
    private String f2869c;

    /* renamed from: d, reason: collision with root package name */
    private String f2870d;

    public String a() {
        return this.f2868b;
    }

    public String b() {
        return this.f2869c;
    }

    public String c() {
        return this.f2870d;
    }

    public SdkInfo setAppKey(String str) {
        this.f2870d = str;
        return this;
    }

    public SdkInfo setExt(Map<String, String> map) {
        this.f2867a = map;
        return this;
    }

    public SdkInfo setSdkId(String str) {
        this.f2868b = str;
        return this;
    }

    public SdkInfo setSdkVersion(String str) {
        this.f2869c = str;
        return this;
    }
}
