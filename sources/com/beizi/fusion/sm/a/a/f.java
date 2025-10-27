package com.beizi.fusion.sm.a.a;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.beizi.fusion.sm.a.a.m;
import com.beizi.fusion.sm.repeackage.com.google.android.gms.ads.identifier.internal.IAdvertisingIdService;

/* loaded from: classes2.dex */
class f implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5290a;

    public f(Context context) {
        this.f5290a = context;
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        Context context = this.f5290a;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.android.vending", 0) != null;
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            return false;
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5290a == null || cVar == null) {
            return;
        }
        Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
        intent.setPackage("com.google.android.gms");
        m.a(this.f5290a, intent, cVar, new m.a() { // from class: com.beizi.fusion.sm.a.a.f.1
            @Override // com.beizi.fusion.sm.a.a.m.a
            public String a(IBinder iBinder) throws com.beizi.fusion.sm.a.e, RemoteException {
                IAdvertisingIdService iAdvertisingIdServiceAsInterface = IAdvertisingIdService.Stub.asInterface(iBinder);
                if (iAdvertisingIdServiceAsInterface.isLimitAdTrackingEnabled(true)) {
                    com.beizi.fusion.sm.a.f.a("User has disabled advertising identifier");
                }
                return iAdvertisingIdServiceAsInterface.getId();
            }
        });
    }
}
