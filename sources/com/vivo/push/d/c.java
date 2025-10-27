package com.vivo.push.d;

import com.vivo.push.cache.ClientConfigManagerImpl;

/* loaded from: classes6.dex */
final class c extends com.vivo.push.l {
    public c(com.vivo.push.o oVar) {
        super(oVar);
    }

    @Override // com.vivo.push.l
    public final void a(com.vivo.push.o oVar) {
        com.vivo.push.util.p.a(ClientConfigManagerImpl.getInstance(this.f24388a).isDebug());
    }
}
