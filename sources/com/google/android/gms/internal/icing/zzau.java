package com.google.android.gms.internal.icing;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.search.SearchAuth;

/* loaded from: classes3.dex */
final class zzau extends BaseImplementation.ApiMethodImpl<Status, zzap> {
    private final String zzbk;
    private final String zzbo;
    private final boolean zzbp;

    public zzau(GoogleApiClient googleApiClient, String str) {
        super(SearchAuth.API, googleApiClient);
        this.zzbp = Log.isLoggable("SearchAuth", 3);
        this.zzbk = str;
        this.zzbo = googleApiClient.getContext().getPackageName();
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public final /* synthetic */ Result createFailedResult(Status status) {
        if (this.zzbp) {
            String strValueOf = String.valueOf(status.getStatusMessage());
            Log.d("SearchAuth", strValueOf.length() != 0 ? "ClearTokenImpl received failure: ".concat(strValueOf) : new String("ClearTokenImpl received failure: "));
        }
        return status;
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        zzap zzapVar = (zzap) anyClient;
        if (this.zzbp) {
            Log.d("SearchAuth", "ClearTokenImpl started");
        }
        ((zzan) zzapVar.getService()).zzb(new zzat(this), this.zzbo, this.zzbk);
    }
}
