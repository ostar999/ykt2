package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.util.concurrent.HandlerExecutor;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes3.dex */
public final class zav implements zabr {
    private final Looper zabl;
    private final GoogleApiManager zabo;
    private final Lock zaer;
    private final Map<Api<?>, Boolean> zaew;
    private final zaaw zaex;
    private final GoogleApiAvailabilityLight zaey;
    private final Condition zaez;
    private final ClientSettings zafa;
    private final boolean zafb;
    private final boolean zafc;

    @GuardedBy("mLock")
    private boolean zafe;

    @GuardedBy("mLock")
    private Map<ApiKey<?>, ConnectionResult> zaff;

    @GuardedBy("mLock")
    private Map<ApiKey<?>, ConnectionResult> zafg;

    @GuardedBy("mLock")
    private zaaa zafh;

    @GuardedBy("mLock")
    private ConnectionResult zafi;
    private final Map<Api.AnyClientKey<?>, zaw<?>> zaeu = new HashMap();
    private final Map<Api.AnyClientKey<?>, zaw<?>> zaev = new HashMap();
    private final Queue<BaseImplementation.ApiMethodImpl<?, ?>> zafd = new LinkedList();

    public zav(Context context, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> abstractClientBuilder, ArrayList<zap> arrayList, zaaw zaawVar, boolean z2) {
        boolean z3;
        boolean z4;
        boolean z5;
        this.zaer = lock;
        this.zabl = looper;
        this.zaez = lock.newCondition();
        this.zaey = googleApiAvailabilityLight;
        this.zaex = zaawVar;
        this.zaew = map2;
        this.zafa = clientSettings;
        this.zafb = z2;
        HashMap map3 = new HashMap();
        for (Api<?> api : map2.keySet()) {
            map3.put(api.getClientKey(), api);
        }
        HashMap map4 = new HashMap();
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            zap zapVar = arrayList.get(i2);
            i2++;
            zap zapVar2 = zapVar;
            map4.put(zapVar2.mApi, zapVar2);
        }
        boolean z6 = true;
        boolean z7 = false;
        boolean z8 = false;
        for (Map.Entry<Api.AnyClientKey<?>, Api.Client> entry : map.entrySet()) {
            Api api2 = (Api) map3.get(entry.getKey());
            Api.Client value = entry.getValue();
            if (value.requiresGooglePlayServices()) {
                z5 = z6;
                if (this.zaew.get(api2).booleanValue()) {
                    z4 = z8;
                    z3 = true;
                } else {
                    z3 = true;
                    z4 = true;
                }
            } else {
                z3 = z7;
                z4 = z8;
                z5 = false;
            }
            zaw<?> zawVar = new zaw<>(context, api2, looper, value, (zap) map4.get(api2), clientSettings, abstractClientBuilder);
            this.zaeu.put(entry.getKey(), zawVar);
            if (value.requiresSignIn()) {
                this.zaev.put(entry.getKey(), zawVar);
            }
            z7 = z3;
            z6 = z5;
            z8 = z4;
        }
        this.zafc = (!z7 || z6 || z8) ? false : true;
        this.zabo = GoogleApiManager.zaba();
    }

    @Nullable
    private final ConnectionResult zaa(@NonNull Api.AnyClientKey<?> anyClientKey) {
        this.zaer.lock();
        try {
            zaw<?> zawVar = this.zaeu.get(anyClientKey);
            Map<ApiKey<?>, ConnectionResult> map = this.zaff;
            if (map != null && zawVar != null) {
                return map.get(zawVar.getApiKey());
            }
            this.zaer.unlock();
            return null;
        } finally {
            this.zaer.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaaa() {
        if (this.zafa == null) {
            this.zaex.zahe = Collections.emptySet();
            return;
        }
        HashSet hashSet = new HashSet(this.zafa.getRequiredScopes());
        Map<Api<?>, ClientSettings.OptionalApiSettings> optionalApiSettings = this.zafa.getOptionalApiSettings();
        for (Api<?> api : optionalApiSettings.keySet()) {
            ConnectionResult connectionResult = getConnectionResult(api);
            if (connectionResult != null && connectionResult.isSuccess()) {
                hashSet.addAll(optionalApiSettings.get(api).mScopes);
            }
        }
        this.zaex.zahe = hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaab() {
        while (!this.zafd.isEmpty()) {
            execute(this.zafd.remove());
        }
        this.zaex.zab((Bundle) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Nullable
    @GuardedBy("mLock")
    public final ConnectionResult zaac() {
        ConnectionResult connectionResult = null;
        int i2 = 0;
        int i3 = 0;
        ConnectionResult connectionResult2 = null;
        for (zaw<?> zawVar : this.zaeu.values()) {
            Api<?> api = zawVar.getApi();
            ConnectionResult connectionResult3 = this.zaff.get(zawVar.getApiKey());
            if (!connectionResult3.isSuccess() && (!this.zaew.get(api).booleanValue() || connectionResult3.hasResolution() || this.zaey.isUserResolvableError(connectionResult3.getErrorCode()))) {
                if (connectionResult3.getErrorCode() == 4 && this.zafb) {
                    int priority = api.zah().getPriority();
                    if (connectionResult2 == null || i3 > priority) {
                        connectionResult2 = connectionResult3;
                        i3 = priority;
                    }
                } else {
                    int priority2 = api.zah().getPriority();
                    if (connectionResult == null || i2 > priority2) {
                        connectionResult = connectionResult3;
                        i2 = priority2;
                    }
                }
            }
        }
        return (connectionResult == null || connectionResult2 == null || i2 <= i3) ? connectionResult : connectionResult2;
    }

    private final <T extends BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient>> boolean zab(@NonNull T t2) {
        Api.AnyClientKey<?> clientKey = t2.getClientKey();
        ConnectionResult connectionResultZaa = zaa(clientKey);
        if (connectionResultZaa == null || connectionResultZaa.getErrorCode() != 4) {
            return false;
        }
        t2.setFailedResult(new Status(4, null, this.zabo.zaa(this.zaeu.get(clientKey).getApiKey(), System.identityHashCode(this.zaex))));
        return true;
    }

    private final boolean zaz() {
        this.zaer.lock();
        try {
            if (this.zafe && this.zafb) {
                Iterator<Api.AnyClientKey<?>> it = this.zaev.keySet().iterator();
                while (it.hasNext()) {
                    ConnectionResult connectionResultZaa = zaa(it.next());
                    if (connectionResultZaa == null || !connectionResultZaa.isSuccess()) {
                        return false;
                    }
                }
                this.zaer.unlock();
                return true;
            }
            return false;
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect() throws InterruptedException {
        connect();
        while (isConnecting()) {
            try {
                this.zaez.await();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (isConnected()) {
            return ConnectionResult.RESULT_SUCCESS;
        }
        ConnectionResult connectionResult = this.zafi;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, null);
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void connect() {
        this.zaer.lock();
        try {
            if (this.zafe) {
                return;
            }
            this.zafe = true;
            this.zaff = null;
            this.zafg = null;
            this.zafh = null;
            this.zafi = null;
            this.zabo.zam();
            this.zabo.zaa(this.zaeu.values()).addOnCompleteListener(new HandlerExecutor(this.zabl), new zax(this));
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void disconnect() {
        this.zaer.lock();
        try {
            this.zafe = false;
            this.zaff = null;
            this.zafg = null;
            zaaa zaaaVar = this.zafh;
            if (zaaaVar != null) {
                zaaaVar.cancel();
                this.zafh = null;
            }
            this.zafi = null;
            while (!this.zafd.isEmpty()) {
                BaseImplementation.ApiMethodImpl<?, ?> apiMethodImplRemove = this.zafd.remove();
                apiMethodImplRemove.zaa((zacq) null);
                apiMethodImplRemove.cancel();
            }
            this.zaez.signalAll();
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t2) {
        if (this.zafb && zab((zav) t2)) {
            return t2;
        }
        if (isConnected()) {
            this.zaex.zahj.zac(t2);
            return (T) this.zaeu.get(t2.getClientKey()).doRead((zaw<?>) t2);
        }
        this.zafd.add(t2);
        return t2;
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t2) {
        Api.AnyClientKey<A> clientKey = t2.getClientKey();
        if (this.zafb && zab((zav) t2)) {
            return t2;
        }
        this.zaex.zahj.zac(t2);
        return (T) this.zaeu.get(clientKey).doWrite((zaw<?>) t2);
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @Nullable
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return zaa(api.getClientKey());
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x000f  */
    @Override // com.google.android.gms.common.api.internal.zabr
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isConnected() {
        /*
            r2 = this;
            java.util.concurrent.locks.Lock r0 = r2.zaer
            r0.lock()
            java.util.Map<com.google.android.gms.common.api.internal.ApiKey<?>, com.google.android.gms.common.ConnectionResult> r0 = r2.zaff     // Catch: java.lang.Throwable -> L16
            if (r0 == 0) goto Lf
            com.google.android.gms.common.ConnectionResult r0 = r2.zafi     // Catch: java.lang.Throwable -> L16
            if (r0 != 0) goto Lf
            r0 = 1
            goto L10
        Lf:
            r0 = 0
        L10:
            java.util.concurrent.locks.Lock r1 = r2.zaer
            r1.unlock()
            return r0
        L16:
            r0 = move-exception
            java.util.concurrent.locks.Lock r1 = r2.zaer
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zav.isConnected():boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x000f  */
    @Override // com.google.android.gms.common.api.internal.zabr
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isConnecting() {
        /*
            r2 = this;
            java.util.concurrent.locks.Lock r0 = r2.zaer
            r0.lock()
            java.util.Map<com.google.android.gms.common.api.internal.ApiKey<?>, com.google.android.gms.common.ConnectionResult> r0 = r2.zaff     // Catch: java.lang.Throwable -> L16
            if (r0 != 0) goto Lf
            boolean r0 = r2.zafe     // Catch: java.lang.Throwable -> L16
            if (r0 == 0) goto Lf
            r0 = 1
            goto L10
        Lf:
            r0 = 0
        L10:
            java.util.concurrent.locks.Lock r1 = r2.zaer
            r1.unlock()
            return r0
        L16:
            r0 = move-exception
            java.util.concurrent.locks.Lock r1 = r2.zaer
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zav.isConnecting():boolean");
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        this.zaer.lock();
        try {
            if (!this.zafe || zaz()) {
                this.zaer.unlock();
                return false;
            }
            this.zabo.zam();
            this.zafh = new zaaa(this, signInConnectionListener);
            this.zabo.zaa(this.zaev.values()).addOnCompleteListener(new HandlerExecutor(this.zabl), this.zafh);
            this.zaer.unlock();
            return true;
        } catch (Throwable th) {
            this.zaer.unlock();
            throw th;
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void maybeSignOut() {
        this.zaer.lock();
        try {
            this.zabo.maybeSignOut();
            zaaa zaaaVar = this.zafh;
            if (zaaaVar != null) {
                zaaaVar.cancel();
                this.zafh = null;
            }
            if (this.zafg == null) {
                this.zafg = new ArrayMap(this.zaev.size());
            }
            ConnectionResult connectionResult = new ConnectionResult(4);
            Iterator<zaw<?>> it = this.zaev.values().iterator();
            while (it.hasNext()) {
                this.zafg.put(it.next().getApiKey(), connectionResult);
            }
            Map<ApiKey<?>, ConnectionResult> map = this.zaff;
            if (map != null) {
                map.putAll(this.zafg);
            }
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void zau() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zaa(zaw<?> zawVar, ConnectionResult connectionResult) {
        return !connectionResult.isSuccess() && !connectionResult.hasResolution() && this.zaew.get(zawVar.getApi()).booleanValue() && zawVar.zaad().requiresGooglePlayServices() && this.zaey.isUserResolvableError(connectionResult.getErrorCode());
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
                nanos = this.zaez.awaitNanos(nanos);
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
        ConnectionResult connectionResult = this.zafi;
        return connectionResult != null ? connectionResult : new ConnectionResult(13, null);
    }

    public static /* synthetic */ boolean zaa(zav zavVar, boolean z2) {
        zavVar.zafe = false;
        return false;
    }
}
