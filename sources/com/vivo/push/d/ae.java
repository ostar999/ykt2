package com.vivo.push.d;

/* loaded from: classes6.dex */
final class ae implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.i f24315a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ ad f24316b;

    public ae(ad adVar, com.vivo.push.b.i iVar) {
        this.f24316b = adVar;
        this.f24315a = iVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ad adVar = this.f24316b;
        ((z) adVar).f24347b.onUnBind(((com.vivo.push.l) adVar).f24388a, this.f24315a.h(), this.f24315a.d());
    }
}
