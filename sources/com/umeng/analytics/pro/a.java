package com.umeng.analytics.pro;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface a extends IInterface {
    String a(String str) throws RemoteException;

    boolean a() throws RemoteException;

    String b() throws RemoteException;

    String b(String str) throws RemoteException;

    String c(String str) throws RemoteException;

    String d(String str) throws RemoteException;

    String e(String str) throws RemoteException;

    /* renamed from: com.umeng.analytics.pro.a$a, reason: collision with other inner class name */
    public static abstract class AbstractBinderC0375a extends Binder implements a {

        /* renamed from: a, reason: collision with root package name */
        static final int f22400a = 1;

        /* renamed from: b, reason: collision with root package name */
        static final int f22401b = 2;

        /* renamed from: c, reason: collision with root package name */
        static final int f22402c = 3;

        /* renamed from: d, reason: collision with root package name */
        static final int f22403d = 4;

        /* renamed from: e, reason: collision with root package name */
        static final int f22404e = 5;

        /* renamed from: f, reason: collision with root package name */
        static final int f22405f = 6;

        /* renamed from: g, reason: collision with root package name */
        static final int f22406g = 7;

        /* renamed from: h, reason: collision with root package name */
        private static final String f22407h = "com.coolpad.deviceidsupport.IDeviceIdManager";

        public AbstractBinderC0375a() {
            attachInterface(this, f22407h);
        }

        public static a a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(f22407h);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof a)) ? new C0376a(iBinder) : (a) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) throws RemoteException {
            if (i2 == 1598968902) {
                parcel2.writeString(f22407h);
                return true;
            }
            switch (i2) {
                case 1:
                    parcel.enforceInterface(f22407h);
                    String strA = a(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(strA);
                    return true;
                case 2:
                    parcel.enforceInterface(f22407h);
                    String strB = b(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(strB);
                    return true;
                case 3:
                    parcel.enforceInterface(f22407h);
                    String strC = c(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(strC);
                    return true;
                case 4:
                    parcel.enforceInterface(f22407h);
                    String strD = d(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(strD);
                    return true;
                case 5:
                    parcel.enforceInterface(f22407h);
                    String strE = e(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(strE);
                    return true;
                case 6:
                    parcel.enforceInterface(f22407h);
                    boolean zA = a();
                    parcel2.writeNoException();
                    parcel2.writeInt(zA ? 1 : 0);
                    return true;
                case 7:
                    parcel.enforceInterface(f22407h);
                    String strB2 = b();
                    parcel2.writeNoException();
                    parcel2.writeString(strB2);
                    return true;
                default:
                    return super.onTransact(i2, parcel, parcel2, i3);
            }
        }

        /* renamed from: com.umeng.analytics.pro.a$a$a, reason: collision with other inner class name */
        public static class C0376a implements a {

            /* renamed from: a, reason: collision with root package name */
            private IBinder f22408a;

            public C0376a(IBinder iBinder) {
                this.f22408a = iBinder;
            }

            @Override // com.umeng.analytics.pro.a
            public String a(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0375a.f22407h);
                    parcelObtain.writeString(str);
                    this.f22408a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f22408a;
            }

            @Override // com.umeng.analytics.pro.a
            public String b(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0375a.f22407h);
                    parcelObtain.writeString(str);
                    this.f22408a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String c() {
                return AbstractBinderC0375a.f22407h;
            }

            @Override // com.umeng.analytics.pro.a
            public String c(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0375a.f22407h);
                    parcelObtain.writeString(str);
                    this.f22408a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.umeng.analytics.pro.a
            public String d(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0375a.f22407h);
                    parcelObtain.writeString(str);
                    this.f22408a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.umeng.analytics.pro.a
            public String e(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0375a.f22407h);
                    parcelObtain.writeString(str);
                    this.f22408a.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.umeng.analytics.pro.a
            public boolean a() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0375a.f22407h);
                    this.f22408a.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.umeng.analytics.pro.a
            public String b() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(AbstractBinderC0375a.f22407h);
                    this.f22408a.transact(7, parcelObtain, parcelObtain2, 0);
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
