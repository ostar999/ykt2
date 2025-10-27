package com.hyphenate.a;

import android.annotation.SuppressLint;
import android.net.TrafficStats;
import android.os.Process;
import com.hyphenate.util.EMLog;

@SuppressLint({"NewApi"})
/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    static final String f8490a = "net";

    /* renamed from: b, reason: collision with root package name */
    protected static a f8491b = null;

    /* renamed from: c, reason: collision with root package name */
    static long f8492c = 0;

    /* renamed from: d, reason: collision with root package name */
    static long f8493d = 0;

    /* renamed from: e, reason: collision with root package name */
    static long f8494e = 0;

    /* renamed from: f, reason: collision with root package name */
    static long f8495f = 0;

    /* renamed from: g, reason: collision with root package name */
    static long f8496g = 0;

    /* renamed from: h, reason: collision with root package name */
    static long f8497h = 0;

    /* renamed from: i, reason: collision with root package name */
    static long f8498i = 0;

    /* renamed from: j, reason: collision with root package name */
    static long f8499j = 0;

    /* renamed from: k, reason: collision with root package name */
    static long f8500k = 0;

    /* renamed from: l, reason: collision with root package name */
    static long f8501l = 0;

    /* renamed from: m, reason: collision with root package name */
    static long f8502m = 0;

    /* renamed from: n, reason: collision with root package name */
    static long f8503n = 0;

    /* renamed from: o, reason: collision with root package name */
    static long f8504o = 0;

    /* renamed from: p, reason: collision with root package name */
    static long f8505p = 0;

    /* renamed from: q, reason: collision with root package name */
    static long f8506q = 0;

    /* renamed from: r, reason: collision with root package name */
    static long f8507r = 0;

    /* renamed from: s, reason: collision with root package name */
    static int f8508s = 0;

    /* renamed from: t, reason: collision with root package name */
    static long f8509t = 0;

    /* renamed from: u, reason: collision with root package name */
    static long f8510u = 0;

    /* renamed from: v, reason: collision with root package name */
    static boolean f8511v = false;

    public static void a() {
        f8508s = Process.myUid();
        b();
        f8511v = true;
    }

    public static void b() {
        f8492c = TrafficStats.getUidRxBytes(f8508s);
        f8493d = TrafficStats.getUidTxBytes(f8508s);
        f8494e = TrafficStats.getUidRxPackets(f8508s);
        f8495f = TrafficStats.getUidTxPackets(f8508s);
        f8500k = 0L;
        f8501l = 0L;
        f8502m = 0L;
        f8503n = 0L;
        f8504o = 0L;
        f8505p = 0L;
        f8506q = 0L;
        f8507r = 0L;
        f8510u = System.currentTimeMillis();
        f8509t = System.currentTimeMillis();
    }

    public static void c() {
        f8511v = false;
        b();
    }

    public static void d() {
        if (f8511v) {
            Long lValueOf = Long.valueOf(System.currentTimeMillis());
            long jLongValue = (lValueOf.longValue() - f8509t) / 1000;
            if (jLongValue == 0) {
                jLongValue = 1;
            }
            f8504o = TrafficStats.getUidRxBytes(f8508s);
            long uidTxBytes = TrafficStats.getUidTxBytes(f8508s);
            f8505p = uidTxBytes;
            long j2 = f8504o - f8492c;
            f8500k = j2;
            long j3 = uidTxBytes - f8493d;
            f8501l = j3;
            f8496g += j2;
            f8497h += j3;
            f8506q = TrafficStats.getUidRxPackets(f8508s);
            long uidTxPackets = TrafficStats.getUidTxPackets(f8508s);
            f8507r = uidTxPackets;
            long j4 = f8506q - f8494e;
            f8502m = j4;
            long j5 = uidTxPackets - f8495f;
            f8503n = j5;
            f8498i += j4;
            f8499j += j5;
            if (f8500k == 0 && f8501l == 0) {
                EMLog.d("net", "no network traffice");
                return;
            }
            EMLog.d("net", f8501l + " bytes send; " + f8500k + " bytes received in " + jLongValue + " sec");
            if (f8503n > 0) {
                EMLog.d("net", f8503n + " packets send; " + f8502m + " packets received in " + jLongValue + " sec");
            }
            EMLog.d("net", "total:" + f8497h + " bytes send; " + f8496g + " bytes received");
            if (f8499j > 0) {
                EMLog.d("net", "total:" + f8499j + " packets send; " + f8498i + " packets received in " + ((System.currentTimeMillis() - f8510u) / 1000));
            }
            f8492c = f8504o;
            f8493d = f8505p;
            f8494e = f8506q;
            f8495f = f8507r;
            f8509t = lValueOf.longValue();
        }
    }
}
