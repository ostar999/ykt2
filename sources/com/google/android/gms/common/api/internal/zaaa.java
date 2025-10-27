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
final class zaaa implements OnCompleteListener<Map<ApiKey<?>, String>> {
    private final /* synthetic */ zav zafl;
    private SignInConnectionListener zafo;

    public zaaa(zav zavVar, SignInConnectionListener signInConnectionListener) {
        this.zafl = zavVar;
        this.zafo = signInConnectionListener;
    }

    public final void cancel() {
        this.zafo.onComplete();
    }

    @Override // com.google.android.gms.tasks.OnCompleteListener
    public final void onComplete(@NonNull Task<Map<ApiKey<?>, String>> task) {
        this.zafl.zaer.lock();
        try {
            if (!this.zafl.zafe) {
                this.zafo.onComplete();
                return;
            }
            if (task.isSuccessful()) {
                zav zavVar = this.zafl;
                zavVar.zafg = new ArrayMap(zavVar.zaev.size());
                Iterator it = this.zafl.zaev.values().iterator();
                while (it.hasNext()) {
                    this.zafl.zafg.put(((zaw) it.next()).getApiKey(), ConnectionResult.RESULT_SUCCESS);
                }
            } else if (task.getException() instanceof AvailabilityException) {
                AvailabilityException availabilityException = (AvailabilityException) task.getException();
                if (this.zafl.zafc) {
                    zav zavVar2 = this.zafl;
                    zavVar2.zafg = new ArrayMap(zavVar2.zaev.size());
                    for (zaw zawVar : this.zafl.zaev.values()) {
                        Object apiKey = zawVar.getApiKey();
                        ConnectionResult connectionResult = availabilityException.getConnectionResult((GoogleApi<? extends Api.ApiOptions>) zawVar);
                        if (this.zafl.zaa((zaw<?>) zawVar, connectionResult)) {
                            this.zafl.zafg.put(apiKey, new ConnectionResult(16));
                        } else {
                            this.zafl.zafg.put(apiKey, connectionResult);
                        }
                    }
                } else {
                    this.zafl.zafg = availabilityException.zaj();
                }
            } else {
                Log.e("ConnectionlessGAC", "Unexpected availability exception", task.getException());
                this.zafl.zafg = Collections.emptyMap();
            }
            if (this.zafl.isConnected()) {
                this.zafl.zaff.putAll(this.zafl.zafg);
                if (this.zafl.zaac() == null) {
                    this.zafl.zaaa();
                    this.zafl.zaab();
                    this.zafl.zaez.signalAll();
                }
            }
            this.zafo.onComplete();
        } finally {
            this.zafl.zaer.unlock();
        }
    }
}
