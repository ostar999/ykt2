package com.alibaba.sdk.android.httpdns.net;

import java.net.Inet6Address;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public Inet6Address f2818a;

    /* renamed from: h, reason: collision with root package name */
    public int f2819h;

    public b(Inet6Address inet6Address, int i2) {
        this.f2819h = i2;
        this.f2818a = inet6Address;
    }

    public String toString() {
        return this.f2818a.getHostAddress() + "/" + this.f2819h;
    }
}
