package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
abstract class zae<T> extends zab {
    protected final TaskCompletionSource<T> zacq;

    public zae(int i2, TaskCompletionSource<T> taskCompletionSource) {
        super(i2);
        this.zacq = taskCompletionSource;
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public void zaa(@NonNull Status status) {
        this.zacq.trySetException(new ApiException(status));
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public void zaa(@NonNull zaz zazVar, boolean z2) {
    }

    @Override // com.google.android.gms.common.api.internal.zac
    public final void zac(GoogleApiManager.zaa<?> zaaVar) throws DeadObjectException {
        try {
            zad(zaaVar);
        } catch (DeadObjectException e2) {
            zaa(zac.zaa(e2));
            throw e2;
        } catch (RemoteException e3) {
            zaa(zac.zaa(e3));
        } catch (RuntimeException e4) {
            zaa(e4);
        }
    }

    public abstract void zad(GoogleApiManager.zaa<?> zaaVar) throws RemoteException;

    @Override // com.google.android.gms.common.api.internal.zac
    public void zaa(@NonNull RuntimeException runtimeException) {
        this.zacq.trySetException(runtimeException);
    }
}
