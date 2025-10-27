package com.google.android.gms.common.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.BaseGmsClient;

/* loaded from: classes3.dex */
final class zag implements BaseGmsClient.BaseOnConnectionFailedListener {
    private final /* synthetic */ OnConnectionFailedListener zapc;

    public zag(OnConnectionFailedListener onConnectionFailedListener) {
        this.zapc = onConnectionFailedListener;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseOnConnectionFailedListener
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zapc.onConnectionFailed(connectionResult);
    }
}
