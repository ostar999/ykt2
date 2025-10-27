package com.google.android.gms.internal.icing;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public class zzb implements IInterface {
    private final IBinder zzb;
    private final String zzc;

    public zzb(IBinder iBinder, String str) {
        this.zzb = iBinder;
        this.zzc = str;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.zzb;
    }

    public final Parcel zza() {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeInterfaceToken(this.zzc);
        return parcelObtain;
    }

    public final void zzb(int i2, Parcel parcel) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            this.zzb.transact(i2, parcel, parcelObtain, 0);
            parcelObtain.readException();
        } finally {
            parcel.recycle();
            parcelObtain.recycle();
        }
    }

    public final Parcel zza(int i2, Parcel parcel) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            try {
                this.zzb.transact(8, parcel, parcelObtain, 0);
                parcelObtain.readException();
                return parcelObtain;
            } catch (RuntimeException e2) {
                parcelObtain.recycle();
                throw e2;
            }
        } finally {
            parcel.recycle();
        }
    }
}
