package com.vivo.push.d;

/* loaded from: classes6.dex */
public final class k extends z {
    public k(com.vivo.push.o oVar) {
        super(oVar);
    }

    @Override // com.vivo.push.l
    public final void a(com.vivo.push.o oVar) {
        com.vivo.push.b.l lVar = (com.vivo.push.b.l) oVar;
        int iD = lVar.d();
        int iE = lVar.e();
        com.vivo.push.util.w.b().a("key_dispatch_environment", iD);
        com.vivo.push.util.w.b().a("key_dispatch_area", iE);
    }
}
