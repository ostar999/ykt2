package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes3.dex */
public final class zah extends com.google.android.gms.internal.base.zab implements ISignInButtonCreator {
    public zah(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ISignInButtonCreator");
    }

    @Override // com.google.android.gms.common.internal.ISignInButtonCreator
    public final IObjectWrapper newSignInButton(IObjectWrapper iObjectWrapper, int i2, int i3) throws RemoteException {
        Parcel parcelZaa = zaa();
        com.google.android.gms.internal.base.zad.zaa(parcelZaa, iObjectWrapper);
        parcelZaa.writeInt(i2);
        parcelZaa.writeInt(i3);
        Parcel parcelZaa2 = zaa(1, parcelZaa);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZaa2.readStrongBinder());
        parcelZaa2.recycle();
        return iObjectWrapperAsInterface;
    }

    @Override // com.google.android.gms.common.internal.ISignInButtonCreator
    public final IObjectWrapper newSignInButtonFromConfig(IObjectWrapper iObjectWrapper, SignInButtonConfig signInButtonConfig) throws RemoteException {
        Parcel parcelZaa = zaa();
        com.google.android.gms.internal.base.zad.zaa(parcelZaa, iObjectWrapper);
        com.google.android.gms.internal.base.zad.zaa(parcelZaa, signInButtonConfig);
        Parcel parcelZaa2 = zaa(2, parcelZaa);
        IObjectWrapper iObjectWrapperAsInterface = IObjectWrapper.Stub.asInterface(parcelZaa2.readStrongBinder());
        parcelZaa2.recycle();
        return iObjectWrapperAsInterface;
    }
}
