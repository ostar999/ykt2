package com.google.android.gms.internal.icing;

import java.io.IOException;

/* loaded from: classes3.dex */
final class zzgo extends zzgm<zzgp, zzgp> {
    @Override // com.google.android.gms.internal.icing.zzgm
    public final /* synthetic */ void zza(zzgp zzgpVar, zzhg zzhgVar) throws IOException {
        zzgpVar.zzb(zzhgVar);
    }

    @Override // com.google.android.gms.internal.icing.zzgm
    public final /* synthetic */ void zzc(zzgp zzgpVar, zzhg zzhgVar) throws IOException {
        zzgpVar.zza(zzhgVar);
    }

    @Override // com.google.android.gms.internal.icing.zzgm
    public final /* synthetic */ void zze(Object obj, zzgp zzgpVar) {
        ((zzdx) obj).zzkc = zzgpVar;
    }

    @Override // com.google.android.gms.internal.icing.zzgm
    public final void zzf(Object obj) {
        ((zzdx) obj).zzkc.zzai();
    }

    @Override // com.google.android.gms.internal.icing.zzgm
    public final /* synthetic */ int zzn(zzgp zzgpVar) {
        return zzgpVar.zzbl();
    }

    @Override // com.google.android.gms.internal.icing.zzgm
    public final /* synthetic */ zzgp zzp(Object obj) {
        return ((zzdx) obj).zzkc;
    }

    @Override // com.google.android.gms.internal.icing.zzgm
    public final /* synthetic */ int zzq(zzgp zzgpVar) {
        return zzgpVar.zzdm();
    }

    @Override // com.google.android.gms.internal.icing.zzgm
    public final /* synthetic */ zzgp zzf(zzgp zzgpVar, zzgp zzgpVar2) {
        zzgp zzgpVar3 = zzgpVar;
        zzgp zzgpVar4 = zzgpVar2;
        return zzgpVar4.equals(zzgp.zzdl()) ? zzgpVar3 : zzgp.zza(zzgpVar3, zzgpVar4);
    }
}
