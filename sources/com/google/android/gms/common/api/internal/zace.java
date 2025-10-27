package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.BinderThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.signin.SignInOptions;
import java.util.Set;

/* loaded from: classes3.dex */
public final class zace extends com.google.android.gms.signin.internal.zad implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> zakm = com.google.android.gms.signin.zab.zapv;
    private final Context mContext;
    private final Handler mHandler;
    private Set<Scope> mScopes;
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> zaaw;
    private ClientSettings zafa;
    private com.google.android.gms.signin.zac zagf;
    private zacf zakn;

    @WorkerThread
    public zace(Context context, Handler handler, @NonNull ClientSettings clientSettings) {
        this(context, handler, clientSettings, zakm);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @WorkerThread
    public final void zac(com.google.android.gms.signin.internal.zak zakVar) {
        ConnectionResult connectionResult = zakVar.getConnectionResult();
        if (connectionResult.isSuccess()) {
            ResolveAccountResponse resolveAccountResponseZacv = zakVar.zacv();
            ConnectionResult connectionResult2 = resolveAccountResponseZacv.getConnectionResult();
            if (!connectionResult2.isSuccess()) {
                String strValueOf = String.valueOf(connectionResult2);
                StringBuilder sb = new StringBuilder(strValueOf.length() + 48);
                sb.append("Sign-in succeeded with resolve account failure: ");
                sb.append(strValueOf);
                Log.wtf("SignInCoordinator", sb.toString(), new Exception());
                this.zakn.zag(connectionResult2);
                this.zagf.disconnect();
                return;
            }
            this.zakn.zaa(resolveAccountResponseZacv.getAccountAccessor(), this.mScopes);
        } else {
            this.zakn.zag(connectionResult);
        }
        this.zagf.disconnect();
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    @WorkerThread
    public final void onConnected(@Nullable Bundle bundle) {
        this.zagf.zaa(this);
    }

    @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
    @WorkerThread
    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zakn.zag(connectionResult);
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    @WorkerThread
    public final void onConnectionSuspended(int i2) {
        this.zagf.disconnect();
    }

    @WorkerThread
    public final void zaa(zacf zacfVar) {
        com.google.android.gms.signin.zac zacVar = this.zagf;
        if (zacVar != null) {
            zacVar.disconnect();
        }
        this.zafa.setClientSessionId(Integer.valueOf(System.identityHashCode(this)));
        Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> abstractClientBuilder = this.zaaw;
        Context context = this.mContext;
        Looper looper = this.mHandler.getLooper();
        ClientSettings clientSettings = this.zafa;
        this.zagf = (com.google.android.gms.signin.zac) abstractClientBuilder.buildClient(context, looper, clientSettings, (ClientSettings) clientSettings.getSignInOptions(), (GoogleApiClient.ConnectionCallbacks) this, (GoogleApiClient.OnConnectionFailedListener) this);
        this.zakn = zacfVar;
        Set<Scope> set = this.mScopes;
        if (set == null || set.isEmpty()) {
            this.mHandler.post(new zacd(this));
        } else {
            this.zagf.connect();
        }
    }

    @Override // com.google.android.gms.signin.internal.zad, com.google.android.gms.signin.internal.zac
    @BinderThread
    public final void zab(com.google.android.gms.signin.internal.zak zakVar) {
        this.mHandler.post(new zacg(this, zakVar));
    }

    public final com.google.android.gms.signin.zac zabo() {
        return this.zagf;
    }

    public final void zabq() {
        com.google.android.gms.signin.zac zacVar = this.zagf;
        if (zacVar != null) {
            zacVar.disconnect();
        }
    }

    @WorkerThread
    public zace(Context context, Handler handler, @NonNull ClientSettings clientSettings, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> abstractClientBuilder) {
        this.mContext = context;
        this.mHandler = handler;
        this.zafa = (ClientSettings) Preconditions.checkNotNull(clientSettings, "ClientSettings must not be null");
        this.mScopes = clientSettings.getRequiredScopes();
        this.zaaw = abstractClientBuilder;
    }
}
