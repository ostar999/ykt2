package com.google.android.gms.tasks;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
final class zzf implements Runnable {
    private final /* synthetic */ Task zzg;
    private final /* synthetic */ zze zzi;

    public zzf(zze zzeVar, Task task) {
        this.zzi = zzeVar;
        this.zzg = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Task task = (Task) this.zzi.zze.then(this.zzg);
            if (task == null) {
                this.zzi.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            Executor executor = TaskExecutors.zzw;
            task.addOnSuccessListener(executor, this.zzi);
            task.addOnFailureListener(executor, this.zzi);
            task.addOnCanceledListener(executor, this.zzi);
        } catch (RuntimeExecutionException e2) {
            if (e2.getCause() instanceof Exception) {
                this.zzi.zzf.setException((Exception) e2.getCause());
            } else {
                this.zzi.zzf.setException(e2);
            }
        } catch (Exception e3) {
            this.zzi.zzf.setException(e3);
        }
    }
}
