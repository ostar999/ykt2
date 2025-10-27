package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzdb {
    private final byte[] buffer;
    private final zzdk zzgo;

    private zzdb(int i2) {
        byte[] bArr = new byte[i2];
        this.buffer = bArr;
        this.zzgo = zzdk.zzb(bArr);
    }

    public final zzct zzar() {
        this.zzgo.zzav();
        return new zzdd(this.buffer);
    }

    public final zzdk zzas() {
        return this.zzgo;
    }

    public /* synthetic */ zzdb(int i2, zzcw zzcwVar) {
        this(i2);
    }
}
