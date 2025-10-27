package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClientEventManager;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.common.util.ClientLibraryUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.signin.SignInOptions;
import com.heytap.mcssdk.constant.a;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes3.dex */
public final class zaaw extends GoogleApiClient implements zabs {
    private final Context mContext;
    private final Looper zabl;
    private final int zacc;
    private final GoogleApiAvailability zace;
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> zacf;
    private boolean zaci;
    private final Lock zaer;
    private final Map<Api<?>, Boolean> zaew;
    private final ClientSettings zafa;
    private final GmsClientEventManager zagw;
    private volatile boolean zagy;
    private long zagz;
    private long zaha;
    private final zaaz zahb;

    @VisibleForTesting
    private zabq zahc;
    final Map<Api.AnyClientKey<?>, Api.Client> zahd;
    Set<Scope> zahe;
    private final ListenerHolders zahf;
    private final ArrayList<zap> zahg;
    private Integer zahh;
    Set<zack> zahi;
    final zacp zahj;
    private final GmsClientEventManager.GmsClientEventState zahk;
    private zabr zagx = null;

    @VisibleForTesting
    final Queue<BaseImplementation.ApiMethodImpl<?, ?>> zafd = new LinkedList();

    public zaaw(Context context, Lock lock, Looper looper, ClientSettings clientSettings, GoogleApiAvailability googleApiAvailability, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> abstractClientBuilder, Map<Api<?>, Boolean> map, List<GoogleApiClient.ConnectionCallbacks> list, List<GoogleApiClient.OnConnectionFailedListener> list2, Map<Api.AnyClientKey<?>, Api.Client> map2, int i2, int i3, ArrayList<zap> arrayList, boolean z2) {
        this.zagz = ClientLibraryUtils.isPackageSide() ? a.f7153q : 120000L;
        this.zaha = 5000L;
        this.zahe = new HashSet();
        this.zahf = new ListenerHolders();
        this.zahh = null;
        this.zahi = null;
        zaav zaavVar = new zaav(this);
        this.zahk = zaavVar;
        this.mContext = context;
        this.zaer = lock;
        this.zaci = false;
        this.zagw = new GmsClientEventManager(looper, zaavVar);
        this.zabl = looper;
        this.zahb = new zaaz(this, looper);
        this.zace = googleApiAvailability;
        this.zacc = i2;
        if (i2 >= 0) {
            this.zahh = Integer.valueOf(i3);
        }
        this.zaew = map;
        this.zahd = map2;
        this.zahg = arrayList;
        this.zahj = new zacp(map2);
        Iterator<GoogleApiClient.ConnectionCallbacks> it = list.iterator();
        while (it.hasNext()) {
            this.zagw.registerConnectionCallbacks(it.next());
        }
        Iterator<GoogleApiClient.OnConnectionFailedListener> it2 = list2.iterator();
        while (it2.hasNext()) {
            this.zagw.registerConnectionFailedListener(it2.next());
        }
        this.zafa = clientSettings;
        this.zacf = abstractClientBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void resume() {
        this.zaer.lock();
        try {
            if (this.zagy) {
                zaas();
            }
        } finally {
            this.zaer.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zaa(GoogleApiClient googleApiClient, StatusPendingResult statusPendingResult, boolean z2) {
        Common.zapw.zaa(googleApiClient).setResultCallback(new zaba(this, statusPendingResult, z2, googleApiClient));
    }

    @GuardedBy("mLock")
    private final void zaas() {
        this.zagw.enableCallbacks();
        this.zagx.connect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zaat() {
        this.zaer.lock();
        try {
            if (zaau()) {
                zaas();
            }
        } finally {
            this.zaer.unlock();
        }
    }

    private final void zae(int i2) {
        Integer num = this.zahh;
        if (num == null) {
            this.zahh = Integer.valueOf(i2);
        } else if (num.intValue() != i2) {
            String strZaf = zaf(i2);
            String strZaf2 = zaf(this.zahh.intValue());
            StringBuilder sb = new StringBuilder(String.valueOf(strZaf).length() + 51 + String.valueOf(strZaf2).length());
            sb.append("Cannot use sign-in mode: ");
            sb.append(strZaf);
            sb.append(". Mode was already set to ");
            sb.append(strZaf2);
            throw new IllegalStateException(sb.toString());
        }
        if (this.zagx != null) {
            return;
        }
        boolean z2 = false;
        boolean z3 = false;
        for (Api.Client client : this.zahd.values()) {
            if (client.requiresSignIn()) {
                z2 = true;
            }
            if (client.providesSignIn()) {
                z3 = true;
            }
        }
        int iIntValue = this.zahh.intValue();
        if (iIntValue == 1) {
            if (!z2) {
                throw new IllegalStateException("SIGN_IN_MODE_REQUIRED cannot be used on a GoogleApiClient that does not contain any authenticated APIs. Use connect() instead.");
            }
            if (z3) {
                throw new IllegalStateException("Cannot use SIGN_IN_MODE_REQUIRED with GOOGLE_SIGN_IN_API. Use connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
        } else if (iIntValue == 2 && z2) {
            if (this.zaci) {
                this.zagx = new zav(this.mContext, this.zaer, this.zabl, this.zace, this.zahd, this.zafa, this.zaew, this.zacf, this.zahg, this, true);
                return;
            } else {
                this.zagx = zaq.zaa(this.mContext, this, this.zaer, this.zabl, this.zace, this.zahd, this.zafa, this.zaew, this.zacf, this.zahg);
                return;
            }
        }
        if (!this.zaci || z3) {
            this.zagx = new zabe(this.mContext, this, this.zaer, this.zabl, this.zace, this.zahd, this.zafa, this.zaew, this.zacf, this.zahg, this);
        } else {
            this.zagx = new zav(this.mContext, this.zaer, this.zabl, this.zace, this.zahd, this.zafa, this.zaew, this.zacf, this.zahg, this, false);
        }
    }

    private static String zaf(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? "UNKNOWN" : "SIGN_IN_MODE_NONE" : "SIGN_IN_MODE_OPTIONAL" : "SIGN_IN_MODE_REQUIRED";
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final ConnectionResult blockingConnect() {
        boolean z2 = true;
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        this.zaer.lock();
        try {
            if (this.zacc >= 0) {
                if (this.zahh == null) {
                    z2 = false;
                }
                Preconditions.checkState(z2, "Sign-in mode should have been set explicitly by auto-manage.");
            } else {
                Integer num = this.zahh;
                if (num == null) {
                    this.zahh = Integer.valueOf(zaa(this.zahd.values(), false));
                } else if (num.intValue() == 2) {
                    throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
                }
            }
            zae(this.zahh.intValue());
            this.zagw.enableCallbacks();
            return this.zagx.blockingConnect();
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final PendingResult<Status> clearDefaultAccountAndReconnect() {
        Preconditions.checkState(isConnected(), "GoogleApiClient is not connected yet.");
        Preconditions.checkState(this.zahh.intValue() != 2, "Cannot use clearDefaultAccountAndReconnect with GOOGLE_SIGN_IN_API");
        StatusPendingResult statusPendingResult = new StatusPendingResult(this);
        if (this.zahd.containsKey(Common.CLIENT_KEY)) {
            zaa(this, statusPendingResult, false);
        } else {
            AtomicReference atomicReference = new AtomicReference();
            GoogleApiClient googleApiClientBuild = new GoogleApiClient.Builder(this.mContext).addApi(Common.API).addConnectionCallbacks(new zaay(this, atomicReference, statusPendingResult)).addOnConnectionFailedListener(new zaax(this, statusPendingResult)).setHandler(this.zahb).build();
            atomicReference.set(googleApiClientBuild);
            googleApiClientBuild.connect();
        }
        return statusPendingResult;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void connect() {
        this.zaer.lock();
        try {
            if (this.zacc >= 0) {
                Preconditions.checkState(this.zahh != null, "Sign-in mode should have been set explicitly by auto-manage.");
            } else {
                Integer num = this.zahh;
                if (num == null) {
                    this.zahh = Integer.valueOf(zaa(this.zahd.values(), false));
                } else if (num.intValue() == 2) {
                    throw new IllegalStateException("Cannot call connect() when SignInMode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
                }
            }
            connect(this.zahh.intValue());
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void disconnect() {
        this.zaer.lock();
        try {
            this.zahj.release();
            zabr zabrVar = this.zagx;
            if (zabrVar != null) {
                zabrVar.disconnect();
            }
            this.zahf.release();
            for (BaseImplementation.ApiMethodImpl<?, ?> apiMethodImpl : this.zafd) {
                apiMethodImpl.zaa((zacq) null);
                apiMethodImpl.cancel();
            }
            this.zafd.clear();
            if (this.zagx == null) {
                return;
            }
            zaau();
            this.zagw.disableCallbacks();
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.append((CharSequence) str).append("mContext=").println(this.mContext);
        printWriter.append((CharSequence) str).append("mResuming=").print(this.zagy);
        printWriter.append(" mWorkQueue.size()=").print(this.zafd.size());
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.zahj.zald.size());
        zabr zabrVar = this.zagx;
        if (zabrVar != null) {
            zabrVar.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(@NonNull T t2) {
        Preconditions.checkArgument(t2.getClientKey() != null, "This task can not be enqueued (it's probably a Batch or malformed)");
        boolean zContainsKey = this.zahd.containsKey(t2.getClientKey());
        String name = t2.getApi() != null ? t2.getApi().getName() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(name);
        sb.append(" required for this call.");
        Preconditions.checkArgument(zContainsKey, sb.toString());
        this.zaer.lock();
        try {
            zabr zabrVar = this.zagx;
            if (zabrVar != null) {
                return (T) zabrVar.enqueue(t2);
            }
            this.zafd.add(t2);
            return t2;
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(@NonNull T t2) {
        Preconditions.checkArgument(t2.getClientKey() != null, "This task can not be executed (it's probably a Batch or malformed)");
        boolean zContainsKey = this.zahd.containsKey(t2.getClientKey());
        String name = t2.getApi() != null ? t2.getApi().getName() : "the API";
        StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 65);
        sb.append("GoogleApiClient is not configured to use ");
        sb.append(name);
        sb.append(" required for this call.");
        Preconditions.checkArgument(zContainsKey, sb.toString());
        this.zaer.lock();
        try {
            if (this.zagx == null) {
                throw new IllegalStateException("GoogleApiClient is not connected yet.");
            }
            if (!this.zagy) {
                return (T) this.zagx.execute(t2);
            }
            this.zafd.add(t2);
            while (!this.zafd.isEmpty()) {
                BaseImplementation.ApiMethodImpl<?, ?> apiMethodImplRemove = this.zafd.remove();
                this.zahj.zac(apiMethodImplRemove);
                apiMethodImplRemove.setFailedResult(Status.RESULT_INTERNAL_ERROR);
            }
            return t2;
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    @NonNull
    public final <C extends Api.Client> C getClient(@NonNull Api.AnyClientKey<C> anyClientKey) {
        C c3 = (C) this.zahd.get(anyClientKey);
        Preconditions.checkNotNull(c3, "Appropriate Api was not requested.");
        return c3;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    @NonNull
    public final ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        this.zaer.lock();
        try {
            if (!isConnected() && !this.zagy) {
                throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            }
            if (!this.zahd.containsKey(api.getClientKey())) {
                throw new IllegalArgumentException(String.valueOf(api.getName()).concat(" was never registered with GoogleApiClient"));
            }
            ConnectionResult connectionResult = this.zagx.getConnectionResult(api);
            if (connectionResult != null) {
                return connectionResult;
            }
            if (this.zagy) {
                return ConnectionResult.RESULT_SUCCESS;
            }
            Log.w("GoogleApiClientImpl", zaaw());
            Log.wtf("GoogleApiClientImpl", String.valueOf(api.getName()).concat(" requested in getConnectionResult is not connected but is not present in the failed  connections map"), new Exception());
            return new ConnectionResult(8, null);
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final Looper getLooper() {
        return this.zabl;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean hasApi(@NonNull Api<?> api) {
        return this.zahd.containsKey(api.getClientKey());
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean hasConnectedApi(@NonNull Api<?> api) {
        Api.Client client;
        return isConnected() && (client = this.zahd.get(api.getClientKey())) != null && client.isConnected();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnected() {
        zabr zabrVar = this.zagx;
        return zabrVar != null && zabrVar.isConnected();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnecting() {
        zabr zabrVar = this.zagx;
        return zabrVar != null && zabrVar.isConnecting();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zagw.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zagw.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final boolean maybeSignIn(SignInConnectionListener signInConnectionListener) {
        zabr zabrVar = this.zagx;
        return zabrVar != null && zabrVar.maybeSignIn(signInConnectionListener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void maybeSignOut() {
        zabr zabrVar = this.zagx;
        if (zabrVar != null) {
            zabrVar.maybeSignOut();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void reconnect() {
        disconnect();
        connect();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zagw.registerConnectionCallbacks(connectionCallbacks);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zagw.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final <L> ListenerHolder<L> registerListener(@NonNull L l2) {
        this.zaer.lock();
        try {
            return this.zahf.zaa(l2, this.zabl, "NO_TYPE");
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        LifecycleActivity lifecycleActivity = new LifecycleActivity((Activity) fragmentActivity);
        if (this.zacc < 0) {
            throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
        }
        zai.zaa(lifecycleActivity).zaa(this.zacc);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zagw.unregisterConnectionCallbacks(connectionCallbacks);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zagw.unregisterConnectionFailedListener(onConnectionFailedListener);
    }

    @GuardedBy("mLock")
    public final boolean zaau() {
        if (!this.zagy) {
            return false;
        }
        this.zagy = false;
        this.zahb.removeMessages(2);
        this.zahb.removeMessages(1);
        zabq zabqVar = this.zahc;
        if (zabqVar != null) {
            zabqVar.unregister();
            this.zahc = null;
        }
        return true;
    }

    public final boolean zaav() {
        this.zaer.lock();
        try {
            if (this.zahi != null) {
                return !r0.isEmpty();
            }
            this.zaer.unlock();
            return false;
        } finally {
            this.zaer.unlock();
        }
    }

    public final String zaaw() {
        StringWriter stringWriter = new StringWriter();
        dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final void zab(Bundle bundle) {
        while (!this.zafd.isEmpty()) {
            execute(this.zafd.remove());
        }
        this.zagw.onConnectionSuccess(bundle);
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final void zac(ConnectionResult connectionResult) {
        if (!this.zace.isPlayServicesPossiblyUpdating(this.mContext, connectionResult.getErrorCode())) {
            zaau();
        }
        if (this.zagy) {
            return;
        }
        this.zagw.onConnectionFailure(connectionResult);
        this.zagw.disableCallbacks();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void zaa(zack zackVar) {
        this.zaer.lock();
        try {
            if (this.zahi == null) {
                this.zahi = new HashSet();
            }
            this.zahi.add(zackVar);
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabs
    @GuardedBy("mLock")
    public final void zab(int i2, boolean z2) {
        if (i2 == 1 && !z2 && !this.zagy) {
            this.zagy = true;
            if (this.zahc == null && !ClientLibraryUtils.isPackageSide()) {
                try {
                    this.zahc = this.zace.zaa(this.mContext.getApplicationContext(), new zabc(this));
                } catch (SecurityException unused) {
                }
            }
            zaaz zaazVar = this.zahb;
            zaazVar.sendMessageDelayed(zaazVar.obtainMessage(1), this.zagz);
            zaaz zaazVar2 = this.zahb;
            zaazVar2.sendMessageDelayed(zaazVar2.obtainMessage(2), this.zaha);
        }
        this.zahj.zabv();
        this.zagw.onUnintentionalDisconnection(i2);
        this.zagw.disableCallbacks();
        if (i2 == 2) {
            zaas();
        }
    }

    public static int zaa(Iterable<Api.Client> iterable, boolean z2) {
        boolean z3 = false;
        boolean z4 = false;
        for (Api.Client client : iterable) {
            if (client.requiresSignIn()) {
                z3 = true;
            }
            if (client.providesSignIn()) {
                z4 = true;
            }
        }
        if (z3) {
            return (z4 && z2) ? 2 : 1;
        }
        return 3;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void connect(int i2) {
        this.zaer.lock();
        boolean z2 = true;
        if (i2 != 3 && i2 != 1 && i2 != 2) {
            z2 = false;
        }
        try {
            StringBuilder sb = new StringBuilder(33);
            sb.append("Illegal sign-in mode: ");
            sb.append(i2);
            Preconditions.checkArgument(z2, sb.toString());
            zae(i2);
            zaas();
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final ConnectionResult blockingConnect(long j2, @NonNull TimeUnit timeUnit) {
        Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        Preconditions.checkNotNull(timeUnit, "TimeUnit must not be null");
        this.zaer.lock();
        try {
            Integer num = this.zahh;
            if (num == null) {
                this.zahh = Integer.valueOf(zaa(this.zahd.values(), false));
            } else if (num.intValue() == 2) {
                throw new IllegalStateException("Cannot call blockingConnect() when sign-in mode is set to SIGN_IN_MODE_OPTIONAL. Call connect(SIGN_IN_MODE_OPTIONAL) instead.");
            }
            zae(this.zahh.intValue());
            this.zagw.enableCallbacks();
            return this.zagx.blockingConnect(j2, timeUnit);
        } finally {
            this.zaer.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public final void zab(zack zackVar) {
        this.zaer.lock();
        try {
            Set<zack> set = this.zahi;
            if (set == null) {
                Log.wtf("GoogleApiClientImpl", "Attempted to remove pending transform when no transforms are registered.", new Exception());
            } else if (!set.remove(zackVar)) {
                Log.wtf("GoogleApiClientImpl", "Failed to remove pending transform - this may lead to memory leaks!", new Exception());
            } else if (!zaav()) {
                this.zagx.zau();
            }
        } finally {
            this.zaer.unlock();
        }
    }
}
