package com.umeng.socialize;

/* loaded from: classes6.dex */
public class SocializeException extends RuntimeException {

    /* renamed from: b, reason: collision with root package name */
    private static final long f23579b = 1;

    /* renamed from: a, reason: collision with root package name */
    protected int f23580a;

    /* renamed from: c, reason: collision with root package name */
    private String f23581c;

    public SocializeException(int i2, String str) {
        super(str);
        this.f23580a = i2;
        this.f23581c = str;
    }

    public int getErrorCode() {
        return this.f23580a;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.f23581c;
    }

    public SocializeException(String str, Throwable th) {
        super(str, th);
        this.f23580a = 5000;
        this.f23581c = str;
    }

    public SocializeException(String str) {
        super(str);
        this.f23580a = 5000;
        this.f23581c = str;
    }
}
