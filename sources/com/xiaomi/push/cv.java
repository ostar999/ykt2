package com.xiaomi.push;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.List;

/* loaded from: classes6.dex */
public interface cv extends IInterface {

    public static abstract class a extends Binder implements cv {

        /* renamed from: com.xiaomi.push.cv$a$a, reason: collision with other inner class name */
        public static class C0406a implements cv {

            /* renamed from: a, reason: collision with root package name */
            private IBinder f24700a;

            public C0406a(IBinder iBinder) {
                this.f24700a = iBinder;
            }

            @Override // com.xiaomi.push.cv
            public int a() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    this.f24700a.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.push.cv
            /* renamed from: a */
            public String mo301a() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    this.f24700a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.push.cv
            public List<String> a(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    parcelObtain.writeString(str);
                    this.f24700a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.push.cv
            public void a(double d3, double d4, float f2, long j2, PendingIntent pendingIntent, String str, String str2) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    parcelObtain.writeDouble(d3);
                    parcelObtain.writeDouble(d4);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeLong(j2);
                    if (pendingIntent != null) {
                        parcelObtain.writeInt(1);
                        pendingIntent.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.f24700a.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.push.cv
            public void a(double d3, double d4, float f2, long j2, String str, String str2, String str3) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    parcelObtain.writeDouble(d3);
                    parcelObtain.writeDouble(d4);
                    parcelObtain.writeFloat(f2);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.f24700a.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.push.cv
            public void a(PendingIntent pendingIntent) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    if (pendingIntent != null) {
                        parcelObtain.writeInt(1);
                        pendingIntent.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.f24700a.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.push.cv
            /* renamed from: a */
            public void mo302a(String str) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    parcelObtain.writeString(str);
                    this.f24700a.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.push.cv
            public void a(String str, String str2) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.f24700a.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.push.cv
            public void a(List<String> list, String str) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str);
                    this.f24700a.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.f24700a;
            }
        }

        public static cv a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.metoknlp.geofencing.IGeoFencing");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof cv)) ? new C0406a(iBinder) : (cv) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) {
            if (i2 == 1598968902) {
                parcel2.writeString("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                return true;
            }
            switch (i2) {
                case 1:
                    parcel.enforceInterface("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    a(parcel.createStringArrayList(), parcel.readString());
                    return true;
                case 2:
                    parcel.enforceInterface("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    mo302a(parcel.readString());
                    return true;
                case 3:
                    parcel.enforceInterface("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    String strMo301a = mo301a();
                    parcel2.writeNoException();
                    parcel2.writeString(strMo301a);
                    return true;
                case 4:
                    parcel.enforceInterface("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    List<String> listA = a(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeStringList(listA);
                    return true;
                case 5:
                    parcel.enforceInterface("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    int iA = a();
                    parcel2.writeNoException();
                    parcel2.writeInt(iA);
                    return true;
                case 6:
                    parcel.enforceInterface("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    a(parcel.readDouble(), parcel.readDouble(), parcel.readFloat(), parcel.readLong(), parcel.readString(), parcel.readString(), parcel.readString());
                    return true;
                case 7:
                    parcel.enforceInterface("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    a(parcel.readDouble(), parcel.readDouble(), parcel.readFloat(), parcel.readLong(), parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null, parcel.readString(), parcel.readString());
                    return true;
                case 8:
                    parcel.enforceInterface("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    a(parcel.readString(), parcel.readString());
                    return true;
                case 9:
                    parcel.enforceInterface("com.xiaomi.metoknlp.geofencing.IGeoFencing");
                    a(parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    return true;
                default:
                    return super.onTransact(i2, parcel, parcel2, i3);
            }
        }
    }

    int a();

    /* renamed from: a, reason: collision with other method in class */
    String mo301a();

    List<String> a(String str);

    void a(double d3, double d4, float f2, long j2, PendingIntent pendingIntent, String str, String str2);

    void a(double d3, double d4, float f2, long j2, String str, String str2, String str3);

    void a(PendingIntent pendingIntent);

    /* renamed from: a, reason: collision with other method in class */
    void mo302a(String str);

    void a(String str, String str2);

    void a(List<String> list, String str);
}
