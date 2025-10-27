package com.tencent.open.log;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.connect.common.Constants;
import com.tencent.open.log.d;
import java.io.File;

/* loaded from: classes6.dex */
public class SLog implements TraceLevel {
    public static final String TAG = "openSDK_LOG";

    /* renamed from: c, reason: collision with root package name */
    private static boolean f20592c = false;
    public static SLog instance;

    /* renamed from: a, reason: collision with root package name */
    protected a f20593a = new a(new b(a(), c.f20633m, c.f20627g, c.f20628h, c.f20623c, c.f20629i, 10, c.f20625e, c.f20634n));

    /* renamed from: b, reason: collision with root package name */
    private Tracer f20594b;

    private SLog() {
    }

    public static final void d(String str, String str2) {
        getInstance().a(2, str, str2, null);
    }

    public static final void e(String str, String str2) {
        getInstance().a(16, str, str2, null);
    }

    public static void flushLogs() {
        getInstance().c();
    }

    public static SLog getInstance() {
        if (instance == null) {
            synchronized (SLog.class) {
                if (instance == null) {
                    instance = new SLog();
                    f20592c = true;
                }
            }
        }
        return instance;
    }

    public static final void i(String str, String str2) {
        getInstance().a(4, str, str2, null);
    }

    public static void release() {
        synchronized (SLog.class) {
            getInstance().b();
            if (instance != null) {
                instance = null;
            }
        }
    }

    public static final void u(String str, String str2) {
        getInstance().a(32, str, str2, null);
    }

    public static final void v(String str, String str2) {
        getInstance().a(1, str, str2, null);
    }

    public static final void w(String str, String str2) {
        getInstance().a(8, str, str2, null);
    }

    public void a(int i2, String str, String str2, Throwable th) {
        if (f20592c) {
            String strB = com.tencent.open.utils.f.b();
            if (!TextUtils.isEmpty(strB)) {
                String str3 = strB + " SDK_VERSION:" + Constants.SDK_VERSION;
                if (this.f20593a == null) {
                    return;
                }
                e.f20639a.a(32, Thread.currentThread(), System.currentTimeMillis(), TAG, str3, null);
                this.f20593a.a(32, Thread.currentThread(), System.currentTimeMillis(), TAG, str3, null);
                f20592c = false;
            }
        }
        e.f20639a.a(i2, Thread.currentThread(), System.currentTimeMillis(), str, str2, th);
        if (d.a.a(c.f20622b, i2)) {
            a aVar = this.f20593a;
            if (aVar == null) {
                return;
            } else {
                aVar.a(i2, Thread.currentThread(), System.currentTimeMillis(), str, str2, th);
            }
        }
        Tracer tracer = this.f20594b;
        if (tracer != null) {
            try {
                tracer.a(i2, Thread.currentThread(), System.currentTimeMillis(), str, a(str2), th);
            } catch (Exception e2) {
                Log.e(str, "Exception", e2);
            }
        }
    }

    public void b() {
        a aVar = this.f20593a;
        if (aVar != null) {
            aVar.a();
            this.f20593a.b();
            this.f20593a = null;
        }
    }

    public void c() {
        a aVar = this.f20593a;
        if (aVar != null) {
            aVar.a();
        }
    }

    public void setCustomLogger(Tracer tracer) {
        this.f20594b = tracer;
    }

    public static final void d(String str, String str2, Throwable th) {
        getInstance().a(2, str, str2, th);
    }

    public static final void e(String str, String str2, Throwable th) {
        getInstance().a(16, str, str2, th);
    }

    public static final void i(String str, String str2, Throwable th) {
        getInstance().a(4, str, str2, th);
    }

    public static final void u(String str, String str2, Throwable th) {
        getInstance().a(32, str, str2, th);
    }

    public static final void v(String str, String str2, Throwable th) {
        getInstance().a(1, str, str2, th);
    }

    public static final void w(String str, String str2, Throwable th) {
        getInstance().a(8, str, str2, th);
    }

    private String a(String str) {
        return TextUtils.isEmpty(str) ? "" : d.a(str) ? "xxxxxx" : str;
    }

    public static File a() {
        String str = c.f20624d;
        try {
            d.c cVarB = d.b.b();
            if (cVarB != null && cVarB.c() > c.f20626f) {
                return new File(Environment.getExternalStorageDirectory(), str);
            }
            return new File(com.tencent.open.utils.f.c(), str);
        } catch (Throwable th) {
            e(TAG, "getLogFilePath:", th);
            return null;
        }
    }
}
