package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.GoogleApiAvailabilityCache;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes3.dex */
final class zaal extends zaau {
    final /* synthetic */ zaak zafz;
    private final Map<Api.Client, zaam> zagn;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public zaal(zaak zaakVar, Map<Api.Client, zaam> map) {
        super(zaakVar, null);
        this.zafz = zaakVar;
        this.zagn = map;
    }

    @Override // com.google.android.gms.common.api.internal.zaau
    @GuardedBy("mLock")
    @WorkerThread
    public final void zaal() {
        GoogleApiAvailabilityCache googleApiAvailabilityCache = new GoogleApiAvailabilityCache(this.zafz.zaey);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (Api.Client client : this.zagn.keySet()) {
            if (!client.requiresGooglePlayServices() || this.zagn.get(client).zaee) {
                arrayList2.add(client);
            } else {
                arrayList.add(client);
            }
        }
        int i2 = 0;
        int clientAvailability = -1;
        if (!arrayList.isEmpty()) {
            int size = arrayList.size();
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                clientAvailability = googleApiAvailabilityCache.getClientAvailability(this.zafz.mContext, (Api.Client) obj);
                if (clientAvailability != 0) {
                    break;
                }
            }
        } else {
            int size2 = arrayList2.size();
            while (i2 < size2) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                clientAvailability = googleApiAvailabilityCache.getClientAvailability(this.zafz.mContext, (Api.Client) obj2);
                if (clientAvailability == 0) {
                    break;
                }
            }
        }
        if (clientAvailability != 0) {
            this.zafz.zafv.zaa(new zaao(this, this.zafz, new ConnectionResult(clientAvailability, null)));
            return;
        }
        if (this.zafz.zagh && this.zafz.zagf != null) {
            this.zafz.zagf.connect();
        }
        for (Api.Client client2 : this.zagn.keySet()) {
            zaam zaamVar = this.zagn.get(client2);
            if (!client2.requiresGooglePlayServices() || googleApiAvailabilityCache.getClientAvailability(this.zafz.mContext, client2) == 0) {
                client2.connect(zaamVar);
            } else {
                this.zafz.zafv.zaa(new zaan(this, this.zafz, zaamVar));
            }
        }
    }
}
