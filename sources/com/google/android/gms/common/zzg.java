package com.google.android.gms.common;

import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
abstract class zzg extends zze {
    private static final WeakReference<byte[]> zzw = new WeakReference<>(null);
    private WeakReference<byte[]> zzv;

    public zzg(byte[] bArr) {
        super(bArr);
        this.zzv = zzw;
    }

    @Override // com.google.android.gms.common.zze
    public final byte[] getBytes() {
        byte[] bArrZzd;
        synchronized (this) {
            bArrZzd = this.zzv.get();
            if (bArrZzd == null) {
                bArrZzd = zzd();
                this.zzv = new WeakReference<>(bArrZzd);
            }
        }
        return bArrZzd;
    }

    public abstract byte[] zzd();
}
