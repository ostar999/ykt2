package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zzj implements Runnable {
    private final /* synthetic */ Task zzg;
    private final /* synthetic */ zzi zzm;

    public zzj(zzi zziVar, Task task) {
        this.zzm = zziVar;
        this.zzg = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzm.mLock) {
            if (this.zzm.zzl != null) {
                this.zzm.zzl.onComplete(this.zzg);
            }
        }
    }
}
