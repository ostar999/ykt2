package com.tencent.liteav.basic.util;

import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class a<T> {

    /* renamed from: b, reason: collision with root package name */
    private final InterfaceC0331a<T> f18694b;

    /* renamed from: a, reason: collision with root package name */
    private final ThreadLocal<T> f18693a = new ThreadLocal<>();

    /* renamed from: c, reason: collision with root package name */
    private WeakReference<T> f18695c = new WeakReference<>(null);

    /* renamed from: com.tencent.liteav.basic.util.a$a, reason: collision with other inner class name */
    public interface InterfaceC0331a<T> {
        T a();
    }

    public a(InterfaceC0331a<T> interfaceC0331a) {
        this.f18694b = interfaceC0331a;
    }

    private T b() {
        T tA = this.f18695c.get();
        if (tA == null) {
            synchronized (this) {
                tA = this.f18695c.get();
                if (tA == null) {
                    tA = this.f18694b.a();
                    this.f18695c = new WeakReference<>(tA);
                }
            }
        }
        return tA;
    }

    public T a() {
        T t2 = this.f18693a.get();
        if (t2 != null) {
            return t2;
        }
        T tB = b();
        this.f18693a.set(tB);
        return tB;
    }
}
