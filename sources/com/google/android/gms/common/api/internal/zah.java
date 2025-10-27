package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
public final class zah extends zae<Boolean> {
    private final ListenerHolder.ListenerKey<?> zacv;

    public zah(ListenerHolder.ListenerKey<?> listenerKey, TaskCompletionSource<Boolean> taskCompletionSource) {
        super(4, taskCompletionSource);
        this.zacv = listenerKey;
    }

    @Override // com.google.android.gms.common.api.internal.zae, com.google.android.gms.common.api.internal.zac
    public final /* bridge */ /* synthetic */ void zaa(@NonNull zaz zazVar, boolean z2) {
    }

    @Override // com.google.android.gms.common.api.internal.zab
    @Nullable
    public final Feature[] zaa(GoogleApiManager.zaa<?> zaaVar) {
        zabv zabvVar = zaaVar.zabi().get(this.zacv);
        if (zabvVar == null) {
            return null;
        }
        return zabvVar.zakc.getRequiredFeatures();
    }

    @Override // com.google.android.gms.common.api.internal.zab
    public final boolean zab(GoogleApiManager.zaa<?> zaaVar) {
        zabv zabvVar = zaaVar.zabi().get(this.zacv);
        return zabvVar != null && zabvVar.zakc.shouldAutoResolveMissingFeatures();
    }

    @Override // com.google.android.gms.common.api.internal.zae
    public final void zad(GoogleApiManager.zaa<?> zaaVar) throws RemoteException {
        zabv zabvVarRemove = zaaVar.zabi().remove(this.zacv);
        if (zabvVarRemove == null) {
            this.zacq.trySetResult(Boolean.FALSE);
        } else {
            zabvVarRemove.zakd.unregisterListener(zaaVar.zaad(), this.zacq);
            zabvVarRemove.zakc.clearListener();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zae, com.google.android.gms.common.api.internal.zac
    public final /* bridge */ /* synthetic */ void zaa(@NonNull RuntimeException runtimeException) {
        super.zaa(runtimeException);
    }

    @Override // com.google.android.gms.common.api.internal.zae, com.google.android.gms.common.api.internal.zac
    public final /* bridge */ /* synthetic */ void zaa(@NonNull Status status) {
        super.zaa(status);
    }
}
