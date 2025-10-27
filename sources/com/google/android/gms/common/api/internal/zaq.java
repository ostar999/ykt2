package com.google.android.gms.common.api.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
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
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.signin.SignInOptions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes3.dex */
final class zaq implements zabr {
    private final Context mContext;
    private final Looper zabl;
    private final zaaw zaeh;
    private final zabe zaei;
    private final zabe zaej;
    private final Map<Api.AnyClientKey<?>, zabe> zaek;
    private final Api.Client zaem;
    private Bundle zaen;
    private final Lock zaer;
    private final Set<SignInConnectionListener> zael = Collections.newSetFromMap(new WeakHashMap());
    private ConnectionResult zaeo = null;
    private ConnectionResult zaep = null;
    private boolean zaeq = false;

    @GuardedBy("mLock")
    private int zaes = 0;

    private zaq(Context context, zaaw zaawVar, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> map, Map<Api.AnyClientKey<?>, Api.Client> map2, ClientSettings clientSettings, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> abstractClientBuilder, Api.Client client, ArrayList<zap> arrayList, ArrayList<zap> arrayList2, Map<Api<?>, Boolean> map3, Map<Api<?>, Boolean> map4) {
        this.mContext = context;
        this.zaeh = zaawVar;
        this.zaer = lock;
        this.zabl = looper;
        this.zaem = client;
        this.zaei = new zabe(context, zaawVar, lock, looper, googleApiAvailabilityLight, map2, null, map4, null, arrayList2, new zas(this, null));
        this.zaej = new zabe(context, zaawVar, lock, looper, googleApiAvailabilityLight, map, clientSettings, map3, abstractClientBuilder, arrayList, new zau(this, null));
        ArrayMap arrayMap = new ArrayMap();
        Iterator<Api.AnyClientKey<?>> it = map2.keySet().iterator();
        while (it.hasNext()) {
            arrayMap.put(it.next(), this.zaei);
        }
        Iterator<Api.AnyClientKey<?>> it2 = map.keySet().iterator();
        while (it2.hasNext()) {
            arrayMap.put(it2.next(), this.zaej);
        }
        this.zaek = Collections.unmodifiableMap(arrayMap);
    }

