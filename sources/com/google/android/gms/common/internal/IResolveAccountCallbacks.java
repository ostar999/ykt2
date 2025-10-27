package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IResolveAccountCallbacks extends IInterface {

    public static abstract class Stub extends com.google.android.gms.internal.base.zaa implements IResolveAccountCallbacks {

        public static class Proxy extends com.google.android.gms.internal.base.zab implements IResolveAccountCallbacks {
            public Proxy(IBinder iBinder) {
                super(iBinder, "com.google.android.gms.common.internal.IResolveAccountCallbacks");
            }

            @Override // com.google.android.gms.common.internal.IResolveAccountCallbacks
            public void onAccountResolutionComplete(ResolveAccountResponse resolveAccountResponse) throws RemoteException {
                Parcel parcelZaa = zaa();
                com.google.android.gms.internal.base.zad.zaa(parcelZaa, resolveAccountResponse);
                zab(2, parcelZaa);
            }
        }

        public Stub() {
            super("com.google.android.gms.common.internal.IResolveAccountCallbacks");
        }

        public static IResolveAccountCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IResolveAccountCallbacks");
            return iInterfaceQueryLocalInterface instanceof IResolveAccountCallbacks ? (IResolveAccountCallbacks) iInterfaceQueryLocalInterface : new Proxy(iBinder);
        }

        @Override // com.google.android.gms.internal.base.zaa
        public boolean dispatchTransaction(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 != 2) {
                return false;
            }
            onAccountResolutionComplete((ResolveAccountResponse) com.google.android.gms.internal.base.zad.zaa(parcel, ResolveAccountResponse.CREATOR));
            parcel2.writeNoException();
            return true;
        }
    }

    void onAccountResolutionComplete(ResolveAccountResponse resolveAccountResponse) throws RemoteException;
}
