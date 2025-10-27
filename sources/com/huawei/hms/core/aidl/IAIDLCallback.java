package com.huawei.hms.core.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IAIDLCallback extends IInterface {

    public static abstract class Stub extends Binder implements IAIDLCallback {
        private static final String DESCRIPTOR = "com.huawei.hms.core.aidl.IAIDLCallback";
        static final int TRANSACTION_call = 1;

        public static class a implements IAIDLCallback {

            /* renamed from: b, reason: collision with root package name */
            public static IAIDLCallback f7557b;

            /* renamed from: a, reason: collision with root package name */
            private IBinder f7558a;

            public a(IBinder iBinder) {
                this.f7558a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f7558a;
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAIDLCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAIDLCallback)) ? new a(iBinder) : (IAIDLCallback) iInterfaceQueryLocalInterface;
        }

        public static IAIDLCallback getDefaultImpl() {
            return a.f7557b;
        }

        public static boolean setDefaultImpl(IAIDLCallback iAIDLCallback) {
            if (a.f7557b != null || iAIDLCallback == null) {
                return false;
            }
            a.f7557b = iAIDLCallback;
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
                call(parcel.readInt() != 0 ? DataBuffer.CREATOR.createFromParcel(parcel) : null);
                return true;
            }
            if (i2 != 1598968902) {
                return super.onTransact(i2, parcel, parcel2, i3);
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }
    }

    void call(DataBuffer dataBuffer) throws RemoteException;
}
