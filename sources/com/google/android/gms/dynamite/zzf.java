package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule;

/* loaded from: classes3.dex */
final class zzf implements DynamiteModule.VersionPolicy {
    @Override // com.google.android.gms.dynamite.DynamiteModule.VersionPolicy
    public final DynamiteModule.VersionPolicy.zzb zza(Context context, String str, DynamiteModule.VersionPolicy.zza zzaVar) throws DynamiteModule.LoadingException {
        DynamiteModule.VersionPolicy.zzb zzbVar = new DynamiteModule.VersionPolicy.zzb();
        zzbVar.zzir = zzaVar.getLocalVersion(context, str);
        int iZza = zzaVar.zza(context, str, true);
        zzbVar.zzis = iZza;
        int i2 = zzbVar.zzir;
        if (i2 == 0 && iZza == 0) {
            zzbVar.zzit = 0;
        } else if (iZza >= i2) {
            zzbVar.zzit = 1;
        } else {
            zzbVar.zzit = -1;
        }
        return zzbVar;
    }
}
