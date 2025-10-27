package com.google.android.gms.tasks;

import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
final class zzp implements Runnable {
    private final /* synthetic */ Task zzg;
    private final /* synthetic */ zzo zzs;

    public zzp(zzo zzoVar, Task task) {
        this.zzs = zzoVar;
        this.zzg = task;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            Task taskThen = this.zzs.zzr.then(this.zzg.getResult());
            if (taskThen == null) {
                this.zzs.onFailure(new NullPointerException("Continuation returned null"));
                return;
            }
            Executor executor = TaskExecutors.zzw;
            taskThen.addOnSuccessListener(executor, this.zzs);
            taskThen.addOnFailureListener(executor, this.zzs);
            taskThen.addOnCanceledListener(executor, this.zzs);
        } catch (RuntimeExecutionException e2) {
            if (e2.getCause() instanceof Exception) {
                this.zzs.onFailure((Exception) e2.getCause());
            } else {
                this.zzs.onFailure(e2);
            }
        } catch (CancellationException unused) {
            this.zzs.onCanceled();
        } catch (Exception e3) {
            this.zzs.onFailure(e3);
        }
    }
}
