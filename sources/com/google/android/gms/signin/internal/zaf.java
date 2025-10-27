package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
public abstract class zaf extends com.google.android.gms.internal.base.zaa implements zac {
    public zaf() {
        super("com.google.android.gms.signin.internal.ISignInCallbacks");
    }

    @Override // com.google.android.gms.internal.base.zaa
    public boolean dispatchTransaction(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 == 3) {
            zaa((ConnectionResult) com.google.android.gms.internal.base.zad.zaa(parcel, ConnectionResult.CREATOR), (zab) com.google.android.gms.internal.base.zad.zaa(parcel, zab.CREATOR));
        } else if (i2 == 4) {
            zag((Status) com.google.android.gms.internal.base.zad.zaa(parcel, Status.CREATOR));
        } else if (i2 == 6) {
            zah((Status) com.google.android.gms.internal.base.zad.zaa(parcel, Status.CREATOR));
        } else if (i2 == 7) {
            zaa((Status) com.google.android.gms.internal.base.zad.zaa(parcel, Status.CREATOR), (GoogleSignInAccount) com.google.android.gms.internal.base.zad.zaa(parcel, GoogleSignInAccount.CREATOR));
        } else {
            if (i2 != 8) {
                return false;
            }
            zab((zak) com.google.android.gms.internal.base.zad.zaa(parcel, zak.CREATOR));
        }
        parcel2.writeNoException();
        return true;
    }
}
