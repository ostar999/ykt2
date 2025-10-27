package com.mobile.auth.j;

import com.mobile.auth.i.e;
import com.mobile.auth.l.p;

/* loaded from: classes4.dex */
public class b extends c {

    /* renamed from: b, reason: collision with root package name */
    private final e f10396b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f10397c;

    public b(String str, e eVar, String str2, String str3) {
        super(str, eVar, str2, str3);
        this.f10397c = false;
        this.f10396b = eVar;
    }

    public void a(com.cmic.sso.sdk.a aVar) {
        if (this.f10397c) {
            return;
        }
        com.mobile.auth.i.a aVarC = this.f10396b.c();
        if (!aVar.b("isCloseIpv4", false)) {
            aVarC.q(p.a(true));
        }
        if (!aVar.b("isCloseIpv6", false)) {
            aVarC.r(p.b(true));
        }
        aVarC.n(aVarC.u(aVar.b("appkey")));
        this.f10396b.a(aVarC);
        this.f10396b.a(true);
        this.f10398a = this.f10396b.b().toString();
        this.f10397c = true;
    }
}
