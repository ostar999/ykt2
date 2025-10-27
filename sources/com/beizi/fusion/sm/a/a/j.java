package com.beizi.fusion.sm.a.a;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import com.beizi.fusion.sm.a.a.m;
import com.beizi.fusion.sm.repeackage.com.bun.lib.MsaIdInterface;

/* loaded from: classes2.dex */
class j implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5298a;

    public j(Context context) {
        this.f5298a = context;
    }

    private void b() {
        try {
            Intent intent = new Intent("com.bun.msa.action.start.service");
            intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaKlService");
            intent.putExtra("com.bun.msa.param.pkgname", this.f5298a.getPackageName());
            if (Build.VERSION.SDK_INT < 26) {
                this.f5298a.startService(intent);
            } else {
                this.f5298a.startForegroundService(intent);
            }
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        Context context = this.f5298a;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.mdid.msa", 0) != null;
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            return false;
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5298a == null || cVar == null) {
            return;
        }
        b();
        Intent intent = new Intent("com.bun.msa.action.bindto.service");
        intent.setClassName("com.mdid.msa", "com.mdid.msa.service.MsaIdService");
        intent.putExtra("com.bun.msa.param.pkgname", this.f5298a.getPackageName());
        m.a(this.f5298a, intent, cVar, new m.a() { // from class: com.beizi.fusion.sm.a.a.j.1
            @Override // com.beizi.fusion.sm.a.a.m.a
            public String a(IBinder iBinder) throws com.beizi.fusion.sm.a.e, RemoteException {
                MsaIdInterface msaIdInterfaceAsInterface = MsaIdInterface.Stub.asInterface(iBinder);
                if (msaIdInterfaceAsInterface == null) {
                    throw new com.beizi.fusion.sm.a.e("MsaIdInterface is null");
                }
                if (msaIdInterfaceAsInterface.isSupported()) {
                    return msaIdInterfaceAsInterface.getOAID();
                }
                throw new com.beizi.fusion.sm.a.e("MsaIdInterface#isSupported return false");
            }
        });
    }
}
