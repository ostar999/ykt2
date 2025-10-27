package com.tencent.open.b;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.tencent.connect.common.Constants;
import com.tencent.open.log.SLog;
import com.tencent.open.utils.i;
import com.tencent.open.utils.j;
import com.tencent.open.utils.k;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static b f20522a;

    /* renamed from: b, reason: collision with root package name */
    private String f20523b = "";

    /* renamed from: c, reason: collision with root package name */
    private String f20524c = "";

    /* renamed from: d, reason: collision with root package name */
    private String f20525d = "";

    /* renamed from: e, reason: collision with root package name */
    private String f20526e = "";

    /* renamed from: f, reason: collision with root package name */
    private String f20527f = "";

    /* renamed from: g, reason: collision with root package name */
    private String f20528g = "";

    /* renamed from: h, reason: collision with root package name */
    private String f20529h = "";

    /* renamed from: i, reason: collision with root package name */
    private List<Serializable> f20530i = Collections.synchronizedList(new ArrayList());

    /* renamed from: j, reason: collision with root package name */
    private List<Serializable> f20531j = Collections.synchronizedList(new ArrayList());

    /* renamed from: k, reason: collision with root package name */
    private Executor f20532k = j.b();

    /* renamed from: l, reason: collision with root package name */
    private boolean f20533l;

    private b() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        SLog.i("AttaReporter", "attaReportAtSubThread");
        if (!this.f20533l) {
            List<Serializable> listA = g.a().a("report_atta");
            this.f20533l = listA.isEmpty();
            this.f20530i.addAll(listA);
            Iterator<Serializable> it = listA.iterator();
            while (it.hasNext()) {
                SLog.i("AttaReporter", "attaReportAtSubThread from db = " + it.next());
            }
        }
        ArrayList arrayList = new ArrayList();
        while (!this.f20530i.isEmpty()) {
            c cVar = (c) this.f20530i.remove(0);
            if (!b(cVar)) {
                arrayList.add(cVar);
            }
        }
        if (arrayList.isEmpty()) {
            if (this.f20533l) {
                return;
            }
            SLog.i("AttaReporter", "attaReportAtSubThread clear db");
            g.a().b("report_atta");
            this.f20533l = true;
            return;
        }
        SLog.i("AttaReporter", "attaReportAtSubThread fail size=" + arrayList.size());
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            SLog.i("AttaReporter", "attaReportAtSubThread fail cache to db, " + ((c) ((Serializable) it2.next())));
        }
        g.a().a("report_atta", arrayList);
        this.f20533l = false;
    }

    public static synchronized b a() {
        if (f20522a == null) {
            f20522a = new b();
        }
        return f20522a;
    }

    private void b() {
        while (!this.f20531j.isEmpty()) {
            c cVar = (c) this.f20531j.remove(0);
            cVar.f20536a.put("appid", this.f20523b);
            cVar.f20536a.put("app_name", this.f20524c);
            cVar.f20536a.put(Constants.PARAM_APP_VER, this.f20526e);
            cVar.f20536a.put(Constants.PARAM_PKG_NAME, this.f20527f);
            cVar.f20536a.put("qq_install", this.f20528g);
            cVar.f20536a.put(Constants.PARAM_QQ_VER, this.f20529h);
            cVar.f20536a.put("openid", this.f20525d);
            cVar.f20536a.put("time_appid_openid", cVar.f20536a.get(CrashHianalyticsData.TIME) + StrPool.UNDERLINE + this.f20523b + StrPool.UNDERLINE + this.f20525d);
            StringBuilder sb = new StringBuilder();
            sb.append("fixDirtyData--------------------------");
            sb.append(cVar);
            SLog.i("AttaReporter", sb.toString());
            this.f20530i.add(cVar);
        }
    }

    public void a(String str, Context context) {
        SLog.i("AttaReporter", "init");
        this.f20523b = str;
        this.f20524c = i.a(context);
        this.f20526e = k.d(context, com.tencent.open.utils.f.b());
        this.f20527f = com.tencent.open.utils.f.b();
        this.f20528g = i.b(context) ? "1" : "0";
        this.f20529h = k.c(context, "com.tencent.mobileqq");
        b();
    }

    public void a(String str) {
        SLog.i("AttaReporter", "updateOpenId");
        if (str == null) {
            str = "";
        }
        this.f20525d = str;
    }

    private c b(String str, String str2, Object obj, Map<String, Object> map) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map2 = new HashMap();
        map2.put("attaid", "09400051119");
        map2.put("token", "9389887874");
        map2.put("time_appid_openid", jCurrentTimeMillis + StrPool.UNDERLINE + this.f20523b + StrPool.UNDERLINE + this.f20525d);
        map2.put(CrashHianalyticsData.TIME, String.valueOf(jCurrentTimeMillis));
        map2.put("openid", this.f20525d);
        map2.put("appid", this.f20523b);
        map2.put("app_name", this.f20524c);
        map2.put(Constants.PARAM_APP_VER, this.f20526e);
        map2.put(Constants.PARAM_PKG_NAME, this.f20527f);
        map2.put("os", "AND");
        map2.put("os_ver", Build.VERSION.RELEASE);
        map2.put("sdk_ver", Constants.SDK_VERSION);
        map2.put(Constants.PARAM_MODEL_NAME, Build.MODEL);
        map2.put("interface_name", str);
        map2.put("interface_data", str2);
        map2.put("interface_result", obj == null ? "" : obj.toString());
        map2.put("qq_install", this.f20528g);
        map2.put(Constants.PARAM_QQ_VER, this.f20529h);
        if (map != null && !map.isEmpty()) {
            Object obj2 = map.get("reserve1");
            map2.put("reserve1", obj2 == null ? "" : obj2.toString());
            Object obj3 = map.get("reserve2");
            map2.put("reserve2", obj3 == null ? "" : obj3.toString());
            Object obj4 = map.get("reserve3");
            map2.put("reserve3", obj4 == null ? "" : obj4.toString());
            Object obj5 = map.get("reserve4");
            map2.put("reserve4", obj5 != null ? obj5.toString() : "");
        }
        return new c((HashMap<String, String>) map2);
    }

    public void a(String str, String str2) {
        a(str, str2, null);
    }

    public void a(String str, String str2, Map<String, Object> map) {
        a(str, str2, "", map);
    }

    public void a(String str, Object obj) {
        a(str, "", obj, null);
    }

    public void a(String str, String str2, Object obj, Map<String, Object> map) {
        c cVarB = b(str, str2, obj, map);
        if (!TextUtils.isEmpty(this.f20523b) && !TextUtils.isEmpty(this.f20524c) && com.tencent.open.utils.f.a() != null) {
            a(cVarB);
            return;
        }
        SLog.i("AttaReporter", "attaReport cancel appid=" + this.f20523b + ", mAppName=" + this.f20524c + ", context=" + com.tencent.open.utils.f.a() + ", " + cVarB);
        this.f20531j.add(cVarB);
    }

    private void a(final c cVar) {
        this.f20532k.execute(new Runnable() { // from class: com.tencent.open.b.b.1
            @Override // java.lang.Runnable
            public void run() {
                b.this.f20530i.add(cVar);
                if (k.b(com.tencent.open.utils.f.a())) {
                    try {
                        b.this.c();
                        return;
                    } catch (Exception e2) {
                        SLog.e("AttaReporter", "Exception", e2);
                        return;
                    }
                }
                SLog.i("AttaReporter", "attaReport net disconnect, " + cVar);
            }
        });
    }

    private boolean b(c cVar) {
        int i2 = 0;
        do {
            i2++;
            try {
                SLog.i("AttaReporter", "doAttaReportItem post " + cVar);
                return com.tencent.open.a.a.a().b("https://h.trace.qq.com/kv", cVar.f20536a).d() == 200;
            } catch (Exception e2) {
                SLog.i("AttaReporter", "Exception", e2);
            }
        } while (i2 < 2);
        return false;
    }
}
