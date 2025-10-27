package com.google.android.gms.internal.icing;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public class zza extends Binder implements IInterface {
    private static zzc zza;

    public zza(String str) {
        attachInterface(this, str);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    public boolean dispatchTransaction(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        return false;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        boolean zOnTransact;
        if (i2 > 16777215) {
            zOnTransact = super.onTransact(i2, parcel, parcel2, i3);
        } else {
            parcel.enforceInterface(getInterfaceDescriptor());
            zOnTransact = false;
        }
        if (zOnTransact) {
            return true;
        }
        return dispatchTransaction(i2, parcel, parcel2, i3);
    }
}
