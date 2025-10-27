package androidx.room;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IMultiInstanceInvalidationCallback extends IInterface {

    public static class Default implements IMultiInstanceInvalidationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // androidx.room.IMultiInstanceInvalidationCallback
        public void onInvalidation(String[] strArr) throws RemoteException {
        }
    }

    public static abstract class Stub extends Binder implements IMultiInstanceInvalidationCallback {
        private static final String DESCRIPTOR = "androidx.room.IMultiInstanceInvalidationCallback";
        static final int TRANSACTION_onInvalidation = 1;

        public static class Proxy implements IMultiInstanceInvalidationCallback {
            public static IMultiInstanceInvalidationCallback sDefaultImpl;
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // androidx.room.IMultiInstanceInvalidationCallback
            public void onInvalidation(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    if (this.mRemote.transact(1, parcelObtain, null, 1) || Stub.getDefaultImpl() == null) {
                        return;
                    }
                    Stub.getDefaultImpl().onInvalidation(strArr);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMultiInstanceInvalidationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMultiInstanceInvalidationCallback)) ? new Proxy(iBinder) : (IMultiInstanceInvalidationCallback) iInterfaceQueryLocalInterface;
        }

        public static IMultiInstanceInvalidationCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback) {
            if (Proxy.sDefaultImpl != null || iMultiInstanceInvalidationCallback == null) {
                return false;
            }
            Proxy.sDefaultImpl = iMultiInstanceInvalidationCallback;
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                onInvalidation(parcel.createStringArray());
                return true;
            }
            if (i2 != 1598968902) {
                return super.onTransact(i2, parcel, parcel2, i3);
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }
    }

    void onInvalidation(String[] strArr) throws RemoteException;
}
