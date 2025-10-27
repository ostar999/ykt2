package com.google.android.gms.common.api.internal;

import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.Preconditions;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
final class zaam implements BaseGmsClient.ConnectionProgressReportCallbacks {
    private final Api<?> mApi;
    private final boolean zaee;
    private final WeakReference<zaak> zago;

    public zaam(zaak zaakVar, Api<?> api, boolean z2) {
        this.zago = new WeakReference<>(zaakVar);
        this.mApi = api;
        this.zaee = z2;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient.ConnectionProgressReportCallbacks
    public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
        zaak zaakVar = this.zago.get();
        if (zaakVar == null) {
            return;
        }
        Preconditions.checkState(Looper.myLooper() == zaakVar.zafv.zaeh.getLooper(), "onReportServiceBinding must be called on the GoogleApiClient handler thread");
        zaakVar.zaer.lock();
        try {
            if (zaakVar.zac(0)) {
                if (!connectionResult.isSuccess()) {
                    zaakVar.zab(connectionResult, this.mApi, this.zaee);
                }
                if (zaakVar.zaam()) {
                    zaakVar.zaan();
                }
            }
        } finally {
            zaakVar.zaer.unlock();
        }
    }
}
