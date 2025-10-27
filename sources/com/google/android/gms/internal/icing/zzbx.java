package com.google.android.gms.internal.icing;

import java.io.Serializable;

/* loaded from: classes3.dex */
public abstract class zzbx<T> implements Serializable {
    public static <T> zzbx<T> zzb(T t2) {
        return new zzbz(zzca.checkNotNull(t2));
    }

    public static <T> zzbx<T> zzw() {
        return zzbv.zzdt;
    }

    public abstract T get();

    public abstract boolean isPresent();
}
