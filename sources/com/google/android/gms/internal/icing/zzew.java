package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzew implements zzfe {
    private zzfe[] zzmh;

    public zzew(zzfe... zzfeVarArr) {
        this.zzmh = zzfeVarArr;
    }

    @Override // com.google.android.gms.internal.icing.zzfe
    public final boolean zzb(Class<?> cls) {
        for (zzfe zzfeVar : this.zzmh) {
            if (zzfeVar.zzb(cls)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.android.gms.internal.icing.zzfe
    public final zzff zzc(Class<?> cls) {
        for (zzfe zzfeVar : this.zzmh) {
            if (zzfeVar.zzb(cls)) {
                return zzfeVar.zzc(cls);
            }
        }
        String name = cls.getName();
        throw new UnsupportedOperationException(name.length() != 0 ? "No factory is available for message type: ".concat(name) : new String("No factory is available for message type: "));
    }
}
