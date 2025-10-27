package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

/* loaded from: classes3.dex */
public interface IStatusCallback extends IInterface {

    public static abstract class Stub extends com.google.android.gms.internal.base.zaa implements IStatusCallback {

        public static class zaa extends com.google.android.gms.internal.base.zab implements IStatusCallback {
            public zaa(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.common.api.internal.IStatusCallback");
            }

            @Override // com.google.android.gms.common.api.internal.IStatusCallback
            public final void onResult(Status status) throws RemoteException {
                Parcel parcelZaa = zaa();
                com.google.android.gms.internal.base.zad.zaa(parcelZaa, status);
                zac(1, parcelZaa);
            }
        }

        public Stub() {
            super("com.google.android.gms.common.api.internal.IStatusCallback");
        }

        public static IStatusCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.api.internal.IStatusCallback");
            return iInterfaceQueryLocalInterface instanceof IStatusCallback ? (IStatusCallback) iInterfaceQueryLocalInterface : new zaa(iBinder);
        }

        @Override // com.google.android.gms.internal.base.zaa
        public boolean dispatchTransaction(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 != 1) {
                return false;
            }
            onResult((Status) com.google.android.gms.internal.base.zad.zaa(parcel, Status.CREATOR));
            return true;
        }
    }

    void onResult(Status status) throws RemoteException;
}
