package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
final class zzdm implements zzhg {
    private final zzdk zzgo;

    private zzdm(zzdk zzdkVar) {
        zzdk zzdkVar2 = (zzdk) zzeb.zza(zzdkVar, "output");
        this.zzgo = zzdkVar2;
        zzdkVar2.zzgy = this;
    }

    public static zzdm zza(zzdk zzdkVar) {
        zzdm zzdmVar = zzdkVar.zzgy;
        return zzdmVar != null ? zzdmVar : new zzdm(zzdkVar);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzab(int i2) throws IOException {
        this.zzgo.zzb(i2, 3);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzac(int i2) throws IOException {
        this.zzgo.zzb(i2, 4);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final int zzay() {
        return zzdx.zze.zzkx;
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzb(int i2, long j2) throws IOException {
        this.zzgo.zzb(i2, j2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzc(int i2, int i3) throws IOException {
        this.zzgo.zzc(i2, i3);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzd(int i2, int i3) throws IOException {
        this.zzgo.zzd(i2, i3);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zze(int i2, int i3) throws IOException {
        this.zzgo.zze(i2, i3);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzf(int i2, int i3) throws IOException {
        this.zzgo.zzf(i2, i3);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzg(int i2, List<Double> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zzgo.zza(i2, list.get(i3).doubleValue());
                i3++;
            }
            return;
        }
        this.zzgo.zzb(i2, 2);
        int iZzb = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzb += zzdk.zzb(list.get(i4).doubleValue());
        }
        this.zzgo.zzp(iZzb);
        while (i3 < list.size()) {
            this.zzgo.zza(list.get(i3).doubleValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzh(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zzgo.zzc(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.zzgo.zzb(i2, 2);
        int iZzy = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzy += zzdk.zzy(list.get(i4).intValue());
        }
        this.zzgo.zzp(iZzy);
        while (i3 < list.size()) {
            this.zzgo.zzo(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzi(int i2, long j2) throws IOException {
        this.zzgo.zza(i2, j2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzj(int i2, long j2) throws IOException {
        this.zzgo.zzc(i2, j2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzk(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zzgo.zzf(i2, list.get(i3).intValue());
                i3++;
            }
            return;
        }
        this.zzgo.zzb(i2, 2);
        int iZzx = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzx += zzdk.zzx(list.get(i4).intValue());
        }
        this.zzgo.zzp(iZzx);
        while (i3 < list.size()) {
            this.zzgo.zzr(list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzl(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zzgo.zzc(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.zzgo.zzb(i2, 2);
        int iZzi = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzi += zzdk.zzi(list.get(i4).longValue());
        }
        this.zzgo.zzp(iZzi);
        while (i3 < list.size()) {
            this.zzgo.zzd(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzm(int i2, int i3) throws IOException {
        this.zzgo.zzf(i2, i3);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzn(int i2, int i3) throws IOException {
        this.zzgo.zzc(i2, i3);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzb(int i2, Object obj, zzfu zzfuVar) throws IOException {
        zzdk zzdkVar = this.zzgo;
        zzdkVar.zzb(i2, 3);
        zzfuVar.zza((zzfh) obj, zzdkVar.zzgy);
        zzdkVar.zzb(i2, 4);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzc(int i2, long j2) throws IOException {
        this.zzgo.zzc(i2, j2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzd(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zzgo.zza(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.zzgo.zzb(i2, 2);
        int iZzf = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzf += zzdk.zzf(list.get(i4).longValue());
        }
        this.zzgo.zzp(iZzf);
        while (i3 < list.size()) {
            this.zzgo.zzb(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zze(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zzgo.zzc(i2, list.get(i3).longValue());
                i3++;
            }
            return;
        }
        this.zzgo.zzb(i2, 2);
        int iZzh = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzh += zzdk.zzh(list.get(i4).longValue());
        }
        this.zzgo.zzp(iZzh);
        while (i3 < list.size()) {
            this.zzgo.zzd(list.get(i3).longValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzf(int i2, List<Float> list, boolean z2) throws IOException {
        int i3 = 0;
        if (!z2) {
            while (i3 < list.size()) {
                this.zzgo.zza(i2, list.get(i3).floatValue());
                i3++;
            }
            return;
        }
        this.zzgo.zzb(i2, 2);
        int iZzb = 0;
        for (int i4 = 0; i4 < list.size(); i4++) {
            iZzb += zzdk.zzb(list.get(i4).floatValue());
        }
        this.zzgo.zzp(iZzb);
        while (i3 < list.size()) {
            this.zzgo.zza(list.get(i3).floatValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, float f2) throws IOException {
        this.zzgo.zza(i2, f2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzc(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (z2) {
            this.zzgo.zzb(i2, 2);
            int iZze = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                iZze += zzdk.zze(list.get(i4).longValue());
            }
            this.zzgo.zzp(iZze);
            while (i3 < list.size()) {
                this.zzgo.zzb(list.get(i3).longValue());
                i3++;
            }
            return;
        }
        while (i3 < list.size()) {
            this.zzgo.zza(i2, list.get(i3).longValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzi(int i2, List<Boolean> list, boolean z2) throws IOException {
        int i3 = 0;
        if (z2) {
            this.zzgo.zzb(i2, 2);
            int iZzf = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                iZzf += zzdk.zzf(list.get(i4).booleanValue());
            }
            this.zzgo.zzp(iZzf);
            while (i3 < list.size()) {
                this.zzgo.zze(list.get(i3).booleanValue());
                i3++;
            }
            return;
        }
        while (i3 < list.size()) {
            this.zzgo.zza(i2, list.get(i3).booleanValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzj(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (z2) {
            this.zzgo.zzb(i2, 2);
            int iZzu = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                iZzu += zzdk.zzu(list.get(i4).intValue());
            }
            this.zzgo.zzp(iZzu);
            while (i3 < list.size()) {
                this.zzgo.zzp(list.get(i3).intValue());
                i3++;
            }
            return;
        }
        while (i3 < list.size()) {
            this.zzgo.zzd(i2, list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzm(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (z2) {
            this.zzgo.zzb(i2, 2);
            int iZzv = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                iZzv += zzdk.zzv(list.get(i4).intValue());
            }
            this.zzgo.zzp(iZzv);
            while (i3 < list.size()) {
                this.zzgo.zzq(list.get(i3).intValue());
                i3++;
            }
            return;
        }
        while (i3 < list.size()) {
            this.zzgo.zze(i2, list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzn(int i2, List<Long> list, boolean z2) throws IOException {
        int i3 = 0;
        if (z2) {
            this.zzgo.zzb(i2, 2);
            int iZzg = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                iZzg += zzdk.zzg(list.get(i4).longValue());
            }
            this.zzgo.zzp(iZzg);
            while (i3 < list.size()) {
                this.zzgo.zzc(list.get(i3).longValue());
                i3++;
            }
            return;
        }
        while (i3 < list.size()) {
            this.zzgo.zzb(i2, list.get(i3).longValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, double d3) throws IOException {
        this.zzgo.zza(i2, d3);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, long j2) throws IOException {
        this.zzgo.zza(i2, j2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, boolean z2) throws IOException {
        this.zzgo.zza(i2, z2);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzb(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (z2) {
            this.zzgo.zzb(i2, 2);
            int iZzw = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                iZzw += zzdk.zzw(list.get(i4).intValue());
            }
            this.zzgo.zzp(iZzw);
            while (i3 < list.size()) {
                this.zzgo.zzr(list.get(i3).intValue());
                i3++;
            }
            return;
        }
        while (i3 < list.size()) {
            this.zzgo.zzf(i2, list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, String str) throws IOException {
        this.zzgo.zza(i2, str);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, zzct zzctVar) throws IOException {
        this.zzgo.zza(i2, zzctVar);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, Object obj, zzfu zzfuVar) throws IOException {
        this.zzgo.zza(i2, (zzfh) obj, zzfuVar);
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, Object obj) throws IOException {
        if (obj instanceof zzct) {
            this.zzgo.zzb(i2, (zzct) obj);
        } else {
            this.zzgo.zza(i2, (zzfh) obj);
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, List<Integer> list, boolean z2) throws IOException {
        int i3 = 0;
        if (z2) {
            this.zzgo.zzb(i2, 2);
            int iZzt = 0;
            for (int i4 = 0; i4 < list.size(); i4++) {
                iZzt += zzdk.zzt(list.get(i4).intValue());
            }
            this.zzgo.zzp(iZzt);
            while (i3 < list.size()) {
                this.zzgo.zzo(list.get(i3).intValue());
                i3++;
            }
            return;
        }
        while (i3 < list.size()) {
            this.zzgo.zzc(i2, list.get(i3).intValue());
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzb(int i2, List<zzct> list) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            this.zzgo.zza(i2, list.get(i3));
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zzb(int i2, List<?> list, zzfu zzfuVar) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            zzb(i2, list.get(i3), zzfuVar);
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, List<String> list) throws IOException {
        int i3 = 0;
        if (list instanceof zzeo) {
            zzeo zzeoVar = (zzeo) list;
            while (i3 < list.size()) {
                Object objZzad = zzeoVar.zzad(i3);
                if (objZzad instanceof String) {
                    this.zzgo.zza(i2, (String) objZzad);
                } else {
                    this.zzgo.zza(i2, (zzct) objZzad);
                }
                i3++;
            }
            return;
        }
        while (i3 < list.size()) {
            this.zzgo.zza(i2, list.get(i3));
            i3++;
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final void zza(int i2, List<?> list, zzfu zzfuVar) throws IOException {
        for (int i3 = 0; i3 < list.size(); i3++) {
            zza(i2, list.get(i3), zzfuVar);
        }
    }

    @Override // com.google.android.gms.internal.icing.zzhg
    public final <K, V> void zza(int i2, zzey<K, V> zzeyVar, Map<K, V> map) throws IOException {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            this.zzgo.zzb(i2, 2);
            this.zzgo.zzp(zzds.zza(zzeyVar.zzmi, 1, entry.getKey()) + zzds.zza(zzeyVar.zzmj, 2, entry.getValue()));
            zzdk zzdkVar = this.zzgo;
            K key = entry.getKey();
            V value = entry.getValue();
            zzds.zza(zzdkVar, zzeyVar.zzmi, 1, key);
            zzds.zza(zzdkVar, zzeyVar.zzmj, 2, value);
        }
    }
}
