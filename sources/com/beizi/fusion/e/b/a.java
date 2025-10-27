package com.beizi.fusion.e.b;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface a extends IInterface {

    /* renamed from: com.beizi.fusion.e.b.a$a, reason: collision with other inner class name */
    public static final class C0063a implements a {

        /* renamed from: a, reason: collision with root package name */
        private IBinder f5048a;

        public C0063a(IBinder iBinder) {
            this.f5048a = iBinder;
        }

        public String a() {
            String string;
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.asus.msa.SupplementaryDID.IDidAidlInterface");
                this.f5048a.transact(3, parcelObtain, parcelObtain2, 0);
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
            return this.f5048a;
        }
    }
}
