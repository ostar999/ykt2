package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
public abstract class zzdf {
    private int zzgq;
    private int zzgr;
    private boolean zzgs;

    private zzdf() {
        this.zzgq = 100;
        this.zzgr = Integer.MAX_VALUE;
        this.zzgs = false;
    }

    public static zzdf zza(byte[] bArr, int i2, int i3, boolean z2) {
        zzdh zzdhVar = new zzdh(bArr, 0, i3, false);
        try {
            zzdhVar.zzn(i3);
            return zzdhVar;
        } catch (zzeh e2) {
            throw new IllegalArgumentException(e2);
        }
    }

    public abstract int zzat();

    public abstract int zzn(int i2) throws zzeh;
}
