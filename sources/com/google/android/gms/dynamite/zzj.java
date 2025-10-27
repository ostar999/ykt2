package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes3.dex */
public final class zzj extends com.google.android.gms.internal.common.zza implements zzi {
    public zzj(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoader");
    }

    @Override // com.google.android.gms.dynamite.zzi
    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String str, int i2) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.common.zzc.zza(parcelZza, iObjectWrapper);
        parcelZza.writeString(str);
        parcelZza.writeInt(i2);
        Parcel parcelZza2 = zza(2, parcelZza);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZza2.readStrongBinder());
        parcelZza2.recycle();
        return iObjectWrapperAsInterface;
    }

    @Override // com.google.android.gms.dynamite.zzi
    public final int zzak() throws RemoteException {
        Parcel parcelZza = zza(6, zza());
        int i2 = parcelZza.readInt();
        parcelZza.recycle();
        return i2;
    }

    @Override // com.google.android.gms.dynamite.zzi
    public final IObjectWrapper zzb(IObjectWrapper iObjectWrapper, String str, int i2) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.common.zzc.zza(parcelZza, iObjectWrapper);
        parcelZza.writeString(str);
        parcelZza.writeInt(i2);
        Parcel parcelZza2 = zza(4, parcelZza);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZza2.readStrongBinder());
        parcelZza2.recycle();
        return iObjectWrapperAsInterface;
    }

    @Override // com.google.android.gms.dynamite.zzi
    public final int zza(IObjectWrapper iObjectWrapper, String str, boolean z2) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.common.zzc.zza(parcelZza, iObjectWrapper);
        parcelZza.writeString(str);
        com.google.android.gms.internal.common.zzc.writeBoolean(parcelZza, z2);
        Parcel parcelZza2 = zza(3, parcelZza);
        int i2 = parcelZza2.readInt();
        parcelZza2.recycle();
        return i2;
    }

    @Override // com.google.android.gms.dynamite.zzi
    public final int zzb(IObjectWrapper iObjectWrapper, String str, boolean z2) throws RemoteException {
        Parcel parcelZza = zza();
        com.google.android.gms.internal.common.zzc.zza(parcelZza, iObjectWrapper);
        parcelZza.writeString(str);
        com.google.android.gms.internal.common.zzc.writeBoolean(parcelZza, z2);
        Parcel parcelZza2 = zza(5, parcelZza);
        int i2 = parcelZza2.readInt();
        parcelZza2.recycle();
        return i2;
    }
}
