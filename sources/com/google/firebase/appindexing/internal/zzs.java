package com.google.firebase.appindexing.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.icing.zzah;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes4.dex */
abstract class zzs extends TaskApiCall<zzah, Void> implements BaseImplementation.ResultHolder<Status> {
    private TaskCompletionSource<Void> zzfn;

    private zzs() {
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    public /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        this.zzfn = taskCompletionSource;
        zza((com.google.android.gms.internal.icing.zzaa) ((zzah) anyClient).getService());
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
    public void setFailedResult(Status status) {
        Preconditions.checkArgument(!status.isSuccess(), "Failed result must not be success.");
        this.zzfn.setException(zzaf.zza(status, status.getStatusMessage()));
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder
    public /* synthetic */ void setResult(Status status) {
        Status status2 = status;
        if (status2.isSuccess()) {
            this.zzfn.setResult(null);
        } else {
            this.zzfn.setException(zzaf.zza(status2, "User Action indexing error, please try again."));
        }
    }

    public abstract void zza(com.google.android.gms.internal.icing.zzaa zzaaVar) throws RemoteException;

    public /* synthetic */ zzs(zzq zzqVar) {
        this();
    }
}
