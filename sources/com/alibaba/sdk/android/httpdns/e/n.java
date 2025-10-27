package com.alibaba.sdk.android.httpdns.e;

import com.alibaba.sdk.android.httpdns.g.g;
import java.net.SocketTimeoutException;

/* loaded from: classes2.dex */
public class n implements g.a {

    /* renamed from: a, reason: collision with root package name */
    private q f2769a;

    /* renamed from: a, reason: collision with other field name */
    private com.alibaba.sdk.android.httpdns.h.a f51a;

    /* renamed from: b, reason: collision with root package name */
    private com.alibaba.sdk.android.httpdns.d.d f2770b;

    /* renamed from: d, reason: collision with root package name */
    private long f2771d;

    public n(com.alibaba.sdk.android.httpdns.d.d dVar, com.alibaba.sdk.android.httpdns.h.a aVar, q qVar) {
        this.f2770b = dVar;
        this.f51a = aVar;
        this.f2769a = qVar;
    }

    private boolean a(Throwable th) {
        if (th instanceof SocketTimeoutException) {
            return true;
        }
        if (th instanceof com.alibaba.sdk.android.httpdns.g.b) {
            return ((com.alibaba.sdk.android.httpdns.g.b) th).e();
        }
        return false;
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(com.alibaba.sdk.android.httpdns.g.d dVar) {
        this.f2771d = System.currentTimeMillis();
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(com.alibaba.sdk.android.httpdns.g.d dVar, Object obj) {
        q qVar;
        if (!this.f2770b.b(dVar.l(), dVar.getPort()) || (qVar = this.f2769a) == null) {
            return;
        }
        qVar.d();
    }

    @Override // com.alibaba.sdk.android.httpdns.g.g.a
    public void a(com.alibaba.sdk.android.httpdns.g.d dVar, Throwable th) {
        com.alibaba.sdk.android.httpdns.h.a aVar;
        long jCurrentTimeMillis = System.currentTimeMillis() - this.f2771d;
        if (a(th) || jCurrentTimeMillis > dVar.getTimeout()) {
            boolean zA = this.f2770b.a(dVar.l(), dVar.getPort());
            dVar.j(this.f2770b.d());
            dVar.setPort(this.f2770b.getPort());
            if (zA && (aVar = this.f51a) != null) {
                aVar.f();
            }
            q qVar = this.f2769a;
            if (qVar != null) {
                qVar.c();
            }
        }
    }
}
