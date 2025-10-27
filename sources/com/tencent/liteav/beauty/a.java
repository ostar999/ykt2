package com.tencent.liteav.beauty;

import android.content.Context;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public class a {
    private static boolean A = false;
    private static boolean B = false;
    private static boolean C = false;
    private static boolean D = false;
    private static boolean E = false;
    private static boolean F = false;

    /* renamed from: a, reason: collision with root package name */
    private static a f18755a = null;

    /* renamed from: b, reason: collision with root package name */
    private static Context f18756b = null;

    /* renamed from: c, reason: collision with root package name */
    private static boolean f18757c = false;

    /* renamed from: d, reason: collision with root package name */
    private static boolean f18758d = false;

    /* renamed from: e, reason: collision with root package name */
    private static boolean f18759e = false;

    /* renamed from: f, reason: collision with root package name */
    private static boolean f18760f = false;

    /* renamed from: g, reason: collision with root package name */
    private static boolean f18761g = false;

    /* renamed from: h, reason: collision with root package name */
    private static boolean f18762h = false;

    /* renamed from: i, reason: collision with root package name */
    private static boolean f18763i = false;

    /* renamed from: j, reason: collision with root package name */
    private static boolean f18764j = false;

    /* renamed from: k, reason: collision with root package name */
    private static boolean f18765k = false;

    /* renamed from: l, reason: collision with root package name */
    private static boolean f18766l = false;

    /* renamed from: m, reason: collision with root package name */
    private static boolean f18767m = false;

    /* renamed from: n, reason: collision with root package name */
    private static boolean f18768n = false;

    /* renamed from: o, reason: collision with root package name */
    private static boolean f18769o = false;

    /* renamed from: p, reason: collision with root package name */
    private static boolean f18770p = false;

    /* renamed from: q, reason: collision with root package name */
    private static boolean f18771q = false;

    /* renamed from: r, reason: collision with root package name */
    private static boolean f18772r = false;

    /* renamed from: s, reason: collision with root package name */
    private static boolean f18773s = false;

    /* renamed from: t, reason: collision with root package name */
    private static boolean f18774t = false;

    /* renamed from: u, reason: collision with root package name */
    private static boolean f18775u = false;

    /* renamed from: v, reason: collision with root package name */
    private static boolean f18776v = false;

    /* renamed from: w, reason: collision with root package name */
    private static boolean f18777w = false;

    /* renamed from: x, reason: collision with root package name */
    private static boolean f18778x = false;

    /* renamed from: y, reason: collision with root package name */
    private static boolean f18779y = false;

    /* renamed from: z, reason: collision with root package name */
    private static boolean f18780z = false;
    private String G = "ReportDuaManage";

    public static a a() {
        if (f18755a == null) {
            f18755a = new a();
        }
        return f18755a;
    }

    private void h() {
        TXCLog.i(this.G, "resetReportState");
        f18757c = false;
        f18758d = false;
        f18759e = false;
        f18760f = false;
        f18761g = false;
        f18762h = false;
        f18763i = false;
        f18764j = false;
        f18765k = false;
        f18766l = false;
        f18767m = false;
        f18768n = false;
        C = false;
        f18769o = false;
        f18770p = false;
        f18771q = false;
        f18772r = false;
        f18773s = false;
        f18774t = false;
        f18775u = false;
        f18776v = false;
        f18777w = false;
        f18778x = false;
        f18779y = false;
        f18780z = false;
        A = false;
        B = false;
        D = false;
        E = false;
        F = false;
    }

    public void b() {
        if (!f18758d) {
            TXCLog.i(this.G, "reportBeautyDua");
            TXCDRApi.txReportDAU(f18756b, 1202, 0, "reportBeautyDua");
        }
        f18758d = true;
    }

    public void c() {
        if (!f18759e) {
            TXCLog.i(this.G, "reportWhiteDua");
            TXCDRApi.txReportDAU(f18756b, 1203, 0, "reportWhiteDua");
        }
        f18759e = true;
    }

    public void d() {
        if (!f18760f) {
            TXCLog.i(this.G, "reportRuddyDua");
            TXCDRApi.txReportDAU(f18756b, 1204, 0, "reportRuddyDua");
        }
        f18760f = true;
    }

    public void e() {
        if (!f18764j) {
            TXCLog.i(this.G, "reportFilterImageDua");
            TXCDRApi.txReportDAU(f18756b, R2.attr.crossfade, 0, "reportFilterImageDua");
        }
        f18764j = true;
    }

    public void f() {
        if (!f18766l) {
            TXCLog.i(this.G, "reportSharpDua");
            TXCDRApi.txReportDAU(f18756b, R2.attr.ct_clickTextBgColor, 0, "reportSharpDua");
        }
        f18766l = true;
    }

    public void g() {
        if (!f18768n) {
            TXCLog.i(this.G, "reportWarterMarkDua");
            TXCDRApi.txReportDAU(f18756b, R2.attr.ct_clickable, 0, "reportWarterMarkDua");
        }
        f18768n = true;
    }

    public void a(Context context) {
        h();
        f18756b = context.getApplicationContext();
        if (!f18757c) {
            TXCLog.i(this.G, "reportSDKInit");
            TXCDRApi.txReportDAU(f18756b, 1201, 0, "reportSDKInit!");
        }
        f18757c = true;
    }
}
