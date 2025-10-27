package com.alipay.android.phone.mrpc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public final class y implements InvocationHandler {

    /* renamed from: a, reason: collision with root package name */
    protected g f3009a;

    /* renamed from: b, reason: collision with root package name */
    protected Class<?> f3010b;

    /* renamed from: c, reason: collision with root package name */
    protected z f3011c;

    public y(g gVar, Class<?> cls, z zVar) {
        this.f3009a = gVar;
        this.f3010b = cls;
        this.f3011c = zVar;
    }

    @Override // java.lang.reflect.InvocationHandler
    public final Object invoke(Object obj, Method method, Object[] objArr) {
        return this.f3011c.a(method, objArr);
    }
}
