package com.vivo.push;

/* loaded from: classes6.dex */
final class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.b f24383a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f24384b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ e f24385c;

    public j(e eVar, com.vivo.push.b.b bVar, String str) {
        this.f24385c = eVar;
        this.f24383a = bVar;
        this.f24384b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f24385c.a(this.f24383a);
        this.f24385c.e(this.f24384b);
    }
}
