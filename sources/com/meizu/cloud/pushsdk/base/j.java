package com.meizu.cloud.pushsdk.base;

/* loaded from: classes4.dex */
public class j<T> {

    /* renamed from: a, reason: collision with root package name */
    protected T f9267a;

    /* renamed from: b, reason: collision with root package name */
    protected T f9268b;

    public j(T t2) {
        if (t2 == null) {
            throw new RuntimeException("proxy must be has a default implementation");
        }
        this.f9268b = t2;
    }

    public T c() {
        T t2 = this.f9267a;
        return t2 != null ? t2 : this.f9268b;
    }
}
