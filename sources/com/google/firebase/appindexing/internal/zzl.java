package com.google.firebase.appindexing.internal;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseAppIndexingException;

@VisibleForTesting
/* loaded from: classes4.dex */
final class zzl extends TaskApiCall<zze, Void> {
    final /* synthetic */ zzj zzfh;

    public zzl(zzj zzjVar) {
        this.zzfh = zzjVar;
    }

    @Override // com.google.android.gms.common.api.internal.TaskApiCall
    public final /* synthetic */ void doExecute(Api.AnyClient anyClient, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException {
        zzg zzgVarZza = ((zzr) ((zze) anyClient).getService()).zza(new zzo(this, taskCompletionSource), this.zzfh.zzfc);
        int i2 = zzgVarZza == null ? 2 : zzgVarZza.status;
        boolean z2 = true;
        zzj zzjVar = null;
        if (i2 == 3) {
            if (zzt.isLoggable(4)) {
                Log.i(FirebaseAppIndex.APP_INDEXING_API_TAG, "Queue was full. API call will be retried.");
            }
            if (taskCompletionSource.trySetResult(null)) {
                synchronized (this.zzfh.zzfe.zzff) {
                    if (this.zzfh.zzfe.zzfg == 0) {
                        zzjVar = (zzj) this.zzfh.zzfe.zzff.peek();
                        Preconditions.checkState(zzjVar == this.zzfh);
                    } else {
                        this.zzfh.zzfe.zzfg = 2;
                    }
                }
            }
        } else {
            if (i2 != 1) {
                StringBuilder sb = new StringBuilder(41);
                sb.append("API call failed. Status code: ");
                sb.append(i2);
                String string = sb.toString();
                if (zzt.isLoggable(6)) {
                    Log.e(FirebaseAppIndex.APP_INDEXING_API_TAG, string);
                }
                if (taskCompletionSource.trySetResult(null)) {
                    this.zzfh.zzfd.setException(new FirebaseAppIndexingException("Indexing error."));
                }
            }
            synchronized (this.zzfh.zzfe.zzff) {
                if (((zzj) this.zzfh.zzfe.zzff.poll()) != this.zzfh) {
                    z2 = false;
                }
                Preconditions.checkState(z2);
                zzjVar = (zzj) this.zzfh.zzfe.zzff.peek();
                this.zzfh.zzfe.zzfg = 0;
            }
        }
        if (zzjVar != null) {
            zzjVar.execute();
        }
    }
}
