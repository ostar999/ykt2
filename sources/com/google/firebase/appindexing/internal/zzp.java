package com.google.firebase.appindexing.internal;

import android.content.Context;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.firebase.FirebaseExceptionMapper;

/* loaded from: classes4.dex */
final class zzp extends GoogleApi<Api.ApiOptions.NoOptions> {
    public zzp(Context context) {
        super(context, com.google.android.gms.internal.icing.zzf.zzg, (Api.ApiOptions) null, new FirebaseExceptionMapper());
    }
}
