package com.cmic.sso.sdk.d;

import com.mobile.auth.BuildConfig;
import com.mobile.auth.i.g;
import com.xiaomi.mipush.sdk.Constants;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class b extends g {
    private String A;
    private String B;

    /* renamed from: o, reason: collision with root package name */
    private JSONArray f6405o;

    /* renamed from: y, reason: collision with root package name */
    private String f6415y;

    /* renamed from: z, reason: collision with root package name */
    private String f6416z;

    /* renamed from: b, reason: collision with root package name */
    private String f6392b = null;

    /* renamed from: c, reason: collision with root package name */
    private String f6393c = null;

    /* renamed from: d, reason: collision with root package name */
    private String f6394d = null;

    /* renamed from: e, reason: collision with root package name */
    private String f6395e = null;

    /* renamed from: f, reason: collision with root package name */
    private String f6396f = null;

    /* renamed from: g, reason: collision with root package name */
    private String f6397g = null;

    /* renamed from: h, reason: collision with root package name */
    private String f6398h = null;

    /* renamed from: i, reason: collision with root package name */
    private String f6399i = null;

    /* renamed from: j, reason: collision with root package name */
    private String f6400j = null;

    /* renamed from: k, reason: collision with root package name */
    private String f6401k = "";

    /* renamed from: l, reason: collision with root package name */
    private String f6402l = null;

    /* renamed from: m, reason: collision with root package name */
    private String f6403m = null;

    /* renamed from: n, reason: collision with root package name */
    private String f6404n = null;

    /* renamed from: p, reason: collision with root package name */
    private String f6406p = null;

    /* renamed from: q, reason: collision with root package name */
    private String f6407q = null;

    /* renamed from: r, reason: collision with root package name */
    private String f6408r = null;

    /* renamed from: s, reason: collision with root package name */
    private String f6409s = null;

    /* renamed from: t, reason: collision with root package name */
    private String f6410t = null;

    /* renamed from: u, reason: collision with root package name */
    private String f6411u = null;

    /* renamed from: v, reason: collision with root package name */
    private String f6412v = null;

    /* renamed from: w, reason: collision with root package name */
    private String f6413w = null;

    /* renamed from: x, reason: collision with root package name */
    private String f6414x = null;

    /* renamed from: a, reason: collision with root package name */
    public CopyOnWriteArrayList<Throwable> f6391a = new CopyOnWriteArrayList<>();

    public void A(String str) {
        this.A = str;
    }

    public void B(String str) {
        this.B = str;
    }

    @Override // com.mobile.auth.i.g
    public String a() {
        return null;
    }

    @Override // com.mobile.auth.i.g
    public String a(String str) {
        return null;
    }

    public void a(JSONArray jSONArray) {
        this.f6405o = jSONArray;
    }

    @Override // com.mobile.auth.i.g
    public JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appid", this.f6392b);
            jSONObject.put("traceId", this.f6393c);
            jSONObject.put("appName", this.f6394d);
            jSONObject.put("appVersion", this.f6395e);
            jSONObject.put(com.heytap.mcssdk.constant.b.C, BuildConfig.CMCC_SDK_VERSION);
            jSONObject.put("clientType", "android");
            jSONObject.put("timeOut", this.f6396f);
            jSONObject.put("requestTime", this.f6397g);
            jSONObject.put("responseTime", this.f6398h);
            jSONObject.put("elapsedTime", this.f6399i);
            jSONObject.put("requestType", this.f6400j);
            jSONObject.put("interfaceType", this.f6401k);
            jSONObject.put("interfaceCode", this.f6402l);
            jSONObject.put("interfaceElasped", this.f6403m);
            jSONObject.put("loginType", this.f6404n);
            jSONObject.put("exceptionStackTrace", this.f6405o);
            jSONObject.put("operatorType", this.f6406p);
            jSONObject.put("networkType", this.f6407q);
            jSONObject.put("networkClass", this.f6408r);
            jSONObject.put(Constants.PHONE_BRAND, this.f6409s);
            jSONObject.put("reqDevice", this.f6410t);
            jSONObject.put("reqSystem", this.f6411u);
            jSONObject.put("simCardNum", this.f6412v);
            jSONObject.put("imsiState", this.f6413w);
            jSONObject.put("resultCode", this.f6414x);
            jSONObject.put("is_phoneStatePermission", this.f6415y);
            jSONObject.put("AID", this.f6416z);
            jSONObject.put("sysOperType", this.A);
            jSONObject.put("scripType", this.B);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public void b(String str) {
        this.f6392b = str;
    }

    public void c(String str) {
        this.f6415y = str;
    }

    public void d(String str) {
        this.f6413w = str;
    }

    public void e(String str) {
        this.f6414x = str;
    }

    public void f(String str) {
        this.f6409s = str;
    }

    public void g(String str) {
        this.f6403m = str;
    }

    public void h(String str) {
        this.f6402l = str;
    }

    public void i(String str) {
        this.f6401k = str;
    }

    public void j(String str) {
        this.f6394d = str;
    }

    public void k(String str) {
        this.f6395e = str;
    }

    public void l(String str) {
        this.f6396f = str;
    }

    public void m(String str) {
        this.f6399i = str;
    }

    public void n(String str) {
        this.f6412v = str;
    }

    public void o(String str) {
        this.f6406p = str;
    }

    public void p(String str) {
        this.f6410t = str;
    }

    public void q(String str) {
        this.f6411u = str;
    }

    public void r(String str) {
        this.f6404n = str;
    }

    public void s(String str) {
        this.f6393c = str;
    }

    public void t(String str) {
        this.f6397g = str;
    }

    public void v(String str) {
        this.f6408r = str;
    }

    public void w(String str) {
        this.f6398h = str;
    }

    public void x(String str) {
        this.f6400j = str;
    }

    public void y(String str) {
        this.f6407q = str;
    }

    public void z(String str) {
        this.f6416z = str;
    }
}
