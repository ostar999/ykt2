package com.beizi.fusion.sm.a.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.beizi.fusion.sm.a.a.m;
import com.beizi.fusion.sm.repeackage.com.asus.msa.SupplementaryDID.IDidAidlInterface;

/* loaded from: classes2.dex */
class a implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5282a;

    public a(Context context) {
        this.f5282a = context;
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        Context context = this.f5282a;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.asus.msa.SupplementaryDID", 0) != null;
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            return false;
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5282a == null || cVar == null) {
            return;
        }
        Intent intent = new Intent("com.asus.msa.action.ACCESS_DID");
        intent.setComponent(new ComponentName("com.asus.msa.SupplementaryDID", "com.asus.msa.SupplementaryDID.SupplementaryDIDService"));
        m.a(this.f5282a, intent, cVar, new m.a() { // from class: com.beizi.fusion.sm.a.a.a.1
            @Override // com.beizi.fusion.sm.a.a.m.a
            public String a(IBinder iBinder) throws com.beizi.fusion.sm.a.e, RemoteException {
                IDidAidlInterface iDidAidlInterfaceAsInterface = IDidAidlInterface.Stub.asInterface(iBinder);
                if (iDidAidlInterfaceAsInterface == null) {
                    throw new com.beizi.fusion.sm.a.e("IDidAidlInterface is null");
                }
                if (iDidAidlInterfaceAsInterface.isSupport()) {
                    return iDidAidlInterfaceAsInterface.getOAID();
                }
                throw new com.beizi.fusion.sm.a.e("IDidAidlInterface#isSupport return false");
            }
        });
    }
}
