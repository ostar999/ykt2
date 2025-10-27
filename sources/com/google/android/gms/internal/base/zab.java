package com.google.android.gms.internal.base;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public class zab implements IInterface {
    private final IBinder zab;
    private final String zac;

    public zab(IBinder iBinder, String str) {
        this.zab = iBinder;
        this.zac = str;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.zab;
    }

    public final Parcel zaa() {
        Parcel parcelObtain = Parcel.obtain();
        parcelObtain.writeInterfaceToken(this.zac);
        return parcelObtain;
    }

    public final void zab(int i2, Parcel parcel) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            this.zab.transact(i2, parcel, parcelObtain, 0);
            parcelObtain.readException();
        } finally {
            parcel.recycle();
            parcelObtain.recycle();
        }
    }

    public final void zac(int i2, Parcel parcel) throws RemoteException {
        try {
            this.zab.transact(1, parcel, null, 1);
        } finally {
            parcel.recycle();
        }
    }

    public final Parcel zaa(int i2, Parcel parcel) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            try {
                this.zab.transact(i2, parcel, parcelObtain, 0);
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
