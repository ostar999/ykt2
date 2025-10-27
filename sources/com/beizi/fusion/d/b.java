package com.beizi.fusion.d;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.g.ac;
import com.beizi.fusion.g.af;
import com.beizi.fusion.g.ag;
import com.beizi.fusion.g.aq;
import com.beizi.fusion.g.ar;
import com.beizi.fusion.g.b;
import com.beizi.fusion.model.AppEventId;
import com.beizi.fusion.model.RequestInfo;
import com.beizi.fusion.model.ResponseInfo;

/* loaded from: classes2.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static String f4907a = null;

    /* renamed from: b, reason: collision with root package name */
    public static String f4908b = "";

    /* renamed from: c, reason: collision with root package name */
    private static String f4909c = "AdManager";

    /* renamed from: d, reason: collision with root package name */
    private static b f4910d;

    /* renamed from: e, reason: collision with root package name */
    private static String f4911e;

    /* renamed from: j, reason: collision with root package name */
    private static String f4912j;

    /* renamed from: k, reason: collision with root package name */
    private static String f4913k;

    /* renamed from: f, reason: collision with root package name */
    private Context f4914f;

    /* renamed from: h, reason: collision with root package name */
    private com.beizi.fusion.update.b f4916h;

    /* renamed from: i, reason: collision with root package name */
    private com.beizi.fusion.b.d f4917i;

    /* renamed from: g, reason: collision with root package name */
    private boolean f4915g = false;

    /* renamed from: l, reason: collision with root package name */
    private boolean f4918l = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        com.beizi.fusion.g.q.a().a(this.f4914f);
        ResponseInfo.getInstance(this.f4914f).init();
        this.f4917i.f4803a.a(1);
        com.beizi.fusion.update.b bVarA = com.beizi.fusion.update.b.a(this.f4914f);
        this.f4916h = bVarA;
        bVarA.b(0);
        if (!ag.b() || (!BeiZis.isLimitPersonalAds() && (BeiZis.getCustomController() == null || (BeiZis.getCustomController() != null && BeiZis.getCustomController().isCanUseOaid())))) {
            String str = (String) aq.b(this.f4914f, "__OAID__", "");
            if (!TextUtils.isEmpty(str) && RequestInfo.getInstance(this.f4914f).getDevInfo() != null) {
                RequestInfo.getInstance(this.f4914f).getDevInfo().setOaid(str);
            }
            j();
            k();
        }
        if (!ag.b() || (!BeiZis.isLimitPersonalAds() && (BeiZis.getCustomController() == null || (BeiZis.getCustomController() != null && BeiZis.getCustomController().isCanUseGaid())))) {
            com.beizi.fusion.g.h.b().c().execute(new Runnable() { // from class: com.beizi.fusion.d.b.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        b.a aVarA = com.beizi.fusion.g.b.a(b.this.f4914f);
                        String strA = aVarA.a();
                        boolean zB = aVarA.b();
                        aq.a(b.this.f4914f, "__GAID__", (Object) strA);
                        aq.a(b.this.f4914f, "isLimitTrackGaid", Boolean.valueOf(zB));
                        if (zB) {
                            ac.b(b.f4909c, "User has opted not to use the advertising Id");
                        } else {
                            RequestInfo.getInstance(b.this.f4914f).getDevInfo().setGaid(strA);
                            ac.b(b.f4909c, "advertising id is " + strA);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            });
        }
        if (this.f4917i.f4803a.a() == 1) {
            this.f4917i.f4803a.a(2);
        } else {
            Log.i("BeiZis", "init status error not kInitStatusBegin");
        }
    }

    private void j() {
        try {
            new com.beizi.fusion.e.a.b(af.f5100b).a(this.f4914f);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void k() {
        try {
            if ("0".equals(ResponseInfo.getInstance(this.f4914f).getSmFlag())) {
                int iIntValue = ((Integer) aq.b(this.f4914f, "SM_STATUS", 0)).intValue();
                int i2 = 1;
                if (iIntValue == 1) {
                    aq.a(this.f4914f, "SM_STATUS", (Object) 3);
                    aq.a(this.f4914f, "SM_STATUS_EXPIRE_TIME", Long.valueOf(System.currentTimeMillis()));
                    iIntValue = 3;
                }
                if (iIntValue == 0 || iIntValue == 2) {
                    aq.a(this.f4914f, "SM_STATUS", (Object) 1);
                } else {
                    i2 = iIntValue;
                }
                if (i2 != 3) {
                    com.beizi.fusion.sm.a.b.a((Application) this.f4914f);
                    if (com.beizi.fusion.sm.a.a.a(this.f4914f)) {
                        com.beizi.fusion.sm.a.a.a(this.f4914f, new com.beizi.fusion.sm.a.c() { // from class: com.beizi.fusion.d.b.3
                            @Override // com.beizi.fusion.sm.a.c
                            public void a(Exception exc) {
                            }

                            @Override // com.beizi.fusion.sm.a.c
                            public void a(String str) {
                                Log.e(b.f4909c, "code sm Oaid:" + str);
                                if (TextUtils.isEmpty(str)) {
                                    return;
                                }
                                aq.a(b.this.f4914f, "__OAID__", (Object) str);
                                aq.a(b.this.f4914f, "__SMOAID__", (Object) str);
                                if (RequestInfo.getInstance(b.this.f4914f).getDevInfo() != null) {
                                    if (ag.b() && BeiZis.isLimitPersonalAds()) {
                                        return;
                                    }
                                    RequestInfo.getInstance(b.this.f4914f).getDevInfo().setOaid(str);
                                    RequestInfo.getInstance(b.this.f4914f).getDevInfo().setSmOaid(str);
                                }
                            }
                        });
                    }
                    aq.a(this.f4914f, "SM_STATUS", (Object) 2);
                    return;
                }
                Long l2 = (Long) aq.b(this.f4914f, "SM_STATUS_EXPIRE_TIME", Long.valueOf(Long.parseLong("0")));
                if (l2.longValue() == 0 || Long.valueOf(System.currentTimeMillis() - l2.longValue()).longValue() <= 864000000) {
                    return;
                }
                aq.a(this.f4914f, "SM_STATUS", (Object) 0);
                aq.a(this.f4914f, "SM_STATUS_EXPIRE_TIME", Long.valueOf(Long.parseLong("0")));
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public String c() {
        return f4912j;
    }

    public String d() {
        return f4913k;
    }

    public Context e() {
        return this.f4914f;
    }

    public boolean f() {
        return this.f4918l;
    }

    public com.beizi.fusion.b.d g() {
        return this.f4917i;
    }

    public static b a() {
        if (f4910d == null) {
            synchronized (b.class) {
                if (f4910d == null) {
                    f4910d = new b();
                }
            }
        }
        return f4910d;
    }

    public String b() {
        return f4907a;
    }

    public b a(String str) {
        f4911e = str;
        return f4910d;
    }

    public void a(Context context, String str, String str2, String str3) {
        synchronized (b.class) {
            Log.e("BeiZis", "init start applicationCode：" + str);
            if (context != null) {
                if (!this.f4915g) {
                    String strA = ar.a();
                    f4908b = strA;
                    com.beizi.fusion.b.b bVar = new com.beizi.fusion.b.b(strA, "", "", "", str, "", "", String.valueOf(System.currentTimeMillis()), "");
                    this.f4917i = new com.beizi.fusion.b.d(bVar);
                    Log.d("BeiZis", "SDK_VERSION_MANAGER:4.90.2.36");
                    this.f4914f = context.getApplicationContext();
                    f4907a = str;
                    f4912j = str2;
                    f4913k = str3;
                    com.beizi.fusion.b.d dVar = this.f4917i;
                    dVar.f4803a.addObserver(dVar);
                    this.f4917i.a().a(bVar);
                    AppEventId.getInstance(this.f4914f).setAppStart();
                    AppEventId.getInstance(this.f4914f).setAppSdkInit();
                    if (this.f4917i.f4803a.a() == 0) {
                        if (BeiZis.isIsSyncInit()) {
                            i();
                        } else {
                            com.beizi.fusion.g.h.b().c().execute(new Runnable() { // from class: com.beizi.fusion.d.b.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    b.this.i();
                                }
                            });
                        }
                        this.f4915g = true;
                    } else {
                        Log.i("BeiZis", "init status error not kInitStatusUnknown");
                    }
                }
                Log.e("BeiZis", "init end");
            } else {
                throw new IllegalArgumentException("Context cannot be null.");
            }
        }
    }
}
