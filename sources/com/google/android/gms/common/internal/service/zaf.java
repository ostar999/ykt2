package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;

/* loaded from: classes3.dex */
abstract class zaf<R extends Result> extends BaseImplementation.ApiMethodImpl<R, zah> {
    public zaf(GoogleApiClient googleApiClient) {
        super(Common.API, googleApiClient);
    }
}
