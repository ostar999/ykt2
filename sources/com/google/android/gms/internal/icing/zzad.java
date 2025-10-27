package com.google.android.gms.internal.icing;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzad extends zzb implements zzaa {
    public zzad(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.appdatasearch.internal.ILightweightAppDataSearch");
    }

    @Override // com.google.android.gms.internal.icing.zzaa
    public final void zza(zzac zzacVar, String str, zzw[] zzwVarArr) throws RemoteException {
        Parcel parcelZza = zza();
        zzd.zza(parcelZza, zzacVar);
        parcelZza.writeString(null);
        parcelZza.writeTypedArray(zzwVarArr, 0);
        zzb(1, parcelZza);
    }

    @Override // com.google.android.gms.internal.icing.zzaa
    public final void zza(zzac zzacVar, com.google.firebase.appindexing.internal.zza[] zzaVarArr) throws RemoteException {
        Parcel parcelZza = zza();
        zzd.zza(parcelZza, zzacVar);
        parcelZza.writeTypedArray(zzaVarArr, 0);
        zzb(7, parcelZza);
    }
}
