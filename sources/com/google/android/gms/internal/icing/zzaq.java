package com.google.android.gms.internal.icing;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class zzaq extends zzb implements zzan {
    public zzaq(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.search.internal.ISearchAuthService");
    }

    @Override // com.google.android.gms.internal.icing.zzan
    public final void zza(zzam zzamVar, String str, String str2) throws RemoteException {
        Parcel parcelZza = zza();
        zzd.zza(parcelZza, zzamVar);
        parcelZza.writeString(str);
        parcelZza.writeString(str2);
        zzb(1, parcelZza);
    }

    @Override // com.google.android.gms.internal.icing.zzan
    public final void zzb(zzam zzamVar, String str, String str2) throws RemoteException {
        Parcel parcelZza = zza();
        zzd.zza(parcelZza, zzamVar);
        parcelZza.writeString(str);
        parcelZza.writeString(str2);
        zzb(2, parcelZza);
    }
}
