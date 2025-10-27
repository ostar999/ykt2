package com.google.android.gms.internal.icing;

import android.os.IInterface;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
public interface zzac extends IInterface {
    void zza(Status status) throws RemoteException;

    void zza(Status status, ParcelFileDescriptor parcelFileDescriptor) throws RemoteException;

    void zza(zzo zzoVar) throws RemoteException;
}
