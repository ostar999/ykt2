package com.beizi.fusion.sm.a.a;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.beizi.fusion.sm.a.a.m;
import com.beizi.fusion.sm.repeackage.com.zui.deviceidservice.IDeviceidInterface;

/* loaded from: classes2.dex */
class h implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5295a;

    public h(Context context) {
        this.f5295a = context;
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        Context context = this.f5295a;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.zui.deviceidservice", 0) != null;
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            return false;
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5295a == null || cVar == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
        m.a(this.f5295a, intent, cVar, new m.a() { // from class: com.beizi.fusion.sm.a.a.h.1
            @Override // com.beizi.fusion.sm.a.a.m.a
            public String a(IBinder iBinder) throws com.beizi.fusion.sm.a.e, RemoteException {
                IDeviceidInterface iDeviceidInterfaceAsInterface = IDeviceidInterface.Stub.asInterface(iBinder);
                if (iDeviceidInterfaceAsInterface == null) {
                    throw new com.beizi.fusion.sm.a.e("IDeviceidInterface is null");
                }
                if (iDeviceidInterfaceAsInterface.isSupport()) {
                    return iDeviceidInterfaceAsInterface.getOAID();
                }
                throw new com.beizi.fusion.sm.a.e("IDeviceidInterface#isSupport return false");
            }
        });
    }
}
