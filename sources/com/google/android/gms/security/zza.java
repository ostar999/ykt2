package com.google.android.gms.security;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

/* loaded from: classes3.dex */
final class zza extends AsyncTask<Void, Void, Integer> {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ ProviderInstaller.ProviderInstallListener zzix;

    public zza(Context context, ProviderInstaller.ProviderInstallListener providerInstallListener) {
        this.val$context = context;
        this.zzix = providerInstallListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // android.os.AsyncTask
    /* renamed from: zza, reason: merged with bridge method [inline-methods] */
    public final Integer doInBackground(Void... voidArr) {
        try {
            ProviderInstaller.installIfNeeded(this.val$context);
            return 0;
        } catch (GooglePlayServicesNotAvailableException e2) {
            return Integer.valueOf(e2.errorCode);
        } catch (GooglePlayServicesRepairableException e3) {
            return Integer.valueOf(e3.getConnectionStatusCode());
        }
    }

    @Override // android.os.AsyncTask
    public final /* synthetic */ void onPostExecute(Integer num) {
        Integer num2 = num;
        if (num2.intValue() == 0) {
            this.zzix.onProviderInstalled();
        } else {
            this.zzix.onProviderInstallFailed(num2.intValue(), ProviderInstaller.zziv.getErrorResolutionIntent(this.val$context, num2.intValue(), "pi"));
        }
    }
}
