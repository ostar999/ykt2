package com.umeng.analytics.pro;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.umeng.analytics.pro.b;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class ai implements z {

    public static final class a implements ServiceConnection {

        /* renamed from: a, reason: collision with root package name */
        boolean f22434a;

        /* renamed from: b, reason: collision with root package name */
        private final LinkedBlockingQueue<IBinder> f22435b;

        public IBinder a() throws InterruptedException {
            if (this.f22434a) {
                throw new IllegalStateException();
            }
            this.f22434a = true;
            return this.f22435b.poll(5L, TimeUnit.SECONDS);
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws InterruptedException {
            try {
                this.f22435b.put(iBinder);
            } catch (InterruptedException unused) {
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
        }

        private a() {
            this.f22434a = false;
            this.f22435b = new LinkedBlockingQueue<>();
        }
    }

    @Override // com.umeng.analytics.pro.z
    public String a(Context context) {
        a aVar = new a();
        Intent intent = new Intent();
        intent.setClassName("com.samsung.android.deviceidservice", "com.samsung.android.deviceidservice.DeviceIdService");
        if (context.bindService(intent, aVar, 1)) {
            try {
                return b.a.a(aVar.a()).a();
            } catch (Exception unused) {
            } finally {
                context.unbindService(aVar);
            }
        }
        return null;
    }
}
