package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes3.dex */
public final class zabe implements zabr, zar {
    private final Context mContext;
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> zacf;
    final zaaw zaeh;
    private final Lock zaer;
    private final Map<Api<?>, Boolean> zaew;
    private final GoogleApiAvailabilityLight zaey;
    private final ClientSettings zafa;
    final Map<Api.AnyClientKey<?>, Api.Client> zahd;
    private final Condition zahr;
    private final zabg zahs;
    private volatile zabb zahu;
    int zahw;
    final zabs zahx;
    final Map<Api.AnyClientKey<?>, ConnectionResult> zaht = new HashMap();
    private ConnectionResult zahv = null;

    public zabe(Context context, zaaw zaawVar, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> abstractClientBuilder, ArrayList<zap> arrayList, zabs zabsVar) {
        this.mContext = context;
        this.zaer = lock;
        this.zaey = googleApiAvailabilityLight;
        this.zahd = map;
        this.zafa = clientSettings;
        this.zaew = map2;
        this.zacf = abstractClientBuilder;
        this.zaeh = zaawVar;
        this.zahx = zabsVar;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            zap zapVar = arrayList.get(i2);
            i2++;
            zapVar.zaa(this);
        }
        this.zahs = new zabg(this, looper);
        this.zahr = lock.newCondition();
        this.zahu = new zaat(this);
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect() throws InterruptedException {
        connect();
        while (isConnecting()) {
            try {
                this.zahr.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zahv;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, null);
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final void connect() {
        this.zahu.connect();
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final void disconnect() {
        if (this.zahu.disconnect()) {
            this.zaht.clear();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String strConcat = String.valueOf(str).concat("  ");
        printWriter.append((CharSequence) str).append("mState=").println(this.zahu);
        for (Api<?> api : this.zaew.keySet()) {
            printWriter.append((CharSequence) str).append((CharSequence) api.getName()).println(":");
            this.zahd.get(api.getClientKey()).dump(strConcat, fileDescriptor, printWriter, strArr);
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t2) {
        t2.zar();
        return (T) this.zahu.enqueue(t2);
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t2) {
        t2.zar();
        return (T) this.zahu.execute(t2);
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @Nullable
    @GuardedBy("mLock")
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        Api.AnyClientKey<?> clientKey = api.getClientKey();
        if (!this.zahd.containsKey(clientKey)) {
            return null;
        }
        if (this.zahd.get(clientKey).isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        if (this.zaht.containsKey(clientKey)) {
            return this.zaht.get(clientKey);
        }
        return null;
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final boolean isConnected() {
        return this.zahu instanceof zaaf;
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final boolean isConnecting() {
        return this.zahu instanceof zaak;
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        return false;
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void maybeSignOut() {
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnected(@Nullable Bundle bundle) {
        this.zaer.lock();
        try {
            this.zahu.onConnected(bundle);
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.ConnectionCallbacks
    public final void onConnectionSuspended(int i2) {
        this.zaer.lock();
        try {
            this.zahu.onConnectionSuspended(i2);
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zar
    public final void zaa(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, boolean z2) {
        this.zaer.lock();
        try {
            this.zahu.zaa(connectionResult, api, z2);
        } finally {
            this.zaer.unlock();
        }
    }

    public final void zaax() {
        this.zaer.lock();
        try {
            this.zahu = new zaak(this, this.zafa, this.zaew, this.zaey, this.zacf, this.zaer, this.mContext);
            this.zahu.begin();
            this.zahr.signalAll();
        } finally {
            this.zaer.unlock();
        }
    }

    public final void zaay() {
        this.zaer.lock();
        try {
            this.zaeh.zaau();
            this.zahu = new zaaf(this);
            this.zahu.begin();
            this.zahr.signalAll();
        } finally {
            this.zaer.unlock();
        }
    }

    public final void zab(RuntimeException runtimeException) {
        this.zahs.sendMessage(this.zahs.obtainMessage(2, runtimeException));
    }

    public final void zaf(ConnectionResult connectionResult) {
        this.zaer.lock();
        try {
            this.zahv = connectionResult;
            this.zahu = new zaat(this);
            this.zahu.begin();
            this.zahr.signalAll();
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final void zau() {
        if (isConnected()) {
            ((zaaf) this.zahu).zaak();
        }
    }

    public final void zaa(zabd zabdVar) {
        this.zahs.sendMessage(this.zahs.obtainMessage(1, zabdVar));
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect(long j2, TimeUnit timeUnit) throws InterruptedException {
        connect();
        long nanos = timeUnit.toNanos(j2);
        while (isConnecting()) {
            if (nanos <= 0) {
                disconnect();
                return new ConnectionResult(14, null);
            }
            try {
                nanos = this.zahr.awaitNanos(nanos);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
            Thread.currentThread().interrupt();
            return new ConnectionResult(15, null);
        }
        if (isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zahv;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, null);
    }
}
