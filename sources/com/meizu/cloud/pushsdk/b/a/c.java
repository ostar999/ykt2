package com.meizu.cloud.pushsdk.b.a;

import com.meizu.cloud.pushsdk.b.c.k;

/* loaded from: classes4.dex */
public class c<T> {

    /* renamed from: a, reason: collision with root package name */
    private final T f9051a;

    /* renamed from: b, reason: collision with root package name */
    private final com.meizu.cloud.pushsdk.b.b.a f9052b;

    /* renamed from: c, reason: collision with root package name */
    private k f9053c;

    public c(com.meizu.cloud.pushsdk.b.b.a aVar) {
        this.f9051a = null;
        this.f9052b = aVar;
    }

    public c(T t2) {
        this.f9051a = t2;
        this.f9052b = null;
    }

    public static <T> c<T> a(com.meizu.cloud.pushsdk.b.b.a aVar) {
        return new c<>(aVar);
    }

    public static <T> c<T> a(T t2) {
        return new c<>(t2);
    }

    public T a() {
        return this.f9051a;
    }

    public void a(k kVar) {
        this.f9053c = kVar;
    }

    public boolean b() {
        return this.f9052b == null;
    }

    public com.meizu.cloud.pushsdk.b.b.a c() {
        return this.f9052b;
    }
}
