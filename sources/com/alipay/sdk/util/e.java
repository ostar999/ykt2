package com.alipay.sdk.util;

import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import com.alipay.android.app.IAlixPay;
import com.alipay.android.app.IRemoteServiceCallback;
import com.alipay.sdk.util.l;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;

/* loaded from: classes2.dex */
public class e {

    /* renamed from: b, reason: collision with root package name */
    public static final String f3365b = "failed";

    /* renamed from: a, reason: collision with root package name */
    public Activity f3366a;

    /* renamed from: c, reason: collision with root package name */
    private IAlixPay f3367c;

    /* renamed from: e, reason: collision with root package name */
    private boolean f3369e;

    /* renamed from: f, reason: collision with root package name */
    private a f3370f;

    /* renamed from: d, reason: collision with root package name */
    private final Object f3368d = IAlixPay.class;

    /* renamed from: g, reason: collision with root package name */
    private ServiceConnection f3371g = new f(this);

    /* renamed from: h, reason: collision with root package name */
    private IRemoteServiceCallback f3372h = new g(this);

    public interface a {
        void a();

        void b();
    }

    public e(Activity activity, a aVar) {
        this.f3366a = activity;
        this.f3370f = aVar;
    }

    private String b(String str) {
        Activity activity;
        String strA;
        Activity activity2;
        Activity activity3;
        Intent intent = new Intent();
        String strA2 = l.a();
        intent.setPackage(strA2);
        intent.setAction(strA2 + ".IAlixPay");
        String strJ = l.j(this.f3366a);
        try {
            if (!this.f3366a.getApplicationContext().bindService(intent, this.f3371g, 1)) {
                throw new Throwable("bindService fail");
            }
            synchronized (this.f3368d) {
                if (this.f3367c == null) {
                    try {
                        this.f3368d.wait(com.alipay.sdk.data.a.b().a());
                    } catch (InterruptedException e2) {
                        com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.A, e2);
                    }
                }
            }
            try {
            } catch (Throwable th) {
                try {
                    com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.f3134x, th);
                    strA = com.alipay.sdk.app.i.a();
                    try {
                        this.f3367c.unregisterCallback(this.f3372h);
                    } catch (Throwable unused) {
                    }
                    try {
                        this.f3366a.getApplicationContext().unbindService(this.f3371g);
                    } catch (Throwable unused2) {
                    }
                    this.f3370f = null;
                    this.f3372h = null;
                    this.f3371g = null;
                    this.f3367c = null;
                    if (this.f3369e && (activity2 = this.f3366a) != null) {
                    }
                } catch (Throwable th2) {
                    try {
                        this.f3367c.unregisterCallback(this.f3372h);
                    } catch (Throwable unused3) {
                    }
                    try {
                        this.f3366a.getApplicationContext().unbindService(this.f3371g);
                    } catch (Throwable unused4) {
                    }
                    this.f3370f = null;
                    this.f3372h = null;
                    this.f3371g = null;
                    this.f3367c = null;
                    if (!this.f3369e || (activity = this.f3366a) == null) {
                        throw th2;
                    }
                    activity.setRequestedOrientation(0);
                    this.f3369e = false;
                    throw th2;
                }
            }
            if (this.f3367c != null) {
                if (this.f3366a.getRequestedOrientation() == 0) {
                    this.f3366a.setRequestedOrientation(1);
                    this.f3369e = true;
                }
                this.f3367c.registerCallback(this.f3372h);
                strA = this.f3367c.Pay(str);
                try {
                    this.f3367c.unregisterCallback(this.f3372h);
                } catch (Throwable unused5) {
                }
                try {
                    this.f3366a.getApplicationContext().unbindService(this.f3371g);
                } catch (Throwable unused6) {
                }
                this.f3370f = null;
                this.f3372h = null;
                this.f3371g = null;
                this.f3367c = null;
                if (this.f3369e && (activity2 = this.f3366a) != null) {
                    activity2.setRequestedOrientation(0);
                    this.f3369e = false;
                }
                return strA;
            }
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.f3131u, strJ + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + l.j(this.f3366a) + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + l.k(this.f3366a));
            try {
                this.f3367c.unregisterCallback(this.f3372h);
            } catch (Throwable unused7) {
            }
            try {
                this.f3366a.getApplicationContext().unbindService(this.f3371g);
            } catch (Throwable unused8) {
            }
            this.f3370f = null;
            this.f3372h = null;
            this.f3371g = null;
            this.f3367c = null;
            if (this.f3369e && (activity3 = this.f3366a) != null) {
                activity3.setRequestedOrientation(0);
                this.f3369e = false;
            }
            return f3365b;
        } catch (Throwable th3) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.f3136z, th3);
            return f3365b;
        }
    }

    public final String a(String str) {
        l.a aVarA;
        if (l.d(this.f3366a)) {
            return f3365b;
        }
        try {
            aVarA = l.a(this.f3366a);
        } catch (Throwable th) {
            com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.C, th);
        }
        if (aVarA.a()) {
            return f3365b;
        }
        if (aVarA.f3394b > 78) {
            String strA = l.a();
            Intent intent = new Intent();
            intent.setClassName(strA, "com.alipay.android.app.TransProcessPayActivity");
            this.f3366a.startActivity(intent);
            Thread.sleep(200L);
        }
        return b(str);
    }

    private void a(l.a aVar) throws InterruptedException {
        if (aVar == null || aVar.f3394b <= 78) {
            return;
        }
        String strA = l.a();
        Intent intent = new Intent();
        intent.setClassName(strA, "com.alipay.android.app.TransProcessPayActivity");
        this.f3366a.startActivity(intent);
        Thread.sleep(200L);
    }

    private void a() {
        this.f3366a = null;
    }
}
