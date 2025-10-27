package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zaay implements GoogleApiClient.ConnectionCallbacks {
    private final /* synthetic */ zaaw zagv;
    private final /* synthetic */ StatusPendingResult zahl;
    private final /* synthetic */ AtomicReference zahm;

    public zaay(zaaw zaawVar, AtomicReference atomicReference, StatusPendingResult statusPendingResult) {
        this.zagv = zaawVar;
        this.zahm = atomicReference;
        this.zahl = statusPendingResult;
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnected(Bundle bundle) {
        this.zagv.zaa((GoogleApiClient) this.zahm.get(), this.zahl, true);
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnectionSuspended(int i2) {
    }
}
