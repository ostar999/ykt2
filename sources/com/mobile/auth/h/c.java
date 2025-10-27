package com.mobile.auth.h;

import android.text.TextUtils;

/* loaded from: classes4.dex */
public class c implements b {

    /* renamed from: a, reason: collision with root package name */
    private b f10322a;

    /* renamed from: b, reason: collision with root package name */
    private com.mobile.auth.k.c f10323b;

    /* renamed from: c, reason: collision with root package name */
    private final com.mobile.auth.g.a f10324c = new com.mobile.auth.g.a();

    public void a(b bVar) {
        this.f10322a = bVar;
    }

    @Override // com.mobile.auth.h.b
    public void a(com.mobile.auth.j.c cVar, com.mobile.auth.k.c cVar2, com.cmic.sso.sdk.a aVar) {
        b(cVar, cVar2, aVar);
    }

    public void b(final com.mobile.auth.j.c cVar, final com.mobile.auth.k.c cVar2, final com.cmic.sso.sdk.a aVar) {
        if (this.f10322a != null) {
            this.f10323b = new com.mobile.auth.k.c() { // from class: com.mobile.auth.h.c.1
                @Override // com.mobile.auth.k.c
                public void a(com.mobile.auth.k.a aVar2) {
                    if (!cVar.j()) {
                        cVar2.a(aVar2);
                        return;
                    }
                    com.mobile.auth.l.c.a("RetryAndRedirectInterceptor", "retry: " + cVar.a());
                    c.this.b(cVar, cVar2, aVar);
                }

                @Override // com.mobile.auth.k.c
                public void a(com.mobile.auth.k.b bVar) {
                    com.mobile.auth.j.c cVarB;
                    if (bVar.d()) {
                        cVarB = c.this.f10324c.a(cVar, bVar, aVar);
                    } else {
                        if (TextUtils.isEmpty(c.this.f10324c.a())) {
                            cVar2.a(bVar);
                            return;
                        }
                        cVarB = c.this.f10324c.b(cVar, bVar, aVar);
                    }
                    c.this.b(cVarB, cVar2, aVar);
                }
            };
            if (cVar.g()) {
                this.f10322a.a(cVar, this.f10323b, aVar);
            } else {
                cVar2.a(com.mobile.auth.k.a.a(200025));
            }
        }
    }
}
