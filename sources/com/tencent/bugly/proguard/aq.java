package com.tencent.bugly.proguard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/* loaded from: classes6.dex */
public final class aq extends BroadcastReceiver {

    /* renamed from: d, reason: collision with root package name */
    private static aq f17547d;

    /* renamed from: b, reason: collision with root package name */
    private Context f17549b;

    /* renamed from: c, reason: collision with root package name */
    private String f17550c;

    /* renamed from: e, reason: collision with root package name */
    private boolean f17551e = true;

    /* renamed from: a, reason: collision with root package name */
    private IntentFilter f17548a = new IntentFilter();

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        try {
            a(context, intent);
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public static synchronized aq a() {
        if (f17547d == null) {
            f17547d = new aq();
        }
        return f17547d;
    }

    public final synchronized void b(Context context) {
        try {
            al.a(aq.class, "Unregister broadcast receiver of Bugly.", new Object[0]);
            context.unregisterReceiver(this);
            this.f17549b = context;
        } catch (Throwable th) {
            if (al.a(th)) {
                return;
            }
            th.printStackTrace();
        }
    }

    public final synchronized void a(String str) {
        if (!this.f17548a.hasAction(str)) {
            this.f17548a.addAction(str);
        }
        al.c("add action %s", str);
    }

    public final synchronized void a(Context context) {
        this.f17549b = context;
        ap.a(new Runnable() { // from class: com.tencent.bugly.proguard.aq.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    al.a(aq.f17547d.getClass(), "Register broadcast receiver of Bugly.", new Object[0]);
                    synchronized (this) {
                        aq.this.f17549b.registerReceiver(aq.f17547d, aq.this.f17548a, "com.tencent.bugly.BuglyBroadcastReceiver.permission", null);
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                }
            }
        });
    }

    private synchronized boolean a(Context context, Intent intent) {
        if (context != null && intent != null) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                if (this.f17551e) {
                    this.f17551e = false;
                    return true;
                }
                String strC = ab.c(this.f17549b);
                al.c("is Connect BC ".concat(String.valueOf(strC)), new Object[0]);
                al.a("network %s changed to %s", this.f17550c, String.valueOf(strC));
                if (strC == null) {
                    this.f17550c = null;
                    return true;
                }
                String str = this.f17550c;
                this.f17550c = strC;
                long jCurrentTimeMillis = System.currentTimeMillis();
                ac acVarA = ac.a();
                ai aiVarA = ai.a();
                aa aaVarA = aa.a(context);
                if (acVarA != null && aiVarA != null && aaVarA != null) {
                    if (!strC.equals(str) && jCurrentTimeMillis - aiVarA.a(at.f17577a) > 30000) {
                        al.a("try to upload crash on network changed.", new Object[0]);
                        at atVarA = at.a();
                        if (atVarA != null) {
                            atVarA.a(0L);
                        }
                        al.a("try to upload userinfo on network changed.", new Object[0]);
                        s.f17870b.b();
                    }
                    return true;
                }
                al.d("not inited BC not work", new Object[0]);
                return true;
            }
        }
        return false;
    }
}
