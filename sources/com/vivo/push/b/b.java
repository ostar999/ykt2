package com.vivo.push.b;

/* loaded from: classes6.dex */
public final class b extends c {

    /* renamed from: a, reason: collision with root package name */
    private String f24240a;

    /* renamed from: b, reason: collision with root package name */
    private String f24241b;

    /* renamed from: c, reason: collision with root package name */
    private String f24242c;

    /* renamed from: d, reason: collision with root package name */
    private String f24243d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f24244e;

    public b(boolean z2, String str) {
        super(z2 ? 2006 : 2007, str);
        this.f24244e = false;
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("sdk_clients", this.f24240a);
        aVar.a("sdk_version", 323L);
        aVar.a("BaseAppCommand.EXTRA_APPID", this.f24242c);
        aVar.a("BaseAppCommand.EXTRA_APPKEY", this.f24241b);
        aVar.a("PUSH_REGID", this.f24243d);
    }

    public final void d() {
        this.f24242c = null;
    }

    public final void e() {
        this.f24241b = null;
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final String toString() {
        return "AppCommand:" + b();
    }

    @Override // com.vivo.push.b.c, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24240a = aVar.a("sdk_clients");
        this.f24242c = aVar.a("BaseAppCommand.EXTRA_APPID");
        this.f24241b = aVar.a("BaseAppCommand.EXTRA_APPKEY");
        this.f24243d = aVar.a("PUSH_REGID");
    }
}
