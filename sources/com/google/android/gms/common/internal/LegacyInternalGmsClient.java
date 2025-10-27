package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IInterface;
import androidx.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

@Deprecated
/* loaded from: classes3.dex */
public abstract class LegacyInternalGmsClient<T extends IInterface> extends GmsClient<T> {
    private final GmsClientEventManager zagw;

    public LegacyInternalGmsClient(Context context, int i2, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, context.getMainLooper(), i2, clientSettings);
        GmsClientEventManager gmsClientEventManager = new GmsClientEventManager(context.getMainLooper(), this);
        this.zagw = gmsClientEventManager;
        gmsClientEventManager.registerConnectionCallbacks(connectionCallbacks);
        gmsClientEventManager.registerConnectionFailedListener(onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public void checkAvailabilityAndConnect() {
        this.zagw.enableCallbacks();
        super.checkAvailabilityAndConnect();
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public void disconnect() {
        this.zagw.disableCallbacks();
        super.disconnect();
    }

    public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        return this.zagw.isConnectionCallbacksRegistered(connectionCallbacks);
    }

    public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return this.zagw.isConnectionFailedListenerRegistered(onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public void onConnectedLocked(@NonNull T t2) {
        super.onConnectedLocked(t2);
        this.zagw.onConnectionSuccess(getConnectionHint());
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public void onConnectionFailed(ConnectionResult connectionResult) {
        super.onConnectionFailed(connectionResult);
        this.zagw.onConnectionFailure(connectionResult);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public void onConnectionSuspended(int i2) {
        super.onConnectionSuspended(i2);
        this.zagw.onUnintentionalDisconnection(i2);
    }

    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zagw.registerConnectionCallbacks(connectionCallbacks);
    }

    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zagw.registerConnectionFailedListener(onConnectionFailedListener);
    }

    public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        this.zagw.unregisterConnectionCallbacks(connectionCallbacks);
    }

    public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this.zagw.unregisterConnectionFailedListener(onConnectionFailedListener);
    }
}
