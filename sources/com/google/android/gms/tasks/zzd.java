package com.google.android.gms.tasks;

/* loaded from: classes3.dex */
final class zzd implements Runnable {
    private final /* synthetic */ Task zzg;
    private final /* synthetic */ zzc zzh;

    public zzd(zzc zzcVar, Task task) {
        this.zzh = zzcVar;
        this.zzg = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzg.isCanceled()) {
            this.zzh.zzf.zza();
            return;
        }
        try {
            this.zzh.zzf.setResult(this.zzh.zze.then(this.zzg));
        } catch (RuntimeExecutionException e2) {
            if (e2.getCause() instanceof Exception) {
                this.zzh.zzf.setException((Exception) e2.getCause());
            } else {
                this.zzh.zzf.setException(e2);
            }
        } catch (Exception e3) {
            this.zzh.zzf.setException(e3);
        }
    }
}
