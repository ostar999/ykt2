package com.beizi.fusion.sm.a.a;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

/* loaded from: classes2.dex */
class m implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    private final Context f5302a;

    /* renamed from: b, reason: collision with root package name */
    private final com.beizi.fusion.sm.a.c f5303b;

    /* renamed from: c, reason: collision with root package name */
    private final a f5304c;

    @FunctionalInterface
    public interface a {
        String a(IBinder iBinder) throws com.beizi.fusion.sm.a.e, RemoteException;
    }

    private m(Context context, com.beizi.fusion.sm.a.c cVar, a aVar) {
        if (context instanceof Application) {
            this.f5302a = context;
        } else {
            this.f5302a = context.getApplicationContext();
        }
        this.f5303b = cVar;
        this.f5304c = aVar;
    }

    public static void a(Context context, Intent intent, com.beizi.fusion.sm.a.c cVar, a aVar) {
        new m(context, cVar, aVar).a(intent);
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        com.beizi.fusion.sm.a.f.a("Service has been connected: " + componentName.getClassName());
        try {
            try {
                try {
                    String strA = this.f5304c.a(iBinder);
                    if (strA == null || strA.length() == 0) {
                        throw new com.beizi.fusion.sm.a.e("OAID/AAID acquire failed");
                    }
                    com.beizi.fusion.sm.a.f.a("OAID/AAID acquire success: " + strA);
                    this.f5303b.a(strA);
                    this.f5302a.unbindService(this);
                    com.beizi.fusion.sm.a.f.a("Service has been unbound: " + componentName.getClassName());
                } catch (Exception e2) {
                    com.beizi.fusion.sm.a.f.a(e2);
                    this.f5303b.a(e2);
                    this.f5302a.unbindService(this);
                    com.beizi.fusion.sm.a.f.a("Service has been unbound: " + componentName.getClassName());
                }
            } catch (Exception e3) {
                com.beizi.fusion.sm.a.f.a(e3);
            }
        } catch (Throwable th) {
            try {
                this.f5302a.unbindService(this);
                com.beizi.fusion.sm.a.f.a("Service has been unbound: " + componentName.getClassName());
            } catch (Exception e4) {
                com.beizi.fusion.sm.a.f.a(e4);
            }
            throw th;
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        com.beizi.fusion.sm.a.f.a("Service has been disconnected: " + componentName.getClassName());
    }

    private void a(Intent intent) {
        try {
            if (!this.f5302a.bindService(intent, this, 1)) {
                throw new com.beizi.fusion.sm.a.e("Service binding failed");
            }
            com.beizi.fusion.sm.a.f.a("Service has been bound: " + intent);
        } catch (Exception e2) {
            this.f5303b.a(e2);
        }
    }
}
