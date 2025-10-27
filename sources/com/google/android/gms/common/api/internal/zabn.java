package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;

/* loaded from: classes3.dex */
public final class zabn<O extends Api.ApiOptions> extends zaag {
    private final GoogleApi<O> zajj;

    public zabn(GoogleApi<O> googleApi) {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.zajj = googleApi;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t2) {
        return (T) this.zajj.doRead((GoogleApi<O>) t2);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t2) {
        return (T) this.zajj.doWrite((GoogleApi<O>) t2);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final Context getContext() {
        return this.zajj.getApplicationContext();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final Looper getLooper() {
        return this.zajj.getLooper();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void zaa(zack zackVar) {
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void zab(zack zackVar) {
    }
}
