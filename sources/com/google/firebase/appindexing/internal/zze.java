package com.google.firebase.appindexing.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;

/* loaded from: classes4.dex */
public final class zze extends GmsClient<zzr> {
    static final Api<Api.ApiOptions.NoOptions> API;
    private static final Api.ClientKey<zze> CLIENT_KEY;
    private static final Api.AbstractClientBuilder<zze, Api.ApiOptions.NoOptions> zzev;

    static {
        Api.ClientKey<zze> clientKey = new Api.ClientKey<>();
        CLIENT_KEY = clientKey;
        zzd zzdVar = new zzd();
        zzev = zzdVar;
        API = new Api<>("AppIndexing.API", zzdVar, clientKey);
    }

    public zze(Context context, Looper looper, ClientSettings clientSettings, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 113, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.firebase.appindexing.internal.IAppIndexingService");
        return iInterfaceQueryLocalInterface instanceof zzr ? (zzr) iInterfaceQueryLocalInterface : new zzu(iBinder);
    }

    @Override // com.google.android.gms.common.internal.GmsClient, com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 12600000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getServiceDescriptor() {
        return "com.google.firebase.appindexing.internal.IAppIndexingService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final String getStartServiceAction() {
        return "com.google.android.gms.icing.APP_INDEXING_SERVICE";
    }
}
