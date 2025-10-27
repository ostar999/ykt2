package com.vivo.push.b;

/* loaded from: classes6.dex */
public final class j extends s {

    /* renamed from: a, reason: collision with root package name */
    private int f24256a;

    /* renamed from: b, reason: collision with root package name */
    private int f24257b;

    public j() {
        super(12);
        this.f24256a = -1;
        this.f24257b = -1;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("OnChangePushStatus.EXTRA_REQ_SERVICE_STATUS", this.f24256a);
        aVar.a("OnChangePushStatus.EXTRA_REQ_RECEIVER_STATUS", this.f24257b);
    }

    public final int d() {
        return this.f24256a;
    }

    public final int e() {
        return this.f24257b;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnChangePushStatusCommand";
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24256a = aVar.b("OnChangePushStatus.EXTRA_REQ_SERVICE_STATUS", this.f24256a);
        this.f24257b = aVar.b("OnChangePushStatus.EXTRA_REQ_RECEIVER_STATUS", this.f24257b);
    }
}
