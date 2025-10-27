package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
final class zaax implements GoogleApiClient.OnConnectionFailedListener {
    private final /* synthetic */ StatusPendingResult zahl;

    public zaax(zaaw zaawVar, StatusPendingResult statusPendingResult) {
        this.zahl = statusPendingResult;
    }

    @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zahl.setResult(new Status(8));
    }
}
