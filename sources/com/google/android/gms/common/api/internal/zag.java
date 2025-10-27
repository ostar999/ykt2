package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.tasks.TaskCompletionSource;

/* loaded from: classes3.dex */
public final class zag extends zae<Void> {
    private final RegisterListenerMethod<Api.AnyClient, ?> zact;
    private final UnregisterListenerMethod<Api.AnyClient, ?> zacu;

    public zag(zabv zabvVar, TaskCompletionSource<Void> taskCompletionSource) {
        super(3, taskCompletionSource);
        this.zact = zabvVar.zakc;
        this.zacu = zabvVar.zakd;
    }

    @Override // com.google.android.gms.common.api.internal.zae, com.google.android.gms.common.api.internal.zac
    public final /* bridge */ /* synthetic */ void zaa(@NonNull zaz zazVar, boolean z2) {
    }

    @Override // com.google.android.gms.common.api.internal.zab
    @Nullable
    public final Feature[] zaa(GoogleApiManager.zaa<?> zaaVar) {
        return this.zact.getRequiredFeatures();
    }

    @Override // com.google.android.gms.common.api.internal.zab
    public final boolean zab(GoogleApiManager.zaa<?> zaaVar) {
        return this.zact.shouldAutoResolveMissingFeatures();
    }

    @Override // com.google.android.gms.common.api.internal.zae
    public final void zad(GoogleApiManager.zaa<?> zaaVar) throws RemoteException {
        this.zact.registerListener(zaaVar.zaad(), this.zacq);
        if (this.zact.getListenerKey() != null) {
            zaaVar.zabi().put(this.zact.getListenerKey(), new zabv(this.zact, this.zacu));
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
