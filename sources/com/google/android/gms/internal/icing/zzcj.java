package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzcj extends zzci {
    private final zzch zzee = new zzch();

    @Override // com.google.android.gms.internal.icing.zzci
    public final void zza(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        }
        if (th2 == null) {
            throw new NullPointerException("The suppressed exception cannot be null.");
        }
        this.zzee.zza(th, true).add(th2);
    }
}
