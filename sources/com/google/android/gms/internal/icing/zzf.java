package com.google.android.gms.internal.icing;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.util.VisibleForTesting;

@ShowFirstParty
@VisibleForTesting
/* loaded from: classes3.dex */
public final class zzf {
    private static final Api.ClientKey<zzah> zze;
    private static final Api.AbstractClientBuilder<zzah, Api.ApiOptions.NoOptions> zzf;
    public static final Api<Api.ApiOptions.NoOptions> zzg;

    @Deprecated
    private static final zzab zzh;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.gms.internal.icing.zzab, com.google.android.gms.internal.icing.zzaj] */
    static {
        Api.ClientKey<zzah> clientKey = new Api.ClientKey<>();
        zze = clientKey;
        zze zzeVar = new zze();
        zzf = zzeVar;
        zzg = new Api<>("AppDataSearch.LIGHTWEIGHT_API", zzeVar, clientKey);
        zzh = new zzaj();
    }
}
