package com.vivo.push.d;

/* loaded from: classes6.dex */
final class g extends z {
    public g(com.vivo.push.o oVar) {
        super(oVar);
    }

    @Override // com.vivo.push.l
    public final void a(com.vivo.push.o oVar) {
        com.vivo.push.util.p.d("OnClearCacheTask", "delete push info " + this.f24388a.getPackageName());
        com.vivo.push.util.y.b(this.f24388a).a();
    }
}
