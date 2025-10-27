package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
public final class zaf<ResultT> extends zab {
    private final TaskCompletionSource<ResultT> zacq;
    private final TaskApiCall<Api.AnyClient, ResultT> zacr;
    private final StatusExceptionMapper zacs;

    public zaf(int i2, TaskApiCall<Api.AnyClient, ResultT> taskApiCall, TaskCompletionSource<ResultT> taskCompletionSource, StatusExceptionMapper statusExceptionMapper) {
        super(i2);
        this.zacq = taskCompletionSource;
        this.zacr = taskApiCall;
        this.zacs = statusExceptionMapper;
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final void zaa(@NonNull Status status) {
        this.zacq.trySetException(this.zacs.getException(status));
    }

    @Override // com.google.android.gms.common.api.internal.zab
    public final boolean zab(GoogleApiManager.zaa<?> zaaVar) {
        return this.zacr.shouldAutoResolveMissingFeatures();
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final void zac(GoogleApiManager.zaa<?> zaaVar) throws DeadObjectException {
        try {
            this.zacr.doExecute(zaaVar.zaad(), this.zacq);
        } catch (DeadObjectException e2) {
            throw e2;
        } catch (RemoteException e3) {
            zaa(zac.zaa(e3));
        } catch (RuntimeException e4) {
            zaa(e4);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final void zaa(@NonNull RuntimeException runtimeException) {
        this.zacq.trySetException(runtimeException);
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final void zaa(@NonNull zaz zazVar, boolean z2) {
        zazVar.zaa(this.zacq, z2);
    }

    @Override // com.google.android.gms.common.api.internal.zab
    @Nullable
    public final Feature[] zaa(GoogleApiManager.zaa<?> zaaVar) {
        return this.zacr.zabr();
    }
}
