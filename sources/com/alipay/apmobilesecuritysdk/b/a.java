package com.alipay.apmobilesecuritysdk.b;

/* loaded from: classes2.dex */
public final class a {

    /* renamed from: b, reason: collision with root package name */
    private static a f3020b = new a();

    /* renamed from: a, reason: collision with root package name */
    private int f3021a = 0;

    public static a a() {
        return f3020b;
    }

    public final void a(int i2) {
        this.f3021a = i2;
    }

    public final int b() {
        return this.f3021a;
    }

    public final String c() {
        if (com.alipay.security.mobile.module.a.a.b(null)) {
            return null;
        }
        int i2 = this.f3021a;
        return i2 != 1 ? i2 != 3 ? i2 != 4 ? "https://mobilegw.alipay.com/mgw.htm" : "http://mobilegw.aaa.alipay.net/mgw.htm" : "http://mobilegw-1-64.test.alipay.net/mgw.htm" : "http://mobilegw.stable.alipay.net/mgw.htm";
    }
}
