package com.google.android.gms.internal.icing;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.search.GoogleNowAuthState;
import com.google.android.gms.search.SearchAuthApi;

/* loaded from: classes3.dex */
final class zzay implements SearchAuthApi.GoogleNowAuthResult {
    private final GoogleNowAuthState zzce;
    private final Status zzv;

    public zzay(Status status, GoogleNowAuthState googleNowAuthState) {
        this.zzv = status;
        this.zzce = googleNowAuthState;
    }

    @Override // com.google.android.gms.search.SearchAuthApi.GoogleNowAuthResult
    public final GoogleNowAuthState getGoogleNowAuthState() {
        return this.zzce;
    }

    @Override // com.google.android.gms.common.api.Result
    public final Status getStatus() {
        return this.zzv;
    }
}
