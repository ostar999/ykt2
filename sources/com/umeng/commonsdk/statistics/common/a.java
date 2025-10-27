package com.umeng.commonsdk.statistics.common;

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

/* loaded from: classes6.dex */
public class a {

    /* renamed from: com.umeng.commonsdk.statistics.common.a$a, reason: collision with other inner class name */
    public static final class C0384a {

        /* renamed from: a, reason: collision with root package name */
        private final String f23318a;

        /* renamed from: b, reason: collision with root package name */
        private final boolean f23319b;

        public C0384a(String str, boolean z2) {
            this.f23318a = str;
            this.f23319b = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String b() {
            return this.f23318a;
        }

        public boolean a() {
            return this.f23319b;
        }
    }

    public static final class b implements ServiceConnection {

        /* renamed from: a, reason: collision with root package name */
        boolean f23320a;

        /* renamed from: b, reason: collision with root package name */
        private final LinkedBlockingQueue<IBinder> f23321b;

        private b() {
            this.f23320a = false;
            this.f23321b = new LinkedBlockingQueue<>(1);
        }

        public IBinder a() throws InterruptedException {
            if (this.f23320a) {
                throw new IllegalStateException();
            }
            this.f23320a = true;
            return this.f23321b.take();
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
            try {
                this.f23321b.put(iBinder);
            } catch (InterruptedException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    public static String a(Context context) {
        try {
            C0384a c0384aC = c(context);
            if (c0384aC != null && !c0384aC.a()) {
                return c0384aC.b();
            }
        } catch (Exception unused) {
        }
        return null;
    }

    public static String b(Context context) {
        try {
            C0384a c0384aC = c(context);
            if (c0384aC == null) {
                return null;
            }
            return c0384aC.b();
        } catch (Exception unused) {
            return null;
        }
    }

    private static C0384a c(Context context) throws Exception {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return null;
        }
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            b bVar = new b();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            if (!context.bindService(intent, bVar, 1)) {
                throw new IOException("Google Play connection failed");
            }
            try {
                try {
                    c cVar = new c(bVar.a());
                    boolean zA = cVar.a(true);
                    return new C0384a(zA ? "" : cVar.a(), zA);
                } finally {
                    context.unbindService(bVar);
                }
            } catch (Exception e2) {
                throw e2;
            }
        } catch (Exception e3) {
            throw e3;
        }
    }

    public static final class c implements IInterface {

        /* renamed from: a, reason: collision with root package name */
        private IBinder f23322a;

        public c(IBinder iBinder) {
            this.f23322a = iBinder;
        }

        public String a() throws RemoteException {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.f23322a.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readString();
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.f23322a;
        }

        public boolean a(boolean z2) throws RemoteException {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                parcelObtain.writeInt(z2 ? 1 : 0);
                this.f23322a.transact(2, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readInt() != 0;
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }
    }
}
