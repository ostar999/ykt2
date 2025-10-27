package com.beizi.fusion.sm.a.a;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.beizi.fusion.sm.a.a.m;
import com.beizi.fusion.sm.repeackage.com.samsung.android.deviceidservice.IDeviceIdService;

/* loaded from: classes2.dex */
class o implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5308a;

    public o(Context context) {
        this.f5308a = context;
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        Context context = this.f5308a;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.samsung.android.deviceidservice", 0) != null;
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            return false;
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5308a == null || cVar == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName("com.samsung.android.deviceidservice", "com.samsung.android.deviceidservice.DeviceIdService");
        m.a(this.f5308a, intent, cVar, new m.a() { // from class: com.beizi.fusion.sm.a.a.o.1
            @Override // com.beizi.fusion.sm.a.a.m.a
            public String a(IBinder iBinder) throws com.beizi.fusion.sm.a.e, RemoteException {
                IDeviceIdService iDeviceIdServiceAsInterface = IDeviceIdService.Stub.asInterface(iBinder);
                if (iDeviceIdServiceAsInterface != null) {
                    return iDeviceIdServiceAsInterface.getOAID();
                }
                throw new com.beizi.fusion.sm.a.e("IDeviceIdService is null");
            }
        });
    }
}
