package com.tencent.bugly.proguard;

import android.content.Context;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public final class ac {

    /* renamed from: a, reason: collision with root package name */
    public static int f17444a = 1000;

    /* renamed from: b, reason: collision with root package name */
    public static long f17445b = 259200000;

    /* renamed from: d, reason: collision with root package name */
    private static ac f17446d;

    /* renamed from: i, reason: collision with root package name */
    private static String f17447i;

    /* renamed from: c, reason: collision with root package name */
    public final ak f17448c;

    /* renamed from: e, reason: collision with root package name */
    private final List<o> f17449e;

    /* renamed from: f, reason: collision with root package name */
    private final StrategyBean f17450f;

    /* renamed from: g, reason: collision with root package name */
    private StrategyBean f17451g = null;

    /* renamed from: h, reason: collision with root package name */
    private Context f17452h;

    private ac(Context context, List<o> list) {
        this.f17452h = context;
        if (aa.a(context) != null) {
            String str = aa.a(context).H;
            if ("oversea".equals(str)) {
                StrategyBean.f17336a = "https://astat.bugly.qcloud.com/rqd/async";
                StrategyBean.f17337b = "https://astat.bugly.qcloud.com/rqd/async";
            } else if ("na_https".equals(str)) {
                StrategyBean.f17336a = "https://astat.bugly.cros.wr.pvp.net/:8180/rqd/async";
                StrategyBean.f17337b = "https://astat.bugly.cros.wr.pvp.net/:8180/rqd/async";
            }
        }
        this.f17450f = new StrategyBean();
        this.f17449e = list;
        this.f17448c = ak.a();
    }

    public static StrategyBean d() {
        byte[] bArr;
        List<y> listA = w.a().a(2);
        if (listA == null || listA.size() <= 0 || (bArr = listA.get(0).f17933g) == null) {
            return null;
        }
        return (StrategyBean) ap.a(bArr, StrategyBean.CREATOR);
    }

    public final StrategyBean c() {
        StrategyBean strategyBean = this.f17451g;
        if (strategyBean != null) {
            if (!ap.d(strategyBean.f17352q)) {
                this.f17451g.f17352q = StrategyBean.f17336a;
            }
            if (!ap.d(this.f17451g.f17353r)) {
                this.f17451g.f17353r = StrategyBean.f17337b;
            }
            return this.f17451g;
        }
        if (!ap.b(f17447i) && ap.d(f17447i)) {
            StrategyBean strategyBean2 = this.f17450f;
            String str = f17447i;
            strategyBean2.f17352q = str;
            strategyBean2.f17353r = str;
        }
        return this.f17450f;
    }

    public final synchronized boolean b() {
        return this.f17451g != null;
    }

    public static synchronized ac a(Context context, List<o> list) {
        if (f17446d == null) {
            f17446d = new ac(context, list);
        }
        return f17446d;
    }

    public static synchronized ac a() {
        return f17446d;
    }

    public final void a(StrategyBean strategyBean, boolean z2) {
        al.c("[Strategy] Notify %s", s.class.getName());
        s.a(strategyBean, z2);
        for (o oVar : this.f17449e) {
            try {
                al.c("[Strategy] Notify %s", oVar.getClass().getName());
                oVar.onServerStrategyChanged(strategyBean);
            } catch (Throwable th) {
                if (!al.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    public static void a(String str) {
        if (!ap.b(str) && ap.d(str)) {
            f17447i = str;
        } else {
            al.d("URL user set is invalid.", new Object[0]);
        }
    }

    public final void a(bt btVar) throws NumberFormatException {
        if (btVar == null) {
            return;
        }
        StrategyBean strategyBean = this.f17451g;
        if (strategyBean == null || btVar.f17791h != strategyBean.f17350o) {
            StrategyBean strategyBean2 = new StrategyBean();
            strategyBean2.f17341f = btVar.f17784a;
            strategyBean2.f17343h = btVar.f17786c;
            strategyBean2.f17342g = btVar.f17785b;
            if (ap.b(f17447i) || !ap.d(f17447i)) {
                if (ap.d(btVar.f17787d)) {
                    al.c("[Strategy] Upload url changes to %s", btVar.f17787d);
                    strategyBean2.f17352q = btVar.f17787d;
                }
                if (ap.d(btVar.f17788e)) {
                    al.c("[Strategy] Exception upload url changes to %s", btVar.f17788e);
                    strategyBean2.f17353r = btVar.f17788e;
                }
            }
            bs bsVar = btVar.f17789f;
            if (bsVar != null && !ap.b(bsVar.f17779a)) {
                strategyBean2.f17354s = btVar.f17789f.f17779a;
            }
            long j2 = btVar.f17791h;
            if (j2 != 0) {
                strategyBean2.f17350o = j2;
            }
            Map<String, String> map = btVar.f17790g;
            if (map != null && map.size() > 0) {
                Map<String, String> map2 = btVar.f17790g;
                strategyBean2.f17355t = map2;
                String str = map2.get("B11");
                strategyBean2.f17344i = str != null && str.equals("1");
                String str2 = btVar.f17790g.get("B3");
                if (str2 != null) {
                    strategyBean2.f17358w = Long.parseLong(str2);
                }
                int i2 = btVar.f17795l;
                strategyBean2.f17351p = i2;
                strategyBean2.f17357v = i2;
                String str3 = btVar.f17790g.get("B27");
                if (str3 != null && str3.length() > 0) {
                    try {
                        int i3 = Integer.parseInt(str3);
                        if (i3 > 0) {
                            strategyBean2.f17356u = i3;
                        }
                    } catch (Exception e2) {
                        if (!al.a(e2)) {
                            e2.printStackTrace();
                        }
                    }
                }
                String str4 = btVar.f17790g.get("B25");
                strategyBean2.f17346k = str4 != null && str4.equals("1");
            }
            al.a("[Strategy] enableCrashReport:%b, enableQuery:%b, enableUserInfo:%b, enableAnr:%b, enableBlock:%b, enableSession:%b, enableSessionTimer:%b, sessionOverTime:%d, enableCocos:%b, strategyLastUpdateTime:%d", Boolean.valueOf(strategyBean2.f17341f), Boolean.valueOf(strategyBean2.f17343h), Boolean.valueOf(strategyBean2.f17342g), Boolean.valueOf(strategyBean2.f17344i), Boolean.valueOf(strategyBean2.f17345j), Boolean.valueOf(strategyBean2.f17348m), Boolean.valueOf(strategyBean2.f17349n), Long.valueOf(strategyBean2.f17351p), Boolean.valueOf(strategyBean2.f17346k), Long.valueOf(strategyBean2.f17350o));
            this.f17451g = strategyBean2;
            if (!ap.d(btVar.f17787d)) {
                al.c("[Strategy] download url is null", new Object[0]);
                this.f17451g.f17352q = "";
            }
            if (!ap.d(btVar.f17788e)) {
                al.c("[Strategy] download crashurl is null", new Object[0]);
                this.f17451g.f17353r = "";
            }
            w.a().b(2);
            y yVar = new y();
            yVar.f17928b = 2;
            yVar.f17927a = strategyBean2.f17339d;
            yVar.f17931e = strategyBean2.f17340e;
            yVar.f17933g = ap.a(strategyBean2);
            w.a().a(yVar);
            a(strategyBean2, true);
        }
    }
}
