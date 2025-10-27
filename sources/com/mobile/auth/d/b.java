package com.mobile.auth.d;

import android.text.TextUtils;
import com.mobile.auth.BuildConfig;
import com.mobile.auth.d.a;
import com.mobile.auth.l.k;
import com.mobile.auth.l.n;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: c, reason: collision with root package name */
    private static b f9693c;

    /* renamed from: a, reason: collision with root package name */
    private com.mobile.auth.d.a f9694a;

    /* renamed from: b, reason: collision with root package name */
    private final com.mobile.auth.d.a f9695b;

    /* renamed from: d, reason: collision with root package name */
    private volatile boolean f9696d = false;

    /* renamed from: e, reason: collision with root package name */
    private a f9697e;

    public interface a {
        void a(com.mobile.auth.d.a aVar);
    }

    private b(boolean z2) {
        com.mobile.auth.d.a aVarA = new a.C0197a().a();
        this.f9695b = aVarA;
        if (z2) {
            this.f9694a = aVarA;
        } else {
            this.f9694a = d();
        }
    }

    public static b a(boolean z2) {
        if (f9693c == null) {
            synchronized (b.class) {
                if (f9693c == null) {
                    f9693c = new b(z2);
                }
            }
        }
        return f9693c;
    }

    private String a(String str, String str2) {
        String str3;
        String[] strArrSplit = str.split("&");
        int length = strArrSplit.length;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                str3 = "";
                break;
            }
            str3 = strArrSplit[i2];
            if (str3.contains(str2)) {
                break;
            }
            i2++;
        }
        return !TextUtils.isEmpty(str3) ? str3.substring(str3.lastIndexOf("=") + 1) : str3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(JSONObject jSONObject) throws JSONException, NumberFormatException {
        k.a aVarB = k.b("sso_config_xf");
        try {
            if (jSONObject.has("client_valid")) {
                aVarB.a("client_valid", System.currentTimeMillis() + (Integer.parseInt(jSONObject.getString("client_valid")) * 60 * 60 * 1000));
            }
            if (jSONObject.has("Configlist")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("Configlist");
                if (jSONObject2.has("CHANGE_HOST")) {
                    String string = jSONObject2.getString("CHANGE_HOST");
                    if (string.contains("M007")) {
                        String strA = a(string, "M007");
                        if (!TextUtils.isEmpty(strA)) {
                            aVarB.a("logHost", strA);
                        }
                    }
                    if (string.contains("M008")) {
                        String strA2 = a(string, "M008");
                        if (!TextUtils.isEmpty(strA2)) {
                            aVarB.a("https_get_phone_scrip_host", strA2);
                        }
                    }
                    if (string.contains("M009")) {
                        String strA3 = a(string, "M009");
                        if (!TextUtils.isEmpty(strA3)) {
                            aVarB.a("config_host", strA3);
                        }
                    }
                } else {
                    aVarB.a("logHost");
                    aVarB.a("https_get_phone_scrip_host");
                    aVarB.a("config_host");
                }
                a(jSONObject2, "CLOSE_FRIEND_WAPKS", "0", aVarB);
                a(jSONObject2, "CLOSE_LOGS_VERSION", "0", aVarB);
                a(jSONObject2, "CLOSE_IPV4_LIST", "0", aVarB);
                a(jSONObject2, "CLOSE_IPV6_LIST", "0", aVarB);
                a(jSONObject2, "CLOSE_M008_SDKVERSION_LIST", "0", aVarB);
                a(jSONObject2, "CLOSE_M008_APPID_LIST", "0", aVarB);
                if (jSONObject2.has("LOGS_CONTROL")) {
                    String[] strArrSplit = jSONObject2.getString("LOGS_CONTROL").replace("h", "").split("&");
                    if (strArrSplit.length == 2 && !TextUtils.isEmpty(strArrSplit[0]) && !TextUtils.isEmpty(strArrSplit[1])) {
                        try {
                            int i2 = Integer.parseInt(strArrSplit[0]);
                            int i3 = Integer.parseInt(strArrSplit[1]);
                            aVarB.a("maxFailedLogTimes", i2);
                            aVarB.a("pauseTime", i3);
                        } catch (Exception unused) {
                            com.mobile.auth.l.c.a("UmcConfigHandle", "解析日志上报限制时间次数异常");
                        }
                    }
                } else {
                    aVarB.a("maxFailedLogTimes");
                    aVarB.a("pauseTime");
                }
            }
            aVarB.b();
        } catch (Exception e2) {
            com.mobile.auth.l.c.a("UmcConfigHandle", "配置项异常，配置失效");
            e2.printStackTrace();
        }
    }

    private void a(JSONObject jSONObject, String str, String str2, k.a aVar) {
        if (!jSONObject.has(str)) {
            aVar.a(str);
            return;
        }
        String strOptString = jSONObject.optString(str, str2);
        if ("CLOSE_FRIEND_WAPKS".equals(str)) {
            if (TextUtils.isEmpty(strOptString)) {
                return;
            }
            if (!strOptString.contains("CU") && !strOptString.contains("CT") && !strOptString.contains("CM")) {
                return;
            }
        } else if (!"0".equals(strOptString) && !"1".equals(strOptString)) {
            return;
        }
        aVar.a(str, jSONObject.optString(str, str2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(com.cmic.sso.sdk.a aVar) {
        if (this.f9696d) {
            com.mobile.auth.l.c.a("UmcConfigHandle", "正在获取配置中...");
        } else {
            this.f9696d = true;
            com.mobile.auth.j.a.a().a(false, aVar, new com.mobile.auth.j.d() { // from class: com.mobile.auth.d.b.1
                @Override // com.mobile.auth.j.d
                public void a(String str, String str2, JSONObject jSONObject) {
                    try {
                        if ("103000".equals(str)) {
                            b.this.a(jSONObject);
                            k.a("sdk_config_version", BuildConfig.CMCC_SDK_VERSION);
                            b bVar = b.this;
                            bVar.f9694a = bVar.d();
                            if (b.this.f9697e != null) {
                                b.this.f9697e.a(b.this.f9694a);
                            }
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    b.this.f9696d = false;
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.mobile.auth.d.a d() {
        return new a.C0197a().a(d.b(this.f9695b.a())).c(d.a(this.f9695b.c())).b(d.b(this.f9695b.b())).d(d.c(this.f9695b.d())).d(d.a(this.f9695b.h())).e(d.b(this.f9695b.i())).a(d.e(this.f9695b.e())).b(d.d(this.f9695b.f())).c(d.c(this.f9695b.g())).f(d.f(this.f9695b.j())).a(d.a(this.f9695b.k())).b(d.b(this.f9695b.l())).a();
    }

    public com.mobile.auth.d.a a() {
        return this.f9695b;
    }

    public void a(final com.cmic.sso.sdk.a aVar) {
        if (d.a()) {
            n.a(new n.a() { // from class: com.mobile.auth.d.b.2
                @Override // com.mobile.auth.l.n.a
                public void a() {
                    com.mobile.auth.l.c.b("UmcConfigHandle", "开始拉取配置..");
                    b.this.b(aVar);
                }
            });
        }
    }

    public void a(a aVar) {
        this.f9697e = aVar;
    }

    public com.mobile.auth.d.a b() {
        return this.f9694a;
    }

    public void c() {
        k.a aVarB = k.b("sso_config_xf");
        aVarB.c();
        aVarB.b();
    }
}
