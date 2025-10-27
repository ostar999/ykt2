package com.hihonor.cloudservice.oaid;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.hihonor.cloudservice.oaid.a;

/* loaded from: classes4.dex */
public interface b extends IInterface {

    public static abstract class a extends Binder implements b {

        /* renamed from: a, reason: collision with root package name */
        public static final /* synthetic */ int f7274a = 0;

        /* renamed from: com.hihonor.cloudservice.oaid.b$a$a, reason: collision with other inner class name */
        public static class C0175a implements b {

            /* renamed from: a, reason: collision with root package name */
            public IBinder f7275a;

            public C0175a(IBinder iBinder) {
                this.f7275a = iBinder;
            }

            @Override // com.hihonor.cloudservice.oaid.b
            public void a(com.hihonor.cloudservice.oaid.a aVar) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.hihonor.cloudservice.oaid.IOAIDService");
                    parcelObtain.writeStrongBinder(aVar != null ? (a.AbstractBinderC0174a) aVar : null);
                    if (!this.f7275a.transact(3, parcelObtain, parcelObtain2, 0)) {
                        int i2 = a.f7274a;
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f7275a;
            }

            @Override // com.hihonor.cloudservice.oaid.b
            public void b(com.hihonor.cloudservice.oaid.a aVar) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.hihonor.cloudservice.oaid.IOAIDService");
                    parcelObtain.writeStrongBinder(aVar != null ? (a.AbstractBinderC0174a) aVar : null);
                    if (!this.f7275a.transact(2, parcelObtain, parcelObtain2, 0)) {
                        int i2 = a.f7274a;
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }

    void a(com.hihonor.cloudservice.oaid.a aVar);

    void b(com.hihonor.cloudservice.oaid.a aVar);
}
