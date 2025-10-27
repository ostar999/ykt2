package com.beizi.fusion.e.b;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface f extends IInterface {

    public static class a implements f {

        /* renamed from: a, reason: collision with root package name */
        private IBinder f5053a;

        public a(IBinder iBinder) {
            this.f5053a = iBinder;
        }

        public String a() {
            String string;
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.samsung.android.deviceidservice.IDeviceIdService");
                this.f5053a.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                string = parcelObtain2.readString();
            } catch (Throwable th) {
                parcelObtain2.recycle();
                parcelObtain.recycle();
                th.printStackTrace();
                string = null;
            }
            parcelObtain2.recycle();
            parcelObtain.recycle();
            return string;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.f5053a;
        }
    }
}
