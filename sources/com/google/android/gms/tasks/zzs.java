package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zzs implements OnTokenCanceledListener {
    private final /* synthetic */ TaskCompletionSource zzv;

    public zzs(TaskCompletionSource taskCompletionSource) {
        this.zzv = taskCompletionSource;
    }

    @Override // com.google.android.gms.tasks.OnTokenCanceledListener
    public final void onCanceled() {
        this.zzv.zza.zza();
    }
}
