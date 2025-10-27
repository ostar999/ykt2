package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.SimpleClientAdapter;
import java.util.Iterator;

/* loaded from: classes3.dex */
public final class zaaf implements zabb {
    private final zabe zafv;
    private boolean zafw = false;

    public zaaf(zabe zabeVar) {
        this.zafv = zabeVar;
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final void begin() {
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final void connect() {
        if (this.zafw) {
            this.zafw = false;
            this.zafv.zaa(new zaah(this, this));
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final boolean disconnect() {
        if (this.zafw) {
            return false;
        }
        if (!this.zafv.zaeh.zaav()) {
            this.zafv.zaf(null);
            return true;
        }
        this.zafw = true;
        Iterator<zack> it = this.zafv.zaeh.zahi.iterator();
        while (it.hasNext()) {
            it.next().zabt();
        }
        return false;
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T t2) {
        return (T) execute(t2);
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T t2) {
        try {
            this.zafv.zaeh.zahj.zac(t2);
            zaaw zaawVar = this.zafv.zaeh;
            Api.Client client = zaawVar.zahd.get(t2.getClientKey());
            Preconditions.checkNotNull(client, "Appropriate Api was not requested.");
            if (client.isConnected() || !this.zafv.zaht.containsKey(t2.getClientKey())) {
                boolean z2 = client instanceof SimpleClientAdapter;
                A client2 = client;
                if (z2) {
                    client2 = ((SimpleClientAdapter) client).getClient();
                }
                t2.run(client2);
            } else {
                t2.setFailedResult(new Status(17));
            }
        } catch (DeadObjectException unused) {
            this.zafv.zaa(new zaai(this, this));
        }
        return t2;
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final void onConnected(Bundle bundle) {
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final void onConnectionSuspended(int i2) {
        this.zafv.zaf(null);
        this.zafv.zahx.zab(i2, this.zafw);
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z2) {
    }

    public final void zaak() {
        if (this.zafw) {
            this.zafw = false;
            this.zafv.zaeh.zahj.release();
            disconnect();
        }
    }
}
