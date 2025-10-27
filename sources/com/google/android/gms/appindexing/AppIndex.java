package com.google.android.gms.appindexing;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.icing.zzaj;
import com.google.android.gms.internal.icing.zzf;

@VisibleForTesting
@Deprecated
/* loaded from: classes3.dex */
public final class AppIndex {
    public static final Api<Api.ApiOptions.NoOptions> API;
    public static final Api<Api.ApiOptions.NoOptions> APP_INDEX_API;
    public static final AppIndexApi AppIndexApi;

    static {
        Api<Api.ApiOptions.NoOptions> api = zzf.zzg;
        API = api;
        APP_INDEX_API = api;
        AppIndexApi = new zzaj();
    }

    private AppIndex() {
    }
}
