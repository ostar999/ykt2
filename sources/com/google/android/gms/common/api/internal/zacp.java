package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
public final class zacp {
    public static final Status zalb = new Status(8, "The connection to Google Play services was lost");
    private static final BasePendingResult<?>[] zalc = new BasePendingResult[0];
    private final Map<Api.AnyClientKey<?>, Api.Client> zahd;

    @VisibleForTesting
    final Set<BasePendingResult<?>> zald = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
    private final zacq zale = new zaco(this);

    public zacp(Map<Api.AnyClientKey<?>, Api.Client> map) {
        this.zahd = map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void release() throws RemoteException {
        for (BasePendingResult basePendingResult : (BasePendingResult[]) this.zald.toArray(zalc)) {
            com.google.android.gms.common.api.zac zacVar = null;
            byte b3 = 0;
            byte b4 = 0;
            byte b5 = 0;
            basePendingResult.zaa((zacq) null);
            if (basePendingResult.zal() != null) {
                basePendingResult.setResultCallback(null);
                IBinder serviceBrokerBinder = this.zahd.get(((BaseImplementation.ApiMethodImpl) basePendingResult).getClientKey()).getServiceBrokerBinder();
                if (basePendingResult.isReady()) {
                    basePendingResult.zaa(new zacr(basePendingResult, zacVar, serviceBrokerBinder, b5 == true ? 1 : 0));
                } else {
                    if (serviceBrokerBinder == null || !serviceBrokerBinder.isBinderAlive()) {
                        basePendingResult.zaa((zacq) null);
                        basePendingResult.cancel();
                        basePendingResult.zal().intValue();
                        throw null;
                    }
                    zacr zacrVar = new zacr(basePendingResult, b4 == true ? 1 : 0, serviceBrokerBinder, b3 == true ? 1 : 0);
                    basePendingResult.zaa(zacrVar);
                    try {
                        serviceBrokerBinder.linkToDeath(zacrVar, 0);
                    } catch (RemoteException unused) {
                        basePendingResult.cancel();
                        basePendingResult.zal().intValue();
                        throw null;
                    }
                }
                this.zald.remove(basePendingResult);
            } else if (basePendingResult.zaq()) {
                this.zald.remove(basePendingResult);
            }
        }
    }

    public final void zabv() {
        for (BasePendingResult basePendingResult : (BasePendingResult[]) this.zald.toArray(zalc)) {
            basePendingResult.zab(zalb);
        }
    }

    public final void zac(BasePendingResult<? extends Result> basePendingResult) {
        this.zald.add(basePendingResult);
        basePendingResult.zaa(this.zale);
    }
}
