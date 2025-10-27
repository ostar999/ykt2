package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzes extends zzer {
    private zzes() {
        super();
    }

    private static <E> zzee<E> zzb(Object obj, long j2) {
        return (zzee) zzgs.zzo(obj, j2);
    }

    @Override // com.google.android.gms.internal.icing.zzer
    public final void zza(Object obj, long j2) {
        zzb(obj, j2).zzai();
    }

    @Override // com.google.android.gms.internal.icing.zzer
    public final <E> void zza(Object obj, Object obj2, long j2) {
        zzee zzeeVarZzb = zzb(obj, j2);
        zzee zzeeVarZzb2 = zzb(obj2, j2);
        int size = zzeeVarZzb.size();
        int size2 = zzeeVarZzb2.size();
        if (size > 0 && size2 > 0) {
            if (!zzeeVarZzb.zzah()) {
                zzeeVarZzb = zzeeVarZzb.zzj(size2 + size);
            }
            zzeeVarZzb.addAll(zzeeVarZzb2);
        }
        if (size > 0) {
            zzeeVarZzb2 = zzeeVarZzb;
        }
        zzgs.zza(obj, j2, zzeeVarZzb2);
    }
}
