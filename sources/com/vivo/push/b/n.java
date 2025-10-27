package com.vivo.push.b;

/* loaded from: classes6.dex */
public final class n extends s {

    /* renamed from: a, reason: collision with root package name */
    private String f24261a;

    /* renamed from: b, reason: collision with root package name */
    private int f24262b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f24263c;

    public n() {
        super(7);
        this.f24262b = 0;
        this.f24263c = false;
    }

    public final void a(int i2) {
        this.f24262b = i2;
    }

    public final void b(String str) {
        this.f24261a = str;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("content", this.f24261a);
        aVar.a("log_level", this.f24262b);
        aVar.a("is_server_log", this.f24263c);
    }

    public final String d() {
        return this.f24261a;
    }

    public final int e() {
        return this.f24262b;
    }

    public final boolean f() {
        return this.f24263c;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnLogCommand";
    }

    public final void a(boolean z2) {
        this.f24263c = z2;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24261a = aVar.a("content");
        this.f24262b = aVar.b("log_level", 0);
        this.f24263c = aVar.e("is_server_log");
    }
}
