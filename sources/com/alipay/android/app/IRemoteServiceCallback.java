package com.alipay.android.app;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRemoteServiceCallback extends IInterface {

    public static abstract class Stub extends Binder implements IRemoteServiceCallback {
        private static final String DESCRIPTOR = "com.alipay.android.app.IRemoteServiceCallback";
        static final int TRANSACTION_isHideLoadingScreen = 3;
        static final int TRANSACTION_payEnd = 2;
        static final int TRANSACTION_startActivity = 1;

        public static class a implements IRemoteServiceCallback {

            /* renamed from: a, reason: collision with root package name */
            private IBinder f2922a;

            public a(IBinder iBinder) {
                this.f2922a = iBinder;
            }

            private static String a() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.f2922a;
            }

            @Override // com.alipay.android.app.IRemoteServiceCallback
            public final boolean isHideLoadingScreen() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.f2922a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alipay.android.app.IRemoteServiceCallback
            public final void payEnd(boolean z2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    parcelObtain.writeString(str);
                    this.f2922a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.alipay.android.app.IRemoteServiceCallback
            public final void startActivity(String str, String str2, int i2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    if (bundle != null) {
                        parcelObtain.writeInt(1);
                        bundle.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.f2922a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IRemoteServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRemoteServiceCallback)) ? new a(iBinder) : (IRemoteServiceCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                startActivity(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                parcel2.writeNoException();
                return true;
            }
            if (i2 == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                payEnd(parcel.readInt() != 0, parcel.readString());
                parcel2.writeNoException();
                return true;
            }
            if (i2 != 3) {
                if (i2 != 1598968902) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            boolean zIsHideLoadingScreen = isHideLoadingScreen();
            parcel2.writeNoException();
            parcel2.writeInt(zIsHideLoadingScreen ? 1 : 0);
            return true;
        }
    }

    boolean isHideLoadingScreen() throws RemoteException;

    void payEnd(boolean z2, String str) throws RemoteException;

    void startActivity(String str, String str2, int i2, Bundle bundle) throws RemoteException;
}
