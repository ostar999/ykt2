package com.google.android.gms.common.api.internal;

import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public class zai extends zak {
    private final SparseArray<zaa> zacw;

    public class zaa implements GoogleApiClient.OnConnectionFailedListener {
        public final int zadd;
        public final GoogleApiClient zade;
        public final GoogleApiClient.OnConnectionFailedListener zadf;

        public zaa(int i2, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            this.zadd = i2;
            this.zade = googleApiClient;
            this.zadf = onConnectionFailedListener;
            googleApiClient.registerConnectionFailedListener(this);
        }

        @Override // com.google.android.gms.common.api.internal.OnConnectionFailedListener
        public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            String strValueOf = String.valueOf(connectionResult);
            StringBuilder sb = new StringBuilder(strValueOf.length() + 27);
            sb.append("beginFailureResolution for ");
            sb.append(strValueOf);
            Log.d("AutoManageHelper", sb.toString());
            zai.this.zab(connectionResult, this.zadd);
        }
    }

    private zai(LifecycleFragment lifecycleFragment) {
        super(lifecycleFragment);
        this.zacw = new SparseArray<>();
        this.mLifecycleFragment.addCallback("AutoManageHelper", this);
    }

    public static zai zaa(LifecycleActivity lifecycleActivity) {
        LifecycleFragment fragment = LifecycleCallback.getFragment(lifecycleActivity);
        zai zaiVar = (zai) fragment.getCallbackOrNull("AutoManageHelper", zai.class);
        return zaiVar != null ? zaiVar : new zai(fragment);
    }

    @Nullable
    private final zaa zab(int i2) {
        if (this.zacw.size() <= i2) {
            return null;
        }
        SparseArray<zaa> sparseArray = this.zacw;
        return sparseArray.get(sparseArray.keyAt(i2));
    }

    @Override // com.google.android.gms.common.api.internal.LifecycleCallback
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i2 = 0; i2 < this.zacw.size(); i2++) {
            zaa zaaVarZab = zab(i2);
            if (zaaVarZab != null) {
                printWriter.append((CharSequence) str).append("GoogleApiClient #").print(zaaVarZab.zadd);
                printWriter.println(":");
                zaaVarZab.zade.dump(String.valueOf(str).concat("  "), fileDescriptor, printWriter, strArr);
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zak, com.google.android.gms.common.api.internal.LifecycleCallback
    public void onStart() {
        super.onStart();
        boolean z2 = this.zadh;
        String strValueOf = String.valueOf(this.zacw);
        StringBuilder sb = new StringBuilder(strValueOf.length() + 14);
        sb.append("onStart ");
        sb.append(z2);
        sb.append(" ");
        sb.append(strValueOf);
        Log.d("AutoManageHelper", sb.toString());
        if (this.zadi.get() == null) {
            for (int i2 = 0; i2 < this.zacw.size(); i2++) {
                zaa zaaVarZab = zab(i2);
                if (zaaVarZab != null) {
                    zaaVarZab.zade.connect();
                }
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zak, com.google.android.gms.common.api.internal.LifecycleCallback
    public void onStop() {
        super.onStop();
        for (int i2 = 0; i2 < this.zacw.size(); i2++) {
            zaa zaaVarZab = zab(i2);
            if (zaaVarZab != null) {
                zaaVarZab.zade.disconnect();
            }
        }
    }

    @Override // com.google.android.gms.common.api.internal.zak
    public final void zam() {
        for (int i2 = 0; i2 < this.zacw.size(); i2++) {
            zaa zaaVarZab = zab(i2);
            if (zaaVarZab != null) {
                zaaVarZab.zade.connect();
            }
        }
    }

    public final void zaa(int i2, GoogleApiClient googleApiClient, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Preconditions.checkNotNull(googleApiClient, "GoogleApiClient instance cannot be null");
        boolean z2 = this.zacw.indexOfKey(i2) < 0;
        StringBuilder sb = new StringBuilder(54);
        sb.append("Already managing a GoogleApiClient with id ");
        sb.append(i2);
        Preconditions.checkState(z2, sb.toString());
        zam zamVar = this.zadi.get();
        boolean z3 = this.zadh;
        String strValueOf = String.valueOf(zamVar);
        StringBuilder sb2 = new StringBuilder(strValueOf.length() + 49);
        sb2.append("starting AutoManage for client ");
        sb2.append(i2);
        sb2.append(" ");
        sb2.append(z3);
        sb2.append(" ");
        sb2.append(strValueOf);
        Log.d("AutoManageHelper", sb2.toString());
        this.zacw.put(i2, new zaa(i2, googleApiClient, onConnectionFailedListener));
        if (this.zadh && zamVar == null) {
            String strValueOf2 = String.valueOf(googleApiClient);
            StringBuilder sb3 = new StringBuilder(strValueOf2.length() + 11);
            sb3.append("connecting ");
            sb3.append(strValueOf2);
            Log.d("AutoManageHelper", sb3.toString());
            googleApiClient.connect();
        }
    }

    public final void zaa(int i2) {
        zaa zaaVar = this.zacw.get(i2);
        this.zacw.remove(i2);
        if (zaaVar != null) {
            zaaVar.zade.unregisterConnectionFailedListener(zaaVar);
            zaaVar.zade.disconnect();
        }
    }

    @Override // com.google.android.gms.common.api.internal.zak
    public final void zaa(ConnectionResult connectionResult, int i2) {
        Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
        if (i2 < 0) {
            Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
            return;
        }
        zaa zaaVar = this.zacw.get(i2);
        if (zaaVar != null) {
            zaa(i2);
            GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener = zaaVar.zadf;
            if (onConnectionFailedListener != null) {
                onConnectionFailedListener.onConnectionFailed(connectionResult);
            }
        }
    }
}
