package com.google.android.gms.internal.icing;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzfk<T> implements zzfu<T> {
    private final zzfh zzmn;
    private final zzgm<?, ?> zzmo;
    private final boolean zzmp;
    private final zzdn<?> zzmq;

    private zzfk(zzgm<?, ?> zzgmVar, zzdn<?> zzdnVar, zzfh zzfhVar) {
        this.zzmo = zzgmVar;
        this.zzmp = zzdnVar.zze(zzfhVar);
        this.zzmq = zzdnVar;
        this.zzmn = zzfhVar;
    }

    public static <T> zzfk<T> zza(zzgm<?, ?> zzgmVar, zzdn<?> zzdnVar, zzfh zzfhVar) {
        return new zzfk<>(zzgmVar, zzdnVar, zzfhVar);
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final boolean equals(T t2, T t3) {
        if (!this.zzmo.zzp(t2).equals(this.zzmo.zzp(t3))) {
            return false;
        }
        if (this.zzmp) {
            return this.zzmq.zzd(t2).equals(this.zzmq.zzd(t3));
        }
        return true;
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final int hashCode(T t2) {
        int iHashCode = this.zzmo.zzp(t2).hashCode();
        return this.zzmp ? (iHashCode * 53) + this.zzmq.zzd(t2).hashCode() : iHashCode;
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final void zzc(T t2, T t3) {
        zzfw.zza(this.zzmo, t2, t3);
        if (this.zzmp) {
            zzfw.zza(this.zzmq, t2, t3);
        }
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final void zzf(T t2) {
        this.zzmo.zzf(t2);
        this.zzmq.zzf(t2);
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final boolean zzm(T t2) {
        return this.zzmq.zzd(t2).isInitialized();
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final int zzn(T t2) {
        zzgm<?, ?> zzgmVar = this.zzmo;
        int iZzq = zzgmVar.zzq(zzgmVar.zzp(t2)) + 0;
        return this.zzmp ? iZzq + this.zzmq.zzd(t2).zzbe() : iZzq;
    }

    @Override // com.google.android.gms.internal.icing.zzfu
    public final void zza(T t2, zzhg zzhgVar) throws IOException {
        Iterator it = this.zzmq.zzd(t2).iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            zzdu zzduVar = (zzdu) entry.getKey();
            if (zzduVar.zzbh() != zzhh.MESSAGE || zzduVar.zzbi() || zzduVar.zzbj()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            }
            if (entry instanceof zzek) {
                zzhgVar.zza(zzduVar.zzbf(), (Object) ((zzek) entry).zzcc().zzad());
            } else {
                zzhgVar.zza(zzduVar.zzbf(), entry.getValue());
            }
        }
        zzgm<?, ?> zzgmVar = this.zzmo;
        zzgmVar.zzc(zzgmVar.zzp(t2), zzhgVar);
    }
}
