package com.vivo.push.d;

/* loaded from: classes6.dex */
final class m implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.m f24330a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ l f24331b;

    public m(l lVar, com.vivo.push.b.m mVar) {
        this.f24331b = lVar;
        this.f24330a = mVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        l lVar = this.f24331b;
        ((z) lVar).f24347b.onListTags(((com.vivo.push.l) lVar).f24388a, this.f24330a.h(), this.f24330a.d(), this.f24330a.g());
    }
}
