package com.vivo.push.d;

import android.content.pm.PackageManager;

/* loaded from: classes6.dex */
final class b extends com.vivo.push.l {
    public b(com.vivo.push.o oVar) {
        super(oVar);
    }

    @Override // com.vivo.push.l
    public final void a(com.vivo.push.o oVar) throws PackageManager.NameNotFoundException {
        com.vivo.push.model.b bVarA = com.vivo.push.util.t.a(this.f24388a);
        try {
            if (((com.vivo.push.b.d) oVar).d() ? f.a(this.f24388a) : f.b(this.f24388a)) {
                com.vivo.push.model.b bVarA2 = com.vivo.push.util.t.a(this.f24388a);
                if (bVarA == null || bVarA2 == null || bVarA2.a() == null || !bVarA2.a().equals(bVarA.a())) {
                    if (bVarA != null && bVarA.a() != null) {
                        com.vivo.push.a.a.a(this.f24388a, bVarA.a(), new com.vivo.push.b.y(bVarA.a()));
                    }
                    if (bVarA2 == null || bVarA2.a() == null) {
                        return;
                    }
                    com.vivo.push.a.a.a(this.f24388a, bVarA2.a(), new com.vivo.push.b.f());
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
