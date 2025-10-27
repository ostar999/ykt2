package com.alibaba.sdk.android.httpdns.g;

import com.alibaba.sdk.android.httpdns.g.g;

/* loaded from: classes2.dex */
public class e implements g.a {

    /* renamed from: a, reason: collision with root package name */
    com.alibaba.sdk.android.httpdns.f.b f2788a;

    public e(com.alibaba.sdk.android.httpdns.f.b bVar) {
        this.f2788a = bVar;
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(d dVar) {
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(d dVar, Object obj) {
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(d dVar, Throwable th) {
        if (this.f2788a != null) {
            if (dVar.m().contains("/ss")) {
                this.f2788a.a(dVar.l(), com.alibaba.sdk.android.httpdns.f.c.a(th) + "", com.alibaba.sdk.android.httpdns.f.c.m49a(th), 0);
                return;
            }
            this.f2788a.a(dVar.l(), com.alibaba.sdk.android.httpdns.f.c.a(th) + "", com.alibaba.sdk.android.httpdns.f.c.m49a(th), 0, 0);
        }
    }
}
