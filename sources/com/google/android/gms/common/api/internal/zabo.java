package com.google.android.gms.common.api.internal;

import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.GoogleApiManager;

/* loaded from: classes3.dex */
final class zabo implements Runnable {
    private final /* synthetic */ ConnectionResult zajc;
    private final /* synthetic */ GoogleApiManager.zab zajk;

    public zabo(GoogleApiManager.zab zabVar, ConnectionResult connectionResult) {
        this.zajk = zabVar;
        this.zajc = connectionResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        GoogleApiManager.zaa zaaVar = (GoogleApiManager.zaa) GoogleApiManager.this.zaim.get(this.zajk.zaft);
        if (zaaVar == null) {
            return;
        }
        if (!this.zajc.isSuccess()) {
            zaaVar.onConnectionFailed(this.zajc);
            return;
        }
        GoogleApiManager.zab.zaa(this.zajk, true);
        if (this.zajk.zais.requiresSignIn()) {
            this.zajk.zabp();
            return;
        }
        try {
            this.zajk.zais.getRemoteService(null, this.zajk.zais.getScopesForConnectionlessNonSignIn());
        } catch (SecurityException e2) {
            Log.e("GoogleApiManager", "Failed to get service from broker. ", e2);
            zaaVar.onConnectionFailed(new ConnectionResult(10));
        }
    }
}
