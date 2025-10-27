package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
public final class zzhl implements zzcc<zzhk> {
    private static zzhl zzre = new zzhl();
    private final zzcc<zzhk> zzrf;

    private zzhl(zzcc<zzhk> zzccVar) {
        this.zzrf = zzcb.zza(zzccVar);
    }

    public static boolean zzeb() {
        return ((zzhk) zzre.get()).zzeb();
    }

    @Override // com.google.android.gms.internal.icing.zzcc
    public final /* synthetic */ zzhk get() {
        return this.zzrf.get();
    }

    public zzhl() {
        this(zzcb.zzc(new zzhn()));
    }
}
