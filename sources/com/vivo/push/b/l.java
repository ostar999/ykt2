package com.vivo.push.b;

/* loaded from: classes6.dex */
public final class l extends s {

    /* renamed from: a, reason: collision with root package name */
    private int f24258a;

    /* renamed from: b, reason: collision with root package name */
    private int f24259b;

    public l() {
        super(2016);
        this.f24258a = -1;
        this.f24259b = -1;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("key_dispatch_environment", this.f24258a);
        aVar.a("key_dispatch_area", this.f24259b);
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24258a = aVar.b("key_dispatch_environment", 1);
        this.f24259b = aVar.b("key_dispatch_area", 1);
    }

    public final int e() {
        return this.f24259b;
    }

    public final int d() {
        return this.f24258a;
    }
}
