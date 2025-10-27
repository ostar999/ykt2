package com.google.android.gms.common.api.internal;

import android.app.Activity;
import androidx.collection.ArraySet;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
public class zaad extends zak {
    private GoogleApiManager zabo;
    private final ArraySet<ApiKey<?>> zafs;

    private zaad(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.zafs = new ArraySet<>();
        this.mLifecycleFragment.addCallback("ConnectionlessLifecycleHelper", this);
    }

    public static void zaa(Activity activity, GoogleApiManager googleApiManager, ApiKey<?> apiKey) {
        LifecycleFragment fragment = LifecycleCallback.getFragment(activity);
        zaad zaadVar = (zaad) fragment.getCallbackOrNull("ConnectionlessLifecycleHelper", zaad.class);
        if (zaadVar == null) {
            zaadVar = new zaad(fragment);
        }
        zaadVar.zabo = googleApiManager;
        Preconditions.checkNotNull(apiKey, "ApiKey cannot be null");
        zaadVar.zafs.add(apiKey);
        googleApiManager.zaa(zaadVar);
    }

    private final void zaai() {
        if (this.zafs.isEmpty()) {
            return;
        }
        this.zabo.zaa(this);
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void onResume() {
        super.onResume();
        zaai();
    }

    @Override // com.google.android.gms.common.api.internal.zak, com.google.android.gms.common.api.internal.LifecycleCallback
    public void onStart() {
        super.onStart();
        zaai();
    }

    @Override // com.google.android.gms.common.api.internal.zak, com.google.android.gms.common.api.internal.LifecycleCallback
    public void onStop() {
        super.onStop();
        this.zabo.zab(this);
    }

    public final ArraySet<ApiKey<?>> zaah() {
        return this.zafs;
    }

    @Override // com.google.android.gms.common.api.internal.zak
    public final void zam() {
        this.zabo.zam();
    }

    @Override // com.google.android.gms.common.api.internal.zak
    public final void zaa(ConnectionResult connectionResult, int i2) {
        this.zabo.zaa(connectionResult, i2);
    }
}
