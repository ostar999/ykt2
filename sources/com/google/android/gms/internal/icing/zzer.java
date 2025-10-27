package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
abstract class zzer {
    private static final zzer zzma;
    private static final zzer zzmb;

    static {
        zzeq zzeqVar = null;
        zzma = new zzet();
        zzmb = new zzes();
    }

    private zzer() {
    }

    public static zzer zzcf() {
        return zzma;
    }

    public static zzer zzcg() {
        return zzmb;
    }

    public abstract void zza(Object obj, long j2);

    public abstract <L> void zza(Object obj, Object obj2, long j2);
}
