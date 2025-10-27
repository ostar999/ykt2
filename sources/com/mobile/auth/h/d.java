package com.mobile.auth.h;

import android.content.Context;
import android.net.Network;
import com.mobile.auth.l.n;
import com.mobile.auth.l.r;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class d implements b {

    /* renamed from: a, reason: collision with root package name */
    private b f10329a;

    public void a(b bVar) {
        this.f10329a = bVar;
    }

    @Override // com.mobile.auth.h.b
    public void a(final com.mobile.auth.j.c cVar, final com.mobile.auth.k.c cVar2, final com.cmic.sso.sdk.a aVar) {
        if (cVar.b()) {
            r.a((Context) null).a(new r.a() { // from class: com.mobile.auth.h.d.1

                /* renamed from: e, reason: collision with root package name */
                private final AtomicBoolean f10334e = new AtomicBoolean(false);

                @Override // com.mobile.auth.l.r.a
                public void a(final Network network) {
                    if (this.f10334e.getAndSet(true)) {
                        return;
                    }
                    n.a(new n.a(null, aVar) { // from class: com.mobile.auth.h.d.1.1
                        @Override // com.mobile.auth.l.n.a
                        public void a() {
                            if (network == null) {
                                cVar2.a(com.mobile.auth.k.a.a(102508));
                            } else {
                                com.mobile.auth.l.c.b("WifiChangeInterceptor", "onAvailable");
                                cVar.a(network);
                                AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                                d.this.b(cVar, cVar2, aVar);
                            }
                        }
                    });
                }
            });
        } else {
            b(cVar, cVar2, aVar);
        }
    }

    public void b(com.mobile.auth.j.c cVar, final com.mobile.auth.k.c cVar2, com.cmic.sso.sdk.a aVar) {
        b bVar = this.f10329a;
        if (bVar != null) {
            bVar.a(cVar, new com.mobile.auth.k.c() { // from class: com.mobile.auth.h.d.2
                @Override // com.mobile.auth.k.c
                public void a(com.mobile.auth.k.a aVar2) {
                    cVar2.a(aVar2);
                }

                @Override // com.mobile.auth.k.c
                public void a(com.mobile.auth.k.b bVar2) {
                    cVar2.a(bVar2);
                }
            }, aVar);
        }
    }
}
