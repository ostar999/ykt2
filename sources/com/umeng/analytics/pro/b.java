package com.umeng.analytics.pro;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface b extends IInterface {
    String a() throws RemoteException;

    String a(String str) throws RemoteException;

    String b(String str) throws RemoteException;

    public static abstract class a extends Binder implements b {

        /* renamed from: a, reason: collision with root package name */
        static final int f22547a = 1;

        /* renamed from: b, reason: collision with root package name */
        static final int f22548b = 2;

        /* renamed from: c, reason: collision with root package name */
        static final int f22549c = 3;

        /* renamed from: d, reason: collision with root package name */
        private static final String f22550d = "com.samsung.android.deviceidservice.IDeviceIdService";

        public a() {
            attachInterface(this, f22550d);
        }

        public static b a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(f22550d);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof b)) ? new C0377a(iBinder) : (b) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1) {
                parcel.enforceInterface(f22550d);
                String strA = a();
                parcel2.writeNoException();
                parcel2.writeString(strA);
                return true;
            }
            if (i2 == 2) {
                parcel.enforceInterface(f22550d);
                String strA2 = a(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(strA2);
                return true;
            }
            if (i2 != 3) {
                if (i2 != 1598968902) {
                    return super.onTransact(i2, parcel, parcel2, i3);
                }
                parcel2.writeString(f22550d);
                return true;
            }
            parcel.enforceInterface(f22550d);
            String strB = b(parcel.readString());
            parcel2.writeNoException();
            parcel2.writeString(strB);
            return true;
        }

        /* renamed from: com.umeng.analytics.pro.b$a$a, reason: collision with other inner class name */
        public static class C0377a implements b {

            /* renamed from: a, reason: collision with root package name */
            private IBinder f22551a;

            public C0377a(IBinder iBinder) {
                this.f22551a = iBinder;
            }

            @Override // com.umeng.analytics.pro.b
            public String a() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f22550d);
                    this.f22551a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f22551a;
            }

            public String b() {
                return a.f22550d;
            }

            @Override // com.umeng.analytics.pro.b
            public String b(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f22550d);
                    parcelObtain.writeString(str);
                    this.f22551a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.umeng.analytics.pro.b
            public String a(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(a.f22550d);
                    parcelObtain.writeString(str);
                    this.f22551a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
