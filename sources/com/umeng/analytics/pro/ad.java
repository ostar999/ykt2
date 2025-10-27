package com.umeng.analytics.pro;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
class ad implements z {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22422a = "com.uodis.opendevice.aidl.OpenDeviceIdentifierService";

    /* renamed from: b, reason: collision with root package name */
    private static final int f22423b = 1;

    /* renamed from: c, reason: collision with root package name */
    private static final int f22424c = 2;

    public static final class a implements ServiceConnection {

        /* renamed from: a, reason: collision with root package name */
        boolean f22425a;

        /* renamed from: b, reason: collision with root package name */
        private final LinkedBlockingQueue<IBinder> f22426b;

        private a() {
            this.f22425a = false;
            this.f22426b = new LinkedBlockingQueue<>();
        }

        public IBinder a() throws InterruptedException {
            if (this.f22425a) {
                throw new IllegalStateException();
            }
            this.f22425a = true;
            return this.f22426b.poll(5L, TimeUnit.SECONDS);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
            try {
                this.f22426b.put(iBinder);
            } catch (InterruptedException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    public static final class b implements IInterface {

        /* renamed from: a, reason: collision with root package name */
        private IBinder f22427a;

        public b(IBinder iBinder) {
            this.f22427a = iBinder;
        }

        public String a() throws RemoteException {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(ad.f22422a);
                this.f22427a.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readString();
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.f22427a;
        }

        public boolean b() throws RemoteException {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken(ad.f22422a);
                this.f22427a.transact(2, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readInt() != 0;
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }
    }

    @Override // com.umeng.analytics.pro.z
    public String a(Context context) {
        a aVar = new a();
        Intent intent = new Intent("com.uodis.opendevice.OPENIDS_SERVICE");
        intent.setPackage("com.huawei.hwid");
        if (context.bindService(intent, aVar, 1)) {
            try {
                return new b(aVar.a()).a();
            } catch (Exception unused) {
            } finally {
                context.unbindService(aVar);
            }
        }
        return null;
    }
}
