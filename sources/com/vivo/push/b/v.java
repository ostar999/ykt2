package com.vivo.push.b;

/* loaded from: classes6.dex */
public abstract class v extends s {

    /* renamed from: a, reason: collision with root package name */
    private String f24278a;

    /* renamed from: b, reason: collision with root package name */
    private long f24279b;

    public v(int i2) {
        super(i2);
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("OnVerifyCallBackCommand.EXTRA_SECURITY_CONTENT", this.f24278a);
        aVar.a("notify_id", this.f24279b);
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24278a = aVar.a("OnVerifyCallBackCommand.EXTRA_SECURITY_CONTENT");
        this.f24279b = aVar.b("notify_id", -1L);
    }

    public final long f() {
        return this.f24279b;
    }

    public final String i() {
        return this.f24278a;
    }
}
