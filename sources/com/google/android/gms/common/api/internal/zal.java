package com.google.android.gms.common.api.internal;

import android.app.Dialog;
import androidx.annotation.MainThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;

/* loaded from: classes3.dex */
final class zal implements Runnable {
    private final zam zadk;
    final /* synthetic */ zak zadl;

    public zal(zak zakVar, zam zamVar) {
        this.zadl = zakVar;
        this.zadk = zamVar;
    }

    @Override // java.lang.Runnable
    @MainThread
    public final void run() {
        if (this.zadl.zadh) {
            ConnectionResult connectionResult = this.zadk.getConnectionResult();
            if (connectionResult.hasResolution()) {
                zak zakVar = this.zadl;
                zakVar.mLifecycleFragment.startActivityForResult(GoogleApiActivity.zaa(zakVar.getActivity(), connectionResult.getResolution(), this.zadk.zap(), false), 1);
            } else if (this.zadl.zace.isUserResolvableError(connectionResult.getErrorCode())) {
                zak zakVar2 = this.zadl;
                zakVar2.zace.zaa(zakVar2.getActivity(), this.zadl.mLifecycleFragment, connectionResult.getErrorCode(), 2, this.zadl);
            } else {
                if (connectionResult.getErrorCode() != 18) {
                    this.zadl.zaa(connectionResult, this.zadk.zap());
                    return;
                }
                Dialog dialogZaa = GoogleApiAvailability.zaa(this.zadl.getActivity(), this.zadl);
                zak zakVar3 = this.zadl;
                zakVar3.zace.zaa(zakVar3.getActivity().getApplicationContext(), new zan(this, dialogZaa));
            }
        }
    }
}
