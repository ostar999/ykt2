package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.BaseGmsClient;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes3.dex */
final class zaan extends zabd {
    private final /* synthetic */ BaseGmsClient.ConnectionProgressReportCallbacks zagp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaan(zaal zaalVar, zabb zabbVar, BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        super(zabbVar);
        this.zagp = connectionProgressReportCallbacks;
    }

    @Override // com.google.android.gms.common.api.internal.zabd
    @GuardedBy("mLock")
    public final void zaal() {
        this.zagp.onReportServiceBinding(new ConnectionResult(16, null));
    }
}
