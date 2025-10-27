package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
public abstract class zzaf extends zza implements zzac {
    public zzaf() {
        super("com.google.android.gms.appdatasearch.internal.ILightweightAppDataSearchCallbacks");
    }

    @Override // com.google.android.gms.internal.icing.zza
    public final boolean dispatchTransaction(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
        if (i2 == 1) {
            zza((Status) zzd.zza(parcel, Status.CREATOR));
        } else if (i2 == 2) {
            zza((Status) zzd.zza(parcel, Status.CREATOR), (ParcelFileDescriptor) zzd.zza(parcel, ParcelFileDescriptor.CREATOR));
        } else {
            if (i2 != 4) {
                return false;
            }
            zza((zzo) zzd.zza(parcel, zzo.CREATOR));
        }
        return true;
    }
}
