package com.alibaba.sdk.android.httpdns.g;

/* loaded from: classes2.dex */
public abstract class a<T> implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private j<T> f2782a;

    public a(j<T> jVar) {
        this.f2782a = jVar;
    }

    public abstract T a();

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.f2782a.a(a());
        } catch (Throwable th) {
            th.printStackTrace();
            this.f2782a.b(th);
        }
    }
}
