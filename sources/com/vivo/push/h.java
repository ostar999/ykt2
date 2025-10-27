package com.vivo.push;

/* loaded from: classes6.dex */
final class h implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.b f24379a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f24380b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ e f24381c;

    public h(e eVar, com.vivo.push.b.b bVar, String str) {
        this.f24381c = eVar;
        this.f24379a = bVar;
        this.f24380b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f24381c.a(this.f24379a);
        this.f24381c.e(this.f24380b);
    }
}
