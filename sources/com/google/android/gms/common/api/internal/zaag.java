package com.google.android.gms.common.api.internal;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class zaag extends GoogleApiClient {
    private final String zafx;

    public zaag(String str) {
        this.zafx = str;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public ConnectionResult blockingConnect() {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void connect() {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void disconnect() {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    @NonNull
    public ConnectionResult getConnectionResult(@NonNull Api<?> api) {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean hasConnectedApi(@NonNull Api<?> api) {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnected() {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnecting() {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnectionCallbacksRegistered(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnectionFailedListenerRegistered(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void reconnect() {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void registerConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void registerConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void stopAutoManage(@NonNull FragmentActivity fragmentActivity) {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void unregisterConnectionCallbacks(@NonNull GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void unregisterConnectionFailedListener(@NonNull GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        throw new UnsupportedOperationException(this.zafx);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public ConnectionResult blockingConnect(long j2, @NonNull TimeUnit timeUnit) {
        throw new UnsupportedOperationException(this.zafx);
    }
}
