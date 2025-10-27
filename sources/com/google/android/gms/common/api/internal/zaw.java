package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.SignInOptions;

/* loaded from: classes3.dex */
public final class zaw<O extends Api.ApiOptions> extends GoogleApi<O> {
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> zacf;
    private final ClientSettings zafa;
    private final Api.Client zafj;
    private final zap zafk;

    public zaw(@NonNull Context context, Api<O> api, Looper looper, @NonNull Api.Client client, @NonNull zap zapVar, ClientSettings clientSettings, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> abstractClientBuilder) {
        super(context, api, looper);
        this.zafj = client;
        this.zafk = zapVar;
        this.zafa = clientSettings;
        this.zacf = abstractClientBuilder;
        this.zabo.zaa(this);
    }

    @Override // com.google.android.gms.common.api.GoogleApi
    public final Api.Client zaa(Looper looper, GoogleApiManager.zaa<O> zaaVar) {
        this.zafk.zaa(zaaVar);
        return this.zafj;
    }

    public final Api.Client zaad() {
        return this.zafj;
    }

    @Override // com.google.android.gms.common.api.GoogleApi
    public final zace zaa(Context context, Handler handler) {
        return new zace(context, handler, this.zafa, this.zacf);
    }
}
