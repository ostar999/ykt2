package com.google.android.gms.internal.icing;

import com.google.android.gms.internal.icing.zzdx;

/* loaded from: classes3.dex */
final class zzdy implements zzfe {
    private static final zzdy zzkf = new zzdy();

    private zzdy() {
    }

    public static zzdy zzbs() {
        return zzkf;
    }

    @Override // com.google.android.gms.internal.icing.zzfe
    public final boolean zzb(Class<?> cls) {
        return zzdx.class.isAssignableFrom(cls);
    }

    @Override // com.google.android.gms.internal.icing.zzfe
    public final zzff zzc(Class<?> cls) {
        if (!zzdx.class.isAssignableFrom(cls)) {
            String name = cls.getName();
            throw new IllegalArgumentException(name.length() != 0 ? "Unsupported message type: ".concat(name) : new String("Unsupported message type: "));
        }
        try {
            return (zzff) zzdx.zza(cls.asSubclass(zzdx.class)).zza(zzdx.zze.zzko, (Object) null, (Object) null);
        } catch (Exception e2) {
            String name2 = cls.getName();
            throw new RuntimeException(name2.length() != 0 ? "Unable to get message info for ".concat(name2) : new String("Unable to get message info for "), e2);
        }
    }
}
