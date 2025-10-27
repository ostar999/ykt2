package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.proguard.ag;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public final class at {
    private static at D = null;

    /* renamed from: a, reason: collision with root package name */
    public static int f17577a = 0;

    /* renamed from: b, reason: collision with root package name */
    public static boolean f17578b = false;

    /* renamed from: d, reason: collision with root package name */
    public static int f17579d = 2;

    /* renamed from: e, reason: collision with root package name */
    public static boolean f17580e = false;

    /* renamed from: f, reason: collision with root package name */
    public static int f17581f = 20480;

    /* renamed from: g, reason: collision with root package name */
    public static int f17582g = 3000;

    /* renamed from: h, reason: collision with root package name */
    public static int f17583h = 20480;

    /* renamed from: i, reason: collision with root package name */
    public static long f17584i = 209715200;

    /* renamed from: j, reason: collision with root package name */
    public static long f17585j = 604800000;

    /* renamed from: k, reason: collision with root package name */
    public static String f17586k = null;

    /* renamed from: l, reason: collision with root package name */
    public static boolean f17587l = false;

    /* renamed from: m, reason: collision with root package name */
    public static String f17588m = null;

    /* renamed from: n, reason: collision with root package name */
    public static int f17589n = 5000;

    /* renamed from: o, reason: collision with root package name */
    public static boolean f17590o = true;

    /* renamed from: p, reason: collision with root package name */
    public static boolean f17591p = false;

    /* renamed from: q, reason: collision with root package name */
    public static String f17592q;

    /* renamed from: r, reason: collision with root package name */
    public static String f17593r;
    public Boolean A;
    public int B = 31;
    public boolean C = false;

    /* renamed from: c, reason: collision with root package name */
    public final Context f17594c;

    /* renamed from: s, reason: collision with root package name */
    public final as f17595s;

    /* renamed from: t, reason: collision with root package name */
    public final av f17596t;

    /* renamed from: u, reason: collision with root package name */
    public final NativeCrashHandler f17597u;

    /* renamed from: v, reason: collision with root package name */
    public final ac f17598v;

    /* renamed from: w, reason: collision with root package name */
    public final ak f17599w;

    /* renamed from: x, reason: collision with root package name */
    public final ay f17600x;

    /* renamed from: y, reason: collision with root package name */
    public BuglyStrategy.a f17601y;

    /* renamed from: z, reason: collision with root package name */
    public aw f17602z;

    private at(Context context, ak akVar, boolean z2, BuglyStrategy.a aVar) {
        f17577a = 1004;
        Context contextA = ap.a(context);
        this.f17594c = contextA;
        ac acVarA = ac.a();
        this.f17598v = acVarA;
        this.f17599w = akVar;
        this.f17601y = aVar;
        this.f17602z = null;
        as asVar = new as(contextA, ai.a(), w.a(), acVarA, aVar);
        this.f17595s = asVar;
        aa aaVarA = aa.a(contextA);
        this.f17596t = new av(contextA, asVar, acVarA, aaVarA);
        NativeCrashHandler nativeCrashHandler = NativeCrashHandler.getInstance(contextA, aaVarA, asVar, acVarA, akVar, z2, null);
        this.f17597u = nativeCrashHandler;
        aaVarA.N = nativeCrashHandler;
        if (ay.f17643f == null) {
            ay.f17643f = new ay(contextA, acVarA, aaVarA, akVar, asVar);
        }
        this.f17600x = ay.f17643f;
    }

    public final synchronized void c() {
        this.f17596t.b();
        d();
        g();
    }

    public final void d() {
        this.f17597u.setUserOpened(false);
    }

    public final void e() {
        this.f17597u.setUserOpened(true);
    }

    public final void f() {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.bugly.proguard.at.1
            @Override // java.lang.Runnable
            public final void run() {
                NativeCrashHandler.getInstance().unBlockSigquit(true);
            }
        });
        this.f17600x.b(true);
    }

    public final void g() {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.tencent.bugly.proguard.at.2
            @Override // java.lang.Runnable
            public final void run() {
                NativeCrashHandler.getInstance().unBlockSigquit(false);
            }
        });
        this.f17600x.b(false);
    }

    public final synchronized void h() {
        int i2 = 0;
        while (true) {
            int i3 = i2 + 1;
            if (i2 < 30) {
                try {
                    al.a("try main sleep for make a test anr! try:%d/30 , kill it if you don't want to wait!", Integer.valueOf(i3));
                    ap.b(5000L);
                    i2 = i3;
                } catch (Throwable th) {
                    if (al.a(th)) {
                        return;
                    }
                    th.printStackTrace();
                    return;
                }
            }
        }
    }

    public final boolean i() {
        return this.f17600x.f17644a.get();
    }

    public final boolean j() {
        return (this.B & 16) > 0;
    }

    public final boolean k() {
        return (this.B & 8) > 0;
    }

    public static synchronized at a(Context context, boolean z2, BuglyStrategy.a aVar) {
        if (D == null) {
            D = new at(context, ak.a(), z2, aVar);
        }
        return D;
    }

    public final synchronized void b() {
        this.f17596t.a();
        e();
        f();
    }

    public static synchronized at a() {
        return D;
    }

    public final synchronized void a(boolean z2, boolean z3, boolean z4) {
        this.f17597u.testNativeCrash(z2, z3, z4);
    }

    public final void a(CrashDetailBean crashDetailBean) {
        this.f17595s.b(crashDetailBean);
    }

    public final void a(long j2) {
        ak.a().a(new Thread() { // from class: com.tencent.bugly.proguard.at.4
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                List<CrashDetailBean> list;
                if (!ap.a(at.this.f17594c, "local_crash_lock")) {
                    al.c("Failed to lock file for uploading local crash.", new Object[0]);
                    return;
                }
                ag agVar = ag.a.f17461a;
                List<ag.b> listA = ag.a();
                if (listA == null || listA.isEmpty()) {
                    al.c("sla local data is null", new Object[0]);
                } else {
                    al.c("sla load local data list size:%s", Integer.valueOf(listA.size()));
                    Iterator<ag.b> it = listA.iterator();
                    ArrayList arrayList = new ArrayList();
                    while (it.hasNext()) {
                        ag.b next = it.next();
                        if (next.f17463b < ap.b() - 604800000) {
                            al.c("sla local data is expired:%s", next.f17464c);
                            arrayList.add(next);
                            it.remove();
                        }
                    }
                    ag.d(arrayList);
                    agVar.b(listA);
                }
                List<CrashDetailBean> listA2 = as.a();
                if (listA2 == null || listA2.size() <= 0) {
                    al.c("no crash need to be uploaded at this start", new Object[0]);
                } else {
                    al.c("Size of crash list: %s", Integer.valueOf(listA2.size()));
                    int size = listA2.size();
                    if (size > 20) {
                        ArrayList arrayList2 = new ArrayList();
                        Collections.sort(listA2);
                        for (int i2 = 0; i2 < 20; i2++) {
                            arrayList2.add(listA2.get((size - 1) - i2));
                        }
                        list = arrayList2;
                    } else {
                        list = listA2;
                    }
                    at.this.f17595s.a(list, 0L, false, false, false);
                }
                ap.b(at.this.f17594c, "local_crash_lock");
            }
        }, j2);
    }
}
