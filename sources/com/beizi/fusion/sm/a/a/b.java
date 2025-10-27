package com.beizi.fusion.sm.a.a;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.beizi.fusion.sm.a.a.m;
import com.beizi.fusion.sm.repeackage.com.coolpad.deviceidsupport.IDeviceIdManager;

/* loaded from: classes2.dex */
public class b implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5284a;

    public b(Context context) {
        if (context instanceof Application) {
            this.f5284a = context;
        } else {
            this.f5284a = context.getApplicationContext();
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        Context context = this.f5284a;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.coolpad.deviceidsupport", 0) != null;
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            return false;
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5284a == null || cVar == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.coolpad.deviceidsupport", "com.coolpad.deviceidsupport.DeviceIdService"));
        m.a(this.f5284a, intent, cVar, new m.a() { // from class: com.beizi.fusion.sm.a.a.b.1
            @Override // com.beizi.fusion.sm.a.a.m.a
            public String a(IBinder iBinder) throws com.beizi.fusion.sm.a.e, RemoteException {
                IDeviceIdManager iDeviceIdManagerAsInterface = IDeviceIdManager.Stub.asInterface(iBinder);
                if (iDeviceIdManagerAsInterface != null) {
                    return iDeviceIdManagerAsInterface.getOAID(b.this.f5284a.getPackageName());
                }
                throw new com.beizi.fusion.sm.a.e("IDeviceIdManager is null");
            }
        });
    }
}
