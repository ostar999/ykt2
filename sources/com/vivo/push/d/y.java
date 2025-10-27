package com.vivo.push.d;

/* loaded from: classes6.dex */
final class y implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.r f24345a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ x f24346b;

    public y(x xVar, com.vivo.push.b.r rVar) {
        this.f24346b = xVar;
        this.f24345a = rVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        x xVar = this.f24346b;
        ((z) xVar).f24347b.onPublish(((com.vivo.push.l) xVar).f24388a, this.f24345a.h(), this.f24345a.g());
    }
}
