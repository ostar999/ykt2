package com.google.firebase.appindexing.internal;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

@VisibleForTesting
/* loaded from: classes4.dex */
final class zzj {
    private final zzy zzfc;
    private final TaskCompletionSource<Void> zzfd = new TaskCompletionSource<>();
    final /* synthetic */ zzk zzfe;

    public zzj(zzk zzkVar, zzy zzyVar) {
        this.zzfe = zzkVar;
        this.zzfc = zzyVar;
    }

    public final void execute() {
        synchronized (this.zzfe.zzff) {
            Preconditions.checkState(this.zzfe.zzfg == 0);
            this.zzfe.zzfg = 1;
        }
        this.zzfe.zzfa.doWrite(new zzl(this)).addOnFailureListener(this.zzfe, new OnFailureListener(this) { // from class: com.google.firebase.appindexing.internal.zzm
            private final zzj zzfi;

            {
                this.zzfi = this;
            }

            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) {
                this.zzfi.zza(exc);
            }
        });
    }

    public final Task<Void> getTask() {
        return this.zzfd.getTask();
    }

    public final /* synthetic */ void zza(Exception exc) {
        zzj zzjVar;
        synchronized (this.zzfe.zzff) {
            if (this.zzfe.zzff.peek() == this) {
                this.zzfe.zzff.remove();
                this.zzfe.zzfg = 0;
                zzjVar = (zzj) this.zzfe.zzff.peek();
            } else {
                zzjVar = null;
            }
        }
        this.zzfd.trySetException(exc);
        if (zzjVar != null) {
            zzjVar.execute();
        }
    }
}
