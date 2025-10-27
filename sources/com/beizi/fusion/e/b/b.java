package com.beizi.fusion.e.b;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface b extends IInterface {

    public static final class a implements b {

        /* renamed from: a, reason: collision with root package name */
        private IBinder f5049a;

        public a(IBinder iBinder) {
            this.f5049a = iBinder;
        }

        public String a() {
            String string;
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
                this.f5049a.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                string = parcelObtain2.readString();
            } catch (Throwable th) {
                parcelObtain.recycle();
                parcelObtain2.recycle();
                th.printStackTrace();
                string = null;
            }
            parcelObtain.recycle();
            parcelObtain2.recycle();
            return string;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.f5049a;
        }

        public boolean b() {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            boolean z2 = false;
            try {
                parcelObtain.writeInterfaceToken("com.uodis.opendevice.aidl.OpenDeviceIdentifierService");
                this.f5049a.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                if (parcelObtain2.readInt() == 0) {
                    z2 = true;
                }
            } catch (Throwable unused) {
                parcelObtain.recycle();
                parcelObtain2.recycle();
            }
            parcelObtain.recycle();
            parcelObtain2.recycle();
            return z2;
        }
    }
}
