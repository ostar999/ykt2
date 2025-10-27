package com.google.android.gms.common.internal.service;

import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public abstract class zaj extends com.google.android.gms.internal.base.zaa implements zak {
    public zaj() {
        super("com.google.android.gms.common.internal.service.ICommonCallbacks");
    }

    @Override // com.google.android.gms.internal.base.zaa
    public boolean dispatchTransaction(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 != 1) {
            return false;
        }
        zaj(parcel.readInt());
        parcel2.writeNoException();
        return true;
    }
}
