package com.huawei.secure.android.common.util;

/* loaded from: classes4.dex */
public class SecurityCommonException extends Exception {

    /* renamed from: c, reason: collision with root package name */
    private static final long f8455c = 1;

    /* renamed from: a, reason: collision with root package name */
    private String f8456a;

    /* renamed from: b, reason: collision with root package name */
    private String f8457b;

    public SecurityCommonException() {
    }

    public String getMsgDes() {
        return this.f8457b;
    }

    public String getRetCd() {
        return this.f8456a;
    }

    public SecurityCommonException(Throwable th) {
        super(th);
    }

    public SecurityCommonException(String str, Throwable th) {
        super(str, th);
    }

    public SecurityCommonException(String str) {
        super(str);
        this.f8457b = str;
    }

    public SecurityCommonException(String str, String str2) {
        this.f8456a = str;
        this.f8457b = str2;
    }
}
