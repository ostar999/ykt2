package com.alipay.android.phone.mrpc.core;

/* loaded from: classes2.dex */
public final class p extends u {

    /* renamed from: c, reason: collision with root package name */
    private int f2979c;

    /* renamed from: d, reason: collision with root package name */
    private String f2980d;

    /* renamed from: e, reason: collision with root package name */
    private long f2981e;

    /* renamed from: f, reason: collision with root package name */
    private long f2982f;

    /* renamed from: g, reason: collision with root package name */
    private String f2983g;

    /* renamed from: h, reason: collision with root package name */
    private HttpUrlHeader f2984h;

    public p(HttpUrlHeader httpUrlHeader, int i2, String str, byte[] bArr) {
        this.f2984h = httpUrlHeader;
        this.f2979c = i2;
        this.f2980d = str;
        this.f3005a = bArr;
    }

    public final HttpUrlHeader a() {
        return this.f2984h;
    }

    public final void a(long j2) {
        this.f2981e = j2;
    }

    public final void a(String str) {
        this.f2983g = str;
    }

    public final void b(long j2) {
        this.f2982f = j2;
    }
}
