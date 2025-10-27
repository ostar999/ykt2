package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes3.dex */
final class zaao extends zabd {
    private final /* synthetic */ ConnectionResult zagq;
    private final /* synthetic */ zaal zagr;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaao(zaal zaalVar, zabb zabbVar, ConnectionResult connectionResult) {
        super(zabbVar);
        this.zagr = zaalVar;
        this.zagq = connectionResult;
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    @GuardedBy("mLock")
    public final void zaal() {
        this.zagr.zafz.zae(this.zagq);
    }
}
