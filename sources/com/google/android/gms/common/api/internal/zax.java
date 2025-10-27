package com.google.android.gms.common.api.internal;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes3.dex */
final class zax implements OnCompleteListener<Map<ApiKey<?>, String>> {
    private final /* synthetic */ zav zafl;

    private zax(zav zavVar) {
        this.zafl = zavVar;
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(@NonNull Task<Map<ApiKey<?>, String>> task) {
        this.zafl.zaer.lock();
        try {
            if (this.zafl.zafe) {
                if (task.isSuccessful()) {
                    zav zavVar = this.zafl;
                    zavVar.zaff = new ArrayMap(zavVar.zaeu.size());
                    Iterator it = this.zafl.zaeu.values().iterator();
                    while (it.hasNext()) {
                        this.zafl.zaff.put(((zaw) it.next()).getApiKey(), ConnectionResult.RESULT_SUCCESS);
                    }
                } else if (task.getException() instanceof AvailabilityException) {
                    AvailabilityException availabilityException = (AvailabilityException) task.getException();
                    if (this.zafl.zafc) {
                        zav zavVar2 = this.zafl;
                        zavVar2.zaff = new ArrayMap(zavVar2.zaeu.size());
                        for (zaw zawVar : this.zafl.zaeu.values()) {
                            Object apiKey = zawVar.getApiKey();
                            ConnectionResult connectionResult = availabilityException.getConnectionResult((GoogleApi<? extends Api.ApiOptions>) zawVar);
                            if (this.zafl.zaa((zaw<?>) zawVar, connectionResult)) {
                                this.zafl.zaff.put(apiKey, new ConnectionResult(16));
                            } else {
                                this.zafl.zaff.put(apiKey, connectionResult);
                            }
                        }
                    } else {
                        this.zafl.zaff = availabilityException.zaj();
                    }
                    zav zavVar3 = this.zafl;
                    zavVar3.zafi = zavVar3.zaac();
                } else {
                    Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                    this.zafl.zaff = Collections.emptyMap();
                    this.zafl.zafi = new ConnectionResult(8);
                }
                if (this.zafl.zafg != null) {
                    this.zafl.zaff.putAll(this.zafl.zafg);
                    zav zavVar4 = this.zafl;
                    zavVar4.zafi = zavVar4.zaac();
                }
                if (this.zafl.zafi == null) {
                    this.zafl.zaaa();
                    this.zafl.zaab();
                } else {
                    zav.zaa(this.zafl, false);
                    this.zafl.zaex.zac(this.zafl.zafi);
                }
                this.zafl.zaez.signalAll();
            }
        } finally {
            this.zafl.zaer.unlock();
        }
    }
}
