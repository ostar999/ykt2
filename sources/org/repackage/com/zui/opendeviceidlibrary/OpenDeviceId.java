package org.repackage.com.zui.opendeviceidlibrary;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import org.repackage.com.zui.deviceidservice.IDeviceidInterface;

/* loaded from: classes9.dex */
public class OpenDeviceId {

    /* renamed from: c, reason: collision with root package name */
    private static String f28088c = "OpenDeviceId library";

    /* renamed from: d, reason: collision with root package name */
    private static boolean f28089d = false;

    /* renamed from: b, reason: collision with root package name */
    private IDeviceidInterface f28091b;

    /* renamed from: e, reason: collision with root package name */
    private ServiceConnection f28092e;

    /* renamed from: a, reason: collision with root package name */
    private Context f28090a = null;

    /* renamed from: f, reason: collision with root package name */
    private CallBack f28093f = null;

    public interface CallBack<T> {
        void a(T t2, OpenDeviceId openDeviceId);
    }

    public String b() {
        if (this.f28090a == null) {
            b("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        try {
            IDeviceidInterface iDeviceidInterface = this.f28091b;
            if (iDeviceidInterface != null) {
                return iDeviceidInterface.b();
            }
            return null;
        } catch (RemoteException e2) {
            b("getUDID error, RemoteException!");
            e2.printStackTrace();
            return null;
        } catch (Exception e3) {
            b("getUDID error, Exception!");
            e3.printStackTrace();
            return null;
        }
    }

    public boolean c() {
        try {
            if (this.f28091b == null) {
                return false;
            }
            a("Device support opendeviceid");
            return this.f28091b.c();
        } catch (RemoteException unused) {
            b("isSupport error, RemoteException!");
            return false;
        }
    }

    public String d() {
        Context context = this.f28090a;
        if (context == null) {
            a("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        String packageName = context.getPackageName();
        a("liufeng, getVAID package：" + packageName);
        if (packageName == null || packageName.equals("")) {
            a("input package is null!");
            return null;
        }
        try {
            IDeviceidInterface iDeviceidInterface = this.f28091b;
            if (iDeviceidInterface != null) {
                return iDeviceidInterface.a(packageName);
            }
            return null;
        } catch (RemoteException e2) {
            b("getVAID error, RemoteException!");
            e2.printStackTrace();
            return null;
        }
    }

    public String e() {
        Context context = this.f28090a;
        if (context == null) {
            a("Context is null.");
            throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
        }
        String packageName = context.getPackageName();
        a("liufeng, getAAID package：" + packageName);
        String strB = null;
        if (packageName == null || packageName.equals("")) {
            a("input package is null!");
            return null;
        }
        try {
            IDeviceidInterface iDeviceidInterface = this.f28091b;
            if (iDeviceidInterface == null) {
                return null;
            }
            strB = iDeviceidInterface.b(packageName);
            return ((strB == null || "".equals(strB)) && this.f28091b.c(packageName)) ? this.f28091b.b(packageName) : strB;
        } catch (RemoteException unused) {
            b("getAAID error, RemoteException!");
            return strB;
        }
    }

    public void f() {
        try {
            this.f28090a.unbindService(this.f28092e);
            a("unBind Service successful");
        } catch (IllegalArgumentException unused) {
            b("unBind Service exception");
        }
        this.f28091b = null;
    }

    public int a(Context context, CallBack<String> callBack) {
        if (context != null) {
            this.f28090a = context;
            this.f28093f = callBack;
            this.f28092e = new ServiceConnection() { // from class: org.repackage.com.zui.opendeviceidlibrary.OpenDeviceId.1
                @Override // android.content.ServiceConnection
                public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    OpenDeviceId.this.f28091b = IDeviceidInterface.Stub.a(iBinder);
                    if (OpenDeviceId.this.f28093f != null) {
                        OpenDeviceId.this.f28093f.a("Deviceid Service Connected", OpenDeviceId.this);
                    }
                    OpenDeviceId.this.a("Service onServiceConnected");
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName componentName) {
                    OpenDeviceId.this.f28091b = null;
                    OpenDeviceId.this.a("Service onServiceDisconnected");
                }
            };
            Intent intent = new Intent();
            intent.setClassName("org.repackage.com.zui.deviceidservice", "org.repackage.com.zui.deviceidservice.DeviceidService");
            if (this.f28090a.bindService(intent, this.f28092e, 1)) {
                a("bindService Successful!");
                return 1;
            }
            a("bindService Failed!");
            return -1;
        }
        throw new NullPointerException("Context can not be null.");
    }

    private void b(String str) {
        if (f28089d) {
            Log.e(f28088c, str);
        }
    }

    public String a() {
        if (this.f28090a != null) {
            try {
                IDeviceidInterface iDeviceidInterface = this.f28091b;
                if (iDeviceidInterface != null) {
                    return iDeviceidInterface.a();
                }
                return null;
            } catch (RemoteException e2) {
                b("getOAID error, RemoteException!");
                e2.printStackTrace();
                return null;
            }
        }
        b("Context is null.");
        throw new IllegalArgumentException("Context is null, must be new OpenDeviceId first");
    }

    public void a(boolean z2) {
        f28089d = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str) {
        if (f28089d) {
            Log.i(f28088c, str);
        }
    }
}
