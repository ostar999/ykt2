package com.beizi.fusion.g;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes2.dex */
public final class b {

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private final String f5185a;

        /* renamed from: b, reason: collision with root package name */
        private final boolean f5186b;

        public a(String str, boolean z2) {
            this.f5185a = str;
            this.f5186b = z2;
        }

        public String a() {
            return this.f5185a;
        }

        public boolean b() {
            return this.f5186b;
        }
    }

    /* renamed from: com.beizi.fusion.g.b$b, reason: collision with other inner class name */
    public static final class ServiceConnectionC0068b implements ServiceConnection {

        /* renamed from: a, reason: collision with root package name */
        boolean f5187a;

        /* renamed from: b, reason: collision with root package name */
        private final LinkedBlockingQueue<IBinder> f5188b;

        private ServiceConnectionC0068b() {
            this.f5187a = false;
            this.f5188b = new LinkedBlockingQueue<>(1);
        }

        public IBinder a() throws InterruptedException {
            if (this.f5187a) {
                throw new IllegalStateException();
            }
            this.f5187a = true;
            return this.f5188b.take();
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
            try {
                this.f5188b.put(iBinder);
            } catch (InterruptedException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    public static a a(Context context) throws Exception {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            ServiceConnectionC0068b serviceConnectionC0068b = new ServiceConnectionC0068b();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            if (!context.bindService(intent, serviceConnectionC0068b, 1)) {
                throw new IOException("Google Play connection failed");
            }
            try {
                try {
                    c cVar = new c(serviceConnectionC0068b.a());
                    return new a(cVar.a(), cVar.a(true));
                } catch (Exception e2) {
                    throw e2;
                }
            } finally {
                context.unbindService(serviceConnectionC0068b);
            }
        } catch (Exception e3) {
            throw e3;
        }
    }

    public static final class c implements IInterface {

        /* renamed from: a, reason: collision with root package name */
        private IBinder f5189a;

        public c(IBinder iBinder) {
            this.f5189a = iBinder;
        }

        public String a() throws RemoteException {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.f5189a.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readString();
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.f5189a;
        }

        public boolean a(boolean z2) throws RemoteException {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                parcelObtain.writeInt(z2 ? 1 : 0);
                this.f5189a.transact(2, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readInt() != 0;
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }
    }
}
