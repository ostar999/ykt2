package com.beizi.fusion.sm.a.a;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.beizi.fusion.sm.a.a.m;
import com.beizi.fusion.sm.repeackage.com.android.creator.IdsSupplier;

/* loaded from: classes2.dex */
public class e implements com.beizi.fusion.sm.a.d {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5288a;

    public e(Context context) {
        this.f5288a = context;
    }

    @Override // com.beizi.fusion.sm.a.d
    public boolean a() {
        Context context = this.f5288a;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().getPackageInfo("com.android.creator", 0) != null;
        } catch (Exception e2) {
            com.beizi.fusion.sm.a.f.a(e2);
            return false;
        }
    }

    @Override // com.beizi.fusion.sm.a.d
    public void a(com.beizi.fusion.sm.a.c cVar) {
        if (this.f5288a == null || cVar == null) {
            return;
        }
        Intent intent = new Intent("android.service.action.msa");
        intent.setPackage("com.android.creator");
        m.a(this.f5288a, intent, cVar, new m.a() { // from class: com.beizi.fusion.sm.a.a.e.1
            @Override // com.beizi.fusion.sm.a.a.m.a
            public String a(IBinder iBinder) throws com.beizi.fusion.sm.a.e, RemoteException {
                IdsSupplier idsSupplierAsInterface = IdsSupplier.Stub.asInterface(iBinder);
                if (idsSupplierAsInterface != null) {
                    return idsSupplierAsInterface.getOAID();
                }
                throw new com.beizi.fusion.sm.a.e("IdsSupplier is null");
            }
        });
    }
}
