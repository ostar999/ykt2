package com.umeng.analytics.pro;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.umeng.analytics.pro.a;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class ab implements z {

    /* renamed from: a, reason: collision with root package name */
    private static final String f22411a = "Coolpad";

    /* renamed from: b, reason: collision with root package name */
    private static final String f22412b = "com.coolpad.deviceidsupport";

    /* renamed from: c, reason: collision with root package name */
    private static final String f22413c = "com.coolpad.deviceidsupport.DeviceIdService";

    /* renamed from: d, reason: collision with root package name */
    private static a f22414d;

    /* renamed from: f, reason: collision with root package name */
    private CountDownLatch f22416f;

    /* renamed from: g, reason: collision with root package name */
    private Context f22417g;

    /* renamed from: e, reason: collision with root package name */
    private String f22415e = "";

    /* renamed from: h, reason: collision with root package name */
    private final ServiceConnection f22418h = new ServiceConnection() { // from class: com.umeng.analytics.pro.ab.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                a unused = ab.f22414d = a.AbstractBinderC0375a.a(iBinder);
                ab.this.f22415e = ab.f22414d.b(ab.this.f22417g.getPackageName());
                Log.d(ab.f22411a, "onServiceConnected: oaid = " + ab.this.f22415e);
            } catch (RemoteException | NullPointerException e2) {
                Log.e(ab.f22411a, "onServiceConnected failed e=" + e2.getMessage());
            }
            ab.this.f22416f.countDown();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(ab.f22411a, "onServiceDisconnected");
            a unused = ab.f22414d = null;
        }
    };

    private void b(Context context) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(f22412b, f22413c));
            if (context.bindService(intent, this.f22418h, 1)) {
                return;
            }
            Log.e(f22411a, "bindService return false");
        } catch (Throwable th) {
            Log.e(f22411a, "bindService failed. e=" + th.getMessage());
            this.f22416f.countDown();
        }
    }

    private void c(Context context) {
        try {
            Log.d(f22411a, "call unbindService.");
            context.unbindService(this.f22418h);
        } catch (Throwable th) {
            Log.e(f22411a, "unbindService failed. e=" + th.getMessage());
        }
    }

    @Override // com.umeng.analytics.pro.z
    public String a(Context context) {
        if (context == null) {
            return null;
        }
        this.f22417g = context.getApplicationContext();
        this.f22416f = new CountDownLatch(1);
        try {
            b(context);
            if (!this.f22416f.await(500L, TimeUnit.MILLISECONDS)) {
                Log.e(f22411a, "getOAID time-out");
            }
            return this.f22415e;
        } catch (InterruptedException e2) {
            Log.e(f22411a, "getOAID interrupted. e=" + e2.getMessage());
            return null;
        } finally {
            c(context);
        }
    }
}
