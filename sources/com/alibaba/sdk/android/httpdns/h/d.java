package com.alibaba.sdk.android.httpdns.h;

import com.alibaba.sdk.android.httpdns.g.g;

/* loaded from: classes2.dex */
public class d implements g.a {

    /* renamed from: b, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.d.d f2804b;

    public d(com.alibaba.sdk.android.httpdns.d.d dVar) {
        this.f2804b = dVar;
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(com.alibaba.sdk.android.httpdns.g.d dVar) {
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(com.alibaba.sdk.android.httpdns.g.d dVar, Object obj) {
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(com.alibaba.sdk.android.httpdns.g.d dVar, Throwable th) {
        if (this.f2804b.a(dVar.l(), dVar.getPort())) {
            this.f2804b.m40b();
        }
        dVar.j(this.f2804b.d());
        dVar.setPort(this.f2804b.getPort());
    }
}
