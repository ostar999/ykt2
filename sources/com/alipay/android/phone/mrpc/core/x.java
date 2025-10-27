package com.alipay.android.phone.mrpc.core;

import java.lang.reflect.Proxy;

/* loaded from: classes2.dex */
public final class x {

    /* renamed from: a, reason: collision with root package name */
    private g f3007a;

    /* renamed from: b, reason: collision with root package name */
    private z f3008b = new z(this);

    public x(g gVar) {
        this.f3007a = gVar;
    }

    public final g a() {
        return this.f3007a;
    }

    public final <T> T a(Class<T> cls) {
        return (T) Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new y(this.f3007a, cls, this.f3008b));
    }
}
