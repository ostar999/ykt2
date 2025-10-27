package com.vivo.push.d;

/* loaded from: classes6.dex */
final class o implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.n f24332a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ n f24333b;

    public o(n nVar, com.vivo.push.b.n nVar2) {
        this.f24333b = nVar;
        this.f24332a = nVar2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        n nVar = this.f24333b;
        ((z) nVar).f24347b.onLog(((com.vivo.push.l) nVar).f24388a, this.f24332a.d(), this.f24332a.e(), this.f24332a.f());
    }
}
