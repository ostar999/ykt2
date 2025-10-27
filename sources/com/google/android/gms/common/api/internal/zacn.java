package com.google.android.gms.common.api.internal;

import androidx.annotation.WorkerThread;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

/* loaded from: classes3.dex */
final class zacn implements Runnable {
    private final /* synthetic */ zack zaky;
    private final /* synthetic */ Result zakz;

    public zacn(zack zackVar, Result result) {
        this.zaky = zackVar;
        this.zakz = result;
    }

    @Override // java.lang.Runnable
    @WorkerThread
    public final void run() {
        try {
            try {
                ThreadLocal<Boolean> threadLocal = BasePendingResult.zado;
                threadLocal.set(Boolean.TRUE);
                this.zaky.zakw.sendMessage(this.zaky.zakw.obtainMessage(0, this.zaky.zakr.onSuccess(this.zakz)));
                threadLocal.set(Boolean.FALSE);
                zack zackVar = this.zaky;
                zack.zab(this.zakz);
                GoogleApiClient googleApiClient = (GoogleApiClient) this.zaky.zadr.get();
                if (googleApiClient != null) {
                    googleApiClient.zab(this.zaky);
                }
            } catch (RuntimeException e2) {
                this.zaky.zakw.sendMessage(this.zaky.zakw.obtainMessage(1, e2));
                BasePendingResult.zado.set(Boolean.FALSE);
                zack zackVar2 = this.zaky;
                zack.zab(this.zakz);
                GoogleApiClient googleApiClient2 = (GoogleApiClient) this.zaky.zadr.get();
                if (googleApiClient2 != null) {
                    googleApiClient2.zab(this.zaky);
                }
            }
        } catch (Throwable th) {
            BasePendingResult.zado.set(Boolean.FALSE);
            zack zackVar3 = this.zaky;
            zack.zab(this.zakz);
            GoogleApiClient googleApiClient3 = (GoogleApiClient) this.zaky.zadr.get();
            if (googleApiClient3 != null) {
                googleApiClient3.zab(this.zaky);
            }
            throw th;
        }
    }
}
