package com.vivo.push;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.vivo.push.util.t;
import com.vivo.push.util.z;
import com.vivo.vms.IPCInvoke;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes6.dex */
public final class b implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    private static final Object f24229a = new Object();

    /* renamed from: b, reason: collision with root package name */
    private static Map<String, b> f24230b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    private boolean f24231c;

    /* renamed from: d, reason: collision with root package name */
    private String f24232d;

    /* renamed from: e, reason: collision with root package name */
    private Context f24233e;

    /* renamed from: g, reason: collision with root package name */
    private volatile IPCInvoke f24235g;

    /* renamed from: i, reason: collision with root package name */
    private String f24237i;

    /* renamed from: j, reason: collision with root package name */
    private Handler f24238j;

    /* renamed from: h, reason: collision with root package name */
    private Object f24236h = new Object();

    /* renamed from: f, reason: collision with root package name */
    private AtomicInteger f24234f = new AtomicInteger(1);

    private b(Context context, String str) {
        this.f24232d = null;
        this.f24238j = null;
        this.f24233e = context;
        this.f24237i = str;
        this.f24238j = new Handler(Looper.getMainLooper(), new c(this));
        String strB = t.b(context);
        this.f24232d = strB;
        if (!TextUtils.isEmpty(strB) && !TextUtils.isEmpty(this.f24237i)) {
            this.f24231c = z.a(context, this.f24232d) >= 1260;
            b();
            return;
        }
        com.vivo.push.util.p.c(this.f24233e, "init error : push pkgname is " + this.f24232d + " ; action is " + this.f24237i);
        this.f24231c = false;
    }

    private void d() {
        this.f24238j.removeMessages(1);
        this.f24238j.sendEmptyMessageDelayed(1, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS);
    }

    private void e() {
        this.f24238j.removeMessages(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f() {
        try {
            this.f24233e.unbindService(this);
        } catch (Exception e2) {
            com.vivo.push.util.p.a("AidlManager", "On unBindServiceException:" + e2.getMessage());
        }
    }

    @Override // android.content.ServiceConnection
    public final void onBindingDied(ComponentName componentName) {
        com.vivo.push.util.p.b("AidlManager", "onBindingDied : ".concat(String.valueOf(componentName)));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        e();
        this.f24235g = IPCInvoke.Stub.asInterface(iBinder);
        if (this.f24235g == null) {
            com.vivo.push.util.p.d("AidlManager", "onServiceConnected error : aidl must not be null.");
            f();
            this.f24234f.set(1);
            return;
        }
        if (this.f24234f.get() == 2) {
            a(4);
        } else if (this.f24234f.get() != 4) {
            f();
        }
        synchronized (this.f24236h) {
            this.f24236h.notifyAll();
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        this.f24235g = null;
        a(1);
    }

    public static b a(Context context, String str) {
        b bVar = f24230b.get(str);
        if (bVar == null) {
            synchronized (f24229a) {
                bVar = f24230b.get(str);
                if (bVar == null) {
                    bVar = new b(context, str);
                    f24230b.put(str, bVar);
                }
            }
        }
        return bVar;
    }

    private void b() {
        int i2 = this.f24234f.get();
        com.vivo.push.util.p.d("AidlManager", "Enter connect, Connection Status: ".concat(String.valueOf(i2)));
        if (i2 == 4 || i2 == 2 || i2 == 3 || i2 == 5 || !this.f24231c) {
            return;
        }
        a(2);
        if (c()) {
            d();
        } else {
            a(1);
            com.vivo.push.util.p.a("AidlManager", "bind core service fail");
        }
    }

    private boolean c() {
        Intent intent = new Intent(this.f24237i);
        intent.setPackage(this.f24232d);
        try {
            return this.f24233e.bindService(intent, this, 1);
        } catch (Exception e2) {
            com.vivo.push.util.p.a("AidlManager", "bind core error", e2);
            return false;
        }
    }

    public final boolean a() {
        String strB = t.b(this.f24233e);
        this.f24232d = strB;
        if (TextUtils.isEmpty(strB)) {
            com.vivo.push.util.p.c(this.f24233e, "push pkgname is null");
            return false;
        }
        boolean z2 = z.a(this.f24233e, this.f24232d) >= 1260;
        this.f24231c = z2;
        return z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(int i2) {
        this.f24234f.set(i2);
    }

    public final boolean a(Bundle bundle) {
        b();
        if (this.f24234f.get() == 2) {
            synchronized (this.f24236h) {
                try {
                    this.f24236h.wait(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
        try {
            int i2 = this.f24234f.get();
            if (i2 == 4) {
                this.f24238j.removeMessages(2);
                this.f24238j.sendEmptyMessageDelayed(2, 30000L);
                this.f24235g.asyncCall(bundle, null);
                return true;
            }
            com.vivo.push.util.p.d("AidlManager", "invoke error : connect status = ".concat(String.valueOf(i2)));
            return false;
        } catch (Exception e3) {
            com.vivo.push.util.p.a("AidlManager", "invoke error ", e3);
            int i3 = this.f24234f.get();
            com.vivo.push.util.p.d("AidlManager", "Enter disconnect, Connection Status: ".concat(String.valueOf(i3)));
            if (i3 == 2) {
                e();
                a(1);
                return false;
            }
            if (i3 == 3) {
                a(1);
                return false;
            }
            if (i3 != 4) {
                return false;
            }
            a(1);
            f();
            return false;
        }
    }
}
