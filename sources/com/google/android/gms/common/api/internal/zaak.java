package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.signin.SignInOptions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import javax.annotation.concurrent.GuardedBy;

/* loaded from: classes3.dex */
public final class zaak implements zabb {
    private final Context mContext;
    private final Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> zacf;
    private final Lock zaer;
    private final Map<Api<?>, Boolean> zaew;
    private final GoogleApiAvailabilityLight zaey;
    private final ClientSettings zafa;
    private ConnectionResult zafi;
    private final zabe zafv;
    private int zaga;
    private int zagc;
    private com.google.android.gms.signin.zac zagf;
    private boolean zagg;
    private boolean zagh;
    private boolean zagi;
    private IAccountAccessor zagj;
    private boolean zagk;
    private boolean zagl;
    private int zagb = 0;
    private final Bundle zagd = new Bundle();
    private final Set<Api.AnyClientKey> zage = new HashSet();
    private ArrayList<Future<?>> zagm = new ArrayList<>();

    public zaak(zabe zabeVar, ClientSettings clientSettings, Map<Api<?>, Boolean> map, GoogleApiAvailabilityLight googleApiAvailabilityLight, Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> abstractClientBuilder, Lock lock, Context context) {
        this.zafv = zabeVar;
        this.zafa = clientSettings;
        this.zaew = map;
        this.zaey = googleApiAvailabilityLight;
        this.zacf = abstractClientBuilder;
        this.zaer = lock;
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaa(com.google.android.gms.signin.internal.zak zakVar) {
        if (zac(0)) {
            ConnectionResult connectionResult = zakVar.getConnectionResult();
            if (!connectionResult.isSuccess()) {
                if (!zad(connectionResult)) {
                    zae(connectionResult);
                    return;
                } else {
                    zaap();
                    zaan();
                    return;
                }
            }
            ResolveAccountResponse resolveAccountResponseZacv = zakVar.zacv();
            ConnectionResult connectionResult2 = resolveAccountResponseZacv.getConnectionResult();
            if (connectionResult2.isSuccess()) {
                this.zagi = true;
                this.zagj = resolveAccountResponseZacv.getAccountAccessor();
                this.zagk = resolveAccountResponseZacv.getSaveDefaultAccount();
                this.zagl = resolveAccountResponseZacv.isFromCrossClientAuth();
                zaan();
                return;
            }
            String strValueOf = String.valueOf(connectionResult2);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 48);
            sb.append("Sign-in succeeded with resolve account failure: ");
            sb.append(strValueOf);
            Log.wtf("GACConnecting", sb.toString(), new Exception());
            zae(connectionResult2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean zaam() {
        int i2 = this.zagc - 1;
        this.zagc = i2;
        if (i2 > 0) {
            return false;
        }
        if (i2 < 0) {
            Log.w("GACConnecting", this.zafv.zaeh.zaaw());
            Log.wtf("GACConnecting", "GoogleApiClient received too many callbacks for the given step. Clients may be in an unexpected state; GoogleApiClient will now disconnect.", new Exception());
            zae(new ConnectionResult(8, null));
            return false;
        }
        ConnectionResult connectionResult = this.zafi;
        if (connectionResult == null) {
            return true;
        }
        this.zafv.zahw = this.zaga;
        zae(connectionResult);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaan() {
        if (this.zagc != 0) {
            return;
        }
        if (!this.zagh || this.zagi) {
            ArrayList arrayList = new ArrayList();
            this.zagb = 1;
            this.zagc = this.zafv.zahd.size();
            for (Api.AnyClientKey<?> anyClientKey : this.zafv.zahd.keySet()) {
                if (!this.zafv.zaht.containsKey(anyClientKey)) {
                    arrayList.add(this.zafv.zahd.get(anyClientKey));
                } else if (zaam()) {
                    zaao();
                }
            }
            if (arrayList.isEmpty()) {
                return;
            }
            this.zagm.add(zabf.zaaz().submit(new zaaq(this, arrayList)));
        }
    }

    @GuardedBy("mLock")
    private final void zaao() {
        this.zafv.zaay();
        zabf.zaaz().execute(new zaaj(this));
        com.google.android.gms.signin.zac zacVar = this.zagf;
        if (zacVar != null) {
            if (this.zagk) {
                zacVar.zaa(this.zagj, this.zagl);
            }
            zab(false);
        }
        Iterator<Api.AnyClientKey<?>> it = this.zafv.zaht.keySet().iterator();
        while (it.hasNext()) {
            this.zafv.zahd.get(it.next()).disconnect();
        }
        this.zafv.zahx.zab(this.zagd.isEmpty() ? null : this.zagd);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zaap() {
        this.zagh = false;
        this.zafv.zaeh.zahe = Collections.emptySet();
        for (Api.AnyClientKey<?> anyClientKey : this.zage) {
            if (!this.zafv.zaht.containsKey(anyClientKey)) {
                this.zafv.zaht.put(anyClientKey, new ConnectionResult(17, null));
            }
        }
    }

    private final void zaaq() {
        ArrayList<Future<?>> arrayList = this.zagm;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Future<?> future = arrayList.get(i2);
            i2++;
            future.cancel(true);
        }
        this.zagm.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Set<Scope> zaar() {
        if (this.zafa == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet(this.zafa.getRequiredScopes());
        Map<Api<?>, ClientSettings.OptionalApiSettings> optionalApiSettings = this.zafa.getOptionalApiSettings();
        for (Api<?> api : optionalApiSettings.keySet()) {
            if (!this.zafv.zaht.containsKey(api.getClientKey())) {
                hashSet.addAll(optionalApiSettings.get(api).mScopes);
            }
        }
        return hashSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0024  */
    @javax.annotation.concurrent.GuardedBy("mLock")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void zab(com.google.android.gms.common.ConnectionResult r5, com.google.android.gms.common.api.Api<?> r6, boolean r7) {
        /*
            r4 = this;
            com.google.android.gms.common.api.Api$BaseClientBuilder r0 = r6.zah()
            int r0 = r0.getPriority()
            r1 = 0
            r2 = 1
            if (r7 == 0) goto L24
            boolean r7 = r5.hasResolution()
            if (r7 == 0) goto L14
        L12:
            r7 = r2
            goto L22
        L14:
            com.google.android.gms.common.GoogleApiAvailabilityLight r7 = r4.zaey
            int r3 = r5.getErrorCode()
            android.content.Intent r7 = r7.getErrorResolutionIntent(r3)
            if (r7 == 0) goto L21
            goto L12
        L21:
            r7 = r1
        L22:
            if (r7 == 0) goto L2d
        L24:
            com.google.android.gms.common.ConnectionResult r7 = r4.zafi
            if (r7 == 0) goto L2c
            int r7 = r4.zaga
            if (r0 >= r7) goto L2d
        L2c:
            r1 = r2
        L2d:
            if (r1 == 0) goto L33
            r4.zafi = r5
            r4.zaga = r0
        L33:
            com.google.android.gms.common.api.internal.zabe r7 = r4.zafv
            java.util.Map<com.google.android.gms.common.api.Api$AnyClientKey<?>, com.google.android.gms.common.ConnectionResult> r7 = r7.zaht
            com.google.android.gms.common.api.Api$AnyClientKey r6 = r6.getClientKey()
            r7.put(r6, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zaak.zab(com.google.android.gms.common.ConnectionResult, com.google.android.gms.common.api.Api, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean zac(int i2) {
        if (this.zagb == i2) {
            return true;
        }
        Log.w("GACConnecting", this.zafv.zaeh.zaaw());
        String strValueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(strValueOf.length() + 23);
        sb.append("Unexpected callback in ");
        sb.append(strValueOf);
        Log.w("GACConnecting", sb.toString());
        int i3 = this.zagc;
        StringBuilder sb2 = new StringBuilder(33);
        sb2.append("mRemainingConnections=");
        sb2.append(i3);
        Log.w("GACConnecting", sb2.toString());
        String strZad = zad(this.zagb);
        String strZad2 = zad(i2);
        StringBuilder sb3 = new StringBuilder(String.valueOf(strZad).length() + 70 + String.valueOf(strZad2).length());
        sb3.append("GoogleApiClient connecting is in step ");
        sb3.append(strZad);
        sb3.append(" but received callback for step ");
        sb3.append(strZad2);
        Log.e("GACConnecting", sb3.toString(), new Exception());
        zae(new ConnectionResult(8, null));
        return false;
    }

    private static String zad(int i2) {
        return i2 != 0 ? i2 != 1 ? "UNKNOWN" : "STEP_GETTING_REMOTE_SERVICE" : "STEP_SERVICE_BINDINGS_AND_SIGN_IN";
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean zad(ConnectionResult connectionResult) {
        return this.zagg && !connectionResult.hasResolution();
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zae(ConnectionResult connectionResult) {
        zaaq();
        zab(!connectionResult.hasResolution());
        this.zafv.zaf(connectionResult);
        this.zafv.zahx.zac(connectionResult);
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    @GuardedBy("mLock")
    public final void begin() {
        this.zafv.zaht.clear();
        this.zagh = false;
        zaaj zaajVar = null;
        this.zafi = null;
        this.zagb = 0;
        this.zagg = true;
        this.zagi = false;
        this.zagk = false;
        HashMap map = new HashMap();
        boolean z2 = false;
        for (Api<?> api : this.zaew.keySet()) {
            Api.Client client = this.zafv.zahd.get(api.getClientKey());
            z2 |= api.zah().getPriority() == 1;
            boolean zBooleanValue = this.zaew.get(api).booleanValue();
            if (client.requiresSignIn()) {
                this.zagh = true;
                if (zBooleanValue) {
                    this.zage.add(api.getClientKey());
                } else {
                    this.zagg = false;
                }
            }
            map.put(client, new zaam(this, api, zBooleanValue));
        }
        if (z2) {
            this.zagh = false;
        }
        if (this.zagh) {
            this.zafa.setClientSessionId(Integer.valueOf(System.identityHashCode(this.zafv.zaeh)));
            zaar zaarVar = new zaar(this, zaajVar);
            Api.AbstractClientBuilder<? extends com.google.android.gms.signin.zac, SignInOptions> abstractClientBuilder = this.zacf;
            Context context = this.mContext;
            Looper looper = this.zafv.zaeh.getLooper();
            ClientSettings clientSettings = this.zafa;
            this.zagf = (com.google.android.gms.signin.zac) abstractClientBuilder.buildClient(context, looper, clientSettings, (ClientSettings) clientSettings.getSignInOptions(), (GoogleApiClient.ConnectionCallbacks) zaarVar, (GoogleApiClient.OnConnectionFailedListener) zaarVar);
        }
        this.zagc = this.zafv.zahd.size();
        this.zagm.add(zabf.zaaz().submit(new zaal(this, map)));
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final void connect() {
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    @GuardedBy("mLock")
    public final boolean disconnect() {
        zaaq();
        zab(true);
        this.zafv.zaf(null);
        return true;
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final <A extends Api.AnyClient, R extends Result, T extends BaseImplementation.ApiMethodImpl<R, A>> T enqueue(T t2) {
        this.zafv.zaeh.zafd.add(t2);
        return t2;
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    public final <A extends Api.AnyClient, T extends BaseImplementation.ApiMethodImpl<? extends Result, A>> T execute(T t2) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    @GuardedBy("mLock")
    public final void onConnected(Bundle bundle) {
        if (zac(1)) {
            if (bundle != null) {
                this.zagd.putAll(bundle);
            }
            if (zaam()) {
                zaao();
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    @GuardedBy("mLock")
    public final void onConnectionSuspended(int i2) {
        zae(new ConnectionResult(8, null));
    }

    @GuardedBy("mLock")
    private final void zab(boolean z2) {
        com.google.android.gms.signin.zac zacVar = this.zagf;
        if (zacVar != null) {
            if (zacVar.isConnected() && z2) {
                this.zagf.zacu();
            }
            this.zagf.disconnect();
            if (this.zafa.isSignInClientDisconnectFixEnabled()) {
                this.zagf = null;
            }
            this.zagj = null;
        }
    }

    @Override // com.google.android.gms.common.api.internal.zabb
    @GuardedBy("mLock")
    public final void zaa(ConnectionResult connectionResult, Api<?> api, boolean z2) {
        if (zac(1)) {
            zab(connectionResult, api, z2);
            if (zaam()) {
                zaao();
            }
        }
    }
}
