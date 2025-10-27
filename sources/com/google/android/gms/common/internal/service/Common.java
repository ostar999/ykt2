package com.google.android.gms.common.internal.service;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.Api;

/* loaded from: classes3.dex */
public final class Common {

    @KeepForSdk
    public static final Api<Api.ApiOptions.NoOptions> API;

    @KeepForSdk
    public static final Api.ClientKey<zah> CLIENT_KEY;
    private static final Api.AbstractClientBuilder<zah, Api.ApiOptions.NoOptions> zapv;
    public static final zab zapw;

    static {
        Api.ClientKey<zah> clientKey = new Api.ClientKey<>();
        CLIENT_KEY = clientKey;
        zac zacVar = new zac();
        zapv = zacVar;
        API = new Api<>("Common.API", zacVar, clientKey);
        zapw = new zae();
    }
}
