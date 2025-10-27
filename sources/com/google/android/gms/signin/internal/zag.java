package com.google.android.gms.signin.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IAccountAccessor;

/* loaded from: classes3.dex */
public final class zag extends com.google.android.gms.internal.base.zab implements zae {
    public zag(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    @Override // com.google.android.gms.signin.internal.zae
    public final void zaa(IAccountAccessor iAccountAccessor, int i2, boolean z2) throws RemoteException {
        Parcel parcelZaa = zaa();
        com.google.android.gms.internal.base.zad.zaa(parcelZaa, iAccountAccessor);
        parcelZaa.writeInt(i2);
        com.google.android.gms.internal.base.zad.writeBoolean(parcelZaa, z2);
        zab(9, parcelZaa);
    }

    @Override // com.google.android.gms.signin.internal.zae
    public final void zam(int i2) throws RemoteException {
        Parcel parcelZaa = zaa();
        parcelZaa.writeInt(i2);
        zab(7, parcelZaa);
    }

    @Override // com.google.android.gms.signin.internal.zae
    public final void zaa(zai zaiVar, zac zacVar) throws RemoteException {
        Parcel parcelZaa = zaa();
        com.google.android.gms.internal.base.zad.zaa(parcelZaa, zaiVar);
        com.google.android.gms.internal.base.zad.zaa(parcelZaa, zacVar);
        zab(12, parcelZaa);
    }
}
