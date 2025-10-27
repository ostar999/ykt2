package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.search.GoogleNowAuthState;

/* loaded from: classes3.dex */
public abstract class zzao extends zza implements zzam {
    public zzao() {
        super("com.google.android.gms.search.internal.ISearchAuthCallbacks");
    }

    @Override // com.google.android.gms.internal.icing.zza
    public final boolean dispatchTransaction(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 == 1) {
            zza((Status) zzd.zza(parcel, Status.CREATOR), (GoogleNowAuthState) zzd.zza(parcel, GoogleNowAuthState.CREATOR));
        } else {
            if (i2 != 2) {
                return false;
            }
            zzb((Status) zzd.zza(parcel, Status.CREATOR));
        }
        return true;
    }
}
