package com.google.firebase.appindexing.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* loaded from: classes4.dex */
public final class zzu extends com.google.android.gms.internal.icing.zzb implements zzr {
    public zzu(IBinder iBinder) {
        super(iBinder, "com.google.firebase.appindexing.internal.IAppIndexingService");
    }

    @Override // com.google.firebase.appindexing.internal.zzr
    public final zzg zza(IStatusCallback iStatusCallback, zzy zzyVar) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.icing.zzd.zza(parcelZza, iStatusCallback);
        com.google.android.gms.internal.icing.zzd.zza(parcelZza, zzyVar);
        Parcel parcelZza2 = zza(8, parcelZza);
        zzg zzgVar = (zzg) com.google.android.gms.internal.icing.zzd.zza(parcelZza2, zzg.CREATOR);
        parcelZza2.recycle();
        return zzgVar;
    }
}
