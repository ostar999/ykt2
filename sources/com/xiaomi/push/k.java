package com.xiaomi.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
final class k {

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        private final String f25504a;

        /* renamed from: a, reason: collision with other field name */
        private final boolean f930a;

        public a(String str, boolean z2) {
            this.f25504a = str;
            this.f930a = z2;
        }

        public String a() {
            return this.f25504a;
        }
    }

    public static final class b implements ServiceConnection {

        /* renamed from: a, reason: collision with root package name */
        private final LinkedBlockingQueue<IBinder> f25505a;

        /* renamed from: a, reason: collision with other field name */
        boolean f931a;

        private b() {
            this.f931a = false;
            this.f25505a = new LinkedBlockingQueue<>(1);
        }

        public IBinder a() {
            if (this.f931a) {
                throw new IllegalStateException();
            }
            this.f931a = true;
            return this.f25505a.poll(30000L, TimeUnit.MILLISECONDS);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
            try {
                this.f25505a.put(iBinder);
            } catch (InterruptedException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    public static final class c implements IInterface {

        /* renamed from: a, reason: collision with root package name */
        private IBinder f25506a;

        public c(IBinder iBinder) {
            this.f25506a = iBinder;
        }

        public String a() {
            Parcel parcelObtain = Parcel.obtain();
            Parcel parcelObtain2 = Parcel.obtain();
            try {
                parcelObtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.f25506a.transact(1, parcelObtain, parcelObtain2, 0);
                parcelObtain2.readException();
                return parcelObtain2.readString();
            } finally {
                parcelObtain2.recycle();
                parcelObtain.recycle();
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this.f25506a;
        }
    }

    public static a a(Context context) throws Exception {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
            b bVar = new b();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            if (context.bindService(intent, bVar, 1)) {
                try {
                    try {
                        IBinder iBinderA = bVar.a();
                        if (iBinderA != null) {
                            return new a(new c(iBinderA).a(), false);
                        }
                    } finally {
                        context.unbindService(bVar);
                    }
                } catch (Exception e2) {
                    throw e2;
                }
            }
            throw new IOException("Google Play connection failed");
        } catch (Exception e3) {
            throw e3;
        }
    }
}
