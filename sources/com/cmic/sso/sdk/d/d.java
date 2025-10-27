package com.cmic.sso.sdk.d;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.l.f;
import com.mobile.auth.l.k;
import com.mobile.auth.l.m;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private com.cmic.sso.sdk.a f6420a;

    private static void a(b bVar, com.cmic.sso.sdk.a aVar) {
        if (bVar == null || aVar == null) {
            return;
        }
        bVar.b(aVar.b("appid", ""));
        bVar.f(m.a());
        bVar.i(aVar.b("interfaceType", ""));
        bVar.h(aVar.b("interfaceCode", ""));
        bVar.g(aVar.b("interfaceElasped", ""));
        bVar.l(aVar.b("timeOut"));
        bVar.s(aVar.b("traceId"));
        bVar.v(aVar.b("networkClass"));
        bVar.n(aVar.b("simCardNum"));
        bVar.o(aVar.b("operatortype"));
        bVar.p(m.b());
        bVar.q(m.c());
        bVar.y(String.valueOf(aVar.b("networktype", 0)));
        bVar.t(aVar.b("starttime"));
        bVar.w(aVar.b("endtime"));
        bVar.m(String.valueOf(aVar.b("systemEndTime", 0L) - aVar.b("systemStartTime", 0L)));
        bVar.d(aVar.b("imsiState"));
        bVar.z(k.b("AID", ""));
        bVar.A(aVar.b("operatortype"));
        bVar.B(aVar.b("scripType"));
        com.mobile.auth.l.c.a("SendLog", "traceId" + aVar.b("traceId"));
    }

    private void a(JSONObject jSONObject) {
        com.mobile.auth.j.a.a().a(jSONObject, this.f6420a, new com.mobile.auth.j.d() { // from class: com.cmic.sso.sdk.d.d.1
            @Override // com.mobile.auth.j.d
            public void a(String str, String str2, JSONObject jSONObject2) {
                long jCurrentTimeMillis;
                com.mobile.auth.d.a aVarB = d.this.f6420a.b();
                HashMap map = new HashMap();
                if (!str.equals("103000")) {
                    if (aVarB.l() != 0 && aVarB.k() != 0) {
                        int iA = k.a("logFailTimes", 0) + 1;
                        if (iA >= aVarB.k()) {
                            map.put("logFailTimes", 0);
                            jCurrentTimeMillis = System.currentTimeMillis();
                        } else {
                            map.put("logFailTimes", Integer.valueOf(iA));
                        }
                    }
                    k.a(map);
                }
                map.put("logFailTimes", 0);
                jCurrentTimeMillis = 0;
                map.put("logCloseTime", Long.valueOf(jCurrentTimeMillis));
                k.a(map);
            }
        });
    }

    public void a(Context context, String str, com.cmic.sso.sdk.a aVar) {
        JSONArray jSONArray;
        String str2 = "";
        try {
            b bVarA = aVar.a();
            String strB = f.b(context);
            bVarA.e(str);
            bVarA.x(aVar.b("loginMethod", ""));
            bVarA.r(aVar.b("isCacheScrip", false) ? "scrip" : "pgw");
            bVarA.j(f.a(context));
            if (!TextUtils.isEmpty(strB)) {
                str2 = strB;
            }
            bVarA.k(str2);
            bVarA.c(aVar.b("hsaReadPhoneStatePermission", false) ? "1" : "0");
            a(bVarA, aVar);
            if (bVarA.f6391a.size() > 0) {
                jSONArray = new JSONArray();
                Iterator<Throwable> it = bVarA.f6391a.iterator();
                while (it.hasNext()) {
                    Throwable next = it.next();
                    StringBuffer stringBuffer = new StringBuffer();
                    JSONObject jSONObject = new JSONObject();
                    for (StackTraceElement stackTraceElement : next.getStackTrace()) {
                        stringBuffer.append("\n");
                        stringBuffer.append(stackTraceElement.toString());
                    }
                    jSONObject.put("message", next.toString());
                    jSONObject.put("stack", stringBuffer.toString());
                    jSONArray.put(jSONObject);
                }
                bVarA.f6391a.clear();
            } else {
                jSONArray = null;
            }
            if (jSONArray != null && jSONArray.length() > 0) {
                bVarA.a(jSONArray);
            }
            com.mobile.auth.l.c.a("SendLog", "登录日志");
            a(bVarA.b(), aVar);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void a(JSONObject jSONObject, com.cmic.sso.sdk.a aVar) {
        this.f6420a = aVar;
        a(jSONObject);
    }
}
