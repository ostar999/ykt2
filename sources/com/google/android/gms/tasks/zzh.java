package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zzh implements Runnable {
    private final /* synthetic */ zzg zzk;

    public zzh(zzg zzgVar) {
        this.zzk = zzgVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzk.mLock) {
            if (this.zzk.zzj != null) {
                this.zzk.zzj.onCanceled();
            }
        }
    }
}
