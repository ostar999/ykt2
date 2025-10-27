package com.google.android.gms.internal.icing;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.search.SearchAuth;
import com.google.android.gms.search.SearchAuthApi;

/* loaded from: classes3.dex */
final class zzaw extends BaseImplementation.ApiMethodImpl<SearchAuthApi.GoogleNowAuthResult, zzap> {
    private final String zzbo;
    private final boolean zzbp;
    private final String zzbr;

    public zzaw(GoogleApiClient googleApiClient, String str) {
        super(SearchAuth.API, googleApiClient);
        this.zzbp = Log.isLoggable("SearchAuth", 3);
        this.zzbr = str;
        this.zzbo = googleApiClient.getContext().getPackageName();
    }

    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    public final /* synthetic */ Result createFailedResult(Status status) {
        if (this.zzbp) {
            String strValueOf = String.valueOf(status.getStatusMessage());
            Log.d("SearchAuth", strValueOf.length() != 0 ? "GetGoogleNowAuthImpl received failure: ".concat(strValueOf) : new String("GetGoogleNowAuthImpl received failure: "));
        }
        return new zzay(status, null);
    }

    @Override // com.google.android.gms.common.api.internal.BaseImplementation.ApiMethodImpl
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient) throws RemoteException {
        zzap zzapVar = (zzap) anyClient;
        if (this.zzbp) {
            Log.d("SearchAuth", "GetGoogleNowAuthImpl started");
        }
        ((zzan) zzapVar.getService()).zza(new zzav(this), this.zzbo, this.zzbr);
    }
}
