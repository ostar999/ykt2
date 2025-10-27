package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zam {
    private final int zadm;
    private final ConnectionResult zadn;

    public zam(ConnectionResult connectionResult, int i2) {
        Preconditions.checkNotNull(connectionResult);
        this.zadn = connectionResult;
        this.zadm = i2;
    }

    public final ConnectionResult getConnectionResult() {
        return this.zadn;
    }

    public final int zap() {
        return this.zadm;
    }
}
