package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zzb implements OnSuccessListener<Void> {
    private final /* synthetic */ OnTokenCanceledListener zzb;

    public zzb(zza zzaVar, OnTokenCanceledListener onTokenCanceledListener) {
        this.zzb = onTokenCanceledListener;
    }

    @Override // com.google.android.gms.tasks.OnSuccessListener
    public final /* synthetic */ void onSuccess(Void r12) {
        this.zzb.onCanceled();
    }
}