    public static zaq zaa(Context context, zaaw zaawVar, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.AnyClientKey<?>, Api.Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> abstractClientBuilder, ArrayList<zap> arrayList) {
        ArrayMap arrayMap = new ArrayMap();
        ArrayMap arrayMap2 = new ArrayMap();
        Api.Client client = null;
        for (Map.Entry<Api.AnyClientKey<?>, Api.Client> entry : map.entrySet()) {
            Api.Client value = entry.getValue();
            if (value.providesSignIn()) {
                client = value;
            }
            if (value.requiresSignIn()) {
                arrayMap.put(entry.getKey(), value);
            } else {
                arrayMap2.put(entry.getKey(), value);
            }
        }
        Preconditions.checkState(!arrayMap.isEmpty(), "CompositeGoogleApiClient should not be used without any APIs that require sign-in.");
        ArrayMap arrayMap3 = new ArrayMap();
        ArrayMap arrayMap4 = new ArrayMap();
        for (Api<?> api : map2.keySet()) {
            Api.AnyClientKey<?> clientKey = api.getClientKey();
            if (arrayMap.containsKey(clientKey)) {
                arrayMap3.put(api, map2.get(api));
            } else {
                if (!arrayMap2.containsKey(clientKey)) {
                    throw new IllegalStateException("Each API in the isOptionalMap must have a corresponding client in the clients map.");
                }
                arrayMap4.put(api, map2.get(api));
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            zap zapVar = arrayList.get(i2);
            i2++;
            zap zapVar2 = zapVar;
            if (arrayMap3.containsKey(zapVar2.mApi)) {
                arrayList2.add(zapVar2);
            } else {
                if (!arrayMap4.containsKey(zapVar2.mApi)) {
                    throw new IllegalStateException("Each ClientCallbacks must have a corresponding API in the isOptionalMap");
                }
                arrayList3.add(zapVar2);
            }
        }
        return new zaq(context, zaawVar, lock, looper, googleApiAvailabilityLight, arrayMap, arrayMap2, clientSettings, abstractClientBuilder, client, arrayList2, arrayList3, arrayMap3, arrayMap4);
    }

    private static boolean zab(ConnectionResult connectionResult) {
        return connectionResult != null && connectionResult.isSuccess();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zav() {
        ConnectionResult connectionResult;
        if (!zab(this.zaeo)) {
            if (this.zaeo != null && zab(this.zaep)) {
                this.zaej.disconnect();
                zaa(this.zaeo);
                return;
            }
            ConnectionResult connectionResult2 = this.zaeo;
            if (connectionResult2 == null || (connectionResult = this.zaep) == null) {
                return;
            }
            if (this.zaej.zahw < this.zaei.zahw) {
                connectionResult2 = connectionResult;
            }
            zaa(connectionResult2);
            return;
        }
        if (zab(this.zaep) || zax()) {
            int i2 = this.zaes;
            if (i2 == 1) {
                zaw();
            } else if (i2 != 2) {
                Log.wtf("CompositeGAC", "Attempted to call success callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new AssertionError());
            } else {
                this.zaeh.zab(this.zaen);
                zaw();
            }
            this.zaes = 0;
            return;
        }
        ConnectionResult connectionResult3 = this.zaep;
        if (connectionResult3 != null) {
            if (this.zaes == 1) {
                zaw();
            } else {
                zaa(connectionResult3);
                this.zaei.disconnect();
            }
        }
    }

    @GuardedBy("mLock")
    private final void zaw() {
        Iterator<SignInConnectionListener> it = this.zael.iterator();
        while (it.hasNext()) {
            it.next().onComplete();
        }
        this.zael.clear();
    }

    @GuardedBy("mLock")
    private final boolean zax() {
        ConnectionResult connectionResult = this.zaep;
        return connectionResult != null && connectionResult.getErrorCode() == 4;
    }

    @Nullable
    private final PendingIntent zay() {
        if (this.zaem == null) {
            return null;
        }
        return PendingIntent.getActivity(this.mContext, System.identityHashCode(this.zaeh), this.zaem.getSignInIntent(), 134217728);
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final void connect() {
        this.zaes = 2;
        this.zaeq = false;
        this.zaep = null;
        this.zaeo = null;
        this.zaei.connect();
        this.zaej.connect();
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final void disconnect() {
        this.zaep = null;
        this.zaeo = null;
        this.zaes = 0;
        this.zaei.disconnect();
        this.zaej.disconnect();
        zaw();
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append((CharSequence) str).append("authClient").println(":");
        this.zaej.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
        printWriter.append((CharSequence) str).append("anonClient").println(":");
        this.zaei.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t2) {
        if (!zaa((BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient>) t2)) {
            return (T) this.zaei.enqueue(t2);
        }
        if (!zax()) {
            return (T) this.zaej.enqueue(t2);
        }
        t2.setFailedResult(new Status(4, null, zay()));
        return t2;
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t2) {
        if (!zaa((BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient>) t2)) {
            return (T) this.zaei.execute(t2);
        }
        if (!zax()) {
            return (T) this.zaej.execute(t2);
        }
        t2.setFailedResult(new Status(4, null, zay()));
        return t2;
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @Nullable
    @GuardedBy("mLock")
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        return this.zaek.get(api.getClientKey()).equals(this.zaej) ? zax() ? new ConnectionResult(4, zay()) : this.zaej.getConnectionResult(api) : this.zaei.getConnectionResult(api);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0021  */
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
            com.google.android.gms.common.api.internal.zabe r0 = r2.zaei     // Catch: java.lang.Throwable -> L28
            boolean r0 = r0.isConnected()     // Catch: java.lang.Throwable -> L28
            if (r0 == 0) goto L21
            com.google.android.gms.common.api.internal.zabe r0 = r2.zaej     // Catch: java.lang.Throwable -> L28
            boolean r0 = r0.isConnected()     // Catch: java.lang.Throwable -> L28
            r1 = 1
            if (r0 != 0) goto L22
            boolean r0 = r2.zax()     // Catch: java.lang.Throwable -> L28
            if (r0 != 0) goto L22
            int r0 = r2.zaes     // Catch: java.lang.Throwable -> L28
            if (r0 != r1) goto L21
            goto L22
        L21:
            r1 = 0
        L22:
            java.util.concurrent.locks.Lock r0 = r2.zaer
            r0.unlock()
            return r1
        L28:
            r0 = move-exception
            java.util.concurrent.locks.Lock r1 = r2.zaer
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zaq.isConnected():boolean");
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final boolean isConnecting() {
        this.zaer.lock();
        try {
            return this.zaes == 2;
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        this.zaer.lock();
        try {
            if ((!isConnecting() && !isConnected()) || this.zaej.isConnected()) {
                this.zaer.unlock();
                return false;
            }
            this.zael.add(signInConnectionListener);
            if (this.zaes == 0) {
                this.zaes = 1;
            }
            this.zaep = null;
            this.zaej.connect();
            return true;
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    public final void maybeSignOut() {
        this.zaer.lock();
        try {
            boolean zIsConnecting = isConnecting();
            this.zaej.disconnect();
            this.zaep = new ConnectionResult(4);
            if (zIsConnecting) {
                new com.google.android.gms.internal.base.zar(this.zabl).post(new zat(this));
            } else {
                zaw();
            }
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final void zau() {
        this.zaei.zau();
        this.zaej.zau();
    }

    @Override // com.google.android.gms.common.api.internal.zabr
    @GuardedBy("mLock")
    public final ConnectionResult blockingConnect(long j2, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @GuardedBy("mLock")
    private final void zaa(ConnectionResult connectionResult) {
        int i2 = this.zaes;
        if (i2 == 1) {
            zaw();
        } else if (i2 != 2) {
            Log.wtf("CompositeGAC", "Attempted to call failure callbacks in CONNECTION_MODE_NONE. Callbacks should be disabled via GmsClientSupervisor", new Exception());
        } else {
            this.zaeh.zac(connectionResult);
            zaw();
        }
        this.zaes = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaa(int i2, boolean z2) {
        this.zaeh.zab(i2, z2);
        this.zaep = null;
        this.zaeo = null;
    }

    private final boolean zaa(BaseImplementation.ApiMethodImpl<? extends Result, ? extends Api.AnyClient> apiMethodImpl) {
        Object clientKey = apiMethodImpl.getClientKey();
        Preconditions.checkArgument(this.zaek.containsKey(clientKey), "GoogleApiClient is not configured to use the API required for this call.");
        return this.zaek.get(clientKey).equals(this.zaej);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zaa(Bundle bundle) {
        Bundle bundle2 = this.zaen;
        if (bundle2 == null) {
            this.zaen = bundle;
        } else if (bundle != null) {
            bundle2.putAll(bundle);
        }
    }
}
