package com.alibaba.sdk.android.httpdns.log;

import android.util.Log;
import com.alibaba.sdk.android.httpdns.ILogger;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class HttpDnsLog {

    /* renamed from: b, reason: collision with root package name */
    private static HashSet<ILogger> f2814b = new HashSet<>();

    /* renamed from: h, reason: collision with root package name */
    private static boolean f2815h = false;

    private static void c(Throwable th) {
        if (f2814b.size() > 0) {
            Iterator<ILogger> it = f2814b.iterator();
            while (it.hasNext()) {
                it.next().log(Log.getStackTraceString(th));
            }
        }
    }

    public static void d(String str) {
        if (f2815h) {
            Log.d("httpdns", str);
        }
        if (f2814b.size() > 0) {
            Iterator<ILogger> it = f2814b.iterator();
            while (it.hasNext()) {
                it.next().log("[D]" + str);
            }
        }
    }

    public static void e(String str) {
        if (f2815h) {
            Log.e("httpdns", str);
        }
        if (f2814b.size() > 0) {
            Iterator<ILogger> it = f2814b.iterator();
            while (it.hasNext()) {
                it.next().log("[E]" + str);
            }
        }
    }

    public static void e(String str, Throwable th) {
        if (f2815h) {
            Log.e("httpdns", str, th);
        }
        if (f2814b.size() > 0) {
            Iterator<ILogger> it = f2814b.iterator();
            while (it.hasNext()) {
                it.next().log("[E]" + str);
            }
            c(th);
        }
    }

    public static void enable(boolean z2) {
        f2815h = z2;
    }

    public static void i(String str) {
        if (f2815h) {
            Log.i("httpdns", str);
        }
        if (f2814b.size() > 0) {
            Iterator<ILogger> it = f2814b.iterator();
            while (it.hasNext()) {
                it.next().log("[I]" + str);
            }
        }
    }

    public static void removeLogger(ILogger iLogger) {
        if (iLogger != null) {
            f2814b.remove(iLogger);
        }
    }

    public static void setLogger(ILogger iLogger) {
        if (iLogger != null) {
            f2814b.add(iLogger);
        }
    }

    public static void w(String str) {
        if (f2815h) {
            Log.w("httpdns", str);
        }
        if (f2814b.size() > 0) {
            Iterator<ILogger> it = f2814b.iterator();
            while (it.hasNext()) {
                it.next().log("[W]" + str);
            }
        }
    }

    public static void w(String str, Throwable th) {
        if (f2815h) {
            Log.e("httpdns", str, th);
        }
        if (f2814b.size() > 0) {
            Iterator<ILogger> it = f2814b.iterator();
            while (it.hasNext()) {
                it.next().log("[W]" + str);
            }
            c(th);
        }
    }
}
