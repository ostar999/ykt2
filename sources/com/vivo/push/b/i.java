package com.vivo.push.b;

import com.tencent.connect.common.Constants;

/* loaded from: classes6.dex */
public final class i extends s {

    /* renamed from: a, reason: collision with root package name */
    private String f24253a;

    /* renamed from: b, reason: collision with root package name */
    private String f24254b;

    /* renamed from: c, reason: collision with root package name */
    private String f24255c;

    public i(int i2) {
        super(i2);
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void c(com.vivo.push.a aVar) {
        super.c(aVar);
        aVar.a("app_id", this.f24253a);
        aVar.a(Constants.PARAM_CLIENT_ID, this.f24254b);
        aVar.a("client_token", this.f24255c);
    }

    public final String d() {
        return this.f24253a;
    }

    public final String e() {
        return this.f24255c;
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final String toString() {
        return "OnBindCommand";
    }

    @Override // com.vivo.push.b.s, com.vivo.push.o
    public final void d(com.vivo.push.a aVar) {
        super.d(aVar);
        this.f24253a = aVar.a("app_id");
        this.f24254b = aVar.a(Constants.PARAM_CLIENT_ID);
        this.f24255c = aVar.a("client_token");
    }
}
