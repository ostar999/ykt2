package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes3.dex */
public final class zzl extends com.google.android.gms.internal.common.zza implements zzk {
    public zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
    }

    @Override // com.google.android.gms.dynamite.zzk
    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String str, int i2, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.common.zzc.zza(parcelZza, iObjectWrapper);
        parcelZza.writeString(str);
        parcelZza.writeInt(i2);
        com.google.android.gms.internal.common.zzc.zza(parcelZza, iObjectWrapper2);
        Parcel parcelZza2 = zza(2, parcelZza);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZza2.readStrongBinder());
        parcelZza2.recycle();
        return iObjectWrapperAsInterface;
    }

    @Override // com.google.android.gms.dynamite.zzk
    public final IObjectWrapper zzb(IObjectWrapper iObjectWrapper, String str, int i2, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.common.zzc.zza(parcelZza, iObjectWrapper);
        parcelZza.writeString(str);
        parcelZza.writeInt(i2);
        com.google.android.gms.internal.common.zzc.zza(parcelZza, iObjectWrapper2);
        Parcel parcelZza2 = zza(3, parcelZza);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZza2.readStrongBinder());
        parcelZza2.recycle();
        return iObjectWrapperAsInterface;
    }
}
