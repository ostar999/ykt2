package com.xiaomi.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.HandlerThread;
import android.os.Message;

/* loaded from: classes6.dex */
public class cg {

    /* renamed from: a, reason: collision with root package name */
    private static final long f24682a;

    /* renamed from: a, reason: collision with other field name */
    private static final Object f255a;

    /* renamed from: a, reason: collision with other field name */
    private BroadcastReceiver f256a = new cl(this);

    /* renamed from: a, reason: collision with other field name */
    private Context f257a;

    /* renamed from: a, reason: collision with other field name */
    private ConnectivityManager f258a;

    /* renamed from: a, reason: collision with other field name */
    private HandlerThread f259a;

    /* renamed from: a, reason: collision with other field name */
    private cc f260a;

    /* renamed from: a, reason: collision with other field name */
    private co f261a;

    /* renamed from: a, reason: collision with other field name */
    private cp f262a;

    static {
        bl.a();
        f24682a = bl.m239a() ? 30000L : 1800000L;
        f255a = new Object();
    }

    public cg(Context context) {
        this.f257a = context;
    }

    private int a() {
        try {
            return ((bk) this.f257a).m235a();
        } catch (Exception unused) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z2) {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo = null;
        try {
            Context context = this.f257a;
            if (context != null && context.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.f257a.getPackageName()) == 0 && (connectivityManager = this.f258a) != null) {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            }
        } catch (Exception unused) {
        }
        if (this.f260a == null) {
            return;
        }
        if (activeNetworkInfo == null || activeNetworkInfo.getType() != 1 || !activeNetworkInfo.isConnected()) {
            this.f260a.d();
            return;
        }
        String strA = cj.a(this.f257a, 1);
        if (this.f260a.m274a() == null || !this.f260a.m274a().equals(strA)) {
            this.f260a.a(strA);
        }
        if (this.f261a.hasMessages(2)) {
            this.f261a.removeMessages(2);
        }
        Message messageObtainMessage = this.f261a.obtainMessage(2);
        long j2 = f24682a;
        messageObtainMessage.obj = Boolean.valueOf(z2);
        if (z2) {
            this.f261a.sendMessage(messageObtainMessage);
        } else {
            this.f261a.sendMessageDelayed(messageObtainMessage, j2);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private boolean m283a() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jA = this.f260a.a();
        long jC = bl.a().c();
        if (jC == Long.MAX_VALUE) {
            jC = f24682a;
        }
        String strM274a = this.f260a.m274a();
        return strM274a != null && strM274a.equals(cj.a(this.f257a, 1)) && jCurrentTimeMillis - jA >= jC;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(boolean z2) {
        if (bl.a().m245b()) {
            if (z2 || (m283a() && c() && b())) {
                e();
                this.f260a.m277c();
                this.f260a.e();
            }
        }
    }

    private boolean b() {
        if (!bl.a().m246c()) {
            return true;
        }
        long jB = bl.a().b();
        if (jB == Long.MAX_VALUE) {
            jB = 172800000;
        }
        this.f260a.m276b();
        return this.f260a.b() > jB;
    }

    private boolean c() {
        long jC = this.f260a.c();
        long jM240a = bl.a().m240a();
        if (jM240a == Long.MAX_VALUE) {
            jM240a = 172800000;
        }
        return System.currentTimeMillis() - jC > jM240a;
    }

    private void e() {
        this.f262a.a(this.f260a.m274a(), this.f260a.a(), this.f260a.b());
    }

    private void f() {
        this.f257a.registerReceiver(this.f256a, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private void g() {
        if (this.f261a.hasMessages(1)) {
            this.f261a.removeMessages(1);
        }
        if (this.f261a.hasMessages(2)) {
            this.f261a.removeMessages(2);
        }
        this.f257a.unregisterReceiver(this.f256a);
    }

    /* renamed from: a, reason: collision with other method in class */
    public void m284a() {
        a(true);
    }

    public void a(cp cpVar) {
        synchronized (f255a) {
            this.f262a = cpVar;
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public void m285b() {
        this.f260a = new cc(this.f257a);
        this.f258a = (ConnectivityManager) this.f257a.getSystemService("connectivity");
        HandlerThread handlerThread = new HandlerThread("WifiCampStatics");
        this.f259a = handlerThread;
        handlerThread.start();
        this.f261a = new co(this, this.f259a.getLooper());
        if (a() == 0) {
            f();
        }
    }

    /* renamed from: c, reason: collision with other method in class */
    public void m286c() {
        if (a() == 0) {
            g();
        }
        this.f258a = null;
        this.f260a.m275a();
        HandlerThread handlerThread = this.f259a;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            this.f259a = null;
        }
    }

    public void d() {
        synchronized (f255a) {
            this.f262a = null;
        }
    }
}
