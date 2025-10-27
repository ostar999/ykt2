package com.google.firebase.appindexing.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.icing.zzaj;

/* loaded from: classes4.dex */
final class zzq extends zzs {
    private final /* synthetic */ zza[] zzfm;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zzq(zzn zznVar, zza[] zzaVarArr) {
        super(null);
        this.zzfm = zzaVarArr;
    }

    @Override // com.google.firebase.appindexing.internal.zzs
    public final void zza(com.google.android.gms.internal.icing.zzaa zzaaVar) throws RemoteException {
        zzaaVar.zza(new zzaj.zzc(this), this.zzfm);
    }
}
