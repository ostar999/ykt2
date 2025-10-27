package com.alibaba.sdk.android.httpdns.g;

/* loaded from: classes2.dex */
public class g<T> extends h<T> {

    /* renamed from: a, reason: collision with root package name */
    private a f2790a;

    public interface a {
        void a(d dVar);

        void a(d dVar, Object obj);

        void a(d dVar, Throwable th);
    }

    public g(c<T> cVar, a aVar) {
        super(cVar);
        this.f2790a = aVar;
    }

    @Override // com.alibaba.sdk.android.httpdns.g.h, com.alibaba.sdk.android.httpdns.g.c
    /* renamed from: a */
    public T mo50a() {
        try {
            a aVar = this.f2790a;
            if (aVar != null) {
                try {
                    aVar.a(a());
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            T t2 = (T) super.mo50a();
            a aVar2 = this.f2790a;
            if (aVar2 != null) {
                try {
                    aVar2.a(a(), t2);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            return t2;
        } catch (Throwable th) {
            a aVar3 = this.f2790a;
            if (aVar3 != null) {
                try {
                    aVar3.a(a(), th);
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }
}
