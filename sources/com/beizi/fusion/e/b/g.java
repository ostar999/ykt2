package com.beizi.fusion.e.b;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* loaded from: classes2.dex */
public interface g extends IInterface {

    public static abstract class a extends Binder implements g {

        /* renamed from: com.beizi.fusion.e.b.g$a$a, reason: collision with other inner class name */
        public static class C0067a implements g {

            /* renamed from: a, reason: collision with root package name */
            private IBinder f5054a;

            public C0067a(IBinder iBinder) {
                this.f5054a = iBinder;
            }

            @Override // com.beizi.fusion.e.b.g
            public String a() {
                String string;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.bun.lib.MsaIdInterface");
                    this.f5054a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    string = parcelObtain2.readString();
                } catch (Throwable unused) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    string = null;
                }
                parcelObtain2.recycle();
                parcelObtain.recycle();
                return string;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f5054a;
            }
        }
    }

    String a();
}
