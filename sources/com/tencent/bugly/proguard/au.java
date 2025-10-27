package com.tencent.bugly.proguard;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class au {

    /* renamed from: a, reason: collision with root package name */
    private static au f17614a;

    /* renamed from: b, reason: collision with root package name */
    private ac f17615b;

    /* renamed from: c, reason: collision with root package name */
    private aa f17616c;

    /* renamed from: d, reason: collision with root package name */
    private as f17617d;

    /* renamed from: e, reason: collision with root package name */
    private Context f17618e;

    private au(Context context) {
        at atVarA = at.a();
        if (atVarA == null) {
            return;
        }
        this.f17615b = ac.a();
        this.f17616c = aa.a(context);
        this.f17617d = atVarA.f17595s;
        this.f17618e = context;
        ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.au.1
            @Override // java.lang.Runnable
            public final void run() {
                au.a(au.this);
            }
        });
    }

    public static au a(Context context) {
        if (f17614a == null) {
            f17614a = new au(context);
        }
        return f17614a;
    }

    public static void a(final Thread thread, final int i2, final String str, final String str2, final String str3, final Map<String, String> map) {
        ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.au.2
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    if (au.f17614a == null) {
                        al.e("[ExtraCrashManager] Extra crash manager has not been initialized.", new Object[0]);
                    } else {
                        au.a(au.f17614a, thread, i2, str, str2, str3, map);
                    }
                } catch (Throwable th) {
                    if (!al.b(th)) {
                        th.printStackTrace();
                    }
                    al.e("[ExtraCrashManager] Crash error %s %s %s", str, str2, str3);
                }
            }
        });
    }

    public static /* synthetic */ void a(au auVar) {
        al.c("[ExtraCrashManager] Trying to notify Bugly agents.", new Object[0]);
        try {
            Class<?> cls = Class.forName("com.tencent.bugly.agent.GameAgent");
            auVar.f17616c.getClass();
            ap.a(cls, "sdkPackageName", "com.tencent.bugly");
            al.c("[ExtraCrashManager] Bugly game agent has been notified.", new Object[0]);
        } catch (Throwable unused) {
            al.a("[ExtraCrashManager] no game agent", new Object[0]);
        }
    }

    public static /* synthetic */ void a(au auVar, Thread thread, int i2, String str, String str2, String str3, Map map) {
        String str4;
        String str5;
        String str6;
        Thread threadCurrentThread = thread == null ? Thread.currentThread() : thread;
        if (i2 == 4) {
            str4 = "Unity";
        } else if (i2 == 5 || i2 == 6) {
            str4 = "Cocos";
        } else {
            if (i2 != 8) {
                al.d("[ExtraCrashManager] Unknown extra crash type: %d", Integer.valueOf(i2));
                return;
            }
            str4 = "H5";
        }
        al.e("[ExtraCrashManager] %s Crash Happen", str4);
        try {
            if (!auVar.f17615b.b()) {
                al.d("[ExtraCrashManager] There is no remote strategy, but still store it.", new Object[0]);
            }
            StrategyBean strategyBeanC = auVar.f17615b.c();
            if (!strategyBeanC.f17341f && auVar.f17615b.b()) {
                al.e("[ExtraCrashManager] Crash report was closed by remote. Will not upload to Bugly , print local for helpful!", new Object[0]);
                as.a(str4, ap.a(), auVar.f17616c.f17418d, threadCurrentThread.getName(), str + "\n" + str2 + "\n" + str3, null);
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
            if (i2 != 5 && i2 != 6) {
                if (i2 == 8 && !strategyBeanC.f17347l) {
                    al.e("[ExtraCrashManager] %s report is disabled.", str4);
                    al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                    return;
                }
            } else if (!strategyBeanC.f17346k) {
                al.e("[ExtraCrashManager] %s report is disabled.", str4);
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                return;
            }
            int i3 = i2 != 8 ? i2 : 5;
            CrashDetailBean crashDetailBean = new CrashDetailBean();
            crashDetailBean.C = ab.j();
            crashDetailBean.D = ab.f();
            crashDetailBean.E = ab.l();
            crashDetailBean.F = auVar.f17616c.k();
            crashDetailBean.G = auVar.f17616c.j();
            crashDetailBean.H = auVar.f17616c.l();
            crashDetailBean.I = ab.b(auVar.f17618e);
            crashDetailBean.J = ab.g();
            crashDetailBean.K = ab.h();
            crashDetailBean.f17360b = i3;
            crashDetailBean.f17363e = auVar.f17616c.g();
            aa aaVar = auVar.f17616c;
            crashDetailBean.f17364f = aaVar.f17429o;
            crashDetailBean.f17365g = aaVar.q();
            crashDetailBean.f17371m = auVar.f17616c.f();
            crashDetailBean.f17372n = String.valueOf(str);
            crashDetailBean.f17373o = String.valueOf(str2);
            str5 = "";
            if (str3 != null) {
                String[] strArrSplit = str3.split("\n");
                str5 = strArrSplit.length > 0 ? strArrSplit[0] : "";
                str6 = str3;
            } else {
                str6 = "";
            }
            crashDetailBean.f17374p = str5;
            crashDetailBean.f17375q = str6;
            crashDetailBean.f17376r = System.currentTimeMillis();
            crashDetailBean.f17379u = ap.c(crashDetailBean.f17375q.getBytes());
            crashDetailBean.f17384z = ap.a(auVar.f17616c.Q, at.f17583h);
            crashDetailBean.A = auVar.f17616c.f17418d;
            crashDetailBean.B = threadCurrentThread.getName() + "(" + threadCurrentThread.getId() + ")";
            crashDetailBean.L = auVar.f17616c.s();
            crashDetailBean.f17366h = auVar.f17616c.p();
            aa aaVar2 = auVar.f17616c;
            crashDetailBean.Q = aaVar2.f17415a;
            crashDetailBean.R = aaVar2.a();
            crashDetailBean.U = auVar.f17616c.z();
            aa aaVar3 = auVar.f17616c;
            crashDetailBean.V = aaVar3.f17438x;
            crashDetailBean.W = aaVar3.t();
            crashDetailBean.X = auVar.f17616c.y();
            crashDetailBean.f17383y = ao.a();
            if (crashDetailBean.S == null) {
                crashDetailBean.S = new LinkedHashMap();
            }
            if (map != null) {
                crashDetailBean.S.putAll(map);
            }
            as.a(str4, ap.a(), auVar.f17616c.f17418d, threadCurrentThread.getName(), str + "\n" + str2 + "\n" + str3, crashDetailBean);
            if (!auVar.f17617d.a(crashDetailBean, !at.a().C)) {
                auVar.f17617d.b(crashDetailBean, false);
            }
            al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
        } catch (Throwable th) {
            try {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
            } catch (Throwable th2) {
                al.e("[ExtraCrashManager] Successfully handled.", new Object[0]);
                throw th2;
            }
        }
    }
}
