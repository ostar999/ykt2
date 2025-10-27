package com.alibaba.sdk.android.httpdns.e;

/* loaded from: classes2.dex */
public class o implements c {

    /* renamed from: a, reason: collision with root package name */
    private q f2772a;

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.h.a f52a;

    /* renamed from: f, reason: collision with root package name */
    private int f2774f = 30000;

    /* renamed from: e, reason: collision with root package name */
    private long f2773e = 0;

    public o(com.alibaba.sdk.android.httpdns.h.a aVar, q qVar) {
        this.f52a = aVar;
        this.f2772a = qVar;
    }

    @Override // com.alibaba.sdk.android.httpdns.e.c
    public void a(com.alibaba.sdk.android.httpdns.d.d dVar, com.alibaba.sdk.android.httpdns.g.d dVar2, com.alibaba.sdk.android.httpdns.g.j<f> jVar) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.f2773e < this.f2774f) {
            jVar.b(new p("sniff too often"));
            return;
        }
        this.f2773e = jCurrentTimeMillis;
        dVar.m35a().execute(new com.alibaba.sdk.android.httpdns.g.f(new com.alibaba.sdk.android.httpdns.g.g(new com.alibaba.sdk.android.httpdns.g.g(new com.alibaba.sdk.android.httpdns.g.g(new com.alibaba.sdk.android.httpdns.g.c(dVar2, new g()), new com.alibaba.sdk.android.httpdns.g.e(com.alibaba.sdk.android.httpdns.f.b.a(dVar.getAccountId()))), new com.alibaba.sdk.android.httpdns.g.i(dVar)), new n(dVar, this.f52a, this.f2772a)), jVar));
    }

    public void reset() {
        this.f2773e = 0L;
    }
}
