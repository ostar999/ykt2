package com.google.android.gms.internal.icing;

import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.search.GoogleNowAuthState;

/* loaded from: classes3.dex */
final class zzav extends zzar {
    private final /* synthetic */ zzaw zzbq;

    public zzav(zzaw zzawVar) {
        this.zzbq = zzawVar;
    }

    @Override // com.google.android.gms.internal.icing.zzar, com.google.android.gms.internal.icing.zzam
    public final void zza(Status status, GoogleNowAuthState googleNowAuthState) {
        if (this.zzbq.zzbp) {
            Log.d("SearchAuth", "GetGoogleNowAuthImpl success");
        }
        this.zzbq.setResult((zzaw) new zzay(status, googleNowAuthState));
    }
}
