package com.google.android.gms.common.internal;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.internal.BaseGmsClient;

/* loaded from: classes3.dex */
final class zaf implements BaseGmsClient.BaseConnectionCallbacks {
    private final /* synthetic */ ConnectionCallbacks zaou;

    public zaf(ConnectionCallbacks connectionCallbacks) {
        this.zaou = connectionCallbacks;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnected(@Nullable Bundle bundle) {
        this.zaou.onConnected(bundle);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.BaseConnectionCallbacks
    public final void onConnectionSuspended(int i2) {
        this.zaou.onConnectionSuspended(i2);
    }
}
