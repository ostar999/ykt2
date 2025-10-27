package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;
import java.io.IOException;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzdq extends zzdn<zzdx.zzc> {
    @Override // com.google.android.gms.internal.icing.zzdn
    public final int zza(Map.Entry<?, ?> entry) {
        throw new NoSuchMethodError();
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    public final zzds<zzdx.zzc> zzd(Object obj) {
        return ((zzdx.zzd) obj).zzkj;
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    public final boolean zze(zzfh zzfhVar) {
        return zzfhVar instanceof zzdx.zzd;
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    public final void zzf(Object obj) {
        zzd(obj).zzai();
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    public final zzds<zzdx.zzc> zze(Object obj) {
        zzdx.zzd zzdVar = (zzdx.zzd) obj;
        if (zzdVar.zzkj.isImmutable()) {
            zzdVar.zzkj = (zzds) zzdVar.zzkj.clone();
        }
        return zzdVar.zzkj;
    }

    @Override // com.google.android.gms.internal.icing.zzdn
    public final void zza(zzhg zzhgVar, Map.Entry<?, ?> entry) throws IOException {
        throw new NoSuchMethodError();
    }
}
