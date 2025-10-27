package com.vivo.push.d;

import android.content.Context;
import android.content.pm.PackageManager;
import com.vivo.push.cache.ClientConfigManagerImpl;

/* loaded from: classes6.dex */
final class ah extends com.vivo.push.l {
    public ah(com.vivo.push.o oVar) {
        super(oVar);
    }

    @Override // com.vivo.push.l
    public final void a(com.vivo.push.o oVar) throws PackageManager.NameNotFoundException {
        Context context = this.f24388a;
        if (context == null) {
            com.vivo.push.util.p.d("SendCommandTask", "SendCommandTask " + oVar + " ; mContext is Null");
            return;
        }
        if (oVar == null) {
            com.vivo.push.util.p.d("SendCommandTask", "SendCommandTask pushCommand is Null");
            return;
        }
        com.vivo.push.model.b bVarA = com.vivo.push.util.t.a(context);
        int iB = oVar.b();
        if (iB == 2009) {
            com.vivo.push.util.p.a(ClientConfigManagerImpl.getInstance(this.f24388a).isDebug());
            if (com.vivo.push.util.p.a()) {
                com.vivo.push.e.a().i();
                com.vivo.push.util.b bVar = new com.vivo.push.util.b();
                bVar.a(this.f24388a, "com.vivo.push_preferences.hybridapptoken_v1");
                bVar.a();
                com.vivo.push.util.b bVar2 = new com.vivo.push.util.b();
                bVar2.a(this.f24388a, "com.vivo.push_preferences.appconfig_v1");
                bVar2.a();
                if (!com.vivo.push.e.a().e()) {
                    ClientConfigManagerImpl.getInstance(this.f24388a).clearPush();
                }
            }
        } else if (iB != 2011) {
            switch (iB) {
                case 2002:
                case 2003:
                case 2004:
                case 2005:
                    if (bVarA == null || bVarA.c()) {
                        com.vivo.push.e.a().a(((com.vivo.push.b.c) oVar).h(), 1005);
                        break;
                    } else {
                        com.vivo.push.b.c cVar = (com.vivo.push.b.c) oVar;
                        int iA = com.vivo.push.util.s.a(cVar);
                        if (iA != 0) {
                            com.vivo.push.e.a().a(cVar.h(), iA);
                            return;
                        }
                    }
                    break;
            }
        } else {
            com.vivo.push.util.p.a(ClientConfigManagerImpl.getInstance(this.f24388a).isDebug(((com.vivo.push.b.w) oVar).d()));
        }
        if (bVarA == null) {
            com.vivo.push.util.p.d("SendCommandTask", "SendCommandTask " + oVar + " ; pushPkgInfo is Null");
            return;
        }
        String strA = bVarA.a();
        if (bVarA.c()) {
            try {
                com.vivo.push.e.a().a(((com.vivo.push.b.c) oVar).h(), 1004);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            oVar = new com.vivo.push.b.e();
            com.vivo.push.util.p.d("SendCommandTask", "SendCommandTask " + oVar + " ; pkgName is InBlackList ");
        }
        com.vivo.push.a.a.a(this.f24388a, strA, oVar);
    }
}
