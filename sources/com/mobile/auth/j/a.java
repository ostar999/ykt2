package com.mobile.auth.j;

import android.os.SystemClock;
import com.cmic.sso.sdk.b;
import com.just.agentweb.DefaultWebClient;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.e.f;
import com.mobile.auth.i.e;
import com.mobile.auth.i.f;
import com.mobile.auth.i.h;
import com.mobile.auth.l.i;
import com.mobile.auth.l.k;
import com.mobile.auth.l.m;
import com.mobile.auth.l.o;
import com.mobile.auth.l.p;
import com.mobile.auth.l.q;
import com.weibo.ssosdk.WeiboSsoSdk;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static a f10391a;

    private a() {
    }

    public static a a() {
        if (f10391a == null) {
            synchronized (a.class) {
                if (f10391a == null) {
                    f10391a = new a();
                }
            }
        }
        return f10391a;
    }

    private void a(final c cVar, final d dVar, final com.cmic.sso.sdk.a aVar) {
        com.mobile.auth.h.d dVar2 = new com.mobile.auth.h.d();
        com.mobile.auth.h.c cVar2 = new com.mobile.auth.h.c();
        com.mobile.auth.h.a aVar2 = new com.mobile.auth.h.a();
        dVar2.a(cVar2);
        cVar2.a(aVar2);
        cVar.a(SystemClock.elapsedRealtime());
        dVar2.a(cVar, new com.mobile.auth.k.c() { // from class: com.mobile.auth.j.a.1
            private void a() {
                if (cVar.a().contains("uniConfig")) {
                    return;
                }
                q.c(aVar, String.valueOf(SystemClock.elapsedRealtime() - cVar.i()));
            }

            @Override // com.mobile.auth.k.c
            public void a(com.mobile.auth.k.a aVar3) throws JSONException {
                if (cVar.g()) {
                    a();
                    q.b(aVar, String.valueOf(aVar3.a()));
                    dVar.a(String.valueOf(aVar3.a()), aVar3.b(), f.a(String.valueOf(aVar3.a()), aVar3.b()));
                }
            }

            @Override // com.mobile.auth.k.c
            public void a(com.mobile.auth.k.b bVar) throws JSONException {
                if (cVar.g()) {
                    try {
                        a();
                        JSONObject jSONObject = new JSONObject(bVar.c());
                        String string = jSONObject.has("resultcode") ? jSONObject.getString("resultcode") : jSONObject.getString("resultCode");
                        q.b(aVar, string);
                        dVar.a(string, jSONObject.optString("desc"), jSONObject);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        a(com.mobile.auth.k.a.a(102223));
                    }
                }
            }
        }, aVar);
    }

    public void a(com.cmic.sso.sdk.a aVar, d dVar) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        String str;
        String strA;
        c bVar;
        int iC = aVar.c("networktype");
        h hVar = new h();
        hVar.b("1.0");
        hVar.c(BuildConfig.CMCC_SDK_VERSION);
        hVar.d(aVar.b("appid"));
        hVar.e(aVar.b("operatortype"));
        hVar.f(iC + "");
        hVar.g(m.a());
        hVar.h(m.b());
        hVar.i(m.c());
        hVar.j("0");
        hVar.k(WeiboSsoSdk.SDK_VERSION_CODE);
        hVar.l(q.b());
        hVar.m(o.a());
        hVar.o(aVar.b("apppackage"));
        hVar.p(aVar.b("appsign"));
        hVar.a_(k.b("AID", ""));
        if (aVar.c("logintype") == 3) {
            str = "pre";
        } else {
            hVar.w(aVar.b("userCapaid"));
            hVar.w(aVar.c("logintype") == 1 ? "200" : "50");
            str = "authz";
        }
        hVar.s(str);
        q.a(aVar, "scripAndTokenForHttps");
        com.mobile.auth.d.a aVarB = aVar.b();
        if (aVar.b("isCacheScrip", false)) {
            hVar.q(p.a(false));
            hVar.r(p.b(false));
            hVar.v(aVar.b("phonescrip"));
            hVar.n(hVar.u(aVar.b("appkey")));
            bVar = new c(DefaultWebClient.HTTPS_SCHEME + aVarB.a() + "/unisdk/rs/scripAndTokenForHttps", hVar, "POST", aVar.b("traceId"));
            bVar.a("defendEOF", "0");
        } else {
            e eVar = new e();
            eVar.a(aVar.a(b.a.f6384a));
            eVar.b(aVar.a(b.a.f6385b));
            eVar.a(hVar);
            eVar.a(false);
            aVar.a("isCloseIpv4", aVarB.h());
            aVar.a("isCloseIpv6", aVarB.i());
            String str2 = DefaultWebClient.HTTPS_SCHEME + aVarB.b() + "/unisdk/rs/scripAndTokenForHttps";
            if (aVar.b("use2048PublicKey", false)) {
                com.mobile.auth.l.c.a("BaseRequest", "使用2对应的编码");
                eVar.b("2");
                strA = i.a().b(aVar.a(b.a.f6384a));
            } else {
                strA = i.a().a(aVar.a(b.a.f6384a));
            }
            eVar.c(strA);
            bVar = new b(str2, eVar, "POST", aVar.b("traceId"));
            bVar.a("defendEOF", "1");
            if (iC == 3) {
                bVar.a(true);
                aVar.a("doNetworkSwitch", true);
            } else {
                bVar.a(false);
                aVar.a("doNetworkSwitch", false);
            }
        }
        bVar.a("interfaceVersion", WeiboSsoSdk.SDK_VERSION_CODE);
        a(bVar, dVar, aVar);
    }

    public void a(JSONObject jSONObject, com.cmic.sso.sdk.a aVar, d dVar) {
        com.mobile.auth.i.f fVar = new com.mobile.auth.i.f();
        f.a aVar2 = new f.a();
        f.b bVar = new f.b();
        bVar.e(q.b());
        bVar.f(o.a());
        bVar.b(WeiboSsoSdk.SDK_VERSION_CODE);
        bVar.c(aVar.b("appid", ""));
        bVar.d(bVar.u(""));
        aVar2.a(jSONObject);
        fVar.a(aVar2);
        fVar.a(bVar);
        a(new c(DefaultWebClient.HTTPS_SCHEME + aVar.b().d() + "/log/logReport", fVar, "POST", aVar.b("traceId")), dVar, aVar);
    }

    public void a(boolean z2, com.cmic.sso.sdk.a aVar, d dVar) {
        com.mobile.auth.i.b bVar = new com.mobile.auth.i.b();
        bVar.b("1.0");
        bVar.c("Android");
        bVar.d(k.b("AID", ""));
        bVar.e(z2 ? "1" : "0");
        bVar.f(BuildConfig.CMCC_SDK_VERSION);
        bVar.g(aVar.b("appid"));
        bVar.h(bVar.u("iYm0HAnkxQtpvN44"));
        a(new c(DefaultWebClient.HTTPS_SCHEME + aVar.b().c() + "/client/uniConfig", bVar, "POST", aVar.b("traceId")), dVar, aVar);
    }
}
